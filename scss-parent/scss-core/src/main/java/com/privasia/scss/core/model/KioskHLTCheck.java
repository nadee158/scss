package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.Type;

import com.privasia.scss.common.dto.HealthCheckInfoDTO;
import com.privasia.scss.common.enums.KioskHLTCheckStatus;
import com.privasia.scss.common.util.CommonUtil;

@Entity
@Table(name = "SCSS_KIOSK_HLT_CHECK")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "CREATION_TIME")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class KioskHLTCheck extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SCSS_KIOSK_HLT_CHECK")
  @SequenceGenerator(name = "SEQ_SCSS_KIOSK_HLT_CHECK", sequenceName = "HEALTH_CHECK_SEQ")
  @Column(name = "HEALTH_CHECK_SEQ")
  private Long healthCheckSeq;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "KIOSK_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client kioskID;

  // OK
  // Card Reader Down
  @Column(name = "CARD_READER_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus cardReaderStatus;

  // OK
  // PC Down
  @Column(name = "PC_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus pCStatus;

  // Intercom Down
  // OK
  @Column(name = "INTERCOM_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus intercomStatus;

  // OK
  // Printer Down
  @Column(name = "PRINTER_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus printerStatus;


  @Column(name = "PAPER_STATUS")
  private String paperStatus;

  @Column(name = "LCD_STATUS")
  private String lCDStatus;

  // OK
  // Camera Down
  @Column(name = "CAMERA_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private String cameraStatus;

  @Column(name = "WEB_SERVICE_STATUS")
  private String webServiceStatus;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "BOOTH_ID", nullable = false, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client boothID;

  @Column(name = "LANE_NUMBER")
  private String laneNumber;

  @Column(name = "NOTIFICATION_STATUS")
  @Type(type = "yes_no")
  private Boolean notificationStatus; // YES/NO

  // t1.KIOSK_ID, t1.PC_STATUS, t1.CREATION_TIME, t1.CARD_READER_STATUS, t1.INTERCOM_STATUS,
  // t1.PRINTER_STATUS, \
  // t1.PAPER_STATUS, t1.LCD_STATUS, t1.CAMERA_STATUS, t1.WEB_SERVICE_STATUS, t1.BOOTH_ID,
  // t1.LANE_NUMBER
  public KioskHLTCheck(Long healthCheckSeq, Client kioskID, KioskHLTCheckStatus pCStatus, LocalDateTime dateTimeAdd,
      KioskHLTCheckStatus cardReaderStatus, KioskHLTCheckStatus intercomStatus, KioskHLTCheckStatus printerStatus,
      String paperStatus, String lCDStatus, String cameraStatus, String webServiceStatus, Client boothID,
      String laneNumber) {
    super();
    this.healthCheckSeq = healthCheckSeq;
    this.kioskID = kioskID;
    this.cardReaderStatus = cardReaderStatus;
    this.pCStatus = pCStatus;
    this.intercomStatus = intercomStatus;
    this.printerStatus = printerStatus;
    this.paperStatus = paperStatus;
    this.lCDStatus = lCDStatus;
    this.cameraStatus = cameraStatus;
    this.webServiceStatus = webServiceStatus;
    this.boothID = boothID;
    this.laneNumber = laneNumber;
  }



  public KioskHLTCheck(Long addBy, Long updateBy, LocalDateTime dateTimeAdd, LocalDateTime dateTimeUpdate, Long version,
      Client kioskID, KioskHLTCheckStatus cardReaderStatus, KioskHLTCheckStatus pCStatus,
      KioskHLTCheckStatus intercomStatus, KioskHLTCheckStatus printerStatus, String paperStatus, String lCDStatus,
      String cameraStatus, String webServiceStatus, Client boothID, String laneNumber, Boolean notificationStatus) {
    super(addBy, updateBy, dateTimeAdd, dateTimeUpdate, version);
    this.kioskID = kioskID;
    this.cardReaderStatus = cardReaderStatus;
    this.pCStatus = pCStatus;
    this.intercomStatus = intercomStatus;
    this.printerStatus = printerStatus;
    this.paperStatus = paperStatus;
    this.lCDStatus = lCDStatus;
    this.cameraStatus = cameraStatus;
    this.webServiceStatus = webServiceStatus;
    this.boothID = boothID;
    this.laneNumber = laneNumber;
    this.notificationStatus = notificationStatus;
  }



  public KioskHLTCheck() {
    super();
  }



  public KioskHLTCheck(HealthCheckInfoDTO healthCheckInfo) {
    if (StringUtils.isNotEmpty(healthCheckInfo.getKioskID())) {
      long kioskId = Long.parseLong(healthCheckInfo.getKioskID());
      Client client = new Client();
      client.setClientID(kioskId);
      this.kioskID = client;
    }
    this.cardReaderStatus = KioskHLTCheckStatus.fromValue(healthCheckInfo.getCardReaderStatus());
    this.pCStatus = KioskHLTCheckStatus.fromValue(healthCheckInfo.getPcStatus());
    this.intercomStatus = KioskHLTCheckStatus.fromValue(healthCheckInfo.getIntercomStatus());
    this.printerStatus = KioskHLTCheckStatus.fromValue(healthCheckInfo.getPrinterStatus());
    this.paperStatus = healthCheckInfo.getPaperStatus();
    this.lCDStatus = healthCheckInfo.getLcdStatus();
    this.cameraStatus = healthCheckInfo.getCameraStatus();
    this.webServiceStatus = healthCheckInfo.getWebServiceStatus();
    if (StringUtils.isNotEmpty(healthCheckInfo.getBoothID())) {
      long boothId = Long.parseLong(healthCheckInfo.getBoothID());
      Client client = new Client();
      client.setClientID(boothId);
      this.boothID = client;
    }
    this.laneNumber = healthCheckInfo.getLaneNo();
    this.notificationStatus = Boolean.valueOf(healthCheckInfo.getNotificationStatus());
  }



  public Long getHealthCheckSeq() {
    return healthCheckSeq;
  }

  public void setHealthCheckSeq(Long healthCheckSeq) {
    this.healthCheckSeq = healthCheckSeq;
  }

  public Client getKioskID() {
    return kioskID;
  }

  public void setKioskID(Client kioskID) {
    this.kioskID = kioskID;
  }

  public KioskHLTCheckStatus getCardReaderStatus() {
    return cardReaderStatus;
  }

  public void setCardReaderStatus(KioskHLTCheckStatus cardReaderStatus) {
    this.cardReaderStatus = cardReaderStatus;
  }

  public KioskHLTCheckStatus getpCStatus() {
    return pCStatus;
  }

  public void setpCStatus(KioskHLTCheckStatus pCStatus) {
    this.pCStatus = pCStatus;
  }

  public KioskHLTCheckStatus getIntercomStatus() {
    return intercomStatus;
  }

  public void setIntercomStatus(KioskHLTCheckStatus intercomStatus) {
    this.intercomStatus = intercomStatus;
  }

  public KioskHLTCheckStatus getPrinterStatus() {
    return printerStatus;
  }

  public void setPrinterStatus(KioskHLTCheckStatus printerStatus) {
    this.printerStatus = printerStatus;
  }

  public String getPaperStatus() {
    return paperStatus;
  }

  public void setPaperStatus(String paperStatus) {
    this.paperStatus = paperStatus;
  }

  public String getlCDStatus() {
    return lCDStatus;
  }

  public void setlCDStatus(String lCDStatus) {
    this.lCDStatus = lCDStatus;
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

  public Client getBoothID() {
    return boothID;
  }

  public void setBoothID(Client boothID) {
    this.boothID = boothID;
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



  public HealthCheckInfoDTO constructDto() {
    HealthCheckInfoDTO dto = new HealthCheckInfoDTO();
    if (!(this.boothID == null)) {
      dto.setBoothID(Long.toString(boothID.getClientID()));
    }
    dto.setCameraStatus(cameraStatus);
    if (!(this.cardReaderStatus == null)) {
      dto.setCardReaderStatus(cardReaderStatus.getValue());
    }
    dto.setCreationTime(CommonUtil.getFormatteDate(this.getDateTimeAdd()));

    if (!(this.healthCheckSeq == null)) {
      dto.setHealthCheckSeq(Long.toString(healthCheckSeq));
    }
    if (!(this.intercomStatus == null)) {
      dto.setIntercomStatus(intercomStatus.getValue());
    }
    if (!(this.kioskID == null)) {
      dto.setKioskID(Long.toString(kioskID.getClientID()));
    }
    dto.setLaneNo(laneNumber);
    dto.setLcdStatus(lCDStatus);

    if (!(this.notificationStatus == null)) {
      dto.setNotificationStatus(Boolean.toString(notificationStatus));
    }
    dto.setPaperStatus(paperStatus);
    if (!(this.pCStatus == null)) {
      dto.setPcStatus(pCStatus.getValue());
    }
    if (!(this.printerStatus == null)) {
      dto.setPrinterStatus(printerStatus.getValue());
    }
    dto.setWebServiceStatus(webServiceStatus);
    return dto;
  }



}
