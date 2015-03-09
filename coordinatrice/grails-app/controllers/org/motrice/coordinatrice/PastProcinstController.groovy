package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class PastProcinstController {

  def pastProcinstService
  def procdefService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  /**
   * Define filter for querying historic process instances.
   */
  def filterdef() {
    if (log.debugEnabled) log.debug "FILTER DEF ${params}"
    def procdefKeyList = procdefService.allProcessDefinitionKeys()
    def procdefs = procdefService.allProcessDefinitionsIdNameLists()
    def procdefIdList = procdefs.keys
    def procdefNameList = procdefs.values

    [procdefKeyList: procdefKeyList, procdefIdList: procdefIdList, procdefNameList: procdefNameList,
    procdefKeyList: procdefKeyList, selectedProcdef: null, filterId: null]
  }

  /**
   * List process instances using a filter.
   * The filter is persisted to allow paging.
   */
  def filterlist(Integer max) {
    if (log.debugEnabled) log.debug "FILTER LIST ${max}"
    def filterId = params.filterId
    def filter = null
    if (filterId) {
      filter = CrdProcessInstanceFilter.get(filterId)
    } else {
      def session = cookie(name: 'JSESSIONID')
      filter = new CrdProcessInstanceFilter(params)
      filter.session = session
      filter.save()
    }

    def result = pastProcinstService.filterProcessInstances(filter)
    params.max = Math.min(max ?: 10, 100)
    def total = result?.size() ?: 0
    if (log.debugEnabled) log.debug "RESULT ${result} (${total})"
    [pastProcInstList: result, pastProcInstTotal: total, filterId: filter.id]
  }

  def list(Integer max) {
    if (log.debugEnabled) log.debug "LIST PAST PROCINST ${params}"
    params.max = Math.min(max ?: 10, 100)
    [pastProcInstList: PastProcinst.list(params), pastProcInstTotal: PastProcinst.count()]
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

}
