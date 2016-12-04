package com.privasia.scss.common.dto;

import java.io.Serializable;


public class HealthCheckInfoDTO2 implements Serializable {

  private static final long serialVersionUID = 1L;

  
	private Long healthCheckSeq;

	private Long kioskClientID;

	private String cardReaderStatus;

	private String pcStatus;

	private String intercomStatus;

	private String printerStatus;

	private String paperStatus;

	private String lcdStatus;

	private String cameraStatus;

	private String webServiceStatus;

	private Long boothClientID;

	private String laneNumber;

	private Boolean notificationStatus;

	public Long getHealthCheckSeq() {
		return healthCheckSeq;
	}

	public void setHealthCheckSeq(Long healthCheckSeq) {
		this.healthCheckSeq = healthCheckSeq;
	}

	public String getPcStatus() {
		return pcStatus;
	}

	public void setPcStatus(String pcStatus) {
		this.pcStatus = pcStatus;
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

	public String getWebServiceStatus() {
		return webServiceStatus;
	}

	public void setWebServiceStatus(String webServiceStatus) {
		this.webServiceStatus = webServiceStatus;
	}

	public String getLaneNumber() {
		return laneNumber;
	}

	public void setLaneNumber(String laneNumber) {
		this.laneNumber = laneNumber;
	}

	public Boolean getNotificationStatus() {
		return notificationStatus;
	}

	public void setNotificationStatus(Boolean notificationStatus) {
		this.notificationStatus = notificationStatus;
	}
	
	public Long getKioskClientID() {
		return kioskClientID;
	}

	public void setKioskClientID(Long kioskClientID) {
		this.kioskClientID = kioskClientID;
	}

	public Long getBoothClientID() {
		return boothClientID;
	}

	public void setBoothClientID(Long boothClientID) {
		this.boothClientID = boothClientID;
	}
	
	public String getCardReaderStatus() {
		return cardReaderStatus;
	}

	public void setCardReaderStatus(String cardReaderStatus) {
		this.cardReaderStatus = cardReaderStatus;
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

	public String getCameraStatus() {
		return cameraStatus;
	}

	public void setCameraStatus(String cameraStatus) {
		this.cameraStatus = cameraStatus;
	}

	public HealthCheckInfoDTO2() {
		super();
	} 
	
	
}
