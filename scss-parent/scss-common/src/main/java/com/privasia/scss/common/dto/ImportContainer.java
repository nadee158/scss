package com.privasia.scss.common.dto;

import java.io.Serializable;

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

  private String currentPosition;

  private String handlingID;

  private String InOrOut;

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

  private String rejectRemarks;

  private String acceptOrReject;

  // cardUsageID
  private String cugId;

  // bookingID
  private String hpat;

  //////////////////////////// TO PRINT EIR
  private String operationReefer;

  private String temp;

  private String tempUnit;

  private String imdg;

  private String unc;

  private String oogoh;

  private String oogol;

  private String oogof;

  private String oogoa;

  private String oogor;

  private DGInfo dgInfo;

  private boolean FOTBKGFlag = true;

  private String netWeight;


  private String containerHeight;

  private String containerLength;

  private String containerType;

  //////////////////////////// TO createImpRequestXML
  private String errXMLMsg;

  private String contRefer;


  // from opus import container
  private long containerDischargeDateTime;// 20161124162510,
  private String impCarrierType;// null,
  private String impCarrier;// null,
  private String vesselCode;// UANE,
  private String vesselVoyage;// 003/2016,
  private String visitId;// 121212,
  private String vesselScn;// DB0899,
  private String vesselName;// AL NEFUD,
  private String vesselATA;// 20161124161800

  public String getContRefer() {
    return contRefer;
  }

  public void setContRefer(String contRefer) {
    this.contRefer = contRefer;
  }

  public String getErrXMLMsg() {
    return errXMLMsg;
  }

  public void setErrXMLMsg(String errXMLMsg) {
    this.errXMLMsg = errXMLMsg;
  }

  public String getContainerHeight() {
    return containerHeight;
  }

  public void setContainerHeight(String containerHeight) {
    this.containerHeight = containerHeight;
  }

  public String getContainerLength() {
    return containerLength;
  }

  public void setContainerLength(String containerLength) {
    this.containerLength = containerLength;
  }

  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }


  public String getNetWeight() {
    return netWeight;
  }

  public void setNetWeight(String netWeight) {
    this.netWeight = netWeight;
  }

  public String getOogoh() {
    return oogoh;
  }

  public void setOogoh(String oogoh) {
    this.oogoh = oogoh;
  }

  public String getOogol() {
    return oogol;
  }

  public void setOogol(String oogol) {
    this.oogol = oogol;
  }

  public String getOogof() {
    return oogof;
  }

  public void setOogof(String oogof) {
    this.oogof = oogof;
  }

  public String getOogoa() {
    return oogoa;
  }

  public void setOogoa(String oogoa) {
    this.oogoa = oogoa;
  }

  public String getOogor() {
    return oogor;
  }

  public void setOogor(String oogor) {
    this.oogor = oogor;
  }

  public DGInfo getDgInfo() {
    return dgInfo;
  }

  public void setDgInfo(DGInfo dgInfo) {
    this.dgInfo = dgInfo;
  }

  public String getUnc() {
    return unc;
  }

  public void setUnc(String unc) {
    this.unc = unc;
  }

  public String getTemp() {
    return temp;
  }

  public void setTemp(String temp) {
    this.temp = temp;
  }

  public String getTempUnit() {
    return tempUnit;
  }

  public void setTempUnit(String tempUnit) {
    this.tempUnit = tempUnit;
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

  public String getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(String currentPosition) {
    this.currentPosition = currentPosition;
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

  public String getOperationReefer() {
    return operationReefer;
  }

  public void setOperationReefer(String operationReefer) {
    this.operationReefer = operationReefer;
  }

  public String getImdg() {
    return imdg;
  }

  public void setImdg(String imdg) {
    this.imdg = imdg;
  }

  public String getHandlingID() {
    return handlingID;
  }

  public void setHandlingID(String handlingID) {
    this.handlingID = handlingID;
  }

  public String getInOrOut() {
    return InOrOut;
  }

  public void setInOrOut(String inOrOut) {
    InOrOut = inOrOut;
  }

  public boolean isFOTBKGFlag() {
    return FOTBKGFlag;
  }

  public void setFOTBKGFlag(boolean fOTBKGFlag) {
    FOTBKGFlag = fOTBKGFlag;
  }

  public long getContainerDischargeDateTime() {
    return containerDischargeDateTime;
  }

  public void setContainerDischargeDateTime(long containerDischargeDateTime) {
    this.containerDischargeDateTime = containerDischargeDateTime;
  }

  public String getImpCarrierType() {
    return impCarrierType;
  }

  public void setImpCarrierType(String impCarrierType) {
    this.impCarrierType = impCarrierType;
  }

  public String getImpCarrier() {
    return impCarrier;
  }

  public void setImpCarrier(String impCarrier) {
    this.impCarrier = impCarrier;
  }

  public String getVesselCode() {
    return vesselCode;
  }

  public void setVesselCode(String vesselCode) {
    this.vesselCode = vesselCode;
  }

  public String getVesselVoyage() {
    return vesselVoyage;
  }

  public void setVesselVoyage(String vesselVoyage) {
    this.vesselVoyage = vesselVoyage;
  }

  public String getVisitId() {
    return visitId;
  }

  public void setVisitId(String visitId) {
    this.visitId = visitId;
  }

  public String getVesselScn() {
    return vesselScn;
  }

  public void setVesselScn(String vesselScn) {
    this.vesselScn = vesselScn;
  }

  public String getVesselName() {
    return vesselName;
  }

  public void setVesselName(String vesselName) {
    this.vesselName = vesselName;
  }

  public String getVesselATA() {
    return vesselATA;
  }

  public void setVesselATA(String vesselATA) {
    this.vesselATA = vesselATA;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
