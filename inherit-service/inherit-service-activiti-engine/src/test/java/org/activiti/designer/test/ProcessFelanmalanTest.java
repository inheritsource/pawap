package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Attachment;
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
		// variableMap.put("startevent1_service_code",
		// "Vintervaghallning"); // automatic
		variableMap.put("startevent1_service_code", "Ovrigt"); // manual
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

		// make some update through fms
		// similar to
		// org.inheritsource.service.rest.server.services.RuntimeService
		// open311/v2/servicerequestupdates.{format}
		Map<String, Object> updateVariableMap = new HashMap<String, Object>();
		// message variables
		Map<String, Object> messageMap = new HashMap<String, Object>();

		for (int iter = 0; iter < 3; iter++) {

			String status = "";
			String description = "";
			if (iter < 2) {
				status = "OPEN";
				description = "fortfarande problem";
			} else {
				status = "CLOSED";
				//
				description = "Nu funkar det!";
			}
			String email = "foo.bar@";
			String last_name = "Bar " + iter;
			String first_name = "Foo";
			String title = "Tester";
			String media_url = "motrice.se";
			String account_id = "";

			updateVariableMap.clear();
			updateVariableMap.put("motriceOpen311UpdateStatus", status);
			updateVariableMap.put("motriceOpen311Updatedescription",
					description);
			updateVariableMap.put("motriceOpen311UpdateEmail", email);
			updateVariableMap.put("motriceOpen311UpdateLast_name", last_name);
			updateVariableMap.put("motriceOpen311UpdateFirst_name", first_name);
			updateVariableMap.put("motriceOpen311UpdateTitle", title);
			updateVariableMap.put("motriceOpen311UpdateMedia_url", media_url);
			updateVariableMap.put("motriceOpen311UpdateAccount_id", account_id);

			messageMap.put("fromOpen311Update", "fromOpen311Update");
			messageMap.put("messageSubjectOpen311Update",
					"messageSubjectOpen311Update");
			messageMap.put("messageTextOpen311Update",
					"messageTextOpen311Update");
			messageMap.put("recipientEmailOpen311Update=", "to@foo.bar");

			ExecutionQuery executionQuery = runtimeService
					.createExecutionQuery();
			String activityId = "open311update";
			executionQuery.processInstanceId(processInstance.getId())
					.activityId(activityId);

			Execution execution = executionQuery.singleResult();
			if (execution != null) {

				// update process

				// remove variables from the process in case of earlier
				// update
				Map<String, Object> variables = runtimeService
						.getVariables(execution.getId());
				System.out.println("variables.keySet() =" + variables.keySet());

				// do not remove variables before signal
				/*
				 * runtimeService.removeVariables(execution.getId(),
				 * updateVariableMap.keySet());
				 * runtimeService.removeVariables(execution.getId(),
				 * messageMap.keySet());
				 */
				variables = runtimeService.getVariables(execution.getId());
				System.out.println("after remove : variables.keySet() ="
						+ variables.keySet());
				// variables will be overwritten if more updates are
				// done
				// which is probably ok.

				// fill in orbeon form with update TODO
				// Might be tricky since the form is not related to user
				// task ??

				// send the signal to the process
				System.out.println("STATUS = " + status);
				runtimeService.signal(execution.getId(), updateVariableMap);

				if (!(status.equals("CLOSED"))) {

					variables = runtimeService.getVariables(execution.getId());
					System.out.println("after update  : variables.keySet() ="
							+ variables.keySet());
					String lastNameRead = (String) variables
							.get("motriceOpen311UpdateLast_name");
					System.out.println("lastNameRead = " + lastNameRead);
					System.out.println("messageTextOpen311Update ="
							+ variables.get("messageTextOpen311Update"));
					assertTrue(lastNameRead.equals(last_name));
					String messageTextOpen311Update = (String) variables
							.get("messageTextOpen311Update");
					String messageTextOpen311UpdateRef = "Uppdatering från fixamingata status="
							+ status;
					assertTrue(messageTextOpen311Update
							.equals(messageTextOpen311UpdateRef));
				}

				Task usertask = taskService.createTaskQuery()
						.taskDefinitionKey("handlaggare1").singleResult();
				if (usertask != null) {
				taskService.createAttachment("application/pdf",
						usertask.getId(), null, "attachmentName" + iter,
						"attachmentDescription", "http://motrice.se");
				}
			}
			Task usertask = taskService.createTaskQuery()
					.taskDefinitionKey("handlaggare1").singleResult();
			if (usertask != null) {
			List<Attachment> attachmentList = taskService
					.getTaskAttachments(usertask.getId());
			for (Attachment attachment : attachmentList) {
				System.out.println(attachment.getName());
				System.out.println(attachment.getDescription());
				System.out.println(attachment.getUrl());

			}
			}

		}

		// taskService.complete(task.getId());
		/*
		 * TaskQuery tq = taskService.createTaskQuery();
		 * 
		 * List<Task> taskList = tq.list(); for (Task t : taskList) {
		 * System.out.println("taskId= " + t.getId());
		 * taskService.complete(t.getId()); }
		 * 
		 * tq = taskService.createTaskQuery(); taskList = tq.list(); for (Task t
		 * : taskList) { System.out.println("taskId= " + t.getId());
		 * 
		 * }
		 */
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
		serviceCodes.add("");
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
				// variableMap.put("startevent1_service_code",
				// "Vintervaghallning"); // automatic
				variableMap.put("startevent1_service_code", serviceCode);
				// variableMap.put("startevent1_service_code", "Ovrigt"); //
				// manual
				// variableMap.put("startevent1_service_code",
				// "igensattbrunn@motrice.se");

				// variableMap.put("Handlggare_group", "one"); //
				// simplification,
				// should be
				// set in the registration
				// task
				variableMap.put("Handlggare_group", selection);
				// "one" : reject
				// "two" : elektriker / Registrator
				// "three" : Renhållnin / Assistent
				// "four" : VVs / Chef

				variableMap.put("Handlggare_comment", "Handläggarkommentar.");
				// simplification,	 should be set in the registration task

				ProcessInstance processInstance = runtimeService
						.startProcessInstanceByKey("felanmalan", variableMap);
				assertNotNull(processInstance.getId());
				taskService = activitiRule.getTaskService();

				Task task = taskService.createTaskQuery().singleResult();
				task.setAssignee(userAssignee);
				assertNotNull(task);
				assertNotNull(task.getAssignee());
				assertEquals(task.getAssignee(), userAssignee);

				System.out.println("Task id:  " + task.getId() + "name :"
						+ task.getName() + "process instance :"
						+ processInstance.getId() + " "
						+ processInstance.getProcessDefinitionId());

				taskService = activitiRule.getTaskService();
				taskService.complete(task.getId());

				// more tasks ??
				TaskQuery tq = taskService.createTaskQuery();

				List<Task> taskList = tq.list();

				System.out.println("selection = " + selection + "serviceCode ="
						+ serviceCode);

				if (selection.equals("one")) {
					// rejected no more tasks
					assertEquals(taskList.size(), 0);
				} else {
					if (serviceCode.equals("Vintervaghallning")
							|| serviceCode.equals("Gatubelysning")
							|| serviceCode.equals("IgensattBrunn")
							|| serviceCode.equals("AvfallOchAtervinning")) {
						// directly to utförare => no more tasks
						assertEquals(taskList.size(), 0);
					} else {
						// manually selected utförare
						assertEquals(taskList.size(), 1);
					}
				}
				for (Task t : taskList) {
					System.out.println("taskId= " + t.getId());
					taskService.complete(t.getId());
				}
			}
		}
	}
}
