package org.motrice.open311

/**
 * A named service category for a jurisdiction.
 * The relationship between jurisdiction and service group is 1:M.
 */
class O311ServiceGroup implements Comparable {
  /**
   * Automatic timestamping: creation time.
   */
  Date dateCreated

  /**
   * Automatic timestamping: last update time.
   */
  Date lastUpdated

  /**
   * A short and unique name for this service group.
   * Must begin with a letter (a-z).
   * Remaining characters may be letters, digits and punctuation (._-).
   */
  String code

  /**
   * The display name of this service group.
   */
  String displayName

  static belongsTo = [jurisdiction: O311Jurisdiction]
  static constraints = {
    dateCreated nullable: true
    lastUpdated nullable: true
    code size: 3..64, matches: '[A-Za-z][A-Za-z0-9._-]*', unique: 'jurisdiction'
    displayName size: 3..120
  }

  String toDebug() {
    "[ServiceGroup(${id}/${code}) '${displayName}']"
  }

  String toString() {
    displayName
  }

  //-------------------- Comparable --------------------

  int hashCode() {
    code.hashCode()
  }

  boolean equals(Object obj) {
    (obj instanceof O311ServiceGroup) && ((O311ServiceGroup)obj).code == code
  }

  int compareTo(Object obj) {
    def other = (O311ServiceGroup)obj
    return code.compareTo(obj.code)
  }

}
