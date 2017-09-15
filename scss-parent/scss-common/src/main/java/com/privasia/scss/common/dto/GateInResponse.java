package com.privasia.scss.common.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.DateUtil;

public class GateInResponse implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String laneNo;// LNO01,
	private String haulageCode;// HAUCD,
	private String truckHeadNo;// TRUCK,
	private String truckPlateNo;// null,
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DateUtil.GLOBAL_DATE_TIME_PATTERN)
	private LocalDateTime gateINDateTime;// 20161130112233,
	private boolean checkPreArrival = false;// -boolean

	private List<ImportContainer> importContainers = new ArrayList<ImportContainer>();

	private List<ExportContainer> exportContainers = new ArrayList<ExportContainer>();

	private List<WHoddDTO> whoddContainers;

	private Integer expWeightBridge;// -long

	// for gate in write
	private String callCardNo;// 20161130112233,

	private GateOutMessage message;

	private String trailerPlate;

	private Integer trailerWeight;

	private Integer truckWeight;

	private Boolean axleVerified;

	private Boolean pmVerified;

	private String hpabBookingId;

	private String impExpFlagStatus;

	private boolean manualPlanIndicator = false;

	private String manualPlanDescription;
	
	private String tosIndicator;

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

	public LocalDateTime getGateINDateTime() {
		return gateINDateTime;
	}

	public void setGateINDateTime(LocalDateTime gateINDateTime) {
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

	public List<WHoddDTO> getWhoddContainers() {
		return whoddContainers;
	}

	public void setWhoddContainers(List<WHoddDTO> whoddContainers) {
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

	public Boolean getAxleVerified() {
		return axleVerified;
	}

	public void setAxleVerified(Boolean axleVerified) {
		this.axleVerified = axleVerified;
	}

	public Boolean getPmVerified() {
		return pmVerified;
	}

	public void setPmVerified(Boolean pmVerified) {
		this.pmVerified = pmVerified;
	}

	public String getHpabBookingId() {
		return hpabBookingId;
	}

	public void setHpabBookingId(String hpabBookingId) {
		this.hpabBookingId = hpabBookingId;
	}

	public String getImpExpFlagStatus() {
		return impExpFlagStatus;
	}

	public void setImpExpFlagStatus(String impExpFlagStatus) {
		this.impExpFlagStatus = impExpFlagStatus;
	}

	public boolean isManualPlanIndicator() {
		return manualPlanIndicator;
	}

	public void setManualPlanIndicator(boolean manualPlanIndicator) {

		if (!(this.getExportContainers() == null || this.getExportContainers().isEmpty())) {

			this.manualPlanIndicator = this.getExportContainers().stream()
					.filter(expContainer -> expContainer.getManualPlanIndicator()).findAny().isPresent();
			this.setManualPlanDescription(ApplicationConstants.INF0016);

		} else {
			this.manualPlanIndicator = manualPlanIndicator;
		}
	}

	public String getManualPlanDescription() {
		return manualPlanDescription;
	}

	public void setManualPlanDescription(String manualPlanDescription) {
		this.manualPlanDescription = manualPlanDescription;
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

	public Object clone() throws CloneNotSupportedException {
		GateInResponse gateInResponse = (GateInResponse) super.clone();
		if(axleVerified!=null)
		gateInResponse.setAxleVerified(new Boolean(axleVerified.booleanValue()));
		if(pmVerified!=null)
		gateInResponse.setPmVerified(new Boolean(pmVerified.booleanValue()));
		if(exportContainers!=null)
		gateInResponse.setExportContainers(new ArrayList(exportContainers));
		if(importContainers!=null)
		gateInResponse.setImportContainers(new ArrayList(importContainers));
		if(whoddContainers!=null)
		gateInResponse.setWhoddContainers(new ArrayList(whoddContainers));
		gateInResponse.setGateINDateTime(gateINDateTime);
		if(expWeightBridge!=null)
		gateInResponse.setExpWeightBridge(new Integer(expWeightBridge.intValue()));
		gateInResponse.setMessage(message);
		return gateInResponse;
	}

}
