package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class OpusGateInWriteRequest extends OpusBaseGateWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String truckWeight;// "4000"
	private String trailerNo;// "1122"
	private String trailerWeight;// "4000"
	private String gateINDateTime;// "20161219100000"
	private List<GIWriteRequestExportContainer> exportContainerListCY;
	private List<GIWriteRequestImportContainer> importContainerListCY;

	public String getTruckWeight() {
		return truckWeight;
	}

	public void setTruckWeight(String truckWeight) {
		this.truckWeight = truckWeight;
	}

	public String getTrailerNo() {
		return trailerNo;
	}

	public void setTrailerNo(String trailerNo) {
		this.trailerNo = trailerNo;
	}

	public String getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(String trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	public String getGateINDateTime() {
		return gateINDateTime;
	}

	public void setGateINDateTime(String gateINDateTime) {
		this.gateINDateTime = gateINDateTime;
	}

	public List<GIWriteRequestExportContainer> getExportContainerListCY() {
		return exportContainerListCY;
	}

	public void setExportContainerListCY(List<GIWriteRequestExportContainer> exportContainerListCY) {
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GIWriteRequestImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GIWriteRequestImportContainer> importContainerListCY) {
		this.importContainerListCY = importContainerListCY;
	}

	@Override
	public String toString() {
		return "OpusGateInWriteRequest [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", truckHeadNo=" + getTruckHeadNo()
				+ ",truckWeight=" + truckWeight + ", trailerNo=" + trailerNo + ", trailerWeight="
				+ trailerWeight + ", truckPlateNo=" + getTruckPlateNo() + ", gateINDateTime=" + gateINDateTime + ", haulageCode=" + getHaulageCode() 
				+ ", exportContainerListCY=" + exportContainerListCY + ", importContainerListCY=" + importContainerListCY + "]";
	}

}
