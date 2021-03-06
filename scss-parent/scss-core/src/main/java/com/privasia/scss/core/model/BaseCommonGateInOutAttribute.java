/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.TransactionStatus;

/**
 * @author Janaka
 *
 */
@Embeddable
public class BaseCommonGateInOutAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String pmHeadNo;

	private String pmPlateNo;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "BOOKING_ID", nullable = true, referencedColumnName = "BOOKING_ID")
	private HPABBooking hpabBooking;

	@Type(type = "com.privasia.scss.common.enumusertype.TransactionStatusEnumUserType")
	private TransactionStatus eirStatus;

	@Type(type = "yes_no")
	private Boolean transactionSlipPrinted;

	private String gateOutBoothNo;

	private LocalDateTime timeGateIn;

	private LocalDateTime timeGateInOk;

	private LocalDateTime timeGateOut;

	private LocalDateTime timeGateOutOk;

	private LocalDateTime timeGateOutBooth;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
	@JoinColumn(name = "GTP_HCTDID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GTP_GATEINCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateInClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GTP_GATEOUTCLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutClerk;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEIN", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateInClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "CLI_CLIENTID_GATEOUT", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
	private Client gateOutClient;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GATEOUT_BOOTH_CLERKID", nullable = true, referencedColumnName = "SYS_USERID_SEQ")
	private SystemUser gateOutBoothClerk;

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

	public TransactionStatus getEirStatus() {
		return eirStatus;
	}

	public void setEirStatus(TransactionStatus eirStatus) {
		this.eirStatus = eirStatus;
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

	public SystemUser getGateOutBoothClerk() {
		return gateOutBoothClerk;
	}

	public void setGateOutBoothClerk(SystemUser gateOutBoothClerk) {
		this.gateOutBoothClerk = gateOutBoothClerk;
	}

	public Boolean isTransactionSlipPrinted() {
		return transactionSlipPrinted;
	}

	public void setTransactionSlipPrinted(Boolean transactionSlipPrinted) {
		this.transactionSlipPrinted = transactionSlipPrinted;
	}

	public HPABBooking getHpabBooking() {
		return hpabBooking;
	}

	public void setHpabBooking(HPABBooking hpabBooking) {
		this.hpabBooking = hpabBooking;
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

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
