package org.motrice.open311

/**
 * A connection between a service and a jurisdiction implementing a
 * M:M relationship between the two.
 * There is a third party in this relationship, an optional connection
 * to a service group.
 */
class O311ServiceInJurisd {
  /**
   * An optional service group for this service.
   * We avoid "belongsTo" because it would introduce undesirable cascading.
   */
  O311ServiceGroup serviceGroup

  static belongsTo = [jurisdiction: O311Jurisdiction, service: O311Service]
  static constraints = {
    serviceGroup nullable: true
  }

  static List allByJurisdiction(Long jurisdId) {
    def cr = O311ServiceInJurisd.createCriteria()
    cr.list {
      jurisdiction {
	idEq(jurisdId)
      }
    }
  }

  String toDebug() {
    "[ServiceInJurisd(${id}) '${jurisdiction?.fullName}':'${service?.name} (${serviceGroup?.displayName})]"
  }

  String toString() {
    id
  }

}
