package com.privasia.scss.opus.dto;

import java.io.Serializable;

public class OpusErrorListItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// NH161219003,
	private String containerHoldType;
	private int errorCode;// 1,
	private String errorDescription;// There does no Truck In plan for
									// container,
	private int warningCode;// ,
	private String warningDescription;//

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	public int getWarningCode() {
		return warningCode;
	}

	public void setWarningCode(int warningCode) {
		this.warningCode = warningCode;
	}

	public String getWarningDescription() {
		return warningDescription;
	}

	public void setWarningDescription(String warningDescription) {
		this.warningDescription = warningDescription;
	}

	public String getContainerHoldType() {
		return containerHoldType;
	}

	public void setContainerHoldType(String containerHoldType) {
		this.containerHoldType = containerHoldType;
	}

	@Override
	public String toString() {
		return "OpusErrorListItem [containerNo=" + containerNo + ", errorCode=" + errorCode + ", errorDescription="
				+ errorDescription + ", warningCode=" + warningCode + ", warningDescription=" + warningDescription
				+ ", containerHoldType=" + containerHoldType + "]";
	}

}
