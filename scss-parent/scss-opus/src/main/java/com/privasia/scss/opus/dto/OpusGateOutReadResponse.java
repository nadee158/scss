package com.privasia.scss.opus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpusGateOutReadResponse extends OpusBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String truckPlateNo;// null,
	private String trailerNo;
	private String gateOUTDateTime;// 20161130112233,
	private List<GOReadResponseExportContainer> exportContainerListCY;
	private List<GOReadResponseImportContainer> importContainerListCY;

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

	public String getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(String gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}

	public String getTrailerNo() {
		return trailerNo;
	}

	public void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

	public List<GOReadResponseExportContainer> getExportContainerListCY() {
		return exportContainerListCY;
	}

	public void setExportContainerListCY(List<GOReadResponseExportContainer> exportContainerListCY) {
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GOReadResponseImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GOReadResponseImportContainer> importContainerListCY) {
		this.importContainerListCY = importContainerListCY;
	}

	@Override
	public String toString() {
		return "OpusGateOutReadResponse [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", trailerNo=" + trailerNo
				+ ", truckPlateNo=" + truckPlateNo + ", gateOUTDateTime=" + gateOUTDateTime
				+ ", exportContainerListCY=" + exportContainerListCY + ", importContainerListCY="
				+ importContainerListCY + ", errorList=" + getErrorList() + "]";
	}

}
