package com.privasia.scss.opus.dto;

import java.io.Serializable;

public class GIR01Request implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userID;// USER01"
  private String laneNo;// LNO01"
  private String haulageCode;// HAUCD"
  private String truckHeadNo;// TRUCK"
  private long gateINDateTime;// 20161130112233"
  private String containerNo1ExportCY;// AZHA0000001"
  private String containerNo2ExportCY;// CCMO1000031"
  private String containerNo1ImportCY;// QASS1234566"
  private String containerNo2ImportCY;// EPLA0000002"
  private String containerNo1ExportCFS;// "
  private String containerNo2ExportCFS;// "
  private String containerNo1ImportCFS;// "
  private String containerNo2ImportCFS;// "

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

  public long getGateINDateTime() {
    return gateINDateTime;
  }

  public void setGateINDateTime(long gateINDateTime) {
    this.gateINDateTime = gateINDateTime;
  }

  public String getContainerNo1ExportCY() {
    return containerNo1ExportCY;
  }

  public void setContainerNo1ExportCY(String containerNo1ExportCY) {
    this.containerNo1ExportCY = containerNo1ExportCY;
  }

  public String getContainerNo2ExportCY() {
    return containerNo2ExportCY;
  }

  public void setContainerNo2ExportCY(String containerNo2ExportCY) {
    this.containerNo2ExportCY = containerNo2ExportCY;
  }

  public String getContainerNo1ImportCY() {
    return containerNo1ImportCY;
  }

  public void setContainerNo1ImportCY(String containerNo1ImportCY) {
    this.containerNo1ImportCY = containerNo1ImportCY;
  }

  public String getContainerNo2ImportCY() {
    return containerNo2ImportCY;
  }

  public void setContainerNo2ImportCY(String containerNo2ImportCY) {
    this.containerNo2ImportCY = containerNo2ImportCY;
  }

  public String getContainerNo1ExportCFS() {
    return containerNo1ExportCFS;
  }

  public void setContainerNo1ExportCFS(String containerNo1ExportCFS) {
    this.containerNo1ExportCFS = containerNo1ExportCFS;
  }

  public String getContainerNo2ExportCFS() {
    return containerNo2ExportCFS;
  }

  public void setContainerNo2ExportCFS(String containerNo2ExportCFS) {
    this.containerNo2ExportCFS = containerNo2ExportCFS;
  }

  public String getContainerNo1ImportCFS() {
    return containerNo1ImportCFS;
  }

  public void setContainerNo1ImportCFS(String containerNo1ImportCFS) {
    this.containerNo1ImportCFS = containerNo1ImportCFS;
  }

  public String getContainerNo2ImportCFS() {
    return containerNo2ImportCFS;
  }

  public void setContainerNo2ImportCFS(String containerNo2ImportCFS) {
    this.containerNo2ImportCFS = containerNo2ImportCFS;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
  }

  @Override
  public String toString() {
    return "GIR01Request [userID=" + userID + ", laneNo=" + laneNo + ", haulageCode=" + haulageCode + ", truckHeadNo="
        + truckHeadNo + ", gateINDateTime=" + gateINDateTime + ", containerNo1ExportCY=" + containerNo1ExportCY
        + ", containerNo2ExportCY=" + containerNo2ExportCY + ", containerNo1ImportCY=" + containerNo1ImportCY
        + ", containerNo2ImportCY=" + containerNo2ImportCY + ", containerNo1ExportCFS=" + containerNo1ExportCFS
        + ", containerNo2ExportCFS=" + containerNo2ExportCFS + ", containerNo1ImportCFS=" + containerNo1ImportCFS
        + ", containerNo2ImportCFS=" + containerNo2ImportCFS + "]";
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

  public static GIR01Request constructObjectWithTestValues() {
    GIR01Request gir01Request = new GIR01Request();
    gir01Request.setContainerNo1ExportCFS("");
    gir01Request.setContainerNo1ExportCY("AZHA0000001");
    gir01Request.setContainerNo1ImportCFS("");
    gir01Request.setContainerNo1ImportCY("QASS1234566");
    gir01Request.setContainerNo2ExportCY("CCMO1000031");
    gir01Request.setContainerNo2ExportCFS("");
    gir01Request.setContainerNo2ImportCFS("");
    gir01Request.setContainerNo2ImportCY("EPLA0000002");
    gir01Request.setGateINDateTime(20161130112233l);
    gir01Request.setHaulageCode("HAUCD");
    gir01Request.setLaneNo("LNO01");
    gir01Request.setTruckHeadNo("TRUCK");
    gir01Request.setUserID("USER01");
    return gir01Request;
  }



}
