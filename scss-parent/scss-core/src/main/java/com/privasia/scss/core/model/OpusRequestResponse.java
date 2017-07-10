package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ReadWriteStatus;
import com.privasia.scss.common.enums.TransactionType;

@Entity
@Table(name = "SCSS_OPUS_REQUEST_RESPONSE")
public class OpusRequestResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "OPUS_REQ_RES_SEQ")
	private Long opusReqResID;

	@Column(name = "TRANSACTION_TYPE")
	@Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
	private TransactionType transactionType;

	@Column(name = "GATE_IN_OUT")
	@Type(type = "com.privasia.scss.common.enumusertype.GateInOutStatusEnumUserType")
	private GateInOutStatus gateInOut;

	@Column(name = "IMP_CONTAINER_01")
	private String impContainer01;

	@Column(name = "IMP_CONTAINER_02")
	private String impContainer02;

	@Column(name = "EXP_CONTAINER_01")
	private String expContainer01;

	@Column(name = "EXP_CONTAINER_02")
	private String expContainer02;

	@Column(name = "ODD_IMP_CONTAINER_01")
	private String oddImpContainer01;

	@Column(name = "ODD_IMP_CONTAINER_02")
	private String oddImpContainer02;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
	@JoinColumn(name = "CARD_ID", nullable = true, referencedColumnName = "CRD_CARDID_SEQ")
	private Card card;

	@Column(name = "GATE_IN_TIME")
	private LocalDateTime gateinTime;

	@Column(name = "READ_WRITE")
	@Type(type = "com.privasia.scss.common.enumusertype.ReadWriteStatusEnumUserType")
	private ReadWriteStatus readWrite;

	@Column(name = "REQUEST", length = 5000)
	private String request;

	@Column(name = "RESPONSE", length = 5000)
	private String response;

	@Column(name = "SEND_TIME")
	private LocalDateTime sendTime;

	@Column(name = "RECEIVED_TIME")
	private LocalDateTime receivedTime;

	@Version
	@Column(name = "VERSION")
	private Integer version;

	public Long getOpusReqResID() {
		return opusReqResID;
	}

	public void setOpusReqResID(Long opusReqResID) {
		this.opusReqResID = opusReqResID;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public GateInOutStatus getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(GateInOutStatus gateInOut) {
		this.gateInOut = gateInOut;
	}

	public String getImpContainer01() {
		return impContainer01;
	}

	public void setImpContainer01(String impContainer01) {
		this.impContainer01 = impContainer01;
	}

	public String getImpContainer02() {
		return impContainer02;
	}

	public void setImpContainer02(String impContainer02) {
		this.impContainer02 = impContainer02;
	}

	public String getExpContainer01() {
		return expContainer01;
	}

	public void setExpContainer01(String expContainer01) {
		this.expContainer01 = expContainer01;
	}

	public String getExpContainer02() {
		return expContainer02;
	}

	public void setExpContainer02(String expContainer02) {
		this.expContainer02 = expContainer02;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public LocalDateTime getGateinTime() {
		return gateinTime;
	}

	public void setGateinTime(LocalDateTime gateinTime) {
		this.gateinTime = gateinTime;
	}

	public ReadWriteStatus getReadWrite() {
		return readWrite;
	}

	public void setReadWrite(ReadWriteStatus readWrite) {
		this.readWrite = readWrite;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public LocalDateTime getSendTime() {
		return sendTime;
	}

	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}

	public LocalDateTime getReceivedTime() {
		return receivedTime;
	}

	public void setReceivedTime(LocalDateTime receivedTime) {
		this.receivedTime = receivedTime;
	}

	public String getOddImpContainer01() {
		return oddImpContainer01;
	}

	public void setOddImpContainer01(String oddImpContainer01) {
		this.oddImpContainer01 = oddImpContainer01;
	}

	public String getOddImpContainer02() {
		return oddImpContainer02;
	}

	public void setOddImpContainer02(String oddImpContainer02) {
		this.oddImpContainer02 = oddImpContainer02;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
