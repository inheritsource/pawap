/* == Motrice Copyright Notice == 
 * 
 * Motrice Service Platform 
 * 
 * Copyright (C) 2011-2014 Motrice AB 
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
 
 
package org.inheritsource.taskform.engine.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="mtf_process_activity_tag")
public class ProcessActivityTag {

	@Id
	@GeneratedValue
	@Column(name="process_activity_tag_id")
	private Long processActivityTagId;

	@ManyToOne
    @JoinColumn(name="tag_type_id", nullable=false)
	private TagType type;

	@Column(name="procinst_id")
	private String procinstId;
	
	@Column(name="actinst_id")
	private String actinstId;

	@Column(nullable=false)
	private String value;
	

	@Column(nullable=false)
	private Date timestamp = null;
	

	@Column(nullable=false)
	private String userId;
	
	public ProcessActivityTag() {
		
	}

	/** {@link ProcessActivityTag#}
	 * TODO
	 * 
	 */
	public Long getProcessActivityTagId() {
		return processActivityTagId;
	}

	public void setProcessActivityTagId(Long processActivityTagId) {
		this.processActivityTagId = processActivityTagId;
	}

	/** {@link ProcessActivityTag#type}
	 * TODO
	 * 
	 */
	public TagType getType() {
		return type;
	}

	public void setType(TagType type) {
		this.type = type;
	}

	/** {@link ProcessActivityTag#procinstId}
	 * TODO
	 * 
	 */
	public String getProcinstId() {
		return procinstId;
	}

	public void setProcinstId(String procinstId) {
		this.procinstId = procinstId;
	}

	/** {@link ProcessActivityTag#actinstId}
	 * TODO
	 * 
	 */
	public String getActinstId() {
		return actinstId;
	}

	public void setActinstId(String actinstId) {
		this.actinstId = actinstId;
	}

	/** {@link ProcessActivityTag#value}
	 * TODO
	 * 
	 */	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	/** {@link ProcessActivityTag#timestamp}
	 * Timestamp of last write
	 */
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	/** {@link ProcessActivityTag#userId}
	 * TODO
	 * The last writer
	 */
	public String getUserId() {
		return userId;
	}
	

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actinstId == null) ? 0 : actinstId.hashCode());
		result = prime
				* result
				+ ((processActivityTagId == null) ? 0 : processActivityTagId
						.hashCode());
		result = prime * result
				+ ((procinstId == null) ? 0 : procinstId.hashCode());
		result = prime * result
				+ ((timestamp == null) ? 0 : timestamp.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ProcessActivityTag other = (ProcessActivityTag) obj;
		if (actinstId == null) {
			if (other.actinstId != null)
				return false;
		} else if (!actinstId.equals(other.actinstId))
			return false;
		if (processActivityTagId == null) {
			if (other.processActivityTagId != null)
				return false;
		} else if (!processActivityTagId.equals(other.processActivityTagId))
			return false;
		if (procinstId == null) {
			if (other.procinstId != null)
				return false;
		} else if (!procinstId.equals(other.procinstId))
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ProcessActivityTag [processActivityTagId="
				+ processActivityTagId + ", type=" + type + ", procinstId="
				+ procinstId + ", actinstId=" + actinstId + ", value=" + value
				+ ", timestamp=" + timestamp + ", userId=" + userId + "]";
	}

		
}
