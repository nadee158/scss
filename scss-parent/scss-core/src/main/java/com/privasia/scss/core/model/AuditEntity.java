/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonFormat;

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

	@Column(name = "ADD_BY", nullable = true)
	@CreatedBy
	private Long addBy;

	@Column(name = "UPDATE_BY", nullable = true)
	@LastModifiedBy
	private Long updateBy;

	@CreatedDate
	@Column(name = "DATETIME_ADD", nullable = true)
	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime dateTimeAdd;

	@LastModifiedDate
	@Column(name = "DATETIME_UPDATE", nullable = true)
	@JsonFormat(pattern = "MM/dd/yyyy hh:mm:ss a")
	private LocalDateTime dateTimeUpdate;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	public AuditEntity(Long addBy, Long updateBy, LocalDateTime dateTimeAdd, LocalDateTime dateTimeUpdate,
			Integer version) {
		super();
		this.addBy = addBy;
		this.updateBy = updateBy;
		this.dateTimeAdd = dateTimeAdd;
		this.dateTimeUpdate = dateTimeUpdate;
		this.version = version;
		
	}

	public AuditEntity() {
	}

	public Long getAddBy() {
		return addBy;
	}

	public void setAddBy(Long addBy) {
		this.addBy = addBy;
	}

	public Long getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(Long updateBy) {
		this.updateBy = updateBy;
	}

	public LocalDateTime getDateTimeAdd() {
		return dateTimeAdd;
	}

	public void setDateTimeAdd(LocalDateTime dateTimeAdd) {
		this.dateTimeAdd = dateTimeAdd;
	}

	public LocalDateTime getDateTimeUpdate() {
		return dateTimeUpdate;
	}

	public void setDateTimeUpdate(LocalDateTime dateTimeUpdate) {
		this.dateTimeUpdate = dateTimeUpdate;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}
	
}
