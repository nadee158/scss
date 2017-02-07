package com.privasia.scss.common.dto;

import java.io.Serializable;

public class ImportContainer implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private Long gatePassID;

  private Long gatePassNo;

  private Long gateOrderNo;

  private CommonContainerDTO container;

  private Long company;

  private CommonGateInOutDTO commonGateInOut;

  private BaseCommonGateInOutDTO baseCommonGateInOutAttribute;

  private String gatePassStatus;

  private Long handlingID;

  private String orderNo;

  private String gateInOut;

  private String line;

  private String currentPosition;

  private String containerPosition;

  private String gateInLaneNo;

  private String gateOutLaneNo;

  private CommonSealDTO sealAttribute;

  private String gateOutRemarks;

  private String yardPosition;

  private String bayCode;

  private Long callCard;

  private String cosmosSeal01Origin;

  private String cosmosSeal01Type;

  private String cosmosSeal01Number;

  private String cosmosSeal02Origin;

  private String cosmosSeal02Type;

  private String cosmosSeal02Number;

  private String gatePassValidDate;

  private ISOInfo isoInfo;

  private String agentCode;

  private String orderFOT;

  // systemUser id
  private long userSessionId;

  private long printEIRNo;

  private String acceptOrReject;

  // cardUsageID
  private String cugId;

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

  private boolean FOTBKGFlag = true;

  private String netWeight;

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

  private DGInfo dgInfo;

  public Long getGatePassID() {
    return gatePassID;
  }

  public void setGatePassID(Long gatePassID) {
    this.gatePassID = gatePassID;
  }

  public Long getGatePassNo() {
    return gatePassNo;
  }

  public void setGatePassNo(Long gatePassNo) {
    this.gatePassNo = gatePassNo;
  }

  public Long getGateOrderNo() {
    return gateOrderNo;
  }

  public void setGateOrderNo(Long gateOrderNo) {
    this.gateOrderNo = gateOrderNo;
  }

  public CommonContainerDTO getContainer() {
    return container;
  }

  public void setContainer(CommonContainerDTO container) {
    this.container = container;
  }

  public Long getCompany() {
    return company;
  }

  public void setCompany(Long company) {
    this.company = company;
  }

  public CommonGateInOutDTO getCommonGateInOut() {
    return commonGateInOut;
  }

  public void setCommonGateInOut(CommonGateInOutDTO commonGateInOut) {
    this.commonGateInOut = commonGateInOut;
  }

  public BaseCommonGateInOutDTO getBaseCommonGateInOutAttribute() {
    return baseCommonGateInOutAttribute;
  }

  public void setBaseCommonGateInOutAttribute(BaseCommonGateInOutDTO baseCommonGateInOutAttribute) {
    this.baseCommonGateInOutAttribute = baseCommonGateInOutAttribute;
  }

  public String getGatePassStatus() {
    return gatePassStatus;
  }

  public void setGatePassStatus(String gatePassStatus) {
    this.gatePassStatus = gatePassStatus;
  }

  public Long getHandlingID() {
    return handlingID;
  }

  public void setHandlingID(Long handlingID) {
    this.handlingID = handlingID;
  }

  public String getOrderNo() {
    return orderNo;
  }

  public void setOrderNo(String orderNo) {
    this.orderNo = orderNo;
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

  public String getCurrentPosition() {
    return currentPosition;
  }

  public void setCurrentPosition(String currentPosition) {
    this.currentPosition = currentPosition;
  }

  public String getContainerPosition() {
    return containerPosition;
  }

  public void setContainerPosition(String containerPosition) {
    this.containerPosition = containerPosition;
  }

  public String getGateInLaneNo() {
    return gateInLaneNo;
  }

  public void setGateInLaneNo(String gateInLaneNo) {
    this.gateInLaneNo = gateInLaneNo;
  }

  public String getGateOutLaneNo() {
    return gateOutLaneNo;
  }

  public void setGateOutLaneNo(String gateOutLaneNo) {
    this.gateOutLaneNo = gateOutLaneNo;
  }

  public CommonSealDTO getSealAttribute() {
    return sealAttribute;
  }

  public void setSealAttribute(CommonSealDTO sealAttribute) {
    this.sealAttribute = sealAttribute;
  }

  public String getGateOutRemarks() {
    return gateOutRemarks;
  }

  public void setGateOutRemarks(String gateOutRemarks) {
    this.gateOutRemarks = gateOutRemarks;
  }

  public String getYardPosition() {
    return yardPosition;
  }

  public void setYardPosition(String yardPosition) {
    this.yardPosition = yardPosition;
  }

  public String getBayCode() {
    return bayCode;
  }

  public void setBayCode(String bayCode) {
    this.bayCode = bayCode;
  }

  public Long getCallCard() {
    return callCard;
  }

  public void setCallCard(Long callCard) {
    this.callCard = callCard;
  }

  public String getCosmosSeal01Origin() {
    return cosmosSeal01Origin;
  }

  public void setCosmosSeal01Origin(String cosmosSeal01Origin) {
    this.cosmosSeal01Origin = cosmosSeal01Origin;
  }

  public String getCosmosSeal01Type() {
    return cosmosSeal01Type;
  }

  public void setCosmosSeal01Type(String cosmosSeal01Type) {
    this.cosmosSeal01Type = cosmosSeal01Type;
  }

  public String getCosmosSeal01Number() {
    return cosmosSeal01Number;
  }

  public void setCosmosSeal01Number(String cosmosSeal01Number) {
    this.cosmosSeal01Number = cosmosSeal01Number;
  }

  public String getCosmosSeal02Origin() {
    return cosmosSeal02Origin;
  }

  public void setCosmosSeal02Origin(String cosmosSeal02Origin) {
    this.cosmosSeal02Origin = cosmosSeal02Origin;
  }

  public String getCosmosSeal02Type() {
    return cosmosSeal02Type;
  }

  public void setCosmosSeal02Type(String cosmosSeal02Type) {
    this.cosmosSeal02Type = cosmosSeal02Type;
  }

  public String getCosmosSeal02Number() {
    return cosmosSeal02Number;
  }

  public void setCosmosSeal02Number(String cosmosSeal02Number) {
    this.cosmosSeal02Number = cosmosSeal02Number;
  }

  public String getGatePassValidDate() {
    return gatePassValidDate;
  }

  public void setGatePassValidDate(String gatePassValidDate) {
    this.gatePassValidDate = gatePassValidDate;
  }

  public ISOInfo getIsoInfo() {
    return isoInfo;
  }

  public void setIsoInfo(ISOInfo isoInfo) {
    this.isoInfo = isoInfo;
  }

  public String getAgentCode() {
    return agentCode;
  }

  public void setAgentCode(String agentCode) {
    this.agentCode = agentCode;
  }

  public String getOrderFOT() {
    return orderFOT;
  }

  public void setOrderFOT(String orderFOT) {
    this.orderFOT = orderFOT;
  }

  public long getUserSessionId() {
    return userSessionId;
  }

  public void setUserSessionId(long userSessionId) {
    this.userSessionId = userSessionId;
  }

  public long getPrintEIRNo() {
    return printEIRNo;
  }

  public void setPrintEIRNo(long printEIRNo) {
    this.printEIRNo = printEIRNo;
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

  public String getOperationReefer() {
    return operationReefer;
  }

  public void setOperationReefer(String operationReefer) {
    this.operationReefer = operationReefer;
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

  public String getImdg() {
    return imdg;
  }

  public void setImdg(String imdg) {
    this.imdg = imdg;
  }

  public String getUnc() {
    return unc;
  }

  public void setUnc(String unc) {
    this.unc = unc;
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

  public boolean isFOTBKGFlag() {
    return FOTBKGFlag;
  }

  public void setFOTBKGFlag(boolean fOTBKGFlag) {
    FOTBKGFlag = fOTBKGFlag;
  }

  public String getNetWeight() {
    return netWeight;
  }

  public void setNetWeight(String netWeight) {
    this.netWeight = netWeight;
  }

  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }

  public String getErrXMLMsg() {
    return errXMLMsg;
  }

  public void setErrXMLMsg(String errXMLMsg) {
    this.errXMLMsg = errXMLMsg;
  }

  public String getContRefer() {
    return contRefer;
  }

  public void setContRefer(String contRefer) {
    this.contRefer = contRefer;
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

  public DGInfo getDgInfo() {
    return dgInfo;
  }

  public void setDgInfo(DGInfo dgInfo) {
    this.dgInfo = dgInfo;
  }

}
