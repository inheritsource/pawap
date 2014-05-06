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
 
package org.inheritsource.service.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.inheritsource.service.common.domain.FormInstance;
import org.inheritsource.service.common.domain.StartForm;
import org.inheritsource.service.common.domain.StartLogItem;
import org.inheritsource.service.coordinatrice.ProcessDefinitionState;
import org.inheritsource.service.delegates.DelegateUtil;
import org.inheritsource.service.identity.IdentityService;
import org.inheritsource.service.processengine.ActivitiEngineService;
import org.inheritsource.service.processengine.ActivitiEngineUtil;
import org.inheritsource.taskform.engine.persistence.TaskFormDb;
import org.inheritsource.taskform.engine.persistence.entity.ActivityFormDefinition;
import org.inheritsource.taskform.engine.persistence.entity.StartFormDefinition;

public class FormEngine {
	
	public static final Logger log = Logger.getLogger(FormEngine.class.getName());
	
	// motrice start process instance variables by convention
	public static final String START_FORM_TYPEID        = "motriceStartFormTypeId";
	public static final String START_FORM_DEFINITIONKEY = "motriceStartFormDefinitionKey";
	public static final String START_FORM_INSTANCEID    = "motriceStartFormInstanceId";
	public static final String START_FORM_ASSIGNEE      = "motriceStartFormAssignee";
	public static final String START_FORM_DATA_URI      = "motriceStartFormDataUri";
	public static final String START_FORM_DOCBOXREF		= "motriceStartFormPreservationDocboxRef";
	public static final String START_FORM_ACT_URI       = "motriceStartFormPreservationActUri";
	
	// motrice task local instance variables by convention
	public static final String FORM_TYPEID              = "motriceFormTypeId";
	public static final String FORM_DEFINITIONKEY       = "motriceFormDefinitionKey";
	public static final String FORM_INSTANCEID          = "motriceFormInstanceId";
	public static final String FORM_DATA_URI            = "motriceFormDataUri";
	public static final String FORM_DOCBOXREF			= "motriceFormPreservationDocboxRef";
	public static final String FORM_ACT_URI             = "motriceFormPreservationActUri";
	

	ActivitiEngineService activitiEngineService;
	TaskFormDb taskFormDb; 
	IdentityService identityService; 
	
	private Map<String, TaskFormHandler> formTypeId2Handler = new HashMap<String, TaskFormHandler>();
	
	public FormEngine() {
		
	}
	
	
	
	public TaskFormDb getTaskFormDb() {
		return taskFormDb;
	}



	public void setTaskFormDb(TaskFormDb taskFormDb) {
		this.taskFormDb = taskFormDb;
	}



	public IdentityService getIdentityService() {
		return identityService;
	}



	public void setIdentityService(IdentityService identityService) {
		this.identityService = identityService;
	}

	private TaskFormHandler getTaskFormHandler(Long typeId) {
		TaskFormHandler result = formTypeId2Handler.get(typeId.toString());
		
		if (result == null) {
			log.severe("Cannot find TaskFormHandler for typeId=" + typeId);
		}
		
		return result;
	}
	
	public ActivitiEngineService getActivitiEngineService() {
		return activitiEngineService;
	}

	public void setActivitiEngineService(ActivitiEngineService activitiEngineService) {
		this.activitiEngineService = activitiEngineService;
	}

	public Map<String, TaskFormHandler> getFormTypeId2Handler() {
		return formTypeId2Handler;
	}

