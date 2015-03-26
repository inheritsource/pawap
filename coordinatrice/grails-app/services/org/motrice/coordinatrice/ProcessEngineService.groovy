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

import org.activiti.bpmn.model.BusinessRuleTask
import org.activiti.bpmn.model.ManualTask
import org.activiti.bpmn.model.ReceiveTask
import org.activiti.bpmn.model.ScriptTask
import org.activiti.bpmn.model.SendTask
import org.activiti.bpmn.model.ServiceTask
import org.activiti.bpmn.model.Task
import org.activiti.bpmn.model.UserTask
import org.activiti.engine.ActivitiException
import org.activiti.engine.repository.Deployment
import org.activiti.engine.repository.DeploymentBuilder
import org.activiti.engine.repository.ProcessDefinition
import org.activiti.engine.runtime.Execution
import org.activiti.engine.runtime.ProcessInstance
import org.apache.commons.logging.LogFactory

import org.motrice.coordinatrice.pxd.PxdFormdefVer
import org.motrice.coordinatrice.pxd.PxdItem

/**
 * All interaction with the process engine except keeping a tab on
 * process definitions.
 */
class ProcessEngineService {
  // We don't manage these transactions
  static transactional = false

  // Injection magic
  def activityFormdefService
  def formMapService
  def procdefService

  // Activiti services, see resources.groovy
  def activitiRepositoryService
  def activitiRuntimeService
  def activitiTaskService

  private static final log = LogFactory.getLog(this)

  /**
   * Create a DeploymentBuilder and set its name to prepare for deployment
   * key if not null will be part of the deployment name
   */
  DeploymentBuilder createDeploymentBuilder(String key) {
    if (log.debugEnabled) log.debug "createDeploymentBuilder << ${key}"
    def tstamp = new Date().format('yyyyMMddHHmmss')
    if (key) tstamp = "${key}-${tstamp}"
    def deployment = activitiRepositoryService.createDeployment()
    deployment.name(tstamp)
    if (log.debugEnabled) log.debug "createDeploymentBuilder >> ${deployment}"
    return deployment
  }

  /**
   * Deploy from a resource map obtained from findProcessResource
   * Throws org.activiti.engine.ActivitiException on conflicts in the BPMN
   * xml definition.
   */
  Deployment deployFromResourceMap(Map resourceMap) {
    if (log.debugEnabled) log.debug "deployFromProcdef << ${resourceMap?.procdef}"
    def procdef = resourceMap.procdef
    def db = createDeploymentBuilder(procdef.key)
    if (procdef?.category) db.category(procdef.category.toString())
    def is = new ByteArrayInputStream(resourceMap.bytes)
    db.addInputStream(procdef.resourceName, is)
    def deployment = db.deploy()
    if (log.debugEnabled) log.debug "deployFromProcdef >> id: ${deployment?.id}, name: ${deployment?.name}"
    return deployment
  }

  /**
   * Find the diagram of a process definition with the given id.
   * Return a map containing the following entries:
   * bytes (byte[]) the contents of the diagram
   * ctype (String) content type
   * procdef (Procdef) the process definition or null if the process definition
   * was not found
   */
  Map findProcessDiagram(String id) {
    if (log.debugEnabled) log.debug "findProcessDiagram << ${id}"
    assert id
    def map = [procdef: null, ctype: null, bytes: null]
    def entity = activitiRepositoryService.getProcessDefinition(id)
    if (entity) {
      map.procdef = procdefService.createProcdef(entity)
      map.bytes = activitiRepositoryService.getProcessDiagram(id).bytes
      map.ctype = 'image/png'
    }
    if (log.debugEnabled) log.debug "findProcessDiagram >> [${map?.procdef}, ${map?.bytes?.size()}]"
    return map
  }

