package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GateOutWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userName = StringUtils.EMPTY;
  private String laneNo = StringUtils.EMPTY;// -long (clientID)
  private String haulageCode = StringUtils.EMPTY;// -long (clientID)
  private String truckHeadNo = StringUtils.EMPTY;// -string
  private String truckPlateNo = StringUtils.EMPTY;// -long (clientID)
  private int truckWeight = 0;
  private String trailerNo = StringUtils.EMPTY;
  private int trailerWeight = 0;
  private String gateOUTDateTime = StringUtils.EMPTY;// -string
  private Integer weightBridge = 0;// -long

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


  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
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

  public static GateOutWriteRequest emptyGateOutWriteRequest() {
    GateOutWriteRequest req = new GateOutWriteRequest();
    req.setExportContainers(new ArrayList<>());
    req.setImportContainers(new ArrayList<>());
    req.getExportContainers().add(ExportContainer.emptyExportContainer());
    req.getImportContainers().add(ImportContainer.emptyImportContainer());
    return req;
  }

}
