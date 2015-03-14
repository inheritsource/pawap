package org.motrice.open311

import org.apache.commons.logging.LogFactory

/**
 * Business logic for the Open311 domains.
 */
class Open311Service {
  // Also default.
  static transactional = true

  private static final log = LogFactory.getLog(this)

  /**
   * Post-process a newly created tenant.
   */
  O311Tenant createTenantPostProcess(O311Tenant tenant) {
    tenant.generateApiKey()
    tenant.save()
    return tenant
  }

}
