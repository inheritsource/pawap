package org.motrice.open311

import org.apache.commons.logging.LogFactory
import org.motrice.coordinatrice.ServiceException

/**
 * Business logic for the Open311 domains.
 */
class Open311Service {
  // Also default.
  static transactional = true

  private static final log = LogFactory.getLog(this)

  /**
   * Assign a BPMN process to a jurisdiction.
   */
  def assignBpmnProcess(O311Jurisdiction jurisd, String procdefUuid) {
    jurisd.procdefUuid = procdefUuid
    // A jurisdiction cannot be enabled unless there is a service notice
    // and a process definition.
    if (!jurisd.serviceNotice || !jurisd.procdefUuid) jurisd.enabledFlag = false
  }

  /**
   * Define a number of Open311 services.
   * The services must be defined by an XML structure.
   */
  def defineServices(String serviceXml) {
    if (log.debugEnabled) log.debug "defineServices << size ${serviceXml?.size()}"
    def serviceCount = 0
    try {
      serviceCount = doDefineServices(serviceXml)
    } catch (Exception exc) {
      def msg = exc.message
      throw new ServiceException("Problem defining services: ${msg}", 'OPEN311.101')
    }

    if (log.debugEnabled) log.debug "defineServices >> ${serviceCount}"
    return serviceCount
  }

  /**
   * Define services without exception handling.
   * RETURN the number of services defined.
   */
  private int doDefineServices(String xml) {
    def services = new XmlSlurper().parseText(xml)
    def count = 0
    services.'*'.each {service ->
      String code = service.service_code.text()?.trim()
      String name = service.service_name.text()?.trim()
      String descr = service.description.text()?.trim()
      if (log.debugEnabled) log.debug "code='${code}',name='${name}',descr='${descr}'"
      new O311Service(code: code, name: name, description: descr).save(failOnError: true)
      ++count
    }

    return count
  }

  /**
   * Retrieve all Open311 services.
   * Encode as XML.
   */
  String retrieveServices() {
    if (log.debugEnabled) log.debug "retrieveServices <<"
    def serviceList = O311Service.listOrderByName()
    if (log.debugEnabled) log.debug "retrieveServices service count: ${serviceList?.size()}"
    def sw = new StringWriter()
    def xml = new groovy.xml.MarkupBuilder(sw)
    xml.services() {
      serviceList.each {svc ->
	service() {
	  service_code(svc.code) {}
	  service_name(svc.name) {}
	  description(svc.description) {}
	}
      }
    }

    sw.flush()
    def result = sw.toString()
    if (log.debugEnabled) log.debug "retrieveServices >> (${result?.size()})"
    return result
  }

  def List jurisdictionSelectionList(O311Tenant tenant) {
    if (log.debugEnabled) log.debug "jurisdictionSelectionList << ${tenant}"
    def connectList = O311TenantInJurisd.allByTenant(tenant.id)
    if (log.debugEnabled) log.debug "jurisdictionSelectionList connectList: ${connectList}"
    def connectMap = connectList.inject([:]) {map, tenantInJurisd ->
      map[tenantInJurisd.jurisdiction.id] = tenantInJurisd
      return map
    }
    if (log.debugEnabled) log.debug "jurisdictionSelectionList connectMap: ${connectMap}"

    def result = []
    O311Jurisdiction.list().each {jurisd ->
      def connection = connectMap[jurisd.id]
      boolean allowedFlag = connection != null
      result << [jurisdiction: jurisd, allowed: allowedFlag]
    }

    if (log.debugEnabled) log.debug "jurisdictionSelectionList >> ${result}"
    return result
  }

  /**
   * Update the set of jurisdictions admitting a giving tenant.
   * All previous connections are deleted.
   * New connections are created from scratch.
   * includeList must be a list of jurisdiction ids.
   */
  def updateTenantInJurisdictions(O311Tenant tenant, List includeList) {
    if (log.debugEnabled) log.debug "updateTenantInJurisdictions << ${tenant}, ${includeList}"
    // Delete all existing connections for this tenant.
    O311TenantInJurisd.executeUpdate('delete O311TenantInJurisd x where x.tenant=?', [tenant])

    // Create a new connection object for each selected jurisdiction.
    def createCount = 0
    includeList.each {jurisdId ->
      def jurisdiction = O311Jurisdiction.get(jurisdId)
      def tenantInJurisd = new O311TenantInJurisd()
      tenantInJurisd.assignId(tenant, jurisdiction)
      ++createCount
      tenant.addToJurisdCnx(tenantInJurisd)
      jurisdiction.addToTenantCnx(tenantInJurisd)
      tenantInJurisd.save()
    }

    if (log.debugEnabled) log.debug "updateTenantInJurisdictions >> ${createCount}"
    return createCount
  }

  /**
   * Generate a service selection list to be presented to the user.
   * Each entry corresponds to an Open311 service.
   */
  def List serviceSelectionList(O311Jurisdiction jurisdiction) {
    if (log.debugEnabled) log.debug "serviceSelectionList << ${jurisdiction}"
    def serviceGroupMembers = O311ServiceInJurisd.allByJurisdiction(jurisdiction.id)
    def serviceGroupMap = serviceGroupMembers.inject([:]) {map, servInJurisd ->
      map[servInJurisd.service.id] = servInJurisd
      return map
    }

    def result = []
    O311Service.list().each {service ->
      def serviceGroupMember = serviceGroupMap[service.id]
      boolean includedFlag = serviceGroupMember != null
      def serviceGroup = null
      if (includedFlag && serviceGroupMember.serviceGroup) {
	serviceGroup = serviceGroupMember.serviceGroup
      }

      result << [service: service, included: includedFlag, serviceGroup: serviceGroup]
    }

    if (log.debugEnabled) log.debug "serviceSelectionList >> ${result?.size()}"
    return result
  }

  /**
   * Reconnects a jurisdiction to services and service groups based on user input.
   * Initially all old connections are deleted.
   * jurisd must be the jurisdiction to update,
   * includeList must be a list of services to include in the jurisdiction,
   * serviceGroupList must be a list of maps.
   * The maps in serviceGroupList have two entries: 'service' (the service id)
   * and 'group' (the service group id).
   * All ids in the input are String.
   * Return the number of created connection objects.
   */
  def updateJurisdictionServices(O311Jurisdiction jurisd, List includeList, List serviceGroupList) {
    if (log.debugEnabled) log.debug "updateJurisdictionServices << ${jurisd}, ${includeList}, ${serviceGroupList}"
    // Create a map that connects services to service groups.
    def serviceMap = serviceGroupList.inject([:]) {map, entry ->
      map[entry.service] = entry.group
      return map
    }
    // Delete all service connections.
    O311ServiceInJurisd.executeUpdate('delete O311ServiceInJurisd x where x.jurisdiction=?', [jurisd])

    // Create a new connection object for each selected service.
    def createCount = 0
    includeList.each {serviceId ->
      def service = O311Service.get(serviceId)
      def groupId = serviceMap[serviceId]
      def serviceGroup = groupId? O311ServiceGroup.get(groupId) : null
      def serviceInJurisd = new O311ServiceInJurisd(serviceGroup: serviceGroup)
      ++createCount
      service.addToJurisdCnx(serviceInJurisd)
      jurisd.addToServiceCnx(serviceInJurisd)
      serviceInJurisd.save()
    }

    if (log.debugEnabled) log.debug "updateJurisdictionServices >> ${createCount}"
    return createCount
  }

}
