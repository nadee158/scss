package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GateOutReponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String laneNo;// LNO01,
  private String haulageCode;// HAUCD,
  private String truckHeadNo;// TRUCK,
  private String truckPlateNo;// null,
  private String gateOUTDateTime;// 20161130112233,

  private List<ImportContainer> importContainers = new ArrayList<ImportContainer>();

  private List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();

  // for gate in write
  private String callCardNo;// 20161130112233,

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

  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
  }

  public List<ImportContainer> getImportContainers() {
    return importContainers;
  }

  public void setImportContainers(List<ImportContainer> importContainers) {
    this.importContainers = importContainers;
  }

  public List<ExportContainer> getExportContainers() {
    return exportContainers;
  }

  public void setExportContainers(List<ExportContainer> exportContainers) {
    this.exportContainers = exportContainers;
  }

  public String getCallCardNo() {
    return callCardNo;
  }

  public void setCallCardNo(String callCardNo) {
    this.callCardNo = callCardNo;
  }



}
