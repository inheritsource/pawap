package org.motrice.coordinatrice

import org.activiti.engine.task.DelegationState
import org.activiti.engine.task.Task

import org.motrice.coordinatrice.pxd.PxdFormdefVer
import org.motrice.coordinatrice.pxd.PxdItem

/**
 * A BPMN task as implemented by Activity.
 * This class is NOT PERSISTED, constructed read-only from the BPMN engine.
 */
class BpmnTask {
  /**
   * Database id of this task.
   * The name "uuid" is used to avoid inteference with Grails conventions.
   */
  String uuid

  /**
   * Activity form definition.
   */
  PxdFormdefVer formdef

  /**
   * Activity form instance.
   */
  PxdItem forminst

  /**
   * Process to form connection.
   */
  MtfActivityFormDefinition mafd

  /**
   * The name or title of this task.
   */
  String name

  /**
   * Free text description of this task.
   */
  String description

  /**
   * The id of the activity of the process definition that defines this task, or null.
   */
  String definitionKey

  /**
   * Is this task suspended?
   */
  Boolean suspended

  /**
   * User id of person responsible for this task.
   */
  String owner

  /**
   * User id of person to whom this task has been delegated, or null.
   */
  String assignee

  /**
   * The point in time when this task was created.
   */
  Date createdTime

  /**
   * The point in time when this task is due.
   */
  Date dueTime

  /**
   * The priority of this task.
   */
  Integer priority

  /**
   * Reference to the execution where this task belongs, or null.
   */
  String executionId

  /**
   * Reference to the process instance where this task belongs, or null.
   */
  String processInstanceId

  /**
   * Process variables (transient).
   * Used when completing a task forcefully.
   */
  Map variables

  /***** Transient fields containing Activiti objects *****/

  /**
   * The Activiti object corresponding to this one.
   */
  Task activitiTask

  /**
   * The delegation state of this task.
   */
  DelegationState delegationState

  /**
   * Not a database object, never to be persisted
   */
  static mapWith = 'none'

  static transients = ['activitiTask', 'delegationState', 'procdefId', 'variables']
  static constraints = {
    definitionKey nullable: true
    assignee nullable: true
    formdef nullable: true
  }

  /**
   * Assign values from an Activiti task.
   * The Activiti task is assumed to have been assigned to 'activitiTask'.
   */
  def assignFromTask() {
    uuid = activitiTask.id
    name = activitiTask.name
    description = activitiTask.description
    definitionKey = activitiTask.taskDefinitionKey
    suspended = activitiTask.suspended
    owner = activitiTask.owner
    assignee = activitiTask.assignee
    createdTime = activitiTask.createTime
    dueTime = activitiTask.dueDate
    priority = activitiTask.priority
    executionId = activitiTask.executionId
    processInstanceId = activitiTask.processInstanceId
    delegationState = activitiTask.delegationState
  }

  def assignFromTask(Task task) {
    activitiTask = task
    assignFromTask()
  }

  String getProcdefId() {
    activitiTask?.processDefinitionId
  }

  String toString() {
    def sb = new StringBuilder()
    sb.append('[BpmnTask(').append(uuid).append(') ')
    sb.append('name="').append(name).append('"')
    def sep = ','
    if (description) sb.append(sep).append('description=').append(description)
    if (definitionKey) sb.append(sep).append('definitionKey=').append(definitionKey)
    if (suspended) sb.append(sep).append('suspended=').append(suspended)
    if (delegationState) sb.append(sep).append('delegationState=').append(delegationState)
    if (owner) sb.append(sep).append('owner=').append(owner)
    if (assignee) sb.append(sep).append('assignee=').append(assignee)
    if (createdTime) sb.append(sep).append('createdTime=').append(createdTime)
    if (dueTime) sb.append(sep).append('dueTime=').append(dueTime)
    if (priority) sb.append(sep).append('priority=').append(priority)
    if (executionId) sb.append(sep).append('executionId=').append(executionId)
    if (processInstanceId) sb.append(sep).append('processInstanceId=').append(processInstanceId)
    sb.append(']')
    return sb.toString()
  }

}
