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
	private String fullOrEmptyCon01; // F or E
	private String fullOrEmptyCon02;
	private String trxStatusCon01;
	private String trxStatusCon02;
	private String rejectRemarksCon01;
	private String rejectRemarksCon02;

	public  Long getOddIdSeq() {
		return oddIdSeq;
	}

	public void setOddIdSeq(Long oddIdSeq) {
		this.oddIdSeq = oddIdSeq;
	}

	public String getFullOrEmptyCon01() {
		return fullOrEmptyCon01;
	}

	public void setFullOrEmptyCon01(String fullOrEmptyCon01) {
		this.fullOrEmptyCon01 = fullOrEmptyCon01;
	}

	public String getFullOrEmptyCon02() {
		return fullOrEmptyCon02;
	}

	public void setFullOrEmptyCon02(String fullOrEmptyCon02) {
		this.fullOrEmptyCon02 = fullOrEmptyCon02;
	}

	public String getTrxStatusCon01() {
		return trxStatusCon01;
	}

	public void setTrxStatusCon01(String trxStatusCon01) {
		this.trxStatusCon01 = trxStatusCon01;
	}

	public String getTrxStatusCon02() {
		return trxStatusCon02;
	}

	public void setTrxStatusCon02(String trxStatusCon02) {
		this.trxStatusCon02 = trxStatusCon02;
	}
	
	public String getRejectRemarksCon01() {
		return rejectRemarksCon01;
	}

	public void setRejectRemarksCon01(String rejectRemarksCon01) {
		this.rejectRemarksCon01 = rejectRemarksCon01;
	}

	public String getRejectRemarksCon02() {
		return rejectRemarksCon02;
	}

	public void setRejectRemarksCon02(String rejectRemarksCon02) {
		this.rejectRemarksCon02 = rejectRemarksCon02;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
