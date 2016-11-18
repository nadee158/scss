package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.ODDGroup;
import com.privasia.scss.common.enums.RecordStatus;

@Entity
@Table(name = "SCSS_ODD_LOCATION")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEDBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE")) })
public class ODDLocation extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ODD_CODE")
	private String oddCode;

	@Column(name = "ODD_DESC")
	private String oddDesc;

	@Column(name = "ODD_GROUP")
	@Enumerated(EnumType.STRING)
	private ODDGroup oddGroup;

	@Column(name = "STATUS_CODE")
	@Type(type = "com.privasia.scss.core.util.enumusertype.RecordStatusEnumUserType")
	private RecordStatus statusCode;

	public String getOddCode() {
		return oddCode;
	}

	public void setOddCode(String oddCode) {
		this.oddCode = oddCode;
	}

	public String getOddDesc() {
		return oddDesc;
	}

	public void setOddDesc(String oddDesc) {
		this.oddDesc = oddDesc;
	}

	public ODDGroup getOddGroup() {
		return oddGroup;
	}

	public void setOddGroup(ODDGroup oddGroup) {
		this.oddGroup = oddGroup;
	}

	public RecordStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(RecordStatus statusCode) {
		this.statusCode = statusCode;
	}

	

}
