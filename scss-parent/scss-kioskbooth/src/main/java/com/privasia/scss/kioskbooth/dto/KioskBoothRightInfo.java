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

  private String boothID = StringUtils.EMPTY;

  private String kioskID = StringUtils.EMPTY;

  private String kioskLockStatus = StringUtils.EMPTY;

  private int cardNumber = 0;

  private String cardScanTime = StringUtils.EMPTY;

  private String kioskSelectedTime = StringUtils.EMPTY;

  private int displayScreenID = 0;

  private String transactionIDList = StringUtils.EMPTY;

  private String driverName = StringUtils.EMPTY;

  private String pmHeadNo = StringUtils.EMPTY;

  private String truckCompany = StringUtils.EMPTY;

  private String driverIC = StringUtils.EMPTY;

  private String plateNo = StringUtils.EMPTY;

  private String transactionType = StringUtils.EMPTY;

  private String reviseHeadNo = StringUtils.EMPTY;

  private String reviseHeadNoRemarks = StringUtils.EMPTY;

  private boolean retakePhoto = false;

  private String trxCompleteTime = StringUtils.EMPTY;

  private String lockUserID = StringUtils.EMPTY;

  private String lockUserName = StringUtils.EMPTY;

  private String referReason01List = StringUtils.EMPTY;

  private String referReason02List = StringUtils.EMPTY;

  private KioskBoothContainerDTO container01 = null;

  private KioskBoothContainerDTO container02 = null;

  private KioskBoothContainerDTO container03 = null;

  private KioskBoothContainerDTO container04 = null;

  public KioskBoothRightInfo() {
    super();
  }

  public KioskBoothRightInfo(KioskBoothRights boothRights) {
    if (!(boothRights == null)) {
      if (!(boothRights.getKioskBoothRightsID() == null)) {
        if (!(boothRights.getKioskBoothRightsID().getBoothID() == null)) {
          this.boothID = Long.toString(boothRights.getKioskBoothRightsID().getBoothID().getClientID());
        }
        if (!(boothRights.getKioskBoothRightsID().getKioskID() == null)) {
          this.kioskID = Long.toString(boothRights.getKioskBoothRightsID().getKioskID().getClientID());
        }
      }

      if (!(boothRights.getKioskLockStatus() == null)) {
        this.kioskLockStatus = boothRights.getKioskLockStatus().getValue();
      }
      this.cardNumber = boothRights.getCardNumber();
      this.cardScanTime = convertLocalDateToString(boothRights.getCardScanTime());
      this.kioskSelectedTime = convertLocalDateToString(boothRights.getKioskSelectedTime());
      this.displayScreenID = boothRights.getDisplayScreenID();
      this.transactionIDList = boothRights.getTransactionIDList();
      this.driverName = boothRights.getDriverName();
      this.pmHeadNo = boothRights.getPmHeadNo();
      this.truckCompany = boothRights.getTruckCompany();
      this.driverIC = boothRights.getDriverIC();
      this.plateNo = boothRights.getPlateNo();
      if (!(boothRights.getKioskLockStatus() == null)) {
        this.transactionType = boothRights.getKioskLockStatus().getValue();
      }
      this.reviseHeadNo = boothRights.getReviseHeadNo();
      this.reviseHeadNoRemarks = boothRights.getReviseHeadNoRemarks();
      this.retakePhoto = boothRights.isRetakePhoto();
      this.trxCompleteTime = convertLocalDateToString(boothRights.getTrxCompleteTime());
      this.lockUserID = boothRights.getLockUserID();
      this.lockUserName = boothRights.getLockUserName();
      this.referReason01List = boothRights.getReferReason01List();
      this.referReason02List = boothRights.getReferReason02List();
      if (!(boothRights.getContainer01() == null)) {
        KioskBoothContainerDTO container01 = new KioskBoothContainerDTO(boothRights.getContainer01());
        this.setContainer01(container01);
      }
      if (!(boothRights.getContainer02() == null)) {
        KioskBoothContainerDTO container02 = new KioskBoothContainerDTO(boothRights.getContainer02());
        this.setContainer01(container02);
      }
      if (!(boothRights.getContainer03() == null)) {
        KioskBoothContainerDTO container03 = new KioskBoothContainerDTO(boothRights.getContainer03());
        this.setContainer01(container03);
      }
      if (!(boothRights.getContainer04() == null)) {
        KioskBoothContainerDTO container04 = new KioskBoothContainerDTO(boothRights.getContainer04());
        this.setContainer01(container04);
      }
    }


  }

  public String getBoothID() {
    return boothID;
  }

  public void setBoothID(String boothID) {
    this.boothID = boothID;
  }

  public String getKioskID() {
    return kioskID;
  }

  public void setKioskID(String kioskID) {
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
    kioskBoothRights.setKioskBoothRightsID(kioskBoothRightsID);
    updateKioskBoothRightsValues(kioskBoothRights);
    return kioskBoothRights;
  }

  private KioskBoothRights updateKioskBoothRightsValues(KioskBoothRights kioskBoothRights) {
    kioskBoothRights.setCardNumber(cardNumber);
    kioskBoothRights.setCardScanTime(convertToLocalDate(cardScanTime));
    kioskBoothRights.setDisplayScreenID(displayScreenID);
    kioskBoothRights.setDriverIC(driverIC);
    kioskBoothRights.setDriverName(driverName);
    kioskBoothRights.setDriverName(driverName);
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

  private String convertLocalDateToString(LocalDateTime dateTime) {
    if (!(dateTime == null)) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern(TIME_FORMAT);
      return formatter.format(dateTime);
    }
    return null;
  }

  public KioskBoothRights updateKioskBoothRights(KioskBoothRights kiosk) {
    return updateKioskBoothRights(kiosk);
  }

  @Override
  public String toString() {
    return "KioskBoothRightInfo [boothID=" + boothID + ", kioskID=" + kioskID + ", kioskLockStatus=" + kioskLockStatus
        + ", cardNumber=" + cardNumber + ", cardScanTime=" + cardScanTime + ", kioskSelectedTime=" + kioskSelectedTime
        + ", displayScreenID=" + displayScreenID + ", transactionIDList=" + transactionIDList + ", driverName="
        + driverName + ", pmHeadNo=" + pmHeadNo + ", truckCompany=" + truckCompany + ", driverIC=" + driverIC
        + ", plateNo=" + plateNo + ", transactionType=" + transactionType + ", reviseHeadNo=" + reviseHeadNo
        + ", reviseHeadNoRemarks=" + reviseHeadNoRemarks + ", retakePhoto=" + retakePhoto + ", trxCompleteTime="
        + trxCompleteTime + ", lockUserID=" + lockUserID + ", lockUserName=" + lockUserName + ", referReason01List="
        + referReason01List + ", referReason02List=" + referReason02List + ", container01=" + container01
        + ", container02=" + container02 + ", container03=" + container03 + ", container04=" + container04 + "]";
  }



}
