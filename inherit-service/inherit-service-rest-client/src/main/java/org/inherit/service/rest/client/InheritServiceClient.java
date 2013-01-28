package org.inherit.service.rest.client;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Logger;

import org.inherit.service.common.domain.ActivityInstanceItem;
import org.inherit.service.common.domain.ActivityInstanceLogItem;
import org.inherit.service.common.domain.ActivityInstancePendingItem;
import org.inherit.service.common.domain.ActivityWorkflowInfo;
import org.inherit.service.common.domain.CommentFeedItem;
import org.inherit.service.common.domain.DashOpenActivities;
import org.inherit.service.common.domain.InboxTaskItem;
import org.inherit.service.common.domain.ProcessDefinitionInfo;
import org.inherit.service.common.domain.ProcessInstanceDetails;
import org.inherit.service.common.domain.ProcessInstanceListItem;
import org.inherit.service.common.util.ParameterEncoder;
import org.restlet.Client;
import org.restlet.Context;
import org.restlet.data.ChallengeScheme;
import org.restlet.data.Form;
import org.restlet.data.Protocol;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import com.thoughtworks.xstream.XStream;

/**
 * A ready to use REST client implementation to inherit-service-rest-server.
 * @author bjmo
 *
 */
public class InheritServiceClient {

	public static final Logger log = Logger.getLogger(InheritServiceClient.class.getName());
	
	String serverBaseUrl;
	XStream xstream;
	
	public InheritServiceClient() {
		serverBaseUrl = "http://localhost:58080/inherit-service-rest-server-1.0-SNAPSHOT/";
		xstream = initXStream();
	}

	public InheritServiceClient(String serverBaseUrl) {
		this.serverBaseUrl = serverBaseUrl;
		xstream = initXStream();
	}
	
