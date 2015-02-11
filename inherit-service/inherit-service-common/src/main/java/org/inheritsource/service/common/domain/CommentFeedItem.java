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
 
 
package org.inheritsource.service.common.domain;

import java.io.Serializable;
import java.util.Date;


public class CommentFeedItem implements Serializable, TimelineItem {
	
	private static final long serialVersionUID = 7249719404625798791L;
	
	String processDefinitionUuid;
	String processInstanceUuid;
	String processLabel;
	String activityDefinitionUuid;
	String activityInstanceUuid;
	String activityLabel;
	Date timestamp;
	String timeStampStr;
	String message;
	UserInfo user;
	
	public CommentFeedItem() {

	}

	public String getProcessInstanceUuid() {
		return processInstanceUuid;
	}

	public void setProcessInstanceUuid(String processInstanceUuid) {
		this.processInstanceUuid = processInstanceUuid;
	}

	public String getProcessLabel() {
		return processLabel;
	}

	public void setProcessLabel(String processLabel) {
		this.processLabel = processLabel;
	}

	public String getActivityInstanceUuid() {
		return activityInstanceUuid;
	}

	public void setActivityInstanceUuid(String activityInstanceUuid) {
		this.activityInstanceUuid = activityInstanceUuid;
	}

	public String getActivityLabel() {
		return activityLabel;
	}

	public void setActivityLabel(String activityLabel) {
		this.activityLabel = activityLabel;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public UserInfo getUser() {
		return user;
	}

	public void setUser(UserInfo user) {
		this.user = user;
	}
	
	public String getProcessDefinitionUuid() {
		return processDefinitionUuid;
	}

	public void setProcessDefinitionUuid(String processDefinitionUuid) {
		this.processDefinitionUuid = processDefinitionUuid;
	}

	public String getActivityDefinitionUuid() {
		return activityDefinitionUuid;
	}

	public void setActivityDefinitionUuid(String activityDefinitionUuid) {
		this.activityDefinitionUuid = activityDefinitionUuid;
	}


	@Override
	public Date getTimestamp() {
		return timestamp;
	}

	@Override
	public int getType() {
		return TimelineItem.TYPE_COMMENT;
	}

	@Override
	public String getBriefDescription() {
		return message;
	}

	@Override
	public String getDescription() {
		return message;
	}

	@Override
	public String getViewUrl() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTimeStampStr() {
		return timeStampStr;
	}

	public void setTimeStampStr(String timeStampStr) {
		this.timeStampStr = timeStampStr;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((activityDefinitionUuid == null) ? 0
						: activityDefinitionUuid.hashCode());
		result = prime
				* result
				+ ((activityInstanceUuid == null) ? 0 : activityInstanceUuid
						.hashCode());
		result = prime * result
				+ ((activityLabel == null) ? 0 : activityLabel.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime
				* result
				+ ((processDefinitionUuid == null) ? 0 : processDefinitionUuid
						.hashCode());
		result = prime
				* result
				+ ((processInstanceUuid == null) ? 0 : processInstanceUuid
						.hashCode());
		result = prime * result
				+ ((processLabel == null) ? 0 : processLabel.hashCode());
		result = prime * result
				+ ((timeStampStr == null) ? 0 : timeStampStr.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CommentFeedItem other = (CommentFeedItem) obj;
		if (activityDefinitionUuid == null) {
			if (other.activityDefinitionUuid != null)
				return false;
		} else if (!activityDefinitionUuid.equals(other.activityDefinitionUuid))
			return false;
		if (activityInstanceUuid == null) {
			if (other.activityInstanceUuid != null)
				return false;
		} else if (!activityInstanceUuid.equals(other.activityInstanceUuid))
			return false;
		if (activityLabel == null) {
			if (other.activityLabel != null)
				return false;
		} else if (!activityLabel.equals(other.activityLabel))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (processDefinitionUuid == null) {
			if (other.processDefinitionUuid != null)
				return false;
		} else if (!processDefinitionUuid.equals(other.processDefinitionUuid))
			return false;
		if (processInstanceUuid == null) {
			if (other.processInstanceUuid != null)
				return false;
		} else if (!processInstanceUuid.equals(other.processInstanceUuid))
			return false;
		if (processLabel == null) {
			if (other.processLabel != null)
				return false;
		} else if (!processLabel.equals(other.processLabel))
			return false;
		if (timeStampStr == null) {
			if (other.timeStampStr != null)
				return false;
		} else if (!timeStampStr.equals(other.timeStampStr))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CommentFeedItem [processDefinitionUuid="
				+ processDefinitionUuid + ", processInstanceUuid="
				+ processInstanceUuid + ", processLabel=" + processLabel
				+ ", activityDefinitionUuid=" + activityDefinitionUuid
				+ ", activityInstanceUuid=" + activityInstanceUuid
				+ ", activityLabel=" + activityLabel + ", timestamp="
				+ timestamp + ", timeStampStr=" + timeStampStr + ", message="
				+ message + ", user=" + user + "]";
	}

	
	
}
