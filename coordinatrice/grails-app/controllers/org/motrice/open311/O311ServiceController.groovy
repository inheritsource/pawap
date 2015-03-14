package org.motrice.open311

import org.springframework.dao.DataIntegrityViolationException

class O311ServiceController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [o311ServiceInstList: O311Service.list(params), o311ServiceInstTotal: O311Service.count()]
    }

    def create() {
        [o311ServiceInst: new O311Service(params)]
    }

    def save() {
        def o311ServiceInst = new O311Service(params)
        if (!o311ServiceInst.save(flush: true)) {
            render(view: "create", model: [o311ServiceInst: o311ServiceInst])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'o311Service.label', default: 'O311Service'), o311ServiceInst.id])
        redirect(action: "show", id: o311ServiceInst.id)
    }

    def show(Long id) {
        def o311ServiceInst = O311Service.get(id)
        if (!o311ServiceInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "list")
            return
        }

        [o311ServiceInst: o311ServiceInst]
    }

    def edit(Long id) {
        def o311ServiceInst = O311Service.get(id)
        if (!o311ServiceInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "list")
            return
        }

        [o311ServiceInst: o311ServiceInst]
    }

    def update(Long id, Long version) {
        def o311ServiceInst = O311Service.get(id)
        if (!o311ServiceInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (o311ServiceInst.version > version) {
                o311ServiceInst.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'o311Service.label', default: 'O311Service')] as Object[],
                          "Another user has updated this O311Service while you were editing")
                render(view: "edit", model: [o311ServiceInst: o311ServiceInst])
                return
            }
        }

        o311ServiceInst.properties = params

        if (!o311ServiceInst.save(flush: true)) {
            render(view: "edit", model: [o311ServiceInst: o311ServiceInst])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'o311Service.label', default: 'O311Service'), o311ServiceInst.id])
        redirect(action: "show", id: o311ServiceInst.id)
    }

    def delete(Long id) {
        def o311ServiceInst = O311Service.get(id)
        if (!o311ServiceInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "list")
            return
        }

        try {
            o311ServiceInst.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'o311Service.label', default: 'O311Service'), id])
            redirect(action: "show", id: id)
        }
    }
}
