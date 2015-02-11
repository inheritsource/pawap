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

import java.util.Date;


public class StartLogItem extends ActivityInstanceLogItem {
	private static final long serialVersionUID = 8056671513248069156L;
	
	private String longitude;
	private String latitude;
	
	/**
	 * Geolocation longitude using the WGS84 projection (http://en.wikipedia.org/wiki/World_Geodetic_System)
	 * @return
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * Geolocation longitude using the WGS84 projection (http://en.wikipedia.org/wiki/World_Geodetic_System)
	 * @param longitude
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * Geolocation latitude using the WGS84 projection (http://en.wikipedia.org/wiki/World_Geodetic_System)
	 * @return
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * Geolocation latitude using the WGS84 projection (http://en.wikipedia.org/wiki/World_Geodetic_System)
	 * @param latitude
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * return the start form submission date as timestamp i.e. process instance start date
	 */
	@Override
	public Date getTimestamp() {
		return submitted;
	}

	@Override
	public String toString() {
		return "StartLogItem [longitude=" + longitude + ", latitude="
				+ latitude + "endDate=" + endDate + ", performedByUser="
				+ performedByUser + ", processDefinitionUuid="
				+ processDefinitionUuid + ", processInstanceUuid="
				+ processInstanceUuid + ", activityDefinitionUuid="
				+ activityDefinitionUuid + ", activityInstanceUuid="
				+ activityInstanceUuid + ", activityName=" + activityName
				+ ", activityLabel=" + activityLabel + ", startDate="
				+ startDate + ", currentState=" + currentState
				+ ", lastStateUpdate=" + lastStateUpdate
				+ ", lastStateUpdateByUserId=" + lastStateUpdateByUserId
				+ ", startedBy=" + startedBy + ", guideUri=" + guideUri
				+ ", expectedEndDate=" + expectedEndDate + ", activityType="
				+ activityType + ", priority=" + priority
				+ ", processActivityFormInstanceId="
				+ processActivityFormInstanceId + ", page=" + page
				+ ", viewUrl=" + viewUrl + ", viewUrlExternal="
				+ viewUrlExternal + ", editUrl=" + editUrl
				+ ", editUrlExternal=" + editUrlExternal + ", dataUri="
				+ dataUri + ", definitionKey=" + definitionKey + ", typeId="
				+ typeId + ", instanceId=" + instanceId + ", actUri=" + actUri
				+ ", actinstId=" + actinstId + ", submitted=" + submitted
				+ ", submittedBy=" + submittedBy + "]";
	}
	
	
}
