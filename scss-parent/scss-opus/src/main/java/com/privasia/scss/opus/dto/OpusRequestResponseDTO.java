/**
 * 
 */
package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author Janaka
 *
 */
public class OpusRequestResponseDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long opusReqResID;

	private String transactionType;

	private String gateInOut;

	private String impContainer01;
	
	private String impContainer02;
	
	private String expContainer01;
	
	private String expContainer02;

	private Long cardID;

	private LocalDateTime gateinTime;

	private String readWrite;

	private String request;

	private String response;

	private LocalDateTime sendTime;

	private LocalDateTime receivedTime;

	public Long getOpusReqResID() {
		return opusReqResID;
	}

	public void setOpusReqResID(Long opusReqResID) {
		this.opusReqResID = opusReqResID;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getGateInOut() {
		return gateInOut;
	}

	public void setGateInOut(String gateInOut) {
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

	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}

	public LocalDateTime getGateinTime() {
		return gateinTime;
	}

	public void setGateinTime(LocalDateTime gateinTime) {
		this.gateinTime = gateinTime;
	}

	public String getReadWrite() {
		return readWrite;
	}

	public void setReadWrite(String readWrite) {
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
	
	

}
