package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CustomsDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CustomsExportInfo exportContainer01Info;
	private CustomsExportInfo exportContainer02Info;
	private CustomsGatePassInfo importContainer01Info;
	private CustomsGatePassInfo importContainer02Info;
	private CustomsODDInfo importODDInfo;
	private CustomsODDInfo exportODDInfo;
	private Long gateOutClientId;
	private String transactionType;
	private String customUpdateDateTime;
	
	public Optional<CustomsExportInfo> getExportContainer01Info() {
		return Optional.ofNullable(exportContainer01Info);
	}

	public void setExportContainer01Info(CustomsExportInfo exportContainer01Info) {
		this.exportContainer01Info = exportContainer01Info;
	}

	public Optional<CustomsExportInfo> getExportContainer02Info() {
		return Optional.ofNullable(exportContainer02Info);
	}

	public void setExportContainer02Info(CustomsExportInfo exportContainer02Info) {
		this.exportContainer02Info = exportContainer02Info;
	}

	public Optional<CustomsGatePassInfo> getImportContainer01Info() {
		return Optional.ofNullable(importContainer01Info);
	}

	public void setImportContainer01Info(CustomsGatePassInfo importContainer01Info) {
		this.importContainer01Info = importContainer01Info;
	}

	public Optional<CustomsGatePassInfo> getImportContainer02Info() {
		return Optional.ofNullable(importContainer02Info);
	}

	public void setImportContainer02Info(CustomsGatePassInfo importContainer02Info) {
		this.importContainer02Info = importContainer02Info;
	}

	public Optional<CustomsODDInfo> getImportODDInfo() {
		return Optional.ofNullable(importODDInfo);
	}

	public void setImportODDInfo(CustomsODDInfo importODDInfo) {
		this.importODDInfo = importODDInfo;
	}

	public Optional<CustomsODDInfo> getExportODDInfo() {
		return Optional.ofNullable(exportODDInfo);
	}

	public void setExportODDInfo(CustomsODDInfo exportODDInfo) {
		this.exportODDInfo = exportODDInfo;
	}

	public Long getGateOutClientId() {
		return gateOutClientId;
	}

	public void setGateOutClientId(Long gateOutClientId) {
		this.gateOutClientId = gateOutClientId;
	}
	
	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public String getCustomUpdateDateTime() {
		return customUpdateDateTime;
	}

	public void setCustomUpdateDateTime(String customUpdateDateTime) {
		this.customUpdateDateTime = customUpdateDateTime;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
