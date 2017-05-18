package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateOutWriteRequest extends GateWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	@NotNull(message = "gateOutClient is required!")
	private Long gateOutClient;

	private long gateOutBooth;

	@NotNull(message = "gateOUTDateTime is required!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateOUTDateTime;// -string

	private boolean cancelPickUP = false;
	private List<ExportContainer> exportContainers;
	private List<ImportContainer> importContainers;

	@NotNull(message = "impExpFlag is required!") // check if an allowed value
													// in enum
	private String impExpFlag;

	private byte[] transactionZipFile;

	private List<WHoddDTO> whoddContainers;

	
	public Long getGateOutClient() {
		return gateOutClient;
	}

	public void setGateOutClient(Long gateOutClient) {
		this.gateOutClient = gateOutClient;
	}

	public LocalDateTime getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(LocalDateTime gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}

	public List<ExportContainer> getExportContainers() {
		return exportContainers;
	}

	public void setExportContainers(List<ExportContainer> exportContainers) {
		this.exportContainers = exportContainers;
	}

	public List<ImportContainer> getImportContainers() {
		return importContainers;
	}

	public void setImportContainers(List<ImportContainer> importContainers) {
		this.importContainers = importContainers;
	}

	public String getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(String impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public long getGateOutBooth() {
		return gateOutBooth;
	}

	public void setGateOutBooth(long gateOutBooth) {
		this.gateOutBooth = gateOutBooth;
	}

	public boolean isCancelPickUP() {
		return cancelPickUP;
	}

	public void setCancelPickUP(boolean cancelPickUP) {
		this.cancelPickUP = cancelPickUP;
	}

	public byte[] getTransactionZipFile() {
		return transactionZipFile;
	}

	public void setTransactionZipFile(byte[] transactionZipFile) {
		this.transactionZipFile = transactionZipFile;
	}

	public List<WHoddDTO> getWhoddContainers() {
		return whoddContainers;
	}

	public void setWhoddContainers(List<WHoddDTO> whoddContainers) {
		this.whoddContainers = whoddContainers;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
