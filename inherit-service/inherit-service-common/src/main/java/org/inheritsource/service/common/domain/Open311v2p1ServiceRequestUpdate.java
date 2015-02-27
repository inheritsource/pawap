/* == Motrice Copyright Notice ==
 *
 * Motrice Service Platform
 *
 * Copyright (C) 2011-2015 Motrice AB
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

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "request_update")
public class Open311v2p1ServiceRequestUpdate {

	private String update_id; // The ID of the service request update.
	private String service_request_id; // The ID of the service request that the update
								// is for.
	private String status; // The status of the service request after the update.
					// Possible values: OPEN, CLOSED
	private String updated_datetime; // The datetime of the update. In W3 format
	private String description; // The text of the update.
	private String media_url; // The URL of any associated media, e.g an image
	
	public String getUpdate_id() {
		return update_id;
	}
	public void setUpdate_id(String update_id) {
		this.update_id = update_id;
	}
	public String getService_request_id() {
		return service_request_id;
	}
	public void setService_request_id(String service_request_id) {
		this.service_request_id = service_request_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getUpdated_datetime() {
		return updated_datetime;
	}
	public void setUpdated_datetime(String updated_datetime) {
		this.updated_datetime = updated_datetime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}
	@Override
	public String toString() {
		return "Open311v2p1ServiceRequestUpdate [update_id=" + update_id
				+ ", service_request_id=" + service_request_id + ", status="
				+ status + ", updated_datetime=" + updated_datetime
				+ ", description=" + description + ", media_url=" + media_url
				+ "]";
	}

}
