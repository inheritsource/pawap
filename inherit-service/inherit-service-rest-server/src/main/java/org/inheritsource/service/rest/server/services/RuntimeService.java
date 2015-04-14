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

package org.inheritsource.service.rest.server.services;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;
import org.inheritsource.service.common.domain.ActivityInstanceItem;
import org.inheritsource.service.common.domain.Open311Validity;
//import org.inheritsource.service.common.domain.Open311v2Service;
import org.inheritsource.service.common.domain.Open311v2ServiceResponse;
import org.inheritsource.service.common.domain.Open311v2ServiceRequest;
import org.inheritsource.service.common.domain.Open311v2ServiceResponseItem;
import org.inheritsource.service.common.domain.Open311v2Services;
import org.inheritsource.service.common.domain.Open311v2p1ServiceRequestUpdate;
import org.inheritsource.service.common.domain.Open311v2p1ServiceRequestUpdateMessage;
import org.inheritsource.service.common.domain.Open311v2p1ServiceRequestUpdates;
import org.inheritsource.service.processengine.ActivitiEngineService;
import org.inheritsource.taskform.engine.TaskFormService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

@Component
@Path("/runtime")
public class RuntimeService {

	public static final Logger log = LoggerFactory
			.getLogger(RuntimeService.class.getName());

	@Autowired
	TaskFormService engine;

	/**
	 * Get a start form ActivityInstanceItem. Initialize a new one if it does
	 * not exist or continue with a previous partially filled in form.
	 * 
	 * @param formPath
	 * @param userId
	 * @return
	 */
	@POST
	@Path("/activities/startActivityInstance")
	@Produces({ "application/xml", "application/json" })
	@Consumes("application/x-www-form-urlencoded")
	public ActivityInstanceItem getStartActivityInstanceItemByFormPath(
			@FormParam("formPath") String formPath,
			@FormParam("userId") String userId) {
		ActivityInstanceItem result = null;

		log.info("REST call with parameter formPath=[{}] by userId=[{}]",
				formPath, userId);

		try {
			result = engine
					.getStartActivityInstanceItem(formPath, null, userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e.toString());
		}
		return result;
	}

	/**
	 * Get user with a known inherit platform user uuid
	 * 
	 * @param userUuid
	 * @return
	 */
	@POST
	@Path("/activities/byforminstance/{userUuid}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public ActivityInstanceItem getActivityInstanceItemByActivityInstanceUuid(
			Long processActivityFormInstanceId) {
		return null;
	}

	/**
	 * Get user with a known inherit platform user uuid
	 * 
	 * @param userUuid
	 * @return
	 */
	@POST
	@Path("/activities/{activityInstanceId}")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public ActivityInstanceItem getActivityInstanceItemByActivityInstanceId(
			String activityInstanceId) {
		return null;
	}

	/**
	 * Retrieves representation of an instance of helloWorld.HelloWorld
	 * 
	 * @return an instance of java.lang.String
	 */
	@GET
	@Path("/open311/helloworld")
	@Produces("text/html")
	public String getHtml() {
		return "<html><body><h1>Hello, World!!</body></h1></html>";
	}

