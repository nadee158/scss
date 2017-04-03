package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GOReadResponseImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// QASS1234566,
	private String containerFullOrEmpty;// F,
	private String containerSeal1_SL;
	private String containerSeal1_NO;
	private String containerSeal2_SL;
	private String containerSeal2_NO;
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

	public String getContainerSeal1_SL() {
		return containerSeal1_SL;
	}

	public void setContainerSeal1_SL(String containerSeal1_SL) {
		this.containerSeal1_SL = containerSeal1_SL;
	}

	public String getContainerSeal1_NO() {
		return containerSeal1_NO;
	}

	public void setContainerSeal1_NO(String containerSeal1_NO) {
		this.containerSeal1_NO = containerSeal1_NO;
	}

	public String getContainerSeal2_SL() {
		return containerSeal2_SL;
	}

	public void setContainerSeal2_SL(String containerSeal2_SL) {
		this.containerSeal2_SL = containerSeal2_SL;
	}

	public String getContainerSeal2_NO() {
		return containerSeal2_NO;
	}

	public void setContainerSeal2_NO(String containerSeal2_NO) {
		this.containerSeal2_NO = containerSeal2_NO;
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
		return "GOReadResponseImportContainer [containerNo=" + containerNo + ", containerFullOrEmpty="
				+ containerFullOrEmpty + ", containerSeal1_SL=" + containerSeal1_SL + ", containerSeal1_NO="
				+ containerSeal1_NO + ", containerSeal2_SL=" + containerSeal2_SL + ", rtgExecustionStatus="
				+ rtgExecustionStatus + ", rtgExecustionDateTime=" + rtgExecustionDateTime + ", containerSeal2_NO="
				+ containerSeal2_NO + "]";
	}

}
