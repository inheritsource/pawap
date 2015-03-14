package org.motrice.open311



import org.junit.*
import grails.test.mixin.*

@TestFor(O311ServiceController)
@Mock(O311Service)
class O311ServiceControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/o311Service/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.o311ServiceInstanceList.size() == 0
        assert model.o311ServiceInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.o311ServiceInstance != null
    }

    void testSave() {
        controller.save()

        assert model.o311ServiceInstance != null
        assert view == '/o311Service/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/o311Service/show/1'
        assert controller.flash.message != null
        assert O311Service.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Service/list'

        populateValidParams(params)
        def o311Service = new O311Service(params)

        assert o311Service.save() != null

        params.id = o311Service.id

        def model = controller.show()

        assert model.o311ServiceInstance == o311Service
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Service/list'

        populateValidParams(params)
        def o311Service = new O311Service(params)

        assert o311Service.save() != null

        params.id = o311Service.id

        def model = controller.edit()

        assert model.o311ServiceInstance == o311Service
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Service/list'

        response.reset()

        populateValidParams(params)
        def o311Service = new O311Service(params)

        assert o311Service.save() != null

        // test invalid parameters in update
        params.id = o311Service.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/o311Service/edit"
        assert model.o311ServiceInstance != null

        o311Service.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/o311Service/show/$o311Service.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        o311Service.clearErrors()

        populateValidParams(params)
        params.id = o311Service.id
        params.version = -1
        controller.update()

        assert view == "/o311Service/edit"
        assert model.o311ServiceInstance != null
        assert model.o311ServiceInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/o311Service/list'

        response.reset()

        populateValidParams(params)
        def o311Service = new O311Service(params)

        assert o311Service.save() != null
        assert O311Service.count() == 1

        params.id = o311Service.id

        controller.delete()

        assert O311Service.count() == 0
        assert O311Service.get(o311Service.id) == null
        assert response.redirectedUrl == '/o311Service/list'
    }
}
