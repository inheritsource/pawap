package org.motrice.coordinatrice

import org.activiti.bpmn.model.FlowElement
import org.activiti.engine.runtime.Execution

/**
 * A BPMN execution as implemented by Activiti.
 * A process instance may generate several executions.
 * A process instance is a special case of an execution.
 * This class is NOT PERSISTED, constructed read-only from the BPMN engine.
 */
class BpmnExecution {
  /**
   * The unique id of this execution.
   * Because of the special meaning of "id" in Grails, we use "uuid".
   */
  String uuid

  /**
   * Is the process instance suspended?
   */
  Boolean suspended

  /**
   * Has execution ended?
   */
  Boolean ended

  /**
   * Id of the activity where the execution is currently at.
   */
  String activityId

  /**
   * The flow element corresponding to the activityId
   */
  FlowElement flowElement

  /**
   * Parent execution id.
   */
  String parentId

  /**
   * Process instance id.
   */
  String processInstanceId

  /**
   * Label obtained by "toString()".
   */
  String label

  /**
   * Not a database object, never to be persisted
   */
  static mapWith = 'none'

  static transients = ['flowElement', 'flowElementName', 'variables']
  static constraints = {
    uuid nullable: false
  }

  def assignFromExecution(Execution ex) {
    uuid = ex.id
    suspended = ex.suspended
    ended = ex.ended
    activityId = ex.activityId
    parentId = ex.parentId
    processInstanceId = ex.processInstanceId
    label = ex.toString()
  }

  String getFlowElementName() {
    flowElement? "${flowElement.name}(${activityId})" : activityId
  }

  String toStringDetails() {
    "uuid(${uuid}) parent(${parentId}) pid(${processInstanceId}) suspended(${suspended}) ended(${ended}) activity(${activityId}) flow(${flowElementName})"
  }

  String toString() {
    "[Execution(${uuid}/${processInstanceId}) ${toStringDetails()}]"
  }

}
