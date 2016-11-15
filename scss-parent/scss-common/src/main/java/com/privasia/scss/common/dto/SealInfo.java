package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class SealInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private String sealOrigin = StringUtils.EMPTY;
	private String sealType = StringUtils.EMPTY;
	private String sealNo = StringUtils.EMPTY;

	public String getSealOrigin() {
		return sealOrigin;
	}

	public void setSealOrigin(String sealOrigin) {
		this.sealOrigin = sealOrigin;
	}

	public String getSealType() {
		return sealType;
	}

	public void setSealType(String sealType) {
		this.sealType = sealType;
	}

	public String getSealNo() {
		return sealNo;
	}

	public void setSealNo(String sealNo) {
		this.sealNo = sealNo;
	}

}
