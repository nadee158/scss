package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

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

	private Long gateOutBooth;

	@NotNull(message = "gateOUTDateTime is required!")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateOUTDateTime;// -string

	private boolean cancelPickUP = false;

	@NotNull(message = "impExpFlag is required!") // check if an allowed value
													// in enum
	private String impExpFlag;

	private byte[] transactionZipFile;
	
	private String reviseHeadNoRemarks = StringUtils.EMPTY;

	private String reviseHeadNo = StringUtils.EMPTY;

	private String tosIndicator = StringUtils.EMPTY;
	
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

	public String getImpExpFlag() {
		return impExpFlag;
	}

	public void setImpExpFlag(String impExpFlag) {
		this.impExpFlag = impExpFlag;
	}

	public Long getGateOutBooth() {
		return gateOutBooth;
	}

	public void setGateOutBooth(Long gateOutBooth) {
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
	
	public String getReviseHeadNoRemarks() {
		return reviseHeadNoRemarks;
	}

	public void setReviseHeadNoRemarks(String reviseHeadNoRemarks) {
		this.reviseHeadNoRemarks = reviseHeadNoRemarks;
	}

	public String getReviseHeadNo() {
		return reviseHeadNo;
	}

	public void setReviseHeadNo(String reviseHeadNo) {
		this.reviseHeadNo = reviseHeadNo;
	}
	
	public String getTosIndicator() {
		return tosIndicator;
	}

	public void setTosIndicator(String tosIndicator) {
		this.tosIndicator = tosIndicator;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
