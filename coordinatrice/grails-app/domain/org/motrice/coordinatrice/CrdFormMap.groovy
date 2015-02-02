package org.motrice.coordinatrice

import org.motrice.coordinatrice.pxd.PxdFormdefVer

/**
 * Defines how the values in a form instance are to be translated into
 * process variables.
 * We cannot touch the Postxdb tables, so the form map is a Coordinatrice domain.
 * The relationship between a form map and the corresponding PxdFormdefVer instance
 * is implemented by using the same primary key in this domain.
 */
class CrdFormMap {
  /**
   * A JSON string containing the mapping between form controls and variable names.
   * The structure is a list of arrays (maps in Java terminology).
   * Each array represents a section of the form.
   * Its entries are (keys),
   * "section" (value is the section name),
   * "controls" (value is a list of arrays).
   * The arrays of the "controls" list have two entries,
   * "control" (value is the control name),
   * "variable" (value is the variable name, may be null).
   */
  String jsonMap

  static mapping = {
    id generator: 'assigned'
    jsonMap type: 'text'
  }
  static transients = ['formdefVer']
  static constraints = {
  }

  PxdFormdefVer getFormdefVer() {
    if (!formdefVer) {
      formdefVer = PxdFormdefVer.get(id)
    }

    return formdefVer
  }

}
