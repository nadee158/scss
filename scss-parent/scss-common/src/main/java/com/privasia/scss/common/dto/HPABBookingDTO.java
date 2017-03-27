/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.CommonUtil;

/**
 * @author Janaka
 *
 */
public class HPABBookingDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private String bookingID;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime appointmentStartDate;

	private String buffer;

	private String cardNo;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime hpabCreationDate;

	private String driverName;

	private String driverICNumber;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime hpabLastModifiedDate;

	private String pmNumber;

	private String status;

	private String trailerNo;

	private String trailerType;

	private String haulierCode;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime appointmentEndDate;

	private String pmWeight;

	private String axleWeight;

	private String trailerPlate;

	private Boolean axleVerified;

	private Boolean pmVerified;

	public String getBookingID() {
		return bookingID;
	}

	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}

	public LocalDateTime getAppointmentStartDate() {
		return appointmentStartDate;
	}

	public void setAppointmentStartDate(LocalDateTime appointmentStartDate) {
		this.appointmentStartDate = appointmentStartDate;
	}

	public String getBuffer() {
		return buffer;
	}

	public void setBuffer(String buffer) {
		this.buffer = buffer;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public LocalDateTime getHpabCreationDate() {
		return hpabCreationDate;
	}

	public void setHpabCreationDate(LocalDateTime hpabCreationDate) {
		this.hpabCreationDate = hpabCreationDate;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public String getDriverICNumber() {
		return driverICNumber;
	}

	public void setDriverICNumber(String driverICNumber) {
		this.driverICNumber = driverICNumber;
	}

	public LocalDateTime getHpabLastModifiedDate() {
		return hpabLastModifiedDate;
	}

	public void setHpabLastModifiedDate(LocalDateTime hpabLastModifiedDate) {
		this.hpabLastModifiedDate = hpabLastModifiedDate;
	}

	public String getPmNumber() {
		return pmNumber;
	}

	public void setPmNumber(String pmNumber) {
		this.pmNumber = pmNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTrailerNo() {
		return trailerNo;
	}

	public void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

	public String getTrailerType() {
		return trailerType;
	}

	public void setTrailerType(String trailerType) {
		this.trailerType = trailerType;
	}

	public String getHaulierCode() {
		return haulierCode;
	}

	public void setHaulierCode(String haulierCode) {
		this.haulierCode = haulierCode;
	}

	public LocalDateTime getAppointmentEndDate() {
		return appointmentEndDate;
	}

	public void setAppointmentEndDate(LocalDateTime appointmentEndDate) {
		this.appointmentEndDate = appointmentEndDate;
	}

	public String getPmWeight() {
		return pmWeight;
	}

	public void setPmWeight(String pmWeight) {
		this.pmWeight = pmWeight;
	}

	public String getAxleWeight() {
		return axleWeight;
	}

	public void setAxleWeight(String axleWeight) {
		this.axleWeight = axleWeight;
	}

	public String getTrailerPlate() {
		return trailerPlate;
	}

	public void setTrailerPlate(String trailerPlate) {
		this.trailerPlate = trailerPlate;
	}

	public Boolean getAxleVerified() {
		return axleVerified;
	}

	public void setAxleVerified(Boolean axleVerified) {
		this.axleVerified = axleVerified;
	}

	public Boolean getPmVerified() {
		return pmVerified;
	}

	public void setPmVerified(Boolean pmVerified) {
		this.pmVerified = pmVerified;
	}
	
	

}
