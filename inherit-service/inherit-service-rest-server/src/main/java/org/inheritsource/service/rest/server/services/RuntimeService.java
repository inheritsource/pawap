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

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.inheritsource.service.common.domain.ActivityInstanceItem;
import org.inheritsource.service.common.domain.Open311v2ServiceResponse;
import org.inheritsource.service.common.domain.Open311v2ServiceRequest;
import org.inheritsource.service.common.domain.Open311v2ServiceResponseItem;
import org.inheritsource.taskform.engine.TaskFormService;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

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

		System.out.println("api_key = " + api_key); //
		String correct_api_key = "xyz"; // TODO make key configurable
		System.out.println("format = " + format);
		if (api_key.equals(correct_api_key)) {
			System.out.println("correct api_key ");
		} else {
			return Response.status(Response.Status.BAD_REQUEST)
					.type("text/plain").entity("Bad format").build();
		}
		Map<String, Object> variableMap = new HashMap<String, Object>();
		Open311v2ServiceRequest open311v2ServiceRequest = new Open311v2ServiceRequest();
		// generate a type 4 UUID
		String motriceStartFormInstanceId = java.util.UUID.randomUUID()
				.toString();

		variableMap.put("motriceStartFormAssignee", "admin");
		variableMap.put("motriceStartFormInstanceId",
				motriceStartFormInstanceId);
		variableMap.put("motriceStartFormLat", lat);
		variableMap.put("motriceStartFormLon", lon);
		variableMap.put("motriceStartFormDefinitionKey" ,  "felanmalan/felanmalan--v010" ) ; // TODO  
		
		
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

		System.out.println(variableMap);
		// System.out.println(open311v2ServiceRequest);
		String userId = "admin";

		String processDefinitionId = "felanmalan:11:5408";// TODO in
		// coordinatrice ?

		// fill in the document
		ClientConfig config = new DefaultClientConfig();
		Client client = Client.create(config);
		try {
			WebResource service = client
					.resource("http://localhost:8080/exist/postxdb/data/array/felanmalan/felanmalan/v010"); // TODO
			String response = service.type(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON)
					.post(String.class, open311v2ServiceRequest);
			System.out.println("response= " + response);
			variableMap.put("motriceStartFormInstanceId", response);
			variableMap.put("motriceStartFormDataUri" ,  "MOTRICESTARTFORMDATAURI" ) ; // TODO  
			// initialFormInstance.
			String processInstanceId;

			processInstanceId = engine.getActivitiEngineService().startProcess(
					processDefinitionId, variableMap, userId);
			System.out.println("processInstanceId = " + processInstanceId);

			String service_notice = "Tack för felanmälan."; // TODO from
															// configuration

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
				return null; // This should no happen
			}
		}

		catch (Exception ex) {
			System.out.println("Exception : " + ex);
			return null;
		}

	}

}
