package org.motrice.open311

/**
 * A tenant is some organization having their own API key for accessing
 * our Open311 endpoint.
 */
class O311Tenant {
  /**
   * Automatic timestamping: creation time.
   */
  Date dateCreated

  /**
   * Automatic timestamping: last update time.
   */
  Date lastUpdated

  /**
   * Display name to be used in listings etc.
   */
  String displayName

  /**
   * Formal organization name.
   */
  String organizationName

  /**
   * First name of contact person.
   */
  String firstName

  /**
   * Last name of contact person.
   */
  String lastName

  /**
   * Email of contact person.
   */
  String email

  /**
   * Phone of contact person.
   */
  String phone

  /**
   * API key.
   */
  String apiKey

  static hasMany = [jurisdCnx: O311TenantInJurisd]
  static constraints = {
    dateCreated nullable: true
    lastUpdated nullable: true
    displayName blank: false, size: 3..48
    organizationName size: 3..120
    firstName nullable: true
    lastName size: 3..120
    email blank: false, size: 3..240
    phone blank: false, size: 3..60
    apiKey maxSize: 120, blank: false, unique: true
    jurisdCnx nullable: true
  }

  /**
   * Generate a new API key.
   * The current implementation is the hex encoding of a SHA-256 hash
   * over random bytes limited to 40 characters.
   * Base64 encoding would have to be uuencoded before using over the web.
   */
  def generateApiKey() {
    def bytes = new byte[64]
    def random = new java.security.SecureRandom()
    random.nextBytes(bytes)
    apiKey = java.security.MessageDigest.getInstance("SHA-256").digest(bytes).
    collect { String.format("%02x", it) }.join('').substring(0, 40)
  }

  String toDebug() {
    "[Tenant(${id}) '${displayName}']"
  }

  String toString() {
    displayName
  }

}
