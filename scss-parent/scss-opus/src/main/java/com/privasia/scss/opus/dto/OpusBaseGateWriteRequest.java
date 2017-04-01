package com.privasia.scss.opus.dto;

import java.io.Serializable;

public class OpusBaseGateWriteRequest extends OpusBase implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String truckPlateNo;

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

}
