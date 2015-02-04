package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException
import org.motrice.coordinatrice.pxd.PxdFormdefVer

class CrdFormMapController {

  def formMapService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  /**
   * Create a form map, or edit an existing one.
   */
  def createlist(Long id) {
    if (log.debugEnabled) log.debug "CREATELIST ${params}"
    def formMap = null
    def viewList = null
    try {
      formMap = CrdFormMap.get(id) ?: formMapService.createMapFromForm(id)
      viewList = formMapService.controlListView(formMap)
    } catch (ServiceException exc) {
      handleServiceException('Create CrdFormMap', exc)
      redirect(action: 'list')
      return
    }

    def formDef = PxdFormdefVer.get(id)
    [crdFormMapInst: formMap, crdFormMapList: viewList, formDefTitle: formDef?.path]
  }

  def savelist(Long id, Long version) {
    if (log.debugEnabled) log.debug "SAVELIST ${params}"
    def crdFormMapInst = CrdFormMap.get(id)
    if (!crdFormMapInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), id])
      redirect(action: "list")
      return
    }

    def viewList = null
    if (version != null) {
      if (crdFormMapInst.version > version) {
	crdFormMapInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					  [message(code: 'crdFormMap.label', default: 'CrdFormMap')] as Object[],
					  "Another user has updated this CrdFormMap while you were editing")
	viewList = formMapService.controlListView(formMap)
	render(view: "crelist", model: [crdFormMapInst: crdFormMapInst, crdFormMapList: viewList])
	return
      }
    }

    crdFormMapInst = formMapService.updateVariableList(crdFormMapInst, params)
    if (!crdFormMapInst.save(flush: true)) {
      render(view: "crelist", model: [crdFormMapInst: crdFormMapInst, crdFormMapList: viewList])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), crdFormMapInst.id])
    redirect(action: "show", id: crdFormMapInst.id)
  }

  def show(Long id) {
    def formMap = CrdFormMap.get(id)
    if (!formMap) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), id])
      redirect(action: "list")
      return
    }

    def viewList = null
    try {
      if (!formMap) formMap = formMapService.createMapFromForm(id)
      viewList = formMapService.controlListView(formMap)
    } catch (ServiceException exc) {
      handleServiceException('Create CrdFormMap', exc)
      redirect(action: 'list')
      return
    }

    def formDef = PxdFormdefVer.get(id)
    [crdFormMapInst: formMap, crdFormMapList: viewList, formDefTitle: formDef?.path]
  }

  def delete(Long id) {
    def crdFormMapInst = CrdFormMap.get(id)
    if (!crdFormMapInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), id])
      redirect(controller: 'pxdFormdef', action: 'list')
      return
    }

    try {
      crdFormMapInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), id])
      redirect(controller: 'pxdFormdef', action: 'list')
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'crdFormMap.label', default: 'CrdFormMap'), id])
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
