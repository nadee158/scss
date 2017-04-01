package com.privasia.scss.opus.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpusGateInWriteResponse extends OpusBaseResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String callCardNo;// 99999,
	private String truckPlateNo;// null,
	private String gateINDateTime;// 20161130112233,
	private List<GIWriteResponseExportContainer> exportContainerListCY;
	private List<GIWriteResponseImportContainer> importContainerListCY;

	public String getCallCardNo() {
		return callCardNo;
	}

	public void setCallCardNo(String callCardNo) {
		this.callCardNo = callCardNo;
	}

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

	public String getGateINDateTime() {
		return gateINDateTime;
	}

	public void setGateINDateTime(String gateINDateTime) {
		this.gateINDateTime = gateINDateTime;
	}

	public List<GIWriteResponseExportContainer> getExportContainerListCY() {
		return exportContainerListCY;
	}

	public void setExportContainerListCY(List<GIWriteResponseExportContainer> exportContainerListCY) {
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GIWriteResponseImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GIWriteResponseImportContainer> importContainerListCY) {
		this.importContainerListCY = importContainerListCY;
	}

	@Override
	public String toString() {
		return "OpusGateInWriteResponse [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", truckPlateNo=" + truckPlateNo
				+ ", callCardNo=" + callCardNo + ", gateINDateTime=" + gateINDateTime + ", exportContainerListCY="
				+ exportContainerListCY + ", importContainerListCY=" + importContainerListCY + ", errorList="
				+ getErrorList() + "]";
	}

}
