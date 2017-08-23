package com.privasia.scss.common.dto;

import java.io.Serializable;


public class BoothTransactionInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long exportID01;
	private Long exportID02;
	private Long gatePass01;
	private Long gatePass02;
	private Long oddIdSeq01;
	private Long oddIdSeq02;
	private String transactionType;
	private Long gateoutClientID;

	public Long getExportID01() {
		return exportID01;
	}

	public void setExportID01(Long exportID01) {
		this.exportID01 = exportID01;
	}

	public Long getExportID02() {
		return exportID02;
	}

	public void setExportID02(Long exportID02) {
		this.exportID02 = exportID02;
	}

	public Long getGatePass01() {
		return gatePass01;
	}

	public void setGatePass01(Long gatePass01) {
		this.gatePass01 = gatePass01;
	}

	public Long getGatePass02() {
		return gatePass02;
	}

	public void setGatePass02(Long gatePass02) {
		this.gatePass02 = gatePass02;
	}

	public Long getOddIdSeq01() {
		return oddIdSeq01;
	}

	public void setOddIdSeq01(Long oddIdSeq01) {
		this.oddIdSeq01 = oddIdSeq01;
	}

	public Long getOddIdSeq02() {
		return oddIdSeq02;
	}

	public void setOddIdSeq02(Long oddIdSeq02) {
		this.oddIdSeq02 = oddIdSeq02;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Long getGateoutClientID() {
		return gateoutClientID;
	}

	public void setGateoutClientID(Long gateoutClientID) {
		this.gateoutClientID = gateoutClientID;
	}

}
