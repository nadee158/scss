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
        + ", containerNo2ImportCY=" + getContainerNo2ImportCY() + ", containerNo1ExportCFS=" + getContainerNo1ExportCFS()
        + ", containerNo2ExportCFS=" + getContainerNo2ExportCFS() + ", containerNo1ImportCFS=" + getContainerNo1ImportCFS()
        + ", containerNo2ImportCFS=" + getContainerNo2ImportCFS() + "]";
  }


  // "userID":"USER01"
  // ,"laneNo":"LNO01"
  // ,"haulageCode":"HAUCD"
  // ,"truckHeadNo":"TRUCK"
  // ,"gateINDateTime":"20161130112233"
  // ,"containerNo1ExportCY":"AZHA0000001"
  // ,"containerNo2ExportCY":"CCMO1000031"
  // ,"containerNo1ImportCY":"QASS1234566"
  // ,"containerNo2ImportCY":"EPLA0000002"
  // ,"containerNo1ExportCFS":""
  // ,"containerNo2ExportCFS":""
  // ,"containerNo1ImportCFS":""
  // ,"containerNo2ImportCFS":""

  public static OpusGateInReadRequest constructObjectWithTestValues() {
    OpusGateInReadRequest gir01Request = new OpusGateInReadRequest();
    gir01Request.setContainerNo1ExportCFS("");
    gir01Request.setContainerNo1ExportCY("AZHA0000001");
    gir01Request.setContainerNo1ImportCFS("");
    gir01Request.setContainerNo1ImportCY("QASS1234566");
    gir01Request.setContainerNo2ExportCY("CCMO1000031");
    gir01Request.setContainerNo2ExportCFS("");
    gir01Request.setContainerNo2ImportCFS("");
    gir01Request.setContainerNo2ImportCY("EPLA0000002");
    gir01Request.setGateINDateTime("20161130112233l");
    gir01Request.setHaulageCode("HAUCD");
    gir01Request.setLaneNo("LNO01");
    gir01Request.setTruckHeadNo("TRUCK");
    gir01Request.setUserID("USER01");
    return gir01Request;
  }



}