	public void setFormTypeId2Handler(
			Map<String, TaskFormHandler> formTypeId2Handler) {
		this.formTypeId2Handler = formTypeId2Handler;
	}

	
	public FormInstance getHistoricFormInstance(HistoricTaskInstance historicTask, String userId, FormInstance initialInstance) {
		FormInstance formInstance = null;
		
		if (historicTask!=null) {

			Map <String, Object> localVars = historicTask.getTaskLocalVariables();
			
			Long typeId = (Long)localVars.get(FORM_TYPEID);
			if (typeId != null) {
				formInstance = getFormInstanceByCommonLocalVars(localVars, initialInstance);
				
				formInstance.setSubmitted(historicTask.getEndTime());
				formInstance.setSubmittedBy(identityService.getUserByUuid(historicTask.getAssignee()));
	
				String taskDocActVarName = DelegateUtil.calcTaskVariableName(FormEngine.FORM_ACT_URI, historicTask.getId());
				
				HistoricVariableInstance historicVar = activitiEngineService.getEngine().getHistoryService().createHistoricVariableInstanceQuery().variableName(taskDocActVarName).singleResult();
				if (historicVar != null) {
					formInstance.setActUri((String)historicVar.getValue());
				}
				
				TaskFormHandler handler = getTaskFormHandler(formInstance.getTypeId());
				if (handler != null) {
					handler.getHistoricFormInstance(formInstance, historicTask, userId);
				}
				
			}

			if (formInstance != null) {
				formInstance.setActinstId(historicTask.getId());
			}
			
		}
		
		return formInstance;
	}
	
	
	/**  
	 * initialize a task form instance in activiti engine. set variables by convention.
	 * Get existing FormInstance if form already is initialized.
	 * @return FormInstance of task, new initialized or existing if already initialized.
	 */ 
	public FormInstance getFormInstance(Task task, String userId, FormInstance initialInstance) {
		FormInstance formInstance = null;
		
		Map <String, Object> localVars = ActivitiEngineUtil.getTaskLocalVariables(activitiEngineService.getEngine(), task);
		
		Long typeId = (Long)localVars.get(FORM_TYPEID);
		if (typeId != null) {
			// this form is already initialized
			formInstance = getFormInstanceByCommonLocalVars(localVars, initialInstance);
			TaskFormHandler handler = getTaskFormHandler(formInstance.getTypeId());
			if (handler != null) {
				handler.getPendingFormInstance(formInstance, task, userId);
			}
		}
		else {
			// initialize form instance
			// find definition and form handler
			ActivityFormDefinition actformdef = taskFormDb.getActivityFormDefinition(task.getProcessDefinitionId(), task.getTaskDefinitionKey());
			TaskFormHandler handler = null;
			if (actformdef != null) {
				handler = getTaskFormHandler(actformdef.getFormTypeId());
			}
			else {
				handler = getTaskFormHandler(new Long(6)); // no form handler		
			}
			
			if (handler != null) {
				// initialize form and store task local variables
				formInstance = handler.initializeFormInstance(actformdef, task, userId, initialInstance);
				
				localVars.put(FORM_TYPEID, formInstance.getTypeId());
				localVars.put(FORM_DEFINITIONKEY, formInstance.getDefinitionKey());
				localVars.put(FORM_INSTANCEID, formInstance.getInstanceId());
				localVars.put(FORM_DATA_URI, formInstance.getDataUri());
				//localVars.put(FORM_ACT_URI, formInstance.getActUri());
				
				activitiEngineService.getEngine().getTaskService().setVariablesLocal(task.getId(), localVars);
			}
		}
		if (formInstance != null && task!=null) {
			formInstance.setActinstId(task.getId());
		}
		return formInstance;
	}
	
	
	/**  
	 * initialize a task form instance in activiti engine. set variables by convention.
	 * Get existing FormInstance if form already is initialized.
	 * @return FormInstance of task, new initialized or existing if already initialized.
	 */ 
	public FormInstance getStartFormInstance(Long formTypeId, String formConnectionKey, String userId, FormInstance initialInstance) {
		FormInstance formInstance = null;
		
		// initialize form instance
			
		// find form handler
		TaskFormHandler handler = getTaskFormHandler(formTypeId);
			
		if (handler != null) {
			// initialize form and store task local variables
			formInstance = handler.initializeStartFormInstance(formTypeId, formConnectionKey, userId, initialInstance);
		}
		
		return formInstance;
	}
	
	
	
	public StartLogItem getStartLogItem(HistoricProcessInstance historicProcessInstance, String userId) {
		StartLogItem startFormInstance = null;
		
		Map <String, Object> processVars = historicProcessInstance.getProcessVariables();
		
		if (processVars != null) {
			startFormInstance = new StartLogItem();
			startFormInstance.setTypeId((Long)processVars.get(START_FORM_TYPEID));
			startFormInstance.setDefinitionKey((String)processVars.get(START_FORM_DEFINITIONKEY));
			startFormInstance.setInstanceId((String)processVars.get(START_FORM_INSTANCEID));
			startFormInstance.setDataUri((String)processVars.get(START_FORM_DATA_URI));
			startFormInstance.setActUri((String)processVars.get(FormEngine.START_FORM_ACT_URI));
			startFormInstance.setSubmittedBy(identityService.getUserByUuid((String)processVars.get(START_FORM_ASSIGNEE)));
			
			if (historicProcessInstance != null) {
				startFormInstance.setSubmitted(historicProcessInstance.getStartTime());
			
				if (startFormInstance.getTypeId()!=null) {				
					TaskFormHandler handler = getTaskFormHandler(startFormInstance.getTypeId());
					if (handler != null) {
						handler.getHistoricStartFormInstance(startFormInstance, historicProcessInstance, userId);
					}
				}
			}
		}
		
		return startFormInstance;
	}
	
	protected FormInstance getFormInstanceByCommonLocalVars(Map <String, Object> localVars, FormInstance initialInstance) {
		FormInstance result = null;
		
		if (localVars != null) {
			result = initialInstance;
			result.setTypeId((Long)localVars.get(FORM_TYPEID));
			result.setDefinitionKey((String)localVars.get(FORM_DEFINITIONKEY));
			result.setInstanceId((String)localVars.get(FORM_INSTANCEID));
			result.setDataUri((String)localVars.get(FORM_DATA_URI));
		}
		
		return result;
	}
	
	public List<StartForm> getStartForms(Locale locale){
		List<StartForm> result = new ArrayList<StartForm>();
		List<StartForm> forms = taskFormDb.getStartForms();
		if (forms != null) {
			for (StartForm form : forms) {
				form.setPage("form");
				String label = getActivitiEngineService().getCoordinatriceFacade().getStartFormLabelByStartFormDefinitionKey(form.getDefinitionKey(), locale, form.getDefinitionKey());
				form.setLabel(label);
				
				StartFormDefinition startFormDef;
				try {
					startFormDef = taskFormDb.getStartFormDefinitionByFormPath(form.getDefinitionKey());
					if (startFormDef != null) {
						 ProcessDefinitionState state = getActivitiEngineService().getCoordinatriceFacade().getProcessDefinitionState(startFormDef.getProcessDefinitionUuid());
						 if (state != null) {
							 if (state.getStartableCode()==3) {
								 result.add(form);
							 }
						 }
					}
				} catch (Exception e) {
					log.severe("Could not load StartFormDefinition by formConnectionKey: " + form.getDefinitionKey());
				}

				
			}
		}
		return result;
	}

}
