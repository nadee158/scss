package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.common.enums.SolasInstructionType;

public class CommonSolasDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer mgw = 0;

	private String faLedgerCode;

	private String solasRefNumber;

	private String solasDetailID;

	private String solasInstruction = SolasInstructionType.VGM_INSTRUCTION_NO_SOLAS.getValue();

	private String shipperVGM;

	private String vgmWitnessName;

	private String vgmWitnessID;

	private String vgmWeighingStation;

	private boolean directEntry = false;

	public Integer getMgw() {
		return mgw;
	}

	public void setMgw(Integer mgw) {
		this.mgw = mgw;
	}

	public String getFaLedgerCode() {
		return faLedgerCode;
	}

	public void setFaLedgerCode(String faLedgerCode) {
		this.faLedgerCode = faLedgerCode;
	}

	public String getSolasRefNumber() {
		return solasRefNumber;
	}

	public void setSolasRefNumber(String solasRefNumber) {
		this.solasRefNumber = solasRefNumber;
	}

	public String getSolasDetailID() {
		return solasDetailID;
	}

	public void setSolasDetailID(String solasDetailID) {
		this.solasDetailID = solasDetailID;
	}

	public String getSolasInstruction() {
		return solasInstruction;
	}

	public void setSolasInstruction(String solasInstruction) {
		this.solasInstruction = solasInstruction;
	}

	public String getShipperVGM() {
		return shipperVGM;
	}

	public void setShipperVGM(String shipperVGM) {
		this.shipperVGM = shipperVGM;
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

	public String getVgmWeighingStation() {
		return vgmWeighingStation;
	}

	public void setVgmWeighingStation(String vgmWeighingStation) {
		this.vgmWeighingStation = vgmWeighingStation;
	}
	
	public boolean isDirectEntry() {
		return directEntry;
	}

	public void setDirectEntry(boolean directEntry) {
		this.directEntry = directEntry;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	

}
