package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class WHoddDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long oddIdSeq;

	private String pmHeadNo = StringUtils.EMPTY;

	private String pmPlateNo = StringUtils.EMPTY;

	private boolean transactionSlipPrinted = false;

	private String gateOutBoothNo = StringUtils.EMPTY;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime timeGateIn;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime timeGateInOk;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime timeGateOut;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime timeGateOutOk;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime timeGateOutBooth;

	private Long cardId;

	private Long gateInClerkId;

	private Long gateOutClerkId;

	private Long gateInClientId;

	private Long gateOutClientId;

	private Long gateOutBoothClerkId;

	private String impExpFlag = StringUtils.EMPTY;

	private boolean kioskCancelPickUp = false;

	private boolean kioskConfirmed = false;

	private String gateInStatus = StringUtils.EMPTY;

	private String inOutFlag = StringUtils.EMPTY;

	private String reviseHeadNoRemarks = StringUtils.EMPTY;

	private String reviseHeadNo = StringUtils.EMPTY;

	private String oldHeadNo = StringUtils.EMPTY;

	private String zipFileNo = StringUtils.EMPTY;

	private String trxSlipNo = StringUtils.EMPTY;

	private ODDContainerDetailsDTO container01;

	private ODDContainerDetailsDTO container02;

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
		this.pmHeadNo = pmHeadNo;
	}

	public String getPmPlateNo() {
		return pmPlateNo;
	}

	public void setPmPlateNo(String pmPlateNo) {
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

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	public Long getGateInClerkId() {
		return gateInClerkId;
	}

	public void setGateInClerkId(Long gateInClerkId) {
		this.gateInClerkId = gateInClerkId;
	}

	public Long getGateOutClerkId() {
		return gateOutClerkId;
	}

	public void setGateOutClerkId(Long gateOutClerkId) {
		this.gateOutClerkId = gateOutClerkId;
	}

	public Long getGateInClientId() {
		return gateInClientId;
	}

	public void setGateInClientId(Long gateInClientId) {
		this.gateInClientId = gateInClientId;
	}

	public Long getGateOutClientId() {
		return gateOutClientId;
	}

	public void setGateOutClientId(Long gateOutClientId) {
		this.gateOutClientId = gateOutClientId;
	}

	public Long getGateOutBoothClerkId() {
		return gateOutBoothClerkId;
	}

	public void setGateOutBoothClerkId(Long gateOutBoothClerkId) {
		this.gateOutBoothClerkId = gateOutBoothClerkId;
	}

	public String getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(String impExpFlag) {
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

	public String getGateInStatus() {
		return gateInStatus;
	}

	public void setGateInStatus(String gateInStatus) {
		this.gateInStatus = gateInStatus;
	}

	public String getInOutFlag() {
		return inOutFlag;
	}

	public void setInOutFlag(String inOutFlag) {
		this.inOutFlag = inOutFlag;
	}

	public String getReviseHeadNoRemarks() {
		return reviseHeadNoRemarks;
	}

	public void setReviseHeadNoRemarks(String reviseHeadNoRemarks) {
		this.reviseHeadNoRemarks = reviseHeadNoRemarks;
	}

	public String getReviseHeadNo() {
		return reviseHeadNo;
	}

	public void setReviseHeadNo(String reviseHeadNo) {
		this.reviseHeadNo = reviseHeadNo;
	}

	public String getOldHeadNo() {
		return oldHeadNo;
	}

	public void setOldHeadNo(String oldHeadNo) {
		this.oldHeadNo = oldHeadNo;
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

	public ODDContainerDetailsDTO getContainer01() {
		return container01;
	}

	public void setContainer01(ODDContainerDetailsDTO container01) {
		this.container01 = container01;
	}

	public ODDContainerDetailsDTO getContainer02() {
		return container02;
	}

	public void setContainer02(ODDContainerDetailsDTO container02) {
		this.container02 = container02;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
