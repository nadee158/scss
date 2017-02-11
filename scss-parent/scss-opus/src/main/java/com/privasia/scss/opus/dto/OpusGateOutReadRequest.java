package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OpusGateOutReadRequest extends OpusBaseGateReadRequest implements Serializable {

  private static final long serialVersionUID = 1L;


  private String gateOUTDateTime = StringUtils.EMPTY;// 20161130112233"

  // "userID":"USER02"
  // ,"laneNo":"LNO001"
  // ,"haulageCode":"HCD001"
  // ,"truckHeadNo":"TRUCK01"
  // ,"containerNo1ExportCY":"AZHA0000001"
  // ,"containerNo2ExportCY":"CCMO1000031"
  // ,"containerNo1ImportCY":"QASS1234566"
  // ,"containerNo2ImportCY":"EPLA0000002"
  // ,"containerNo1ExportCFS":""
  // ,"containerNo2ExportCFS":""
  // ,"containerNo1ImportCFS":""
  // ,"containerNo2ImportCFS":""

  public String getGateOUTDateTime() {
    return gateOUTDateTime;
  }

  public void setGateOUTDateTime(String gateOUTDateTime) {
    this.gateOUTDateTime = gateOUTDateTime;
  }



  public static OpusGateOutReadRequest constructObjectWithTestValues() {
    OpusGateOutReadRequest gir01Request = new OpusGateOutReadRequest();
    gir01Request.setContainerNo1ExportCFS("");
    gir01Request.setContainerNo1ExportCY("AZHA0000001");
    gir01Request.setContainerNo1ImportCFS("");
    gir01Request.setContainerNo1ImportCY("QASS1234566");
    gir01Request.setContainerNo2ExportCY("CCMO1000031");
    gir01Request.setContainerNo2ExportCFS("");
    gir01Request.setContainerNo2ImportCFS("");
    gir01Request.setContainerNo2ImportCY("EPLA0000002");
    gir01Request.setGateOUTDateTime("20161130112233l");
    gir01Request.setHaulageCode("HAUCD");
    gir01Request.setLaneNo("LNO01");
    gir01Request.setTruckHeadNo("TRUCK");
    gir01Request.setUserID("USER01");
    return gir01Request;
  }



}
