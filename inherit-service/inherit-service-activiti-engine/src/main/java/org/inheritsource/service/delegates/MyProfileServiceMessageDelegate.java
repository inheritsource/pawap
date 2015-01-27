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

package org.inheritsource.service.delegates;

import java.util.Date;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.Expression;
import org.inheritsource.service.common.util.ConfigUtil;
import org.inheritsource.taskform.engine.TaskFormService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/** MyProfileServiceMessageDelegate
 * Class for sending emails from a Service Task
 * <p>
 * The class needs to be configured with some fields with Expression Language.
 * Hence, one may use variables or fixed strings to set the values.
 * The class is derived from GenericServiceMessageDelegate and instead of
 * using the param recipientEmail directly, it fetches the recipientEmail from the
 * user profile with the recipient UserId as key. This behaviour should be
 * factored out in a subclass of GenericServiceMessageDelegate
 * 
 * A link to the user inbox is always added to the message text. 
 * 
 * The fields are :
 * @param recipientEmail
 * @param recipientUserId;
 * @param from
 * @param messageText
 * @param messageSubject
 *
 */
public class MyProfileServiceMessageDelegate implements JavaDelegate,
	ApplicationContextAware {

	public static final Logger log = LoggerFactory
			.getLogger(MyProfileServiceMessageDelegate.class);

	public static String PROC_VAR_RECIPIENT_USER_ID = "recipientUserId";
	public static String PROC_VAR_RECIPIENT_USER_EMAIL = "recipientEmail";
	public static String PROC_VAR_SERVICE_DOC_URI = "serviceDocUri";

	public static String ACT_VAR_MESSAGE_TEXT = "emailMessageText";
	public static String ACT_VAR_SUBJECT = "emailSubject";

	// get the address and text from configuration in the process
	private Expression recipientEmail;
	private Expression recipientUserId;
	private Expression from;
	private Expression messageText;
	private Expression messageSubject;

	public void execute(DelegateExecution execution) throws Exception {
		try {
			log.info("GenericServiceMessageDelegate called from "
					+ execution.getCurrentActivityName() + " in process "
					+ execution.getProcessInstanceId() + " at " + new Date());
		} catch (Exception e) {
			log.info("GenericServiceMessageDelegate called at +" + new Date()
					+ " catch ...");

		}

		String recipientEmailString = null;
//		if (recipientEmail != null) {
//			recipientEmailString = recipientEmail.getValue(execution)
//					.toString();
//		}


		String recipientUserIdString = null;
		if (recipientUserId != null) {
			recipientUserIdString = recipientUserId.getValue(execution)
					.toString();
		}

		if (context == null ) {
			log.error("context is null, failed to get service ");
			return;
		}	

		TaskFormService service = (TaskFormService) context.getBean("engine");

		if (service == null) {
			log.error("failed to get service, unable to determine emailadress ");
			return;
		} else {
				recipientEmailString = service.getMyProfile(recipientUserIdString).getEmail();
		}

		String fromString = null;
		if (from != null) {
			fromString = from.getValue(execution).toString();
		}

		String messageTextString = null;
		if (messageText != null) {
			messageTextString = messageText.getValue(execution).toString();
		}

		String messageSubjectString = null;
		if (messageSubject != null) {
			messageSubjectString = messageSubject.getValue(execution)
					.toString();
		}

		if ((recipientEmailString == null) || (fromString == null)
				|| (messageTextString == null)
				|| (messageSubjectString == null)) {
			log.error("Configuration error : recipientEmailString = "
					+ recipientEmailString + " fromString =" + fromString
					+ " messageTextString" + messageTextString
					+ " messageSubjectString = " + messageSubjectString);
			return;
		}

		Properties props = ConfigUtil.getConfigProperties();

		String siteUri = (String) props.get("site.base.uri");
		String SMTPSERVER = (String) props.get("mail.smtp.host");
		String SMTPportString = (String) props.get("mail.smtp.port");

		int smtpPort = 25;
		try {
			smtpPort = Integer.parseInt(SMTPportString);
		} catch (NumberFormatException e) {
			log.info("Illegal value of SMTPportString: {}", SMTPportString);
			e.printStackTrace();
		}

		String username = "";
		String password = "";

		// SSL port 465 or TLS port 587
		username = (String) props.get("mail.smtp.username");
		password = (String) props.get("mail.smtp.password");


		String inbox = (String) props.get("site.base.public");
		String authMecanisms = (String) props.get("mail.smtp.authMecanisms");
		if ((authMecanisms != null) && (!(authMecanisms.equals("DIGEST-MD5")))) {
			authMecanisms = null;
		}

		if ((siteUri != null) && (inbox != null)) {
			messageTextString = messageTextString + " " + siteUri.trim() + "/"
					+ inbox;
		}

		// read from configuration
		log.info("siteUri:{}", siteUri);
		log.info("inbox:{}", inbox);
		log.info("recipientEmail to: {}", recipientEmail);

		try {
			SendMail.send(recipientEmailString, fromString,
					messageSubjectString, messageTextString, SMTPSERVER,
					smtpPort, authMecanisms, username, password);
		} catch (Exception e) {
			log.error(e.toString());

		}
		return;

	}

	@Override
	public void setApplicationContext(ApplicationContext arg0)
			throws BeansException {
		context = arg0;
	}

	private static ApplicationContext context;



}
