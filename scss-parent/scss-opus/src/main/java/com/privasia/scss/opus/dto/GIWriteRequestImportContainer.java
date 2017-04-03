package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIWriteRequestImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// QASS1234566,
	private String positionOnTruck;// F/A,
	private String containerFullOrEmpty;// F,

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	public String getPositionOnTruck() {
		return positionOnTruck;
	}

	public void setPositionOnTruck(String positionOnTruck) {
		this.positionOnTruck = positionOnTruck;
	}

	@Override
	public String toString() {
		return "GIWriteRequestImportContainer [containerNo=" + containerNo + ", positionOnTruck=" + positionOnTruck
				+ ", containerFullOrEmpty=" + containerFullOrEmpty + "]";
	}

}
