package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIWriteResponseImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// QASS1234566,
	private String yardPosition;// 10H08A3,
	private String yardBayCode;//

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

	@Override
	public String toString() {
		return "GIWriteResponseImportContainer [containerNo=" + containerNo + ", yardPosition=" + yardPosition
				+ ", yardBayCode=" + yardBayCode + "]";
	}

}
