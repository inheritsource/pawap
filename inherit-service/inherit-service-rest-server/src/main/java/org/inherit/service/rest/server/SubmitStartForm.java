package org.inherit.service.rest.server;

import java.util.logging.Logger;

import org.inherit.service.common.util.ParameterEncoder;
import org.inherit.taskform.engine.TaskFormService;
import org.restlet.data.Status;
import org.restlet.resource.Post;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

public class SubmitStartForm extends ServerResource {

	public static final Logger log = Logger.getLogger(SubmitStartForm.class
			.getName());

	TaskFormService engine = new TaskFormService();

	@Post
	public String submitStartForm() {
		String result;
		String formPath = ParameterEncoder
				.decode((String) getRequestAttributes().get("formPath"));
		String docId = ParameterEncoder.decode((String) getRequestAttributes()
				.get("docId"));
		String userId = ParameterEncoder.decode((String) getRequestAttributes()
				.get("userId"));

		log.fine("REST getUserInstancesList with parameter userid=[" + userId
				+ "]");
		try {
			result = engine.submitStartForm(formPath, docId, userId);
		} catch (Exception e) {
			// TODO The status message is not transferred to the client side (ROL)
			setStatus(new Status(Status.SERVER_ERROR_INTERNAL,"Error in service layer, form not submitted"));
			// throw new ResourceException(Status.SERVER_ERROR_INTERNAL,
			//		"Error in service layer, Start form not submitted", e);
			result=null;
		}
		return result;
	}
}
