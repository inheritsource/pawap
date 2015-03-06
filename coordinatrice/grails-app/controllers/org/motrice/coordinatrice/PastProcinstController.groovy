package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class PastProcinstController {

  def pastProcinstService

  static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    [pastProcInstList: PastProcinst.list(params), pastProcInstTotal: PastProcinst.count()]
  }

  def show(String id) {
    if (log.debugEnabled) log.debug "SHOW PAST PROCINST ${params}"
    def pastProcInst = pastProcinstService.findPastProcinst(id)
    if (!pastProcInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'pastProcinst.label', default: 'PastProcinst'), id])
      redirect(action: "list")
      return
    }

    [pastProcInst: pastProcInst]
  }

  def delete(String id) {
    def pastProcInst = PastProcinst.get(id)
    if (!pastProcInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'pastProcinst.label', default: 'PastProcinst'), id])
      redirect(action: "list")
      return
    }

    try {
      pastProcInst.delete(flush: true)
      flash.message = message(code: 'default.deleted.message', args: [message(code: 'pastProcinst.label', default: 'PastProcinst'), id])
      redirect(action: "list")
    }
    catch (DataIntegrityViolationException e) {
      flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'pastProcinst.label', default: 'PastProcinst'), id])
      redirect(action: "show", id: id)
    }
  }
}
