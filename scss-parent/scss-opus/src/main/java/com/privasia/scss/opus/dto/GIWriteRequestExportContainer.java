package com.privasia.scss.opus.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GIWriteRequestExportContainer implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String containerNo = StringUtils.EMPTY;// ;//AZHA0000001,
	private String containerIso = StringUtils.EMPTY;// 22G0,
	private String containerNetWeight = StringUtils.EMPTY;// 9000,
	private String containerSeal1_SL = StringUtils.EMPTY;// null,
	private String containerSeal1_NO = StringUtils.EMPTY;// SEAL001,
	private String containerSeal2_SL = StringUtils.EMPTY;// null,
	private String containerSeal2_NO = StringUtils.EMPTY;// null,
	private String containerReeferIndicator = StringUtils.EMPTY;// N,
	private String containerReeferTempSign = StringUtils.EMPTY;// null,
	private String containerReeferTempValue = StringUtils.EMPTY;// null,
	private String containerReeferTempUnit = StringUtils.EMPTY;// null,
	private String containerDGImdg = StringUtils.EMPTY;// null,
	private String containerDGUNCode = StringUtils.EMPTY;// null,
	private String containerDGImdgLabel = StringUtils.EMPTY;// null,
	private String containerOOG_OH = StringUtils.EMPTY;// 5.0,
	private String containerOOG_OL = StringUtils.EMPTY;// 3.0,
	private String containerOOG_OF = StringUtils.EMPTY;// 1.0,
	private String containerOOG_OA = StringUtils.EMPTY;// 2.0,
	private String containerOOG_OR = StringUtils.EMPTY;// 4.0
	private String containerDamageCode1 = StringUtils.EMPTY;//
	private String containerDamageCode2 = StringUtils.EMPTY;//
	private String containerDamageCode3 = StringUtils.EMPTY;//
	private String containerDamageCode4 = StringUtils.EMPTY;//
	private String containerDamageCode5 = StringUtils.EMPTY;//
	private String containerDamageCode6 = StringUtils.EMPTY;//
	private String containerDamageCode7 = StringUtils.EMPTY;//
	private String containerDamageCode8 = StringUtils.EMPTY;//
	private String containerDamageCode9 = StringUtils.EMPTY;//

	private String positionOnTruck = StringUtils.EMPTY;// M
	private String wrongDoorIndicator = StringUtils.EMPTY;//
	private String containerHasOCSSR = StringUtils.EMPTY;// Y
	private String containerHasRPSSR = StringUtils.EMPTY;// Y
	private String containerHasOOGSSR = StringUtils.EMPTY;// Y
	private String vgmType = StringUtils.EMPTY;// S
	private String vgmWeighingStation = StringUtils.EMPTY;//
	private String vgmWitnessName = StringUtils.EMPTY;//
	private String vgmWitnessID = StringUtils.EMPTY;//
	private String vgmRefNo = StringUtils.EMPTY;//
	private String vgmMGW = StringUtils.EMPTY;// 1
	private String vgmNetWeight = StringUtils.EMPTY;// 100000
	private String vgmVerificationDatetime = StringUtils.EMPTY;//
	private String vgmSolasRefNo = StringUtils.EMPTY;//
	private String vgmSolasWithinTolerance = StringUtils.EMPTY;//

	public String getContainerNo() {
		return containerNo;
	}

	public void setContainerNo(String containerNo) {
		this.containerNo = containerNo;
	}

	public String getContainerIso() {
		return containerIso;
	}

	public void setContainerIso(String containerIso) {
		this.containerIso = containerIso;
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

	public String getPositionOnTruck() {
		return positionOnTruck;
	}

	public void setPositionOnTruck(String positionOnTruck) {
		this.positionOnTruck = positionOnTruck;
	}

	public String getWrongDoorIndicator() {
		return wrongDoorIndicator;
	}

	public void setWrongDoorIndicator(String wrongDoorIndicator) {
		this.wrongDoorIndicator = wrongDoorIndicator;
	}

	public String getContainerHasOCSSR() {
		return containerHasOCSSR;
	}

	public void setContainerHasOCSSR(String containerHasOCSSR) {
		this.containerHasOCSSR = containerHasOCSSR;
	}

	public String getContainerHasRPSSR() {
		return containerHasRPSSR;
	}

	public void setContainerHasRPSSR(String containerHasRPSSR) {
		this.containerHasRPSSR = containerHasRPSSR;
	}

	public String getContainerHasOOGSSR() {
		return containerHasOOGSSR;
	}

	public void setContainerHasOOGSSR(String containerHasOOGSSR) {
		this.containerHasOOGSSR = containerHasOOGSSR;
	}

	public String getVgmType() {
		return vgmType;
	}

	public void setVgmType(String vgmType) {
		this.vgmType = vgmType;
	}

	public String getVgmWeighingStation() {
		return vgmWeighingStation;
	}

	public void setVgmWeighingStation(String vgmWeighingStation) {
		this.vgmWeighingStation = vgmWeighingStation;
	}

	public String getVgmWitnessName() {
		return vgmWitnessName;
	}

	public void setVgmWitnessName(String vgmWitnessName) {
		this.vgmWitnessName = vgmWitnessName;
	}

	public String getVgmWitnessID() {
		return vgmWitnessID;
	}

	public void setVgmWitnessID(String vgmWitnessID) {
		this.vgmWitnessID = vgmWitnessID;
	}

	public String getVgmRefNo() {
		return vgmRefNo;
	}

	public void setVgmRefNo(String vgmRefNo) {
		this.vgmRefNo = vgmRefNo;
	}

	public String getVgmMGW() {
		return vgmMGW;
	}

	public void setVgmMGW(String vgmMGW) {
		this.vgmMGW = vgmMGW;
	}

	public String getVgmNetWeight() {
		return vgmNetWeight;
	}

	public void setVgmNetWeight(String vgmNetWeight) {
		this.vgmNetWeight = vgmNetWeight;
	}

	public String getVgmVerificationDatetime() {
		return vgmVerificationDatetime;
	}

	public void setVgmVerificationDatetime(String vgmVerificationDatetime) {
		this.vgmVerificationDatetime = vgmVerificationDatetime;
	}

	public String getVgmSolasRefNo() {
		return vgmSolasRefNo;
	}

	public void setVgmSolasRefNo(String vgmSolasRefNo) {
		this.vgmSolasRefNo = vgmSolasRefNo;
	}

	public String getVgmSolasWithinTolerance() {
		return vgmSolasWithinTolerance;
	}

	public void setVgmSolasWithinTolerance(String vgmSolasWithinTolerance) {
		this.vgmSolasWithinTolerance = vgmSolasWithinTolerance;
	}

	@Override
	public String toString() {
		return "GIWriteRequestExportContainer [containerNo=" + containerNo + ", containerIso=" + containerIso
				+ ", wrongDoorIndicator=" + wrongDoorIndicator + ", positionOnTruck=" + positionOnTruck
				+ ", containerHasOCSSR=" + containerHasOCSSR + ", containerHasRPSSR=" + containerHasRPSSR
				+ ", containerNetWeight=" + containerNetWeight + ", containerSeal1_SL=" + containerSeal1_SL
				+ ", containerSeal1_NO=" + containerSeal1_NO + ", containerSeal2_SL=" + containerSeal2_SL
				+ ", containerSeal2_NO=" + containerSeal2_NO + ", containerReeferIndicator=" + containerReeferIndicator
				+ ", containerReeferTempSign=" + containerReeferTempSign + ", containerReeferTempValue="
				+ containerReeferTempValue + ", containerReeferTempUnit=" + containerReeferTempUnit
				+ ", containerDGImdg=" + containerDGImdg + ", containerDGUNCode=" + containerDGUNCode
				+ ", containerDGImdgLabel=" + containerDGImdgLabel + ", containerHasOOGSSR=" + containerHasOOGSSR
				+ ", containerOOG_OH=" + containerOOG_OH + ", containerOOG_OL=" + containerOOG_OL + ", containerOOG_OF="
				+ containerOOG_OF + ", containerOOG_OA=" + containerOOG_OA + ", containerOOG_OR=" + containerOOG_OR
				+ ", containerDamageCode1=" + containerDamageCode1 + ", containerDamageCode2=" + containerDamageCode2
				+ ", containerDamageCode3=" + containerDamageCode3 + ", containerDamageCode4=" + containerDamageCode4
				+ ", containerDamageCode5=" + containerDamageCode5 + ", containerDamageCode6=" + containerDamageCode6
				+ ", containerDamageCode7=" + containerDamageCode7 + ", containerDamageCode8=" + containerDamageCode8
				+ ", containerDamageCode9=" + containerDamageCode9 + ", vgmType=" + vgmType + ", vgmWeighingStation="
				+ vgmWeighingStation + ", vgmWitnessName=" + vgmWitnessName + ", vgmWitnessID=" + vgmWitnessID
				+ ", vgmRefNo=" + vgmRefNo + ", vgmMGW=" + vgmMGW + ", vgmNetWeight=" + vgmNetWeight
				+ ", vgmSolasRefNo=" + vgmSolasRefNo + ", vgmSolasWithinTolerance=" + vgmSolasWithinTolerance
				+ ", vgmVerificationDatetime=" + vgmVerificationDatetime + "]";
	}

}