	/**
	 * Sends the services. Uses coordinatrice as back end.
	 * 
	 * 
	 * @param format
	 *            : xml or json
	 * @param jurisdiction_id
	 * @return
	 */
	@Path("open311/v2/services.{format}")
	// TODO v2.1
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ "application/json", "application/xml" })
	@GET
	public Response services(@PathParam("format") String format,
			@QueryParam("jurisdiction_id") String jurisdiction_id) {

		Open311v2Services open311v2Services = new Open311v2Services();

		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try {
			WebResource service = client
					.resource(
							"http://localhost:8080/coordinatrice/rest/open311/services")
					.queryParam("jurisdiction_id", jurisdiction_id); // TODO
			open311v2Services = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.get(Open311v2Services.class);
		} catch (Exception e) {
			log.info("Exception e =  {}", e); // TODO
		}

		if (format.equals("json")) {
			return Response.ok(open311v2Services, MediaType.APPLICATION_JSON)
					.build();
		} else if (format.equals("xml")) {
			return Response.ok(open311v2Services, MediaType.APPLICATION_XML)
					.build();
		} else {
			// no proper format
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();
		}

	}

	@Path("open311/v2/requests.{format}")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ "application/json", "application/xml" })
	@POST
	public Response requests(@PathParam("format") String format,
			@FormParam("api_key") String api_key,
			@FormParam("jurisdiction_id") String jurisdiction_id,
			@FormParam("service_code") String service_code,
			@FormParam("lat") String lat, @FormParam("long") String lon,
			@FormParam("address_string") String address_string,
			@FormParam("address_id") String address_id,
			@FormParam("email") String email,
			@FormParam("device_id") String device_id,
			@FormParam("account_id") String account_id,
			@FormParam("first_name") String first_name,
			@FormParam("last_name") String last_name,
			@FormParam("phone") String phone,
			@FormParam("description") String description,
			@FormParam("media_url") String media_url) {
		// With JAX-RS 2.0 this may be done with @BeanParam and annotation in
		// the Java bean instead
		// but other packages are still on JAX-RS 1.X

		if (((lat == null) || (lon == null)) && (address_string == null)
				&& (address_id == null)) {
			// no proper address
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad address").build();

		}

		if (service_code == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Missing service_code").build();
		}

		if (jurisdiction_id == null) {
			// TODO This is only required if the endpoint serves multiple
			// jurisdictions
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Missing jurisdiction_id")
					.build();
		}

		if ((!(format.equals("json"))) && (!(format.equals("xml")))) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();
		}

		log.debug("api_key = {}", api_key);
		log.debug("format = {}", format);

		// Check the api key and get
		// information on what process and form to use
		ClientConfig configvalidate = new DefaultClientConfig();
		configvalidate.getFeatures().put(
				JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client clientvalidate = Client.create(configvalidate);

		Open311Validity open311Validity = null;
		try {
			WebResource service = clientvalidate
					.resource(
							"http://localhost:8080/coordinatrice/rest/open311/validity")
					.queryParam("jurisdiction_id", jurisdiction_id)
					.queryParam("service_code", service_code)
					.queryParam("api_key", api_key)
					.queryParam("return", "jurisdiction");

			open311Validity = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.get(Open311Validity.class);

			log.debug("open311Validity = {} " + open311Validity);
		} catch (Exception e) {
			log.error("open311Validity = {} " + open311Validity);
			log.error("Exception e =  {}", e); // TODO

			return Response.status(Response.Status.NOT_FOUND)
					.type("text/plain")
					.entity("jurisdiction_id or service_request_id not found.")
					.build();

		}

		String startform = open311Validity.getOpen311Jurisdiction()
				.getStartForm();
		String startformExistPath = open311Validity.getOpen311Jurisdiction()
				.getStartFormExistPath();
		String processDefinitionId = open311Validity.getOpen311Jurisdiction()
				.getProcdef_id();

		if ((startform == null) || (startformExistPath == null)
				|| (processDefinitionId == null) || (startform.length() == 0)
				|| (startformExistPath.length() == 0)
				|| (processDefinitionId.length() == 0)) {
			return Response.status(Response.Status.NOT_FOUND)
					.type("text/plain").entity("configuration missing.")
					.build();

		}

		log.error("open311Validity = {} " + open311Validity);
		Map<String, Object> variableMap = new HashMap<String, Object>();
		Open311v2ServiceRequest open311v2ServiceRequest = new Open311v2ServiceRequest();

		variableMap.put("motriceStartFormAssignee", "admin");
		variableMap.put("motriceStartFormLat", lat);
		variableMap.put("motriceStartFormLon", lon);
		variableMap.put("motriceStartFormDefinitionKey", startform);

		// motriceStartFormTypeId

		open311v2ServiceRequest.setDescription(description);

		open311v2ServiceRequest.setEmail(email);
		open311v2ServiceRequest.setDevice_id(device_id);
		open311v2ServiceRequest.setAccount_id(account_id);
		open311v2ServiceRequest.setFirst_name(first_name);
		open311v2ServiceRequest.setLast_name(last_name);
		open311v2ServiceRequest.setPhone(phone);
		open311v2ServiceRequest.setMedia_url(media_url);

		open311v2ServiceRequest.setAddress_string(address_string);
		open311v2ServiceRequest.setAddress_id(address_id);
		open311v2ServiceRequest.setJurisdiction_id(jurisdiction_id);
		open311v2ServiceRequest.setService_code(service_code);
		open311v2ServiceRequest.setLat(lat); // duplicate
		open311v2ServiceRequest.setLon(lon); // duplicate

		log.debug("variableMap= {} ", variableMap);
		String userId = "admin";

		// fill in the document
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try {
			WebResource service = client
					.resource("http://localhost:8080/exist/postxdb/data/array/"
							+ startformExistPath); // TODO
			String motriceStartFormInstanceId = service
					.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.post(String.class, open311v2ServiceRequest);
			log.debug(" motriceStartFormInstanceId= {}",
					motriceStartFormInstanceId);
			variableMap.put("motriceStartFormInstanceId",
					motriceStartFormInstanceId);
			variableMap.put("motriceStartFormTypeId", new Long(1)); // Orbeon
																	// form

			String processInstanceId = engine.getActivitiEngineService()
					.startProcess(processDefinitionId, variableMap, userId,
							motriceStartFormInstanceId);
			log.debug("processInstanceId = {}", processInstanceId);
			String service_notice = open311Validity.getOpen311Jurisdiction()
					.getService_notice();
			Open311v2ServiceResponseItem open311v2ServiceResponseItem = new Open311v2ServiceResponseItem();
			open311v2ServiceResponseItem.setService_notice(service_notice);
			open311v2ServiceResponseItem.setAccount_id(account_id);
			open311v2ServiceResponseItem
					.setService_request_id(motriceStartFormInstanceId);

			Open311v2ServiceResponse open311v2ServiceResponse = new Open311v2ServiceResponse();
			open311v2ServiceResponse
					.addOpen311v2ServiceResponseItem(open311v2ServiceResponseItem);

			if (format.equals("json")) {
				return Response.ok(open311v2ServiceResponse,
						MediaType.APPLICATION_JSON).build();
			} else if (format.equals("xml")) {
				return Response.ok(open311v2ServiceResponse,
						MediaType.APPLICATION_XML).build();
			} else {
				return null; // This should not happen
			}
		}

		catch (Exception ex) {
			// no proper format
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();

		}

	}

	// fms extension
	// https://github.com/mysociety/FixMyStreet/wiki/Open311-FMS---Proposed-differences-to-Open311
	@Path("open311/v2/servicerequestupdates.{format}")
	// TODO v2.1
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ "application/json", "application/xml" })
	@POST
	public Response servicerequestupdates(
			@PathParam("format") String format,
			@FormParam("api_key") String api_key, // NOTE missin in spec
			@FormParam("jurisdiction_id") String jurisdiction_id,
			@FormParam("update_id") String update_id,
			@FormParam("service_request_id") String service_request_id,
			@FormParam("updated_datetime") String updated_datetime_string,
			@FormParam("status") String status,
			@FormParam("description") String description,
			@FormParam("email") String email,
			@FormParam("last_name") String last_name,
			@FormParam("first_name") String first_name,
			@FormParam("title") String title,
			@FormParam("media_url") String media_url,
			@FormParam("account_id") String account_id) {
		// With JAX-RS 2.0 this may be done with @BeanParam and annotation in
		// the Java bean instead
		// but other packages are still on JAX-RS 1.X

		if (jurisdiction_id == null) {
			// TODO This is only required if the endpoint serves multiple
			// jurisdictions
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Missing jurisdiction_id")
					.build();
		}
		
		
		
		System.out.println("api_key = {}"+ api_key);
		// Check the api key and get
		// information on what process and form to use
		ClientConfig configvalidate = new DefaultClientConfig();
		configvalidate.getFeatures().put(
				JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
		Client clientvalidate = Client.create(configvalidate);

		Open311Validity open311Validity = null;
		try {
			WebResource service = clientvalidate
					.resource(
							"http://localhost:8080/coordinatrice/rest/open311/validity")
					.queryParam("jurisdiction_id", jurisdiction_id)
					.queryParam("api_key", api_key)
					.queryParam("return", "jurisdiction");

			open311Validity = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.get(Open311Validity.class);

			System.out.println("open311Validity = {} " + open311Validity);
		} catch (Exception e) {
			System.out.println("open311Validity = {} " + open311Validity);
			System.out.println("Exception e =  {}"+ e); // TODO

			return Response.status(Response.Status.NOT_FOUND)
					.type("text/plain")
					.entity("jurisdiction_id or service_request_id not found.")
					.build();

		}
		
		
		
		System.out.println("format = {}"+ format);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'hh:mm:ss");
		dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		System.out.println("updated_datetime_string = {}"+ updated_datetime_string);
		Date updated_datetime = null;
		try {
			updated_datetime = dateformat.parse(updated_datetime_string);
		} catch (Exception ex) {
			System.out.println("updated_datetime_string");
			System.out.println("Exception : " + ex);
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad date format").build();
		}

		System.out.println("updated_datetime = {}"+ updated_datetime);
		if ((!(format.equals("json"))) && (!(format.equals("xml")))) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();
		}

		// find the process from service_request_id
		if (service_request_id == null) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Missing service_request_id")
					.build();
		}

		String userId = "admin";

		String processId = engine.getActivitiEngineService()
				.getProcessInstanceOpen311(userId, jurisdiction_id,
						service_request_id);

		if (processId == null) {
			return Response.status(Response.Status.NOT_FOUND)
					.type("text/plain")
					.entity("jurisdiction_id or service_request_id not found.")
					.build();
		} else {

			ExecutionQuery executionQuery = engine.getActivitiEngineService()
					.getEngine().getRuntimeService().createExecutionQuery();
			String activityId = "open311update";
			executionQuery.processInstanceId(processId).activityId(activityId);

			Execution execution = executionQuery.singleResult();
			if (execution != null) {

				// update process
				Map<String, Object> variableMap = new HashMap<String, Object>();

				System.out.println("status =   " + status);
				System.out.println("description=   " + description);
				System.out.println("email =  " + email);
				System.out.println("last_name =  " + last_name);
				System.out.println("first_name =   " + first_name);
				System.out.println("title =   " + title);
				System.out.println("media_url =   " + media_url);
				System.out.println("account_id =   " + account_id);

			//	System.out.println("status = {}  ", status);
		//		System.out.println("description= {}  ", description);
		//		System.out.println("email = {}  ", email);
		//		System.out.println("last_name = {}  ", last_name);
		//		System.out.println("first_name = {}  ", first_name);
		//		System.out.println("title = {}  ", title);
		//		System.out.println("media_url = {}  ", media_url);
		//		System.out.println("account_id = {}  ", account_id);

				variableMap.put("motriceOpen311UpdateStatus", status);

				Open311v2p1ServiceRequestUpdateMessage open311v2p1ServiceRequestUpdateMessage = new Open311v2p1ServiceRequestUpdateMessage();
				open311v2p1ServiceRequestUpdateMessage
						.setAccount_id(account_id);
				open311v2p1ServiceRequestUpdateMessage
						.setDescription(description);
				open311v2p1ServiceRequestUpdateMessage.setEmail(email);
				open311v2p1ServiceRequestUpdateMessage
						.setFirst_name(first_name);
				open311v2p1ServiceRequestUpdateMessage
						.setJurisdiction_id(jurisdiction_id);
				open311v2p1ServiceRequestUpdateMessage.setLast_name(last_name);
				open311v2p1ServiceRequestUpdateMessage.setMedia_url(media_url);
				open311v2p1ServiceRequestUpdateMessage
						.setService_request_id(service_request_id);
				open311v2p1ServiceRequestUpdateMessage.setStatus(status);
				open311v2p1ServiceRequestUpdateMessage.setTitle(title);
				open311v2p1ServiceRequestUpdateMessage.setUpdate_id(update_id);
				open311v2p1ServiceRequestUpdateMessage
						.setUpdated_datetime(updated_datetime_string);

				if (status.equals("CLOSED")) {
					System.out.println("Status is CLOSED");
				}
				if (status.equals("FIXED")) {
					System.out.println("Status is FIXED");
				}
				
				
				// fill in orbeon form with update TODO
				// Might be tricky since the form is not related to user task ??
				ClientConfig config = new DefaultClientConfig();
				Client client = Client.create(config);
				try {
					WebResource service = client
							.resource("http://localhost:8080/exist/postxdb/data/array/felanmalan/uppdatering/v001"); // TODO
					String motriceFormInstanceId = service
							.type(MediaType.APPLICATION_JSON)
							.accept(MediaType.APPLICATION_JSON)
							.post(String.class,
									open311v2p1ServiceRequestUpdateMessage);
					System.out.println(" motriceFormInstanceId= {}"+
							motriceFormInstanceId);
					System.out.println(" motriceFormInstanceId= "
						+ motriceFormInstanceId);
					variableMap.put("motriceFormInstanceId",
							motriceFormInstanceId);
					variableMap.put("motriceFormTypeId", new Long(1)); // Orbeon

				}

				catch (Exception ex) {
					System.out.println("Exception : " + ex);
					return null;
				}

				// send the signal to the process
				engine.getActivitiEngineService().getEngine()
						.getRuntimeService()
						.signal(execution.getId(), variableMap);
				// engine.getActivitiEngineService().getEngine()
				// .getRuntimeService().signal(execution.getId());

			} else {
				System.out.println("Problem finding executions.");
				System.out.println("execution = " + execution);
				return Response.status(Response.Status.NOT_FOUND)
						.type("text/plain")
						.entity("service_request_id not found.").build();

			}
			// send response to open311 client

		}

		Open311v2p1ServiceRequestUpdates open311v2p1ServiceRequestUpdates = new Open311v2p1ServiceRequestUpdates();
		Open311v2p1ServiceRequestUpdate open311v2p1ServiceRequestUpdate = new Open311v2p1ServiceRequestUpdate();
		open311v2p1ServiceRequestUpdate.setUpdate_id(update_id);
		open311v2p1ServiceRequestUpdates
				.addOpen311v2p1ServiceRequestUpdate(open311v2p1ServiceRequestUpdate);

		System.out.println("open311v2p1ServiceRequestUpdates = {} "+
				open311v2p1ServiceRequestUpdates);
		if (format.equals("json")) {
			return Response.ok(open311v2p1ServiceRequestUpdates,
					MediaType.APPLICATION_JSON).build();
		} else if (format.equals("xml")) {
			return Response.ok(open311v2p1ServiceRequestUpdates,
					MediaType.APPLICATION_XML).build();
		} else {
			return null; // This should not happen
		}

	}

	// fms extension
	// https://github.com/mysociety/FixMyStreet/wiki/Open311-FMS---Proposed-differences-to-Open311
	@Path("open311/v2/servicerequestupdates.{format}")
	// TODO v2.1
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Produces({ "application/json", "application/xml" })
	@GET
	public Response servicerequestupdates(@PathParam("format") String format,
			@QueryParam("jurisdiction_id") String jurisdiction_id,
			@QueryParam("start_date") String start_date_string,
			@QueryParam("end_date") String end_date_string) {
		// With JAX-RS 2.0 this may be done with @BeanParam and annotation in
		// the Java bean instead
		// but other packages are still on JAX-RS 1.X

		if (jurisdiction_id == null) {
			// TODO This is only required if the endpoint serves multiple
			// jurisdictions
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Missing jurisdiction_id")
					.build();
		}
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'hh:mm:ss");
		dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		log.debug("start_date_string = {}", start_date_string);
		log.debug("end_date_string = {}", end_date_string);
		Date start_date = null;
		try {
			start_date = dateformat.parse(start_date_string);
		} catch (Exception ex) {
			log.error("start_date");
			log.error("Exception : " + ex);
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad date format").build();
		}

		Date end_date = null;
		try {
			end_date = dateformat.parse(end_date_string);
		} catch (Exception ex) {
			log.error("end_date");
			log.error("Exception : " + ex);
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad date format").build();
		}

		log.debug("start_date = {}", start_date);
		log.debug("end_date = {}", end_date);
		if ((!(format.equals("json"))) && (!(format.equals("xml")))) {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();
		}

		String userId = "admin";
		String processLike = "felanmalan/felanmalan--v%";
		Open311v2p1ServiceRequestUpdates open311v2p1ServiceRequestUpdates = engine
				.getActivitiEngineService().getProcessInstancesOpen311(userId,
						jurisdiction_id, start_date, end_date, processLike);
		log.debug("open311v2p1ServiceRequestUpdates = {} ",
				open311v2p1ServiceRequestUpdates);
		if (format.equals("json")) {
			return Response.ok(open311v2p1ServiceRequestUpdates,
					MediaType.APPLICATION_JSON).build();
		} else if (format.equals("xml")) {
			return Response.ok(open311v2p1ServiceRequestUpdates,
					MediaType.APPLICATION_XML).build();
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build(); // This
																		// should
																		// not
																		// happen
		}

	}

	public static void main(String[] args) {
		// Some example of date conversions used by the Open311 API
		String start_date_string = "2015-03-03T14:13:09Z";
		System.out.println("start_date_string = " + start_date_string);
		SimpleDateFormat dateformat = new SimpleDateFormat(
				"yyyy-MM-dd'T'hh:mm:ss");
		dateformat.setTimeZone(TimeZone.getTimeZone("UTC"));
		Date start_date = null;
		try {
			start_date = dateformat.parse(start_date_string);
			System.out.println("start_date =" + start_date);
			String W3CDTFDate = ActivitiEngineService.getW3CDTFDate(start_date);
			System.out.println("W3CDTFDate =" + W3CDTFDate);
		} catch (Exception e) {
			System.out.println("Exception:" + e);
		}
		System.exit(0);

	}
}
