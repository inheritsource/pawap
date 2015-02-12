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

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;

/** To be used for 
 *  POST Service Request 
 *  according to the Open311 v2  Spec
 * 
 * 
 *
 */

@XmlRootElement(name="service_request")
public class Open311v2ServiceRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6685424882135297850L;
	/**
	 * 
	 */

	private String jurisdiction_id ; 
	private String service_code ; 
	//location parameter:
	// According to spec :
	// A full location parameter must be submitted. 	One of lat & long or address_string or address_id
	// Here only lat and long is used
	private String lat ; 
	private String lon ; // Notice the name! 
	private String address_string ;
	private String address_id ;
	//TODO attribute : An array of key/value responses based on Service 
	// Definitions.
	// According to spec: This takes the form of attribute[code]=value where multiple code/value pairs can be specified as well as multiple values for the same code in the case of a multivaluelist datatype (attribute[code1][]=value1&attribute[code1][]=value2&attribute[code1][]=value3) - see example.
    //This is only required if the service_code requires a service definition with required fields`.
	private String email; //The email address of the person submitting the request
	private String device_id; //The unique device ID of the device submitting the request
	                  // This is usually only used for mobile devices.
	private String account_id; //The unique ID for the user account of the person submitting 
	private String first_name; //The given name of the person submitting the request
	private String last_name; //The family name of the person submitting the request
	private String phone; //The phone number of the person submitting the request
	private String description; //A full description of the request or report being submitted.
	                    // This may contain line breaks, but not html or code
	                    // Otherwise, this is free form text limited to 4,000 characters.
	private String media_url; // A URL to media associated with the request, eg an image.
	                  // A convention for parsing media from this URL has yet to be established, so currently it will be done on a case by case basis much like Twitter.com does. For example, if a jurisdiction accepts photos submitted via Twitpic.com, then clients can parse the page at the Twitpic URL for the image given the conventions of Twitpic.com. 
	                  // This could also be a URL to a media RSS feed where the clients can parse for 
	// media in a more structured way.
	public String getJurisdiction_id() {
		return jurisdiction_id;
	}
	public void setJurisdiction_id(String jurisdiction_id) {
		this.jurisdiction_id = jurisdiction_id;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getLat() {
		return lat;
	}
	public void setLat(String lat) {
		this.lat = lat;
	}
	public String getLon() {
		return lon;
	}
	public void setLon(String lon) {
		this.lon = lon;
	}
	
	public String getAddress_string() {
		return address_string;
	}
	public void setAddress_string(String address_string) {
		this.address_string = address_string;
	}
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getAccount_id() {
		return account_id;
	}
	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}
	public String getFirst_name() {
		return first_name;
	}
	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}
	public String getLast_name() {
		return last_name;
	}
	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
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
	public int hashCode() {
		// TODO Auto-generated method stub
		return super.hashCode();
	}
	@Override
	public String toString() {
		return email + device_id + account_id + first_name + last_name + phone +  lon+ lat + description + media_url;
	}
	
	
}
