package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class GateOutWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String userName = StringUtils.EMPTY;
	private String laneNo = StringUtils.EMPTY;// -long (clientID)
	private String haulageCode = StringUtils.EMPTY;// -long (clientID)
	private String truckHeadNo = StringUtils.EMPTY;// -string
	private String truckPlateNo = StringUtils.EMPTY;// -long (clientID)
	private long gateOutClient = 0l;
	private long cardID = 0l;
	private String gateOUTDateTime = StringUtils.EMPTY;// -string

	private List<ExportContainer> exportContainers;
	private List<ImportContainer> importContainers;

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

	public void initializeWithDefaultValues() {
		this.userName = "USER01";
		this.laneNo = "GATE00";// -long (clientID)
		this.haulageCode = "HAN";// -long (clientID)
		this.truckHeadNo = "HAN001";// -string
		this.truckPlateNo = "60P1-2933";// -long (clientID)
		this.gateOUTDateTime = "04/11/2017 01:22:55 am";// -string
		this.exportContainers = new ArrayList<ExportContainer>();
		this.exportContainers.add((new ExportContainer()).initializeWithDefaultValues("VSLCONT0011"));
		this.exportContainers.add((new ExportContainer()).initializeWithDefaultValues("QASS1234003"));
		this.importContainers = new ArrayList<ImportContainer>();
		this.importContainers.add((new ImportContainer()).initializeWithDefaultValues("ASIA1234562"));
		this.importContainers.add((new ImportContainer()).initializeWithDefaultValues("ASIA1234563"));
	}

}
