package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateInWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName;
  private String laneNo;// -long (clientID)
  private String haulageCode;// -long (clientID)

  @NotNull(message = "truckHeadNo is required!")
  private String truckHeadNo;// -string

  // @NotNull(message = "truckPlateNo is required!")
  private String truckPlateNo;// -long (clientID)

  private int truckWeight;
  private String trailerNo;
  private int trailerWeight;

  @NotNull(message = "gateInDateTime is required!")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
  private LocalDateTime gateInDateTime;// -string

  private Integer weightBridge;// -long
  private int totalEmptyWeightBridge = 0;

  @NotNull(message = "gateInClient is required!")
  private Long gateInClient;

  @NotNull(message = "cardId is required!")
  private Long cardId;

  private String hpatBookingId;
  private String rejectReason;
  private String gateInStatus;

  private boolean oddReject = false;

  @NotNull(message = "impExpFlag is required!") // check if an allowed value
                                                // in enum
  private String impExpFlag;

  private String fuelWeight;// :"45",
  private String tireWeight;// ":"45",
  private String variance;//

  private List<ExportContainer> exportContainers;
  private List<ImportContainer> importContainers;

  private List<WHODDDTO> whoddContainers;

  private Optional<ReferRejectDTO> referRejectDTO;

  public String getLaneNo() {
    return laneNo;
  }


  public Optional<ReferRejectDTO> getReferRejectDTO() {
    return referRejectDTO;
  }


  public void setReferRejectDTO(Optional<ReferRejectDTO> referRejectDTO) {
    this.referRejectDTO = referRejectDTO;
  }


  public void setLaneNo(String laneNo) {
    this.laneNo = laneNo;
  }

  public String getHaulageCode() {
    return haulageCode;
  }

  public void setHaulageCode(String haulageCode) {
    this.haulageCode = haulageCode;
  }

  public String getTruckHeadNo() {
    return truckHeadNo;
  }

  public void setTruckHeadNo(String truckHeadNo) {
    this.truckHeadNo = truckHeadNo;
  }

  public String getTruckPlateNo() {
    return truckPlateNo;
  }

  public void setTruckPlateNo(String truckPlateNo) {
    this.truckPlateNo = truckPlateNo;
  }

  public int getTruckWeight() {
    return truckWeight;
  }

  public void setTruckWeight(int truckWeight) {
    this.truckWeight = truckWeight;
  }

  public String getTrailerNo() {
    return trailerNo;
  }

  public void setTrailerNo(String trailerNo) {
    this.trailerNo = trailerNo;
  }

  public int getTrailerWeight() {
    return trailerWeight;
  }

  public void setTrailerWeight(int trailerWeight) {
    this.trailerWeight = trailerWeight;
  }

  public LocalDateTime getGateInDateTime() {
    return gateInDateTime;
  }

  public void setGateInDateTime(LocalDateTime gateInDateTime) {
    this.gateInDateTime = gateInDateTime;
  }

  public Integer getWeightBridge() {
    return weightBridge;
  }

  public void setWeightBridge(Integer weightBridge) {
    this.weightBridge = weightBridge;
  }

  public List<ExportContainer> getExportContainers() {
    return exportContainers;
  }

  public void setExportContainers(List<ExportContainer> exportContainers) {
    this.exportContainers = exportContainers;
  }

  public List<ImportContainer> getImportContainers() {
    return importContainers;
  }

  public void setImportContainers(List<ImportContainer> importContainers) {
    this.importContainers = importContainers;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getGateInClient() {
    return gateInClient;
  }

  public void setGateInClient(Long gateInClient) {
    this.gateInClient = gateInClient;
  }

  public Long getCardId() {
    return cardId;
  }

  public void setCardId(Long cardId) {
    this.cardId = cardId;
  }

  public List<WHODDDTO> getWhoddContainers() {
    return whoddContainers;
  }

  public void setWhoddContainers(List<WHODDDTO> whoddContainers) {
    this.whoddContainers = whoddContainers;
  }

  public String getHpatBookingId() {
    return hpatBookingId;
  }

  public void setHpatBookingId(String hpatBookingId) {
    this.hpatBookingId = hpatBookingId;
  }

  public String getRejectReason() {
    return rejectReason;
  }

  public void setRejectReason(String rejectReason) {
    this.rejectReason = rejectReason;
  }

  public String getGateInStatus() {
    return gateInStatus;
  }

  public void setGateInStatus(String gateInStatus) {
    this.gateInStatus = gateInStatus;
  }

  public int getTotalEmptyWeightBridge() {
    return totalEmptyWeightBridge;
  }

  public void setTotalEmptyWeightBridge(int totalEmptyWeightBridge) {
    this.totalEmptyWeightBridge = totalEmptyWeightBridge;
  }

  public String getImpExpFlag() {
    return impExpFlag;
  }

  public void setImpExpFlag(String impExpFlag) {
    this.impExpFlag = impExpFlag;
  }

  public String getFuelWeight() {
    return fuelWeight;
  }

  public void setFuelWeight(String fuelWeight) {
    this.fuelWeight = fuelWeight;
  }

  public String getTireWeight() {
    return tireWeight;
  }

  public void setTireWeight(String tireWeight) {
    this.tireWeight = tireWeight;
  }

  public String getVariance() {
    return variance;
  }

  public void setVariance(String variance) {
    this.variance = variance;
  }

  public boolean isOddReject() {
    return oddReject;
  }

  public void setOddReject(boolean oddReject) {
    this.oddReject = oddReject;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
  }

}
