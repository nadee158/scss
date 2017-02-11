package com.privasia.scss.common.dto;

import java.io.Serializable;

public class GateOutRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long userId;
  private String userName;
  private Long gatePass1;// -long
  private Long gatePass2;// -long
  private Long cardID;
  private String expContainer1;// -string
  private String expContainer2;// -string
  private String impContainer1;// -string
  private String impContainer2;// -string
  private String truckHeadNo;// -string
  private String gateOUTDateTime;// -string
  private Long laneId;// -long (clientID)
  private String laneNo;// -long (clientID)
  private Integer expWeightBridge;// -long
  private boolean checkPreArrival;// -boolean
  private String hpabSeqId;// -string
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


  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
  }

  public String getHaulageCode() {
    return haulageCode;
  }

  public void setHaulageCode(String haulageCode) {
    this.haulageCode = haulageCode;
  }

  public Long getLaneId() {
    return laneId;
  }

  public void setLaneId(Long laneId) {
    this.laneId = laneId;
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

  public String getHpabSeqId() {
    return hpabSeqId;
  }

  public void setHpabSeqId(String hpabSeqId) {
    this.hpabSeqId = hpabSeqId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
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



}
