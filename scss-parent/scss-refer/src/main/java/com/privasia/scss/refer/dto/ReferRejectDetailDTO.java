package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ReferRejectReason;

public class ReferRejectDetailDTO implements Serializable {

  private Long referRejectDetailID;

  private String containerNo;

  private String containerIsoCode;

  private String seal01Origin;

  private String seal01Type;

  private String seal01Number;

  private String seal02Origin;

  private String seal02Type;

  private String seal02Number;

  private String remarks;

  private String status;

  private String supervisorRemarks;

  private Long rejectBy;

  private Long referBy;

  private Integer expPmBTM;

  private Integer expNetWeight;

  private String doubleBooking;

  private String lineCode;

  private String gateInTime;

  private String position;

  private Integer measuredWeightBridge;

  private Integer mgw;

  private String faLedgerCode;

  private String solasRefNumber;

  private String solasDetailID;

  private String solasInstruction;

  private String shipperVGM;

  private Set<ReferRejectReasonDTO> referRejectReasons;

  public ReferRejectDetailDTO(ReferRejectDetail detail) {
    this.referRejectDetailID = detail.getReferRejectDetailID();

    this.containerNo = detail.getContainerNo();

    this.containerIsoCode = detail.getContainerIsoCode();

    if (!(detail.getSeal() == null)) {
      this.seal01Origin = detail.getSeal().getSeal01Origin();

      this.seal01Type = detail.getSeal().getSeal01Type();

      this.seal01Number = detail.getSeal().getSeal01Number();

      this.seal02Origin = detail.getSeal().getSeal02Origin();

      this.seal02Type = detail.getSeal().getSeal02Type();

      this.seal02Number = detail.getSeal().getSeal02Number();
    }
    this.remarks = detail.getRemarks();

    this.status = detail.getStatus() == null ? null : detail.getStatus().getValue();

    this.supervisorRemarks = detail.getSupervisorRemarks();

    this.rejectBy = detail.getRejectBy() == null ? null : detail.getRejectBy().getSystemUserID();

    this.referBy = detail.getReferBy() == null ? null : detail.getRejectBy().getSystemUserID();

    this.expPmBTM = detail.getExpPmBTM();

    this.expNetWeight = detail.getExpNetWeight();

    this.doubleBooking = detail.getDoubleBooking();

    this.lineCode = detail.getLineCode();

    this.gateInTime = DateUtil.getFormatteDateTime(detail.getGateInTime());

    if (!(detail.getPosition() == null)) {
      this.position = detail.getPosition().getValue();
    }


    this.measuredWeightBridge = detail.getMeasuredWeightBridge();

    if (!(detail.getSolas() == null)) {
      this.mgw = detail.getSolas().getMgw();

      this.faLedgerCode = detail.getSolas().getFaLedgerCode();

      this.solasRefNumber = detail.getSolas().getSolasRefNumber();

      this.solasDetailID = detail.getSolas().getSolasDetailID();

      if (!(detail.getSolas().getSolasInstruction() == null)) {
        this.solasInstruction = detail.getSolas().getSolasInstruction().getValue();
      }


      this.shipperVGM = detail.getSolas().getShipperVGM();
    }
    this.referRejectReasons = constructreferRejectReasons(detail.getReferRejectReason());
  }

  private Set<ReferRejectReasonDTO> constructreferRejectReasons(Set<ReferRejectReason> referRejectReason) {
    if (!(referRejectReason == null || referRejectReason.isEmpty())) {
      Set<ReferRejectReasonDTO> dtos = new HashSet<ReferRejectReasonDTO>();
      referRejectReason.forEach(reason -> {
        dtos.add(new ReferRejectReasonDTO(reason));
      });
      return dtos;
    }
    return null;
  }

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

  public String getSeal01Origin() {
    return seal01Origin;
  }

  public void setSeal01Origin(String seal01Origin) {
    this.seal01Origin = seal01Origin;
  }

  public String getSeal01Type() {
    return seal01Type;
  }

  public void setSeal01Type(String seal01Type) {
    this.seal01Type = seal01Type;
  }

  public String getSeal01Number() {
    return seal01Number;
  }

  public void setSeal01Number(String seal01Number) {
    this.seal01Number = seal01Number;
  }

  public String getSeal02Origin() {
    return seal02Origin;
  }

  public void setSeal02Origin(String seal02Origin) {
    this.seal02Origin = seal02Origin;
  }

  public String getSeal02Type() {
    return seal02Type;
  }

  public void setSeal02Type(String seal02Type) {
    this.seal02Type = seal02Type;
  }

  public String getSeal02Number() {
    return seal02Number;
  }

  public void setSeal02Number(String seal02Number) {
    this.seal02Number = seal02Number;
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

  public String getGateInTime() {
    return gateInTime;
  }

  public void setGateInTime(String gateInTime) {
    this.gateInTime = gateInTime;
  }

  public String getPosition() {
    return position;
  }

  public void setPosition(String position) {
    this.position = position;
  }

  public Integer getMeasuredWeightBridge() {
    return measuredWeightBridge;
  }

  public void setMeasuredWeightBridge(Integer measuredWeightBridge) {
    this.measuredWeightBridge = measuredWeightBridge;
  }

  public Integer getMgw() {
    return mgw;
  }

  public void setMgw(Integer mgw) {
    this.mgw = mgw;
  }

  public String getFaLedgerCode() {
    return faLedgerCode;
  }

  public void setFaLedgerCode(String faLedgerCode) {
    this.faLedgerCode = faLedgerCode;
  }

  public String getSolasRefNumber() {
    return solasRefNumber;
  }

  public void setSolasRefNumber(String solasRefNumber) {
    this.solasRefNumber = solasRefNumber;
  }

  public String getSolasDetailID() {
    return solasDetailID;
  }

  public void setSolasDetailID(String solasDetailID) {
    this.solasDetailID = solasDetailID;
  }

  public String getSolasInstruction() {
    return solasInstruction;
  }

  public void setSolasInstruction(String solasInstruction) {
    this.solasInstruction = solasInstruction;
  }

  public String getShipperVGM() {
    return shipperVGM;
  }

  public void setShipperVGM(String shipperVGM) {
    this.shipperVGM = shipperVGM;
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

  @Override
  public String toString() {
    return "ReferRejectDetailDTO [referRejectDetailID=" + referRejectDetailID + ", containerNo=" + containerNo
        + ", containerIsoCode=" + containerIsoCode + ", seal01Origin=" + seal01Origin + ", seal01Type=" + seal01Type
        + ", seal01Number=" + seal01Number + ", seal02Origin=" + seal02Origin + ", seal02Type=" + seal02Type
        + ", seal02Number=" + seal02Number + ", remarks=" + remarks + ", status=" + status + ", supervisorRemarks="
        + supervisorRemarks + ", rejectBy=" + rejectBy + ", referBy=" + referBy + ", expPmBTM=" + expPmBTM
        + ", expNetWeight=" + expNetWeight + ", doubleBooking=" + doubleBooking + ", lineCode=" + lineCode
        + ", gateInTime=" + gateInTime + ", position=" + position + ", measuredWeightBridge=" + measuredWeightBridge
        + ", mgw=" + mgw + ", faLedgerCode=" + faLedgerCode + ", solasRefNumber=" + solasRefNumber + ", solasDetailID="
        + solasDetailID + ", solasInstruction=" + solasInstruction + ", shipperVGM=" + shipperVGM
        + ", referRejectReasons=" + referRejectReasons + "]";
  }

}
