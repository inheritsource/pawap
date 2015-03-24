package org.motrice.open311

import grails.converters.*
import org.apache.commons.logging.LogFactory
import org.motrice.coordinatrice.ServiceException

/**
 * Business logic for the Open311 domains.
 */
class Open311Service {
  // Also default.
  static transactional = true

  def procdefService

  private static final log = LogFactory.getLog(this)

  /**
   * Content-Type header for JSON.
   */
  final static String JSON_CONTENT_TYPE = 'application/json; charset=utf-8'

  /**
   * Content-Type header for XML.
   */
  final static String XML_CONTENT_TYPE = 'text/xml; charset=utf-8'

  /**
   * XML declaration.
   */
  final static String XML_DECLARATION = '<?xml version="1.0" encoding="utf-8"?>'

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
      if (jurisd.procdefUuid) jurisd.procdef = procdefService.findShallowProcdef(jurisd.procdefUuid)
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
      // Remember to propagate the enabled flag.
      def tenantInJurisd = new O311TenantInJurisd(enabledFlag: jurisdiction.enabledFlag)
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
   * Make sure all tenant links for a given jurisdiction are in sync
   * with respect to the enabled flag.
   */
  def jurisdictionEnabledSync(O311Jurisdiction jurisdiction) {
    if (log.debugEnabled) log.debug "jurisdictionEnabledSync << ${jurisdiction}"
    def q = 'update O311TenantInJurisd x set x.enabledFlag=? where jurisdiction=?'
    def result = O311TenantInJurisd.executeUpdate(q, [jurisdiction.enabledFlag, jurisdiction])
    if (log.debugEnabled) log.debug "jurisdictionEnabledSync >> ${result}"
    return result
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

  /**
   * Do the heavy lifting of the Open311 "services" method.
   * Possible entries of the params map (keys and values are String):
   * format: "xml" (default) or "json",
   * jurisdictionId: a jurisdiction id or an empty string or null to indicate
   * the default jurisdiction.
   * RETURNS a map with the following entries:
   * contentType: a content type string,
   * text: the body of the response.
   */
  Map serviceList(Map params) {
    if (log.debugEnabled) log.debug "serviceList << ${params}"
    def jurisd = findJurisdiction(params.jurisdictionId)
    // Get a list of pairs [service, serviceInJurisd]
    def serviceList = O311Service.executeQuery(SERVICE_LIST_Q, jurisd)
    // We may still need service groups.
    def groupMap = [:]
    if (serviceList) {
      def groupList = O311ServiceGroup.findAllByJurisdiction(jurisd)
      groupList.each {serviceGroup ->
	groupMap[serviceGroup.id] = serviceGroup
      }
    }

    // Convert to a list more like the final result.
    def responseList = serviceList.collect {pair ->
      def service = pair[0]
      def serviceInJurisd = pair[1]
      def entry = [service_code: service.code, service_name: service.name,
      description: service.description, metadata: false, type: 'realtime',
      keywords: '']
      if (serviceInJurisd.serviceGroup) {
	def group = groupMap[serviceInJurisd.serviceGroup.id]
	entry.group = group.code
      } else {
	entry.group = ''
      }

      return entry
    }

    // Convert to String in the appropriate format.
    def result = null
    if (params.format == 'json') {
      result = doServiceListAsJson(responseList)
    } else {
      result = doServiceListAsXml(responseList)
    }

    if (log.debugEnabled) log.debug "serviceList >> ${responseList?.size()}"
    return result
  }

  final static SERVICE_LIST_Q = 'from O311Service as s, O311ServiceInJurisd as x where x.service = s and x.jurisdiction=?'

  private Map doServiceListAsJson(List list) {
    def map = [contentType: JSON_CONTENT_TYPE]
    map.text = list as JSON
    return map
  }

  private Map doServiceListAsXml(List list) {
    def map = [contentType: XML_CONTENT_TYPE]
    def sw = new StringWriter()
    def pw = new PrintWriter(sw)
    pw.println(XML_DECLARATION)
    pw.flush()
    def xml = new groovy.xml.MarkupBuilder(sw)
    xml.services() {
      list.each {svc ->
	service() {
	  service_code(svc.service_code) {}
	  service_name(svc.service_name) {}
	  description(svc.description) {}
	  'group'(svc.group) {}
	  metadata(false) {}
	  type('realtime') {}
	  keywords('') {}
	}
      }
    }

    sw.flush()
    map.text = sw.toString()
    return map
  }

  /**
   * Retrieve a jurisdiction.
   * ServiceException if not found
   */
  private O311Jurisdiction findJurisdiction(String jurisdictionId) {
    def key = jurisdictionId ?: O311Jurisdiction.DEFAULT_JURISDICTION_ID
    def jurisd = O311Jurisdiction.findByJurisdictionId(key)
    if (!jurisd) {
      def exc = new ServiceException("Jurisdiction id '${key}'", 'OPEN311.102')
      exc.httpStatus = 404
      throw exc
    }

    if (log.debugEnabled) log.debug "findJurisdiction >> ${jurisd}"
    return jurisd
  }

  /**
   * Pre-process an Open311 "request" call.
   * Possible entries of the params map (keys and values are String):
   * format: "xml" (default) or "json",
   * apiKey: the API key of the calling tenant,
   * jurisdictionId: a jurisdiction id or an empty string or null to indicate
   * the default jurisdiction,
   * serviceCode: a service code intended to identify an Open311 service.
   * The API key is the only mandatory parameter.
   * RETURN a map containing the requested return data -- as a JSON string.
   */
  String checkValidity(Map params) {
    if (log.debugEnabled) log.debug "checkValidity << ${params}"
    def jurisdictionId = params.jurisdictionId ?: ''
    def serviceCode = params.serviceCode
    def idf = O311TenantInJurisd.compoundId(params.apiKey, jurisdictionId)
    def tenantLink = O311TenantInJurisd.get(idf)
    if (!tenantLink) {
      def exc = new ServiceException("Jurisdiction id '${jurisdictionId}'", 'OPEN311.105')
      exc.httpStatus = 401
      throw exc
    } else if (!tenantLink.enabledFlag) {
      def exc = new ServiceException("Jurisdiction id '${jurisdictionId}'", 'OPEN311.103')
      exc.httpStatus = 409
      throw exc
    }
    if (log.debugEnabled) log.debug "checkValidity.tenantLink: ${tenantLink?.toDebug()}"
    // Check the service code, if included.
    def service = null
    def serviceLink = null
    if (serviceCode) {
      service = O311Service.findByCode(serviceCode)
      if (!service) {
	def exc = new ServiceException("Service code '${serviceCode}'", 'OPEN311.104')
	exc.httpStatus = 404
	throw exc
      }

      serviceLink = O311ServiceInJurisd.
      findByJurisdictionAndService(tenantLink.jurisdiction, service)
      if (log.debugEnabled) log.debug "checkValidity.serviceLink: ${serviceLink?.toDebug()}"
      if (!serviceLink) {
	def msg = "Service code '${serviceCode}'. Jurisdiction id '${jurisdictionId}'."
	def exc = new ServiceException(msg, 'OPEN311.106')
	exc.httpStatus = 409
	throw exc
      }
    }

    // So much for checking. Now the return data.
    def map = [:]
    def returnSet = params.return
    if (returnSet instanceof Set) {
      returnSet.each {key ->
	if (checkFor(returnSet, 'jurisdiction')) {
	  map.jurisdiction = doJurisdictionReturn(tenantLink)
	}
	if (checkFor(returnSet, 'tenant')) {
	  map.tenant = doTenantReturn(tenantLink)
	}
	if (checkFor(returnSet, 'service')) {
	  map.service = doServiceReturn(service, serviceLink)
	}
      }
    }

    if (log.debugEnabled) log.debug "checkValidity << ${map}"
    return map as JSON
  }

  /**
   * Special method to check for the return options.
   */
  private boolean checkFor(Set set, String option) {
    set.contains(option) || set.contains('all')
  }

  private Map doJurisdictionReturn(O311TenantInJurisd tenantLink) {
    def jurisd = tenantLink.jurisdiction
    def map = [jurisdiction_id: jurisd.jurisdictionId, full_name: jurisd.fullName,
    enabled_flag: jurisd.enabledFlag, service_notice: jurisd.serviceNotice,
    procdef_id: jurisd.procdefUuid]
    if (!jurisd.procdef) jurisd.procdef = procdefService.findShallowProcdef(jurisd.procdefUuid)
    // List of StartFormView (due to unresolved problem with Hibernate)
    def startForms = jurisd.procdef.startForms
    map.startForms = startForms.collect {it.formConnectionKey}
    return map
  }

  private Map doTenantReturn(O311TenantInJurisd tenantLink) {
    def tenant = tenantLink.tenant
    return [display_name: tenant.displayName, organization_name: tenant.organizationName,
    first_name: tenant.firstName, last_name: tenant.lastName, email: tenant.email,
    phone: tenant.phone]
  }

  private Map doServiceReturn(O311Service service, O311ServiceInJurisd serviceLink) {
    if (!service || !serviceLink) return [:]
    def map = [code: service.code, name: service.name, description: service.description]
    if (serviceLink.serviceGroup) {
      def sg = serviceLink.serviceGroup
      map.service_group_code = sg.code
      map.service_group_display_name = sg.displayName
    }

    return map
  }

}
