package org.motrice.open311

import org.springframework.dao.DataIntegrityViolationException

class O311ServiceGroupController {

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  /**
   * Normally has an 'id' parameter identifying a jurisdiction.
   * In such case the list is limited to service groups for that jurisdiction.
   */
  def list(Integer max) {
    if (log.debugEnabled) log.debug "LIST ${params}"
    params.max = Math.min(max ?: 15, 100)

    def list = []
    def count = 0
    def jurisdictionId = params.id
    def jurisdictionInst = jurisdictionId? O311Jurisdiction.get(jurisdictionId) : null

    // Silently ignore the jurisdiction if the id returns null
    if (jurisdictionInst) {
      def cr = O311ServiceGroup.createCriteria()
      list = cr.list(params) {
	jurisdiction {
	  idEq(jurisdictionInst.id)
	}
      }
      cr = O311ServiceGroup.createCriteria()
      count = cr.count() {
	jurisdiction {
	  eq('id', jurisdictionInst.id)
	}
      }
    } else {
      list = O311ServiceGroup.list(params)
      count = O311ServiceGroup.count()
    }

    [serviceGroupInstList: list, serviceGroupInstTotal: count, jurisdictionInst: jurisdictionInst]
  }

  /**
   * Must have an 'id' parameter identifying a jurisdiction.
   */
  def create(Long id) {
    def jurisdictionInst = id? O311Jurisdiction.get(id) : null
    if (!jurisdictionInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
      redirect(controller: 'o311Jurisdiction', action: 'list')
      return
    }

    [serviceGroupInst: new O311ServiceGroup(params), jurisdictionInst: jurisdictionInst]
  }

  def save() {
    def serviceGroupInst = new O311ServiceGroup(params)
    if (!serviceGroupInst.save(flush: true)) {
      render(view: "create", model: [serviceGroupInst: serviceGroupInst])
      return
    }

    flash.message = message(code: 'default.created.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), serviceGroupInst.id])
    redirect(action: "show", id: serviceGroupInst.id)
  }

  def show(Long id) {
    def serviceGroupInst = O311ServiceGroup.get(id)
    if (!serviceGroupInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: "list")
      return
    }

    [serviceGroupInst: serviceGroupInst, serviceList: serviceGroupInst.services]
  }

  def edit(Long id) {
    def serviceGroupInst = O311ServiceGroup.get(id)
    if (!serviceGroupInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: "list")
      return
    }

    [serviceGroupInst: serviceGroupInst]
  }

  def update(Long id, Long version) {
    def serviceGroupInst = O311ServiceGroup.get(id)
    if (!serviceGroupInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: "list")
      return
    }

    if (version != null) {
      if (serviceGroupInst.version > version) {
	serviceGroupInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					    [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')] as Object[],
					    "Another user has updated this O311ServiceGroup while you were editing")
	render(view: "edit", model: [serviceGroupInst: serviceGroupInst])
	return
      }
    }

    serviceGroupInst.properties = params

    if (!serviceGroupInst.save(flush: true)) {
      render(view: "edit", model: [serviceGroupInst: serviceGroupInst])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), serviceGroupInst.id])
    redirect(action: "show", id: serviceGroupInst.id)
  }

  def delete(Long id) {
    def serviceGroupInst = O311ServiceGroup.get(id)
    if (!serviceGroupInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: "list")
      return
    }

    def jurisdictionId = serviceGroupInst?.jurisdiction?.id

    try {
      serviceGroupInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: 'list', id: jurisdictionId)
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
      redirect(action: "show", id: id)
    }
  }
}
