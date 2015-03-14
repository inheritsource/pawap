package org.motrice.open311



import org.junit.*
import grails.test.mixin.*

@TestFor(O311ServiceGroupController)
@Mock(O311ServiceGroup)
class O311ServiceGroupControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/o311ServiceGroup/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.o311ServiceGroupInstanceList.size() == 0
        assert model.o311ServiceGroupInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.o311ServiceGroupInstance != null
    }

    void testSave() {
        controller.save()

        assert model.o311ServiceGroupInstance != null
        assert view == '/o311ServiceGroup/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/o311ServiceGroup/show/1'
        assert controller.flash.message != null
        assert O311ServiceGroup.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/o311ServiceGroup/list'

        populateValidParams(params)
        def o311ServiceGroup = new O311ServiceGroup(params)

        assert o311ServiceGroup.save() != null

        params.id = o311ServiceGroup.id

        def model = controller.show()

        assert model.o311ServiceGroupInstance == o311ServiceGroup
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/o311ServiceGroup/list'

        populateValidParams(params)
        def o311ServiceGroup = new O311ServiceGroup(params)

        assert o311ServiceGroup.save() != null

        params.id = o311ServiceGroup.id

        def model = controller.edit()

        assert model.o311ServiceGroupInstance == o311ServiceGroup
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/o311ServiceGroup/list'

        response.reset()

        populateValidParams(params)
        def o311ServiceGroup = new O311ServiceGroup(params)

        assert o311ServiceGroup.save() != null

        // test invalid parameters in update
        params.id = o311ServiceGroup.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/o311ServiceGroup/edit"
        assert model.o311ServiceGroupInstance != null

        o311ServiceGroup.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/o311ServiceGroup/show/$o311ServiceGroup.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        o311ServiceGroup.clearErrors()

        populateValidParams(params)
        params.id = o311ServiceGroup.id
        params.version = -1
        controller.update()

        assert view == "/o311ServiceGroup/edit"
        assert model.o311ServiceGroupInstance != null
        assert model.o311ServiceGroupInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/o311ServiceGroup/list'

        response.reset()

        populateValidParams(params)
        def o311ServiceGroup = new O311ServiceGroup(params)

        assert o311ServiceGroup.save() != null
        assert O311ServiceGroup.count() == 1

        params.id = o311ServiceGroup.id

        controller.delete()

        assert O311ServiceGroup.count() == 0
        assert O311ServiceGroup.get(o311ServiceGroup.id) == null
        assert response.redirectedUrl == '/o311ServiceGroup/list'
    }
}
