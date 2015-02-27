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

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "service_request_updates")
@XmlAccessorType(XmlAccessType.FIELD)
public class Open311v2p1ServiceRequestUpdates {


	@XmlElement(name ="request_update")
	private List<Open311v2p1ServiceRequestUpdate> open311v2p1ServiceRequestUpdates = new ArrayList<Open311v2p1ServiceRequestUpdate>();

	public List<Open311v2p1ServiceRequestUpdate> getOpen311v2p1ServiceRequestUpdates() {
		return open311v2p1ServiceRequestUpdates;
	}

	public void setOpen311v2p1ServiceRequestUpdates(
			List<Open311v2p1ServiceRequestUpdate> open311v2p1ServiceRequestUpdates) {
		this.open311v2p1ServiceRequestUpdates = open311v2p1ServiceRequestUpdates;
	}

	public void addOpen311v2p1ServiceRequestUpdate(
			Open311v2p1ServiceRequestUpdate open311v2p1ServiceRequestUpdate) {
		open311v2p1ServiceRequestUpdates.add(open311v2p1ServiceRequestUpdate);

	}

	@Override
	public String toString() {
		return "Open311v2p1ServiceRequestUpdates [open311v2p1ServiceRequestUpdates="
				+ open311v2p1ServiceRequestUpdates + "]";
	}
	
}
