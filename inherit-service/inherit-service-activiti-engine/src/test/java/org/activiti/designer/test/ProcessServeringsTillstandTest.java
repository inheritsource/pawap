package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.activiti.engine.test.ActivitiRule;

import org.junit.Rule;
import org.junit.Test;

public class ProcessServeringsTillstandTest {

	private String filename = "ServeringsTillstand.bpmn";
	private String processname = "serveringstillstand";
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	private TaskService taskService;
	private String userAssignee = "admin";

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource(filename)
				.name(processname).deploy();
		
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		variableMap.put("motriceStartFormAssignee", "5b257b18-01e6-4962-b356-0fc926c21175" ) ; 
		variableMap.put("motrice_form_data_name", "Activiti");
		variableMap.put("startevent1_bolagstyp", "three");
	//	variableMap.put("Registrering_applicationstatus", "two");  // simplification, should be set in the registration task
		variableMap.put("Registrering_applicationstatus", "one");  // simplification, should be set in the registration task
	// "one" => quits process	
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("serveringsTillstand", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		
		taskService = activitiRule.getTaskService() ; 
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		Task task = taskService.createTaskQuery().singleResult();
		task.setAssignee(userAssignee);
		assertNotNull(task);
		assertNotNull(task.getAssignee());
		assertEquals(task.getAssignee(), userAssignee);
		
		taskService.complete(task.getId());
		
		TaskQuery tq = taskService.createTaskQuery() ;  
	
		List<Task> taskList = tq.list() ; 
		for( Task t : taskList){
			System.out.println("taskId= " + t.getId()); 
			taskService.complete(t.getId()) ; 
		}
		
		
		
		tq = taskService.createTaskQuery() ; 
		taskList = tq.list() ; 
		for( Task t : taskList){
			System.out.println("taskId= " + t.getId()); 
			
		}
		String regex ="[^a-zA-Z0-9_]"; 
		String test ="1234-45600345_/"; 
		String testFilt = test.replaceAll("-","_").replaceAll(regex, "") ; 
		System.out.println("test = " + test ) ; 
		System.out.println("testFilt= " + testFilt ) ; 
	}
}
