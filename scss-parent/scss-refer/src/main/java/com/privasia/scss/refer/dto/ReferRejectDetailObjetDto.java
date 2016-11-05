package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.util.constant.ReferStatus;
import com.privasia.scss.core.util.constant.SealOriginType;
import com.privasia.scss.core.util.constant.SealType;

public class ReferRejectDetailObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ReferRejectDetail OBJECT ---------------------------------------
  private Optional<Long> referRejectDetailID;

  private String containerNo;

  // enum
  private String position;

  private String containerIsoCode;

  private String seal01Origin = SealOriginType.L.getValue();

  private String seal01Type = SealType.SL.getValue();

  private String seal01Number;

  private String seal02Origin = SealOriginType.S.getValue();

  private String seal02Type = SealType.SL.getValue();

  private String seal02Number;

  private String remarks;

  private String status = ReferStatus.NONE.getValue();

  private int expPmBTM;

  private int expNetWeight;

  private String doubleBooking;

  private int measuredWeightBridge;

  private int mgw;

  private String faLedgerCode;

  private String solasRefNumber;

  private String solasDetailID;

  private String solasInstruction;

  private String shipperVGM;

  private String supervisorRemarks;

  private String lineCode;

  private LocalDateTime gateInTime;


  // ReferRejectReason objects
  private List<Long> referReasonIds;


  public Optional<Long> getReferRejectDetailID() {
    return referRejectDetailID;
  }


  public void setReferRejectDetailID(Optional<Long> referRejectDetailID) {
    this.referRejectDetailID = referRejectDetailID;
  }


  public String getContainerNo() {
    return containerNo;
  }


  public void setContainerNo(String containerNo) {
    this.containerNo = StringUtils.upperCase(containerNo);
  }


  public String getPosition() {
    return position;
  }


  public void setPosition(String position) {
    this.position = StringUtils.upperCase(position);
  }


  public String getContainerIsoCode() {
    return containerIsoCode;
  }


  public void setContainerIsoCode(String containerIsoCode) {
    this.containerIsoCode = StringUtils.upperCase(containerIsoCode);
  }


  public String getSeal01Origin() {
    return seal01Origin;
  }


  public void setSeal01Origin(String seal01Origin) {
    this.seal01Origin = StringUtils.upperCase(seal01Origin);
  }


  public String getSeal01Type() {
    return seal01Type;
  }


  public void setSeal01Type(String seal01Type) {
    this.seal01Type = StringUtils.upperCase(seal01Type);
  }


  public String getSeal01Number() {
    return seal01Number;
  }


  public void setSeal01Number(String seal01Number) {
    this.seal01Number = StringUtils.upperCase(seal01Number);
  }


  public String getSeal02Origin() {
    return seal02Origin;
  }


  public void setSeal02Origin(String seal02Origin) {
    this.seal02Origin = StringUtils.upperCase(seal02Origin);
  }


  public String getSeal02Type() {
    return seal02Type;
  }


  public void setSeal02Type(String seal02Type) {
    this.seal02Type = StringUtils.upperCase(seal02Type);
  }


  public String getSeal02Number() {
    return seal02Number;
  }


  public void setSeal02Number(String seal02Number) {
    this.seal02Number = StringUtils.upperCase(seal02Number);
  }


  public String getRemarks() {
    return remarks;
  }


  public void setRemarks(String remarks) {
    this.remarks = StringUtils.upperCase(remarks);
  }


  public String getStatus() {
    return status;
  }


  public void setStatus(String status) {
    this.status = StringUtils.upperCase(status);
  }


  public int getExpPmBTM() {
    return expPmBTM;
  }


  public void setExpPmBTM(int expPmBTM) {
    this.expPmBTM = expPmBTM;
  }


  public int getExpNetWeight() {
    return expNetWeight;
  }


  public void setExpNetWeight(int expNetWeight) {
    this.expNetWeight = expNetWeight;
  }


  public String getDoubleBooking() {
    return doubleBooking;
  }


  public void setDoubleBooking(String doubleBooking) {
    this.doubleBooking = StringUtils.upperCase(doubleBooking);
  }


  public int getMeasuredWeightBridge() {
    return measuredWeightBridge;
  }


  public void setMeasuredWeightBridge(int measuredWeightBridge) {
    this.measuredWeightBridge = measuredWeightBridge;
  }


  public int getMgw() {
    return mgw;
  }


  public void setMgw(int mgw) {
    this.mgw = mgw;
  }


  public String getFaLedgerCode() {
    return faLedgerCode;
  }


  public void setFaLedgerCode(String faLedgerCode) {
    this.faLedgerCode = StringUtils.upperCase(faLedgerCode);
  }


  public String getSolasRefNumber() {
    return solasRefNumber;
  }


  public void setSolasRefNumber(String solasRefNumber) {
    this.solasRefNumber = StringUtils.upperCase(solasRefNumber);
  }


  public String getSolasDetailID() {
    return solasDetailID;
  }


  public void setSolasDetailID(String solasDetailID) {
    this.solasDetailID = StringUtils.upperCase(solasDetailID);
  }


  public String getSolasInstruction() {
    return solasInstruction;
  }


  public void setSolasInstruction(String solasInstruction) {
    this.solasInstruction = StringUtils.upperCase(solasInstruction);
  }


  public String getShipperVGM() {
    return shipperVGM;
  }


  public void setShipperVGM(String shipperVGM) {
    this.shipperVGM = StringUtils.upperCase(shipperVGM);
  }


  public String getSupervisorRemarks() {
    return supervisorRemarks;
  }


  public void setSupervisorRemarks(String supervisorRemarks) {
    this.supervisorRemarks = StringUtils.upperCase(supervisorRemarks);
  }


  public String getLineCode() {
    return lineCode;
  }


  public void setLineCode(String lineCode) {
    this.lineCode = StringUtils.upperCase(lineCode);
  }


  public LocalDateTime getGateInTime() {
    return gateInTime;
  }


  public void setGateInTime(LocalDateTime gateInTime) {
    this.gateInTime = gateInTime;
  }


  public List<Long> getReferReasonIds() {
    return referReasonIds;
  }


  public void setReferReasonIds(List<Long> referReasonIds) {
    this.referReasonIds = referReasonIds;
  }


  public ReferRejectDetail convertToReferRejectDetailDomain(ReferRejectDetail referRejectDetail) {
    if (referRejectDetail == null) {
      referRejectDetail = new ReferRejectDetail();
    }
    BeanUtils.copyProperties(this, referRejectDetail);
    return referRejectDetail;
  }



}
