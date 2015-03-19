package org.motrice.open311

import org.motrice.coordinatrice.Procdef

/**
 * A jurisdiction is some entity that runs an Open311 server.
 * The idea is that several jurisdictions may share the same endpoint.
 */
class O311Jurisdiction implements Comparable {
  /**
   * Automatic timestamping: creation time.
   */
  Date dateCreated

  /**
   * Automatic timestamping: last update time.
   */
  Date lastUpdated

  /**
   * A short name that must be passed with API calls.
   * The recommendation is to use the web site minus leading "www".
   * Example: "stockholm.se".
   * Must begin with a letter (a-z) or digit.
   * Remaining characters may be letters, digits and punctuation (._-).
   */
  String jurisdictionId

  /**
   * A full and descriptive name.
   */
  String fullName

  /**
   * Is this jurisdiction enabled?
   * Null means disabled.
   */
  Boolean enabledFlag

  /**
   * A short message to return to human users who create requests.
   * Example: "Thank you for your report"
   */
  String serviceNotice

  /**
   * Process definition id to use for this jurisdiction.
   */
  String procdefUuid

  /**
   * A process definition may be added temporarily as a transient property.
   */
  Procdef procdef

  SortedSet serviceGroups
  static hasMany = [serviceCnx: O311ServiceInJurisd, serviceGroups: O311ServiceGroup,
  tenantCnx: O311TenantInJurisd]
  static transients = ['procdef', 'tenants']
  static mapping = {
    cache true
    sort 'fullName'
  }
  static constraints = {
    dateCreated nullable: true
    lastUpdated nullable: true
    jurisdictionId blank: false, size: 3..60, matches: '[A-Za-z0-9][A-Za-z0-9._-]*', unique: true
    fullName size: 3..160, blank: false, unique: true
    enabledFlag nullable: true
    serviceNotice nullable: true, maxSize: 240
    procdefUuid nullable: true, maxSize: 64
    serviceCnx nullable: true
    serviceGroups nullable: true
    tenantCnx nullable: true
  }

  /**
   * The jurisdiction id of the default jurisdiction (having no name).
   */
  final static String DEFAULT_JURISDICTION_ID = 'nameless'

  /**
   * Initial full name of the default jurisdiction.
   */
  final static String INITIAL_DEFAULT_JURISDICTION_FULL_NAME = 'Default Jurisdiction'

  /**
   * Create a jurisdiction if it does not exist.
   * Mainly for bootstrap.
   */
  static create(String id, String fullName) {
    if (!O311Jurisdiction.findByJurisdictionId(id)) {
      def jurisd = new O311Jurisdiction(jurisdictionId: id, fullName: fullName).
      save(failOnError: true)
    }
  }

  String getProcdefDisplay() {
    (procdef ?: procdefUuid) ?: '--'
  }

  boolean isDefaultJurisdiction() {
    jurisdictionId == DEFAULT_JURISDICTION_ID
  }

  boolean isEnabled() {
    serviceNotice?.trim() && procdefUuid?.trim()
  }

  List getTenants() {
    def cr = O311TenantInJurisd.createCriteria()
    def tenantList = cr.list {
      jurisdiction {
	idEq(id)
      }
    }

    return tenantList? tenantList.collect{O311Tenant.get(it.tenant.id)}.sort() : []
  }

  String toDebug() {
    "[Jurisdiction(${id}/${jurisdictionId}) ${procdef?:'()'} '${fullName}', '${serviceNotice}']"
  }

  String toString() {
    fullName
  }

  //-------------------- Comparable --------------------

  int hashCode() {
    fullName.hashCode()
  }

  boolean equals(Object obj) {
    (obj instanceof O311Jurisdiction) && ((O311Jurisdiction)obj).fullName == fullName
  }

  int compareTo(Object obj) {
    def other = (O311Jurisdiction)obj
    return fullName.compareTo(obj.fullName)
  }

}
