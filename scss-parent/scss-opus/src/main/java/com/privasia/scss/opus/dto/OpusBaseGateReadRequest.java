package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OpusBaseGateReadRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  private String userID = StringUtils.EMPTY;// USER01"
  private String laneNo = StringUtils.EMPTY;// LNO01"
  private String haulageCode = StringUtils.EMPTY;// HAUCD"
  private String truckHeadNo = StringUtils.EMPTY;// TRUCK"
  private String containerNo1ExportCY = StringUtils.EMPTY;// AZHA0000001"
  private String containerNo2ExportCY = StringUtils.EMPTY;// CCMO1000031"
  private String containerNo1ImportCY = StringUtils.EMPTY;// QASS1234566"
  private String containerNo2ImportCY = StringUtils.EMPTY;// EPLA0000002"
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


}
