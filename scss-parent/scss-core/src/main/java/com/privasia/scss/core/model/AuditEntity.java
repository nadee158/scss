/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * @author Janaka
 *
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditEntity implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ADD_BY")
	@CreatedBy
	private long addBy;
	
	@Column(name = "UPDATE_BY")
	@LastModifiedBy
	private long updateBy;
	
	@CreatedDate
	@Column(name = "DATETIME_ADD", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime dateTimeAdd;
	
	@LastModifiedDate
	@Column(name = "DATETIME_UPDATE", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	private ZonedDateTime dateTimeUpdate;
	
	@Version
	@Column(name = "VERSION")
    private long version;
	
	
	public AuditEntity(long addBy, long updateBy, ZonedDateTime dateTimeAdd, ZonedDateTime dateTimeUpdate,
			long version) {
		super();
		this.addBy = addBy;
		this.updateBy = updateBy;
		this.dateTimeAdd = dateTimeAdd;
		this.dateTimeUpdate = dateTimeUpdate;
		this.version = version;
	}
	
	public AuditEntity(){}

	public long getAddBy() {
		return addBy;
	}

	public void setAddBy(long addBy) {
		this.addBy = addBy;
	}

	public long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(long updateBy) {
		this.updateBy = updateBy;
	}

	public long getVersion() {
		return version;
	}

	public void setVersion(long version) {
		this.version = version;
	}

	public ZonedDateTime getDateTimeAdd() {
		return dateTimeAdd;
	}

	public void setDateTimeAdd(ZonedDateTime dateTimeAdd) {
		this.dateTimeAdd = dateTimeAdd;
	}

	public ZonedDateTime getDateTimeUpdate() {
		return dateTimeUpdate;
	}

	public void setDateTimeUpdate(ZonedDateTime dateTimeUpdate) {
		this.dateTimeUpdate = dateTimeUpdate;
	}

	

}
