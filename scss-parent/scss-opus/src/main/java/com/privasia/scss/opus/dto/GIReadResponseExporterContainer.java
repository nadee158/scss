package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIReadResponseExporterContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo;// ;//AZHA0000001,
	private String exportOrderNo;// ;//FA000987,
	private String exportOrderType;// ;//BKG,
	private String exportOrderStatus;// ;//RGS,
	private String containerInOrOut;// IN,
	private String containerShippingLine;// CMA,
	private String containerShippingAgent;// CMA1,
	private String containerFullOrEmpty;// F,
	private String containerSubHandlingType;// null,,
	private String expCarrierType;// VS,
	private String vesselCode;// CSQD,
	private String vesselVoyageIN;// 001/2016,
	private String vesselVoyageOUT;// 001/2016,
	private String visitId;// 10267538,
	private String vesselScn;// ETX9,
	private String vesselName;// XIN QING DAO,
	private String vesselETA;// 20160907080000,
	private String vesselATA;// 20160907080000,
	private String vesselStatus;// ACT,
	private String yardOpeningDateTime;// 20160902080000,
	private String containerSpod;// USSVN,
	private String containerIso;// 22G0,
	private String containerSize;// 20,
	private String containerHeight;// 8.6,
	private String containerType;// GE,
	private String containerTareWeight;
	private String containerGrossWeight;// 11000,
	private String containerNetWeight;// 9000,
	private String containerSeal1_SL;// null,
	private String containerSeal1_NO;// SEAL001,
	private String containerSeal2_SL;// null,
	private String containerSeal2_NO;// null,
	private String containerReeferIndicator;// N,
	private String containerReeferTempSign;// null,
	private String containerReeferTempValue;// null,
	private String containerReeferTempUnit;// null,
	private String containerDGImdg;// null,
	private String containerDGUNCode;// null,
	private String containerDGImdgLabel;// null,
	private String yardDGOpeningDateTime;// 20160904080000,
	private String containerOOG_OH;// 5.0,
	private String containerOOG_OL;// 3.0,
	private String containerOOG_OF;// 1.0,
	private String containerOOG_OA;// 2.0,
	private String containerOOG_OR;// 4.0
	private String containerDamageCode1;//
	private String containerDamageCode2;//
	private String containerDamageCode3;//
	private String containerDamageCode4;//
	private String containerDamageCode5;//
	private String containerDamageCode6;//
	private String containerDamageCode7;//
	private String containerDamageCode8;//
	private String containerDamageCode9;//

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getExportOrderNo() {
		return exportOrderNo;
	}

	public void setExportOrderNo(String exportOrderNo) {
		this.exportOrderNo = exportOrderNo;
	}

	public String getExportOrderType() {
		return exportOrderType;
	}

	public void setExportOrderType(String exportOrderType) {
		this.exportOrderType = exportOrderType;
	}

	public String getExportOrderStatus() {
		return exportOrderStatus;
	}

	public void setExportOrderStatus(String exportOrderStatus) {
		this.exportOrderStatus = exportOrderStatus;
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

	public String getContainerShippingAgent() {
		return containerShippingAgent;
	}

	public void setContainerShippingAgent(String containerShippingAgent) {
		this.containerShippingAgent = containerShippingAgent;
	}

	public String getContainerFullOrEmpty() {
		return containerFullOrEmpty;
	}

	public void setContainerFullOrEmpty(String containerFullOrEmpty) {
		this.containerFullOrEmpty = containerFullOrEmpty;
	}

	public String getContainerSubHandlingType() {
		return containerSubHandlingType;
	}

	public void setContainerSubHandlingType(String containerSubHandlingType) {
		this.containerSubHandlingType = containerSubHandlingType;
	}

	public String getExpCarrierType() {
		return expCarrierType;
	}

	public void setExpCarrierType(String expCarrierType) {
		this.expCarrierType = expCarrierType;
	}

	public String getVesselCode() {
		return vesselCode;
	}

	public void setVesselCode(String vesselCode) {
		this.vesselCode = vesselCode;
	}

	public String getVesselVoyageIN() {
		return vesselVoyageIN;
	}

	public void setVesselVoyageIN(String vesselVoyageIN) {
		this.vesselVoyageIN = vesselVoyageIN;
	}

	public String getVesselVoyageOUT() {
		return vesselVoyageOUT;
	}

	public void setVesselVoyageOUT(String vesselVoyageOUT) {
		this.vesselVoyageOUT = vesselVoyageOUT;
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

	public String getVesselETA() {
		return vesselETA;
	}

	public void setVesselETA(String vesselETA) {
		this.vesselETA = vesselETA;
	}

	public String getVesselATA() {
		return vesselATA;
	}

	public void setVesselATA(String vesselATA) {
		this.vesselATA = vesselATA;
	}

	public String getVesselStatus() {
		return vesselStatus;
	}

	public void setVesselStatus(String vesselStatus) {
		this.vesselStatus = vesselStatus;
	}

	public String getYardOpeningDateTime() {
		return yardOpeningDateTime;
	}

	public void setYardOpeningDateTime(String yardOpeningDateTime) {
		this.yardOpeningDateTime = yardOpeningDateTime;
	}

	public String getContainerSpod() {
		return containerSpod;
	}

	public void setContainerSpod(String containerSpod) {
		this.containerSpod = containerSpod;
	}

	public String getContainerIso() {
		return containerIso;
	}

	public void setContainerIso(String containerIso) {
		this.containerIso = containerIso;
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

	public String getContainerType() {
		return containerType;
	}

	public void setContainerType(String containerType) {
		this.containerType = containerType;
	}

	public String getContainerTareWeight() {
		return containerTareWeight;
	}

	public void setContainerTareWeight(String containerTareWeight) {
		this.containerTareWeight = containerTareWeight;
	}

	public String getContainerGrossWeight() {
		return containerGrossWeight;
	}

	public void setContainerGrossWeight(String containerGrossWeight) {
		this.containerGrossWeight = containerGrossWeight;
	}

	public String getContainerNetWeight() {
		return containerNetWeight;
	}

	public void setContainerNetWeight(String containerNetWeight) {
		this.containerNetWeight = containerNetWeight;
	}

	public String getContainerSeal1_SL() {
		return containerSeal1_SL;
	}

	public void setContainerSeal1_SL(String containerSeal1_SL) {
		this.containerSeal1_SL = containerSeal1_SL;
	}

	public String getContainerSeal1_NO() {
		return containerSeal1_NO;
	}

	public void setContainerSeal1_NO(String containerSeal1_NO) {
		this.containerSeal1_NO = containerSeal1_NO;
	}

	public String getContainerSeal2_SL() {
		return containerSeal2_SL;
	}

	public void setContainerSeal2_SL(String containerSeal2_SL) {
		this.containerSeal2_SL = containerSeal2_SL;
	}

	public String getContainerSeal2_NO() {
		return containerSeal2_NO;
	}

	public void setContainerSeal2_NO(String containerSeal2_NO) {
		this.containerSeal2_NO = containerSeal2_NO;
	}

	public String getContainerReeferIndicator() {
		return containerReeferIndicator;
	}

	public void setContainerReeferIndicator(String containerReeferIndicator) {
		this.containerReeferIndicator = containerReeferIndicator;
	}

	public String getContainerReeferTempSign() {
		return containerReeferTempSign;
	}

	public void setContainerReeferTempSign(String containerReeferTempSign) {
		this.containerReeferTempSign = containerReeferTempSign;
	}

	public String getContainerReeferTempValue() {
		return containerReeferTempValue;
	}

	public void setContainerReeferTempValue(String containerReeferTempValue) {
		this.containerReeferTempValue = containerReeferTempValue;
	}

	public String getContainerReeferTempUnit() {
		return containerReeferTempUnit;
	}

	public void setContainerReeferTempUnit(String containerReeferTempUnit) {
		this.containerReeferTempUnit = containerReeferTempUnit;
	}

	public String getContainerDGImdg() {
		return containerDGImdg;
	}

	public void setContainerDGImdg(String containerDGImdg) {
		this.containerDGImdg = containerDGImdg;
	}

	public String getContainerDGUNCode() {
		return containerDGUNCode;
	}

	public void setContainerDGUNCode(String containerDGUNCode) {
		this.containerDGUNCode = containerDGUNCode;
	}

	public String getContainerDGImdgLabel() {
		return containerDGImdgLabel;
	}

	public void setContainerDGImdgLabel(String containerDGImdgLabel) {
		this.containerDGImdgLabel = containerDGImdgLabel;
	}

	public String getYardDGOpeningDateTime() {
		return yardDGOpeningDateTime;
	}

	public void setYardDGOpeningDateTime(String yardDGOpeningDateTime) {
		this.yardDGOpeningDateTime = yardDGOpeningDateTime;
	}

	public String getContainerOOG_OH() {
		return containerOOG_OH;
	}

	public void setContainerOOG_OH(String containerOOG_OH) {
		this.containerOOG_OH = containerOOG_OH;
	}

	public String getContainerOOG_OL() {
		return containerOOG_OL;
	}

	public void setContainerOOG_OL(String containerOOG_OL) {
		this.containerOOG_OL = containerOOG_OL;
	}

	public String getContainerOOG_OF() {
		return containerOOG_OF;
	}

	public void setContainerOOG_OF(String containerOOG_OF) {
		this.containerOOG_OF = containerOOG_OF;
	}

	public String getContainerOOG_OA() {
		return containerOOG_OA;
	}

	public void setContainerOOG_OA(String containerOOG_OA) {
		this.containerOOG_OA = containerOOG_OA;
	}

	public String getContainerOOG_OR() {
		return containerOOG_OR;
	}

	public void setContainerOOG_OR(String containerOOG_OR) {
		this.containerOOG_OR = containerOOG_OR;
	}

	public String getContainerDamageCode1() {
		return containerDamageCode1;
	}

	public void setContainerDamageCode1(String containerDamageCode1) {
		this.containerDamageCode1 = containerDamageCode1;
	}

	public String getContainerDamageCode2() {
		return containerDamageCode2;
	}

	public void setContainerDamageCode2(String containerDamageCode2) {
		this.containerDamageCode2 = containerDamageCode2;
	}

	public String getContainerDamageCode3() {
		return containerDamageCode3;
	}

	public void setContainerDamageCode3(String containerDamageCode3) {
		this.containerDamageCode3 = containerDamageCode3;
	}

	public String getContainerDamageCode4() {
		return containerDamageCode4;
	}

	public void setContainerDamageCode4(String containerDamageCode4) {
		this.containerDamageCode4 = containerDamageCode4;
	}

	public String getContainerDamageCode5() {
		return containerDamageCode5;
	}

	public void setContainerDamageCode5(String containerDamageCode5) {
		this.containerDamageCode5 = containerDamageCode5;
	}

	public String getContainerDamageCode6() {
		return containerDamageCode6;
	}

	public void setContainerDamageCode6(String containerDamageCode6) {
		this.containerDamageCode6 = containerDamageCode6;
	}

	public String getContainerDamageCode7() {
		return containerDamageCode7;
	}

	public void setContainerDamageCode7(String containerDamageCode7) {
		this.containerDamageCode7 = containerDamageCode7;
	}

	public String getContainerDamageCode8() {
		return containerDamageCode8;
	}

	public void setContainerDamageCode8(String containerDamageCode8) {
		this.containerDamageCode8 = containerDamageCode8;
	}

	public String getContainerDamageCode9() {
		return containerDamageCode9;
	}

	public void setContainerDamageCode9(String containerDamageCode9) {
		this.containerDamageCode9 = containerDamageCode9;
	}

	@Override
	public String toString() {
		return "GIReadResponseExporterContainer [containerNo=" + containerNo + ", exportOrderNo=" + exportOrderNo
				+ ", containerInOrOut=" + containerInOrOut + ", containerShippingLine=" + containerShippingLine
				+ ", containerFullOrEmpty=" + containerFullOrEmpty + ", containerSubHandlingType="
				+ containerSubHandlingType + ", expCarrierType=" + expCarrierType + ", exportOrderType="
				+ exportOrderType + ", vesselCode=" + vesselCode + ", vesselVoyageIN=" + vesselVoyageIN + ", visitId="
				+ visitId + ", vesselScn=" + vesselScn + ", vesselName=" + vesselName + ", vesselETA=" + vesselETA
				+ ", yardOpeningDateTime=" + yardOpeningDateTime + ", containerSpod=" + containerSpod
				+ ", containerIso=" + containerIso + ", containerSize=" + containerSize + ", containerHeight="
				+ containerHeight + ", containerType=" + containerType + ", containerGrossWeight="
				+ containerGrossWeight + ", containerNetWeight=" + containerNetWeight + ", containerSeal1_SL="
				+ containerSeal1_SL + ", containerSeal1_NO=" + containerSeal1_NO + ", containerSeal2_SL="
				+ containerSeal2_SL + ", containerSeal2_NO=" + containerSeal2_NO + ", containerReeferIndicator="
				+ containerReeferIndicator + ", containerReeferTempSign=" + containerReeferTempSign
				+ ", containerReeferTempValue=" + containerReeferTempValue + ", containerReeferTempUnit="
				+ containerReeferTempUnit + ", containerDGImdg=" + containerDGImdg + ", containerDGUNCode="
				+ containerDGUNCode + ", containerDGImdgLabel=" + containerDGImdgLabel + ", yardDGOpeningDateTime="
				+ yardDGOpeningDateTime + ", containerOOG_OH=" + containerOOG_OH + ", containerOOG_OL="
				+ containerOOG_OL + ", containerOOG_OF=" + containerOOG_OF + ", containerOOG_OA=" + containerOOG_OA
				+ ", containerOOG_OR=" + containerOOG_OR + ", containerDamageCode1=" + containerDamageCode1
				+ ", containerDamageCode2=" + containerDamageCode2 + ", containerDamageCode3=" + containerDamageCode3
				+ ", containerDamageCode4=" + containerDamageCode4 + ", containerDamageCode5=" + containerDamageCode5
				+ ", containerDamageCode6=" + containerDamageCode6 + ", containerDamageCode7=" + containerDamageCode7
				+ ", containerDamageCode8=" + containerDamageCode8 + ", containerDamageCode9=" + containerDamageCode9
				+ ", exportOrderStatus=" + exportOrderStatus + ", containerShippingAgent=" + containerShippingAgent
				+ ", vesselVoyageOUT=" + vesselVoyageOUT + ", vesselATA=" + vesselATA + ", vesselStatus=" + vesselStatus
				+ ", containerTareWeight=" + containerTareWeight + "]";
	}

	public static List<GIReadResponseExporterContainer> constructGIW01RequestTestList() {
		List<GIReadResponseExporterContainer> list = new ArrayList<GIReadResponseExporterContainer>();
		GIReadResponseExporterContainer gIReadResponseExporterContainer = new GIReadResponseExporterContainer();
		// "containerNo":"NH161219003"
		// ,"containerIso":"40G1"
		// ,"containerNetWeight":"8000"
		// ,"containerSeal1_SL":"C"
		// ,"containerSeal1_NO":"SL1901"
		// ,"containerReeferIndicator":"N"
		// ,"positionOnTruck":"M"
		// ,"containerHasOCSSR":"Y"
		// ,"containerHasRPSSR":"Y"
		// ,"vgmType":"S"
		// ,"vgmMGW":"1"

		gIReadResponseExporterContainer.setContainerNo("NH161219003");
		gIReadResponseExporterContainer.setContainerIso("40G1");
		gIReadResponseExporterContainer.setContainerNetWeight("8000");
		gIReadResponseExporterContainer.setContainerSeal1_SL("C");
		gIReadResponseExporterContainer.setContainerSeal1_NO("SL1901");
		gIReadResponseExporterContainer.setContainerReeferIndicator("N");
		list.add(gIReadResponseExporterContainer);
		return list;
	}

}
