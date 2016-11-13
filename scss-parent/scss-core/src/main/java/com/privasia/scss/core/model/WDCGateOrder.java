/**
 * 
 */
package com.privasia.scss.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Janaka
 *
 */
@Entity
@Table(name = "WDC_GATE_ORDER")
public class WDCGateOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "GATE_ORDER_NO")
	private Long gateOrderNo;

	@Column(name = "VESSEL_SCN")
	private String vesselSCN;

	@Column(name = "VESSEL_NAME")
	private String vesselName;

	public Long getGateOrderNo() {
		return gateOrderNo;
	}

	public void setGateOrderNo(Long gateOrderNo) {
		this.gateOrderNo = gateOrderNo;
	}

	public String getVesselSCN() {
		return vesselSCN;
	}

	public void setVesselSCN(String vesselSCN) {
		this.vesselSCN = vesselSCN;
	}

	public String getVesselName() {
		return vesselName;
	}

	public void setVesselName(String vesselName) {
		this.vesselName = vesselName;
	}

	public String getMoveType() {
		return moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
	}

	public String getLineCode() {
		return lineCode;
	}

	public void setLineCode(String lineCode) {
		this.lineCode = lineCode;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	@Column(name = "MOVE_TYPE")
	private String moveType;

	@Column(name = "LINE_CODE")
	private String lineCode;

	@Column(name = "TYPE_CODE")
	private String typeCode;

}
