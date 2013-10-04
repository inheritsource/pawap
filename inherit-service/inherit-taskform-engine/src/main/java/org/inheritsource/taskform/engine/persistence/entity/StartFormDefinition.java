/* 
 *  Process Aware Web Application Platform 
 * 
 *  Copyright (C) 2011-2013 Inherit S AB 
 * 
 *  This program is free software: you can redistribute it and/or modify 
 *  it under the terms of the GNU Affero General Public License as published by 
 *  the Free Software Foundation, either version 3 of the License, or 
 *  (at your option) any later version. 
 * 
 *  This program is distributed in the hope that it will be useful, 
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of 
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the 
 *  GNU Affero General Public License for more details. 
 * 
 *  You should have received a copy of the GNU Affero General Public License 
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>. 
 * 
 *  e-mail: info _at_ inherit.se 
 *  mail: Inherit S AB, Långsjövägen 8, SE-131 33 NACKA, SWEDEN 
 *  phone: +46 8 641 64 14 
 */ 
 
package org.inheritsource.taskform.engine.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A given form path can have an associated processDefinitionUuid that will 
 * be started on submit. 
 * Note that different form paths can start the same process.
 * @author bjmo
 *
 */
@Entity
@Table(name="mtf_start_form_definition")
public class StartFormDefinition { 

	/**
	 * Assign the case start event to a user. 
	 * <ul>
	 *   <li>USERSESSION logged on user from session. Do not allow unauthenticated user.</li>
	 *   
	 *   <li>USERSESSION_FORMDATA strategy to find out user:
	 *     <ol>
	 *       <li>logged on user from session</li>
	 *       <li>form data defined by userDataXPath</li>
	 *     </ol>
	 * 		Do not allow anonymous user to submit start form
	 *   </li>
	 *   
	 *   <li>FORMDATA_USERSESSION strategy to find out user:
	 *     <ol>
	 *       <li>form data defined by userDataXPath</li>
	 *       <li>logged on user from session</li>
	 *     </ol>
	 * 		Do not allow anonymous user to submit start form
	 *   </li>
	 *   
	 *   <li>USERSESSION_FORMDATA_ANONYMOUS strategy to find out user:
	 *     <ol>
	 *       <li>logged on user from session</li>
	 *       <li>form data defined by userDataXPath</li>
	 *       <li>Anonymous user</li>
	 *     </ol>
	 *   </li>
	 *   
	 *   <li>FORMDATA_USERSESSION_ANONYMOUS strategy to find out user:
	 *     <ol>
	 *       <li>form data defined by userDataXPath</li>
	 *       <li>logged on user from session</li>
	 *       <li>Anonymous user</li>
	 *     </ol>
	 *   </li>
	 *   
	 *   <li>ANONYMOUS - Assign the case to anonymous user</li>
	 *
	 * </ul>
	 * @author bjmo
	 *
	 */
	public enum AuthTypes {USERSESSION, USERSESSION_FORMDATA, FORMDATA_USERSESSION, USERSESSION_FORMDATA_ANONYMOUS, FORMDATA_USERSESSION_ANONYMOUS, ANONYMOUS};

	
	@Id
	@GeneratedValue
	Long startFormDefinitionId;
	
	/**
	 * Process definition uuid to start when this start form is submitted
	 */
	String processDefinitionUuid;
		
	/**
	 *  Path to start form
	 */
	@Column(nullable = false, unique = true)
	String formPath;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	AuthTypes authTypeReq;
	
	String userDataXPath;

	public StartFormDefinition() {
		
	}

	public Long getStartFormDefinitionId() {
		return startFormDefinitionId;
	}

	public void setStartFormDefinitionId(Long startFormDefinitionId) {
		this.startFormDefinitionId = startFormDefinitionId;
	}

	public String getProcessDefinitionUuid() {
		return processDefinitionUuid;
	}

	public void setProcessDefinitionUuid(String processDefinitionUuid) {
		this.processDefinitionUuid = processDefinitionUuid;
	}

	public String getFormPath() {
		return formPath;
	}

	public void setFormPath(String formPath) {
		this.formPath = formPath;
	}

	public AuthTypes getAuthTypeReq() {
		return authTypeReq;
	}

	public void setAuthTypeReq(AuthTypes authTypeReq) {
		this.authTypeReq = authTypeReq;
	}

	/**
	 * The user data XPath defines where user data in the form data can be found. In use when authTypeReq
	 * defines that the start form submit should try to look up user from form data.
	 * @return
	 */
	public String getUserDataXPath() {
		return userDataXPath;
	}

	/**
	 * The user data XPath defines where user data in the form data can be found. In use when authTypeReq
	 * defines that the start form submit should try to look up user from form data.
	 * @param userDataXPath
	 */
	public void setUserDataXPath(String userDataXPath) {
		this.userDataXPath = userDataXPath;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((authTypeReq == null) ? 0 : authTypeReq.hashCode());
		result = prime * result
				+ ((formPath == null) ? 0 : formPath.hashCode());
		result = prime
				* result
				+ ((processDefinitionUuid == null) ? 0 : processDefinitionUuid
						.hashCode());
		result = prime
				* result
				+ ((startFormDefinitionId == null) ? 0 : startFormDefinitionId
						.hashCode());
		result = prime * result
				+ ((userDataXPath == null) ? 0 : userDataXPath.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StartFormDefinition other = (StartFormDefinition) obj;
		if (authTypeReq != other.authTypeReq)
			return false;
		if (formPath == null) {
			if (other.formPath != null)
				return false;
		} else if (!formPath.equals(other.formPath))
			return false;
		if (processDefinitionUuid == null) {
			if (other.processDefinitionUuid != null)
				return false;
		} else if (!processDefinitionUuid.equals(other.processDefinitionUuid))
			return false;
		if (startFormDefinitionId == null) {
			if (other.startFormDefinitionId != null)
				return false;
		} else if (!startFormDefinitionId.equals(other.startFormDefinitionId))
			return false;
		if (userDataXPath == null) {
			if (other.userDataXPath != null)
				return false;
		} else if (!userDataXPath.equals(other.userDataXPath))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "StartFormDefinition [startFormDefinitionId="
				+ startFormDefinitionId + ", processDefinitionUuid="
				+ processDefinitionUuid + ", formPath=" + formPath
				+ ", authTypeReq=" + authTypeReq + ", userDataXPath="
				+ userDataXPath + "]";
	}

}
