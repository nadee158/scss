/**
 * 
 */
package com.privasia.scss.kioskbooth.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.core.model.KioskBoothRights;
import com.privasia.scss.core.model.KioskBoothRightsPK;


/**
 * @author Janaka
 *
 */
public class KioskBoothRightInfo implements Serializable {

  private static final String TIME_FORMAT = "dd/MM/yyyy hh:ss a";

  private static final long serialVersionUID = 1L;

  private long boothID;

  private long kioskID;

  private String kioskLockStatus;

  private int cardNumber;

  private String cardScanTime;

  private String kioskSelectedTime;

  private int displayScreenID;

  private String transactionIDList;

  private String driverName;

  private String pmHeadNo;

  private String truckCompany;

  private String driverIC;

  private String plateNo;

  private String transactionType;

  private String reviseHeadNo;

  private String reviseHeadNoRemarks;

  private boolean retakePhoto;

  private String trxCompleteTime;

  private String lockUserID;

  private String lockUserName;

  private String referReason01List;

  private String referReason02List;

  private KioskBoothContainerDTO container01;

  private KioskBoothContainerDTO container02;

  private KioskBoothContainerDTO container03;

  private KioskBoothContainerDTO container04;

  public long getBoothID() {
    return boothID;
  }

  public void setBoothID(long boothID) {
    this.boothID = boothID;
  }

  public long getKioskID() {
    return kioskID;
  }

  public void setKioskID(long kioskID) {
    this.kioskID = kioskID;
  }

  public String getKioskLockStatus() {
    return kioskLockStatus;
  }

  public void setKioskLockStatus(String kioskLockStatus) {
    this.kioskLockStatus = kioskLockStatus;
  }

  public int getCardNumber() {
    return cardNumber;
  }

  public void setCardNumber(int cardNumber) {
    this.cardNumber = cardNumber;
  }

  public String getCardScanTime() {
    return cardScanTime;
  }

  public void setCardScanTime(String cardScanTime) {
    this.cardScanTime = cardScanTime;
  }

  public String getKioskSelectedTime() {
    return kioskSelectedTime;
  }

  public void setKioskSelectedTime(String kioskSelectedTime) {
    this.kioskSelectedTime = kioskSelectedTime;
  }

  public int getDisplayScreenID() {
    return displayScreenID;
  }

  public void setDisplayScreenID(int displayScreenID) {
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

  public String getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(String transactionType) {
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

  public boolean isRetakePhoto() {
    return retakePhoto;
  }

  public void setRetakePhoto(boolean retakePhoto) {
    this.retakePhoto = retakePhoto;
  }

  public String getTrxCompleteTime() {
    return trxCompleteTime;
  }

  public void setTrxCompleteTime(String trxCompleteTime) {
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

  public KioskBoothContainerDTO getContainer01() {
    return container01;
  }

  public void setContainer01(KioskBoothContainerDTO container01) {
    this.container01 = container01;
  }

  public KioskBoothContainerDTO getContainer02() {
    return container02;
  }

  public void setContainer02(KioskBoothContainerDTO container02) {
    this.container02 = container02;
  }

  public KioskBoothContainerDTO getContainer03() {
    return container03;
  }

  public void setContainer03(KioskBoothContainerDTO container03) {
    this.container03 = container03;
  }

  public KioskBoothContainerDTO getContainer04() {
    return container04;
  }

  public void setContainer04(KioskBoothContainerDTO container04) {
    this.container04 = container04;
  }

  public KioskBoothRights constructKioskBoothRights(KioskBoothRightsPK kioskBoothRightsID) {
    KioskBoothRights kioskBoothRights = new KioskBoothRights();
    kioskBoothRights.setCardNumber(cardNumber);
    kioskBoothRights.setCardScanTime(convertToLocalDate(cardScanTime));
    kioskBoothRights.setDisplayScreenID(displayScreenID);
    kioskBoothRights.setDriverIC(driverIC);
    kioskBoothRights.setDriverName(driverName);
    kioskBoothRights.setDriverName(driverName);
    kioskBoothRights.setKioskBoothRightsID(kioskBoothRightsID);
    if (!(kioskLockStatus == null)) {
      kioskBoothRights.setKioskLockStatus(KioskLockStatus.fromCode(kioskLockStatus));
    }
    kioskBoothRights.setKioskSelectedTime(convertToLocalDate(kioskSelectedTime));
    kioskBoothRights.setLockUserID(lockUserID);
    kioskBoothRights.setLockUserName(lockUserName);
    kioskBoothRights.setPlateNo(plateNo);
    kioskBoothRights.setPmHeadNo(pmHeadNo);
    kioskBoothRights.setReferReason01List(referReason01List);
    kioskBoothRights.setReferReason02List(referReason02List);
    kioskBoothRights.setRetakePhoto(retakePhoto);
    kioskBoothRights.setReviseHeadNo(reviseHeadNo);
    kioskBoothRights.setReviseHeadNoRemarks(reviseHeadNoRemarks);
    kioskBoothRights.setTransactionIDList(transactionIDList);
    if (!(transactionType == null)) {
      kioskBoothRights.setTransactionType(TransactionType.fromCode(transactionType));
    }
    kioskBoothRights.setTruckCompany(truckCompany);
    kioskBoothRights.setTrxCompleteTime(convertToLocalDate(trxCompleteTime));
    return kioskBoothRights;
  }

  private LocalDateTime convertToLocalDate(String strDate) {
    if (StringUtils.isNotEmpty(strDate)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
      return LocalDateTime.parse(strDate, formatter);
    }
    return null;
  }



}
