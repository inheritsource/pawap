package org.inherit.service.rest.server;

import java.util.List;
import java.util.logging.Logger;

import org.inherit.service.common.domain.CommentFeedItem;
import org.inherit.service.common.util.ParameterEncoder;
import org.inherit.taskform.engine.TaskFormService;
import org.restlet.resource.Post;
import org.restlet.resource.ServerResource;


public class GetCommentFeed extends ServerResource {

	public static final Logger log = Logger.getLogger(GetCommentFeed.class.getName());
	
	TaskFormService engine = new TaskFormService();
	
	@Post
	public List<CommentFeedItem> getCommentFeed() {
		
		String activityInstanceUuid = ParameterEncoder.decode((String)getRequestAttributes().get("activityInstanceUuid"));
		String userid = ParameterEncoder.decode((String)getRequestAttributes().get("userid"));
		
		log.fine("REST getCommentFeed with parameter activityInstanceUuid=[" + activityInstanceUuid + "] userid=[" + userid + "]" );
		
		return engine.getCommentFeed(activityInstanceUuid, userid); 
	}
}