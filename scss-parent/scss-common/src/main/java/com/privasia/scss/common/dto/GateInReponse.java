package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;

public class GateInReponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  private String laneNo;// LNO01,
  private String haulageCode;// HAUCD,
  private String truckHeadNo;// TRUCK,
  private String truckPlateNo;// null,
  private String gateINDateTime;// 20161130112233,

  List<ImportContainer> importContainers;

  List<ExportContainer> exportContainers;

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

  public String getGateINDateTime() {
    return gateINDateTime;
  }

  public void setGateINDateTime(String gateINDateTime) {
    this.gateINDateTime = gateINDateTime;
  }

  public List<ImportContainer> getImportContainers() {
    return importContainers;
  }

  public void setImportContainers(List<ImportContainer> importContainers) {
    this.importContainers = importContainers;
  }



}
