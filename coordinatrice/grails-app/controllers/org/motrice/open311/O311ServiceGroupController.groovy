package org.motrice.open311

import org.springframework.dao.DataIntegrityViolationException

class O311ServiceGroupController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [o311ServiceGroupInstList: O311ServiceGroup.list(params), o311ServiceGroupInstTotal: O311ServiceGroup.count()]
    }

    def create() {
        [o311ServiceGroupInst: new O311ServiceGroup(params)]
    }

    def save() {
        def o311ServiceGroupInst = new O311ServiceGroup(params)
        if (!o311ServiceGroupInst.save(flush: true)) {
            render(view: "create", model: [o311ServiceGroupInst: o311ServiceGroupInst])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), o311ServiceGroupInst.id])
        redirect(action: "show", id: o311ServiceGroupInst.id)
    }

    def show(Long id) {
        def o311ServiceGroupInst = O311ServiceGroup.get(id)
        if (!o311ServiceGroupInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "list")
            return
        }

        [o311ServiceGroupInst: o311ServiceGroupInst]
    }

    def edit(Long id) {
        def o311ServiceGroupInst = O311ServiceGroup.get(id)
        if (!o311ServiceGroupInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "list")
            return
        }

        [o311ServiceGroupInst: o311ServiceGroupInst]
    }

    def update(Long id, Long version) {
        def o311ServiceGroupInst = O311ServiceGroup.get(id)
        if (!o311ServiceGroupInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (o311ServiceGroupInst.version > version) {
                o311ServiceGroupInst.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup')] as Object[],
                          "Another user has updated this O311ServiceGroup while you were editing")
                render(view: "edit", model: [o311ServiceGroupInst: o311ServiceGroupInst])
                return
            }
        }

        o311ServiceGroupInst.properties = params

        if (!o311ServiceGroupInst.save(flush: true)) {
            render(view: "edit", model: [o311ServiceGroupInst: o311ServiceGroupInst])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), o311ServiceGroupInst.id])
        redirect(action: "show", id: o311ServiceGroupInst.id)
    }

    def delete(Long id) {
        def o311ServiceGroupInst = O311ServiceGroup.get(id)
        if (!o311ServiceGroupInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "list")
            return
        }

        try {
            o311ServiceGroupInst.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'o311ServiceGroup.label', default: 'O311ServiceGroup'), id])
            redirect(action: "show", id: id)
        }
    }
}
