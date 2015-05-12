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




/**
 * To be used for POST Service Request Response according to the Open311 v2 Spec
 * 
 * 
 * 
 */

@XmlRootElement(name="request")
public class Open311v2ServiceResponseItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1385714695001341714L;
	private String service_notice;
	private String account_id;
	private String service_request_id ; 
	public String getService_notice() {
		return service_notice;
	}

	public void setService_notice(String service_notice) {
		this.service_notice = service_notice;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}


	public String getService_request_id() {
		return service_request_id;
	}

	public void setService_request_id(String service_request_id) {
		this.service_request_id = service_request_id;
	}

	@Override
	public String toString() {
		return "service_notice=" + service_notice + " account_id=" + account_id;
	}

}
