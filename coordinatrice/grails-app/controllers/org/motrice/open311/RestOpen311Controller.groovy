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
      def outcome = handleServiceException('serviceDefinitionPut', exc)
      render(status: 409, contentType: 'text/plain', text: outcome.message)
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

  /**
   * Return a list of services on the format of the Open311 specification.
   */
  def open311Services() {
    if (log.debugEnabled) log.debug "SERVICES ${params}"
    def paramsMap = [format: params.format, jurisdictionId: params.jurisdiction_id]
    try {
      def data = open311Service.serviceList(paramsMap)
      render(status: 200, contentType: data.contentType, encoding: 'utf-8', text: data.text)
      return
    } catch (ServiceException exc) {
      def outcome = handleServiceException('open311Services', exc)
      render(status: exc.httpStatus, contentType: 'text/plain', text: outcome.message)
    }
  }

  /**
   * Pre-process an Open311 "request" call.
   * Checks the API key for validity.
   * Finds the service.
   * Returns jurisdiction and service data.
   */
  def open311Validity() {
    if (log.debugEnabled) log.debug "VALIDITY ${params}"
    def paramsMap = [apiKey: params.api_key, jurisdictionId: params.jurisdiction_id,
    serviceCode: params.service_code]
    if (params.return) paramsMap.return = params.return.split('~') as Set

    try {
      def data = open311Service.checkValidity(paramsMap)
      render(status: 200, contentType: 'application/json; charset=utf-8', encoding: 'utf-8', text: data)
    } catch (ServiceException exc) {
      def outcome = handleServiceException('open311Validity', exc)
      render(status: exc.httpStatus, contentType: 'text/plain', text: outcome.message)
    }
  }

  /**
   * Handle a service exception.
   * Somewhat different from how service exceptions are handled in other parts of
   * Coordinatrice.
   * Return a map containing entries,
   * status: HTTP status (Integer),
   * message: exception message (String).
   */
  private Map handleServiceException(String op, ServiceException exc) {
    log.error "${op} ${exc?.message}"
    def sb = new StringBuilder()
    if (exc.key) {
      sb.append(exc.key).append('|')
      sb.append(message(code: exc.key)).append('|')
    }
    sb.append(exc.message)
    return [status: exc.httpStatus, message: sb.toString()]
  }

}
