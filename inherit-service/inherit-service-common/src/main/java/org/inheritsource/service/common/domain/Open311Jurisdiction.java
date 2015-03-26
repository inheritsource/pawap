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


/** 
 * 
 * Used for the parameters which define what process and form 
 * to be used for a 
 * Open311 API
 *
 */
public class Open311Jurisdiction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2892801410710782141L;
	private String jurisdiction_id; 
        private String full_name ; 
        private boolean enabled_flag ; 
        private String service_notice ; 
        private String procdef_id ; 
        private String[] startForms ;
        
        public Open311Jurisdiction() {

        }

        
		public String getJurisdiction_id() {
			return jurisdiction_id;
		}
		public void setJurisdiction_id(String jurisdiction_id) {
			this.jurisdiction_id = jurisdiction_id;
		}
		public String getFull_name() {
			return full_name;
		}
		public void setFull_name(String full_name) {
			this.full_name = full_name;
		}
		public boolean isEnabled_flag() {
			return enabled_flag;
		}
		public void setEnabled_flag(boolean enabled_flag) {
			this.enabled_flag = enabled_flag;
		}
		public String getService_notice() {
			return service_notice;
		}
		public void setService_notice(String service_notice) {
			this.service_notice = service_notice;
		}
		public String getProcdef_id() {
			return procdef_id;
		}
		public void setProcdef_id(String procdef_id) {
			this.procdef_id = procdef_id;
		}
		public String[] getStartForms() {
			return startForms;
		}
		public void setStartForms(String[] startForms) {
			this.startForms = startForms;
		}
		
		public String getStartFormExistPath(){
			if (startForms.length > 0) {
				return startForms[0].replace("--", "/");
			} else {
				return null ; 
			}
		}
		
		public String getStartForm(){
			if (startForms.length > 0) {
				return startForms[0];
			} else {
				return null ; 
			}
		}
		
		
		@Override
		public String toString() {
			return "Open311Jurisdiction [jurisdiction_id=" + jurisdiction_id
					+ ", full_name=" + full_name + ", enabled_flag="
					+ enabled_flag 
					+ ", service_notice=" + service_notice
					+ ", procdef_id=" + procdef_id + ", startForms="
					+ startForms 
					+ "]";
		} 
        
        
}
