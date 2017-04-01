package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GOReadResponseExportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// QASS1234566,
	private String containerFullOrEmpty;// F,
	private String rtgExecustionStatus;
	private String rtgExecustionDateTime;
	
	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	public String getRtgExecustionStatus() {
		return rtgExecustionStatus;
	}

	public void setRtgExecustionStatus(String rtgExecustionStatus) {
		this.rtgExecustionStatus = rtgExecustionStatus;
	}

	public String getRtgExecustionDateTime() {
		return rtgExecustionDateTime;
	}

	public void setRtgExecustionDateTime(String rtgExecustionDateTime) {
		this.rtgExecustionDateTime = rtgExecustionDateTime;
	}

	@Override
	public String toString() {
		return "GOReadResponseExportContainer [containerNo=" + containerNo + ", containerFullOrEmpty="
				+ containerFullOrEmpty + ", rtgExecustionStatus=" + rtgExecustionStatus + ", rtgExecustionDateTime="
				+ rtgExecustionDateTime + "]";
	}

}
