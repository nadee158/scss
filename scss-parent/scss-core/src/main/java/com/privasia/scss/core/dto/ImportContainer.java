package com.privasia.scss.core.dto;

import java.io.Serializable;

import org.springframework.beans.BeanUtils;

import com.privasia.scss.core.model.HPATBookingDetail;

public class ImportContainer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String impGatePassNumber;

  private String containerNumber;

  private String gateInOut;

  private String line;

  private ISOInfo isoInfo;

  private SealInfo sealInfo01;

  private SealInfo sealInfo02;

  private String agentCode;

  private String fullOrEmpty;

  private String isoCode;

  private String orderFOT;

  private String curPos;


  // properties added for updateGatePass
  private String positionOnTruck;

  // eirNumber -
  private long eirNo;

  // systemUser id
  private long userSessionId;

  private String gateInLaneNo;

  private String bayCode;

  private String yardPosition;

  private long printEIRNo;

  private long callCard;

  private String rejectRemarks;

  private String acceptOrReject;

  private String gateInNo;
  // cardUsageID
  private String cugId;

  // bookingID
  private String hpat;

  public ImportContainer(HPATBookingDetail bookingDetail) {
    super();
    if (!(bookingDetail == null)) {
      BeanUtils.copyProperties(bookingDetail, this);
    }
  }

  public ImportContainer() {
    super();
  }

  public ISOInfo getIsoInfo() {
    return isoInfo;
  }

  public void setIsoInfo(ISOInfo isoInfo) {
    this.isoInfo = isoInfo;
  }

  public String getImpGatePassNumber() {
    return impGatePassNumber;
  }

  public void setImpGatePassNumber(String impGatePassNumber) {
    this.impGatePassNumber = impGatePassNumber;
  }

  public String getContainerNumber() {
    return containerNumber;
  }

  public void setContainerNumber(String containerNumber) {
    this.containerNumber = containerNumber;
  }

  public String getGateInOut() {
    return gateInOut;
  }

  public void setGateInOut(String gateInOut) {
    this.gateInOut = gateInOut;
  }

  public String getLine() {
    return line;
  }

  public void setLine(String line) {
    this.line = line;
  }


  public SealInfo getSealInfo01() {
    return sealInfo01;
  }

  public void setSealInfo01(SealInfo sealInfo01) {
    this.sealInfo01 = sealInfo01;
  }

  public SealInfo getSealInfo02() {
    return sealInfo02;
  }

  public void setSealInfo02(SealInfo sealInfo02) {
    this.sealInfo02 = sealInfo02;
  }

  public String getAgentCode() {
    return agentCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public String getFullOrEmpty() {
    return fullOrEmpty;
  }

  public void setFullOrEmpty(String fullOrEmpty) {
    this.fullOrEmpty = fullOrEmpty;
  }


  public String getIsoCode() {
    return isoCode;
  }

  public void setIsoCode(String isoCode) {
    this.isoCode = isoCode;
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

  public String getPositionOnTruck() {
    return positionOnTruck;
  }

  public void setPositionOnTruck(String positionOnTruck) {
    this.positionOnTruck = positionOnTruck;
  }

  public long getEirNo() {
    return eirNo;
  }

  public void setEirNo(long eirNo) {
    this.eirNo = eirNo;
  }

  public long getUserSessionId() {
    return userSessionId;
  }

  public void setUserSessionId(long userSessionId) {
    this.userSessionId = userSessionId;
  }

  public String getGateInLaneNo() {
    return gateInLaneNo;
  }

  public void setGateInLaneNo(String gateInLaneNo) {
    this.gateInLaneNo = gateInLaneNo;
  }

  public String getBayCode() {
    return bayCode;
  }

  public void setBayCode(String bayCode) {
    this.bayCode = bayCode;
  }

  public String getYardPosition() {
    return yardPosition;
  }

  public void setYardPosition(String yardPosition) {
    this.yardPosition = yardPosition;
  }

  public long getPrintEIRNo() {
    return printEIRNo;
  }

  public void setPrintEIRNo(long printEIRNo) {
    this.printEIRNo = printEIRNo;
  }

  public long getCallCard() {
    return callCard;
  }

  public void setCallCard(long callCard) {
    this.callCard = callCard;
  }

  public String getRejectRemarks() {
    return rejectRemarks;
  }

  public void setRejectRemarks(String rejectRemarks) {
    this.rejectRemarks = rejectRemarks;
  }

  public String getAcceptOrReject() {
    return acceptOrReject;
  }

  public void setAcceptOrReject(String acceptOrReject) {
    this.acceptOrReject = acceptOrReject;
  }

  public String getGateInNo() {
    return gateInNo;
  }

  public void setGateInNo(String gateInNo) {
    this.gateInNo = gateInNo;
  }

  public String getCugId() {
    return cugId;
  }

  public void setCugId(String cugId) {
    this.cugId = cugId;
  }

  public String getHpat() {
    return hpat;
  }

  public void setHpat(String hpat) {
    this.hpat = hpat;
  }



}
