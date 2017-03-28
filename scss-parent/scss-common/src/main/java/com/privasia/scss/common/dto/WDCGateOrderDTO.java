/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;


/**
 * @author Janaka
 *
 */
public class WDCGateOrderDTO implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long gateOrderNo;

	private String vesselSCN;

	private String vesselName;
	
	private String moveType;

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

}
