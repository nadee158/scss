package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class OpusBaseGateWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  // common fileds in write requests

  private String userID;// "PRABU"
  private String laneNo;// "GATE00"
  private String haulageCode;// "HAN"
  private String truckHeadNo;// "NTK193"
  private String truckPlateNo;// "60P1-2933"
  private List<OpusExporterContainer> exportContainerListCY;//
  private List<OpusImportContainer> importContainerListCY;//
  private List<OpusExporterContainer> exportContainerListCFS;// []
  private List<OpusImportContainer> importContainerListCFS;// []

  public String getUserID() {
    return userID;
  }

  public void setUserID(String userID) {
    this.userID = userID;
  }

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

  public List<OpusExporterContainer> getExportContainerListCY() {
    return exportContainerListCY;
  }

  public void setExportContainerListCY(List<OpusExporterContainer> exportContainerListCY) {
    this.exportContainerListCY = exportContainerListCY;
  }

  public List<OpusImportContainer> getImportContainerListCY() {
    return importContainerListCY;
  }

  public void setImportContainerListCY(List<OpusImportContainer> importContainerListCY) {
    this.importContainerListCY = importContainerListCY;
  }

  public List<OpusExporterContainer> getExportContainerListCFS() {
    return exportContainerListCFS;
  }

  public void setExportContainerListCFS(List<OpusExporterContainer> exportContainerListCFS) {
    this.exportContainerListCFS = exportContainerListCFS;
  }

  public List<OpusImportContainer> getImportContainerListCFS() {
    return importContainerListCFS;
  }

  public void setImportContainerListCFS(List<OpusImportContainer> importContainerListCFS) {
    this.importContainerListCFS = importContainerListCFS;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }



}
