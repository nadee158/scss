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
public class HPABBookingDetailDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long hpatBookingDetailID;

	private String bookingType;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime closingTime;

	private String containerISO;

	private String containerLength;

	private String containerNumber;

	private String containerSize;

	private String containerType;

	private String cosmosStatus;

	private String expSealNo01;

	private String expSealNo02;

	private String impGatePassNumber;

	private String oddLocation;

	private String oddPickOrDrop;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = CommonUtil.GLOBAL_DATE_PATTERN)
	private LocalDateTime yardOpeningTime;

	private HPABBookingDTO hpatBooking;

	private String expBookingNo;
	
	private SolasInfo solas;

	public Long getHpatBookingDetailID() {
		return hpatBookingDetailID;
	}

	public void setHpatBookingDetailID(Long hpatBookingDetailID) {
		this.hpatBookingDetailID = hpatBookingDetailID;
	}

	public String getBookingType() {
		return bookingType;
	}

	public void setBookingType(String bookingType) {
		this.bookingType = bookingType;
	}

	public LocalDateTime getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(LocalDateTime closingTime) {
		this.closingTime = closingTime;
	}

	public String getContainerISO() {
		return containerISO;
	}

	public void setContainerISO(String containerISO) {
		this.containerISO = containerISO;
	}

	public String getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(String containerLength) {
		this.containerLength = containerLength;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getCosmosStatus() {
		return cosmosStatus;
	}

	public void setCosmosStatus(String cosmosStatus) {
		this.cosmosStatus = cosmosStatus;
	}

	public String getExpSealNo01() {
		return expSealNo01;
	}

	public void setExpSealNo01(String expSealNo01) {
		this.expSealNo01 = expSealNo01;
	}

	public String getExpSealNo02() {
		return expSealNo02;
	}

	public void setExpSealNo02(String expSealNo02) {
		this.expSealNo02 = expSealNo02;
	}

	public String getImpGatePassNumber() {
		return impGatePassNumber;
	}

	public void setImpGatePassNumber(String impGatePassNumber) {
		this.impGatePassNumber = impGatePassNumber;
	}

	public String getOddLocation() {
		return oddLocation;
	}

	public void setOddLocation(String oddLocation) {
		this.oddLocation = oddLocation;
	}

	public String getOddPickOrDrop() {
		return oddPickOrDrop;
	}

	public void setOddPickOrDrop(String oddPickOrDrop) {
		this.oddPickOrDrop = oddPickOrDrop;
	}

	public LocalDateTime getYardOpeningTime() {
		return yardOpeningTime;
	}

	public void setYardOpeningTime(LocalDateTime yardOpeningTime) {
		this.yardOpeningTime = yardOpeningTime;
	}

	public HPABBookingDTO getHpatBooking() {
		return hpatBooking;
	}

	public void setHpatBooking(HPABBookingDTO hpatBooking) {
		this.hpatBooking = hpatBooking;
	}

	public String getExpBookingNo() {
		return expBookingNo;
	}

	public void setExpBookingNo(String expBookingNo) {
		this.expBookingNo = expBookingNo;
	}

	public SolasInfo getSolas() {
		return solas;
	}

	public void setSolas(SolasInfo solas) {
		this.solas = solas;
	}
	
	

}
