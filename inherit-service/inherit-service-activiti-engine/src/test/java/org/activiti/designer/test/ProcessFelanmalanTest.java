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

public class ProcessFelanmalanTest {

	private String filename = "Felanmalan.bpmn";
	private String processname = "felanmalan";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	private TaskService taskService;
	private String userAssignee = "admin";

	@Test
	public void teststartProcess() throws Exception {

		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource(filename)
				.name(processname).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();

		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		variableMap.put("motriceStartFormAssignee",
				"5b257b18-01e6-4962-b356-0fc926c21175");
		variableMap.put("startevent1_first_name", "John");
		variableMap.put("startevent1_last_name", "Doe");

		// variableMap.put("startevent1_phonenumber", "12345678");
		variableMap.put("startevent1_email", "email2@se.se");
		variableMap.put("startevent1_phone", "1234");
		variableMap.put("startevent1_address_string", "Gnuvägen 1");
		variableMap.put("startevent1_address_id", "address_id");
		variableMap.put("startevent1_jurisdiction_id", "Nacka");
		variableMap.put("startevent1_description", "description");
		variableMap.put("startevent1_device_id", "device_id");
		variableMap.put("startevent1_account_id", "account_id");
		variableMap.put("startevent1_media_url", "media_url");
		variableMap.put("startevent1_lat", "0.0");
		variableMap.put("startevent1_lon", "0.0");
		variableMap.put("startevent1_service_code",
				"vintervaghallning@motrice.se");
		// variableMap.put("startevent1_service_code",
		// "avfallochatervinning@motrice.se");
		// variableMap.put("startevent1_service_code",
		// "igensattbrunn@motrice.se");

		variableMap.put("Handlggare_group", "two"); // simplification, should be
													// set in the registration
													// task
		variableMap.put("Handlggare_comment", "Handläggarkommentar."); // simplification,
																		// should
																		// be
																		// set
																		// in
																		// the
																		// registration
																		// task

		ProcessInstance processInstance = runtimeService
				.startProcessInstanceByKey("felanmalan", variableMap);
		assertNotNull(processInstance.getId());
		taskService = activitiRule.getTaskService();
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
