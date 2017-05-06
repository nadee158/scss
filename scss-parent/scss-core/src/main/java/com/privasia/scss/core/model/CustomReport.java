package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.TransactionType;

@Entity
@Table(name = "SCSS_CUSTOMS_REPORT")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE")) })
public class CustomReport extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "CSM_SEQ_ID")
	private Long customsReportID;

	@Column(name = "CSM_FLAG", nullable = false)
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
	private TransactionType csmFlag;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CSM_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client csmGateOutClient;

	@Column(name = "CSM_TIMEGATEOUT", nullable = true)
	private LocalDateTime timeGateOut;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ODD_ID_SEQ", nullable = true, referencedColumnName = "ODD_ID_SEQ")
	private WHODD oddID;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "CSM_CONTAINERNO1")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CSM_FULLEMPTY_FLAGC1")),
			@AttributeOverride(name = "gcsDelcarerNo", column = @Column(name = "GCS_DELCARENOC1")),
			@AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION")),
			@AttributeOverride(name = "customRejection", column = @Column(name = "CSM_REJECTREASON")),
			@AttributeOverride(name = "customEirStatus", column = @Column(name = "CSM_EIRSTATUS")),
			@AttributeOverride(name = "cusGCSReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC1")),
			@AttributeOverride(name = "gatePassIssuedDate", column = @Column(name = "GATE_PASS_DATEC1")),
			@AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC1")) })
	@AssociationOverrides({
			@AssociationOverride(name = "gatePassID", joinColumns = @JoinColumn(name = "GATE_PASS_NO1", referencedColumnName = "GTP_PASSNO", nullable = true)),
			@AssociationOverride(name = "exportID", joinColumns = @JoinColumn(name = "EXP_EXPORTNO_SEQ1", referencedColumnName = "EXP_EXPORTNO_SEQ", nullable = true)) })
	private CustomContainer container01;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNumber", column = @Column(name = "CSM_CONTAINERNO2")),
			@AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CSM_FULLEMPTY_FLAGC2")),
			@AttributeOverride(name = "gcsDelcarerNo", column = @Column(name = "GCS_DELCARENOC2")),
			@AttributeOverride(name = "oddLocation", column = @Column(name = "ODD_LOCATION2")),
			@AttributeOverride(name = "customRejection", column = @Column(name = "CSM_REJECTREASON2")),
			@AttributeOverride(name = "customEirStatus", column = @Column(name = "CSM_EIRSTATUS2")),
			@AttributeOverride(name = "cusGCSReleaseDate", column = @Column(name = "GCS_RELEASE_DATEC2")),
			@AttributeOverride(name = "gatePassIssuedDate", column = @Column(name = "GATE_PASS_DATEC2")),
			@AttributeOverride(name = "portPoliceDate", column = @Column(name = "PORT_POLICE_DATEC2")) })
	@AssociationOverrides({
			@AssociationOverride(name = "gatePassID", joinColumns = @JoinColumn(name = "GATE_PASS_NO2", referencedColumnName = "GTP_PASSNO", nullable = true)),
			@AssociationOverride(name = "exportID", joinColumns = @JoinColumn(name = "EXP_EXPORTNO_SEQ2", referencedColumnName = "EXP_EXPORTNO_SEQ", nullable = true)) })
	private CustomContainer container02;


	public Long getCustomsReportID() {
		return customsReportID;
	}

	public void setCustomsReportID(Long customsReportID) {
		this.customsReportID = customsReportID;
	}

	public TransactionType getCsmFlag() {
		return csmFlag;
	}

	public void setCsmFlag(TransactionType csmFlag) {
		this.csmFlag = csmFlag;
	}

	public Client getCsmGateOutClient() {
		return csmGateOutClient;
	}

	public void setCsmGateOutClient(Client csmGateOutClient) {
		this.csmGateOutClient = csmGateOutClient;
	}

	public LocalDateTime getTimeGateOut() {
		return timeGateOut;
	}

	public void setTimeGateOut(LocalDateTime timeGateOut) {
		this.timeGateOut = timeGateOut;
	}

	public WHODD getOddID() {
		return oddID;
	}

	public void setOddID(WHODD oddID) {
		this.oddID = oddID;
	}

	public CustomContainer getContainer01() {
		return container01;
	}

	public void setContainer01(CustomContainer container01) {
		this.container01 = container01;
	}

	public CustomContainer getContainer02() {
		return container02;
	}

	public void setContainer02(CustomContainer container02) {
		this.container02 = container02;
	}

}
