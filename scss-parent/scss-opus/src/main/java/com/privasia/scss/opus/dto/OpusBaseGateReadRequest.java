package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class OpusBaseGateReadRequest extends OpusBase implements Serializable {

	private static final long serialVersionUID = 1L;

	private String containerNo1ExportCY = StringUtils.EMPTY;// AZHA0000001"
	private String containerNo2ExportCY = StringUtils.EMPTY;// CCMO1000031"
	private String containerNo1ImportCY = StringUtils.EMPTY;// QASS1234566"
	private String containerNo2ImportCY = StringUtils.EMPTY;// EPLA0000002"
	private String containerNo1ImportWHODD = StringUtils.EMPTY;// EPLA0000002"
	private String containerNo2ImportWHODD = StringUtils.EMPTY;// EPLA0000002"

	public String getContainerNo1ExportCY() {
		return containerNo1ExportCY;
	}

	public void setContainerNo1ExportCY(String containerNo1ExportCY) {
		this.containerNo1ExportCY = containerNo1ExportCY;
	}

	public String getContainerNo2ExportCY() {
		return containerNo2ExportCY;
	}

	public void setContainerNo2ExportCY(String containerNo2ExportCY) {
		this.containerNo2ExportCY = containerNo2ExportCY;
	}

	public String getContainerNo1ImportCY() {
		return containerNo1ImportCY;
	}

	public void setContainerNo1ImportCY(String containerNo1ImportCY) {
		this.containerNo1ImportCY = containerNo1ImportCY;
	}

	public String getContainerNo2ImportCY() {
		return containerNo2ImportCY;
	}

	public void setContainerNo2ImportCY(String containerNo2ImportCY) {
		this.containerNo2ImportCY = containerNo2ImportCY;
	}

	public String getContainerNo1ImportWHODD() {
		return containerNo1ImportWHODD;
	}

	public void setContainerNo1ImportWHODD(String containerNo1ImportWHODD) {
		this.containerNo1ImportWHODD = containerNo1ImportWHODD;
	}

	public String getContainerNo2ImportWHODD() {
		return containerNo2ImportWHODD;
	}

	public void setContainerNo2ImportWHODD(String containerNo2ImportWHODD) {
		this.containerNo2ImportWHODD = containerNo2ImportWHODD;
	}

}
