package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class PastProcinstController {

  def pastProcinstService
  def procdefService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "filterlist", params: params)
  }

  /**
   * Define filter for querying historic process instances.
   * Special parameters:
   * clearform (Integer): clear the form if > 0
   * selectedProcdef (String): pre-selected process definition id.
   */
  def filterdef() {
    if (log.debugEnabled) log.debug "FILTER DEF ${params}"
    def procdefKeyList = procdefService.allProcessDefinitionKeys()
    def procdefs = procdefService.allProcessDefinitionsIdNameLists()
    def procdefIdList = procdefs.keys
    def procdefNameList = procdefs.values

    def lastFilter = params.clearform? null : pastProcinstService.findLastFilter(session.id)
    def selectedProcdef = params.selectedProcdef ?: lastFilter?.procdefId

    [procdefKeyList: procdefKeyList, procdefIdList: procdefIdList, procdefNameList: procdefNameList,
    procdefKeyList: procdefKeyList, filterInst: lastFilter, selectedProcdef: selectedProcdef]
  }

  /**
   * List process instances using a filter.
   * The filter is persisted to allow paging.
   */
  def filterlist(Integer max) {
    if (log.debugEnabled) log.debug "FILTER LIST (${max}) filter=${params?.filterId}"
    def filter = pastProcinstService.lookupOrCreateFilter(params, session.id)
    def result = pastProcinstService.filterProcessInstancesUnpopulated(filter)

    params.max = Math.min(max ?: 15, 100)
    params.offset = params.offset as Integer ?: 0
    def total = result?.size()
    def maxIndex = Math.min(params.offset + params.max, total)
    def procInstView = result.subList(params.offset, maxIndex)
    // After computing the slice to be shown, populate it.
    def procInstList = pastProcinstService.populateProcessInstances(procInstView)
    [pastProcInstList: procInstList, pastProcInstTotal: total, filterId: filter.id]
  }

  /**
   * Create a summary from filtering process instances.
   */
  def filtersummary() {
    if (log.debugEnabled) log.debug "FILTER SUMMARY ${params}"
    def filter = pastProcinstService.lookupOrCreateFilter(params, session.id)
    def piList = pastProcinstService.filteredProcessInstanceSummary(filter)
    [pastProcInstList: piList, pastProcInstTotal: piList.size(), filterId: filter.id]
  }

  def show(String id) {
    if (log.debugEnabled) log.debug "SHOW PAST PROCINST ${params}"
    def pastProcInst = pastProcinstService.findPastProcinst(id)
    if (!pastProcInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'pastProcinst.label', default: 'PastProcinst'), id])
      redirect(action: "list")
      return
    }

    [pastProcInst: pastProcInst]
  }

  /**
   * Display process variables.
   * AJAX invocation.
   */
  def showProcessVariables(String id) {
    if (log.debugEnabled) log.debug "SHOW PROCESS VARIABLES ${params}"
    def procInst = pastProcinstService.findPastProcinstWithVars(id)
    render(template: "/processVariables", model: [procVarMap: procInst.variables])
  }

}
