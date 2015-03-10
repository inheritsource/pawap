package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;
import java.io.FileInputStream;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Rule;
import org.junit.Test;

public class ProcessTestGenericmessagetest {

	private String filename = "/home/tostman/workspace20140410/pawap/inherit-service/inherit-service-activiti-engine/src/main/resources/GenericMessageTest.bpmn";

	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule.getRepositoryService();
		repositoryService.createDeployment().addInputStream("generic_message_test.bpmn20.xml",
				new FileInputStream(filename)).deploy();
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		variableMap.put("recipientEmail", "xxx@motrice.se");
		variableMap.put("from", "yyy@motrice.se");
		variableMap.put("messageText", "This is a message. Dags att titta på ett ärende  ");
		variableMap.put("messageSubject", "This is the subject");
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("generic_message_test", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
	}
}