  /**
   * Find the resource that defines this process definition.
   * Return a map containing the following entries,
   * bytes (byte[]): the contents of the resource,
   * procdef (Procdef): the process definition or null if the process definition
   * was not found
   * ctype (String): content type
   * fname (String): file name adjusted to end with '.bpmn'
   */
  Map findProcessResource(String id, boolean fullProcdef) {
    if (log.debugEnabled) log.debug "findProcessResource << ${id}"
    assert id
    def map = [procdef: null, bytes: null]
    def entity = activitiRepositoryService.getProcessDefinition(id)
    if (entity) {
      map.procdef = fullProcdef? procdefService.createFullProcdef(entity) :
      procdefService.createProcdef(entity)
      map.bytes = activitiRepositoryService.
      getResourceAsStream(entity.deploymentId, entity.resourceName).bytes
      map.ctype = 'text/xml'
      map.fname = adjustBpmnFileName(map.procdef.resourceName)
    }

    if (log.debugEnabled) log.debug "findProcessResource >> [${map?.procdef}, ${map?.bytes?.size()}]"
    return map
  }

  Map findProcessResource(String id) {
    findProcessResource(id, false)
  }

  private String adjustBpmnFileName(String origName) {
    def name = stripExtension(origName, '.xml')
    name = stripExtension(name, '.bpmn20')
    return "${name}.bpmn"
  }

  private String stripExtension(String name, String extension) {
    def result = name
    def idx = name.lastIndexOf(extension)
    if (idx > 0) result = name[0..idx-1]
    return result
  }

  /**
   * Find an activity definition given its full id.
   */
  ActDef findActivityDefinition(ActDefId id) {
    if (log.debugEnabled) log.debug "findActivityDefinition << ${id}"
    def procdef = procdefService.findProcessDefinition(id.procId)
    def actDef = procdef.findActDef(id.actId)
    if (log.debugEnabled) log.debug "findActivityDefinition >> ${actDef?.toDisplay()}"
    return actDef
  }

  /**
   * Duplicate a process definition, creating a new version.
   * The process definition resource (BPMN xml) is re-deployed.
   * If the resource contains more than one process definition, all of them
   * will be duplicated to a new version.
   * id must be the id of the process definition to duplicate
   * Returns the duplicated process definitions (List of Procdef)
   * The duplicated process definitions are in state Edit.
   */
  List createNewProcdefVersionByDuplication(String id) {
    if (log.debugEnabled) log.debug "procdefVersionByDuplication << ${id}"
    def resourceMap = findProcessResource(id, true)
    def procdef = resourceMap.procdef
    if (!resourceMap.procdef) throw new ServiceException("Process definition ${id} not found",
							 'default.not.found.message', [id])
    def procdefList = null
    procdefList = deployAndReconnect(resourceMap)
    if (log.debugEnabled) log.debug "procdefVersionByDuplication >> ${procdefList}"
    return procdefList
  }

  private List deployAndReconnect(Map resourceMap) {
    def deployment = null
    try {
      deployment = deployFromResourceMap(resourceMap)
    } catch (ActivitiException exc) {
      def msg = exc.message
      throw new ServiceException(msg, 'procdef.xml.conflict', [msg])
    }

    def state = CrdProcdefState.get(CrdProcdefState.STATE_EDIT_ID)
    def procdefList = procdefService.findProcessDefinitionsFromDeployment(deployment.id, state)
    if (!procdefList) throw new ServiceException("Duplicated process definition not found",
						 'procdef.deployed.not.found')
    
    // Copy activity form connections from previous version
    if (resourceMap.procdef) procdefList.each {reconnectActivityForms(resourceMap.procdef, it)}
    return procdefList
  }

  /**
   * Make a best effort to connect activities to forms for a new process version.
   * Find the previous version and copy the connections.
   * origProcdef should be the original process definition that was duplicated,
   * procdef should be the newly deployed copy.
   */
  private reconnectActivityForms(Procdef origProcdef, Procdef procdef) {
    def procdefList = procdefService.findProcessDefinitionsFromKey(procdef.key)
    if (log.debugEnabled) log.debug "reconnectActivityForms: ${procdefList.size()}"
    // The two first elements should be the new and previous versions.
    // No guarantee though.
    if (procdefList.size() >= 2) {
      activityFormdefService.connectActivityForms(origProcdef, procdefList[1], procdefList[0])
    }
  }

