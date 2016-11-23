package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.SealOriginType;
import com.privasia.scss.common.enums.SealType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.CommonSealAttribute;
import com.privasia.scss.core.model.CommonSolasAttribute;
import com.privasia.scss.core.model.ReferRejectDetail;

public class ReferRejectDetailObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // ReferRejectDetail OBJECT ---------------------------------------
  private long referRejectDetailID;

  private String containerNo = StringUtils.EMPTY;

  // enum
  private String position = StringUtils.EMPTY;

  private String containerIsoCode = StringUtils.EMPTY;

  private String seal01Origin = SealOriginType.L.getValue();

  private String seal01Type = SealType.SL.getValue();

  private String seal01Number = StringUtils.EMPTY;

  private String seal02Origin = SealOriginType.S.getValue();

  private String seal02Type = SealType.SL.getValue();

  private String seal02Number = StringUtils.EMPTY;

  private String remarks = StringUtils.EMPTY;

  private String status = ReferStatus.NONE.getValue();

  private int expPmBTM = 0;

  private int expNetWeight = 0;

  private String doubleBooking = StringUtils.EMPTY;

  private int measuredWeightBridge = 0;

  private int mgw = 0;

  private String faLedgerCode = StringUtils.EMPTY;

  private String solasRefNumber = StringUtils.EMPTY;

  private String solasDetailID = StringUtils.EMPTY;

  private String solasInstruction = StringUtils.EMPTY;

  private String shipperVGM = StringUtils.EMPTY;

  private String supervisorRemarks = StringUtils.EMPTY;

  private String lineCode = StringUtils.EMPTY;

  private String gateInTime = StringUtils.EMPTY;


  // ReferRejectReason objects
  private List<Long> referReasonIds = null;



  public long getReferRejectDetailID() {
    return referRejectDetailID;
  }


  public void setReferRejectDetailID(long referRejectDetailID) {
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



  public String getGateInTime() {
    return gateInTime;
  }


  public void setGateInTime(String gateInTime) {
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

    referRejectDetail.setContainerNo(containerNo);
    referRejectDetail.setContainerIsoCode(containerIsoCode);

    if (referRejectDetail.getSeal() == null) {
      referRejectDetail.setSeal(new CommonSealAttribute());
    }

    referRejectDetail.setSeal(updateSeal(referRejectDetail.getSeal()));

    referRejectDetail.setRemarks(remarks);
    if (StringUtils.isNotEmpty(status)) {
      referRejectDetail.setStatus(ReferStatus.fromValue(status));
    }
    referRejectDetail.setSupervisorRemarks(supervisorRemarks);
    referRejectDetail.setExpPmBTM(expPmBTM);
    referRejectDetail.setExpNetWeight(expNetWeight);
    referRejectDetail.setDoubleBooking(doubleBooking);
    referRejectDetail.setLineCode(lineCode);
    referRejectDetail.setGateInTime(CommonUtil.getParsedDate(gateInTime));
    if (StringUtils.isNotEmpty(position)) {
      referRejectDetail.setPosition(ContainerPosition.fromValue(position));
    }
    referRejectDetail.setMeasuredWeightBridge(measuredWeightBridge);
    referRejectDetail.setSolas(constructSolas(referRejectDetail.getSolas()));


    return referRejectDetail;
  }


  private CommonSolasAttribute constructSolas(CommonSolasAttribute solas) {
    if (solas == null) {
      solas = new CommonSolasAttribute();
    }
    solas.setFaLedgerCode(faLedgerCode);
    solas.setMgw(mgw);
    solas.setShipperVGM(shipperVGM);
    solas.setSolasDetailID(solasDetailID);
    if (StringUtils.isNotEmpty(solasInstruction)) {
      solas.setSolasInstruction(SolasInstructionType.fromValue(solasInstruction));
    }
    solas.setSolasRefNumber(solasRefNumber);
    return solas;
  }


  private CommonSealAttribute updateSeal(CommonSealAttribute seal) {
    seal.setSeal01Origin(seal01Origin);
    seal.setSeal01Type(seal01Type);
    seal.setSeal01Number(seal01Number);
    seal.setSeal02Origin(seal02Origin);
    seal.setSeal02Type(seal02Type);
    seal.setSeal02Number(seal02Number);
    return seal;
  }



}
