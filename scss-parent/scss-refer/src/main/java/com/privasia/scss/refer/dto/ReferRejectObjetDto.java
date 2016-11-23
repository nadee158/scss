package com.privasia.scss.refer.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.ReferReject;

public class ReferRejectObjetDto implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  // Card
  private long card = 0;

  private String transactionStatus = StringUtils.EMPTY;

  // ReferReject OBJECT ---------------------------------------

  private long referId;

  // System User
  private long gateInClerk = 0;

  // Client
  private long gateInClient = 0;

  // Client IP
  private String gateInClientIP = StringUtils.EMPTY;

  private String pmHeadNo = StringUtils.EMPTY;

  private int expWeightBridge = 0;

  private long hpatBooking = 0;

  private String statusCode = HpatReferStatus.ACTIVE.getValue();

  private String referDateTime = StringUtils.EMPTY;

  private Long cardUsageID = null;

  private String pmPlateNo = StringUtils.EMPTY;

  // Company
  private Long companyID = null;

  private String timeGateIn = StringUtils.EMPTY;

  private String timeGateInOk = StringUtils.EMPTY;

  private int pmWeight = 0;

  private int trailerWeight = 0;

  private String trailerPlateNo = StringUtils.EMPTY;

  private boolean axleVerified = false;

  private boolean pmVerified = false;

  private ReferRejectDetailObjetDto referRejectDetail = null;

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

  public long getReferId() {
    return referId;
  }

  public void setReferId(long referId) {
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
    referReject.setExpWeightBridge(expWeightBridge);
    if (StringUtils.isNotEmpty(statusCode)) {
      referReject.setStatusCode(HpatReferStatus.fromCode(statusCode));
    }
    referReject.setReferDateTime(CommonUtil.getParsedDate(referDateTime));
    referReject.setPmWeight(pmWeight);
    referReject.setTrailerWeight(trailerWeight);
    referReject.setTrailerPlateNo(trailerPlateNo);
    referReject.setAxleVerified(axleVerified);
    referReject.setPmVerified(pmVerified);
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

  public String getReferDateTime() {
    return referDateTime;
  }

  public void setReferDateTime(String referDateTime) {
    this.referDateTime = referDateTime;
  }

  public String getTimeGateIn() {
    return timeGateIn;
  }

  public void setTimeGateIn(String timeGateIn) {
    this.timeGateIn = timeGateIn;
  }

  public String getTimeGateInOk() {
    return timeGateInOk;
  }

  public void setTimeGateInOk(String timeGateInOk) {
    this.timeGateInOk = timeGateInOk;
  }

  public String getGateInClientIP() {
    return gateInClientIP;
  }

  public void setGateInClientIP(String gateInClientIP) {
    this.gateInClientIP = gateInClientIP;
  }

}
