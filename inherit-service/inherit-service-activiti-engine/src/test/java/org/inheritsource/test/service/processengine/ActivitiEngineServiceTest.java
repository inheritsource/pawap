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

package org.inheritsource.test.service.processengine;

import java.util.Locale;
import java.util.List;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.inheritsource.service.processengine.ActivitiEngineService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.inheritsource.service.common.domain.Open311v2p1ServiceRequestUpdates;
import org.inheritsource.service.common.domain.PagedProcessInstanceSearchResult;
import org.inheritsource.service.common.domain.UserInfo;
import org.inheritsource.service.processengine.ActivitiEngineUtil;

public class ActivitiEngineServiceTest {

	ActivitiEngineService activitiEngineService = null;

	@Before
	public void before() {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"/applicationContext.xml");

		activitiEngineService = (ActivitiEngineService) applicationContext
				.getBean("activitiEngineService");
	}

	@After
	public void after() {
		activitiEngineService.close();
	}

	@Ignore
	@Test
	public void testRemoveProcess() {
		System.out.println("testRemoveProcess()");

		activitiEngineService.getDeployedDeploymentIds();
		List<String> deployedDeploymentIds = activitiEngineService
				.getDeployedDeploymentIds();

		for (String id : deployedDeploymentIds) {
			System.out.println("id = " + id);
		}

		// boolean cascade = true ;
		// String testid = "1701" ;
		// activitiEngineService.deleteDeploymentByDeploymentId(testid ,
		// cascade) ;

		ActivitiEngineUtil.listDeployedProcesses(activitiEngineService
				.getEngine());
		// activitiEngineService.deleteDeploymentByDeploymentId(deploymentId,
		// cascade);
		System.out.println("testRemoveProcess() done");
	}

	@Test
	public void testProcessInstanceDetails() {

		// String searchForUserId = "1";
		String fromIndexStr = "0"; // start from element
		String pageSizeStr = "10"; // nr of element per page
		String sortBy = "started"; //
		// String sortOrder = "desc"; // or "asc"
		// String sortOrder = "asc" ;
		String sortOrder = "desc";
		// String filter = "STARTED" ; // "STARTED"; // or "FINISHED"
		String filter = "FINISHED";
		// maybe constant instead ???

		// String filter = "FINISHED" ;
		// String userIdRaw = "john";
		String userIdRaw = "ranel@inherit.se";
		// String userIdRaw = "197203732396";
		// String userId = "john" ;

		String startedByUserId = searchForBonitaUser(userIdRaw);
		String userId = "john";

		// startedByUserId = null;
		// userId = null;

		// String userId = "561e2ebc-4501-4a29-b6de-53749c723db1" ;

		int fromIndex = Integer.parseInt(fromIndexStr);
		int pageSize = Integer.parseInt(pageSizeStr);

		Locale locale = new Locale("sv", "SE");
		System.out.println("testProcessInstanceDetails: "
				+ activitiEngineService.getProcessInstanceDetails("12801",
						locale));

		int tolDate = 25;
		String involvedUserId;
		involvedUserId = startedByUserId;
		involvedUserId = userId;
		startedByUserId = null;
		startedByUserId = "admin";
		involvedUserId = "john";
		involvedUserId = null;
		// involvedUserId = "ranel@inherit.se";
		userId = "admin";
		userId = null;
		String sourceDate = "2015-02-01T00:00:00Z";
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(sourceDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//open311
		//String sourceStartDate = "2015-01-01T00:00:00Z";
		String sourceStartDate = "2015-02-19T00:00:00Z";

		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");
		Date start_date = null;
		try {
			start_date = dateformat.parse(sourceStartDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String sourceEndDate = "2015-04-01T00:00:00Z";
		//SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date end_date = null;
		try {
			end_date = dateformat.parse(sourceEndDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		filter = "ALL";
		filter = "STARTED";
		int mycase = 4;
		PagedProcessInstanceSearchResult result = null;
		Open311v2p1ServiceRequestUpdates open311v2p1ServiceRequestUpdates = null ; 
		switch (mycase) {

		case 0:
			// komin advanced search
			result = activitiEngineService.getProcessInstancesAdvanced(
					startedByUserId, involvedUserId, fromIndex, pageSize,
					sortBy, sortOrder, filter, locale, userId, date, tolDate);
			break;

		case 1:
			// result =
			// activitiEngineService.getProcessInstancesByUuids(processInstanceIds,
			// fromIndex, pageSize, sortBy, sortOrder, filter, locale,
			// startedByUserId) ;
			// break;

		case 2:
			result = activitiEngineService.getProcessInstancesWithInvolvedUser(
					startedByUserId, fromIndex, pageSize, sortBy, sortOrder,
					filter, locale, userId);

			break;

		case 3:
			// komin simple search
			result = activitiEngineService.getProcessInstancesStartedBy(
					startedByUserId, fromIndex, pageSize, sortBy, sortOrder,
					filter, locale, userId);

			break;

		case 4:
			// for open311
			String jurisdiction_id = "jurisdiction_id";
			String processLike = "felanmalan/felanmalan--v%" ;  
			open311v2p1ServiceRequestUpdates = activitiEngineService
					.getProcessInstancesOpen311(
							 userId, jurisdiction_id, start_date,
							end_date, processLike );
			break;

		}

		if (mycase != 4) {

			System.out
					.println("result.getHits()         = " + result.getHits());
			System.out.println("result.getNumberOfHits() = "
					+ result.getNumberOfHits());
			System.out.println("result.getFromIndex()    = "
					+ result.getFromIndex());
			System.out.println("result.getPageSize()     = "
					+ result.getPageSize());
			System.out.println("result.getSortBy()       = "
					+ result.getSortBy());
			System.out.println("result.getSortOrder()    = "
					+ result.getSortOrder());
		} else {
			System.out.println("open311v2p1ServiceRequestUpdates =" + open311v2p1ServiceRequestUpdates);

		}

		// System.out.println("testProcessInstanceDetails: " +
		// activitiEngineService.getUserInbox(locale, "admin"));
	}

	private String searchForBonitaUser(String searchForUserId) {
		String searchForBonitaUser = searchForUserId;

		if (searchForBonitaUser != null) {
			searchForBonitaUser = searchForBonitaUser.trim();
		}
		UserInfo userInfo = activitiEngineService.getTaskFormDb()
				.getUserBySerial(searchForBonitaUser);
		if (userInfo != null) {
			searchForBonitaUser = userInfo.getUuid();
		}

		return searchForBonitaUser;
	}

	/*
	 * @Test public void testCase1() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/TestFunctionProcess1.bpmn20.xml");
	 * activitiEngineService.startProcessInstanceByKey("TestFunctionProcess1",
	 * userId);
	 * 
	 * // Get the inbox and verify some data List<InboxTaskItem> inbox =
	 * activitiEngineService.getUserInbox("admin"); Assert.assertNotNull(inbox);
	 * Assert.assertEquals(inbox.size(), 1);
	 * Assert.assertEquals(inbox.get(0).getActivityLabel(), "Registrera");
	 * 
	 * // Get the inbox from another method and verify inboxes are equal
	 * Set<InboxTaskItem> inbox2 =
	 * activitiEngineService.getUserInboxByProcessInstanceId
	 * (inbox.get(0).getProcessInstanceUuid()); Assert.assertNotNull(inbox2);
	 * Assert.assertEquals(inbox2.size(), 1);
	 * Assert.assertEquals(inbox2.toArray()[0], inbox.get(0));
	 * 
	 * Set<InboxTaskItem> historicInbox =
	 * activitiEngineService.getHistoricUserInboxByProcessInstanceId
	 * (inbox.get(0).getProcessInstanceUuid());
	 * Assert.assertEquals(historicInbox.size(), 0);
	 * 
	 * activitiEngineService.executeTask(inbox.get(0).getTaskUuid(), "admin");
	 * historicInbox =
	 * activitiEngineService.getHistoricUserInboxByProcessInstanceId
	 * (inbox.get(0).getProcessInstanceUuid());
	 * Assert.assertNotNull(historicInbox);
	 * Assert.assertEquals(historicInbox.size(), 1);
	 * Assert.assertEquals(inbox.get(0), historicInbox.toArray()[0]); }
	 * 
	 * @Test public void testCase2() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/TestFunctionProcess1.bpmn20.xml");
	 * activitiEngineService.startProcessInstanceByKey("TestFunctionProcess1",
	 * userId);
	 * 
	 * // Get the inbox and verify some data List<InboxTaskItem> inbox =
	 * activitiEngineService.getUserInbox("admin"); Assert.assertNotNull(inbox);
	 * Assert.assertEquals(inbox.size(), 1);
	 * 
	 * String taskId = activitiEngineService.getActivityInstanceUuid
	 * (inbox.get(0).getProcessInstanceUuid(), inbox.get(0).getActivityLabel());
	 * Assert.assertEquals(taskId, inbox.get(0).getTaskUuid());
	 * 
	 * // One executeTask should make the task passed into history
	 * activitiEngineService.executeTask(inbox.get(0).getTaskUuid(), "admin");
	 * 
	 * String historicTaskId = activitiEngineService.getActivityInstanceUuid
	 * (inbox.get(0).getProcessInstanceUuid(), inbox.get(0).getActivityLabel());
	 * Assert.assertEquals(historicTaskId, taskId);
	 * 
	 * }
	 * 
	 * @Test public void testCase3() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/TestFunctionProcess1.bpmn20.xml");
	 * activitiEngineService.startProcessInstanceByKey("TestFunctionProcess1",
	 * userId);
	 * 
	 * // Get the inbox and verify some data List<InboxTaskItem> inbox =
	 * activitiEngineService.getUserInbox("admin"); Assert.assertNotNull(inbox);
	 * Assert.assertEquals(inbox.size(), 1);
	 * 
	 * String taskId = activitiEngineService.getActivityInstanceUuid
	 * (inbox.get(0).getProcessInstanceUuid(), inbox.get(0).getActivityLabel());
	 * Assert.assertEquals(taskId, inbox.get(0).getTaskUuid());
	 * 
	 * ActivityInstanceItem taskItem =
	 * activitiEngineService.getActivityInstanceItem
	 * (inbox.get(0).getTaskUuid());
	 * Assert.assertEquals(taskItem.getActivityLabel(),
	 * inbox.get(0).getActivityLabel());
	 * 
	 * // One executeTask should make the task passed into history
	 * activitiEngineService.executeTask(inbox.get(0).getTaskUuid(), "admin");
	 * 
	 * ActivityInstanceItem historicTaskItem =
	 * activitiEngineService.getActivityInstanceItem
	 * (inbox.get(0).getTaskUuid());
	 * Assert.assertEquals(historicTaskItem.getActivityLabel(),
	 * inbox.get(0).getActivityLabel()); }
	 * 
	 * @Test public void testCase4() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/TestFunctionProcess1.bpmn20.xml");
	 * activitiEngineService.startProcessInstanceByKey("TestFunctionProcess1",
	 * userId);
	 * 
	 * // Get the inbox and verify some data List<InboxTaskItem> inbox =
	 * activitiEngineService.getUserInbox("admin"); Assert.assertNotNull(inbox);
	 * Assert.assertEquals(inbox.size(), 1);
	 * 
	 * ActivityInstanceItem taskItem =
	 * activitiEngineService.getActivityInstanceItem
	 * (inbox.get(0).getTaskUuid());
	 * Assert.assertEquals(taskItem.getActivityLabel(),
	 * inbox.get(0).getActivityLabel());
	 * 
	 * // One executeTask should make the task passed into history
	 * activitiEngineService.executeTask(inbox.get(0).getTaskUuid(), "admin");
	 * 
	 * ActivityInstanceItem historicTaskItem =
	 * activitiEngineService.getActivityInstanceItem
	 * (inbox.get(0).getTaskUuid());
	 * Assert.assertEquals(historicTaskItem.getActivityLabel(),
	 * inbox.get(0).getActivityLabel()); }
	 * 
	 * // Test that a candidate user for a task is allowed to assign an
	 * unsassigned task
	 * 
	 * @Test public void testCase5() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/Arendeprocess.bpmn20.xml");
	 * activitiEngineService.startProcessInstanceByKey("Arendeprocess", userId);
	 * 
	 * List<InboxTaskItem> inbox = activitiEngineService.getUserInbox("admin");
	 * Assert.assertNotNull(inbox); Assert.assertNotNull(inbox.get(0)); String
	 * taskId = inbox.get(0).getTaskUuid(); ActivityWorkflowInfo aWFlowInfo =
	 * activitiEngineService.getActivityWorkflowInfo(taskId);
	 * 
	 * // verify the task is unassigned String assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * Assert.assertEquals("assignedUserId should be blank!", assignedUserId,
	 * "");
	 * 
	 * // get a candidate for the task Set<UserInfo> candidates =
	 * aWFlowInfo.getCandidates(); String candidateForTask = ""; if(candidates
	 * != null && candidates.size() > 0) { candidateForTask =
	 * ((UserInfo)(candidates.toArray()[0])).getUuid(); }
	 * Assert.assertNotEquals("candidateForTask should not be blank!",
	 * candidateForTask, ""); aWFlowInfo =
	 * activitiEngineService.assignTask(taskId, candidateForTask);
	 * assignedUserId = aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals("assignedUserId should be same as candidateForTask",
	 * assignedUserId, candidateForTask); }
	 * 
	 * // Test that a candidate user for a task is not allowed to assign an
	 * already assigned task
	 * 
	 * @Test public void testCase6() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/Arendeprocess.bpmn20.xml");
	 * activitiEngineService .startProcessInstanceByKey("Arendeprocess",
	 * userId);
	 * 
	 * List<InboxTaskItem> inbox = activitiEngineService.getUserInbox("admin");
	 * Assert.assertNotNull(inbox); Assert.assertNotNull(inbox.get(0)); String
	 * taskId = inbox.get(0).getTaskUuid(); ActivityWorkflowInfo aWFlowInfo =
	 * activitiEngineService .getActivityWorkflowInfo(taskId);
	 * 
	 * // verify the task is unassigned String assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * Assert.assertEquals("assignedUserId should be blank!", assignedUserId,
	 * "");
	 * 
	 * // get a candidate for the task Set<UserInfo> candidates =
	 * aWFlowInfo.getCandidates(); String candidateForTask_1 = ""; String
	 * candidateForTask_2 = ""; if (candidates != null && candidates.size() > 0)
	 * { candidateForTask_1 = ((UserInfo) (candidates.toArray()[0])).getUuid();
	 * candidateForTask_2 = ((UserInfo) (candidates.toArray()[1])).getUuid(); }
	 * Assert.assertNotEquals("candidateForTask_1 should not be blank!",
	 * candidateForTask_1, "");
	 * Assert.assertNotEquals("candidateForTask_2 should not be blank!",
	 * candidateForTask_2, "");
	 * 
	 * aWFlowInfo = activitiEngineService.assignTask(taskId,
	 * candidateForTask_1); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals("assignedUserId should be same as candidateForTask",
	 * assignedUserId, candidateForTask_1);
	 * 
	 * // This assignement should not be possible cause task is assigned by
	 * another user aWFlowInfo = activitiEngineService.assignTask(taskId,
	 * candidateForTask_2); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid(); Assert.assertNotEquals(
	 * "assignedUserId should not be same as candidateForTask", assignedUserId,
	 * candidateForTask_2); }
	 * 
	 * // Test that a candidate user for a task is not allowed to assign an
	 * already // assigned task and the unassign it.
	 * 
	 * @Test public void testCase7() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/Arendeprocess.bpmn20.xml");
	 * activitiEngineService .startProcessInstanceByKey("Arendeprocess",
	 * userId);
	 * 
	 * List<InboxTaskItem> inbox = activitiEngineService.getUserInbox("admin");
	 * Assert.assertNotNull(inbox); Assert.assertNotNull(inbox.get(0)); String
	 * taskId = inbox.get(0).getTaskUuid(); ActivityWorkflowInfo aWFlowInfo =
	 * activitiEngineService .getActivityWorkflowInfo(taskId);
	 * 
	 * // verify the task is unassigned String assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * Assert.assertEquals("assignedUserId should be blank!", assignedUserId,
	 * "");
	 * 
	 * // get a candidate for the task Set<UserInfo> candidates =
	 * aWFlowInfo.getCandidates(); String candidateForTask_1 = ""; String
	 * candidateForTask_2 = ""; if (candidates != null && candidates.size() > 0)
	 * { candidateForTask_1 = ((UserInfo) (candidates.toArray()[0])) .getUuid();
	 * candidateForTask_2 = ((UserInfo) (candidates.toArray()[1])) .getUuid(); }
	 * Assert.assertNotEquals("candidateForTask_1 should not be blank!",
	 * candidateForTask_1, "");
	 * Assert.assertNotEquals("candidateForTask_2 should not be blank!",
	 * candidateForTask_2, "");
	 * 
	 * aWFlowInfo = activitiEngineService.assignTask(taskId,
	 * candidateForTask_1); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals( "assignedUserId should be same as candidateForTask",
	 * assignedUserId, candidateForTask_1);
	 * 
	 * // This assignemrent should not be possible cause task is assigned by //
	 * another user aWFlowInfo = activitiEngineService.assignTask(taskId,
	 * candidateForTask_2); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid(); Assert.assertNotEquals(
	 * "assignedUserId should not be same as candidateForTask", assignedUserId,
	 * candidateForTask_2);
	 * 
	 * // Unassign
	 * 
	 * aWFlowInfo = activitiEngineService.unassignTask(taskId); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals( "assignedUserId should be same as candidateForTask",
	 * assignedUserId, ""); }
	 */
	/*
	 * // assign and unassign a user
	 * 
	 * @Test public void testCase8() { String userId = "admin"; clearDatabase();
	 * 
	 * // Deploy a BPMN and start a process with a certain user
	 * activitiEngineService
	 * .deployBpmn("../../bpm-processes/Arendeprocess.bpmn20.xml");
	 * activitiEngineService .startProcessInstanceByKey("Arendeprocess",
	 * userId);
	 * 
	 * List<InboxTaskItem> inbox = activitiEngineService.getUserInbox("admin");
	 * Assert.assertNotNull(inbox); Assert.assertNotNull(inbox.get(0)); String
	 * taskId = inbox.get(0).getTaskUuid(); ActivityWorkflowInfo aWFlowInfo =
	 * activitiEngineService .getActivityWorkflowInfo(taskId);
	 * 
	 * // verify the task is unassigned String assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * Assert.assertEquals("assignedUserId should be blank!", assignedUserId,
	 * "");
	 * 
	 * // get a candidate for the task Set<UserInfo> candidates =
	 * aWFlowInfo.getCandidates(); String candidateForTask_1 = ""; if
	 * (candidates != null && candidates.size() > 0) { candidateForTask_1 =
	 * ((UserInfo) (candidates.toArray()[0])) .getUuid(); }
	 * Assert.assertNotEquals("candidateForTask_1 should not be blank!",
	 * candidateForTask_1, "");
	 * 
	 * aWFlowInfo = activitiEngineService.assignTask(taskId,
	 * candidateForTask_1); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals( "assignedUserId should be same as candidateForTask",
	 * assignedUserId, candidateForTask_1);
	 * 
	 * // Unassign
	 * 
	 * aWFlowInfo = activitiEngineService.unassignTask(taskId); assignedUserId =
	 * aWFlowInfo.getAssignedUser().getUuid();
	 * 
	 * Assert.assertEquals( "assignedUserId should be same as candidateForTask",
	 * assignedUserId, ""); }
	 */
	@SuppressWarnings("unused")
	private void clearDatabase() {

		// Clear the deployment, processdefinitions, processinstances, task and
		// history
		for (String deploymentId : activitiEngineService
				.getDeployedDeploymentIds()) {
			activitiEngineService.deleteDeploymentByDeploymentId(deploymentId,
					true);
		}
	}

}
