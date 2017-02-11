package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;

public class GateInWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName;
  private String laneNo;// -long (clientID)
  private String haulageCode;// -long (clientID)
  private String truckHeadNo;// -string
  private String truckPlateNo;// -long (clientID)
  private int truckWeight;
  private String trailerNo;
  private int trailerWeight;
  private String gateInDateTime;// -string
  private Integer weightBridge;// -long

  private List<ExportContainer> exportContainers;
  private List<ImportContainer> importContainers;

  public String getLaneNo() {
    return laneNo;
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

  public String getGateInDateTime() {
    return gateInDateTime;
  }

  public void setGateInDateTime(String gateInDateTime) {
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

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }



}
