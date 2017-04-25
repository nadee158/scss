package com.privasia.scss.common.dto;

import java.io.Serializable;

import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.RecordStatus;

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

  public Integer getCosmosPortNo() {
    return cosmosPortNo;
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

  public ClientDTO initializeWithIdOnly() {
    this.clientID = 101;
    return this;
  }


  public ClientDTO initializeWithDefaultValues() {
    this.clientID = 101;

    this.lpsIPAddress = "172.19.175.155";

    this.type = ClientType.CCC.toString();

    this.csmControl = false;

    this.unitNo = "C5";

    this.cosmosPortNo = 12016;

    this.webIPAddress = "172.18.225.35";

    this.scrIPAddress = "172.18.225.35";

    this.readerID = 1;

    this.csmIPAddress = "172.16.225.74";

    this.description = "CCC: COUNTER 6";

    this.status = RecordStatus.ACTIVE.getValue();

    this.sortSEQ = "3";

    this.cameraServerIPAddress = "10.206.200.11";

    this.cameraServerPortNo = 22491;

    this.laneNo = "2GK3";

    this.ftpIPAddress = "172.21.30.56";

    this.ftpPort = "9022";

    this.ftpProtocol = "SHAREFOLDER";

    this.ftpUsername = "LANE1";

    this.ftpPassword = "lane1";

    this.withCameraImage = true;

    this.ftpDirectory = "//10.206.200.11/LANE3$";
    return this;
  }



}
