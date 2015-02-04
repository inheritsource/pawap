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

import org.motrice.coordinatrice.pxd.PxdFormdefVer

/**
 * Class for decoding and encoding formpath in MtfActivityFormDefinition, a string.
 * The value indicates one of several connection states.
 */
class TaskFormSpec {

  // Unconnected
  public static Integer UNCONNECTED_STATE = 0
  public static String UNCONNECTED_MSG = 'activity.connection.state.unconnected'

  // Connect to a form
  public static Integer FORM_STATE = MtfFormType.ORBEON_TYPE_ID
  public static String FORM_MSG = 'activity.connection.state.form'

  // Connect to no form
  public static Integer NO_FORM_STATE = MtfFormType.NO_FORM_ID
  public static String NO_FORM_KEY = 'none'
  public static String NO_FORM_MSG = 'activity.connection.state.noform'

  // Connect to signing the start form
  public static Integer SIGN_START_FORM_STATE = MtfFormType.SIGN_START_FORM_ID
  public static String SIGN_START_FORM_KEY = 'signstartform'
  public static String SIGN_START_FORM_MSG = 'activity.connection.state.signstartform'

  // Connect to signing an activity
  public static Integer SIGN_ACTIVITY_STATE = MtfFormType.SIGN_TASK_FORM_ID
  public static String SIGN_ACTIVITY_KEY = 'signactivity'
  public static String SIGN_ACTIVITY_MSG = 'activity.connection.state.signactivity'

  // (Future:) Connect to signing an activity
  public static Integer PAY_ACTIVITY_STATE = MtfFormType.PAY_TASK_ID
  public static String PAY_ACTIVITY_KEY = 'payactivity'
  public static String PAY_ACTIVITY_MSG = 'activity.connection.state.payactivity'

  // Notification
  public static Integer NOTIFY_ACTIVITY_STATE = MtfFormType.NOTIFY_ACT_ID
  public static String NOTIFY_ACTIVITY_KEY = 'notifyactivity'
  public static String NOTIFY_ACTIVITY_MSG = 'activity.connection.state.notifyactivity'

  // State of this connection, one of the STATE constants
  Integer state

  // The key defining this connection
  // The following states have a connection key:
  // FORM, SIGN_ACTIVITY, PAY_ACTIVITY, NOTIFY_ACTIVITY
  String connectionKey

  // Text to render in the gui
  String connectionLabel

  // The ActDef instance to which this connection belongs
  ActDef activity

  /**
   * Construct from an activity definition and an connection.
   * The connection may be null.
   */
  TaskFormSpec(ActDef activity, MtfActivityFormDefinition connection) {
    this.activity = activity
    if (!connection?.formHandlerType) {
      state = UNCONNECTED_STATE
      connectionKey = null
      connectionLabel = null
    } else {
      state = connection.formHandlerType.id
      switch (state) {
      case FORM_STATE:
      connectionKey = connection.formConnectionKey
      connectionLabel = connectionKey
      break
      case SIGN_ACTIVITY_STATE:
      case PAY_ACTIVITY_STATE:
      case NOTIFY_ACTIVITY_STATE:
      connectionKey = connection.formConnectionKey
      def otherActivity = activity.findSibling(connectionKey)
      connectionLabel = otherActivity?.name
      break
      }
    }
  }

  /**
   * Construct from an activity definition and activity connection edit input
   * Arguments reversed to avoid overloading conflicts.
   */
  TaskFormSpec(ActivityConnectionCommand command, ActDef activity) {
    this.activity = activity
    def otherActivity = null
    if (command.otherActivityId) otherActivity = activity.findSibling(command.otherActivityId)
    // This happens if no radio button is selected
    if (command.form && command.connectionState == null) {
      command.connectionState = TaskFormSpec.FORM_STATE
    }
    state = command.connectionState
    
    switch (state) {
    case FORM_STATE:
    connectionKey = command?.form?.path
    connectionLabel = connectionKey
    break
    case SIGN_ACTIVITY_STATE:
    case PAY_ACTIVITY_STATE:
    case NOTIFY_ACTIVITY_STATE:
    connectionKey = otherActivity?.uuid
    connectionLabel = otherActivity?.name
    break
    default:
    // No connection specified
    connectionKey = null
    }
  }

  static String unconnected() {
    UNCONNECTED_MSG
  }

  /**
   * Find the form definition associated with this task form spec if relevant.
   */
  PxdFormdefVer findFormdef() {
    def result = null
    if (formState && connectionKey) {
      result = PxdFormdefVer.findByPath(connectionKey)
    }

    return result
  }

  /**
   * Get a list of state label codes and values (used for radio buttons)
   * Return a map with the following entries:
   * codes: List of String containing state label message codes
   * values: List of Integer containing state integer codes
   */
  Map stateInfo() {
    def map = [:]
    map.codes = [FORM_MSG, NO_FORM_MSG, SIGN_START_FORM_MSG, SIGN_ACTIVITY_MSG,
		 NOTIFY_ACTIVITY_MSG]
    map.values = [FORM_STATE, NO_FORM_STATE, SIGN_START_FORM_STATE, SIGN_ACTIVITY_STATE,
		  NOTIFY_ACTIVITY_STATE]
    return map
  }

  boolean isUnconnectedState() {
    state == UNCONNECTED_STATE
  }

  boolean isFormState() {
    state == FORM_STATE
  }

  boolean isNoFormState() {
    state == NO_FORM_STATE
  }

  boolean isSignStartFormState() {
    state == SIGN_START_FORM_STATE
  }

  boolean isSignActivityState() {
    state == SIGN_ACTIVITY_STATE
  }

  boolean isPayActivityState() {
    state == PAY_ACTIVITY_STATE
  }

  boolean isNotifyState() {
    state == NOTIFY_ACTIVITY_STATE
  }

  /**
   * Convert state to a message key
   */
  String msgKey() {
    switch (state) {
    case UNCONNECTED_STATE:
    UNCONNECTED_MSG
    break
    case FORM_STATE:
    FORM_MSG
    break
    case NO_FORM_STATE:
    NO_FORM_MSG
    break
    case SIGN_START_FORM_STATE:
    SIGN_START_FORM_MSG
    break
    case SIGN_ACTIVITY_STATE:
    SIGN_ACTIVITY_MSG
    break
    case PAY_ACTIVITY_STATE:
    PAY_ACTIVITY_MSG
    break
    case NOTIFY_ACTIVITY_STATE:
    NOTIFY_ACTIVITY_MSG
    break
    default:
    'activity.connection.state.unknown'
    }
  }

  /**
   * Convert to a display string
   */
  String toDisplay() {
    connectionLabel ?: ''
  }

  String toString() {
    "[TaskFormSpec(${state}) owner=[${activity}] connection=[${connectionKey}|${connectionLabel}]]"
  }

}
