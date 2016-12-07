/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.enums.TransactionType;


/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "SCSS_KIOSK_BOOTH_RIGHTS")
@AttributeOverrides({@AttributeOverride(name = "addBy", column = @Column(name = "CREATED_BY")),
    @AttributeOverride(name = "updateBy", column = @Column(name = "UPDATED_BY")),
    @AttributeOverride(name = "dateTimeAdd", column = @Column(name = "DATE_TIME_CREATED")),
    @AttributeOverride(name = "dateTimeUpdate", column = @Column(name = "DATE_TIME_UPDATE"))})
public class KioskBoothRights extends AuditEntity implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  @EmbeddedId
  private KioskBoothRightsPK kioskBoothRightsID;

  @Column(name = "KIOSK_LOCK_STATUS")
  @Type(type = "com.privasia.scss.common.enumusertype.KioskLockStatusEnumUserType")
  private KioskLockStatus kioskLockStatus;

  @Column(name = "CARDNUMBER")
  private Integer cardNumber;

  @Column(name = "CARD_SCAN_TIME")
  private LocalDateTime cardScanTime;

  @Column(name = "KIOSK_SELECT_TIME")
  private LocalDateTime kioskSelectedTime;

  @Column(name = "DISPLAY_SCREEN_ID")
  private Integer displayScreenID;

  @Column(name = "TRANSID")
  private String transactionIDList;

  @Column(name = "DRIVERNAME")
  private String driverName;

  @Column(name = "PMHEAD")
  private String pmHeadNo;

  @Column(name = "TRUCKCO")
  private String truckCompany;

  @Column(name = "DRIVER_IC")
  private String driverIC;

  @Column(name = "PLATE_NO")
  private String plateNo;

  @Column(name = "TRANSACTION_TYPE")
  @Type(type = "com.privasia.scss.common.enumusertype.TransactionTypeEnumUserType")
  private TransactionType transactionType;

  @Column(name = "REVISE_HEAD_NO")
  private String reviseHeadNo;

  @Column(name = "REVISE_HEAD_NO_REMARK")
  private String reviseHeadNoRemarks;

  @Column(name = "RE_TAKE_PHOTO")
  @Type(type = "yes_no")
  private Boolean retakePhoto;

  @Column(name = "TRX_COMPLETE_TIME")
  private LocalDateTime trxCompleteTime;

  @Column(name = "LOCK_USER_ID")
  private String lockUserID;

  @Column(name = "LOCK_USER_NAME")
  private String lockUserName;

  @Column(name = "REFER_REASON_01")
  private String referReason01List;

  @Column(name = "REFER_REASON_02")
  private String referReason02List;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CT1")),
      @AttributeOverride(name = "containerISOCode", column = @Column(name = "CT1ISO")),
      @AttributeOverride(name = "containerLength", column = @Column(name = "CT1_LENGTH")),
      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CT1FE")),
      @AttributeOverride(name = "cancelPickup", column = @Column(name = "CT1CANPICKUP")),
      @AttributeOverride(name = "location", column = @Column(name = "CT1LOC")),
      @AttributeOverride(name = "status", column = @Column(name = "CT1STATUS")),
      @AttributeOverride(name = "others", column = @Column(name = "CT1_OTHERS")),
      @AttributeOverride(name = "rejectRemarks", column = @Column(name = "CT1_REJECT_REMARK")),
      @AttributeOverride(name = "customCheck", column = @Column(name = "CT1_CUSTOM_CHECK")),
      @AttributeOverride(name = "shipper", column = @Column(name = "CT1S")),
      @AttributeOverride(name = "line", column = @Column(name = "CT1L"))})
  private KioskBoothContainerAttribute container01;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CT2")),
      @AttributeOverride(name = "containerISOCode", column = @Column(name = "CT2ISO")),
      @AttributeOverride(name = "containerLength", column = @Column(name = "CT2_LENGTH")),
      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CT2FE")),
      @AttributeOverride(name = "cancelPickup", column = @Column(name = "CT2CANPICKUP")),
      @AttributeOverride(name = "location", column = @Column(name = "CT2LOC")),
      @AttributeOverride(name = "status", column = @Column(name = "CT2STATUS")),
      @AttributeOverride(name = "others", column = @Column(name = "CT2_OTHERS")),
      @AttributeOverride(name = "rejectRemarks", column = @Column(name = "CT2_REJECT_REMARK")),
      @AttributeOverride(name = "customCheck", column = @Column(name = "CT2_CUSTOM_CHECK")),
      @AttributeOverride(name = "shipper", column = @Column(name = "CT2S")),
      @AttributeOverride(name = "line", column = @Column(name = "CT2L"))})
  private KioskBoothContainerAttribute container02;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CT3")),
      @AttributeOverride(name = "containerISOCode", column = @Column(name = "CT3ISO")),
      @AttributeOverride(name = "containerLength", column = @Column(name = "CT3_LENGTH")),
      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CT3FE")),
      @AttributeOverride(name = "cancelPickup", column = @Column(name = "CT3CANPICKUP")),
      @AttributeOverride(name = "location", column = @Column(name = "CT3LOC")),
      @AttributeOverride(name = "status", column = @Column(name = "CT3STATUS")),
      @AttributeOverride(name = "others", column = @Column(name = "CT3_OTHERS")),
      @AttributeOverride(name = "rejectRemarks", column = @Column(name = "CT3_REJECT_REMARK")),
      @AttributeOverride(name = "customCheck", column = @Column(name = "CT3_CUSTOM_CHECK")),
      @AttributeOverride(name = "shipper", column = @Column(name = "CT3S")),
      @AttributeOverride(name = "line", column = @Column(name = "CT3L"))})
  private KioskBoothContainerAttribute container03;

  @Embedded
  @AttributeOverrides({@AttributeOverride(name = "containerNumber", column = @Column(name = "CT4")),
      @AttributeOverride(name = "containerISOCode", column = @Column(name = "CT4ISO")),
      @AttributeOverride(name = "containerLength", column = @Column(name = "CT4_LENGTH")),
      @AttributeOverride(name = "containerFullOrEmpty", column = @Column(name = "CT4FE")),
      @AttributeOverride(name = "cancelPickup", column = @Column(name = "CT4CANPICKUP")),
      @AttributeOverride(name = "location", column = @Column(name = "CT4LOC")),
      @AttributeOverride(name = "status", column = @Column(name = "CT4STATUS")),
      @AttributeOverride(name = "others", column = @Column(name = "CT4_OTHERS")),
      @AttributeOverride(name = "rejectRemarks", column = @Column(name = "CT4_REJECT_REMARK")),
      @AttributeOverride(name = "customCheck", column = @Column(name = "CT4_CUSTOM_CHECK")),
      @AttributeOverride(name = "shipper", column = @Column(name = "CT4S")),
      @AttributeOverride(name = "line", column = @Column(name = "CT4L"))})
  private KioskBoothContainerAttribute container04;

  public KioskBoothRightsPK getKioskBoothRightsID() {
    return kioskBoothRightsID;
  }

  public void setKioskBoothRightsID(KioskBoothRightsPK kioskBoothRightsID) {
    this.kioskBoothRightsID = kioskBoothRightsID;
  }

  public KioskLockStatus getKioskLockStatus() {
    return kioskLockStatus;
  }

  public void setKioskLockStatus(KioskLockStatus kioskLockStatus) {
    this.kioskLockStatus = kioskLockStatus;
  }

  public Integer getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(Integer cardNumber) {
    this.cardNumber = cardNumber;
  }

  public LocalDateTime getCardScanTime() {
    return cardScanTime;
  }

  public void setCardScanTime(LocalDateTime cardScanTime) {
    this.cardScanTime = cardScanTime;
  }

  public LocalDateTime getKioskSelectedTime() {
    return kioskSelectedTime;
  }

  public void setKioskSelectedTime(LocalDateTime kioskSelectedTime) {
    this.kioskSelectedTime = kioskSelectedTime;
  }

  public Integer getDisplayScreenID() {
    return displayScreenID;
  }

  public void setDisplayScreenID(Integer displayScreenID) {
    this.displayScreenID = displayScreenID;
  }

  public String getTransactionIDList() {
    return transactionIDList;
  }

  public void setTransactionIDList(String transactionIDList) {
    this.transactionIDList = transactionIDList;
  }

  public String getDriverName() {
    return driverName;
  }

  public void setDriverName(String driverName) {
    this.driverName = driverName;
  }

  public String getPmHeadNo() {
    return pmHeadNo;
  }

  public void setPmHeadNo(String pmHeadNo) {
    this.pmHeadNo = pmHeadNo;
  }

  public String getTruckCompany() {
    return truckCompany;
  }

  public void setTruckCompany(String truckCompany) {
    this.truckCompany = truckCompany;
  }

  public String getDriverIC() {
    return driverIC;
  }

  public void setDriverIC(String driverIC) {
    this.driverIC = driverIC;
  }

  public String getPlateNo() {
    return plateNo;
  }

  public void setPlateNo(String plateNo) {
    this.plateNo = plateNo;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  public String getReviseHeadNo() {
    return reviseHeadNo;
  }

  public void setReviseHeadNo(String reviseHeadNo) {
    this.reviseHeadNo = reviseHeadNo;
  }

  public String getReviseHeadNoRemarks() {
    return reviseHeadNoRemarks;
  }

  public void setReviseHeadNoRemarks(String reviseHeadNoRemarks) {
    this.reviseHeadNoRemarks = reviseHeadNoRemarks;
  }

  public Boolean isRetakePhoto() {
    return retakePhoto;
  }

  public void setRetakePhoto(Boolean retakePhoto) {
    this.retakePhoto = retakePhoto;
  }

  public LocalDateTime getTrxCompleteTime() {
    return trxCompleteTime;
  }

  public void setTrxCompleteTime(LocalDateTime trxCompleteTime) {
    this.trxCompleteTime = trxCompleteTime;
  }

  public String getLockUserID() {
    return lockUserID;
  }

  public void setLockUserID(String lockUserID) {
    this.lockUserID = lockUserID;
  }

  public String getLockUserName() {
    return lockUserName;
  }

  public void setLockUserName(String lockUserName) {
    this.lockUserName = lockUserName;
  }

  public String getReferReason01List() {
    return referReason01List;
  }

  public void setReferReason01List(String referReason01List) {
    this.referReason01List = referReason01List;
  }

  public String getReferReason02List() {
    return referReason02List;
  }

  public void setReferReason02List(String referReason02List) {
    this.referReason02List = referReason02List;
  }

  public KioskBoothContainerAttribute getContainer01() {
    return container01;
  }

  public void setContainer01(KioskBoothContainerAttribute container01) {
    this.container01 = container01;
  }

  public KioskBoothContainerAttribute getContainer02() {
    return container02;
  }

  public void setContainer02(KioskBoothContainerAttribute container02) {
    this.container02 = container02;
  }

  public KioskBoothContainerAttribute getContainer03() {
    return container03;
  }

  public void setContainer03(KioskBoothContainerAttribute container03) {
    this.container03 = container03;
  }

  public KioskBoothContainerAttribute getContainer04() {
    return container04;
  }

  public void setContainer04(KioskBoothContainerAttribute container04) {
    this.container04 = container04;
  }



}
