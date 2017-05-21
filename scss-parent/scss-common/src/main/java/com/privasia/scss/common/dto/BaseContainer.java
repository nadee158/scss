package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class BaseContainer implements Serializable {

  private static final long serialVersionUID = 1L;

  private PrintEirDTO printEir;

  private CommonContainerDTO container;

  private CommonGateInOutDTO commonGateInOut;

  private BaseCommonGateInOutDTO baseCommonGateInOutAttribute;

  private String gateInOut;

  private String shippingLine;

  private String containerPosition;

  private CommonSealDTO sealAttribute;

  private String gateOutRemarks;

  private String yardPosition;

  private String yardBayCode;

  private Long callCard;

  private String shippingAgent;

  private String imdg;

  private Integer tareWeight;

  private Integer grossWeight;

  private String containerType;

  private String errXMLMsg;

  private String vesselCode;// UANE,

  private String vesselName;// AL NEFUD,

  private String vesselVisitID;

  private String vesselStatus;

  private String rtgExecustionStatus;// ":"RGS"

  @JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime vesselETADate;

  @JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime vesselATADate;


  @JsonFormat(pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
  private LocalDateTime rtgExecustionDateTime;// ;":"20161212101010"


  public PrintEirDTO getPrintEir() {
    return printEir;
  }


  public void setPrintEir(PrintEirDTO printEir) {
    this.printEir = printEir;
  }


  public CommonContainerDTO getContainer() {
    return container;
  }


  public void setContainer(CommonContainerDTO container) {
    this.container = container;
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


  public String getGateInOut() {
    return gateInOut;
  }


  public void setGateInOut(String gateInOut) {
    this.gateInOut = gateInOut;
  }


  public String getShippingLine() {
    return shippingLine;
  }


  public void setShippingLine(String shippingLine) {
    this.shippingLine = shippingLine;
  }


  public String getContainerPosition() {
    return containerPosition;
  }


  public void setContainerPosition(String containerPosition) {
    this.containerPosition = containerPosition;
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


  public String getYardBayCode() {
    return yardBayCode;
  }


  public void setYardBayCode(String yardBayCode) {
    this.yardBayCode = yardBayCode;
  }


  public Long getCallCard() {
    return callCard;
  }


  public void setCallCard(Long callCard) {
    this.callCard = callCard;
  }


  public String getShippingAgent() {
    return shippingAgent;
  }


  public void setShippingAgent(String shippingAgent) {
    this.shippingAgent = shippingAgent;
  }

  public String getImdg() {
    return imdg;
  }


  public void setImdg(String imdg) {
    this.imdg = imdg;
  }


  public Integer getTareWeight() {
    return tareWeight;
  }


  public void setTareWeight(Integer tareWeight) {
    this.tareWeight = tareWeight;
  }


  public Integer getGrossWeight() {
    return grossWeight;
  }


  public void setGrossWeight(Integer grossWeight) {
    this.grossWeight = grossWeight;
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


  public String getVesselCode() {
    return vesselCode;
  }


  public void setVesselCode(String vesselCode) {
    this.vesselCode = vesselCode;
  }


  public String getVesselName() {
    return vesselName;
  }


  public void setVesselName(String vesselName) {
    this.vesselName = vesselName;
  }


  public String getVesselVisitID() {
    return vesselVisitID;
  }


  public void setVesselVisitID(String vesselVisitID) {
    this.vesselVisitID = vesselVisitID;
  }


  public String getVesselStatus() {
    return vesselStatus;
  }


  public void setVesselStatus(String vesselStatus) {
    this.vesselStatus = vesselStatus;
  }


  public String getRtgExecustionStatus() {
    return rtgExecustionStatus;
  }


  public void setRtgExecustionStatus(String rtgExecustionStatus) {
    this.rtgExecustionStatus = rtgExecustionStatus;
  }


  public LocalDateTime getVesselETADate() {
    return vesselETADate;
  }


  public void setVesselETADate(LocalDateTime vesselETADate) {
    this.vesselETADate = vesselETADate;
  }


  public LocalDateTime getVesselATADate() {
    return vesselATADate;
  }


  public void setVesselATADate(LocalDateTime vesselATADate) {
    this.vesselATADate = vesselATADate;
  }


  public LocalDateTime getRtgExecustionDateTime() {
    return rtgExecustionDateTime;
  }


  public void setRtgExecustionDateTime(LocalDateTime rtgExecustionDateTime) {
    this.rtgExecustionDateTime = rtgExecustionDateTime;
  }


  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  // #####################################################################



}
