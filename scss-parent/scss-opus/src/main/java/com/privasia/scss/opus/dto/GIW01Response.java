package com.privasia.scss.opus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIW01Response extends BaseResponse {


  /**
   * 
   */
  private static final long serialVersionUID = 1L;
  private String callCardNo;// 99999,
  private String laneNo;// LNO01,
  private String haulageCode;// HAUCD,
  private String truckHeadNo;// TRUCK,
  private String truckPlateNo;// null,
  private long gateINDateTime;// 20161130112233,
  private List<OpusExporterContainer> exportContainerListCY;
  private List<OpusImportContainer> importContainerListCY;
  private List<OpusExporterContainer> exportContainerListCFS;
  private List<OpusImportContainer> importContainerListCFS;



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

  public long getGateINDateTime() {
    return gateINDateTime;
  }

  public void setGateINDateTime(long gateINDateTime) {
    this.gateINDateTime = gateINDateTime;
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

  @Override
  public String toString() {
    return "GIW01Response [callCardNo=" + callCardNo + ", laneNo=" + laneNo + ", haulageCode=" + haulageCode
        + ", truckHeadNo=" + truckHeadNo + ", truckPlateNo=" + truckPlateNo + ", gateINDateTime=" + gateINDateTime
        + ", exportContainerListCY=" + exportContainerListCY + ", importContainerListCY=" + importContainerListCY
        + ", exportContainerListCFS=" + exportContainerListCFS + ", importContainerListCFS=" + importContainerListCFS
        + "]";
  }

  public String getCallCardNo() {
    return callCardNo;
  }

  public void setCallCardNo(String callCardNo) {
    this.callCardNo = callCardNo;
  }



}
