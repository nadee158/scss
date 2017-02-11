package com.privasia.scss.opus.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpusImportContainer {

  private String containerNo;// QASS1234566,
  private String impOrderNo;// ORDER0001,
  private String containerInOrOut;// OUT,
  private String containerShippingLine;// CMA,
  private String containerFullOrEmpty;// F,
  private String containerIso;// 4001,
  private String containerSize;// 40,
  private String containerHeight;// 8,
  private String containerType;// GE,
  private String containerDischargeDateTime;// 20161124162510,
  private String currentYardPosition;// 02S-0102-C-1,
  private String impCarrierType;// null,
  private String impCarrier;// null,
  private String vesselCode;// UANE,
  private String vesselVoyage;// 003/2016,
  private String visitId;// 121212,
  private String vesselScn;// DB0899,
  private String vesselName;// AL NEFUD,
  private String vesselATA;// 20161124161800

  private String rtgExecustionStatus;// ":"RGS"
  private String rtgExecustionDateTime;// ;":"20161212101010"

  public String getRtgExecustionStatus() {
    return rtgExecustionStatus;
  }

  public void setRtgExecustionStatus(String rtgExecustionStatus) {
    this.rtgExecustionStatus = rtgExecustionStatus;
  }

  public String getRtgExecustionDateTime() {
    return rtgExecustionDateTime;
  }

  public void setRtgExecustionDateTime(String rtgExecustionDateTime) {
    this.rtgExecustionDateTime = rtgExecustionDateTime;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getImpOrderNo() {
    return impOrderNo;
  }

  public void setImpOrderNo(String impOrderNo) {
    this.impOrderNo = impOrderNo;
  }

  public String getContainerInOrOut() {
    return containerInOrOut;
  }

  public void setContainerInOrOut(String containerInOrOut) {
    this.containerInOrOut = containerInOrOut;
  }

  public String getContainerShippingLine() {
    return containerShippingLine;
  }

  public void setContainerShippingLine(String containerShippingLine) {
    this.containerShippingLine = containerShippingLine;
  }

  public String getContainerFullOrEmpty() {
    return containerFullOrEmpty;
  }

  public void setContainerFullOrEmpty(String containerFullOrEmpty) {
    this.containerFullOrEmpty = containerFullOrEmpty;
  }

  public String getContainerIso() {
    return containerIso;
  }

  public void setContainerIso(String containerIso) {
    this.containerIso = containerIso;
  }


  public String getContainerType() {
    return containerType;
  }

  public void setContainerType(String containerType) {
    this.containerType = containerType;
  }


  public String getCurrentYardPosition() {
    return currentYardPosition;
  }

  public void setCurrentYardPosition(String currentYardPosition) {
    this.currentYardPosition = currentYardPosition;
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

  public String getContainerSize() {
    return containerSize;
  }

  public void setContainerSize(String containerSize) {
    this.containerSize = containerSize;
  }

  public String getContainerHeight() {
    return containerHeight;
  }

  public void setContainerHeight(String containerHeight) {
    this.containerHeight = containerHeight;
  }

  public String getContainerDischargeDateTime() {
    return containerDischargeDateTime;
  }

  public void setContainerDischargeDateTime(String containerDischargeDateTime) {
    this.containerDischargeDateTime = containerDischargeDateTime;
  }

  @Override
  public String toString() {
    return "OpusImportContainer [containerNo=" + containerNo + ", impOrderNo=" + impOrderNo + ", containerInOrOut="
        + containerInOrOut + ", containerShippingLine=" + containerShippingLine + ", containerFullOrEmpty="
        + containerFullOrEmpty + ", containerIso=" + containerIso + ", containerSize=" + containerSize
        + ", containerHeight=" + containerHeight + ", containerType=" + containerType + ", containerDischargeDateTime="
        + containerDischargeDateTime + ", currentYardPosition=" + currentYardPosition + ", impCarrierType="
        + impCarrierType + ", impCarrier=" + impCarrier + ", vesselCode=" + vesselCode + ", vesselVoyage="
        + vesselVoyage + ", visitId=" + visitId + ", vesselScn=" + vesselScn + ", vesselName=" + vesselName
        + ", vesselATA=" + vesselATA + ", rtgExecustionStatus=" + rtgExecustionStatus + ", rtgExecustionDateTime="
        + rtgExecustionDateTime + "]";
  }



}
