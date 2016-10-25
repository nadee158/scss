/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.Type;

import com.privasia.scss.core.util.constant.ClientType;
import com.privasia.scss.core.util.constant.RecordStatus;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_CLIENT",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"CLI_WEB_IPADDR"}),
        @UniqueConstraint(columnNames = {"CLI_SCR_IPADDR"}), @UniqueConstraint(columnNames = {"CLI_LPS_IPADDR"}),
        @UniqueConstraint(columnNames = {"LANE_NO"}), @UniqueConstraint(columnNames = {"CLI_UNITNO"})})

@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CLI_CREATEDBY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "CLI_UPDATEDBY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "CLI_DATECREATE")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "CLI_DATEUPDATE"))})
public class Client extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_CLIENT")
  @SequenceGenerator(name = "SEQ_SCSS_CLIENT", sequenceName = "CLI_CLIENTID_SEQ")
  @Column(name = "CLI_CLIENTID_SEQ")
  private Long clientID;

  @Column(name = "CLI_WEB_IPADDR", nullable = false)
  private String webIPAddress;

  @Column(name = "CLI_SCR_IPADDR", nullable = false)
  private String scrIPAddress;

  @Column(name = "CLI_READERID", nullable = false)
  private int readerID;

  @Column(name = "CLI_LPS_IPADDR")
  private String lpsIPAddress;

  @Column(name = "CLI_CSM_IPADDR")
  private String csmIPAddress;

  @Column(name = "CLI_DESCRIPTION", nullable = false)
  private String description;

  @Column(name = "CLI_STATUS", nullable = false)
  @Enumerated(EnumType.STRING)
  private RecordStatus status;

  @Column(name = "CLI_TYPE", nullable = false)
  @Enumerated(EnumType.STRING)
  private ClientType type;

  @Column(name = "CLI_UNITNO", nullable = false)
  private String unitNo;

  @Column(name = "CLI_CSM_CTRL")
  @Type(type="yes_no")
  private boolean csmControl;

  @Column(name = "COSMOS_PORT_NO")
  private int cosmosPortNo;

  @Column(name = "SORT_SEQ")
  private String sortSEQ;

  @Column(name = "CAMERA_SERVER_IPADDR")
  private String cameraServerIPAddress;

  @Column(name = "CAMERA_SERVER_PORT")
  private int cameraServerPortNo;

  @Column(name = "LANE_NO")
  private String laneNo;

  @Column(name = "FTP_IP")
  private String ftpIPAddress;

  @Column(name = "FTP_PORT")
  private String ftpPort;

  @Column(name = "FTP_PROTOCAL")
  private String ftpProtocol;

  @Column(name = "FTP_USERNAME")
  private String ftpUsername;

  @Column(name = "FTP_PASSWORD")
  private String ftpPassword;

  @Column(name = "WITH_CMA_IMAGE")
  @Type(type="yes_no")
  private boolean withCameraImage;

  @Column(name = "FTP_DIRECTORY")
  private String ftpDirectory;

  public Long getClientID() {
    return clientID;
  }

  public void setClientID(Long clientID) {
    this.clientID = clientID;
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

  public int getReaderID() {
    return readerID;
  }

  public void setReaderID(int readerID) {
    this.readerID = readerID;
  }

  public String getLpsIPAddress() {
    return lpsIPAddress;
  }

  public void setLpsIPAddress(String lpsIPAddress) {
    this.lpsIPAddress = lpsIPAddress;
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

  public RecordStatus getStatus() {
    return status;
  }

  public void setStatus(RecordStatus status) {
    this.status = status;
  }

  public ClientType getType() {
    return type;
  }

  public void setType(ClientType type) {
    this.type = type;
  }

  public String getUnitNo() {
    return unitNo;
  }

  public void setUnitNo(String unitNo) {
    this.unitNo = unitNo;
  }

  public boolean isCsmControl() {
	return csmControl;
  }

  public void setCsmControl(boolean csmControl) {
	this.csmControl = csmControl;
  }

  public int getCosmosPortNo() {
    return cosmosPortNo;
  }

  public void setCosmosPortNo(int cosmosPortNo) {
    this.cosmosPortNo = cosmosPortNo;
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

  public int getCameraServerPortNo() {
    return cameraServerPortNo;
  }

  public void setCameraServerPortNo(int cameraServerPortNo) {
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

  public boolean isWithCameraImage() {
    return withCameraImage;
  }

  public void setWithCameraImage(boolean withCameraImage) {
    this.withCameraImage = withCameraImage;
  }

  public String getFtpDirectory() {
    return ftpDirectory;
  }

  public void setFtpDirectory(String ftpDirectory) {
    this.ftpDirectory = ftpDirectory;
  }



}
