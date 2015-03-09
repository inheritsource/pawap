package org.motrice.coordinatrice

import org.activiti.engine.history.HistoricProcessInstance
import org.activiti.engine.history.HistoricProcessInstanceQuery
import org.apache.commons.logging.LogFactory

/**
 * Collect data about historic process instances.
 * Also manages filters for querying.
 */
class PastProcinstService {
  // We don't manage these transactions
  static transactional = false

  // Injection magic
  def activitiHistoryService
  def procdefService

  private static final log = LogFactory.getLog(this)

  /**
   * Check if a historic process instance exists with a given id.
   */
  boolean checkPastProcinst(String id) {
    if (log.debugEnabled) log.debug "checkPastProcinst << ${id}"
    def result = null
    if (id) {
      def pi = activitiHistoryService.createHistoricProcessInstanceQuery().
      processInstanceId(id).singleResult()
      result = pi != null
    }
    if (log.debugEnabled) log.debug "checkPastProcinst >> ${result}"
    return result
  }

  /**
   * Retrieve a historic process instance given its id.
   */
  PastProcinst findPastProcinst(String id) {
    if (log.debugEnabled) log.debug "findPastProcinst << ${id}"
    def result = null
    if (id) {
      def pi = activitiHistoryService.createHistoricProcessInstanceQuery().
      processInstanceId(id).singleResult()
      result = pi? createPastProcinst(pi) : null
    }
    if (log.debugEnabled) log.debug "findPastProcinst >> ${result}"
    return result
  }

  /**
   * Create a historic process instance query from a filter.
   * RETURN the result (List of PastProcinst).
   */
  List filterProcessInstances(CrdProcessInstanceFilter filter) {
    if (log.debugEnabled) log.debug "filterProcessInstances << ${filter}"
      def q = activitiHistoryService.createHistoricProcessInstanceQuery()
      q = addQueryConditions(q, filter)
      q = addQueryResultOrder(q, filter)
      if (log.debugEnabled) log.debug "filterProcessInstances query: ${q}"
      def pi = q.list()
      def result = pi.collect {entity ->
	createPastProcinst(entity)
      }
    if (log.debugEnabled) log.debug "filterProcessInstances >> ${result?.size()}"
    return result
  }

  /**
   * Add conditions from a filter to a query.
   */
  HistoricProcessInstanceQuery addQueryConditions(HistoricProcessInstanceQuery query,
						  CrdProcessInstanceFilter filter)
  {
    if (filter.finishedState == 1) {
      query.finished()
    } else if (filter.finishedState == 4) {
      query.unfinished()
    }
    if (filter.startedBeforeFlag) query.startedBefore(filter.startedBefore)
    if (filter.startedAfterFlag) query.startedAfter(filter.startedAfter)
    if (filter.finishedBeforeFlag) query.finishedBefore(filter.finishedBefore)
    if (filter.finishedAfterFlag) query.finishedAfter(filter.finishedAfter)
    if (filter.startedBy) query.startedBy(filter.startedBy)
    if (filter.procdefId) query.processDefinitionId(filter.procdefId)
    if (filter.procdefExcludeKey) {
      def nameList = filter.procdefExcludeKey.split(',').toList()
      query.processDefinitionKeyNotIn(nameList)
    }
    if (filter.variableName) query.variableValueLike(filter.variableName, filter.variablePattern)
    return query
  }

  /**
   * Add "order by" from a filter to a query.
   */
  HistoricProcessInstanceQuery addQueryResultOrder(HistoricProcessInstanceQuery query,
						   CrdProcessInstanceFilter filter)
  {
    def q = query
    switch (filter.orderByProperty) {
    case CrdProcessInstanceFilter.ORDER_BY_START_TIME: q = q.orderByProcessInstanceStartTime()
      break
    case CrdProcessInstanceFilter.ORDER_BY_END_TIME: q = q.orderByProcessInstanceEndTime()
      break
    case CrdProcessInstanceFilter.ORDER_BY_PROC_DEF: q = q.orderByProcessDefinitionId()
      break
    }

    if (filter.orderDirection == CrdProcessInstanceFilter.ORDER_ASCENDING) {
      q = q.asc()
    } else if (filter.orderDirection == CrdProcessInstanceFilter.ORDER_DESCENDING) {
      q = q.desc()
    }

    return q
  }

  private PastProcinst createPastProcinst(HistoricProcessInstance pi) {
    def procDef = procdefService.findProcessDefinition(pi.processDefinitionId)
    def procInst = new PastProcinst(procdef: procDef)
    procInst.assignFromHistoricProcessInstance(pi)
    return procInst
  }

}