	private XStream initXStream() {
		XStream xstream = new XStream();
		
		xstream.alias("Date", Date.class);
		
		xstream.alias("set", HashSet.class);
		xstream.alias("list", ArrayList.class);
		
		xstream.alias("InboxTaskItem", InboxTaskItem.class);
		xstream.alias("ProcessDefinitionInfo", ProcessDefinitionInfo.class);
		xstream.alias("ProcessInstanceListItem", ProcessInstanceListItem.class);
		xstream.alias("CommentFeedItem", CommentFeedItem.class);
		
		xstream.alias("ProcessInstanceDetails", ProcessInstanceDetails.class);
		xstream.alias("ActivityInstanceItem", ActivityInstanceItem.class);
		xstream.alias("ActivityInstanceLogItem", ActivityInstanceLogItem.class);
		xstream.alias("ActivityInstancePendingItem", ActivityInstancePendingItem.class);
		xstream.alias("DashOpenActivities", DashOpenActivities.class);
		xstream.alias("ActivityWorkflowInfo", ActivityWorkflowInfo.class);
		
		return xstream;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<ProcessInstanceListItem> getStatusByUserId(String bonitaUser) {
		 ArrayList<ProcessInstanceListItem> result = null;
		String uri = serverBaseUrl + "statusByUserId/" + ParameterEncoder.encode(bonitaUser) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ArrayList<ProcessInstanceListItem>)xstream.fromXML(response);
		}
		return result;
	}
	
	public ProcessInstanceDetails getProcessInstanceDetailByUuid(String processInstanceUuid) {
		ProcessInstanceDetails result = null;
		String uri = serverBaseUrl + "processInstanceDetailsByUuid/" + ParameterEncoder.encode(processInstanceUuid) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ProcessInstanceDetails)xstream.fromXML(response);
		}
		
		return result;
	}
	
	public ProcessInstanceDetails getProcessInstanceDetailByActivityInstanceUuid(String activityInstanceUuid) {
		ProcessInstanceDetails result = null;
		String uri = serverBaseUrl + "processInstanceDetailsByActivityInstanceUuid/" + ParameterEncoder.encode(activityInstanceUuid) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ProcessInstanceDetails)xstream.fromXML(response);
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList<InboxTaskItem> getInboxByUserId(String bonitaUser) {
		 ArrayList<InboxTaskItem> result = null;
		String uri = serverBaseUrl + "inboxByUserId/" + ParameterEncoder.encode(bonitaUser) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ArrayList<InboxTaskItem>)xstream.fromXML(response);
		}
		return result;
	}
	
	public ActivityInstanceItem getActivityInstanceItem(String activityInstanceUuid, String userId) {
		ActivityInstanceItem result = null;
		String uri = serverBaseUrl + "getActivityInstanceItem/" + ParameterEncoder.encode(activityInstanceUuid) + "/" + ParameterEncoder.encode(userId) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ActivityInstanceItem)xstream.fromXML(response);
		}
		
		return result;
	}
	
	public ActivityInstanceItem getStartActivityInstanceItem(String formPath, String userId) {
		ActivityInstanceItem result = null;
		String uri = serverBaseUrl + "getStartActivityInstanceItem/" + ParameterEncoder.encode(formPath) + "/" + ParameterEncoder.encode(userId) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ActivityInstanceItem)xstream.fromXML(response);
		}
		
		return result;
	}

	public ActivityInstanceItem getActivityInstanceItem(String processActivityFormInstanceId) {
		ActivityInstanceItem result = null;
		String uri = serverBaseUrl + "getActivityInstanceItemById/" + ParameterEncoder.encode(processActivityFormInstanceId) + "?media=xml";
		String response = call(uri);
		System.out.println(response);
		if (response != null) {
			result = (ActivityInstanceItem)xstream.fromXML(response);
		}
		return result;
	}

	public ActivityInstanceItem getStartActivityInstanceItem(Long processActivityFormInstanceId) {
		ActivityInstanceItem result = null;
		if (processActivityFormInstanceId != null) {
			result = getActivityInstanceItem(processActivityFormInstanceId.toString());
		}
		return result;
	}
	
	public String submitStartForm(String formPath, String docId, String userId) {
		String result = null;
		String uri;
		
		uri = serverBaseUrl + "submitStartForm/" + ParameterEncoder.encode(formPath) + 
					"/" + ParameterEncoder.encode(docId) + "/" + ParameterEncoder.encode(userId) + "?media=xml";
		log.severe("submitStartForm uri: " + uri);
		String response = call(uri);
		
		if (response != null) {
			result = (String)response;
		}
		
		return result;
	}

	public String submitForm(String docId, String userId) {
		String result = null;
		String uri;
		
		uri = serverBaseUrl + "submitForm/" 
				+ ParameterEncoder.encode(docId) + "/" 
				+ ParameterEncoder.encode(userId) + "?media=xml";
		log.severe("submitStartForm uri: " + uri);
		String response = call(uri);
		
		if (response != null) {
			result = (String)response;
		}
		
		return result;
	}
	
	public DashOpenActivities getDashOpenActivitiesByUserId(String userid, String remainingDays) {
		DashOpenActivities result = null;
		String uri;
		
		uri = serverBaseUrl + "getDashOpenActivitiesByUserId/" 
				+ ParameterEncoder.encode(userid) + "/" 
				+ ParameterEncoder.encode(remainingDays) + "?media=xml";
		log.severe("getDashOpenActivitiesByUserId uri: " + uri);
		String response = call(uri);
		
		if (response != null) {
			result = (DashOpenActivities)xstream.fromXML(response);
		}
		
		return result;
	}
	
	public DashOpenActivities getDashOpenActivitiesByUserId(String userid, int remainingDays) {
		DashOpenActivities result = null;
		result = getDashOpenActivitiesByUserId(userid, Integer.toString(remainingDays));
		return result;
	}
	
	public int addComment(String activityInstanceUuid, String comment, String userId) {
		int result = -2;
		String uri;
		
		uri = serverBaseUrl + "addComment/" + ParameterEncoder.encode(activityInstanceUuid) + 
					"/" + ParameterEncoder.encode(comment) + "/" + ParameterEncoder.encode(userId) + "?media=xml";
		log.severe("addComment uri: " + uri);
		
		String response = call(uri);
		log.severe("response addComment: [" + response + "]");
		result = parseIntResponse(response);

		return result;
	}

	public ActivityWorkflowInfo assignTask(String activityInstanceUuid, String action, String userId) {
		ActivityWorkflowInfo result = null;
		String uri;
		
		uri = serverBaseUrl + "assignTask/" + ParameterEncoder.encode(activityInstanceUuid) + 
					"/" + ParameterEncoder.encode(action) + "/" + ParameterEncoder.encode(userId) + "?media=xml";
		log.severe("addComment uri: " + uri);
		
		String response = call(uri);
		if (response != null) {
			result = (ActivityWorkflowInfo)xstream.fromXML(response);
		}

		return result;
	}
	
	public ActivityWorkflowInfo setActivityPriority(String activityInstanceUuid, int priority) {
		ActivityWorkflowInfo result = null;
		String uri;
		
		uri = serverBaseUrl + "setActivityPriority/" + ParameterEncoder.encode(activityInstanceUuid) + 
					"/" + priority + "?media=xml";
		log.severe("setActivityPriority uri: " + uri);
		
		String response = call(uri);
		if (response != null) {
			result = (ActivityWorkflowInfo)xstream.fromXML(response);
		}

		return result;
	}

	public ActivityWorkflowInfo getActivityWorkflowInfo(String activityInstanceUuid) {
		ActivityWorkflowInfo result = null;
		String uri;
		
		uri = serverBaseUrl + "getActivityWorkflowInfo/" + ParameterEncoder.encode(activityInstanceUuid) + "?media=xml";
		log.severe("getActivityWorkflowInfo uri: " + uri);
		
		String response = call(uri);
		if (response != null) {
			result = (ActivityWorkflowInfo)xstream.fromXML(response);
		}

		return result;
	}

	
	private int parseIntResponse(String val) {
		int result = -1000;
		try {
			if (val!=null) {
				result = ((Integer)xstream.fromXML(val)).intValue();
				
			}
		}
		catch (Exception nfe) {
			log.warning("Cannot parse integer value from val: " + val);
			result = -1001;
		}
		return result;
	}
	
	private String call(String uri) {
		String result = null;
		try {
			Client client = new Client(new Context(), Protocol.HTTP);
			
			ClientResource cr = new ClientResource(uri);
			cr.setNext(client);
		
			cr.setChallengeResponse(ChallengeScheme.HTTP_BASIC, "restuser", "restbpm");
				
			final String RESTLET_HTTP_HEADERS = "org.restlet.http.headers";
	        Map<String, Object> reqAttribs = cr.getRequestAttributes();
	        Form headers = (Form)reqAttribs.get(RESTLET_HTTP_HEADERS);
	        if (headers == null) {
	            headers = new Form();
	            reqAttribs.put(RESTLET_HTTP_HEADERS, headers);
	        } 
	        //headers.add("options", "user:" + bonitaUser); 

	        result = (String)cr.post(null, String.class);
	        			
		} catch (ResourceException e) {
			log.severe("call ResourceException: " + e);
		}
		
		return result;
	}
	
	public static void main(String args[]) {
		System.out.println("Testa InheritServiceClient");
		
		InheritServiceClient c = new InheritServiceClient();
		ArrayList<ProcessInstanceListItem> list = c.getStatusByUserId("john");
		for (ProcessInstanceListItem item : list) {
			System.out.println("item: " + item);
		}

		ArrayList<InboxTaskItem> listInbx = c.getInboxByUserId("john");
		for (InboxTaskItem item : listInbx) {
			System.out.println("listInbx item: " + item);
		}

		System.out.println("InheritServiceClient avslutas");
	}
	
}
