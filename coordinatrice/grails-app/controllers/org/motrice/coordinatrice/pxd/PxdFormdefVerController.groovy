/* == Motrice Copyright Notice ==
 *
 * Motrice Service Platform
 *
 * Copyright (C) 2011-2014 Motrice AB
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * e-mail: info _at_ motrice.se
 * mail: Motrice AB, Långsjövägen 8, SE-131 33 NACKA, SWEDEN
 * phone: +46 8 641 64 14
 */
package org.motrice.coordinatrice.pxd

import org.motrice.coordinatrice.CrdFormMap

class PxdFormdefVerController {

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 15, 100)
        [pxdFormdefVerInstList: PxdFormdefVer.list(params), pxdFormdefVerInstTotal: PxdFormdefVer.count()]
    }

    def show(Long id) {
        def pxdFormdefVerInst = PxdFormdefVer.get(id)
        if (!pxdFormdefVerInst) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'pxdFormdefVer.label', default: 'PxdFormdefVer'), id])
            redirect(action: "list")
            return
        }

	def formMap = CrdFormMap.get(id)
        [pxdFormdefVerInst: pxdFormdefVerInst, crdFormMap: formMap]
    }

}
