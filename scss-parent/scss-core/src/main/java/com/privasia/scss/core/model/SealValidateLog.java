package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

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

@Entity
@Table(name = "SCSS_SEAL_VALIDATE_LOG")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATECREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATEUPDATE")) })
public class SealValidateLog extends AuditEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_SEAL_VALIDATE_LOG")
	@SequenceGenerator(name = "SEQ_SCSS_SEAL_VALIDATE_LOG", sequenceName = "SEQ_SCSS_SEAL_VALIDATE_LOG", allocationSize = 1)
	@Column(name = "SEAL_VALIDATE_LOG_ID")
	private Long sealValidateLogId;

	@Column(name = "SHIPPING_LINE")
	private String shippingLine;

	@Column(name = "VESSEL_SCN")
	private String vesselSCN;

	@Column(name = "VESSEL_NAME")
	private String vesselName;

	@Column(name = "SEAL_LINE_CODE")
	private String sealLineCode;

	@Column(name = "GATE_IN_TIME")
	private LocalDateTime gateInTime;

	@Column(name = "BOOKING_NO")
	private String bookingNo;

	@Column(name = "CONT_NO")
	private String contNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GATE_IN_CLERK_ID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;

	public Long getSealValidateLogId() {
		return sealValidateLogId;
	}

	public void setSealValidateLogId(Long sealValidateLogId) {
		this.sealValidateLogId = sealValidateLogId;
	}

	public String getShippingLine() {
		return shippingLine;
	}

	public void setShippingLine(String shippingLine) {
		this.shippingLine = shippingLine;
	}

	public String getVesselSCN() {
		return vesselSCN;
	}

	public void setVesselSCN(String vesselSCN) {
		this.vesselSCN = vesselSCN;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getSealLineCode() {
		return sealLineCode;
	}

	public void setSealLineCode(String sealLineCode) {
		this.sealLineCode = sealLineCode;
	}

	public LocalDateTime getGateInTime() {
		return gateInTime;
	}

	public void setGateInTime(LocalDateTime gateInTime) {
		this.gateInTime = gateInTime;
	}

	public String getBookingNo() {
		return bookingNo;
	}

	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public SystemUser getGateInClerk() {
		return gateInClerk;
	}

	public void setGateInClerk(SystemUser gateInClerk) {
		this.gateInClerk = gateInClerk;
	}

}
