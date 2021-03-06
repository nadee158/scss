/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.springframework.beans.BeanUtils;

import com.privasia.scss.common.dto.SolasInfo;
import com.privasia.scss.common.enums.SolasInstructionType;

/**
 * @author Janaka
 *
 */
@Embeddable
public class CommonSolasAttribute implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "MGW")
	private Integer mgw;

	@Column(name = "FA_LEDGER_CODE")
	private String faLedgerCode;

	@Column(name = "SOLAS_REF_NO")
	private String solasRefNumber;

	@Column(name = "SOLAS_DET_ID")
	private String solasDetailID;

	@Column(name = "SOLAS_INSTRUCTION", nullable = true)
	@Type(type = "com.privasia.scss.common.enumusertype.SolasInstructionEnumUserType")
	private SolasInstructionType solasInstruction;

	@Column(name = "SHIPPER_VGM")
	private String shipperVGM;

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

	public SolasInstructionType getSolasInstruction() {
		return solasInstruction;
	}

	public void setSolasInstruction(SolasInstructionType solasInstruction) {
		this.solasInstruction = solasInstruction;
	}

	public String getShipperVGM() {
		return shipperVGM;
	}

	public void setShipperVGM(String shipperVGM) {
		this.shipperVGM = shipperVGM;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public SolasInfo constructSolasInfo() {
		SolasInfo solasInfo = new SolasInfo();
		BeanUtils.copyProperties(this, solasInfo, "solasInstruction");
		if (!(this.getSolasInstruction() == null)) {
			solasInfo.setSolasInstruction(this.getSolasInstruction().getValue());
		}
		return solasInfo;
	}

}
