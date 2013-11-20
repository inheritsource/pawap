package org.motrice.orifice

/**
 * A package is a container for transferring form definitions between
 * Motrice sites.
 * A package contains one or more form definitions, all versions of those
 * form definitions, and all items (i.e. data) making up the form definitions.
 * Form definitions and versions really are metadata.
 * The substance of a form definition is the items.
 * A package may be gererated "here" (the local Motrice site), or in some
 * other site and unpacked here.
 * The same structure is used for both cases.
 */
class OriPackage implements Comparable {
  // Name of the site where the package was generated
  String siteName

  // Name of the package.
  // The name need not be unique in itself, only with site name and timestamp.
  String packageName

  // Format of the package
  String packageFormat

  // Site timestamp, the point in time when the package was generated
  Date siteTstamp

  // Was the package generated here?
  Boolean originLocal

  // Auto timestamping, the point in time when the package was created here.
  Date dateCreated

  SortedSet formdefs
  static hasMany = [formdefs: OriFormdef, versions: OriFormdefVer, items: OriItem]
  static constraints = {
    siteName size: 1..120
    packageName size: 1..120
    originLocal nullable: true
    dateCreated nullable: true
    formdefs nullable: true
    versions nullable: true
    items nullable: true
  }

  //-------------------- Comparable --------------------

  int hashCode() {
    (siteName + packageName).hashCode() ^ siteTstamp.hashCode()
  }

  boolean equals(Object obj) {
    def result = false
    if (obj instanceof OriPackage) {
      def other = (OriPackage)obj
      result = siteName == other.siteName && packageName == other.packageName &&
      siteTstamp == other.siteTstamp
    }

    return result
  }

  /**
   * Date-based comparison, latest first.
   */
  int compareTo(Object obj) {
    def other = (OriPackage)obj
    def result = -siteTstamp.compareTo(other.siteTstamp)
    if (result == 0) {
      result = siteName.compareTo(other.siteName)
      if (result == 0) {
	result == packageName.compareTo(other.packageName)
      }
    }

    return result
  }

}
