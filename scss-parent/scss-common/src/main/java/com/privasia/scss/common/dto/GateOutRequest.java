package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateOutRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName;
	private Long gatePass1;// -long
	private Long gatePass2;// -long

	@NotNull(message = "cardID is required!")
	private Long cardID;

	private Long comID;
	private String expContainer1;// -string
	private String expContainer2;// -string
	private String impContainer1;// -string
	private String impContainer2;// -string
	private String oddImpContainer1;// -string
	private String oddImpContainer2;// -string
	private String truckHeadNo;// -string

	@NotNull(message = "gateOUTDateTime is required!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateOUTDateTime;// -string

	@NotNull(message = "clientID is required!")
	private Long clientID;// -long (clientID)

	private String laneNo;// -long (clientID)
	private Integer expWeightBridge;// -long
	private boolean checkPreArrival;// -boolean
	private String haulageCode;

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

	public LocalDateTime getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(LocalDateTime gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}

	public String getHaulageCode() {
		return haulageCode;
	}

	public void setHaulageCode(String haulageCode) {
		this.haulageCode = haulageCode;
	}

	public String getLaneNo() {
		return laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public boolean isCheckPreArrival() {
		return checkPreArrival;
	}

	public void setCheckPreArrival(boolean checkPreArrival) {
		this.checkPreArrival = checkPreArrival;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public String getImpContainer1() {
		return impContainer1;
	}

	public void setImpContainer1(String impContainer1) {
		this.impContainer1 = impContainer1;
	}

	public String getImpContainer2() {
		return impContainer2;
	}

	public void setImpContainer2(String impContainer2) {
		this.impContainer2 = impContainer2;
	}

	public Long getComID() {
		return comID;
	}

	public void setComID(Long comID) {
		this.comID = comID;
	}

	public Long getClientID() {
		return clientID;
	}

	public void setClientID(Long clientID) {
		this.clientID = clientID;
	}

	public String getOddImpContainer1() {
		return oddImpContainer1;
	}

	public void setOddImpContainer1(String oddImpContainer1) {
		this.oddImpContainer1 = oddImpContainer1;
	}

	public String getOddImpContainer2() {
		return oddImpContainer2;
	}

	public void setOddImpContainer2(String oddImpContainer2) {
		this.oddImpContainer2 = oddImpContainer2;
	}

}
