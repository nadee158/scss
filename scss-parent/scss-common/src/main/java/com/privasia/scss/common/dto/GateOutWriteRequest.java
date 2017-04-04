package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GateOutWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName = StringUtils.EMPTY;
	private String laneNo = StringUtils.EMPTY;// -long (clientID)
	private String haulageCode = StringUtils.EMPTY;// -long (clientID)
	private String truckHeadNo = StringUtils.EMPTY;// -string
	private String truckPlateNo = StringUtils.EMPTY;// -long (clientID)
	private long gateOutClient;
	private long cardID;
	private long gateOutBooth;
	private String gateOUTDateTime = StringUtils.EMPTY;// -string
	private boolean cancelPickUP = false;
	private List<ExportContainer> exportContainers;
	private List<ImportContainer> importContainers;
	private String impExpFlag;
	private byte[] transactionZipFile;

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

	public long getGateOutClient() {
		return gateOutClient;
	}

	public void setGateOutClient(long gateOutClient) {
		this.gateOutClient = gateOutClient;
	}

	public long getCardID() {
		return cardID;
	}

	public void setCardID(long cardID) {
		this.cardID = cardID;
	}

	public String getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(String gateOUTDateTime) {
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
	
}
