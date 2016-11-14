/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * @author Janaka
 *
 */
public class ClientInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private long clientIdSeq;
	private String webIPAddress;
	private String clientDescription;
	private String clientStaus;
	private String clientType;
	private String unitNo;
	private String csmControl;
	private String cosmosPortNumber;
	private String sortSeq;
	private String cameraServerIPAddress;
	private String cameraServerPort;
	private String displayScreenId;
	private String kioskLockStatus;
	private String laneNO;
	private String ftpIP;
	private String ftpPort;
	private String ftpProtocal;
	private String ftpUserName;
	private String ftpPassword; 
	private String ftpDirectory;
	private String withCameraImage;
	private GateOutMessage messageStatus;
	
	public long getClientIdSeq() {
		return clientIdSeq;
	}
	public void setClientIdSeq(long clientIdSeq) {
		this.clientIdSeq = clientIdSeq;
	}
	public String getWebIPAddress() {
		return webIPAddress;
	}
	public void setWebIPAddress(String webIPAddress) {
		this.webIPAddress = webIPAddress;
	}
	public String getClientDescription() {
		return clientDescription;
	}
	public void setClientDescription(String clientDescription) {
		this.clientDescription = clientDescription;
	}
	public String getClientStaus() {
		return clientStaus;
	}
	public void setClientStaus(String clientStaus) {
		this.clientStaus = clientStaus;
	}
	public String getClientType() {
		return clientType;
	}
	public void setClientType(String clientType) {
		this.clientType = clientType;
	}
	public String getUnitNo() {
		return unitNo;
	}
	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}
	public String getCsmControl() {
		return csmControl;
	}
	public void setCsmControl(String csmControl) {
		this.csmControl = csmControl;
	}
	public String getCosmosPortNumber() {
		return cosmosPortNumber;
	}
	public void setCosmosPortNumber(String cosmosPortNumber) {
		this.cosmosPortNumber = cosmosPortNumber;
	}
	public String getSortSeq() {
		return sortSeq;
	}
	public void setSortSeq(String sortSeq) {
		this.sortSeq = sortSeq;
	}
	public String getCameraServerIPAddress() {
		return cameraServerIPAddress;
	}
	public void setCameraServerIPAddress(String cameraServerIPAddress) {
		this.cameraServerIPAddress = cameraServerIPAddress;
	}
	public String getCameraServerPort() {
		return cameraServerPort;
	}
	public void setCameraServerPort(String cameraServerPort) {
		this.cameraServerPort = cameraServerPort;
	}
	public String getDisplayScreenId() {
		return displayScreenId;
	}
	public void setDisplayScreenId(String displayScreenId) {
		this.displayScreenId = displayScreenId;
	}
	public String getKioskLockStatus() {
		return kioskLockStatus;
	}
	public void setKioskLockStatus(String kioskLockStatus) {
		this.kioskLockStatus = kioskLockStatus;
	}
	public GateOutMessage getMessageStatus() {
		return messageStatus;
	}
	public void setMessageStatus(GateOutMessage messageStatus) {
		this.messageStatus = messageStatus;
	}
	public String getLaneNO() {
		return laneNO;
	}
	public void setLaneNO(String laneNO) {
		this.laneNO = laneNO;
	}
	public String getFtpIP() {
		return ftpIP;
	}
	public void setFtpIP(String ftpIP) {
		this.ftpIP = ftpIP;
	}
	public String getFtpPort() {
		return ftpPort;
	}
	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}
	public String getFtpProtocal() {
		return ftpProtocal;
	}
	public void setFtpProtocal(String ftpProtocal) {
		this.ftpProtocal = ftpProtocal;
	}
	public String getFtpUserName() {
		return ftpUserName;
	}
	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	public String getFtpDirectory() {
		return ftpDirectory;
	}
	public void setFtpDirectory(String ftpDirectory) {
		this.ftpDirectory = ftpDirectory;
	}
	
	public String getWithCameraImage() {
		return withCameraImage;
	}
	public void setWithCameraImage(String withCameraImage) {
		this.withCameraImage = withCameraImage;
	}
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}
	
	

}
