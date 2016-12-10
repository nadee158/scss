package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class KioskBoothContainerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String containerNumber = StringUtils.EMPTY;

	private String cancelPickup = StringUtils.EMPTY;

	private String location = StringUtils.EMPTY;

	private String status = StringUtils.EMPTY;

	private String others = StringUtils.EMPTY;

	private String rejectRemarks = StringUtils.EMPTY;

	private String customCheck = StringUtils.EMPTY;

	private String shipper = StringUtils.EMPTY;

	private String line = StringUtils.EMPTY;

	private Integer containerLength = null;

	private String containerISOCode = StringUtils.EMPTY;

	private String containerFullOrEmpty = StringUtils.EMPTY;

	public KioskBoothContainerDTO() {
		super();
	}

	public String getCancelPickup() {
		return cancelPickup;
	}

	public void setCancelPickup(String cancelPickup) {
		this.cancelPickup = cancelPickup;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOthers() {
		return others;
	}

	public void setOthers(String others) {
		this.others = others;
	}

	public String getRejectRemarks() {
		return rejectRemarks;
	}

	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}

	public String getCustomCheck() {
		return customCheck;
	}

	public void setCustomCheck(String customCheck) {
		this.customCheck = customCheck;
	}

	public String getShipper() {
		return shipper;
	}

	public void setShipper(String shipper) {
		this.shipper = shipper;
	}

	public String getLine() {
		return line;
	}

	public void setLine(String line) {
		this.line = line;
	}

	public String getContainerNumber() {
		return containerNumber;
	}

	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	public Integer getContainerLength() {
		return containerLength;
	}

	public void setContainerLength(Integer containerLength) {
		this.containerLength = containerLength;
	}

	public String getContainerISOCode() {
		return containerISOCode;
	}

	public void setContainerISOCode(String containerISOCode) {
		this.containerISOCode = containerISOCode;
	}

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	@Override
	public String toString() {
		return "KioskBoothContainerDTO [cancelPickup=" + cancelPickup + ", location=" + location + ", status=" + status
				+ ", others=" + others + ", rejectRemarks=" + rejectRemarks + ", customCheck=" + customCheck
				+ ", shipper=" + shipper + ", line=" + line + "]";
	}

}