  /**
   * Update a process definition by uploading a new BPMN model.
   */
  List createNewProcdefVersionByUpdate(Procdef origProc, InputStream is) {
    if (log.debugEnabled) log.debug "procdefVersionByUpdate << ${origProc?.uuid}"
    def resourceMap = [procdef: origProc, bytes: is.bytes, ctype: 'text/xml']
    def procdefList = deployAndReconnect(resourceMap)
    if (log.debugEnabled) log.debug "procdefVersionByUpdate >> ${procdefList}"
    return procdefList
  }

  /**
   * Create a process definition from scratch by uploading a new BPMN model.
   */
  List createProcdefFromScratch(UploadBpmnCommand cmd) {
    if (log.debugEnabled) log.debug "procdefVersionFromScratch << ${cmd}"
    def db = createDeploymentBuilder(cmd.deploymentName)
    db.category(cmd.crdProcCategory.toString())
    db.addInputStream(cmd.fileName, cmd.inputStream)
    def deployment = null
    try {
      deployment = db.deploy()
    } catch (ActivitiException exc) {
      def msg = exc.message
      def sb = new StringBuilder(msg)
      sb.append(' [')
      def excp = exc.cause
      def sep = ''
      while (excp) {
	sb.append(sep).append(excp.message)
	sep = '||'
	excp = excp.cause
      }
      sb.append(']')
      throw new ServiceException(msg, 'procdef.xml.conflict', [sb.toString()])
    }
    def editState = CrdProcdefState.get(CrdProcdefState.STATE_EDIT_ID)
    def procdefList = procdefService.findProcessDefinitionsFromDeployment(deployment.id, editState)
    if (!procdefList) throw new ServiceException("Created process definition not found",
						 'procdef.deployed.not.found')
    if (log.debugEnabled) log.debug "procdefVersionFromScratch >> ${procdefList}"
    return procdefList
  }

  /**
   * Start a process instance and add a comment, if provided.
   * SIDE EFFECT: Fills in properties in the Procinst object from the new
   * Activiti process instance.
   */
  Procinst startProcessInstance(Procinst procInst, String commentText) {
    if (log.debugEnabled) log.debug "startProcessInstance << ${procInst}"
    try {
      def activitiInst = activitiRuntimeService.
      startProcessInstanceById(procInst.procdef.uuid, procInst.variables)
      procInst.assignFromExecution(activitiInst)
      if (commentText) {
	def comment = activitiTaskService.addComment(null, procInst.uuid, commentText)
	if (log.debugEnabled) log.debug "startProcessInstance COMMENT id: ${comment?.id}"
      }
    } catch (ActivitiException exc) {
      throw new ServiceException("Problem starting process instance: ${exc}",
				 'procinst.start.failure', [exc.message])
    }
    if (log.debugEnabled) log.debug "startProcessInstance >> ${procInst}"
    return procInst
  }

  Procinst findProcessInstance(String id) {
    if (log.debugEnabled) log.debug "findProcessInstance << ${id}"
    def result = null
    if (id) {
      def pi = activitiRuntimeService.createProcessInstanceQuery().
      processInstanceId(id).singleResult()
      result = pi? createProcinst(pi) : null
    }
    if (log.debugEnabled) log.debug "findProcessInstance >> ${result}"
    return result
  }

  Procinst findProcessInstanceWithVars(String id) {
    if (log.debugEnabled) log.debug "findProcessInstanceWithVars << ${id}"
    def result = null
    if (id) {
      def pi = activitiRuntimeService.createProcessInstanceQuery().
      includeProcessVariables().processInstanceId(id).singleResult()
      result = pi? createProcinst(pi) : null
    }
    if (log.debugEnabled) log.debug "findProcessInstanceWithVars >> ${result}"
    return result
  }

  List listProcessInstances(String processDefinitionId) {
    if (log.debugEnabled) log.debug "listProcessInstances <<"
    def query = activitiRuntimeService.createProcessInstanceQuery()
    if (processDefinitionId) {
      query = query.processDefinitionId(processDefinitionId)
    }
    def list = query.orderByProcessDefinitionId().asc().list()
    def result = list.collect {pi ->
      return createProcinst(pi)
    }

    if (log.debugEnabled) log.debug "listProcessInstances >> ${result}"
    return result
  }

