package com.privasia.scss.opus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpusGateInReadResponse extends OpusBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String gateInDateTime;// 20161130112233,
	private List<GIReadResponseExporterContainer> exportContainerListCY;
	private List<GIReadResponseImportContainer> importContainerListCY;
	

	public String getGateInDateTime() {
		return gateInDateTime;
	}

	public void setGateInDateTime(String gateInDateTime) {
		this.gateInDateTime = gateInDateTime;
	}

	public List<GIReadResponseExporterContainer> getExportContainerListCY() {
		return exportContainerListCY;
	}

	public void setExportContainerListCY(List<GIReadResponseExporterContainer> exportContainerListCY) {
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GIReadResponseImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GIReadResponseImportContainer> importContainerListCY) {
		this.importContainerListCY = importContainerListCY;
	}

	@Override
	public String toString() {
		return "OpusGateInReadResponse [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", gateInDateTime=" + gateInDateTime
				+ ", exportContainerListCY=" + exportContainerListCY + ", importContainerListCY="
				+ importContainerListCY + ", errorList=" + getErrorList() + "]";
	}

}
