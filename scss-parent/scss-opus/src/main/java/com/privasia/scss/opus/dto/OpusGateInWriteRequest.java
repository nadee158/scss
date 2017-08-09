package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
		if(exportContainerListCY == null){
			exportContainerListCY = new ArrayList<GIWriteRequestExportContainer>();
		}
		this.exportContainerListCY = exportContainerListCY;
	}

	public List<GIWriteRequestImportContainer> getImportContainerListCY() {
		return importContainerListCY;
	}

	public void setImportContainerListCY(List<GIWriteRequestImportContainer> importContainerListCY) {
		if(importContainerListCY == null){
			importContainerListCY = new ArrayList<GIWriteRequestImportContainer>();
		}
		this.importContainerListCY = importContainerListCY;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
