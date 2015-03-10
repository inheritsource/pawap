package org.activiti.designer.test;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.test.ActivitiRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

public class ProcessOverFormyndPoolTest {

	private String filename = "OverformyndareProcStart.bpmn";
	private String processname = "OverFormyndPool";
	@Rule
	public ActivitiRule activitiRule = new ActivitiRule();

	
	@Ignore
	@Test
	public void startProcess() throws Exception {
		RepositoryService repositoryService = activitiRule
				.getRepositoryService();
		repositoryService.createDeployment().addClasspathResource(filename)
				.name(processname).deploy();
		
	
		RuntimeService runtimeService = activitiRule.getRuntimeService();
		Map<String, Object> variableMap = new HashMap<String, Object>();
		variableMap.put("name", "Activiti");
		variableMap.put("recipientEmail", "xxx@motrice.se");
		variableMap.put("from", "yyy@motrice.se");
		variableMap.put("messageText", "This is a message. Dags att titta på ett ärende  ");
		variableMap.put("messageSubject", "This is the subject");
		variableMap.put("motriceStartFormAssignee",
				"5b257b18-01e6-4962-b356-0fc926c21175");
		variableMap.put("startevent1_personnummer", "195204151517");
		variableMap.put("startevent1_meddelande", "hello world");
		
		ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("OverFormyndPool", variableMap);
		assertNotNull(processInstance.getId());
		System.out.println("id " + processInstance.getId() + " "
				+ processInstance.getProcessDefinitionId());
	}
}
