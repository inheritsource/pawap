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
package org.motrice.coordinatrice

import org.springframework.dao.DataIntegrityViolationException

class CrdI18nActLabelController {

  def activityLabelService

  static allowedMethods = [save: "POST", updatekey: "POST", deletekey: "POST"]

  def index() {
    redirect(action: "list", params: params)
  }

  def list(Integer max) {
    if (log.debugEnabled) log.debug "LIST ${params}"
    params.max = Math.min(max ?: 30, 100)
    [actLabelInstList: CrdI18nActLabel.list(params), actLabelInstTotal: CrdI18nActLabel.count()]
  }

  // List activity labels
  // id must be defined and limits the list to that process definition key
  def listkey(Integer max) {
    if (log.debugEnabled) log.debug "LISTKEY ${params}"
    params.max = Math.min(max ?: 30, 100)
    def key = params.id
    if (key) {
      def instSet = new TreeSet(CrdI18nActLabel.findAllByProcdefKey(key, params))
      def instTotal = CrdI18nActLabel.countByProcdefKey(key)
      [procdefKey: key, actLabelInstList: instSet, actLabelInstTotal: instTotal]
    } else {
      redirect(action: "list")
    }
  }

  // Create i18n labels for a locale.
  // The process definition key must be given as params.id.
  def createlocale(String id) {
    if (log.debugEnabled) log.debug "CREATE LOCALE ${params}"
    if (id) {
      [procdefKey: id]
    } else {
      redirect(action: 'list')
    }
  }

  def savelocale() {
    if (log.debugEnabled) log.debug "SAVE LOCALE ${params}"
    def key = params.key
    def localeStr = params.locale
    def labelCount = activityLabelService.createLabels(key, localeStr)
    flash.message = message(code: 'crdI18nActLabel.generated.count', args: [labelCount])
    redirect(action: 'listkey', id: key)
  }

  /**
   * Create a new activity label with a new process version
   */
  def createversion(Long id) {
    if (log.debugEnabled) log.debug "CREATE VERSION ${params}"
    def actLabelInstProto = CrdI18nActLabel.get(id)
    if (!actLabelInstProto) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdI18nActLabel.label', default: 'CrdI18nActLabel'), id])
      redirect(action: "list")
      return
    }

    def actLabelInst = activityLabelService.createVersion(actLabelInstProto)
    flash.message = message(code: 'default.created.message', args: [message(code: 'crdI18nActLabel.label', default: 'CrdI18nActLabel'), actLabelInst.id])
    redirect(action: 'listkey', id: actLabelInst?.procdefKey)
  }

  def show(Long id) {
    def actLabelInst = CrdI18nActLabel.get(id)
    if (!actLabelInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdI18nActLabel.label', default: 'CrdI18nActLabel'), id])
      redirect(action: "list")
      return
    }

    [actLabelInst: actLabelInst]
  }

  def editkey(Long id) {
    if (log.debugEnabled) log.debug "EDIT KEY ${params}"
    def actLabelInst = CrdI18nActLabel.get(id)
    if (!actLabelInst) {
      flash.message = message(code: 'default.not.found.message', args: [message(code: 'crdI18nActLabel.label', default: 'CrdI18nActLabel'), id])
      redirect(action: "list")
      return
    }

    def actLabelList = null
    if (params.mode == 'activity') {
      actLabelList = CrdI18nActLabel.findAllByProcdefKeyAndActdefName(actLabelInst.procdefKey, actLabelInst.actdefName)
    } else {
      actLabelList = CrdI18nActLabel.findAllByProcdefKeyAndLocale(actLabelInst.procdefKey, actLabelInst.locale)
    }

    def idList = actLabelList.collect {it.id}
    [actLabelList: actLabelList, procdefKey: actLabelInst.procdefKey,
    idList: idList.join('|').bytes.encodeBase64()]
  }

  def updatekey() {
    if (log.debugEnabled) log.debug "UPDATE KEY ${params}"
    def updateCount = 0
    try {
      updateCount = activityLabelService.updateEditedLabels(params)
    } catch (ServiceException exc) {
      handleServiceException('updatekey', exc)
      redirect(controller: 'procdef', action: 'list')
    }

    def procdefKey = params.procdefKey
    flash.message = message(code: 'crdI18nActLabel.update.count', args: [updateCount])
    redirect(action: 'listkey', id: procdefKey)
  }

  def deletekey(Long id) {
    if (log.debugEnabled) log.debug "DELETE KEY ${params}"
    def deleteCount = activityLabelService.deleteEditedLabels(params)
    def procdefKey = params.procdefKey
    flash.message = message(code: 'crdI18nActLabel.delete.count', args: [deleteCount])
    redirect(action: 'listkey', id: procdefKey)
  }

  private handleServiceException(String op, ServiceException exc) {
    log.error "${op} ${exc?.message}"
    if (exc.key) {
      flash.message = message(code: exc.key, args: exc.args ?: [])
    } else {
      flash.message = exc.message
    }
  }

}
