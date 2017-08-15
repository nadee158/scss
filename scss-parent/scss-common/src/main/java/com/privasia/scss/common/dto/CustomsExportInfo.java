package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomsExportInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long exportIDSeq;
	private String fullOrEmpty; 
	private String trxStatus;
	private String rejectRemarks;

	public Long getExportIDSeq() {
		return exportIDSeq;
	}

	public void setExportIDSeq(Long exportIDSeq) {
		this.exportIDSeq = exportIDSeq;
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
	

	public String getRejectRemarks() {
		return rejectRemarks;
	}

	public void setRejectRemarks(String rejectRemarks) {
		this.rejectRemarks = rejectRemarks;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
