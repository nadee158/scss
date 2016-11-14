package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class GateInfo implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  String cardIdSeq = StringUtils.EMPTY;
  String clientId = StringUtils.EMPTY;
  String timeGateIn = StringUtils.EMPTY;

  String weightBridge = StringUtils.EMPTY;
  String cugIdSeq = StringUtils.EMPTY;

  public GateInfo() {
    super();

  }

  public String getCardIdSeq() {
    return cardIdSeq;
  }

  public void setCardIdSeq(String cardIdSeq) {
    this.cardIdSeq = cardIdSeq;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public String getTimeGateIn() {
    return timeGateIn;
  }

  public void setTimeGateIn(String timeGateIn) {
    this.timeGateIn = timeGateIn;
  }

  public String getWeightBridge() {
    return weightBridge;
  }

  public void setWeightBridge(String weightBridge) {
    this.weightBridge = weightBridge;
  }

  public String getCugIdSeq() {
    return cugIdSeq;
  }

  public void setCugIdSeq(String cugIdSeq) {
    this.cugIdSeq = cugIdSeq;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
