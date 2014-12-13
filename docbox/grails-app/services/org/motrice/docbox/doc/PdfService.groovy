/* == Motrice Copyright Notice ==
 *
 * Motrice Service Platform
 *
 * Copyright (C) 2011-2014 Motrice AB
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * e-mail: info _at_ motrice.se
 * mail: Motrice AB, Långsjövägen 8, SE-131 33 NACKA, SWEDEN
 * phone: +46 8 641 64 14
 */
package org.motrice.docbox.doc

import org.motrice.docbox.DocData
import org.motrice.docbox.form.PxdFormdefVer
import org.motrice.docbox.form.PxdItem
import org.motrice.docbox.pdf.Attachment
import org.motrice.docbox.pdf.Control
import org.motrice.docbox.pdf.FormData
import org.motrice.docbox.pdf.FormDef
import org.motrice.docbox.pdf.FormMeta
import org.motrice.docbox.pdf.Options
import org.motrice.docbox.pdf.Processor
import org.motrice.docbox.pdf.Section
import org.motrice.docbox.pdf.Tag
import org.motrice.signatrice.ServiceException

// The only way to create a logger with a predictable name?
import org.apache.commons.logging.LogFactory

/**
 * Logic for converting an Orbeon form to PDF/A.
 */
class PdfService {
  static transactional = true
  private static final log = LogFactory.getLog(this)

  def grailsApplication
  def docService
  def signdocService

  static final UNKNOWN_MOTRICE_SITE_NAME = '***Okänd***'

  /**
   * Retrieve all database objects relevant for a form instance.
   * @param uuid must be the uuid of the form instance.
   * @return a DocData object, or null if form data is not found.
   */
  DocData retrieveDocData(String uuid) {
    def docData = null
    def dataItem = PxdItem.findWhere(uuid: uuid, format: 'xml', instance: true)
    if (dataItem) {
      docData = new DocData(uuid, dataItem)
      String formDefPath = docData.dataItem.formDef
      // Find attachments
      docData.auxItems = PxdItem.findAllWhere(uuid: docData.uuid, format: 'binary',
      instance: true)
      docData.formDef = PxdItem.findWhere(formDef: formDefPath, instance: false)
      docData.formMeta = PxdFormdefVer.findWhere(path: formDefPath)
      // Find fixed items, part of the form definition
      docData.fixedItems =
	PxdItem.findAllWhere(uuid: docData.formMeta.formdef.uuid, instance: false)
    }

    if (log.debugEnabled) log.debug "retrieveDocData: ${docData}"
    return docData
  }

  /**
   * Convert a form instance to PDF/A
   * @param docData must contain all database objects relevant for a
   * form instance
   * @return a BoxContents object
   */
  BoxContents generatePdfa(DocData docData, BoxDocStep docStep, boolean debug) {
    def formData = new FormData(docData.dataItem.text)
    def formDef = new FormDef(docData.formDef.text, localSiteName())
    def formatSpec = signdocService.pdfFormatName()
    formDef.build(docData, docStep, formatSpec, log)
    if (log.debugEnabled) log.debug formDef.dump()
    createPreview(docStep, formDef, formData)
    def docContents = createDocBook(docStep, formDef, formData)
    return docbookXmlToPdf(docStep, docData, docContents, debug)
  }

  /**
   * Pick up the Motrice site name, or a default text.
   */
  private String localSiteName() {
    grailsApplication.config.motrice.site.name ?: UNKNOWN_MOTRICE_SITE_NAME
  }

  /**
   * Create a DocBook equivalent of the form
   * SIDE EFFECT: A new BoxContents containing DocBook XML is attached
   * to form data
   * RETURN a map containing DocBook and RDF contents, with the following keys:
   * docbook: BoxContents with DocBook XML
   * rdf: BoxContents with RDF (metadata) XML fragment
   * xref: Form cross-reference as an XML structure (String)
   */
  private Map createDocBook(BoxDocStep docStep, formDef, formData) {
    def map = formDef.generateDocBook(formData, log)

    def docbook = docService.createContents(docStep, 'docbook.xml', 'xml')
    docbook.assignText(map.xmldocbook)
    if (!docbook.save(insert: true)) {
      log.error "BoxContents save: ${docbook.errors.allErrors.join(',')}"
    }

    def rdf = docService.createContents(docStep, 'rdf.xml', 'xml')
    rdf.assignText(map.xmlrdf)
    if (!rdf.save(insert: true)) {
      log.error "BoxContents save: ${rdf.errors.allErrors.join(',')}"
    }

    def formXref = formDef.generateFormLabelXref(formData)
    return [docbook: docbook, rdf: rdf, xref: formXref]
  }

  /**
   * Create an all-text form preview for debugging
   * SIDE EFFECT: A new PxdItem containing the text is attached to
   * form data
   */
  private createPreview(BoxDocStep docStep, FormDef formDef, FormData formData) {
    def pv = formDef.generateTextPreview(formData)
    def preview = docService.createContents(docStep, 'preview', 'text')
    preview.assignText(pv?.toString())
    if (log.debugEnabled) "createPreview: ${preview}"
    if (!preview.save(insert: true)) {
      log.error "BoxContents save: ${preview.errors.allErrors.join(',')}"
    }

    return preview
  }

