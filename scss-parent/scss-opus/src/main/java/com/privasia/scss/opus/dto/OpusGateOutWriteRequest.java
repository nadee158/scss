package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.List;

public class OpusGateOutWriteRequest extends OpusBaseGateWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String gateOUTDateTime;// "20161219100000"
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

	public String getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(String gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}
	
	@Override
	public String toString() {
		return "OpusGateOutWriteRequest [userID=" + getUserID() + ", laneNo=" + getLaneNo() + ", haulageCode="
				+ getHaulageCode() + ", truckHeadNo=" + getTruckHeadNo() + ", truckPlateNo=" + getTruckPlateNo() + ", gateOUTDateTime="
				+ gateOUTDateTime + ", exportContainerListCY=" + exportContainerListCY + ", importContainerListCY=" + importContainerListCY + "]";
	}

}
