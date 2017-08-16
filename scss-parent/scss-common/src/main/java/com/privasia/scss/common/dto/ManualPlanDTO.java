package com.privasia.scss.common.dto;

import java.io.Serializable;

public class ManualPlanDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long exportID;

	private String yardPosition;

	private String yardBayCode;

	public Long getExportID() {
		return exportID;
	}

	public void setExportID(Long exportID) {
		this.exportID = exportID;
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

}
