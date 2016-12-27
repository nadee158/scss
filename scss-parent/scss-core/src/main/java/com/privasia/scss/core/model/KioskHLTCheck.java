package com.privasia.scss.core.model;

import java.io.Serializable;

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

import org.hibernate.annotations.Type;
import com.privasia.scss.common.enums.KioskHLTCheckStatus;


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
  @SequenceGenerator(name = "SEQ_SCSS_KIOSK_HLT_CHECK", sequenceName = "HEALTH_CHECK_SEQ", allocationSize = 1) 
  @Column(name = "HEALTH_CHECK_SEQ")
  private Long healthCheckSeq;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "KIOSK_ID", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client kiosk;

  @Column(name = "CARD_READER_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus cardReaderStatus;

  @Column(name = "PC_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus pcStatus;

  @Column(name = "INTERCOM_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus intercomStatus;

  @Column(name = "PRINTER_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus printerStatus;

  @Column(name = "PAPER_STATUS")
  private String paperStatus;

  @Column(name = "LCD_STATUS")
  private String lcdStatus;

  @Column(name = "CAMERA_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskHLTCheckStatusEnumUserType")
  private KioskHLTCheckStatus cameraStatus;

  @Column(name = "WEB_SERVICE_STATUS")
  private String webServiceStatus;

  @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REFRESH)
  @JoinColumn(name = "BOOTH_ID", nullable = true, referencedColumnName = "CLI_CLIENTID_SEQ")
  private Client booth;

  @Column(name = "LANE_NUMBER")
  private String laneNumber;

  @Column(name = "NOTIFICATION_STATUS")
  @Type(type = "yes_no")
  private Boolean notificationStatus;

  public KioskHLTCheck() {
    super();
  }

  public Long getHealthCheckSeq() {
    return healthCheckSeq;
  }

  public void setHealthCheckSeq(Long healthCheckSeq) {
    this.healthCheckSeq = healthCheckSeq;
  }

  public KioskHLTCheckStatus getCardReaderStatus() {
    return cardReaderStatus;
  }

  public void setCardReaderStatus(KioskHLTCheckStatus cardReaderStatus) {
    this.cardReaderStatus = cardReaderStatus;
  }

  public KioskHLTCheckStatus getPcStatus() {
    return pcStatus;
  }

  public void setPcStatus(KioskHLTCheckStatus pcStatus) {
    this.pcStatus = pcStatus;
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

  public String getLcdStatus() {
    return lcdStatus;
  }

  public void setLcdStatus(String lcdStatus) {
    this.lcdStatus = lcdStatus;
  }

  public KioskHLTCheckStatus getCameraStatus() {
    return cameraStatus;
  }

  public void setCameraStatus(KioskHLTCheckStatus cameraStatus) {
    this.cameraStatus = cameraStatus;
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

  public Client getKiosk() {
    return kiosk;
  }

  public void setKiosk(Client kiosk) {
    this.kiosk = kiosk;
  }

  public Client getBooth() {
    return booth;
  }

  public void setBooth(Client booth) {
    this.booth = booth;
  }

  

  @Override
  public String toString() {
    return "KioskHLTCheck [healthCheckSeq=" + healthCheckSeq + ", kiosk=" + kiosk + ", cardReaderStatus="
        + cardReaderStatus + ", pcStatus=" + pcStatus + ", intercomStatus=" + intercomStatus + ", printerStatus="
        + printerStatus + ", paperStatus=" + paperStatus + ", lcdStatus=" + lcdStatus + ", cameraStatus=" + cameraStatus
        + ", webServiceStatus=" + webServiceStatus + ", booth=" + booth + ", laneNumber=" + laneNumber
        + ", notificationStatus=" + notificationStatus + "]";
  }

}