  /**
   * Convert a DocBook XML PxdItem, generating PDF BoxContents.
   * Add the new Pdf BoxContents to the data.
   * Return the new contents or throw a ServiceException on failure
   * Throwing a RuntimeException will roll back the transaction
   */
  private BoxContents docbookXmlToPdf(BoxDocStep docStep, DocData docData,
				      Map docContents, boolean debug)
  {
    def processor = new Processor(debug)
    if (log.debugEnabled) {
      log.debug "docbookXmlToPdf ${docStep} << ${processor.tempDir.absolutePath}"
    }
    // Store DocBook and RDF xml
    storeBoxContents(docContents.docbook, processor.tempDir)
    storeBoxContents(docContents.rdf, processor.tempDir)

    // Copy the contents of all pxdItems to files in the temp directory
    // defined by the processor.
    // 1. Attachments:
    docData.auxItems.each {pxdItem ->
      storePxdItem(pxdItem, processor.tempDir, 'attachment')
    }
    // 2. Fixed items:
    docData.fixedItems.each {pxdItem ->
      storePxdItem(pxdItem, processor.tempDir, 'fixed item')
    }

    // Run the conversion pipeline
    def result = processor.toPdf()
    if (log.debugEnabled) log.debug "toPdf returns ${result}"
    def pdfPath = result.pdf
    def logPath = result.log
    def excMsg = result.exc
    def htmlPath = null

    // Change when HTML conversion is complete
    if (false) {
      result = processor.toHtml()
      if (log.debugEnabled) log.debug "toHtml returns ${result}"
      htmlPath = result.html
      logPath = result.log
    }

    // Store the newly generated pdf
    def pdfFile = new File(pdfPath)
    def pdf = null
    if (pdfFile.exists()) {
      pdf = docService.createContents(docStep, 'pdf', 'binary')
      // Insert form data into the generated PDF
      def pdfBytes = null
      try {
	pdfBytes = signdocService.pdfPostProcess(pdfFile, docData, docContents.xref)
      } catch (Exception exc) {
	log.error "SigndocService.pdfPostProcess: ${exc.message}"
	def sw = new StringWriter()
	def pw = new PrintWriter(sw)
	exc.printStackTrace(pw)
	log.error sw.toString()
	// Don't let this feature spoil the show
	pdfBytes = pdfFile.bytes
      }

      pdf.assignStream(pdfBytes, true)
      if (!pdf.save()) {
	log.error "BoxContents (pdf) save: ${pdf.errors.allErrors.join(',')}"
      }
    }

    // Store the generated HTML
    if (htmlPath) {
      def htmlFile = new File(htmlPath)
      def html = null
      if (htmlFile.exists()) {
	html = docService.createContents(docStep, 'html', 'xml')
	html.assignText(htmlFile.text)
	if (!html.save(insert: true)) {
	  log.error "BoxContents (html) save: ${html.errors.allErrors.join(',')}"
	}
      }
    }

    // Store the log file
    def logFile = new File(logPath)
    if (logFile.exists()) {
      def conversionLog = docService.createContents(docStep, 'convlog', 'text')
      conversionLog.assignText(logFile.text)
      if (!conversionLog.save(insert: true)) {
	log.error "BoxContents (log) save: ${conversionLog.errors.allErrors.join(',')}"
      }
    }

    // Clean up temp files
    processor.cleanUp()
    if (excMsg) {
      log.error "Pdf conversion FAILS: ${excMsg}"
      throw new ServiceException('DOCBOX.106', excMsg)
    }

    if (log.debugEnabled) log.debug "docbookXmlToPdf >> ${pdf}"
    return pdf
  }

  /**
   * Store BoxContents in the temp directory
   */
  private storeBoxContents(BoxContents boxContents, File tempDir) {
    // Do not store anything with a slash in the path
    if (boxContents.name.indexOf('/') > 0) return
    def tgtFile = new File(tempDir, boxContents.name)
    if (boxContents.text) {
      tgtFile.text = boxContents.text
    } else if (boxContents.stream) {
      tgtFile.bytes = boxContents.stream
    }
  }

  /**
   * Store pxdItem content in a temp directory in the local file system.
   * The text or stream of the pxdItem is copied into a file.
   */
  private storePxdItem(PxdItem pxdItem, File tempDir, String comment) {
    // Do not store anything with a slash in the path
    if (pxdItem.path.indexOf('/') > 0) return
    if (log.debugEnabled) log.debug "${comment}: ${pxdItem}"
    def tgtFile = new File(tempDir, pxdItem.path)
    if (pxdItem.text) {
      tgtFile.text = pxdItem.text
    } else if (pxdItem.stream) {
      tgtFile.bytes = pxdItem.stream
    }
  }

}
