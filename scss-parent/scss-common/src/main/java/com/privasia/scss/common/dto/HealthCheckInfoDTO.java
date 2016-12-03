package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class HealthCheckInfoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  private String healthCheckSeq = "0";
  private String kioskID = "0";
  private String boothID = "0";
  private String creationTime = StringUtils.EMPTY;
  private String cardReaderStatus = StringUtils.EMPTY;
  private String pcStatus = StringUtils.EMPTY;
  private String intercomStatus = StringUtils.EMPTY;
  private String printerStatus = StringUtils.EMPTY;
  private String paperStatus = StringUtils.EMPTY;
  private String lcdStatus = StringUtils.EMPTY;
  private String cameraStatus = StringUtils.EMPTY;
  private String webServiceStatus = StringUtils.EMPTY;
  private String laneNo = StringUtils.EMPTY;
  private String notificationStatus = "N";

  public String getHealthCheckSeq() {
    return healthCheckSeq;
  }

  public void setHealthCheckSeq(String healthCheckSeq) {
    this.healthCheckSeq = healthCheckSeq;
  }

  public String getKioskID() {
    return kioskID;
  }

  public void setKioskID(String kioskID) {
    this.kioskID = kioskID;
  }

  public String getBoothID() {
    return boothID;
  }

  public void setBoothID(String boothID) {
    this.boothID = boothID;
  }

  public String getCreationTime() {
    return creationTime;
  }

  public void setCreationTime(String creationTime) {
    this.creationTime = creationTime;
  }

  public String getCardReaderStatus() {
    return cardReaderStatus;
  }

  public void setCardReaderStatus(String cardReaderStatus) {
    this.cardReaderStatus = cardReaderStatus;
  }

  public String getPcStatus() {
    return pcStatus;
  }

  public void setPcStatus(String pcStatus) {
    this.pcStatus = pcStatus;
  }

  public String getIntercomStatus() {
    return intercomStatus;
  }

  public void setIntercomStatus(String intercomStatus) {
    this.intercomStatus = intercomStatus;
  }

  public String getPrinterStatus() {
    return printerStatus;
  }

  public void setPrinterStatus(String printerStatus) {
    this.printerStatus = printerStatus;
  }

  public String getPaperStatus() {
    return paperStatus;
  }

  public void setPaperStatus(String paperStatus) {
    this.paperStatus = paperStatus;
  }

  public String getLcdStatus() {
    return lcdStatus;
  }

  public void setLcdStatus(String lcdStatus) {
    this.lcdStatus = lcdStatus;
  }

  public String getCameraStatus() {
    return cameraStatus;
  }

  public void setCameraStatus(String cameraStatus) {
    this.cameraStatus = cameraStatus;
  }

  public String getWebServiceStatus() {
    return webServiceStatus;
  }

  public void setWebServiceStatus(String webServiceStatus) {
    this.webServiceStatus = webServiceStatus;
  }

  public String getLaneNo() {
    return laneNo;
  }

  public void setLaneNo(String laneNo) {
    this.laneNo = laneNo;
  }

  public String getNotificationStatus() {
    return notificationStatus;
  }

  public void setNotificationStatus(String notificationStatus) {
    this.notificationStatus = notificationStatus;
  }



}
