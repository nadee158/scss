package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.ReferReject;

public class ReferRejectObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Card
  private long card;
  private String transactionStatus;

  // ReferReject OBJECT ---------------------------------------

  private Optional<Long> referId;

  // System User
  private long gateInClerk;

  // Client
  private long gateInClient;

  private String pmHeadNo;

  private int expWeightBridge;

  private long hpatBooking;

  private String statusCode = HpatReferStatus.ACTIVE.getValue();

  private LocalDateTime referDateTime = LocalDateTime.now();

  private long addBy;

  private long updateBy;

  private ZonedDateTime dateTimeAdd = ZonedDateTime.now();

  private ZonedDateTime dateTimeUpdate = ZonedDateTime.now();

  private Long cardUsageID;

  private String pmPlateNo;

  // Company
  private Long companyID;

  private LocalDateTime timeGateIn;

  private LocalDateTime timeGateInOk = LocalDateTime.now();

  private int pmWeight;

  private int trailerWeight;

  private String trailerPlateNo;

  private boolean axleVerified;

  private boolean pmVerified;

  private ReferRejectDetailObjetDto referRejectDetail;


  public long getCard() {
    return card;
  }


  public void setCard(long card) {
    this.card = card;
  }


  public String getTransactionStatus() {
    return transactionStatus;
  }


  public void setTransactionStatus(String transactionStatus) {
    this.transactionStatus = StringUtils.upperCase(transactionStatus);
  }


  public Optional<Long> getReferId() {
    return referId;
  }


  public void setReferId(Optional<Long> referId) {
    this.referId = referId;
  }


  public long getGateInClerk() {
    return gateInClerk;
  }


  public void setGateInClerk(long gateInClerk) {
    this.gateInClerk = gateInClerk;
  }


  public long getGateInClient() {
    return gateInClient;
  }


  public void setGateInClient(long gateInClient) {
    this.gateInClient = gateInClient;
  }


  public String getPmHeadNo() {
    return pmHeadNo;
  }


  public void setPmHeadNo(String pmHeadNo) {
    this.pmHeadNo = StringUtils.upperCase(pmHeadNo);
  }


  public int getExpWeightBridge() {
    return expWeightBridge;
  }


  public void setExpWeightBridge(int expWeightBridge) {
    this.expWeightBridge = expWeightBridge;
  }


  public long getHpatBooking() {
    return hpatBooking;
  }


  public void setHpatBooking(long hpatBooking) {
    this.hpatBooking = hpatBooking;
  }


  public String getStatusCode() {
    return statusCode;
  }


  public void setStatusCode(String statusCode) {
    this.statusCode = StringUtils.upperCase(statusCode);
  }


  public LocalDateTime getReferDateTime() {
    return referDateTime;
  }


  public void setReferDateTime(LocalDateTime referDateTime) {
    this.referDateTime = referDateTime;
  }


  public long getAddBy() {
    return addBy;
  }


  public void setAddBy(long addBy) {
    this.addBy = addBy;
  }


  public long getUpdateBy() {
    return updateBy;
  }


  public void setUpdateBy(long updateBy) {
    this.updateBy = updateBy;
  }


  public ZonedDateTime getDateTimeAdd() {
    return dateTimeAdd;
  }


  public void setDateTimeAdd(ZonedDateTime dateTimeAdd) {
    this.dateTimeAdd = dateTimeAdd;
  }


  public ZonedDateTime getDateTimeUpdate() {
    return dateTimeUpdate;
  }


  public void setDateTimeUpdate(ZonedDateTime dateTimeUpdate) {
    this.dateTimeUpdate = dateTimeUpdate;
  }


  public Long getCardUsageID() {
    return cardUsageID;
  }


  public void setCardUsageID(Long cardUsageID) {
    this.cardUsageID = cardUsageID;
  }


  public String getPmPlateNo() {
    return pmPlateNo;
  }


  public void setPmPlateNo(String pmPlateNo) {
    this.pmPlateNo = StringUtils.upperCase(pmPlateNo);
  }


  public Long getCompanyID() {
    return companyID;
  }


  public void setCompanyID(Long companyID) {
    this.companyID = companyID;
  }


  public LocalDateTime getTimeGateIn() {
    return timeGateIn;
  }


  public void setTimeGateIn(LocalDateTime timeGateIn) {
    this.timeGateIn = timeGateIn;
  }


  public LocalDateTime getTimeGateInOk() {
    return timeGateInOk;
  }


  public void setTimeGateInOk(LocalDateTime timeGateInOk) {
    this.timeGateInOk = timeGateInOk;
  }


  public int getPmWeight() {
    return pmWeight;
  }


  public void setPmWeight(int pmWeight) {
    this.pmWeight = pmWeight;
  }


  public int getTrailerWeight() {
    return trailerWeight;
  }


  public void setTrailerWeight(int trailerWeight) {
    this.trailerWeight = trailerWeight;
  }


  public String getTrailerPlateNo() {
    return trailerPlateNo;
  }


  public void setTrailerPlateNo(String trailerPlateNo) {
    this.trailerPlateNo = StringUtils.upperCase(trailerPlateNo);
  }


  public boolean isAxleVerified() {
    return axleVerified;
  }


  public void setAxleVerified(boolean axleVerified) {
    this.axleVerified = axleVerified;
  }


  public boolean isPmVerified() {
    return pmVerified;
  }


  public void setPmVerified(boolean pmVerified) {
    this.pmVerified = pmVerified;
  }


  public ReferRejectDetailObjetDto getReferRejectDetail() {
    return referRejectDetail;
  }


  public void setReferRejectDetail(ReferRejectDetailObjetDto referRejectDetail) {
    this.referRejectDetail = referRejectDetail;
  }


  public ReferReject convertToReferRejectDomain(ReferReject referReject) {
    if (referReject == null) {
      referReject = new ReferReject();
    }
    BeanUtils.copyProperties(this, referReject);

    return referReject;
  }


  public BaseCommonGateInOutAttribute convertToBaseCommonGateInOutAttribute(
      BaseCommonGateInOutAttribute baseCommonGateInOut) {
    if (baseCommonGateInOut == null) {
      baseCommonGateInOut = new BaseCommonGateInOutAttribute();
    }
    BeanUtils.copyProperties(this, baseCommonGateInOut);
    return baseCommonGateInOut;
  }



}
