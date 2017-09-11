package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.DateUtil;

public class GateOutReponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userID;
	private String laneNo;// LNO01,
	private String haulageCode;// HAUCD,
	private String truckHeadNo;// TRUCK,
	private String truckPlateNo;// null,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateOUTDateTime;// 20161130112233,

	private List<ImportContainer> importContainers = new ArrayList<ImportContainer>();

	private List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();

	private List<WHoddDTO> whODDContainers = new ArrayList<WHoddDTO>();

	// for gate in write
	private String callCardNo;// 20161130112233,

	private String transactionType;

	private GateOutMessage message;

	private String clerkName;// - string

	private String gateInLaneNo;// -string

	// MM-dd-yyyy HH:mm
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateInDateTime;// -string
	
	private String gateInStatus;
	
	private String tosIndicator;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

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

	public LocalDateTime getGateOUTDateTime() {
		return gateOUTDateTime;
	}

	public void setGateOUTDateTime(LocalDateTime gateOUTDateTime) {
		this.gateOUTDateTime = gateOUTDateTime;
	}

	public List<ImportContainer> getImportContainers() {
		return importContainers;
	}

	public void setImportContainers(List<ImportContainer> importContainers) {
		this.importContainers = importContainers;
	}

	public List<ExportContainer> getExportContainers() {
		return exportContainers;
	}

	public void setExportContainers(List<ExportContainer> exportContainers) {
		this.exportContainers = exportContainers;
	}

	public String getCallCardNo() {
		return callCardNo;
	}

	public void setCallCardNo(String callCardNo) {
		this.callCardNo = callCardNo;
	}

	public List<WHoddDTO> getWhODDContainers() {
		return whODDContainers;
	}

	public void setWhODDContainers(List<WHoddDTO> whODDContainers) {
		this.whODDContainers = whODDContainers;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public GateOutMessage getMessage() {
		return message;
	}

	public void setMessage(GateOutMessage message) {
		this.message = message;
	}

	public String getClerkName() {
		return clerkName;
	}

	public void setClerkName(String clerkName) {
		this.clerkName = clerkName;
	}

	public String getGateInLaneNo() {
		return gateInLaneNo;
	}

	public void setGateInLaneNo(String gateInLaneNo) {
		this.gateInLaneNo = gateInLaneNo;
	}

	public LocalDateTime getGateInDateTime() {
		return gateInDateTime;
	}

	public void setGateInDateTime(LocalDateTime gateInDateTime) {
		this.gateInDateTime = gateInDateTime;
	}

	public String getGateInStatus() {
		return gateInStatus;
	}

	public void setGateInStatus(String gateInStatus) {
		this.gateInStatus = gateInStatus;
	}

	public String getTosIndicator() {
		return tosIndicator;
	}

	public void setTosIndicator(String tosIndicator) {
		this.tosIndicator = tosIndicator;
	}
	
	
	
	

}
