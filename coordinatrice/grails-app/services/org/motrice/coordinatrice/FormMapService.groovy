package org.motrice.coordinatrice

import org.apache.commons.logging.LogFactory
import grails.converters.*
import org.motrice.coordinatrice.pxd.PxdFormdefVer

class FormMapService {
  static transactional = true

  private static final log = LogFactory.getLog(this)

  /**
   * Create a form map from a form definition version.
   * Copy variables from the previous published version, if possible.
   * The initial form map will have no variable names defined.
   */
  CrdFormMap createMapFromForm(id) {
    if (log.debugEnabled) log.debug "createMapFromForm << ${id}"
    // Note that "get" is the only method that casts to Long.
    def formDef = PxdFormdefVer.get(id)
    if (!formDef) {
      def msg = "Form definition not found with id ${id}"
      throw new ServiceException(msg, 'crdFormMap.formdef.not.found', [id])
    }

    def prevMap = valuesFromPreviousVersion(formDef)
    def formMap = new CrdFormMap()
    formMap.id = formDef.id
    def sections = null
    try {
      sections = formdefControlList(formDef.formXml, prevMap)
    } catch (Exception exc) {
      def msg = "Problem extracting controls: ${exc.message}"
      throw new ServiceException(msg, 'crdFormMap.extraction.conflict', [exc.message])
    }

    formMap.jsonMap = sections as JSON
    if (!formMap.save()) log.error "formMap save: ${formMap.errors.allErrors.join(', ')}"
    if (log.debugEnabled) log.debug "createMapFromForm >> ${sections}"
    return formMap
  }

  /**
   * Given a published form definition, extract section and
   * control names.
   * formXml must be the form definition contents.
   * prevMap may be a map containing values from the previous published
   * version of the form definition, or null.
   * See CrdFormMap for a description of the resulting data structure.
   * The SAX parser throws exception on conflict.
   * NOTE: Tested only with published form definitions.
   */
  private List formdefControlList(String formXml, Map prevMap) {
    def xml = new XmlSlurper().parseText(formXml)
    def instance = xml.'**'.find {it.@id == 'fr-form-instance'}
    def sections = []
    instance.form.'*'.each {section ->
      def sectionName = section.name()
      def descr = [section: sectionName]
      def controls = []
      section.'*'.each {control ->
	def controlName = control.name()
	def value = null
	if (prevMap) value = prevMap[fieldId(sectionName, controlName)]
	controls << [control: controlName, variable: value]
      }
      descr.controls = controls
      sections << descr
    }

    return sections
  }

  /**
   * Convert a JSON form control list back to Java.
   */
  private List controlListFromJson(CrdFormMap formMap) {
    JSON.parse(formMap.jsonMap)
  }

  /**
   * Create a control list suitable for the edit view.
   * RETURN a list of maps.
   * Each map contains entries "section", "control", "variable", "id".
   * The values are all strings.
   * The "id" is a generated input id for collecting the input.
   */
  List controlListView(CrdFormMap formMap) {
    if (log.debugEnabled) log.debug "controlListView << ${formMap}"
    def controlList = controlListFromJson(formMap)
    def viewList = []
    controlList.each {section ->
      def sectionName = section.section
      section.controls.each {control ->
	def controlName = control.control
	viewList << [section: sectionName, control: controlName,
	variable: control.variable, id: fieldId(sectionName, controlName)]
      }
    }

    if (log.debugEnabled) {
      def debugList = (viewList.size() > 7)? viewList[0..7] : viewList
      log.debug "controlListView >> ${debugList}"
    }
    return viewList
  }

  /**
   * Update a variable list from user input without saving.
   */
  CrdFormMap updateVariableList(CrdFormMap formMap, params) {
    // The old control list in Java format.
    def controlList = controlListFromJson(formMap)
    // Store relevant input in a map.
    def valueMap = params.inject([:]) {map, param ->
      def key = param.key
      def value = param.value
      if (key.startsWith('VAR') && value) map[key] = value
      return map
    }

    // Iterate over the control list and make changes.
    controlList.each {section ->
      def sectionName = section.section
      section.controls.each {control ->
	def controlName = control.control
	def inputId = fieldId(sectionName, controlName)
	control.variable = valueMap[inputId]
      }
    }

    formMap.jsonMap = controlList as JSON
    return formMap
  }

  private String fieldId(String sectionName, String controlName) {
    "VAR_${sectionName}_${controlName}"
  }

  /**
   * Create a value map from the previous published version of a form definition.
   * RETURN a map mapping from field id to variable name.
   * The map is empty if the preceding published version does not have a
   * variable map.
   */
  private Map valuesFromPreviousVersion(PxdFormdefVer formDef) {
    // Find the previous published version.
    def prev = formDef.previousPublished()
    if (log.debugEnabled) log.debug "valuesFromPreviousVersion << ${formDef} -> ${prev}"
    def prevMap = [:]
    if (prev) {
      def prevFormMap = CrdFormMap.get(prev.id)
      def fullPrevMap = null
      if (prevFormMap) fullPrevMap = JSON.parse(prevFormMap.jsonMap)
      if (fullPrevMap) {
	fullPrevMap.each {section ->
	  def sectionName = section.section
	  section.controls.each {control ->
	    def controlName = control.control
	    if (control.variable) {
	      prevMap[fieldId(sectionName, controlName)] = control.variable
	    }
	  }
	}
      }
    }

    if (log.debugEnabled) log.debug "valuesFromPreviousVersion >> ${prevMap}"
    return prevMap
  }

}
