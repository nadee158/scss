package com.privasia.scss.opus.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GOWriteRequestImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo = StringUtils.EMPTY;// QASS1234566,
	private String containerSeal1_SL = StringUtils.EMPTY;
	private String containerSeal1_NO = StringUtils.EMPTY;
	private String containerSeal2_SL = StringUtils.EMPTY;
	private String containerSeal2_NO = StringUtils.EMPTY;

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}
	
	public String getContainerSeal1_SL() {
		return containerSeal1_SL;
	}

	public void setContainerSeal1_SL(String containerSeal1_SL) {
		this.containerSeal1_SL = containerSeal1_SL;
	}

	public String getContainerSeal1_NO() {
		return containerSeal1_NO;
	}

	public void setContainerSeal1_NO(String containerSeal1_NO) {
		this.containerSeal1_NO = containerSeal1_NO;
	}

	public String getContainerSeal2_SL() {
		return containerSeal2_SL;
	}

	public void setContainerSeal2_SL(String containerSeal2_SL) {
		this.containerSeal2_SL = containerSeal2_SL;
	}

	public String getContainerSeal2_NO() {
		return containerSeal2_NO;
	}

	public void setContainerSeal2_NO(String containerSeal2_NO) {
		this.containerSeal2_NO = containerSeal2_NO;
	}

	@Override
	public String toString() {
		return "GOWriteRequestImportContainer [containerNo=" + containerNo +  
				", containerSeal1_SL=" + containerSeal1_SL + ", containerSeal1_NO="
				+ containerSeal1_NO + ", containerSeal2_SL=" + containerSeal2_SL + ", containerSeal2_NO="
				+ containerSeal2_NO + "]";
	}

}
