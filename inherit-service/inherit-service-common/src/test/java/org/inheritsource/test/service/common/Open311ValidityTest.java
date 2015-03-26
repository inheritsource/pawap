package org.inheritsource.test.service.common;

//import org.junit.After;
//import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.ws.rs.core.MediaType;

import org.inheritsource.service.common.domain.Open311Validity;

public class Open311ValidityTest {

	// @Before
	// public void before() {
	// System.out.println("Open311ValiditTest: before");
	// }
	//
	// @After
	// public void after() {
	// System.out.println("Open311EndPointTest: after");
	// }

	@Ignore
	@Test
	public void restTest() {
		System.out.println("Open311EndPointTest: restTest");
		String jurisdiction_id = "jurisdiction_id";
		String service_code = "AvfallOchAtervinning";
		String api_key = "7bdc012429cced61cc2c7c5078f7e3c31e25320c";

		// let Coordinatrice check the api key
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
			System.out.println("open311Validity = " + open311Validity);

		} catch (Exception e) {

			System.out.println("e, open311EndPoint = " + open311Validity);
			System.out.println("Exception e = " + e);

		}

		String startform = open311Validity.getOpen311Jurisdiction().getStartForm() ; 
		String startformExistPath = open311Validity.getOpen311Jurisdiction().getStartFormExistPath() ; 
		System.out.println("startform = " + startform ) ; 
		System.out.println("startformExistPath = " +  startformExistPath) ; 
		
	}

}
