package org.motrice.coordinatrice

import org.activiti.engine.history.HistoricProcessInstance
import org.apache.commons.logging.LogFactory

/**
 * Collect data about historic process instances.
 */
class PastProcinstService {
  // We don't manage these transactions
  static transactional = false

  // Injection magic
  def activitiHistoryService
  def procdefService

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

  private PastProcinst createPastProcinst(HistoricProcessInstance pi) {
    def procDef = procdefService.findProcessDefinition(pi.processDefinitionId)
    def procInst = new PastProcinst(procdef: procDef)
    procInst.assignFromHistoricProcessInstance(pi)
    return procInst
  }

}
