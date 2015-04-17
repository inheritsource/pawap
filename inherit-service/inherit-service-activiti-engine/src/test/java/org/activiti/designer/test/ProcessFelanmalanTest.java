package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class ProcessFelanmalanTest {

	private String filename = "Felanmalan.bpmn";
	private String processname = "felanmalan";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();
	private TaskService taskService;
	private String userAssignee = "admin";

	@Ignore
	@Test
	// close from open311 update
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

		variableMap.put("felanmalan_email", "email2@se.se");

		variableMap.put("felanmalan_service_code", "Ovrigt"); // manual

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

		// make some update through fms
		// similar to
		// org.inheritsource.service.rest.server.services.RuntimeService
		// open311/v2/servicerequestupdates.{format}

		for (int iter = 0; iter < 3; iter++) {

			String status = "";
			if (iter < 2) {
				status = "OPEN";
			} else {
				status = "CLOSED";
			}

			Task usertask = taskService.createTaskQuery()
					.taskDefinitionKey("open311update").singleResult();
			assertNotNull(usertask);

			taskService.setVariable(usertask.getId(),
					"motriceOpen311UpdateStatus", status);

			taskService.complete(usertask.getId());

			// loop
			usertask = taskService.createTaskQuery()
					.taskDefinitionKey("open311update").singleResult();
			if (status.equals("CLOSED")) {
				assertNull(usertask);
			} else {
				assertNotNull(usertask);
			}

		}

	}

	@Ignore
	@Test
	// closed from handläggare
	public void teststartProcess2() throws Exception {
		// iterate over the selection made by the handläggare
		ArrayList<String> serviceSelection = new ArrayList<String>();
		serviceSelection.add("one");
		serviceSelection.add("two");
		serviceSelection.add("three");
		serviceSelection.add("four");

		// iterate over service codes
		ArrayList<String> serviceCodes = new ArrayList<String>();
		// serviceCodes.add("");
		serviceCodes.add("AvfallOchAtervinning");
		serviceCodes.add("Badplatser");
		serviceCodes.add("Cykelstall");
		serviceCodes.add("Gator");
		serviceCodes.add("Gatubelysning");
		serviceCodes.add("GangOchCykelbana");
		serviceCodes.add("Hundrastgardar");
		serviceCodes.add("Hallplats");
		serviceCodes.add("IgensattBrunn");
		serviceCodes.add("Klotter");
		serviceCodes.add("Lekplatser");
		serviceCodes.add("Nedskrapning");
		serviceCodes.add("NedskrapningGator");
		serviceCodes.add("NedskrapningPark");
		serviceCodes.add("OffentligToalett");
		serviceCodes.add("Park");
		serviceCodes.add("ParkGronyta");
		serviceCodes.add("Parkering");
		serviceCodes.add("Trafiksignaler");
		serviceCodes.add("TradOchBuskage");
		serviceCodes.add("VattenOchAvlopp");
	    serviceCodes.add("Vintervaghallning");
		serviceCodes.add("Vagar");
		serviceCodes.add("VagmarkenOchSkyltar");
		serviceCodes.add("Ovrigt");

		for (String serviceCode : serviceCodes) {
			for (String selection : serviceSelection) {
				RepositoryService repositoryService = activitiRule
						.getRepositoryService();
				repositoryService.createDeployment()
						.addClasspathResource(filename).name(processname)
						.deploy();
				RuntimeService runtimeService = activitiRule
						.getRuntimeService();

				Map<String, Object> variableMap = new HashMap<String, Object>();
				variableMap.put("name", "Activiti");
				variableMap.put("motriceStartFormAssignee",
						"5b257b18-01e6-4962-b356-0fc926c21175");
				variableMap.put("felanmalan_email", "email2@se.se");

				variableMap.put("felanmalan_service_code",   serviceCode); // manual

				variableMap.put("Handlggare_group", "two"); // simplification,
															// should be
															// set in the
															// registration
															// task
				variableMap.put("Handlggare_comment", "Handläggarkommentar."); // simplification,
																				// should
																				// be
																				// set
																				// in
																				// the
																				// registration
																				// task

				variableMap.put("Handlggare_group", selection);
				// "one" : reject
				// "two" : elektriker / Registrator
				// "three" : Renhållnin / Assistent
				// "four" : VVs / Chef

				variableMap.put("Handlggare_comment", "Handläggarkommentar.");
				// simplification, should be set in the registration task

				ProcessInstance processInstance = runtimeService
						.startProcessInstanceByKey("felanmalan", variableMap);
				assertNotNull(processInstance.getId());
				taskService = activitiRule.getTaskService();

				Task task = taskService.createTaskQuery()
						.taskDefinitionKey("handlaggare").singleResult();
				System.out.println("selection = " + selection
						+ " serviceCode =" + serviceCode);

				if (noHandlaggare(serviceCode)) {
					assertNull(task);
				} else {
					assertNotNull(task);
					task.setAssignee(userAssignee);
					assertNotNull(task.getAssignee());
					assertEquals(task.getAssignee(), userAssignee);

					System.out.println("Task id:  " + task.getId() + "name :"
							+ task.getName() + "process instance :"
							+ processInstance.getId() + " "
							+ processInstance.getProcessDefinitionId());

					taskService = activitiRule.getTaskService();
					// TODO
					task = taskService.createTaskQuery()
							.taskDefinitionKey("handlaggare").singleResult();
					try {
						if (task != null) {
							taskService.complete(task.getId());
						}
					} catch (Exception e) {
						System.out.println("Exception e" + e);
					}
				}

				// more tasks ??
				// TaskQuery tq = taskService.createTaskQuery().processDefinitionId(processInstance.getId());
				 TaskQuery tq = taskService.createTaskQuery()  ; 
				List<Task> taskList = tq.list();
				for (Task t : taskList) {
					System.out.println("taskId= " + t.getId());
					System.out.println("task name : " + t.getName());
					System.out.println("task TaskDefinitionKey : " +  t.getTaskDefinitionKey()  ) ; 
				}
				if (selection.equals("one") && !(noHandlaggare(serviceCode))) {
					// rejected no more tasks
					// TODO assertEquals(0, taskList.size());
					taskList = tq.list();
					System.out.println("rejected : taskList.size() = "
							+ taskList.size());
					

				} else {
					if (noHandlaggare(serviceCode)) {
						// directly to utförare =>
						// only the update task
						assertEquals(2, taskList.size());
					} else {
						// manually selected utförare + update
						assertEquals(2, taskList.size());
					}

					//task = taskService.createTaskQuery().processDefinitionId(processInstance.getId())
					//		.taskDefinitionKey("utforare").singleResult();
					task = taskService.createTaskQuery().taskDefinitionKey("utforare").singleResult();
					
					assertNotNull(task);
					taskService.complete(task.getId());
				}

				// more tasks ??
				taskList = tq.list();
				System.out.println("taskList.size() = " + taskList.size());
				assertEquals(0, taskList.size());

			}
		}
	}

	boolean noHandlaggare(String serviceCode) {
		return (serviceCode.equals("Vintervaghallning")
				|| serviceCode.equals("Gatubelysning")
				|| serviceCode.equals("IgensattBrunn") || serviceCode
					.equals("AvfallOchAtervinning"));
	}
}
