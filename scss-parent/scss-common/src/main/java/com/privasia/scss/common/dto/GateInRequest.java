package com.privasia.scss.common.dto;

import java.io.Serializable;

public class GateInRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userId;
	private Long gatePass1;// -long
	private Long gatePass2;// -long
	private Long cardID;
	private String expContainer1;// -string
	private String expContainer2;// -string
	private String truckHeadNo;// -string
	private String gateInDateTime;// -string
	private String laneId;// -long (clientID)
	private Integer expWeightBridge;// -long
	private boolean checkPreArrival;// -boolean
	private String hpabSeqId;// -string

	public String getExpContainer1() {
		return expContainer1;
	}

	public void setExpContainer1(String expContainer1) {
		this.expContainer1 = expContainer1;
	}

	public String getExpContainer2() {
		return expContainer2;
	}

	public void setExpContainer2(String expContainer2) {
		this.expContainer2 = expContainer2;
	}

	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}

	public String getGateInDateTime() {
		return gateInDateTime;
	}

	public void setGateInDateTime(String gateInDateTime) {
		this.gateInDateTime = gateInDateTime;
	}

	public String getLaneId() {
		return laneId;
	}

	public void setLaneId(String laneId) {
		this.laneId = laneId;
	}

	public boolean isCheckPreArrival() {
		return checkPreArrival;
	}

	public void setCheckPreArrival(boolean checkPreArrival) {
		this.checkPreArrival = checkPreArrival;
	}

	public String getHpabSeqId() {
		return hpabSeqId;
	}

	public void setHpabSeqId(String hpabSeqId) {
		this.hpabSeqId = hpabSeqId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Long getGatePass1() {
		return gatePass1;
	}

	public void setGatePass1(Long gatePass1) {
		this.gatePass1 = gatePass1;
	}

	public Long getGatePass2() {
		return gatePass2;
	}

	public void setGatePass2(Long gatePass2) {
		this.gatePass2 = gatePass2;
	}

	public Integer getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(Integer expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}
	
	

}
