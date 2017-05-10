package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
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

import com.privasia.scss.common.enums.LpkClassType;

@Entity
@Table(name = "SCSS_DG_VALIDATE_LOG")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "CREATEDBY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "UPDATEDBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATE")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE")) })
public class DGValidateLog extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_DG_VALIDATE_LOG")
	@SequenceGenerator(name = "SEQ_SCSS_DG_VALIDATE_LOG", sequenceName = "SEQ_SCSS_DG_VALIDATE_LOG")
	@Column(name = "DG_VALIDATE_LOG_ID")
	private Long dgValidateLogId;

	@Column(name = "LPK_CLASS")
	@Type(type = "com.privasia.scss.common.enumusertype.LpkClassTypeEnumUserType")
	private LpkClassType lpkClass;

	@Column(name = "LPK_APPROVAL")
	private String lpkApproval;

	@Column(name = "GATE_IN_TIME")
	private LocalDateTime gateInTime;

	@Column(name = "CONT_NO")
	private String contNo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GATE_IN_CLERK_ID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;

	@Column(name = "GATE_IN_DURING_WINDOW")
	private int gateInDuringWindow;

	@Column(name = "DG_BYPASS_REMARK")
	private String dgByPassRemark;

	@ManyToOne(fetch = FetchType.LAZY, cascade=CascadeType.MERGE)
	@JoinColumn(name = "EXP_EXPORTNO_SEQ", nullable = true, referencedColumnName = "EXP_EXPORTNO_SEQ")
	private Exports exports;

	public Long getDgValidateLogId() {
		return dgValidateLogId;
	}

	public void setDgValidateLogId(Long dgValidateLogId) {
		this.dgValidateLogId = dgValidateLogId;
	}

	public String getLpkApproval() {
		return lpkApproval;
	}

	public void setLpkApproval(String lpkApproval) {
		this.lpkApproval = lpkApproval;
	}

	public LocalDateTime getGateInTime() {
		return gateInTime;
	}

	public void setGateInTime(LocalDateTime gateInTime) {
		this.gateInTime = gateInTime;
	}

	public String getContNo() {
		return contNo;
	}

	public void setContNo(String contNo) {
		this.contNo = contNo;
	}

	public int getGateInDuringWindow() {
		return gateInDuringWindow;
	}

	public void setGateInDuringWindow(int gateInDuringWindow) {
		this.gateInDuringWindow = gateInDuringWindow;
	}

	public String getDgByPassRemark() {
		return dgByPassRemark;
	}

	public void setDgByPassRemark(String dgByPassRemark) {
		this.dgByPassRemark = dgByPassRemark;
	}

	public LpkClassType getLpkClass() {
		return lpkClass;
	}

	public void setLpkClass(LpkClassType lpkClass) {
		this.lpkClass = lpkClass;
	}

	public SystemUser getGateInClerk() {
		return gateInClerk;
	}

	public void setGateInClerk(SystemUser gateInClerk) {
		this.gateInClerk = gateInClerk;
	}

	public Exports getExports() {
		return exports;
	}

	public void setExports(Exports exports) {
		this.exports = exports;
	}

}
