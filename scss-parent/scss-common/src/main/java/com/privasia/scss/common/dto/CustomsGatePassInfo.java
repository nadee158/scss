package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomsGatePassInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long gatePassIDSeq;
	private String fullOrEmpty; 
	private String trxStatus;

	public Long getGatePassIDSeq() {
		return gatePassIDSeq;
	}

	public void setGatePassIDSeq(Long gatePassIDSeq) {
		this.gatePassIDSeq = gatePassIDSeq;
	}

	public String getTrxStatus() {
		return trxStatus;
	}

	public void setTrxStatus(String trxStatus) {
		this.trxStatus = trxStatus;
	}

	public String getFullOrEmpty() {
		return fullOrEmpty;
	}

	public void setFullOrEmpty(String fullOrEmpty) {
		this.fullOrEmpty = fullOrEmpty;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
