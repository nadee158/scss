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

public class GateOutWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName = StringUtils.EMPTY;
	private String laneNo = StringUtils.EMPTY;// -long (clientID)
	private String haulageCode = StringUtils.EMPTY;// -long (clientID)
	private String truckHeadNo = StringUtils.EMPTY;// -string
	private String truckPlateNo = StringUtils.EMPTY;// -long (clientID)

	@NotNull(message = "gateOutClient is required!")
	private Long gateOutClient;

	@NotNull(message = "cardId is required!")
	private Long cardID;

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

	private List<WHODDDTO> whoddContainers;

	public String getLaneNo() {
		return laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public String getHaulageCode() {
		return haulageCode;
	}

	public void setHaulageCode(String haulageCode) {
		this.haulageCode = haulageCode;
	}

	public String getTruckHeadNo() {
		return truckHeadNo;
	}

	public void setTruckHeadNo(String truckHeadNo) {
		this.truckHeadNo = truckHeadNo;
	}

	public String getTruckPlateNo() {
		return truckPlateNo;
	}

	public void setTruckPlateNo(String truckPlateNo) {
		this.truckPlateNo = truckPlateNo;
	}

	public Long getGateOutClient() {
		return gateOutClient;
	}

	public void setGateOutClient(Long gateOutClient) {
		this.gateOutClient = gateOutClient;
	}

	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public List<WHODDDTO> getWhoddContainers() {
		return whoddContainers;
	}

	public void setWhoddContainers(List<WHODDDTO> whoddContainers) {
		this.whoddContainers = whoddContainers;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
