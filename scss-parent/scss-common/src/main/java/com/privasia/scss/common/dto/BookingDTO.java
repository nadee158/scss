package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.util.DateUtil;

public class BookingDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String bookingId;
	private String truckHeadNo;
	private String truckPlateNo;
	private String gatePass1;
	private String gatePass2;
	private String container1;
	private String container2;
	private String length1;
	private String length2;
	private String bookingStatus; // EARLY, ON TIME, LATE
	private String bookingStartTime; // DD/MM/YYYY HH24:MI:SS
	private String bookingEndTime; // DD/MM/YYYY HH24:MI:SS

	private String location;
	private String bookingType;// "CY" or "ODDPICKUP" OR "ODDDROP"

	public BookingDTO() {

	}

	public BookingDTO(HpatDto hpat) {

		this.setBookingId(hpat.getBookingId());

		if (StringUtils.isNotBlank(hpat.getExpContainer01())) {
			this.setContainer1(hpat.getExpContainer01());
		} else {
			this.setContainer1("");
		}

		if (StringUtils.isNotBlank(hpat.getExpContainer02())) {
			this.setContainer2(hpat.getExpContainer02());
		} else {
			this.setContainer2("");
		}

		if (StringUtils.isNotBlank(hpat.getImpGatePass01())) {
			this.setGatePass1(hpat.getImpGatePass01());
			this.setContainer1(hpat.getImpContainer01());
			this.setLength1(hpat.getContainer01Size());
		} else {
			this.setGatePass1("");
		}

		if (StringUtils.isNotBlank(hpat.getImpGatePass02())) {
			this.setGatePass2(hpat.getImpGatePass02());
			this.setContainer2(hpat.getImpContainer02());
			this.setLength2(hpat.getContainer02Size());
		} else {
			this.setGatePass2("");
		}

		if (StringUtils.equals(HpabReferStatus.ACTIVE.getValue(), hpat.getStatus())) {
			this.setBookingStatus("ON TIME");
		} else {
			this.setBookingStatus(hpat.getStatus());
		}

		// if (StringUtils.isNotBlank(hpatForm.getImpContSize1())) {
		// this.setLength1(hpatForm.getImpContSize1());
		// } else {
		// this.setLength1("");
		// }
		//
		// if (StringUtils.isNotBlank(hpatForm.getImpContSize2())) {
		// this.setLength2(hpatForm.getImpContSize2());
		// } else {
		// this.setLength2("");
		// }

		this.setBookingType("CY");
		this.setBookingStartTime(hpat.getApptStart());
		this.setBookingEndTime(hpat.getApptEnd());

		this.setTruckHeadNo(hpat.getPmNo());

	}

	public BookingDTO(HDBSBkgDetailGridDTO gridDtoItem) {
		
		System.out.println("gridDtoItem.getHdbsBKGDetailID() "+gridDtoItem.getHdbsBKGDetailID());
		
		this.setBookingId(gridDtoItem.getHdbsBKGDetailID());

		if (StringUtils.isNotBlank(gridDtoItem.getContainerNo())) {
			this.setContainer1(gridDtoItem.getContainerNo());
		} else {
			this.setContainer1("");
		}

		if (StringUtils.equals(HpabReferStatus.ACTIVE.getValue(), gridDtoItem.getStatus())) {
			this.setBookingStatus("ON TIME");
		} else {
			this.setBookingStatus(gridDtoItem.getStatus());
		}

		if (StringUtils.isNotBlank(gridDtoItem.getContainerSize())) {
			this.setLength1(gridDtoItem.getContainerSize());
		} else {
			this.setLength1("");
		}

		if (StringUtils.isNotBlank(gridDtoItem.gethDBSBkgMasterDepotCode())) {
			this.setLocation(gridDtoItem.gethDBSBkgMasterDepotCode());
		} else {
			this.setLocation("");
		}

		if (StringUtils.isNotBlank(gridDtoItem.getHdbsBkgType())) {
			this.setBookingType("ODD" + gridDtoItem.getHdbsBkgType());
		}

		this.setBookingStartTime(DateUtil.getFormatteDateTime(gridDtoItem.getApptDateTimeFrom()));
		this.setBookingEndTime(DateUtil.getFormatteDateTime(gridDtoItem.getApptDateTimeToActual()));

		this.setTruckHeadNo(gridDtoItem.getPmHeadNo());
		this.setTruckPlateNo(gridDtoItem.getPlateNo());
	}

	public String getBookingId() {
		return bookingId;
	}

	public void setBookingId(String bookingId) {
		this.bookingId = bookingId;
	}

	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}

	public String getGatePass1() {
		return gatePass1;
	}

	public void setGatePass1(String gatePass1) {
		this.gatePass1 = gatePass1;
	}

	public String getGatePass2() {
		return gatePass2;
	}

	public void setGatePass2(String gatePass2) {
		this.gatePass2 = gatePass2;
	}

	public String getContainer1() {
		return container1;
	}

	public void setContainer1(String container1) {
		this.container1 = container1;
	}

	public String getContainer2() {
		return container2;
	}

	public void setContainer2(String container2) {
		this.container2 = container2;
	}

	public String getLength1() {
		return length1;
	}

	public void setLength1(String length1) {
		this.length1 = length1;
	}

	public String getLength2() {
		return length2;
	}

	public void setLength2(String length2) {
		this.length2 = length2;
	}

	public String getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(String bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public String getBookingStartTime() {
		return bookingStartTime;
	}

	public void setBookingStartTime(String bookingStartTime) {
		this.bookingStartTime = bookingStartTime;
	}

	public String getBookingEndTime() {
		return bookingEndTime;
	}

	public void setBookingEndTime(String bookingEndTime) {
		this.bookingEndTime = bookingEndTime;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

}
