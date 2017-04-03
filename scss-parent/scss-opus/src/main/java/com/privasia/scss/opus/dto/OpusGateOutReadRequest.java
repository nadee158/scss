package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OpusGateOutReadRequest extends OpusBaseGateReadRequest implements Serializable {

  private static final long serialVersionUID = 1L;


  private String gateOUTDateTime = StringUtils.EMPTY;// 20161130112233"


  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
  }
  
  @Override
	public String toString() {
		return "OpusGateOutReadRequest [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", containerNo1ExportCY=" + getContainerNo1ExportCY() + ", containerNo2ExportCY="
				+ getContainerNo2ExportCY() + ", containerNo1ImportCY=" + getContainerNo1ImportCY() + ", containerNo2ImportCY="
				+ getContainerNo2ImportCY()  + ", gateOUTDateTime=" + gateOUTDateTime + "]";
	}


}
