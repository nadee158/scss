package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class GIW01Request implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userID;// "PRABU"
  private String laneNo;// "GATE00"
  private String haulageCode;// "HAN"
  private String truckHeadNo;// "NTK193"
  private String truckPlateNo;// "60P1-2933"
  private double truckWeight;// "4000"
  private String trailerNo;// "1122"
  private double trailerWeight;// "4000"
  private long gateINDateTime;// "20161219100000"
  private List<OpusExporterContainer> exportContainerListCY;//
  private List<OpusImportContainer> importContainerListCY;//
  private List<OpusExporterContainer> exportContainerListCFS;// []
  private List<OpusImportContainer> importContainerListCFS;// []

  public static GIW01Request constructObjectWithTestValues() {
    GIW01Request giw01Request = new GIW01Request();
    giw01Request.setExportContainerListCFS(null);
    giw01Request.setExportContainerListCY(OpusExporterContainer.constructGIW01RequestTestList());
    giw01Request.setGateINDateTime(20161219100000l);
    giw01Request.setHaulageCode("HAN");
    giw01Request.setImportContainerListCFS(null);
    giw01Request.setImportContainerListCY(null);
    giw01Request.setLaneNo("GATE00");
    giw01Request.setTrailerNo("1122");
    giw01Request.setTrailerWeight(4000);
    giw01Request.setTruckHeadNo("NTK193");
    giw01Request.setTruckPlateNo("60P1-2933");
    giw01Request.setTruckWeight(4000);
    giw01Request.setUserID("PRABU");
    return giw01Request;
  }

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

  public double getTruckWeight() {
    return truckWeight;
  }

  public void setTruckWeight(double truckWeight) {
    this.truckWeight = truckWeight;
  }

  public String getTrailerNo() {
    return trailerNo;
  }

  public void setTrailerNo(String trailerNo) {
    this.trailerNo = trailerNo;
  }

  public double getTrailerWeight() {
    return trailerWeight;
  }

  public void setTrailerWeight(double trailerWeight) {
    this.trailerWeight = trailerWeight;
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

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "GIW01Request [userID=" + userID + ", laneNo=" + laneNo + ", haulageCode=" + haulageCode + ", truckHeadNo="
        + truckHeadNo + ", truckPlateNo=" + truckPlateNo + ", truckWeight=" + truckWeight + ", trailerNo=" + trailerNo
        + ", trailerWeight=" + trailerWeight + ", gateINDateTime=" + gateINDateTime + ", exportContainerListCY="
        + exportContainerListCY + ", importContainerListCY=" + importContainerListCY + ", exportContainerListCFS="
        + exportContainerListCFS + ", importContainerListCFS=" + importContainerListCFS + "]";
  }



}
