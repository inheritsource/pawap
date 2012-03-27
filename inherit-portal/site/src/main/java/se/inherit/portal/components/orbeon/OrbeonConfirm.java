package se.inherit.portal.components.orbeon;

import java.security.Principal;
import java.util.Locale;

import org.hippoecm.hst.component.support.bean.BaseHstComponent;
import org.hippoecm.hst.content.beans.standard.HippoBean;
import org.hippoecm.hst.core.component.HstComponentException;
import org.hippoecm.hst.core.component.HstRequest;
import org.hippoecm.hst.core.component.HstResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.inherit.bonita.restclient.BonitaClient;
import se.inherit.portal.beans.EServiceDocument;

public class OrbeonConfirm  extends BaseHstComponent {

	public static final Logger log = LoggerFactory.getLogger(OrbeonConfirm.class);
	
	@Override
    public void doBeforeRender(final HstRequest request, final HstResponse response) throws HstComponentException {
		
        HippoBean doc = getContentBean(request);
        
        Principal principal = request.getUserPrincipal();
        //String userName = principal.getName();
        String userName = "eva_extern";
        
        if (doc == null) {
            log.warn("Did not find a content bean for relative content path '{}' for pathInfo '{}'", 
                         request.getRequestContext().getResolvedSiteMapItem().getRelativeContentPath(),
                         request.getRequestContext().getResolvedSiteMapItem().getPathInfo());
            response.setStatus(404);
            return;
        }
     
        request.setAttribute("document",doc);

        String docId = getPublicRequestParameter(request, "docId");
        
        if (doc instanceof EServiceDocument) {
        	EServiceDocument eServiceDocument = (EServiceDocument)doc;
        	
        	String formUrl = eServiceDocument.getFormPath() + "/view/" + docId + "?orbeon-embeddable=true";
        	request.setAttribute("formUrl", formUrl);
        	log.error("XXXXXXXXXXXXXXXXXXXX orbeon confirm form url:" + formUrl);
        	
        	// starta bonita process quick and dirty. fel sätt att trigga det och fel metod att hitta process uuid
        	
        	BonitaClient bc = new BonitaClient("http://localhost:58080/bonita-server-rest/", "http://localhost:58080/inherit-bonita-rest-server-custom-1.0-SNAPSHOT/", "restuser", "restbpm");
        	String puuidstr = eServiceDocument.getFormPath();
        	if ("BMTest/BMTestForm".equals(eServiceDocument.getFormPath())) {
        		puuidstr = "Felanmalan--2.0";
    		}
        	bc.bonitaStartProcess(puuidstr, userName);
        	//bc.bonitaStartProcess("Felanmalan--2.0", userName);
        }
        
    }
}