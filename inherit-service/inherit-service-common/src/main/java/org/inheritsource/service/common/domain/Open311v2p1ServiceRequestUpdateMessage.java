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

@XmlRootElement(name = "request_update_message")
/** 
 * 
 * Used for creating Orbeon form for
 * Open311 FMS extension
 * POST Service Request Update
 *
 */
public class Open311v2p1ServiceRequestUpdateMessage {

	private String jurisdiction_id; 
	private String update_id ; 
	private String service_request_id ; 
	private String updated_datetime ; 
	private String status ; 
	private String description ; 
	private String email ; 
	private String last_name ; 
	private String first_name ; 
	private String title ; 
	private String media_url ; 
	private String account_id ;
	
	public String getJurisdiction_id() {
		return jurisdiction_id;
	}
	public void setJurisdiction_id(String jurisdiction_id) {
		this.jurisdiction_id = jurisdiction_id;
	}
	
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
	public String getUpdated_datetime() {
		return updated_datetime;
	}
	public void setUpdated_datetime(String updated_datetime) {
		this.updated_datetime = updated_datetime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getMedia_url() {
		return media_url;
	}
	public void setMedia_url(String media_url) {
		this.media_url = media_url;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	@Override
	public String toString() {
		return "Open311v2p1ServiceRequestUpdateMessage [jurisdiction_id="
				+ jurisdiction_id + ", update_id=" + update_id
				+ ", service_request_id=" + service_request_id
				+ ", updated_datetime=" + updated_datetime + ", status="
				+ status + ", description=" + description + ", email=" + email
				+ ", last_name=" + last_name + ", first_name=" + first_name
				+ ", title=" + title + ", media_url=" + media_url
				+ ", account_id=" + account_id + "]";
	} 

}
