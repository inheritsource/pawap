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
 * To be used for GET Service List according to the Open311 v2 Spec
 * 
 */


@XmlRootElement(name="services")
@XmlAccessorType(XmlAccessType.FIELD)

public class Open311v2Services implements Serializable {

	public Open311v2Services() {
		open311v2Services = new ArrayList<Open311v2Service>() ; 
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 496675683138885225L;
	/**
	 * 
	 */

	@XmlElement(name ="service")
	private List<Open311v2Service> open311v2Services ;

	public List<Open311v2Service> getOpen311v2Service() {
		return open311v2Services;
	}

	public void setOpen311v2Service(List<Open311v2Service> open311v2Service) {
		this.open311v2Services = open311v2Service;
	}

	public void add(Open311v2Service open311v2Service) {
		open311v2Services.add(open311v2Service)  ; 
		
	} 

	
}
