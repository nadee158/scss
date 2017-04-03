package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OpusGateInReadRequest extends OpusBaseGateReadRequest implements Serializable {

  private static final long serialVersionUID = 1L;


  private String gateINDateTime =  StringUtils.EMPTY;// 20161130112233"
 

  public String getGateINDateTime() {
    return gateINDateTime;
  }

  public void setGateINDateTime(String gateINDateTime) {
    this.gateINDateTime = gateINDateTime;
  }

  
  @Override
  public String toString() {
    return "OpusGateInReadRequest [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode=" + getHaulageCode() + ", truckHeadNo="
        + getTruckHeadNo() + ", gateINDateTime=" + gateINDateTime + ", containerNo1ExportCY=" + getContainerNo1ExportCY()
        + ", containerNo2ExportCY=" + getContainerNo2ExportCY() + ", containerNo1ImportCY=" + getContainerNo1ImportCY()
        + ", containerNo2ImportCY=" + getContainerNo2ImportCY() + "]";
  }

}
