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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;

@Entity
@Table(name = "SCSS_WH_ODD")
@AttributeOverrides({ @AttributeOverride(name = "addBy", column = @Column(name = "ODD_ADDBY")),
		@AttributeOverride(name = "updateBy", column = @Column(name = "ODD_UPDATEDBY")),
		@AttributeOverride(name = "dateTimeAdd", column = @Column(name = "ODD_DATEADD")),
		@AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "ODD_DATEUPDATE")) })
public class WHODD extends AuditEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_WH_ODD")
	@SequenceGenerator(name = "SEQ_SCSS_WH_ODD", sequenceName = "ODD_ID_SEQ")
	@Column(name = "ODD_ID_SEQ")
	private Long oddIdSeq;

	@Column(name = "ODD_HEADNO")
	private String pmHeadNo;

	@Column(name = "ODD_PLATENO")
	private String pmPlateNo;

	@Column(name = "TRANSACTION_SLIP_PRINTED", nullable = true)
	@Type(type = "yes_no")
	private boolean transactionSlipPrinted = false;

	@Column(name = "ODD_GATE_OUT_BOOTH_NO")
	private String gateOutBoothNo;

	@Column(name = "ODD_TIMEGATEIN")
	private LocalDateTime timeGateIn;

	@Column(name = "ODD_TIMEGATEINOK")
	private LocalDateTime timeGateInOk;

	@Column(name = "ODD_TIMEGATEOUT")
	private LocalDateTime timeGateOut;

	@Column(name = "ODD_TIMEGATEOUTOK")
	private LocalDateTime timeGateOutOk;

	@Column(name = "ODD_TIMEGATEOUT_BOOTH")
	private LocalDateTime timeGateOutBooth;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "ODD_HCTDID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ODD_GATEINCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ODD_GATEOUTCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEIN", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateInClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateOutClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ODD_GATEOUT_BOOTH_CLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutBoothClerk;

	@Column(name = "ODD_IMPEXPFLAG", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.ImpExpFlagEnumUserType")
	private ImpExpFlagStatus impExpFlag;

	@Column(name = "KIOSK_CANCEL_PICKUP", nullable = true)
	@Type(type = "yes_no")
	private boolean kioskCancelPickUp;

	@Column(name = "KIOSK_CONFIRMED", nullable = true)
	@Type(type = "yes_no")
	private boolean kioskConfirmed;

	@Column(name = "ODD_GATE_IN_STATUS", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus gateInStatus;

	@Column(name = "ODD_INOUTFLAG", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
	private GateInOutStatus inOutFlag;

	@Column(name = "ODD_REV_HEADNO_REM")
	private String reviseHeadNoRemarks;

	@Column(name = "ODD_REV_HEADNO")
	private String reviseHeadNo;

	@Column(name = "ODD_OLD_HEADNO")
	private String oldHeadNo;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNo", column = @Column(name = "ODD_CONTAINERNO1")),
			@AttributeOverride(name = "remarks", column = @Column(name = "ODD_REMARKS1")),
			@AttributeOverride(name = "oddStatus", column = @Column(name = "ODD_STATUS", nullable = true)),
			@AttributeOverride(name = "rejectionReason", column = @Column(name = "ODD_REJECTREASON")),
			@AttributeOverride(name = "fullOrEmpty", column = @Column(name = "ODD_F_E", nullable = true)),
			@AttributeOverride(name = "containerSize", column = @Column(name = "ODD_CONTAINERNO1_SIZE", nullable = true)),
			@AttributeOverride(name = "hdbsStatus", column = @Column(name = "HDBS_STATUS1", nullable = true)),
			@AttributeOverride(name = "hdbsArrivalStatus", column = @Column(name = "ARRIVAL_STATUS1", nullable = true)) })
	@AssociationOverrides({
			@AssociationOverride(name = "hdbsBkgDetailNo", joinColumns = @JoinColumn(name = "HDBS_BKG_DETAIL_NO_1", referencedColumnName = "BKG_DETAIL_ID", nullable = true)),
			@AssociationOverride(name = "location", joinColumns = @JoinColumn(name = "ODD_LOCATION", referencedColumnName = "ODD_CODE", nullable = true))})
	private ODDContainerDetails container01;

	@Embedded
	@AttributeOverrides({ @AttributeOverride(name = "containerNo", column = @Column(name = "ODD_CONTAINERNO2")),
			@AttributeOverride(name = "remarks", column = @Column(name = "ODD_REMARKS2")),
			@AttributeOverride(name = "oddStatus", column = @Column(name = "ODD_STATUS2", nullable = true)),
			@AttributeOverride(name = "rejectionReason", column = @Column(name = "ODD_REJECTREASON2")),
			@AttributeOverride(name = "fullOrEmpty", column = @Column(name = "ODD_F_E_2", nullable = true)),
			@AttributeOverride(name = "containerSize", column = @Column(name = "ODD_CONTAINERNO2_SIZE", nullable = true)),
			@AttributeOverride(name = "hdbsArrivalStatus", column = @Column(name = "ARRIVAL_STATUS2", nullable = true)) })
	@AssociationOverrides({
			@AssociationOverride(name = "hdbsBkgDetailNo", joinColumns = @JoinColumn(name = "HDBS_BKG_DETAIL_NO_2", referencedColumnName = "BKG_DETAIL_ID", nullable = true)),
			@AssociationOverride(name = "location", joinColumns = @JoinColumn(name = "ODD_LOCATION2", referencedColumnName = "ODD_CODE", nullable = true))})
	private ODDContainerDetails container02;

	@Column(name = "ODD_ZIP_FILE_NO")
	private String zipFileNo;

	@Column(name = "ODD_TRX_SLIP_NO")
	private String trxSlipNo;

	public Long getOddIdSeq() {
		return oddIdSeq;
	}

	public void setOddIdSeq(Long oddIdSeq) {
		this.oddIdSeq = oddIdSeq;
	}

	public String getPmHeadNo() {
		return pmHeadNo;
	}

	public void setPmHeadNo(String pmHeadNo) {
		if (StringUtils.isNotEmpty(pmHeadNo)) {
			pmHeadNo = StringUtils.upperCase(pmHeadNo);
		}
		this.pmHeadNo = pmHeadNo;
	}

	public String getPmPlateNo() {
		return pmPlateNo;
	}

	public void setPmPlateNo(String pmPlateNo) {
		if (StringUtils.isNotEmpty(pmPlateNo)) {
			pmPlateNo = StringUtils.upperCase(pmPlateNo);
		}
		this.pmPlateNo = pmPlateNo;
	}

	public boolean isTransactionSlipPrinted() {
		return transactionSlipPrinted;
	}

	public void setTransactionSlipPrinted(boolean transactionSlipPrinted) {
		this.transactionSlipPrinted = transactionSlipPrinted;
	}

	public String getGateOutBoothNo() {
		return gateOutBoothNo;
	}

	public void setGateOutBoothNo(String gateOutBoothNo) {
		this.gateOutBoothNo = gateOutBoothNo;
	}

	public LocalDateTime getTimeGateIn() {
		return timeGateIn;
	}

	public void setTimeGateIn(LocalDateTime timeGateIn) {
		this.timeGateIn = timeGateIn;
	}

	public LocalDateTime getTimeGateInOk() {
		return timeGateInOk;
	}

	public void setTimeGateInOk(LocalDateTime timeGateInOk) {
		this.timeGateInOk = timeGateInOk;
	}

	public LocalDateTime getTimeGateOut() {
		return timeGateOut;
	}

	public void setTimeGateOut(LocalDateTime timeGateOut) {
		this.timeGateOut = timeGateOut;
	}

	public LocalDateTime getTimeGateOutOk() {
		return timeGateOutOk;
	}

	public void setTimeGateOutOk(LocalDateTime timeGateOutOk) {
		this.timeGateOutOk = timeGateOutOk;
	}

	public LocalDateTime getTimeGateOutBooth() {
		return timeGateOutBooth;
	}

	public void setTimeGateOutBooth(LocalDateTime timeGateOutBooth) {
		this.timeGateOutBooth = timeGateOutBooth;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public SystemUser getGateInClerk() {
		return gateInClerk;
	}

	public void setGateInClerk(SystemUser gateInClerk) {
		this.gateInClerk = gateInClerk;
	}

	public SystemUser getGateOutClerk() {
		return gateOutClerk;
	}

	public void setGateOutClerk(SystemUser gateOutClerk) {
		this.gateOutClerk = gateOutClerk;
	}

	public Client getGateInClient() {
		return gateInClient;
	}

	public void setGateInClient(Client gateInClient) {
		this.gateInClient = gateInClient;
	}

	public Client getGateOutClient() {
		return gateOutClient;
	}

	public void setGateOutClient(Client gateOutClient) {
		this.gateOutClient = gateOutClient;
	}

	public SystemUser getGateOutBoothClerk() {
		return gateOutBoothClerk;
	}

	public void setGateOutBoothClerk(SystemUser gateOutBoothClerk) {
		this.gateOutBoothClerk = gateOutBoothClerk;
	}

	public ImpExpFlagStatus getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(ImpExpFlagStatus impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public boolean isKioskCancelPickUp() {
		return kioskCancelPickUp;
	}

	public void setKioskCancelPickUp(boolean kioskCancelPickUp) {
		this.kioskCancelPickUp = kioskCancelPickUp;
	}

	public boolean isKioskConfirmed() {
		return kioskConfirmed;
	}

	public void setKioskConfirmed(boolean kioskConfirmed) {
		this.kioskConfirmed = kioskConfirmed;
	}

	public TransactionStatus getGateInStatus() {
		return gateInStatus;
	}

	public void setGateInStatus(TransactionStatus gateInStatus) {
		this.gateInStatus = gateInStatus;
	}

	public GateInOutStatus getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(GateInOutStatus inOutFlag) {
		this.inOutFlag = inOutFlag;
	}


	public String getReviseHeadNoRemarks() {
		return reviseHeadNoRemarks;
	}

	public void setReviseHeadNoRemarks(String reviseHeadNoRemarks) {
		if(StringUtils.isNotEmpty(reviseHeadNoRemarks)){
			reviseHeadNoRemarks = StringUtils.upperCase(reviseHeadNoRemarks);
		}
		this.reviseHeadNoRemarks = reviseHeadNoRemarks;
	}

	public String getReviseHeadNo() {
		return reviseHeadNo;
	}

	public void setReviseHeadNo(String reviseHeadNo) {
		if (StringUtils.isNotEmpty(reviseHeadNo)) {
			reviseHeadNo = StringUtils.upperCase(reviseHeadNo);
		}
		this.reviseHeadNo = reviseHeadNo;
	}

	public String getOldHeadNo() {
		return oldHeadNo;
	}

	public void setOldHeadNo(String oldHeadNo) {
		if (StringUtils.isNotEmpty(oldHeadNo)) {
			oldHeadNo = StringUtils.upperCase(oldHeadNo);
		}
		this.oldHeadNo = oldHeadNo;
	}

	public ODDContainerDetails getContainer01() {
		return container01;
	}

	public void setContainer01(ODDContainerDetails container01) {
		this.container01 = container01;
	}

	public ODDContainerDetails getContainer02() {
		return container02;
	}

	public void setContainer02(ODDContainerDetails container02) {
		this.container02 = container02;
	}

	public String getZipFileNo() {
		return zipFileNo;
	}

	public void setZipFileNo(String zipFileNo) {
		this.zipFileNo = zipFileNo;
	}

	public String getTrxSlipNo() {
		return trxSlipNo;
	}

	public void setTrxSlipNo(String trxSlipNo) {
		this.trxSlipNo = trxSlipNo;
	}

}
