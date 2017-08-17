package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomsODDInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long oddIdSeq;
	private ODDContainerDetailsDTO container01;
	private ODDContainerDetailsDTO container02;

	public  Long getOddIdSeq() {
		return oddIdSeq;
	}

	public void setOddIdSeq(Long oddIdSeq) {
		this.oddIdSeq = oddIdSeq;
	}

	public ODDContainerDetailsDTO getContainer01() {
		return container01;
	}

	public void setContainer01(ODDContainerDetailsDTO container01) {
		this.container01 = container01;
	}

	public ODDContainerDetailsDTO getContainer02() {
		return container02;
	}

	public void setContainer02(ODDContainerDetailsDTO container02) {
		this.container02 = container02;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
