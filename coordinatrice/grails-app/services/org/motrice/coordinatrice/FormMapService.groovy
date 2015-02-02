package org.motrice.coordinatrice

import org.motrice.coordinatrice.pxd.PxdFormdefVer

class FormMapService {
  static transactional = true

  CrdFormMap createFromForm(PxdFormdefVer formDef) {
    def formMap = new CrdFormMap()
    formMap.id = formDef.id
    return formMap
  }

  def serviceMethod() {

  }
}
