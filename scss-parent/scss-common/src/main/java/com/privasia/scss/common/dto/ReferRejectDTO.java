package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.util.DateUtil;

public class ReferRejectDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long referRejectID;

  private CompanyDTO company;

  private Integer expWeightBridge;

  private Integer expNetWeight;

  private String statusCode;

  private BaseCommonGateInOutDTO baseCommonGateInOut;

  private Boolean transactionSlipPrinted;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  private LocalDateTime referDateTime;

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

  public LocalDateTime getReferDateTime() {
    return referDateTime;
  }

  public void setReferDateTime(LocalDateTime referDateTime) {
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

  public BaseCommonGateInOutDTO getBaseCommonGateInOut() {
    return baseCommonGateInOut;
  }

  public void setBaseCommonGateInOut(BaseCommonGateInOutDTO baseCommonGateInOut) {
    this.baseCommonGateInOut = baseCommonGateInOut;
  }

  public Set<ReferRejectDetailDTO> getReferRejectDetails() {
    return referRejectDetails;
  }

  public void setReferRejectDetails(Set<ReferRejectDetailDTO> referRejectDetails) {
    this.referRejectDetails = referRejectDetails;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

  public CompanyDTO getCompany() {
    return company;
  }

  public void setCompany(CompanyDTO company) {
    this.company = company;
  }

  public ReferRejectDTO initializeWithDefaultValues() {
    this.referRejectID = 10l;
    this.company = new CompanyDTO().initializeWithDefaultValues();
    this.expWeightBridge = 4500;
    this.expNetWeight = 250;
    this.statusCode = HpatReferStatus.ACTIVE.getValue();
    // this.baseCommonGateInOut = new BaseCommonGateInOutDTO().initializeWithDefaultValues();
    this.transactionSlipPrinted = false;
    this.referDateTime = LocalDateTime.now();
    this.pmWeight = 4500;
    this.trailerWeight = 5500;
    this.trailerPlateNo = "";
    this.axleVerified = true;
    this.pmVerified = true;
    // this.referRejectDetails = Optional.of(new HashSet<ReferRejectDetailDTO>());
    // this.referRejectDetails.get().add(new ReferRejectDetailDTO().initializeWithDefaultValues());
    // this.referRejectDetails.get().add(new ReferRejectDetailDTO().initializeWithDefaultValues());
    return this;

  }

}
