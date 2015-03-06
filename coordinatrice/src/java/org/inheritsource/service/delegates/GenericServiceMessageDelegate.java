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

import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.impl.el.Expression;

/** GenericServiceMessageDelegate
 * Class for sending emails from a Service Task
 * <p>
 * The class needs to be configured with some fields with Expression Language.
 * Hence, one may use variables or fixed strings to set the values.  
 * 
 * A link to the user inbox is always added to the message text. 
 * 
 * The fields are :
 * @param recipientEmail
 * @param from
 * @param messageText
 * @param messageSubject
 *
 */
public class GenericServiceMessageDelegate implements JavaDelegate {

	public static String PROC_VAR_RECIPIENT_USER_ID = "recipientUserId";
	public static String PROC_VAR_RECIPIENT_USER_EMAIL = "recipientEmail";
	public static String PROC_VAR_SERVICE_DOC_URI = "serviceDocUri";

	public static String ACT_VAR_MESSAGE_TEXT = "emailMessageText";
	public static String ACT_VAR_SUBJECT = "emailSubject";

	// get the address and text from configuration in the process
	private Expression recipientEmail;
	private Expression from;
	private Expression messageText;
	private Expression messageSubject;

	public void execute(DelegateExecution execution) throws Exception {
		try {
			System.out.println("GenericServiceMessageDelegate called from "
					+ execution.getCurrentActivityName() + " in process "
					+ execution.getProcessInstanceId() + " at " + new Date());
		} catch (Exception e) {
			System.out.println("GenericServiceMessageDelegate called at +" + new Date()
					+ " catch ...");

		}

		String recipientEmailString = null;
		if (recipientEmail != null) {
			recipientEmailString = recipientEmail.getValue(execution)
					.toString();
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
			System.out.println("Configuration error : recipientEmailString = "
					+ recipientEmailString + " fromString =" + fromString
					+ " messageTextString" + messageTextString
					+ " messageSubjectString = " + messageSubjectString);
			return;
		}

		System.out.println("Recipient: " + recipientEmailString);
		System.out.println("     From: " + fromString);
		System.out.println("  Subject: " + messageSubjectString);
		System.out.println("  Message: " + messageTextString);

		return;

	}



}
