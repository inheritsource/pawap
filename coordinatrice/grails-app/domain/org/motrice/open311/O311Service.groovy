package org.motrice.open311

/**
 * A service in Open311 terminology.
 * Services are globally defined.
 * Each jurisdiction selects its own set of services to include.
 */
class O311Service implements Comparable {
  /**
   * Automatic timestamping: creation time.
   */
  Date dateCreated

  /**
   * Automatic timestamping: last update time.
   */
  Date lastUpdated

  /**
   * A short name for this service to be included in API calls.
   * Must begin with a letter (a-z).
   * Remaining characters may be letters, digits and punctuation (._-).
   */
  String code

  /**
   * The full name of this service to be shown to users.
   * TODO: Introduce I18n.
   */
  String name

  /**
   * A short description of this service.
   */
  String description

  static hasMany = [jurisdCnx: O311ServiceInJurisd]
  static transients = ['descriptionAbbrev']
  static mapping = {
    sort 'name'
  }
  static constraints = {
    dateCreated nullable: true
    lastUpdated nullable: true
    code size: 3..64, blank: false, matches: '[A-Za-z0-9][A-Za-z0-9._-]*', unique: true
    name size: 3..120, blank: false, unique: true
    description nullable: true, maxSize: 400
    jurisdCnx nullable: true
  }

  String getDescriptionAbbrev() {
    (description?.size() > 44)? description.substring(0, 42) + '...' : description
  }

  String toDebug() {
    "[Service(${id}/${code}) '${name}', '${descriptionAbbrev}']"
  }

  String toString() {
    name
  }

  //-------------------- Comparable --------------------

  int hashCode() {
    name.hashCode()
  }

  boolean equals(Object obj) {
    (obj instanceof O311Service) && ((O311Service)obj).name == name
  }

  int compareTo(Object obj) {
    def other = (O311Service)obj
    return name.compareTo(obj.name)
  }

}
