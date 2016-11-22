package com.privasia.scss.refer.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;

public class ReferRejectDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long referRejectID;

  private Long companyID;

  private String companyName;

  private Integer expWeightBridge;

  private Integer expNetWeight;

  private String statusCode;

  private String pmHeadNo;

  private String pmPlateNo;

  private String bookingID;

  private String eirStatus;

  private Boolean transactionSlipPrinted;

  private String gateOutBoothNo;

  private String timeGateIn;

  private String timeGateInOk;

  private String timeGateOut;

  private String timeGateOutOk;

  private String timeGateOutBooth;

  private Long cardID;

  private Long cardNo;

  private Long gateInClerkId;

  private Long gateOutClerkId;

  private Long gateInClientId;

  private Long gateOutClientId;

  private Long gateOutBoothClerkId;

  private String referDateTime;

  private Integer pmWeight;

  private Integer trailerWeight;

  private String trailerPlateNo;

  private Boolean axleVerified;

  private Boolean pmVerified;

  private Set<ReferRejectDetailDTO> referRejectDetails;



  public ReferRejectDTO() {
    super();
  }

  public ReferRejectDTO(ReferReject referReject) {
    super();
    this.referRejectID = referReject.getReferRejectID();
    if (!(referReject.getCompany() == null)) {
      this.companyID = referReject.getCompany().getCompanyID();
      this.companyName = referReject.getCompany().getCompanyName();
    }
    this.expWeightBridge = referReject.getExpWeightBridge();
    this.expNetWeight = referReject.getExpNetWeight();
    if (!(referReject.getStatusCode() == null)) {
      this.statusCode = referReject.getStatusCode().getValue();
    }
    BaseCommonGateInOutAttribute com = referReject.getBaseCommonGateInOut();
    if (!(com == null)) {
      this.pmHeadNo = com.getPmHeadNo();
      this.pmPlateNo = com.getPmPlateNo();
      if (!(com.getHpatBooking() == null)) {
        this.bookingID = com.getHpatBooking().getBookingID();
      }
      if (!(com.getEirStatus() == null)) {
        this.eirStatus = com.getEirStatus().getValue();
      }
      this.transactionSlipPrinted = com.isTransactionSlipPrinted();

      this.gateOutBoothNo = com.getGateOutBoothNo();

      this.timeGateIn = CommonUtil.getFormatteDate(com.getTimeGateIn());

      this.timeGateInOk = CommonUtil.getFormatteDate(com.getTimeGateInOk());

      this.timeGateOut = CommonUtil.getFormatteDate(com.getTimeGateOut());

      this.timeGateOutOk = CommonUtil.getFormatteDate(com.getTimeGateOutOk());

      this.timeGateOutBooth = CommonUtil.getFormatteDate(com.getTimeGateOutBooth());

      if (!(com.getCard() == null)) {
        this.cardID = com.getCard().getCardID();
        this.cardNo = com.getCard().getCardNo();
      }

      if (!(com.getGateInClerk() == null)) {
        this.gateInClerkId = com.getGateInClerk().getSystemUserID();
      }

      if (!(com.getGateOutClerk() == null)) {
        this.gateOutClerkId = com.getGateOutClerk().getSystemUserID();
      }
      if (!(com.getGateInClient() == null)) {
        this.gateInClientId = com.getGateInClient().getClientID();
      }
      if (!(com.getGateOutClient() == null)) {
        this.gateOutClientId = com.getGateOutClient().getClientID();
      }
      if (!(com.getGateOutBoothClerk() == null)) {
        this.gateOutBoothClerkId = com.getGateOutBoothClerk().getSystemUserID();
      }
    }
    this.referDateTime = CommonUtil.getFormatteDate(referReject.getReferDateTime());

    this.pmWeight = referReject.getPmWeight();

    this.trailerWeight = referReject.getTrailerWeight();

    this.trailerPlateNo = referReject.getTrailerPlateNo();

    this.axleVerified = referReject.isAxleVerified();

    this.pmVerified = referReject.isPmVerified();

    this.referRejectDetails = constructReferRejectDetails(referReject.getReferRejectDetails());
  }

  private Set<ReferRejectDetailDTO> constructReferRejectDetails(Set<ReferRejectDetail> details) {
    if (!(details == null || details.isEmpty())) {
      Set<ReferRejectDetailDTO> detailDtos = new HashSet<ReferRejectDetailDTO>();
      details.forEach(detail -> {
        detailDtos.add(new ReferRejectDetailDTO(detail));
      });
      return detailDtos;
    }
    return null;
  }

  public Long getReferRejectID() {
    return referRejectID;
  }

  public void setReferRejectID(Long referRejectID) {
    this.referRejectID = referRejectID;
  }

  public Long getCompanyID() {
    return companyID;
  }

  public void setCompanyID(Long companyID) {
    this.companyID = companyID;
  }

  public String getCompanyName() {
    return companyName;
  }

  public void setCompanyName(String companyName) {
    this.companyName = companyName;
  }

  public Integer getExpWeightBridge() {
    return expWeightBridge;
  }

  public void setExpWeightBridge(Integer expWeightBridge) {
    this.expWeightBridge = expWeightBridge;
  }

  public Integer getExpNetWeight() {
    return expNetWeight;
  }

  public void setExpNetWeight(Integer expNetWeight) {
    this.expNetWeight = expNetWeight;
  }

  public String getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(String statusCode) {
    this.statusCode = statusCode;
  }

  public String getPmHeadNo() {
    return pmHeadNo;
  }

  public void setPmHeadNo(String pmHeadNo) {
    this.pmHeadNo = pmHeadNo;
  }

  public String getPmPlateNo() {
    return pmPlateNo;
  }

  public void setPmPlateNo(String pmPlateNo) {
    this.pmPlateNo = pmPlateNo;
  }

  public String getBookingID() {
    return bookingID;
  }

  public void setBookingID(String bookingID) {
    this.bookingID = bookingID;
  }

  public String getEirStatus() {
    return eirStatus;
  }

  public void setEirStatus(String eirStatus) {
    this.eirStatus = eirStatus;
  }

  public Boolean getTransactionSlipPrinted() {
    return transactionSlipPrinted;
  }

  public void setTransactionSlipPrinted(Boolean transactionSlipPrinted) {
    this.transactionSlipPrinted = transactionSlipPrinted;
  }

  public String getGateOutBoothNo() {
    return gateOutBoothNo;
  }

  public void setGateOutBoothNo(String gateOutBoothNo) {
    this.gateOutBoothNo = gateOutBoothNo;
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

  public String getTimeGateOut() {
    return timeGateOut;
  }

  public void setTimeGateOut(String timeGateOut) {
    this.timeGateOut = timeGateOut;
  }

  public String getTimeGateOutOk() {
    return timeGateOutOk;
  }

  public void setTimeGateOutOk(String timeGateOutOk) {
    this.timeGateOutOk = timeGateOutOk;
  }

  public String getTimeGateOutBooth() {
    return timeGateOutBooth;
  }

  public void setTimeGateOutBooth(String timeGateOutBooth) {
    this.timeGateOutBooth = timeGateOutBooth;
  }

  public Long getCardID() {
    return cardID;
  }

  public void setCardID(Long cardID) {
    this.cardID = cardID;
  }

  public Long getCardNo() {
    return cardNo;
  }

  public void setCardNo(Long cardNo) {
    this.cardNo = cardNo;
  }

  public Long getGateInClerkId() {
    return gateInClerkId;
  }

  public void setGateInClerkId(Long gateInClerkId) {
    this.gateInClerkId = gateInClerkId;
  }

  public Long getGateOutClerkId() {
    return gateOutClerkId;
  }

  public void setGateOutClerkId(Long gateOutClerkId) {
    this.gateOutClerkId = gateOutClerkId;
  }

  public Long getGateInClientId() {
    return gateInClientId;
  }

  public void setGateInClientId(Long gateInClientId) {
    this.gateInClientId = gateInClientId;
  }

  public Long getGateOutClientId() {
    return gateOutClientId;
  }

  public void setGateOutClientId(Long gateOutClientId) {
    this.gateOutClientId = gateOutClientId;
  }

  public Long getGateOutBoothClerkId() {
    return gateOutBoothClerkId;
  }

  public void setGateOutBoothClerkId(Long gateOutBoothClerkId) {
    this.gateOutBoothClerkId = gateOutBoothClerkId;
  }

  public String getReferDateTime() {
    return referDateTime;
  }

  public void setReferDateTime(String referDateTime) {
    this.referDateTime = referDateTime;
  }

  public Integer getPmWeight() {
    return pmWeight;
  }

  public void setPmWeight(Integer pmWeight) {
    this.pmWeight = pmWeight;
  }

  public Integer getTrailerWeight() {
    return trailerWeight;
  }

  public void setTrailerWeight(Integer trailerWeight) {
    this.trailerWeight = trailerWeight;
  }

  public String getTrailerPlateNo() {
    return trailerPlateNo;
  }

  public void setTrailerPlateNo(String trailerPlateNo) {
    this.trailerPlateNo = trailerPlateNo;
  }

  public Boolean getAxleVerified() {
    return axleVerified;
  }

  public void setAxleVerified(Boolean axleVerified) {
    this.axleVerified = axleVerified;
  }

  public Boolean getPmVerified() {
    return pmVerified;
  }

  public void setPmVerified(Boolean pmVerified) {
    this.pmVerified = pmVerified;
  }

  public Set<ReferRejectDetailDTO> getReferRejectDetails() {
    return referRejectDetails;
  }

  public void setReferRejectDetails(Set<ReferRejectDetailDTO> referRejectDetails) {
    this.referRejectDetails = referRejectDetails;
  }



}
