package com.privasia.scss.common.dto;

import java.io.Serializable;

import com.privasia.scss.common.enums.ClientType;

public class ClientDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long clientID;

	private String lpsIPAddress;

	private String type;

	private Boolean csmControl;

	private String unitNo;

	private Integer cosmosPortNo;

	private String webIPAddress;

	private String scrIPAddress;

	private Integer readerID;

	private String csmIPAddress;

	private String description;

	private String status;

	private String sortSEQ;

	private String cameraServerIPAddress;

	private Integer cameraServerPortNo;

	private String laneNo;

	private String ftpIPAddress;

	private String ftpPort;

	private String ftpProtocol;

	private String ftpUsername;

	private String ftpPassword;

	private Boolean withCameraImage;

	private String ftpDirectory;

	public ClientDTO(String lpsIPAddress, Enum<ClientType> type, boolean csmControl) {
		super();
		this.lpsIPAddress = lpsIPAddress;
		this.type = ClientType.fromName(type.name()).getValue();
		this.csmControl = csmControl;
	}

	public ClientDTO(String unitNo) {
		super();
		this.unitNo = unitNo;
	}

	public ClientDTO(String unitNo, int cosmosPortNo) {
		super();
		this.unitNo = unitNo;
		this.cosmosPortNo = cosmosPortNo;
	}
	
	public ClientDTO() {
		super();
	}

	public long getClientID() {
		return clientID;
	}

	public void setClientID(long clientID) {
		this.clientID = clientID;
	}

	public String getLpsIPAddress() {
		return lpsIPAddress;
	}

	public void setLpsIPAddress(String lpsIPAddress) {
		this.lpsIPAddress = lpsIPAddress;
	}

	public Boolean isCsmControl() {
		return csmControl;
	}

	public void setCsmControl(Boolean csmControl) {
		this.csmControl = csmControl;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUnitNo() {
		return unitNo;
	}

	public void setUnitNo(String unitNo) {
		this.unitNo = unitNo;
	}

	public int getCosmosPortNo() {
		return cosmosPortNo;
	}

	public void setCosmosPortNo(int cosmosPortNo) {
		this.cosmosPortNo = cosmosPortNo;
	}

	public String getWebIPAddress() {
		return webIPAddress;
	}

	public void setWebIPAddress(String webIPAddress) {
		this.webIPAddress = webIPAddress;
	}

	public String getScrIPAddress() {
		return scrIPAddress;
	}

	public void setScrIPAddress(String scrIPAddress) {
		this.scrIPAddress = scrIPAddress;
	}

	public Integer getReaderID() {
		return readerID;
	}

	public void setReaderID(Integer readerID) {
		this.readerID = readerID;
	}

	public String getCsmIPAddress() {
		return csmIPAddress;
	}

	public void setCsmIPAddress(String csmIPAddress) {
		this.csmIPAddress = csmIPAddress;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSortSEQ() {
		return sortSEQ;
	}

	public void setSortSEQ(String sortSEQ) {
		this.sortSEQ = sortSEQ;
	}

	public String getCameraServerIPAddress() {
		return cameraServerIPAddress;
	}

	public void setCameraServerIPAddress(String cameraServerIPAddress) {
		this.cameraServerIPAddress = cameraServerIPAddress;
	}

	public Integer getCameraServerPortNo() {
		return cameraServerPortNo;
	}

	public void setCameraServerPortNo(Integer cameraServerPortNo) {
		this.cameraServerPortNo = cameraServerPortNo;
	}

	public String getLaneNo() {
		return laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public String getFtpIPAddress() {
		return ftpIPAddress;
	}

	public void setFtpIPAddress(String ftpIPAddress) {
		this.ftpIPAddress = ftpIPAddress;
	}

	public String getFtpPort() {
		return ftpPort;
	}

	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}

	public String getFtpProtocol() {
		return ftpProtocol;
	}

	public void setFtpProtocol(String ftpProtocol) {
		this.ftpProtocol = ftpProtocol;
	}

	public String getFtpUsername() {
		return ftpUsername;
	}

	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public Boolean isWithCameraImage() {
		return withCameraImage;
	}

	public void setWithCameraImage(Boolean withCameraImage) {
		this.withCameraImage = withCameraImage;
	}

	public String getFtpDirectory() {
		return ftpDirectory;
	}

	public void setFtpDirectory(String ftpDirectory) {
		this.ftpDirectory = ftpDirectory;
	}

	public void setCosmosPortNo(Integer cosmosPortNo) {
		this.cosmosPortNo = cosmosPortNo;
	}
	
	

}
