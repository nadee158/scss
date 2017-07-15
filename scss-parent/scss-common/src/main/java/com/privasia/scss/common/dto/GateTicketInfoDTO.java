package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateTicketInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String truckHeadNo;
  private String importOrExport;
  private String icOrPassport;
  private String gateInLaneNo;
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  private LocalDateTime gateInDateTime;// -string
  private String callCardNo;

  private ImportContainer container1;
  private ImportContainer container2;

  private ODDContainerDetailsDTO containerPickup1; // use for ODD
  private ODDContainerDetailsDTO containerPickup2; // use for ODD
  private ODDContainerDetailsDTO containerDrop1;// use for ODD
  private ODDContainerDetailsDTO containerDrop2;// use for ODD

  private String status;

  public String getTruckHeadNo() {
    return truckHeadNo;
  }

  public void setTruckHeadNo(String truckHeadNo) {
    this.truckHeadNo = truckHeadNo;
  }

  public String getImportOrExport() {
    return importOrExport;
  }

  public void setImportOrExport(String importOrExport) {
    this.importOrExport = importOrExport;
  }

  public String getIcOrPassport() {
    return icOrPassport;
  }

  public void setIcOrPassport(String icOrPassport) {
    this.icOrPassport = icOrPassport;
  }

  public String getGateInLaneNo() {
    return gateInLaneNo;
  }

  public void setGateInLaneNo(String gateInLaneNo) {
    this.gateInLaneNo = gateInLaneNo;
  }

  public LocalDateTime getGateInDateTime() {
    return gateInDateTime;
  }

  public void setGateInDateTime(LocalDateTime gateInDateTime) {
    this.gateInDateTime = gateInDateTime;
  }

  public String getCallCardNo() {
    return callCardNo;
  }

  public void setCallCardNo(String callCardNo) {
    this.callCardNo = callCardNo;
  }

  public ImportContainer getContainer1() {
    return container1;
  }

  public void setContainer1(ImportContainer container1) {
    this.container1 = container1;
  }

  public ImportContainer getContainer2() {
    return container2;
  }

  public void setContainer2(ImportContainer container2) {
    this.container2 = container2;
  }

  public ODDContainerDetailsDTO getContainerPickup1() {
    return containerPickup1;
  }

  public void setContainerPickup1(ODDContainerDetailsDTO containerPickup1) {
    this.containerPickup1 = containerPickup1;
  }

  public ODDContainerDetailsDTO getContainerPickup2() {
    return containerPickup2;
  }

  public void setContainerPickup2(ODDContainerDetailsDTO containerPickup2) {
    this.containerPickup2 = containerPickup2;
  }

  public ODDContainerDetailsDTO getContainerDrop1() {
    return containerDrop1;
  }

  public void setContainerDrop1(ODDContainerDetailsDTO containerDrop1) {
    this.containerDrop1 = containerDrop1;
  }

  public ODDContainerDetailsDTO getContainerDrop2() {
    return containerDrop2;
  }

  public void setContainerDrop2(ODDContainerDetailsDTO containerDrop2) {
    this.containerDrop2 = containerDrop2;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }



}
