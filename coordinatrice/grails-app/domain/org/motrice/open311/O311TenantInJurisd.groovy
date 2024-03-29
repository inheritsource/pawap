package org.motrice.open311

/**
 * A single connection between a tenant and a jurisdiction, a many-to-many
 * relationship.
 * An instance is created when a tenant is enabled and removed if it is disabled.
 * NOTE: There is some intentional redundancy in this class.
 * It may have to be updated when updating a Tenant or Jurisdiction.
 */
class O311TenantInJurisd {
  /**
   * This is the primary key, a string containing the API key of a tenant
   * and the id of a jurisdiction (jurisdictionId).
   * The two parts are separated by a vertical bar (|).
   * The jurisdiction id may be empty, denoting the default jurisdiction.
   */
  String id

  /**
   * Is the jurisdiction enabled?
   * This field must be kept in sync with the jurisdiction domain.
   * Intentional redundancy for performance.
   */
  Boolean enabledFlag

  static belongsTo = [tenant: O311Tenant, jurisdiction: O311Jurisdiction]
  static mapping = {
    id generator: 'assigned'
    cache true
  }
  static constraints = {
    id maxSize: 240, blank: false, unique: true
    enabledFlag nullable: true
  }

  static List allByTenant(Long tenantId) {
    def cr = O311TenantInJurisd.createCriteria()
    cr.list {
      tenant {
	idEq(tenantId)
      }
    }
  }

  static String compoundId(String apiKey, String jurisdictionId) {
    "${apiKey}|${jurisdictionId?:''}"
  }

  static String compoundId(O311Tenant tenant, O311Jurisdiction jurisdiction) {
    def jid = jurisdiction.defaultJurisdiction? '' : jurisdiction.jurisdictionId
    return compoundId(tenant.apiKey, jid)
  }

  def assignId(O311Tenant tenant, O311Jurisdiction jurisdiction) {
    id = compoundId(tenant, jurisdiction)
  }

  String toDebug() {
    "[TenantInJurisd(${id}) '${jurisdiction?.fullName}':'${tenant?.displayName}']"
  }

  String toString() {
    id
  }

}
