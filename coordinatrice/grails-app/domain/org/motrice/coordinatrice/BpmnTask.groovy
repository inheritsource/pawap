package org.motrice.coordinatrice

import org.activiti.engine.task.DelegationState
import org.activiti.engine.task.Task

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
   * The delegation state of this task.
   */
  DelegationState delegationState

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
   * Not a database object, never to be persisted
   */
  static mapWith = 'none'

  static transients = ['delegationState']
  static constraints = {
    definitionKey nullable: true
    assignee nullable: true
  }

  def assignFromTask(Task task) {
    uuid = task.id
    name = task.name
    description = task.description
    definitionKey = task.taskDefinitionKey
    suspended = task.suspended
    createdTime = task.createTime
    dueTime = task.dueDate
    priority = task.priority
    executionId = task.executionId
    processInstanceId = task.processInstanceId
  }

}
