package org.motrice.coordinatrice

import org.apache.commons.logging.LogFactory
import grails.converters.*
import org.motrice.coordinatrice.pxd.PxdFormdefVer
import org.motrice.coordinatrice.pxd.PxdItem

class FormMapService {
  static transactional = true

  def procdefService

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
   * Create a variable map given a form definition id and a form instance.
   * If the form definition does not have a variable map, convert all controls to
   * variables.
   */
  Map createVariableMap(Long formdefId, PxdItem forminst) {
    if (log.debugEnabled) log.debug "createVariableMap << ${formdefId}, ${forminst}"
    def formMap = CrdFormMap.get(formdefId)
    def varMap = [:]
    try {
      def xml = new XmlSlurper().parseText(forminst.text)
      if (formMap) {
	def varSpec = flattenVariableMap(formMap)
	xml.'*'.each {section ->
	  def sectionName = section.name()
	  section.'*'.each {control ->
	    def controlName = control.name()
	    def variable = varSpec[fieldId(sectionName, controlName)]
	    if (variable) varMap[variable] = control?.text()?.trim()
	  }
	}
      } else {
	// The form definition has no variable map.
	xml.'*'.each {section ->
	  section.'*'.each {control ->
	    def controlName = control.name()
	    varMap[controlName] = control?.text()?.trim()
	  }
	}
      }
    } catch (org.xml.sax.SAXException exc) {
      def msg = exc.toString()
      throw new ServiceException(msg, 'procinst.parse.conflict', [exc.message])
    }

    if (log.debugEnabled) log.debug "createVariableMap >> ${varMap}"
    return varMap
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
    // Store relevant input (keys beginning with 'VAR') in a map.
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

  /**
   * Check parameters for starting a process instance.
   * formdefId must be the id of a start form.
   */
  Procinst processInstancePrepare(Long formdefId) {
    if (log.debugEnabled) log.debug "processInstancePrepare << ${formdefId}"
    def startFormDef = MtfStartFormDefinition.findByFormdefId(formdefId)
    if (!startFormDef) {
      def msg = "Start form definition not found with id ${formdefId}"
      throw new ServiceException(msg, 'mtfStartFormDefinition.not.found', [formdefId])
    }

    def result = doProcessInstancePrepare(startFormDef, null)
    if (log.debugEnabled) log.debug "processInstancePrepare >> ${result}"
    return result
  }

  /**
   * Check parameters for starting a process instance.
   * startFormDefId must be an MtfStartFormDefinition id defining the connection
   * between process definition and start form,
   * forminstPath must contain the unique path of a start form instance,
   * assignee must be the assignee of the started process instance.
   */
  Procinst processInstancePrepare(String startFormDefId, String forminstPath, String assignee) {
    if (log.debugEnabled) log.debug "processInstancePrepare << ${startFormDefId}, ${forminstPath}"
    def startFormDef = MtfStartFormDefinition.get(startFormDefId)
    if (!startFormDef) {
      def msg = "Start form definition not found with id ${formdefId}"
      throw new ServiceException(msg, 'mtfStartFormDefinition.not.found', [formdefId])
    }

    def result = doProcessInstancePrepare(startFormDef, forminstPath)
    result.assignee = assignee
    if (log.debugEnabled) log.debug "processInstancePrepare >> ${result}"
    return result
  }

  private Procinst doProcessInstancePrepare(MtfStartFormDefinition startFormDef,
					   String forminstPath)
  {
    def formdefId = startFormDef.formdefId
    def formdef = PxdFormdefVer.get(formdefId)
    if (!formdef) {
      def msg = "Form definition version not found with id ${formdefId}"
      throw new ServiceException(msg, 'pxdFormdefVer.not.found', [formdefId])
    }

    def procdefId = startFormDef.procdefId
    def procdef = procdefService.findShallowProcdef(procdefId)
    if (!procdef) {
      def msg = "Process definition not found with id ${procdefId}"
      throw new ServiceException(msg, 'procdef.not.found', [procdefId])
    }

    def forminst = null
    if (forminstPath) {
      forminst = PxdItem.findByPath(forminstPath)
      if (!forminst) {
	def msg = "Form instance not found with path ${forminstPath}"
	throw new ServiceException(msg, 'procinst.form.data.not.found', [forminstPath])
      }
    }

    def result = new Procinst(msfd: startFormDef, formdef: formdef, procdef: procdef)
    if (forminst) result.forminst = forminst
    return result
  }

  /**
   * Given form data and a form variable map, extract process instance
   * variables (names and values).
   */
  Map processInstanceVariables() {
    if (log.debugEnabled) log.debug "processInstanceVariables <<"
    if (log.debugEnabled) log.debug "processInstanceVariables >>"
  }

  /**
   * Convert a JSON form control list back to Java.
   */
  private List controlListFromJson(CrdFormMap formMap) {
    JSON.parse(formMap.jsonMap)
  }

  private String fieldId(String sectionName, String controlName) {
    "VAR_${sectionName}_${controlName}"
  }

  /**
   * Given a CrdFormMap instance, generate a map having keys
   * consisting of section and control names as defined by the "fieldId" method.
   * It should not be necessary to qualify control names, but do it all the
   * same.
   */
  private Map flattenVariableMap(CrdFormMap formMap) {
    def controlList = controlListFromJson(formMap)
    // Store relevant input in a map.
    def valueMap = controlList.inject([:]) {map, sectionMap ->
      def sectionName = sectionMap.section
      sectionMap.controls.each {controlMap ->
	def controlName = controlMap.control
	def variable = controlMap.variable
	if (variable) map[fieldId(sectionName, controlName)] = variable
      }

      return map
    }

    if (log.debugEnabled) log.debug "flattenVariableMap: ${valueMap}"
    return valueMap
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
