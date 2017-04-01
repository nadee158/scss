package com.privasia.scss.opus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpusGateOutWriteResponse extends OpusBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gateINDateTime;// 20161130112233,
	private String truckPlateNo;
	private List<GOWriteRequestExportContainer> exportContainerListCY;
	private List<GOWriteRequestImportContainer> importContainerListCY;

	public List<GOWriteRequestExportContainer> getExportContainerListCY() {
		return exportContainerListCY;
	}

	public void setExportContainerListCY(List<GOWriteRequestExportContainer> exportContainerListCY) {
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GOWriteRequestImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GOWriteRequestImportContainer> importContainerListCY) {
		this.importContainerListCY = importContainerListCY;
	}

	public String getGateINDateTime() {
		return gateINDateTime;
	}

	public void setGateINDateTime(String gateINDateTime) {
		this.gateINDateTime = gateINDateTime;
	}

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

	@Override
	public String toString() {
		return "OpusGateOutWriteResponse [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", truckPlateNo=" + truckPlateNo
				+ ", gateINDateTime=" + getGateINDateTime() + ", exportContainerListCY=" + exportContainerListCY
				+ ", importContainerListCY=" + importContainerListCY + ", errorList=" + getErrorList() + "]";
	}

}
