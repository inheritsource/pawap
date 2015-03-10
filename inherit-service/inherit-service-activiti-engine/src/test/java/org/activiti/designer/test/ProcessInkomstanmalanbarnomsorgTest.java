package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;

import org.junit.Rule;
import org.junit.Test;

public class ProcessTestInkomstanmalanbarnomsorg {

	private String filename = "/home/tostman/workspaces/inheritsource-develop/pawap/inherit-service/inherit-service-activiti-engine/src/main/resources/InkomstAnmalanBarnomsorg.bpmn";

	
	
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	private TaskService taskService;
	private String userAssignee = "admin";

	@Test
	public void startProcess() throws Exception {
		System.out.println("hello cruel world!") ;
		
		
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		System.out.println("godbye cruel world3 !") ; 
		repositoryService
				.createDeployment()
				.addInputStream("inkomstanmalanbarnomsorg.bpmn20.xml",
						new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		variableMap.put("motriceStartFormAssignee", "5b257b18-01e6-4962-b356-0fc926c21175" ) ; //  "195204151517");
		//variableMap.put("motriceStartFormAssignee","66cc0e73-8b16-4579-a295-fc37dfa772b9" ) ; // "199502023782");
	/*
		variableMap.put("startevent1_control_53", "test.ostsson@motrice.se");
		variableMap.put("startevent1_control_17", "195204151517");
		variableMap.put("startevent1_control_56", "199502023782");
		variableMap.put("startevent1_control_62", "test.damberg@motrice.se");
		*/
		variableMap.put("startevent1_control_53", "test.damberg@motrice.se");
		variableMap.put("startevent1_control_17", "199502023782");
		variableMap.put("startevent1_control_56", "195204151517");
		variableMap.put("startevent1_control_62", "test.ostsson@motrice.se");	
		
		
		// Ensam vårdnadshavare 
		variableMap.put("startevent1_control_67", "false");
		//variableMap.put("startevent1_control_67", "true");
		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("inkomstanmalan_barnomsorg",
						variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		taskService = activitiRule.getTaskService();
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
		Task task = taskService.createTaskQuery().singleResult();
		task.setAssignee(userAssignee);
		assertNotNull(task);
		assertNotNull(task.getAssignee());
		assertEquals(task.getAssignee(), userAssignee);

		taskService.complete(task.getId());

		TaskQuery tq = taskService.createTaskQuery();

		List<Task> taskList = tq.list();
		for (Task t : taskList) {
			System.out.println("taskId= " + t.getId());
			taskService.complete(t.getId());
		}

		tq = taskService.createTaskQuery();
		taskList = tq.list();
		for (Task t : taskList) {
			System.out.println("taskId= " + t.getId());

		}

	}
}
