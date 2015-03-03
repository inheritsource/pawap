package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class BpmnExecutionController {

  def processEngineService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    [bpmnExecInstList: BpmnExecution.list(params), bpmnExecInstTotal: BpmnExecution.count()]
  }

  /**
   * List the executions of a process instance.
   */
  def listproc(String id) {
    if (log.debugEnabled) log.debug "LIST EXECUTIONS << ${id} ${params}"
    def procInst = processEngineService.findProcessInstance(id)
    def execList = processEngineService.findExecutionsByPi(id)
    [bpmnExecInstList: execList, bpmnExecInstTotal: execList.size(), procInst: procInst]
  }

  def show(String id) {
    if (log.debugEnabled) log.debug "SHOW EXECUTION ${params}"
    def bpmnExecInst = processEngineService.findExecution(id)
    if (!bpmnExecInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
      return
    }

    [bpmnExecInst: bpmnExecInst]
  }

  def signal(String id) {
    if (log.debugEnabled) log.debug "SIGNAL EXECUTION ${params}"
    def bpmnExecInst = processEngineService.findExecution(id)
    if (!bpmnExecInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
      return
    }

    def pi = bpmnExecInst.processInstanceId
    if (log.debugEnabled) log.debug "signal execution PI = ${pi}"
    try {
      pi = processEngineService.signalExecution(bpmnExecInst)
      flash.message = message(code: 'bpmnExecution.signal.msg', args: [id])
    } catch (ServiceException exc) {
      handleServiceException('Signal Execution', exc)
    }

    if (log.debugEnabled) log.debug "signal execution redirect id = ${pi}"
    redirect(controller: 'procinst', action: 'show', id: pi)
  }

  def edit(Long id) {
    def bpmnExecInst = BpmnExecution.get(id)
    if (!bpmnExecInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
      return
    }

    [bpmnExecInst: bpmnExecInst]
  }

  def update(Long id, Long version) {
    def bpmnExecInst = BpmnExecution.get(id)
    if (!bpmnExecInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
      return
    }

    if (version != null) {
      if (bpmnExecInst.version > version) {
	bpmnExecInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					     [message(code: 'bpmnExecution.label', default: 'BpmnExecution')] as Object[],
					     "Another user has updated this BpmnExecution while you were editing")
	render(view: "edit", model: [bpmnExecInst: bpmnExecInst])
	return
      }
    }

    bpmnExecInst.properties = params

    if (!bpmnExecInst.save(flush: true)) {
      render(view: "edit", model: [bpmnExecInst: bpmnExecInst])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), bpmnExecInst.id])
    redirect(action: "show", id: bpmnExecInst.id)
  }

  def delete(Long id) {
    def bpmnExecInst = BpmnExecution.get(id)
    if (!bpmnExecInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
      return
    }

    try {
      bpmnExecInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bpmnExecution.label', default: 'BpmnExecution'), id])
      redirect(action: "show", id: id)
    }
  }

  private handleServiceException(String op, ServiceException exc) {
    log.error "${op} ${exc?.message}"
    if (exc.key) {
      flash.message = message(code: exc.key, args: exc.args ?: [])
    } else {
      flash.message = exc.message
    }
  }

}
