package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException
import org.motrice.coordinatrice.pxd.PxdFormdefVer

class ProcinstController {

  def formMapService
  def processEngineService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    def procInstList = processEngineService.listProcessInstances()
    params.max = Math.min(max ?: 15, 100)
    [procInstList: procInstList, procInstTotal: procInstList.size()]
  }

  /**
   * List instances of a given process definition.
   */
  def listprocdef(String id) {
    def procInstList = processEngineService.listProcessInstances(id)
    render(view: 'list', model: [procInstList: procInstList, procInstTotal: procInstList.size()])
  }

  /**
   * Collect data for starting a process instance.
   * Identify process and start form definitions.
   * id must identify the start form definition (MtfStartFormDefinition)
   */
  def create(Long id) {
    if (log.debugEnabled) log.debug "CREATE INSTANCE ${params}"
    def procinst = null
    try {
      procinst = formMapService.processInstancePrepare(id)
    } catch (ServiceException exc) {
      handleServiceException('Create Process Instance', exc)
      redirect(controller: 'procdef', action: 'list')
      return
    }

    [procInst: procinst]
  }

  /**
   * Create a process instance.
   */
  def createinstance() {
    if (log.debugEnabled) log.debug "LAUNCH INSTANCE ${params}"
    def procInst = null
    try {
      procInst = formMapService.processInstancePrepare(params.formConnection, params.formDataPath,
						       params.startFormAssignee)
    } catch (ServiceException exc) {
      handleServiceException('Prepare Process Instance', exc)
      redirect(controller: 'procdef', action: 'list')
      return
    }

    if (!procInst?.forminst) {
      flash.message = message(code: 'procinst.form.data.not.found', args: [params.formDataPath])
      redirect(controller: 'procdef', action: 'list')
      return
    }

    // Process variable map.
    def vmap = null
    try {
      vmap = formMapService.createVariableMap(procInst.formdef.id, procInst.forminst)
    } catch (ServiceException exc) {
      handleServiceException('Create Process Instance', exc)
      redirect(controller: 'procdef', action: 'list')
      return
    }

    // Add mandatory entries.
    vmap.motriceStartFormAssignee = procInst.assignee
    vmap.motriceStartFormTypeId = procInst.msfd?.formHandlerId
    vmap.motriceStartFormDefinitionKey = procInst.msfd?.formConnectionKey
    // Process kick off
    procInst.variables = vmap
    procInst = processEngineService.startProcessInstance(procInst)

    flash.message = message(code: 'procinst.created', args: [procInst.processInstanceId])
    redirect(controller: 'procinst', action: 'listprocdef', id: procInst?.procdef?.uuid)
  }

  def show(String id) {
    if (log.debugEnabled) log.debug "SHOW PROCINST ${params}"
    def procInst = processEngineService.findProcessInstance(id)
    if (!procInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'procinst.label'), id])
      redirect(action: "list")
      return
    }

    [procInst: procInst]
  }

  def edit(Long id) {
    def procInst = Procinst.get(id)
    if (!procInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'procinst.label'), id])
      redirect(action: "list")
      return
    }

    [procInst: procInst]
  }

  def update(Long id, Long version) {
    def procInst = Procinst.get(id)
    if (!procInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'procinst.label'), id])
      redirect(action: "list")
      return
    }

    if (version != null) {
      if (procInst.version > version) {
	procInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					[message(code: 'procinst.label')] as Object[],
					"Another user has updated this Procinst while you were editing")
	render(view: "edit", model: [procInst: procInst])
	return
      }
    }

    procInst.properties = params

    if (!procInst.save(flush: true)) {
      render(view: "edit", model: [procInst: procInst])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'procinst.label'), procInst.id])
    redirect(action: "show", id: procInst.id)
  }

  def delete(Long id) {
    def procInst = Procinst.get(id)
    if (!procInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'procinst.label'), id])
      redirect(action: "list")
      return
    }

    try {
      procInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'procinst.label'), id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'procinst.label'), id])
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
