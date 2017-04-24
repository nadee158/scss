package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class ReferRejectDetailDTO implements Serializable {

  private Long referRejectID;

  private Long referRejectDetailID;

  private String containerNo;

  private String containerIsoCode;

  private CommonSealDTO seal;

  private String remarks;

  private String status;

  private String supervisorRemarks;

  private Long rejectBy;

  private Long referBy;

  private Integer expPmBTM;

  private Integer expNetWeight;

  private String doubleBooking;

  private String lineCode;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  private LocalDateTime gateInTime;

  private String position;

  private Integer measuredWeightBridge;

  private CommonSolasDTO solas;

  private Set<ReferRejectReasonDTO> referRejectReasons;


  private static final long serialVersionUID = 1L;

  public Long getReferRejectDetailID() {
    return referRejectDetailID;
  }

  public void setReferRejectDetailID(Long referRejectDetailID) {
    this.referRejectDetailID = referRejectDetailID;
  }

  public String getContainerNo() {
    return containerNo;
  }

  public void setContainerNo(String containerNo) {
    this.containerNo = containerNo;
  }

  public String getContainerIsoCode() {
    return containerIsoCode;
  }

  public void setContainerIsoCode(String containerIsoCode) {
    this.containerIsoCode = containerIsoCode;
  }

  public CommonSealDTO getSeal() {
    return seal;
  }

  public void setSeal(CommonSealDTO seal) {
    this.seal = seal;
  }

  public String getRemarks() {
    return remarks;
  }

  public void setRemarks(String remarks) {
    this.remarks = remarks;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getSupervisorRemarks() {
    return supervisorRemarks;
  }

  public void setSupervisorRemarks(String supervisorRemarks) {
    this.supervisorRemarks = supervisorRemarks;
  }

  public Long getRejectBy() {
    return rejectBy;
  }

  public void setRejectBy(Long rejectBy) {
    this.rejectBy = rejectBy;
  }

  public Long getReferBy() {
    return referBy;
  }

  public void setReferBy(Long referBy) {
    this.referBy = referBy;
  }

  public Integer getExpPmBTM() {
    return expPmBTM;
  }

  public void setExpPmBTM(Integer expPmBTM) {
    this.expPmBTM = expPmBTM;
  }

  public Integer getExpNetWeight() {
    return expNetWeight;
  }

  public void setExpNetWeight(Integer expNetWeight) {
    this.expNetWeight = expNetWeight;
  }

  public String getDoubleBooking() {
    return doubleBooking;
  }

  public void setDoubleBooking(String doubleBooking) {
    this.doubleBooking = doubleBooking;
  }

  public String getLineCode() {
    return lineCode;
  }

  public void setLineCode(String lineCode) {
    this.lineCode = lineCode;
  }


  public LocalDateTime getGateInTime() {
    return gateInTime;
  }

  public void setGateInTime(LocalDateTime gateInTime) {
    this.gateInTime = gateInTime;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public CommonSolasDTO getSolas() {
    return solas;
  }

  public void setSolas(CommonSolasDTO solas) {
    this.solas = solas;
  }

  public Integer getMeasuredWeightBridge() {
    return measuredWeightBridge;
  }

  public void setMeasuredWeightBridge(Integer measuredWeightBridge) {
    this.measuredWeightBridge = measuredWeightBridge;
  }

  public Set<ReferRejectReasonDTO> getReferRejectReasons() {
    return referRejectReasons;
  }

  public void setReferRejectReasons(Set<ReferRejectReasonDTO> referRejectReasons) {
    this.referRejectReasons = referRejectReasons;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public Long getReferRejectID() {
    return referRejectID;
  }

  public void setReferRejectID(Long referRejectID) {
    this.referRejectID = referRejectID;
  }

  @Override
  public String toString() {
    return "ReferRejectDetailDTO [referRejectID=" + referRejectID + ", referRejectDetailID=" + referRejectDetailID
        + ", containerNo=" + containerNo + ", containerIsoCode=" + containerIsoCode + ", seal=" + seal + ", remarks="
        + remarks + ", status=" + status + ", supervisorRemarks=" + supervisorRemarks + ", rejectBy=" + rejectBy
        + ", referBy=" + referBy + ", expPmBTM=" + expPmBTM + ", expNetWeight=" + expNetWeight + ", doubleBooking="
        + doubleBooking + ", lineCode=" + lineCode + ", gateInTime=" + gateInTime + ", position=" + position
        + ", measuredWeightBridge=" + measuredWeightBridge + ", solas=" + solas + ", referRejectReasons="
        + referRejectReasons + "]";
  }

  public ReferRejectDetailDTO initializeWithDefaultValues() {
    this.referRejectID = 10l;
    this.referRejectDetailID = 11l;
    this.containerNo = StringUtils.EMPTY;
    this.containerIsoCode = StringUtils.EMPTY;
    this.seal = new CommonSealDTO().initializeWithDefaultValues();
    this.remarks = StringUtils.EMPTY;
    this.status = StringUtils.EMPTY;
    this.supervisorRemarks = StringUtils.EMPTY;
    this.rejectBy = 50l;
    this.referBy = 20l;
    this.expPmBTM = 250;
    this.expNetWeight = 550;
    this.doubleBooking = StringUtils.EMPTY;
    this.lineCode = StringUtils.EMPTY;
    this.gateInTime = LocalDateTime.now();
    this.position = StringUtils.EMPTY;
    this.measuredWeightBridge = 450;
    this.solas = new CommonSolasDTO().initializeWithDefaultValues();
    this.referRejectReasons = new HashSet<ReferRejectReasonDTO>();
    this.referRejectReasons.add(new ReferRejectReasonDTO().initializeWithDefaultValues());
    this.referRejectReasons.add(new ReferRejectReasonDTO().initializeWithDefaultValues());
    return this;
  }

}
