/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_PRINT_REJECT")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE")) })
public class PrintReject extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_PRINT_REJECT")
	@SequenceGenerator(name = "SEQ_SCSS_PRINT_REJECT", sequenceName = "SEQ_SCSS_PRINT_REJECT")
	@Column(name = "PRINT_REJECT_NO")
	private Long prientRejectID;

	@Column(name = "REJECT_DATE")
	private String rejectDate;

	@Column(name = "REJECT_TIME")
	private String rejectTime;

	@Column(name = "PM_HEAD_NO")
	private String pmHeadNo;

	@Column(name = "CONT_NO1")
	private String container01;

	@Column(name = "REJECT_DUE1C1")
	private String rejectContainer01Due01;

	@Column(name = "REJECT_DUE2C1")
	private String rejectContainer01Due02;

	@Column(name = "REJECT_DUE3C1")
	private String rejectContainer01Due03;

	@Column(name = "REMARKS1")
	private String container01Remarks;

	@Column(name = "CONT_NO2")
	private String container02;

	@Column(name = "REJECT_DUE1C2")
	private String rejectContainer02Due01;

	@Column(name = "REJECT_DUE2C2")
	private String rejectContainer02Due02;

	@Column(name = "REJECT_DUE3C2")
	private String rejectContainer02Due03;

	@Column(name = "REMARKS2")
	private String container02Remarks;

	@Column(name = "STAFF_NAME")
	private String staffName;

	@Column(name = "STAFF_NO")
	private String staffNumber;

	@Column(name = "STATUS")
	@Type(type = "com.privasia.scss.common.enumusertype.HPATReferStatusEnumUserType")
	private String status;

	@Column(name = "CLIENT_IP")
	private String clientIP;

	@Column(name = "DRIVER_NAME")
	private String driverName;

	@Column(name = "IC")
	private String driverIC;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "REFER_ID", nullable = false)
	private ReferReject referReject;

	public Long getPrientRejectID() {
		return prientRejectID;
	}

	public void setPrientRejectID(Long prientRejectID) {
		this.prientRejectID = prientRejectID;
	}
	
	public String getRejectDate() {
		return rejectDate;
	}

	public void setRejectDate(String rejectDate) {
		this.rejectDate = rejectDate;
	}
	
	public String getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(String rejectTime) {
		this.rejectTime = rejectTime;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}

	public String getContainer01() {
		return container01;
	}

	public void setContainer01(String container01) {
		this.container01 = container01;
	}

	public String getRejectContainer01Due01() {
		return rejectContainer01Due01;
	}

	public void setRejectContainer01Due01(String rejectContainer01Due01) {
		this.rejectContainer01Due01 = rejectContainer01Due01;
	}

	public String getRejectContainer01Due02() {
		return rejectContainer01Due02;
	}

	public void setRejectContainer01Due02(String rejectContainer01Due02) {
		this.rejectContainer01Due02 = rejectContainer01Due02;
	}

	public String getRejectContainer01Due03() {
		return rejectContainer01Due03;
	}

	public void setRejectContainer01Due03(String rejectContainer01Due03) {
		this.rejectContainer01Due03 = rejectContainer01Due03;
	}

	public String getContainer01Remarks() {
		return container01Remarks;
	}

	public void setContainer01Remarks(String container01Remarks) {
		this.container01Remarks = container01Remarks;
	}

	public String getContainer02() {
		return container02;
	}

	public void setContainer02(String container02) {
		this.container02 = container02;
	}

	public String getRejectContainer02Due01() {
		return rejectContainer02Due01;
	}

	public void setRejectContainer02Due01(String rejectContainer02Due01) {
		this.rejectContainer02Due01 = rejectContainer02Due01;
	}

	public String getRejectContainer02Due02() {
		return rejectContainer02Due02;
	}

	public void setRejectContainer02Due02(String rejectContainer02Due02) {
		this.rejectContainer02Due02 = rejectContainer02Due02;
	}

	public String getRejectContainer02Due03() {
		return rejectContainer02Due03;
	}

	public void setRejectContainer02Due03(String rejectContainer02Due03) {
		this.rejectContainer02Due03 = rejectContainer02Due03;
	}

	public String getContainer02Remarks() {
		return container02Remarks;
	}

	public void setContainer02Remarks(String container02Remarks) {
		this.container02Remarks = container02Remarks;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public void setStaffNumber(String staffNumber) {
		this.staffNumber = staffNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getClientIP() {
		return clientIP;
	}

	public void setClientIP(String clientIP) {
		this.clientIP = clientIP;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverIC() {
		return driverIC;
	}

	public void setDriverIC(String driverIC) {
		this.driverIC = driverIC;
	}

	public ReferReject getReferReject() {
		return referReject;
	}

	public void setReferReject(ReferReject referReject) {
		this.referReject = referReject;
	}

}
