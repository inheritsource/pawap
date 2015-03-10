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

package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.history.HistoricDetail;
import org.activiti.engine.history.HistoricVariableUpdate;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessScriptprocessTest {

	private String filename = "ScriptProcess.bpmn";
	private String processname = "scriptprocess";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule(
			"activiti.cfg-mem-fullhistory.xml");

	@Test
	public void teststartProcess() throws Exception {

		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource(filename)
				.name(processname).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();

		TaskService taskService = activitiRule.getTaskService();
		List<Long> currentGroupList = new ArrayList<Long>();
		//
		// run the process with a number of values of currentgroup
		//
		currentGroupList.add(5L);
		currentGroupList.add(3L);
		currentGroupList.add(1L);
		currentGroupList.add(4L);
		currentGroupList.add(6L);
		int nVariables = 0;
		for (long currentgroup : currentGroupList) {
			Map<String, Object> variableMap = new HashMap<String, Object>();
			variableMap.put("motrice_form_data_name", "Activiti");
			variableMap.put("startevent1_forename", "Fozzie");
			variableMap.put("startevent1_lastname", "The Bear");
			variableMap.put("startevent1_emailaddress", "fozzie@nowhere");
			variableMap.put("startevent1_phonenumber", "47114711");
			variableMap.put("startevent1_currentgroup_long", currentgroup);
			variableMap.put("startevent1_info", "test info");
			ProcessInstance processInstance = runtimeService
					.startProcessInstanceByKey("scriptprocess", variableMap);
			nVariables += variableMap.size();
			assertNotNull(processInstance.getId());
			System.out.println("id " + processInstance.getId() + " "
					+ processInstance.getProcessDefinitionId());

			//

			System.out.println("processInstance.getActivityId() ="
					+ processInstance.getActivityId());

			System.out.println("=======================================");
			System.out.println("== complete tasks======================");

			Map<String, Object> variableMap2 = processInstance
					.getProcessVariables();

			System.out.println("variableMap2 = " + variableMap2);

			List<Task> availableTaskList = taskService.createTaskQuery()
					.taskName("Add comment").list();

			for (Task task : availableTaskList) {
				System.out.println("Completing task with id :" + task.getId());
				taskService.setVariable(task.getId(),
						"motrice_form_data_comment", "Min kommentar");
				nVariables++;
				taskService.complete(task.getId());
			}
			nVariables++; // myVar is set in the script so more variable is
							// created
			System.out.println("=======================================");
			//
		}
		List<HistoricDetail> historyVariables = activitiRule
				.getHistoryService().createHistoricDetailQuery()
				.variableUpdates().orderByVariableName().asc().list();
		for (HistoricDetail mHistoricDetail : historyVariables) {
			System.out.println(": " + mHistoricDetail.getActivityInstanceId()
					+ ": " + mHistoricDetail.getExecutionId() + ": "
					+ mHistoricDetail.getId() + ": "
					+ mHistoricDetail.getProcessInstanceId() + ": "
					+ mHistoricDetail.getTaskId() + ": "
					+ mHistoricDetail.getTime());
		}
		assertNotNull(historyVariables);
		assertEquals(nVariables, historyVariables.size());
		// HistoricVariableUpdate update0 = ((HistoricVariableUpdate)
		// historyVariables
		// .get(5));
		// assertEquals("myVar", update0.getVariableName());
		// LoanApplication la = (LoanApplication) loanAppUpdate.getValue();
		// assertEquals(true, la.isCreditCheckOk());
		HistoricVariableUpdate update;
		System.out.println("=======================================");
		int iter = 0;
		for (int index = 0; index < historyVariables.size(); index++) {
			update = ((HistoricVariableUpdate) historyVariables.get(index));

			System.out.println(update.getVariableName() + " : "
					+ update.getValue());

			// this checks that the assignment based on the process was correct
			// might be nicer way to do this
			if (update.getVariableName().equals("myVar")) {
				String myVar = (String) update.getValue();
				System.out.println("checking group");

				if (currentGroupList.get(iter) >= 5) {
					assertEquals(myVar, "test456");
				} else {
					assertEquals(myVar, "test789");
				}
				iter++;
			}

		}
	}
}
