package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException
import org.motrice.coordinatrice.pxd.PxdFormdefVer

class BpmnTaskController {

  def formMapService
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

  /**
   * Complete (= finish) a task forcefully.
   * First step is to collect input.
   */
  def complete(String id) {
    if (log.debugEnabled) log.debug "COMPLETE TASK ${params}"
    def taskInst = null
    try {
      taskInst = processEngineService.taskCompletePrepare(id)
    } catch (ServiceException exc) {
      handleServiceException('Complete Task Prepare', exc)
      redirect(controller: 'procinst', action: 'list')
      return
    }

    [bpmnTaskInst: taskInst]
  }

  /**
   * Complete (= finish) a task forcefully.
   */
  def completeAction(BpmnTaskCompleteCommand cmd) {
    if (log.debugEnabled) log.debug "COMPLETE TASK ACTION ${cmd} ${params}"
    def procInstId = null
    try {
      procInstId = processEngineService.taskCompleteFinish(cmd)
    } catch (ServiceException exc) {
      handleServiceException('Complete Task Action', exc)
      redirect(controller: 'procinst', action: 'list')
      return
    }

    redirect(controller: 'procinst', action: 'show', id: procInstId)
  }

  def edit(String id) {
    def bpmnTaskInst = processEngineService.findTask(id)
    if (!bpmnTaskInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), id])
      redirect(action: "list")
      return
    }

    [bpmnTaskInst: bpmnTaskInst]
  }

  def update(BpmnTaskCommand cmd) {
    if (log.debugEnabled) log.debug "UPDATE TASK << ${cmd} ${params}"
    def bpmnTaskInst = null
    try {
      bpmnTaskInst = processEngineService.updateTask(cmd)
    } catch (ServiceException exc) {
      handleServiceException('Update Task', exc)
      redirect(controller: 'procinst', action: 'list')
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'bpmnTask.label', default: 'BpmnTask'), bpmnTaskInst.uuid])
    redirect(action: "show", id: bpmnTaskInst.uuid)
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

  private handleServiceException(String op, ServiceException exc) {
    log.error "${op} ${exc?.message}"
    if (exc.key) {
      flash.message = message(code: exc.key, args: exc.args ?: [])
    } else {
      flash.message = exc.message
    }
  }

}

class BpmnTaskCommand {
  String id
  String assignee
  Date dueTime
  String description
  Integer priority
  String comment

  String toString() {
    "[TaskUpdateCommand(${id}): '${assignee}','${dueTime}','${description}','${priority}',${comment?.size()}]"
  }
}

/**
 * Turns out not all fields are used.
 */
class BpmnTaskCompleteCommand {
  String id
  Long formConnection
  Long formDef
  String formDataPath
  String commentText

  String toString() {
    "[TaskCompleteCommand(${id}): '${formConnection}','${formDef}','${formDataPath}',${commentText?.size()}]"
  }
}
