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
 
package org.inheritsource.service.coordinatrice;

import java.util.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.repository.ProcessDefinition;
import org.inheritsource.service.form.FormEngine;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
public class CoordinatriceFacade {
	
	public static final Logger log = LoggerFactory.getLogger(CoordinatriceFacade.class.getName());
	
	private ProcessEngine engine = null; 

	public CoordinatriceFacade() {
		
	}
	
	public ProcessEngine getEngine() {
		return engine;
	}

	public void setEngine(ProcessEngine engine) {
		this.engine = engine;
	}
	
	
	
	public ActivityLabel getLabel(String procdefkey, String locale, String activityname, int procdefversion) {
		ActivityLabel label = null;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource("http://localhost:8080/coordinatrice/rest/");
			label = 
					service
					.path("activitylabel")
					.path(procdefkey)
					.path(locale)
					.path(activityname)
					.queryParam("version", String.valueOf(procdefversion))
					.accept(MediaType.APPLICATION_JSON)
					.get(ActivityLabel.class);
		}
		catch (Exception e) {
			log.info("label:  "+procdefkey +","+locale+"," +activityname +","  +procdefversion + " not found "  );
			log.debug("Exception: {}" , e);
		}
		return label;
	}
	
	/**
	 * Created to avoid the url encoding problem in getLabel
	 * @param procdefkey
	 * @param locale
	 * @param activityid
	 * @param procdefversion
	 * @return
	 */
	public ActivityLabel getLabelById(String procdefkey, String locale, String activityid, int procdefversion) {
		ActivityLabel label = null;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			WebResource service = client.resource("http://localhost:8080/coordinatrice/rest/");
			label = 
					service
					.path("activitylabelbyid")
					.path(procdefkey)
					.path(locale)
					.path(activityid)
					.queryParam("version", String.valueOf(procdefversion))
					.accept(MediaType.APPLICATION_JSON)
					.get(ActivityLabel.class);
		}
		catch (Exception e) {
			log.info("label:  "+procdefkey +","+locale+"," +activityid +","  +procdefversion + " not found "  );
			log.debug("Exception: {}" , e);
			
		}
		return label;
	}
	

	public String getLabel(String processDefinitionId, String activityname, Locale locale) {
		log.info("XXXXX " + processDefinitionId + " : " + activityname + " : " + (locale != null ? locale.getLanguage() : "null"));
		String result = activityname;
		
		ProcessDefinition procDef = engine.getRepositoryService().getProcessDefinition(processDefinitionId);
		
		if (procDef != null && locale != null) {
			ActivityLabel label = getLabel(procDef.getKey(), locale.getLanguage(), activityname, procDef.getVersion());
			if (label != null) {
				result = label.getLabel();
			}
		}
		
		return result;
	}
	
/**
 * Created to avoid the url encoding problem in getLabel
 * @param processDefinitionId
 * @param activityid
 * @param locale
 * @return
 */
	public String getLabelById(String processDefinitionId, String activityid, Locale locale) {
		//log.info("XXXXX " + processDefinitionId + " : " + activityid + " : " + (locale != null ? locale.getLanguage() : "null"));
		String result = activityid;
		
		ProcessDefinition procDef = engine.getRepositoryService().getProcessDefinition(processDefinitionId);
		
		if (procDef != null && locale != null) {
			ActivityLabel label = getLabelById(procDef.getKey(), locale.getLanguage(), activityid, procDef.getVersion());
			if (label != null) {
				result = label.getLabel();
			}
		}
		
		return result;
	}
	

	public StartFormLabel getStartFormLabel(String appName, String formName, String locale, int formdefversion) {
		StartFormLabel label = null;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			// $PREFIX/startformlabel/$app-name/$form-name/$locale[?version=$formdefversion]
			WebResource service = client.resource("http://localhost:8080/coordinatrice/rest/");
			label = 
					service
					.path("startformlabel")
					.path(appName)
					.path(formName)
					.path(locale)
					.queryParam("version", String.valueOf(formdefversion))
					.accept(MediaType.APPLICATION_JSON)
					.get(StartFormLabel.class);
		}
		catch (Exception e) {
			log.debug("Exception: {}", e);
			log.info("startformlabel:  "+ appName+","+locale+"," + formName+","  +formdefversion  + " not found "  );
		}
		return label;
	}
	
	public String getStartFormLabelByStartFormDefinitionKey(String defKey, Locale locale, String defaultValue) {
		String label = null;
		log.debug("Find name for: {}" , defKey);
		if (defKey != null) {
			String[] parts = defKey.split("/");
			
			if (parts.length == 2) {
				String appName = parts[0];
				log.debug("appname: {}" , appName);
				if (parts[1].length()>6) {
					String formName = parts[1].substring(0, parts[1].length()-6);
					int formdefversion = Integer.parseInt(parts[1].substring(parts[1].length()-3, parts[1].length()));
					log.debug("formName: {}" , formName);
					log.debug("formdefversion: " , formdefversion);
					StartFormLabel startFormLabel = getStartFormLabel(appName, formName, locale.getLanguage(), formdefversion);
					if (startFormLabel != null) {
						label = startFormLabel.label;
					}
				}
				
			}
			
		}

		// no coordinatrice label, try to find a process name
		if (label == null) {
			label = defaultValue;
		}
		
		// no process name, fall back to hard coded generic value
		if (label == null || label.trim().length()==0) {
			label = "case";
		}
		return label;
	}
	
	public String getStartFormLabel(String processInstanceId, Locale locale) {
		String label = null;
		
		HistoricProcessInstance processInstance = getEngine().getHistoryService().createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).includeProcessVariables().singleResult();
		if (processInstance != null && processInstance.getProcessVariables() != null && locale != null) {

			// First try to find form label from coordinatrice
			String defKey = (String) processInstance.getProcessVariables().get(FormEngine.START_FORM_DEFINITIONKEY);
			
			String processLabel = null;
			ProcessDefinition procdef = getEngine().getRepositoryService().getProcessDefinition(processInstance.getProcessDefinitionId());
			if (procdef != null) {
				processLabel = procdef.getName();
			}
			
			label = getStartFormLabelByStartFormDefinitionKey(defKey, locale, processLabel);
			
		}
		return label;
	}

	public ProcessDefinitionState getProcessDefinitionState(String procdefId) {
		ProcessDefinitionState state = null;
		try {
			ClientConfig config = new DefaultClientConfig();
			Client client = Client.create(config);
			
			WebResource service = client.resource("http://localhost:8080/coordinatrice/rest/");
			state = 
					service
					.path("procdef")
					.path("state")
					.path(procdefId)
					.accept(MediaType.APPLICATION_JSON)
					.get(ProcessDefinitionState.class);
		}
		catch (Exception e) {
			log.info("Exception: {}" , e.toString());
		}
		return state;
	}

	public static void main(String[] args) {
		CoordinatriceFacade c = new CoordinatriceFacade();
		System.out.println(c.getStartFormLabel("BMTest", "BMTestForm", "sv", 1));
		System.out.println(c.getProcessDefinitionState("TestFunctionProcess1:1:9"));
		System.out.println(c.getProcessDefinitionState("TestFunctionProcess1:2:305"));
	}

}