  List listProcessInstances() {
    listProcessInstances(null)
  }

  def deleteProcessInstance(String uuid, String reasonText) {
    if (log.debugEnabled) log.debug "deleteProcessInstance << ${uuid}, ${reasonText}"
    try {
      activitiRuntimeService.deleteProcessInstance(uuid, reasonText)
    } catch (ActivitiException exc) {
      throw new ServiceException("Problem deleting process instance ${uuid}: ${exc}",
				 'procinst.deletion.conflict', [uuid, exc.message])
    }
    if (log.debugEnabled) log.debug "deleteProcessInstance >>"
  }

  List findExecutionsByPi(String processInstanceId) {
    if (log.debugEnabled) log.debug "findExecutionsByPi << ${processInstanceId}"
    def list = activitiRuntimeService.createExecutionQuery().
    processInstanceId(processInstanceId).list()
    def result = list.collect {ex ->
      return createExecution(ex)
    }
    if (log.debugEnabled) log.debug "findExecutionsByPi >> ${result.size()}"
    return result
  }

  /**
   * Find an execution by its id.
   */
  BpmnExecution findExecution(String id) {
    if (log.debugEnabled) log.debug "findExecution << ${id}"
    def exec = activitiRuntimeService.createExecutionQuery().
    executionId(id).singleResult()
    def result = exec? createExecution(exec) : null
    if (log.debugEnabled) log.debug "findExecution >> ${result}"
    return result
  }

  /**
   * Find all tasks given a  process instance id.
   */
  List findTasksByPi(String processInstanceId) {
    if (log.debugEnabled) log.debug "findTasksByPi << ${processInstanceId}"
    def list = activitiTaskService.createTaskQuery().
    processInstanceId(processInstanceId).orderByTaskName().asc().list()
    def result = list.collect {task ->
      return createTask(task)
    }
    if (log.debugEnabled) log.debug "findTasksByPi >> ${result.size()}"
    return result
  }

  /**
   * Find a task by its id.
   * Return the task or null.
   */
  BpmnTask findTask(String id) {
    if (log.debugEnabled) log.debug "findTask << ${id}"
    def task = activitiTaskService.createTaskQuery().taskId(id).singleResult()
    def result = task? createTask(task) : null
    if (log.debugEnabled) log.debug "findTask >> ${result}"
    return result
  }

  /**
   * Send a signal to an execution.
   */
  def signalExecution(BpmnExecution exec) {
    if (log.debugEnabled) log.debug "signalExecution << ${exec}"
    def uuid = exec.uuid

    try {
      activitiRuntimeService.signal(exec.uuid)
    } catch (ActivitiException exc) {
      throw new ServiceException("Problem signalling execution ${uuid}",
				 'bpmnExecution.signal.conflict', [uuid, exc.message])
    } catch (NullPointerException exc) {
      throw new ServiceException("Problem signalling execution ${uuid}",
				 'bpmnExecution.signal.conflict', [uuid, 'Internal conflict'])
    }

    if (log.debugEnabled) log.debug "signalExecution >>"
  }

  /**
   * Check parameters before completing a task.
   * This step prepares for input.
   * taskId must be the uuid of a task.
   */
  BpmnTask taskCompletePrepare(String taskId) {
    if (log.debugEnabled) log.debug "taskCompletePrepare << ${taskId}"
    def bpmnTask = findTask(taskId)
    if (!bpmnTask) throw new ServiceException("Task ${taskId} not found",
					  'default.not.found.message', [taskId])
    def procdefId = bpmnTask.procdefId
    def mafd = MtfActivityFormDefinition.findByProcdefIdAndActdefId(procdefId, bpmnTask.definitionKey)
    if (!mafd) {
      String msg = "Activity form def (${procdefId}, ${bpmnTask.definitionKey}) not found"
      throw new ServiceException(msg, 'bpmnTask.form.not.found.msg', [taskId])
    }

    bpmnTask.mafd = mafd
    bpmnTask.formdef = PxdFormdefVer.get(mafd.formdefId)
    if (log.debugEnabled) log.debug "taskCompletePrepare >> ${bpmnTask}"
    return bpmnTask
  }

