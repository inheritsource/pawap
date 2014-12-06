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

import grails.converters.*

import org.motrice.docbox.DocData
import org.motrice.docbox.Util
import org.motrice.docbox.form.PxdFormdefVer
import org.motrice.docbox.form.PxdItem
import org.motrice.signatrice.ServiceException

class RestDocController {
  private final static Integer CONFLICT_STATUS = 409
  private final static CONT_DISP = 'Content-Disposition'
  def docService
  def pdfService
  def signdocService

  /**
   * Create a PDF/A document given a formDataUuid and return metadata.
   * Do not create anything in case the formDataUuid has already been created.
   * Returns status 201 if the document was created at this time,
   * 200 if it already existed, 404 if the form was not found.
   */
  def formDataPut(String uuid) {
    if (log.debugEnabled) log.debug "FORM PUT << ${Util.clean(params)}, ${request.forwardURI}"
    Integer status = 404

    // Does the document exist?
    def docStep = docService.findStepByUuid(uuid)
    def pdfContents = docStep? docStep.pdfContents() : null
    if (docStep && pdfContents) {
      status = 200
    } else {
      // The document has to be created. Begin by retrieving form data.
      def docData = pdfService.retrieveDocData(uuid)
      if (docData && !docStep) docStep = docService.createBoxDocStep(uuid)
      if (docData && docStep) {
	try {
	  pdfContents = pdfService.generatePdfa(docData, docStep, log.debugEnabled)
	  status = 201
	} catch (ServiceException exc) {
	  pdfContents = exc.canonical
	  status = CONFLICT_STATUS
	}
      }
    }

    if (docStep && pdfContents && status < 300) {
      if (log.debugEnabled) log.debug "FORM PUT >> ${status}: ${docStep}, ${pdfContents}"
      render(status: status, contentType: 'text/json') {
	formDataUuid = uuid
	docboxRef = docStep.docboxRef
	docNo = docStep.docNo
	signCount = docStep.signCount
	checkSum = pdfContents.checksum
      }
    } else if (status == CONFLICT_STATUS) {
      if (log.debugEnabled) log.debug "FORM PUT CONFLICT >> ${status}: ${pdfContents}"
      render(status: status, contentType: 'text/json') {
	formDataUuid = uuid
	conflictMessage = pdfContents
      }
    } else {
      render(status: 404)
    }
  }

  /**
   * Get original form data given a docboxRef.
   * NOTE that the parameter is a docboxRef in this case, different from the PUT method.
   */
  def docboxOrbeonData(String uuid) {
    if (log.debugEnabled) log.debug "FORMDATA: ${Util.clean(params)}, ${request.forwardURI}"
    def pdfContents = null
    String msg = null
    Integer status = 404

    def docboxref = uuid
    def docStep = docService.findStepByRef(docboxref)
    if (docStep) {
	pdfContents = docService.findPdfContents(docStep)
	if (!pdfContents) msg = "DOCBOX.120|PDF contents not found: ${docboxref}"
    } else {
      msg = "DOCBOX.110|Document not found: ${docboxref}"
    }

    if (msg) {
      render(status: status, contentType: 'text/plain', text: msg)
    } else {
      def formdataMap = signdocService.formdataMap(pdfContents)
      if (formdataMap) {
	response.status = 200
	render formdataMap as JSON
      } else {
	render(status: 409, contentType: 'text/plain', text: "DOCBOX.119|Form data not found in ${docboxref}")
      }
    }
  }

  /**
   * Get a document given a docboxRef
   */
  def docboxRefGet(String docboxref) {
    if (log.debugEnabled) log.debug "BY REF: ${Util.clean(params)}, ${request.forwardURI}"
    def docStep = docService.findStepByRef(docboxref)
    return contentsResponse(docStep, params.item)
  }

  /**
   * Get a document given a formDataUuid and optionally a step parameter.
   * Without a step parameter the method returns the latest step of the
   * document with the given formDataUuid.
   */
  def formDataGet(String uuid) {
    if (log.debugEnabled) log.debug "BY UUID: ${Util.clean(params)}, ${request.forwardURI}"
    def docStep = null
    if (params.step) {
      def stepNumber = params.step as Integer
      docStep = docService.findStepByUuid(uuid, stepNumber)
    } else {
      docStep = docService.findStepByUuid(uuid)
    }

    return contentsResponse(docStep, params.item)
  }

  /**
   * Get document metadata given a docboxRef
   */
  def metaDataGet(String docboxref) {
    if (log.debugEnabled) log.debug "META: ${Util.clean(params)}, ${request.forwardURI}"
    def docStep = null
    try {
      docStep = docService.findStepByRefExc(docboxref)
    } catch (ServiceException exc) {
      render(status: 404, text: exc.canonical, contentType: 'text/plain', encoding: 'UTF-8')
      return
    }

    return metadataResponse(docStep, params.item)
  }

  /**
   * Get document metadata given a docboxRef
   */
  def metaByFormData(String uuid) {
    if (log.debugEnabled) log.debug "META BY FORMDATA: ${Util.clean(params)}, ${request.forwardURI}"
    def docStep = null
    try {
      if (params.step) {
	def stepNumber = params.step as Integer
	docStep = docService.findStepByUuidExc(uuid, stepNumber)
      } else {
	docStep = docService.findStepByUuidExc(uuid)
      }
    } catch (ServiceException exc) {
      render(status: 404, text: exc.canonical, contentType: 'text/plain', encoding: 'UTF-8')
      return
    }

    if (log.debugEnabled) log.debug "metaByFormData: ${docStep}"
    return metadataResponse(docStep, params.item)
  }

  private contentsResponse(BoxDocStep docStep, String itemName) {
    def contents = null
    if (docStep) contents = docService.findContents(docStep, itemName)

    if (contents) {
      if (log.debugEnabled) log.debug "FOUND: ${contents}"
      String contDisp = "attachment;filename=${contents.fileName}"
      response.setHeader(CONT_DISP, contDisp)
      if (contents.binary) {
	response.status = 200
	response.contentType = contents.contentType
	response.getOutputStream().withStream {stream ->
	  stream.bytes = contents.stream
	}
      } else {
	render(status: 200, text: contents.text, contentType: contents.contentType,
	encoding: 'UTF-8')
      }
    } else {
      response.status = 404
    }
  }

  /**
   * Respond with metadata.
   */
  private metadataResponse(BoxDocStep docStep, String itemName) {
    def contents = itemName? docService.findContents(docStep, itemName) : null
    def contentsMeta = [:]
    if (itemName) {
      if (contents) {
	contentsMeta.itemName = itemName
	contentsMeta.itemFormat = contents.format
	contentsMeta.itemChecksum = contents.checksum
	contentsMeta.itemSize = contents.size
      } else {
	contentsMeta.itemConflict = "DOCBOX.117|Contents not found for item name ${itemName}"
      }
    }

    def meta = docStep.meta
    meta.putAll(contentsMeta)
    if (log.debugEnabled) log.debug "metadataResponse: ${meta}"
    response.status = 200
    render meta as JSON
  }

}
