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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement ;
import java.util.ArrayList;
import java.util.List;



/**
 * To be used for POST Service Request Response according to the Open311 v2 Spec
 * 
 * 
 * 
 */


@XmlRootElement(name="service_requests")
@XmlAccessorType(XmlAccessType.FIELD)

public class Open311v2ServiceResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3427695909797374187L;
	/**
	 * 
	 */
	@XmlElement(name ="request")
	private List<Open311v2ServiceResponseItem>  open311v2ServiceResponseItems= new ArrayList<Open311v2ServiceResponseItem>();

	public List<Open311v2ServiceResponseItem> getOpen311v2ServiceResponseItems() {
		return open311v2ServiceResponseItems;
	}

	public void setOpen311v2ServiceResponseItems(
			List<Open311v2ServiceResponseItem> open311v2ServiceResponseItems) {
		this.open311v2ServiceResponseItems = open311v2ServiceResponseItems;
	}  
	
	public void addOpen311v2ServiceResponseItem( Open311v2ServiceResponseItem  open311v2ServiceResponseItem ) 
	 {
		open311v2ServiceResponseItems.add(open311v2ServiceResponseItem);
	}  
	
	
	

}
