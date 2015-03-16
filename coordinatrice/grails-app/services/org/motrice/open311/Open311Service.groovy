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

}
