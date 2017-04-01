package com.privasia.scss.opus.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIReadResponseImportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// QASS1234566,
	private String impOrderNo;// RSD67981,
	private String impOrderType;// FOT/BKG/CNA
	private String impOrderStatus;// RGS/ACT/EXE
	private String containerInOrOut;// OUT,
	private String containerShippingLine;// CMA,
	private String containerShippingAgent; //
	private String containerFullOrEmpty;// F,
	private String containerSubHandlingType;// IT – ITT, US – Un-stuffing
	private String containerIso;// 4001,
	private String containerSize;// 40,
	private String containerHeight;// 8,
	private String containerType;// GE/DV/RE,
	private String containerTareWeight;
	private String containerDischargeDateTime;// 20161124162510,
	private String currentYardPosition;// 02S-0102-C-1,
	private String impCarrierType;// null,
	private String vesselCode;// UANE,
	private String vesselVoyage;// 003/2016,
	private String visitId;// 121212,
	private String vesselScn;// DB0899,
	private String vesselName;// AL NEFUD,
	private String vesselETA;// 20161124161800 E.g. dd/MM/yyyy HH:mm:ss
	private String vesselATA;// 20161124161800
	private String vesselStatus;

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerInOrOut() {
		return containerInOrOut;
	}

	public void setContainerInOrOut(String containerInOrOut) {
		this.containerInOrOut = containerInOrOut;
	}

	public String getContainerShippingLine() {
		return containerShippingLine;
	}

	public void setContainerShippingLine(String containerShippingLine) {
		this.containerShippingLine = containerShippingLine;
	}

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	public String getContainerIso() {
		return containerIso;
	}

	public void setContainerIso(String containerIso) {
		this.containerIso = containerIso;
	}

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getCurrentYardPosition() {
		return currentYardPosition;
	}

	public void setCurrentYardPosition(String currentYardPosition) {
		this.currentYardPosition = currentYardPosition;
	}

	public String getImpCarrierType() {
		return impCarrierType;
	}

	public void setImpCarrierType(String impCarrierType) {
		this.impCarrierType = impCarrierType;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getVesselVoyage() {
		return vesselVoyage;
	}

	public void setVesselVoyage(String vesselVoyage) {
		this.vesselVoyage = vesselVoyage;
	}

	public String getVisitId() {
		return visitId;
	}

	public void setVisitId(String visitId) {
		this.visitId = visitId;
	}

	public String getVesselScn() {
		return vesselScn;
	}

	public void setVesselScn(String vesselScn) {
		this.vesselScn = vesselScn;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getVesselATA() {
		return vesselATA;
	}

	public void setVesselATA(String vesselATA) {
		this.vesselATA = vesselATA;
	}

	public String getContainerSize() {
		return containerSize;
	}

	public void setContainerSize(String containerSize) {
		this.containerSize = containerSize;
	}

	public String getContainerHeight() {
		return containerHeight;
	}

	public void setContainerHeight(String containerHeight) {
		this.containerHeight = containerHeight;
	}

	public String getContainerDischargeDateTime() {
		return containerDischargeDateTime;
	}

	public void setContainerDischargeDateTime(String containerDischargeDateTime) {
		this.containerDischargeDateTime = containerDischargeDateTime;
	}

	public String getContainerShippingAgent() {
		return containerShippingAgent;
	}

	public void setContainerShippingAgent(String containerShippingAgent) {
		this.containerShippingAgent = containerShippingAgent;
	}

	public String getContainerTareWeight() {
		return containerTareWeight;
	}

	public void setContainerTareWeight(String containerTareWeight) {
		this.containerTareWeight = containerTareWeight;
	}

	public String getVesselETA() {
		return vesselETA;
	}

	public void setVesselETA(String vesselETA) {
		this.vesselETA = vesselETA;
	}

	public String getVesselStatus() {
		return vesselStatus;
	}

	public void setVesselStatus(String vesselStatus) {
		this.vesselStatus = vesselStatus;
	}
	
	public String getImpOrderNo() {
		return impOrderNo;
	}

	public void setImpOrderNo(String impOrderNo) {
		this.impOrderNo = impOrderNo;
	}

	public String getImpOrderType() {
		return impOrderType;
	}

	public void setImpOrderType(String impOrderType) {
		this.impOrderType = impOrderType;
	}

	public String getImpOrderStatus() {
		return impOrderStatus;
	}

	public void setImpOrderStatus(String impOrderStatus) {
		this.impOrderStatus = impOrderStatus;
	}

	public String getContainerSubHandlingType() {
		return containerSubHandlingType;
	}

	public void setContainerSubHandlingType(String containerSubHandlingType) {
		this.containerSubHandlingType = containerSubHandlingType;
	}

	@Override
	public String toString() {
		return "GIReadResponseImportContainer [containerNo=" + containerNo + ", containerShippingAgent="
				+ containerShippingAgent + ", containerInOrOut=" + containerInOrOut + ", containerShippingLine="
				+ containerShippingLine + ", containerFullOrEmpty=" + containerFullOrEmpty + ", containerIso="
				+ containerIso + ", containerSize=" + containerSize + ", containerHeight=" + containerHeight
				+ ", containerType=" + containerType + ", containerDischargeDateTime=" + containerDischargeDateTime
				+ ", currentYardPosition=" + currentYardPosition + ", impCarrierType=" + impCarrierType
				+ ", containerTareWeight=" + containerTareWeight + ", vesselCode=" + vesselCode + ", vesselVoyage="
				+ vesselVoyage + ", visitId=" + visitId + ", vesselScn=" + vesselScn + ", vesselName=" + vesselName
				+ ", vesselATA=" + vesselATA + ", vesselETA=" + vesselETA + ", vesselStatus=" + vesselStatus + ", impOrderNo=" + impOrderNo +
				", impOrderType=" + impOrderType + ", impOrderStatus=" + impOrderStatus + ", containerSubHandlingType=" + containerSubHandlingType +"]";
	}

}