  /**
   * Complete a task after setting appropriate process variables.
   * RETURN the process instance id.
   */
  String taskCompleteFinish(BpmnTaskCompleteCommand cmd) {
    if (log.debugEnabled) log.debug "taskCompleteFinish << ${cmd}"
    def forminst = PxdItem.findByPath(cmd.formDataPath)
    if (!forminst) {
      def msg = "Form instance not found with path ${cmd.formDataPath}"
      throw new ServiceException(msg, 'bpmnTask.form.data.not.found', [cmd.formDataPath])
    }

    def bpmnTask = findTask(cmd.id)
    if (!bpmnTask) throw new ServiceException("Task ${cmd.id} not found",
					  'default.not.found.message', [cmd.id])

    def procInstId = bpmnTask.processInstanceId
    def formConnection = MtfActivityFormDefinition.get(cmd.formConnection)
    if (!formConnection) {
      def msg = "Activity form definition not found with id ${cmd.formConnection}"
      throw new ServiceException(msg, 'mtfActivityFormDefinition.not.found', [cmd.formConnection])
    }

    // Process instance variables from form data
    def map = formMapService.createVariableMap(cmd.formDef, forminst)

    // Add Motrice variables
    map.motriceFormTypeId = formConnection.formHandlerType.id
    map.motriceFormDefinitionKey = formConnection.formConnectionKey
    map.motriceFormInstanceId = forminst.path
    bpmnTask.variables = map
    activitiRuntimeService.setVariables(bpmnTask.executionId, map)

    // Add a comment to the task
    if (cmd.commentText) {
      def comment = activitiTaskService.addComment(bpmnTask.uuid, null, cmd.commentText)
      if (log.debugEnabled) log.debug "taskCompleteFinish COMMENT id: ${comment?.id}"
    }

    // Complete the task
    try {
      activitiTaskService.complete(bpmnTask.uuid)
    } catch (ActivitiException exc) {
      def uuid = bpmnTask.uuid
      throw new ServiceException("Problem completing task ${uuid}",
				 'bpmnTask.completion.conflict', [uuid, exc.message])
    }

    if (log.debugEnabled) log.debug "taskCompleteFinish >> ${procInstId}"
    return procInstId
  }

  /**
   * Update a task given user input.
   * Return the updated task.
   */
  BpmnTask updateTask(BpmnTaskCommand cmd) {
    if (log.debugEnabled) log.debug "updateTask << ${cmd}"
    def uuid = cmd.id
    def task = findTask(uuid)
    if (!task) throw new ServiceException("Task ${uuid} not found",
					  'default.not.found.message', [uuid])
    if (cmd.comment) {
      def comment = activitiTaskService.addComment(uuid, null, cmd.comment)
      if (log.debugEnabled) log.debug "updateTask added COMMENT ${comment?.id} "
    }
    task.activitiTask.assignee = cmd.assignee
    task.activitiTask.dueDate = cmd.dueTime
    task.activitiTask.description = cmd.description
    task.activitiTask.priority = cmd.priority
    task.assignFromTask()
    activitiTaskService.saveTask(task.activitiTask)
    if (log.debugEnabled) log.debug "updateTask >> ${task}"
    return task
  }

  private Procinst createProcinst(ProcessInstance pi) {
    def procDef = procdefService.findProcessDefinition(pi.processDefinitionId)
    def procInst = new Procinst(procdef: procDef, businessKey: pi.businessKey)
    procInst.assignFromExecution(pi)
    def model = activitiRepositoryService.getBpmnModel(pi.processDefinitionId)
    procInst.flowElement = model.getFlowElement(procInst.activityId)
    return procInst
  }

  private BpmnExecution createExecution(Execution ex) {
    def execInst = new BpmnExecution()
    execInst.assignFromExecution(ex)
    def model = activitiRepositoryService.getBpmnModel(ex.processDefinitionId)
    execInst.flowElement = model.getFlowElement(execInst.activityId)
    return execInst
  }

  private BpmnTask createTask(org.activiti.engine.task.Task task) {
    def taskInst = new BpmnTask()
    taskInst.assignFromTask(task)
    return taskInst
  }

}
