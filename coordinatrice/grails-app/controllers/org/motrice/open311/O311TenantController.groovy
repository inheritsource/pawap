package org.motrice.open311

import org.springframework.dao.DataIntegrityViolationException

class O311TenantController {

  // Injection magic
  def open311Service

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  /**
   * Prefix used for jurisdiction checkboxes.
   */
  final static String CHECKBOX_PREFIX = 'JURISD@'
  final static int CHECKBOX_PREFIX_LEN = CHECKBOX_PREFIX.length()

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    params.max = Math.min(max ?: 15, 100)
    [o311TenantInstList: O311Tenant.list(params), o311TenantInstTotal: O311Tenant.count()]
  }

  /**
   * Show all jurisdictions, allowing the user to include/exclude for this tenant.
   * id must be a tenant id.
   */
  def listjurisd(Long id) {
    def tenantInst = O311Tenant.get(id)
    if (!tenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    def selectionList = open311Service.jurisdictionSelectionList(tenantInst)
    [tenantInst: tenantInst, jurisdictionSelectionList: selectionList, checkboxPrefix: CHECKBOX_PREFIX]
  }

  /**
   * Update the selection of jurisdictions admitting a given tenant.
   */
  def updatejurisd(Long id) {
    if (log.debugEnabled) log.debug "UPDATE JURISD ${params}"
    def tenantInst = O311Tenant.get(id)
    if (!tenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    def includeList = params.inject([]) {list, entry ->
      if (entry.key.startsWith(CHECKBOX_PREFIX) && entry.value == 'on') {
	list << entry.key.substring(CHECKBOX_PREFIX_LEN)
      }

      return list
    }

    open311Service.updateTenantInJurisdictions(tenantInst, includeList)
    redirect(action: 'show', id: id)
  }

  def create() {
    [o311TenantInst: new O311Tenant(params)]
  }

  def save() {
    def o311TenantInst = new O311Tenant(params)
    o311TenantInst.generateApiKey()
    if (!o311TenantInst.save(flush: true)) {
      render(view: "create", model: [o311TenantInst: o311TenantInst])
      return
    }

    flash.message = message(code: 'default.created.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), o311TenantInst.id])
    redirect(action: "show", id: o311TenantInst.id)
  }

  def show(Long id) {
    def o311TenantInst = O311Tenant.get(id)
    if (!o311TenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    [o311TenantInst: o311TenantInst]
  }

  def edit(Long id) {
    def o311TenantInst = O311Tenant.get(id)
    if (!o311TenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    [o311TenantInst: o311TenantInst]
  }

  def update(Long id, Long version) {
    def o311TenantInst = O311Tenant.get(id)
    if (!o311TenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    if (version != null) {
      if (o311TenantInst.version > version) {
	o311TenantInst.errors.rejectValue("version", "default.optimistic.locking.failure",
					  [message(code: 'o311Tenant.label', default: 'O311Tenant')] as Object[],
					  "Another user has updated this O311Tenant while you were editing")
	render(view: "edit", model: [o311TenantInst: o311TenantInst])
	return
      }
    }

    o311TenantInst.properties = params

    if (!o311TenantInst.save(flush: true)) {
      render(view: "edit", model: [o311TenantInst: o311TenantInst])
      return
    }

    flash.message = message(code: 'default.updated.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), o311TenantInst.id])
    redirect(action: "show", id: o311TenantInst.id)
  }

  def delete(Long id) {
    def o311TenantInst = O311Tenant.get(id)
    if (!o311TenantInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
      return
    }

    try {
      o311TenantInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'o311Tenant.label', default: 'O311Tenant'), id])
      redirect(action: "show", id: id)
    }
  }

}
