import grails.converters.*

import org.motrice.coordinatrice.CrdProcCategory
import org.motrice.coordinatrice.CrdProcdefState
import org.motrice.coordinatrice.TaskType

class BootStrap {

  def init = { servletContext ->
    // This call makes Grails look for a toXml method in the domain
    // when rendering an instance as XML (for Migratrice)
    XML.registerObjectMarshaller(new org.codehaus.groovy.grails.web.converters
				 .marshaller.xml.InstanceMethodBasedMarshaller())

    // Make sure the default category is defined
    CrdProcCategory.createCategory(CrdProcCategory.DEFAULT_CATEGORY_NAME,
				CrdProcCategory.DEFAULT_CATEGORY_DESCRIPTION)

    // Make sure all process definition states exist
    CrdProcdefState.createState(CrdProcdefState.STATE_ACTIVE_ID,
			     CrdProcdefState.STATE_ACTIVE_RES,
			     CrdProcdefState.STATE_ACTIVE_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_SUSPENDED_ID,
			     CrdProcdefState.STATE_SUSPENDED_RES,
			     CrdProcdefState.STATE_SUSPENDED_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_EDIT_ID,
			     CrdProcdefState.STATE_EDIT_RES,
			     CrdProcdefState.STATE_EDIT_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_TRIAL_ID,
			     CrdProcdefState.STATE_TRIAL_RES,
			     CrdProcdefState.STATE_TRIAL_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_APPROVED_ID,
			     CrdProcdefState.STATE_APPROVED_RES,
			     CrdProcdefState.STATE_APPROVED_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_PUBLISHED_ID,
			     CrdProcdefState.STATE_PUBLISHED_RES,
			     CrdProcdefState.STATE_PUBLISHED_DEF)
    CrdProcdefState.createState(CrdProcdefState.STATE_RETIRED_ID,
			     CrdProcdefState.STATE_RETIRED_RES,
			     CrdProcdefState.STATE_RETIRED_DEF)

    // Make sure all task types exist
    TaskType.createType(TaskType.TYPE_BUSINESS_RULE_ID,
			TaskType.TYPE_BUSINESS_RULE_RES,
			TaskType.TYPE_BUSINESS_RULE_DEF)
    TaskType.createType(TaskType.TYPE_MANUAL_ID,
			TaskType.TYPE_MANUAL_RES,
			TaskType.TYPE_MANUAL_DEF)
    TaskType.createType(TaskType.TYPE_RECEIVE_ID,
			TaskType.TYPE_RECEIVE_RES,
			TaskType.TYPE_RECEIVE_DEF)
    TaskType.createType(TaskType.TYPE_SCRIPT_ID,
			TaskType.TYPE_SCRIPT_RES,
			TaskType.TYPE_SCRIPT_DEF)
    TaskType.createType(TaskType.TYPE_SEND_ID,
			TaskType.TYPE_SEND_RES,
			TaskType.TYPE_SEND_DEF)
    TaskType.createType(TaskType.TYPE_SERVICE_ID,
			TaskType.TYPE_SERVICE_RES,
			TaskType.TYPE_SERVICE_DEF)
    TaskType.createType(TaskType.TYPE_USER_ID,
			TaskType.TYPE_USER_RES,
			TaskType.TYPE_USER_DEF)
    
  }
  def destroy = {
  }
}
