package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIWriteResponseExportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// ;//AZHA0000001,
	private String yardPosition;// 22G0,
	private String yardBayCode;// 9000,
	private String manualPlanIndicator;// null,

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getYardPosition() {
		return yardPosition;
	}

	public void setYardPosition(String yardPosition) {
		this.yardPosition = yardPosition;
	}

	public String getYardBayCode() {
		return yardBayCode;
	}

	public void setYardBayCode(String yardBayCode) {
		this.yardBayCode = yardBayCode;
	}

	public String getManualPlanIndicator() {
		return manualPlanIndicator;
	}

	public void setManualPlanIndicator(String manualPlanIndicator) {
		this.manualPlanIndicator = manualPlanIndicator;
	}

	@Override
	public String toString() {
		return "GIWriteResponseExportContainer [containerNo=" + containerNo + ", yardPosition=" + yardPosition
				+ ", yardBayCode=" + yardBayCode + ", manualPlanIndicator=" + manualPlanIndicator + "]";
	}

}
