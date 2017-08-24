/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Janaka
 *
 */
public class KioskBoothRightsDTO implements Serializable {

	/**
	   * 
	   */
	private static final long serialVersionUID = 1L;

	private Long boothClientID;

	private Long kioskClientID;

	private String kioskLockStatus = StringUtils.EMPTY;
	
	private String boothUnitNo = StringUtils.EMPTY;

	private Long cardNumber = null;

	private String cardScanTime = StringUtils.EMPTY;

	private String kioskSelectedTime = StringUtils.EMPTY;

	private Integer displayScreenID = null;

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

	public Long getBoothClientID() {
		return boothClientID;
	}

	public void setBoothClientID(Long boothClientID) {
		this.boothClientID = boothClientID;
	}

	public Long getKioskClientID() {
		return kioskClientID;
	}

	public void setKioskClientID(Long kioskClientID) {
		this.kioskClientID = kioskClientID;
	}

	public String getKioskLockStatus() {
		return kioskLockStatus;
	}

	public void setKioskLockStatus(String kioskLockStatus) {
		this.kioskLockStatus = kioskLockStatus;
	}

	public Long getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(Long cardNumber) {
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
	
	public String getBoothUnitNo() {
		return boothUnitNo;
	}

	public void setBoothUnitNo(String boothUnitNo) {
		this.boothUnitNo = boothUnitNo;
	}
}
