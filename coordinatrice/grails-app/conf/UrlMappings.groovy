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
class UrlMappings {

  static mappings = {
    "/$controller/$action?/$id?"{
      constraints {
	// apply constraints here
      }
    }
    "/rest/procdef/state/$id"(controller: 'RestProcdef') {
      action = [GET: 'procdefStateGet']
    }
    "/rest/activitylabel/$procdefkey/$locale/$activityname?"(controller: 'RestI18n') {
      action = [GET: 'activityLabelGet']
    }
    "/rest/activitylabelbyid/$procdefkey/$locale/$activityid"(controller: 'RestI18n') {
      action = [GET: 'activityLabelById']
    }
    "/rest/guideurl/$procdefkey/$locale/$activityname?"(controller: 'RestI18n') {
      action = [GET: 'guideUrlGet']
    }
    "/rest/startformlabel/$appname/$formname/$locale"(controller: 'RestI18n') {
      action = [GET: 'startFormLabelGet']
    }
    "/rest/migration/file/$filepath"(controller: 'RestMigration') {
      action = [PUT: 'installPackageFromFile']
    }
    "/rest/open311/servicedef"(controller: 'RestOpen311') {
      action = [GET: 'serviceDefinitionGet', POST: 'serviceDefinitionPut']
    }

    "/"(controller: 'Procdef', action:'/index')
    "500"(controller: 'errors', action: 'internalServerError')
  }
}
