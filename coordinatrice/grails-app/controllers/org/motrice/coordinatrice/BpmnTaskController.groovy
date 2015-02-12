package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class BpmnTaskController {

  def processEngineService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    [bpmnTaskInstList: BpmnTask.list(params), bpmnTaskInstTotal: BpmnTask.count()]
  }

  /**
   * List the tasks of a process instance.
   */
  def listproc(String id) {
    if (log.debugEnabled) log.debug "LIST TASKS << ${id} ${params}"
    def procInst = processEngineService.findProcessInstance(id)
    def taskList = processEngineService.findTasksByPi(id)
    [bpmnTaskInstList: taskList, bpmnTaskInstTotal: taskList.size(), procInst: procInst]
  }

  def create() {
    [bpmnTaskInst: new BpmnTask(params)]
  }

  def save() {
    def bpmnTaskInst = new BpmnTask(params)
    if (!bpmnTaskInst.save(flush: true)) {
      render(view: "create", model: [bpmnTaskInst: bpmnTaskInst])
      return
    }

    flash.message = message(code: 'default.created.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), bpmnTaskInst.id])
    redirect(action: "show", id: bpmnTaskInst.id)
  }

  def show(String id) {
    if (log.debugEnabled) log.debug "SHOW TASK ${params}"
    def bpmnTaskInst = processEngineService.findTask(id)
    if (!bpmnTaskInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
      return
    }

    [bpmnTaskInst: bpmnTaskInst]
  }

  def edit(Long id) {
    def bpmnTaskInst = BpmnTask.get(id)
    if (!bpmnTaskInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
      return
    }

    [bpmnTaskInst: bpmnTaskInst]
  }

  def update(Long id, Long version) {
    def bpmnTaskInst = BpmnTask.get(id)
    if (!bpmnTaskInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
      return
    }

    if (version != null) {
      if (bpmnTaskInst.version > version) {
	bpmnTaskInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					[message(code: 'bpmnTask.label', default: 'BpmnTask')] as Object[],
					"Another user has updated this BpmnTask while you were editing")
	render(view: "edit", model: [bpmnTaskInst: bpmnTaskInst])
	return
      }
    }

    bpmnTaskInst.properties = params

    if (!bpmnTaskInst.save(flush: true)) {
      render(view: "edit", model: [bpmnTaskInst: bpmnTaskInst])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), bpmnTaskInst.id])
    redirect(action: "show", id: bpmnTaskInst.id)
  }

  def delete(Long id) {
    def bpmnTaskInst = BpmnTask.get(id)
    if (!bpmnTaskInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
      return
    }

    try {
      bpmnTaskInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "show", id: id)
    }
  }
}
