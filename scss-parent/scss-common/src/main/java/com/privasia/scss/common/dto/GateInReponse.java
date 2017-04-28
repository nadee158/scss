package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.privasia.scss.common.enums.SolasInstructionType;

public class GateInReponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String laneNo;// LNO01,
	private String haulageCode;// HAUCD,
	private String truckHeadNo;// TRUCK,
	private String truckPlateNo;// null,
	private String gateINDateTime;// 20161130112233,
	private boolean checkPreArrival = false;// -boolean

	private List<ImportContainer> importContainers = new ArrayList<ImportContainer>();

	private List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();

	private List<WHODDDTO> whoddContainers;

	private Integer expWeightBridge;// -long

	// for gate in write
	private String callCardNo;// 20161130112233,

	private GateOutMessage message;

	private String solasInstruction = SolasInstructionType.VGM_INSTRUCTION_NO_SOLAS.getValue();

	private String trailerPlate;

	private Integer trailerWeight;
	
	private Integer truckWeight;

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

	public String getGateINDateTime() {
		return gateINDateTime;
	}

	public void setGateINDateTime(String gateINDateTime) {
		this.gateINDateTime = gateINDateTime;
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

	public boolean isCheckPreArrival() {
		return checkPreArrival;
	}

	public void setCheckPreArrival(boolean checkPreArrival) {
		this.checkPreArrival = checkPreArrival;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public GateOutMessage getMessage() {
		return message;
	}

	public void setMessage(GateOutMessage message) {
		this.message = message;
	}

	public Integer getExpWeightBridge() {
		return expWeightBridge;
	}

	public void setExpWeightBridge(Integer expWeightBridge) {
		this.expWeightBridge = expWeightBridge;
	}

	public String getSolasInstruction() {
		return solasInstruction;
	}

	public void setSolasInstruction(String solasInstruction) {
		this.solasInstruction = solasInstruction;
	}

	public List<WHODDDTO> getWhoddContainers() {
		return whoddContainers;
	}

	public void setWhoddContainers(List<WHODDDTO> whoddContainers) {
		this.whoddContainers = whoddContainers;
	}

	public String getTrailerPlate() {
		return trailerPlate;
	}

	public void setTrailerPlate(String trailerPlate) {
		this.trailerPlate = trailerPlate;
	}

	public Integer getTruckWeight() {
		return truckWeight;
	}

	public void setTruckWeight(Integer truckWeight) {
		this.truckWeight = truckWeight;
	}

	public Integer getTrailerWeight() {
		return trailerWeight;
	}

	public void setTrailerWeight(Integer trailerWeight) {
		this.trailerWeight = trailerWeight;
	}

	

}
