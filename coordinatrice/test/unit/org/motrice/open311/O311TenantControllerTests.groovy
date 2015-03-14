package org.motrice.open311



import org.junit.*
import grails.test.mixin.*

@TestFor(O311TenantController)
@Mock(O311Tenant)
class O311TenantControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/o311Tenant/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.o311TenantInstanceList.size() == 0
        assert model.o311TenantInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.o311TenantInstance != null
    }

    void testSave() {
        controller.save()

        assert model.o311TenantInstance != null
        assert view == '/o311Tenant/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/o311Tenant/show/1'
        assert controller.flash.message != null
        assert O311Tenant.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Tenant/list'

        populateValidParams(params)
        def o311Tenant = new O311Tenant(params)

        assert o311Tenant.save() != null

        params.id = o311Tenant.id

        def model = controller.show()

        assert model.o311TenantInstance == o311Tenant
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Tenant/list'

        populateValidParams(params)
        def o311Tenant = new O311Tenant(params)

        assert o311Tenant.save() != null

        params.id = o311Tenant.id

        def model = controller.edit()

        assert model.o311TenantInstance == o311Tenant
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Tenant/list'

        response.reset()

        populateValidParams(params)
        def o311Tenant = new O311Tenant(params)

        assert o311Tenant.save() != null

        // test invalid parameters in update
        params.id = o311Tenant.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/o311Tenant/edit"
        assert model.o311TenantInstance != null

        o311Tenant.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/o311Tenant/show/$o311Tenant.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        o311Tenant.clearErrors()

        populateValidParams(params)
        params.id = o311Tenant.id
        params.version = -1
        controller.update()

        assert view == "/o311Tenant/edit"
        assert model.o311TenantInstance != null
        assert model.o311TenantInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/o311Tenant/list'

        response.reset()

        populateValidParams(params)
        def o311Tenant = new O311Tenant(params)

        assert o311Tenant.save() != null
        assert O311Tenant.count() == 1

        params.id = o311Tenant.id

        controller.delete()

        assert O311Tenant.count() == 0
        assert O311Tenant.get(o311Tenant.id) == null
        assert response.redirectedUrl == '/o311Tenant/list'
    }
}
