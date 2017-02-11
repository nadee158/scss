package com.privasia.scss.opus.dto;

import java.io.Serializable;

public class OpusGateInWriteRequest extends OpusBaseGateWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private double truckWeight;// "4000"
  private String trailerNo;// "1122"
  private double trailerWeight;// "4000"
  private String gateINDateTime;// "20161219100000"


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


  public String getGateINDateTime() {
    return gateINDateTime;
  }


  public void setGateINDateTime(String gateINDateTime) {
    this.gateINDateTime = gateINDateTime;
  }


  public static OpusGateInWriteRequest constructObjectWithTestValues() {
    OpusGateInWriteRequest giw01Request = new OpusGateInWriteRequest();
    giw01Request.setExportContainerListCFS(null);
    giw01Request.setExportContainerListCY(OpusExporterContainer.constructGIW01RequestTestList());
    giw01Request.setGateINDateTime("20161219100000");
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



  @Override
  public String toString() {
    return "OpusGateInWriteRequest [truckWeight=" + truckWeight + ", trailerNo=" + trailerNo + ", trailerWeight="
        + trailerWeight + ", gateINDateTime=" + gateINDateTime + ", getUserID()=" + getUserID() + ", getLaneNo()="
        + getLaneNo() + ", getHaulageCode()=" + getHaulageCode() + ", getTruckHeadNo()=" + getTruckHeadNo()
        + ", getTruckPlateNo()=" + getTruckPlateNo() + ", getExportContainerListCY()=" + getExportContainerListCY()
        + ", getImportContainerListCY()=" + getImportContainerListCY() + ", getExportContainerListCFS()="
        + getExportContainerListCFS() + ", getImportContainerListCFS()=" + getImportContainerListCFS() + "]";
  }



}
