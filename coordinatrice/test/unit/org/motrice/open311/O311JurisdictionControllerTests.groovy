package org.motrice.open311



import org.junit.*
import grails.test.mixin.*

@TestFor(O311JurisdictionController)
@Mock(O311Jurisdiction)
class O311JurisdictionControllerTests {

    def populateValidParams(params) {
        assert params != null
        // TODO: Populate valid properties like...
        //params["name"] = 'someValidName'
    }

    void testIndex() {
        controller.index()
        assert "/o311Jurisdiction/list" == response.redirectedUrl
    }

    void testList() {

        def model = controller.list()

        assert model.o311JurisdictionInstanceList.size() == 0
        assert model.o311JurisdictionInstanceTotal == 0
    }

    void testCreate() {
        def model = controller.create()

        assert model.o311JurisdictionInstance != null
    }

    void testSave() {
        controller.save()

        assert model.o311JurisdictionInstance != null
        assert view == '/o311Jurisdiction/create'

        response.reset()

        populateValidParams(params)
        controller.save()

        assert response.redirectedUrl == '/o311Jurisdiction/show/1'
        assert controller.flash.message != null
        assert O311Jurisdiction.count() == 1
    }

    void testShow() {
        controller.show()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Jurisdiction/list'

        populateValidParams(params)
        def o311Jurisdiction = new O311Jurisdiction(params)

        assert o311Jurisdiction.save() != null

        params.id = o311Jurisdiction.id

        def model = controller.show()

        assert model.o311JurisdictionInstance == o311Jurisdiction
    }

    void testEdit() {
        controller.edit()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Jurisdiction/list'

        populateValidParams(params)
        def o311Jurisdiction = new O311Jurisdiction(params)

        assert o311Jurisdiction.save() != null

        params.id = o311Jurisdiction.id

        def model = controller.edit()

        assert model.o311JurisdictionInstance == o311Jurisdiction
    }

    void testUpdate() {
        controller.update()

        assert flash.message != null
        assert response.redirectedUrl == '/o311Jurisdiction/list'

        response.reset()

        populateValidParams(params)
        def o311Jurisdiction = new O311Jurisdiction(params)

        assert o311Jurisdiction.save() != null

        // test invalid parameters in update
        params.id = o311Jurisdiction.id
        //TODO: add invalid values to params object

        controller.update()

        assert view == "/o311Jurisdiction/edit"
        assert model.o311JurisdictionInstance != null

        o311Jurisdiction.clearErrors()

        populateValidParams(params)
        controller.update()

        assert response.redirectedUrl == "/o311Jurisdiction/show/$o311Jurisdiction.id"
        assert flash.message != null

        //test outdated version number
        response.reset()
        o311Jurisdiction.clearErrors()

        populateValidParams(params)
        params.id = o311Jurisdiction.id
        params.version = -1
        controller.update()

        assert view == "/o311Jurisdiction/edit"
        assert model.o311JurisdictionInstance != null
        assert model.o311JurisdictionInstance.errors.getFieldError('version')
        assert flash.message != null
    }

    void testDelete() {
        controller.delete()
        assert flash.message != null
        assert response.redirectedUrl == '/o311Jurisdiction/list'

        response.reset()

        populateValidParams(params)
        def o311Jurisdiction = new O311Jurisdiction(params)

        assert o311Jurisdiction.save() != null
        assert O311Jurisdiction.count() == 1

        params.id = o311Jurisdiction.id

        controller.delete()

        assert O311Jurisdiction.count() == 0
        assert O311Jurisdiction.get(o311Jurisdiction.id) == null
        assert response.redirectedUrl == '/o311Jurisdiction/list'
    }
}
