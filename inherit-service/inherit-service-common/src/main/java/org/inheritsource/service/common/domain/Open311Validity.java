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


import javax.xml.bind.annotation.XmlElement;


//   @XmlAccessorType(XmlAccessType.FIELD)
/** 
 * 
 * Used for the parameters which define what process and form 
 * to be used for a 
 * Open311 API
 *
 */
public class Open311Validity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4493330305161301502L;
	@XmlElement(name="jurisdiction")
	private Open311Jurisdiction open311Jurisdiction;

	public Open311Validity() {
	}

	public Open311Jurisdiction getOpen311Jurisdiction() {
		return open311Jurisdiction;
	}

	public void setOpen311Jurisdiction(Open311Jurisdiction open311Jurisdiction) {
		this.open311Jurisdiction = open311Jurisdiction;
	}

	@Override
	public String toString() {
		return "Open311Validity [open311Jurisdiction=" + open311Jurisdiction
				+ "]";
	}

}
