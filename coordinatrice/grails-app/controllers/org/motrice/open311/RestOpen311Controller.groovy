package org.motrice.open311

import org.motrice.coordinatrice.ServiceException

/**
 * Controller for all REST methods related to Open311.
 */
class RestOpen311Controller {

  // Injection magic
  def open311Service

  /**
   * Define a number of Open311 services.
   * The request body must contain an XML structure.
   */
  def serviceDefinitionPut() {
    if (log.debugEnabled) log.debug "SERVICE DEF PUT"
    def serviceXml = request.reader.text
    try {
      def serviceCount = open311Service.defineServices(serviceXml)
      render(status: 201, contentType: 'text/plain', text: "${serviceCount} services created")
    } catch (ServiceException exc) {
      def msg = handleServiceException('serviceDefinitionPut', exc)
      render(status: 409, contentType: 'text/plain', text: msg)
    }
  }

  /**
   * Retrieve Open311 services.
   * Returns an XML text on the format required for 'serviceDefinitionPut'.
   */
  def serviceDefinitionGet() {
    if (log.debugEnabled) log.debug "SERVICE DEF GET"
    def xml = open311Service.retrieveServices()
    render(status: 200, contentType: 'text/xml', encoding: 'utf-8', text: xml)
  }

  private String handleServiceException(String op, ServiceException exc) {
    log.error "${op} ${exc?.message}"
    exc.key? "${exc.key}|${message(code: exc.key, args: exc.args ?: [])}" :
    "OPEN311.000|${exc?.message}"
  }

}
