package org.motrice.coordinatrice

import org.springframework.transaction.annotation.Transactional

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
   * Retrieve the process variables of a historic process instance.
   */
  PastProcinst findPastProcinstWithVars(String id) {
    if (log.debugEnabled) log.debug "findPastProcinstWithVars << ${id}"
    def result = null
    if (id) {
      def pi = activitiHistoryService.createHistoricProcessInstanceQuery().
      includeProcessVariables().processInstanceId(id).singleResult()
      result = pi? createPastProcinst(pi) : null
    }
    if (log.debugEnabled) log.debug "findPastProcinstWithVars >> ${result}"
    return result
  }

  /**
   * Use action parameters to look up or create a process instance filter.
   */
  @Transactional
  CrdProcessInstanceFilter lookupOrCreateFilter(params, String sessionId) {
    boolean filterIsNew = false
    def filterId = params.filterId
    def filter = null
    if (filterId) {
      filter = CrdProcessInstanceFilter.get(filterId)
      // Update the filter from params
      filter.properties = params
    } else {
      filter = new CrdProcessInstanceFilter(params)
      // Add session id
      filter.session = sessionId
      filterIsNew = true
    }

    if (!filter.save(flush: true)) {
      log.error "filter save: ${filter.errors.allErrors.join(', ')}"
    }

    def newFlag = filterIsNew? '(NEW)' : ''
    if (log.debugEnabled) log.debug "lookupOrCreateFilter >> ${newFlag} ${filter}"
    return filter
  }

  /**
   * Create a historic process instance query from a filter.
   * RETURN the result.
   * NOTE: Returns a list of Activiti entities.
   * Use populateProcessInstances to convert to List of PastProcinst.
   */
  List filterProcessInstancesUnpopulated(CrdProcessInstanceFilter filter) {
    if (log.debugEnabled) log.debug "filterProcessInstances << ${filter}"
      def q = activitiHistoryService.createHistoricProcessInstanceQuery()
      q = addQueryConditions(q, filter)
      q = addQueryResultOrder(q, filter)
      def pi = q.list()
    if (log.debugEnabled) log.debug "filterProcessInstances >> ${pi?.size()}"
    return pi
  }

  /**
   * Populate a list of process instances.
   * unpopulatedProcInstList must be a List of HistoricProcessInstance.
   * RETURN List of PastProcinst.
   */
  List populateProcessInstances(List unpopulatedProcInstList) {
    if (log.debugEnabled) log.debug "populateProcessInstances << ${unpopulatedProcInstList?.size()}"
    def result = unpopulatedProcInstList.collect {createPastProcinst(it)}
    if (log.debugEnabled) log.debug "populateProcessInstances >> ${result?.size()}"
    return result
  }

  /**
   * Create a summary from historic process instances given a filter.
   * RETURN List of PastProcinst where transient fields are used.
   * No paging in this case.
   */
  List filteredProcessInstanceSummary(CrdProcessInstanceFilter filter) {
    if (log.debugEnabled) log.debug "filteredProcessInstanceSummary << ${filter}"
    def entityList = filterProcessInstancesUnpopulated(filter)
    // Iterate over all filtered instances, collect numbers.
    def summaryMap = entityList.inject([:]) {map, pi ->
      def entry = map[pi.processDefinitionId]
      if (!entry) {
	def procInst = createPastProcinst(pi)
	entry = [proc: procInst, finishedCount: 0, unfinishedCount: 0, duration: 0L]
	map[pi.processDefinitionId] = entry
      }
      if (pi.endTime) {
	++entry.finishedCount
	entry.duration += pi.durationInMillis
      } else {
	++entry.unfinishedCount
      }
      return map
    }

    def resultMap = new TreeMap()
    summaryMap.each {entry ->
      def proc = entry.value.proc
      def finishedCount = entry.value.finishedCount
      proc.finishedCount = finishedCount
      proc.startTime = new Date(0L)
      proc.endTime = new Date((entry.value.duration as Long)/(finishedCount as Long) as Long)
      proc.unfinishedCount = entry.value.unfinishedCount
      resultMap[proc.procdef.toString()] = proc
    }
    def result = resultMap.values() as List
    if (log.debugEnabled) log.debug "filteredProcessInstanceSummary >> ${result}"
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
    if (filter.procdefExcludeKey) query.processDefinitionKeyNotIn(filter.excludeKeys)
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

  final static String LAST_FILTER_Q =
    'from CrdProcessInstanceFilter f where f.session=? order by dateCreated desc'

  /**
   * Find the last process instance filter for a session.
   * Return a filter or null (for a new session).
   */
  @Transactional
  CrdProcessInstanceFilter findLastFilter(String sessionId) {
    if (log.debugEnabled) log.debug "findLastFilter << ${sessionId}"
    def result = CrdProcessInstanceFilter.find(LAST_FILTER_Q, [sessionId])
    if (log.debugEnabled) log.debug "findLastFilter >> ${result}"
    return result
  }

  private PastProcinst createPastProcinst(HistoricProcessInstance pi) {
    def procDef = procdefService.findProcessDefinition(pi.processDefinitionId)
    def procInst = new PastProcinst(procdef: procDef)
    procInst.assignFromHistoricProcessInstance(pi)
    return procInst
  }

}
