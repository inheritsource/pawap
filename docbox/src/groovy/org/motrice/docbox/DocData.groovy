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
package org.motrice.docbox

import org.motrice.docbox.form.PxdFormdefVer
import org.motrice.docbox.form.PxdItem

/**
 * Basic document data (definition and instance) as retrieved from the database.
 * Convenience class.
 */
class DocData {
  // The uuid (generated by Orbeon) that identifies the form instance
  String uuid

  // Form data as XML
  PxdItem dataItem

  // Form data attachments (List of PxdItem). May be empty.
  List auxItems

  // Form definition XML
  PxdItem formDef

  // Form definition items ("fixed items") except the XML. Example: logo.
  // List of PxdItem
  List fixedItems

  // Form definition metadata
  PxdFormdefVer formMeta

  DocData(String uuid, PxdItem dataItem) {
    this.uuid = uuid
    this.dataItem = dataItem
  }

  String toString() {
    "[DocData ${dataItem}: auxItems: ${auxItems?.size()}, form: ${formDef}, meta: ${formMeta}]"
  }

}
