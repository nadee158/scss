/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class HDBSBookingDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String hdbsBkgDetailNo;
	private String contNo;
	private String depotCode;
	private String hdbsBkgType;
	private Double contSize;
	private String apptStartDateFormat;
	private String apptEndDateFormat;
	private String pmHeadNo;
	private String plateNo;
	private String smartCardNo;
	private String arrivalTime;
	private String arrivalTimeStr;
	private String OnTimeFlag = "N";
	private String status;
	private String apptStart;
	private String apptEnd;
	private String typeCode;
	private String size;
	private String hdbsStatus;
	private String hdbsSubmitDateStr;
	private String bookingNo;
	private String action;
	
	private Boolean isAcceptBooking;
	private String showManual;
	private String proceedBookingNo;
	private String rejectBookingNo;
	
	
	
	public HDBSBookingDTO() {
		super();
	}

	
	public String getHdbsBkgDetailNo() {
		return hdbsBkgDetailNo;
	}
	public void setHdbsBkgDetailNo(String hdbsBkgDetailNo) {
		this.hdbsBkgDetailNo = hdbsBkgDetailNo;
	}
	public String getContNo() {
		return contNo;
	}
	public void setContNo(String contNo) {
		this.contNo = contNo;
	}
	public String getDepotCode() {
		return depotCode;
	}
	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}
	public String getHdbsBkgType() {
		return hdbsBkgType;
	}
	public void setHdbsBkgType(String hdbsBkgType) {
		this.hdbsBkgType = hdbsBkgType;
	}
	public Double getContSize() {
		return contSize;
	}
	public void setContSize(Double contSize) {
		this.contSize = contSize;
	}
	public String getApptStartDateFormat() {
		return apptStartDateFormat;
	}
	public void setApptStartDateFormat(String apptStartDateFormat) {
		this.apptStartDateFormat = apptStartDateFormat;
	}
	public String getApptEndDateFormat() {
		return apptEndDateFormat;
	}
	public void setApptEndDateFormat(String apptEndDateFormat) {
		this.apptEndDateFormat = apptEndDateFormat;
	}
	public String getPmHeadNo() {
		return pmHeadNo;
	}
	public void setPmHeadNo(String pmHeadNo) {
		this.pmHeadNo = pmHeadNo;
	}
	public String getPlateNo() {
		return plateNo;
	}
	public void setPlateNo(String plateNo) {
		this.plateNo = plateNo;
	}
	public String getSmartCardNo() {
		return smartCardNo;
	}
	public void setSmartCardNo(String smartCardNo) {
		this.smartCardNo = smartCardNo;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getArrivalTimeStr() {
		return arrivalTimeStr;
	}
	public void setArrivalTimeStr(String arrivalTimeStr) {
		this.arrivalTimeStr = arrivalTimeStr;
	}
	public String getOnTimeFlag() {
		return OnTimeFlag;
	}
	public void setOnTimeFlag(String onTimeFlag) {
		OnTimeFlag = onTimeFlag;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApptStart() {
		return apptStart;
	}
	public void setApptStart(String apptStart) {
		this.apptStart = apptStart;
	}
	public String getApptEnd() {
		return apptEnd;
	}
	public void setApptEnd(String apptEnd) {
		this.apptEnd = apptEnd;
	}
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getHdbsStatus() {
		return hdbsStatus;
	}
	public void setHdbsStatus(String hdbsStatus) {
		this.hdbsStatus = hdbsStatus;
	}
	public String getHdbsSubmitDateStr() {
		return hdbsSubmitDateStr;
	}
	public void setHdbsSubmitDateStr(String hdbsSubmitDateStr) {
		this.hdbsSubmitDateStr = hdbsSubmitDateStr;
	}
	public String getBookingNo() {
		return bookingNo;
	}
	public void setBookingNo(String bookingNo) {
		this.bookingNo = bookingNo;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public Boolean getIsAcceptBooking() {
		return isAcceptBooking;
	}
	public void setIsAcceptBooking(Boolean isAcceptBooking) {
		this.isAcceptBooking = isAcceptBooking;
	}
	public String getShowManual() {
		return showManual;
	}
	public void setShowManual(String showManual) {
		this.showManual = showManual;
	}
	public String getProceedBookingNo() {
		return proceedBookingNo;
	}
	public void setProceedBookingNo(String proceedBookingNo) {
		this.proceedBookingNo = proceedBookingNo;
	}
	public String getRejectBookingNo() {
		return rejectBookingNo;
	}
	public void setRejectBookingNo(String rejectBookingNo) {
		this.rejectBookingNo = rejectBookingNo;
	}
	
	
	
	
}
