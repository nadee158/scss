package com.privasia.scss.cosmos.dto;

import java.io.Serializable;

public class TestDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String agentCode;
  private String containerNo;
  private String inOrOut;
  private String line;
  private String fullOrEmpty;
  private String iSO;
  private String orderFOT;
  private String curPos;
  private String iSOInfo;
  private String sealInfo;

  public String getAgentCode() {
    return agentCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getInOrOut() {
    return inOrOut;
  }

  public void setInOrOut(String inOrOut) {
    this.inOrOut = inOrOut;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }

  public String getFullOrEmpty() {
    return fullOrEmpty;
  }

  public void setFullOrEmpty(String fullOrEmpty) {
    this.fullOrEmpty = fullOrEmpty;
  }

  public String getiSO() {
    return iSO;
  }

  public void setiSO(String iSO) {
    this.iSO = iSO;
  }

  public String getOrderFOT() {
    return orderFOT;
  }

  public void setOrderFOT(String orderFOT) {
    this.orderFOT = orderFOT;
  }

  public String getCurPos() {
    return curPos;
  }

  public void setCurPos(String curPos) {
    this.curPos = curPos;
  }

  public String getiSOInfo() {
    return iSOInfo;
  }

  public void setiSOInfo(String iSOInfo) {
    this.iSOInfo = iSOInfo;
  }

  public String getSealInfo() {
    return sealInfo;
  }

  public void setSealInfo(String sealInfo) {
    this.sealInfo = sealInfo;
  }



}
