package org.motrice.open311

import org.springframework.dao.DataIntegrityViolationException

class O311JurisdictionController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [o311JurisdictionInstList: O311Jurisdiction.list(params), o311JurisdictionInstTotal: O311Jurisdiction.count()]
    }

    def create() {
        [o311JurisdictionInst: new O311Jurisdiction(params)]
    }

    def save() {
        def o311JurisdictionInst = new O311Jurisdiction(params)
        if (!o311JurisdictionInst.save(flush: true)) {
            render(view: "create", model: [o311JurisdictionInst: o311JurisdictionInst])
            return
        }

        flash.message = message(code: 'default.created.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), o311JurisdictionInst.id])
        redirect(action: "show", id: o311JurisdictionInst.id)
    }

    def show(Long id) {
        def o311JurisdictionInst = O311Jurisdiction.get(id)
        if (!o311JurisdictionInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "list")
            return
        }

        [o311JurisdictionInst: o311JurisdictionInst]
    }

    def edit(Long id) {
        def o311JurisdictionInst = O311Jurisdiction.get(id)
        if (!o311JurisdictionInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "list")
            return
        }

        [o311JurisdictionInst: o311JurisdictionInst]
    }

    def update(Long id, Long version) {
        def o311JurisdictionInst = O311Jurisdiction.get(id)
        if (!o311JurisdictionInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (o311JurisdictionInst.version > version) {
                o311JurisdictionInst.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction')] as Object[],
                          "Another user has updated this O311Jurisdiction while you were editing")
                render(view: "edit", model: [o311JurisdictionInst: o311JurisdictionInst])
                return
            }
        }

        o311JurisdictionInst.properties = params

        if (!o311JurisdictionInst.save(flush: true)) {
            render(view: "edit", model: [o311JurisdictionInst: o311JurisdictionInst])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), o311JurisdictionInst.id])
        redirect(action: "show", id: o311JurisdictionInst.id)
    }

    def delete(Long id) {
        def o311JurisdictionInst = O311Jurisdiction.get(id)
        if (!o311JurisdictionInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "list")
            return
        }

        try {
            o311JurisdictionInst.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'o311Jurisdiction.label', default: 'O311Jurisdiction'), id])
            redirect(action: "show", id: id)
        }
    }
}
