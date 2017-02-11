package com.privasia.scss.opus.dto;

import java.io.Serializable;

public class OpusGateOutWriteRequest extends OpusBaseGateWriteRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String gateOUTDateTime;// "20161219100000"



  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
  }

  public static OpusGateOutWriteRequest constructObjectWithTestValues() {
    OpusGateOutWriteRequest giw01Request = new OpusGateOutWriteRequest();
    giw01Request.setExportContainerListCFS(null);
    giw01Request.setExportContainerListCY(OpusExporterContainer.constructGIW01RequestTestList());
    giw01Request.setGateOUTDateTime("20161219100000l");
    giw01Request.setHaulageCode("HAN");
    giw01Request.setImportContainerListCFS(null);
    giw01Request.setImportContainerListCY(null);
    giw01Request.setLaneNo("GATE00");
    giw01Request.setTruckHeadNo("NTK193");
    giw01Request.setTruckPlateNo("60P1-2933");
    giw01Request.setUserID("PRABU");
    return giw01Request;
  }

  @Override
  public String toString() {
    return "OpusGateOutWriteRequest [gateOUTDateTime=" + gateOUTDateTime + ", getUserID()=" + getUserID()
        + ", getLaneNo()=" + getLaneNo() + ", getHaulageCode()=" + getHaulageCode() + ", getTruckHeadNo()="
        + getTruckHeadNo() + ", getTruckPlateNo()=" + getTruckPlateNo() + ", getExportContainerListCY()="
        + getExportContainerListCY() + ", getImportContainerListCY()=" + getImportContainerListCY()
        + ", getExportContainerListCFS()=" + getExportContainerListCFS() + ", getImportContainerListCFS()="
        + getImportContainerListCFS() + "]";
  }



}
