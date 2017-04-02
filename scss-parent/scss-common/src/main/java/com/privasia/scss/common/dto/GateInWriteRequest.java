package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
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

  public GateInWriteRequest initializeWithDefaultValues() {
    this.userName = "USER01";
    this.laneNo = "GATE00";// -long (clientID)
    this.haulageCode = "HAN";// -long (clientID)
    this.truckHeadNo = "HAN001";// -string
    this.truckPlateNo = "60P1-2933";// -long (clientID)
    this.truckWeight = 4000;
    this.trailerNo = "1122";
    this.trailerWeight = 4000;
    this.gateInDateTime = "04/11/2017 01:22:55 am";// -string
    this.weightBridge = 500;// -long

    this.exportContainers = new ArrayList<ExportContainer>();
    this.exportContainers.add((new ExportContainer()).initializeWithDefaultValues("VSLCONT0011"));
    this.exportContainers.add((new ExportContainer()).initializeWithDefaultValues("QASS1234003"));
    this.importContainers = new ArrayList<ImportContainer>();
    this.importContainers.add((new ImportContainer()).initializeWithDefaultValues("ASIA1234562"));
    this.importContainers.add((new ImportContainer()).initializeWithDefaultValues("ASIA1234563"));
    return this;
  }

}
