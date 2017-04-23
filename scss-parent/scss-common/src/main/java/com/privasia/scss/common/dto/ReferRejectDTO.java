package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.Set;

public class ReferRejectDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long referRejectID;

  private CompanyDTO company;

  private Integer expWeightBridge;

  private Integer expNetWeight;

  private String statusCode;

  private BaseCommonGateInOutDTO baseCommonGateInOut;

  private Boolean transactionSlipPrinted;

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


  public Long getReferRejectID() {
    return referRejectID;
  }

  public void setReferRejectID(Long referRejectID) {
    this.referRejectID = referRejectID;
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

  public Boolean getTransactionSlipPrinted() {
    return transactionSlipPrinted;
  }

  public void setTransactionSlipPrinted(Boolean transactionSlipPrinted) {
    this.transactionSlipPrinted = transactionSlipPrinted;
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

  public BaseCommonGateInOutDTO getBaseCommonGateInOut() {
    return baseCommonGateInOut;
  }

  public void setBaseCommonGateInOut(BaseCommonGateInOutDTO baseCommonGateInOut) {
    this.baseCommonGateInOut = baseCommonGateInOut;
  }


  @Override
  public String toString() {
    return "ReferRejectDTO [referRejectID=" + referRejectID + ", company=" + company + ", expWeightBridge="
        + expWeightBridge + ", expNetWeight=" + expNetWeight + ", statusCode=" + statusCode + ", baseCommonGateInOut="
        + baseCommonGateInOut + ", transactionSlipPrinted=" + transactionSlipPrinted + ", referDateTime="
        + referDateTime + ", pmWeight=" + pmWeight + ", trailerWeight=" + trailerWeight + ", trailerPlateNo="
        + trailerPlateNo + ", axleVerified=" + axleVerified + ", pmVerified=" + pmVerified + ", referRejectDetails="
        + referRejectDetails + "]";
  }

  public CompanyDTO getCompany() {
    return company;
  }


  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

}
