/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class UpdateSealDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long gatePass01;
	private String gatePass01Seal01;
	private String gatePass01Seal02;
	private Long gatePass02;
	private String gatePass02Seal01;
	private String gatePass02Seal02;
	private boolean forceSealUpdate;
	

	public Long getGatePass01() {
		return gatePass01;
	}
	public void setGatePass01(Long gatePass01) {
		this.gatePass01 = gatePass01;
	}
	public Long getGatePass02() {
		return gatePass02;
	}
	public void setGatePass02(Long gatePass02) {
		this.gatePass02 = gatePass02;
	}
	public String getGatePass01Seal01() {
		return gatePass01Seal01;
	}
	public void setGatePass01Seal01(String gatePass01Seal01) {
		this.gatePass01Seal01 = gatePass01Seal01;
	}
	public String getGatePass01Seal02() {
		return gatePass01Seal02;
	}
	public void setGatePass01Seal02(String gatePass01Seal02) {
		this.gatePass01Seal02 = gatePass01Seal02;
	}
	public String getGatePass02Seal01() {
		return gatePass02Seal01;
	}
	public void setGatePass02Seal01(String gatePass02Seal01) {
		this.gatePass02Seal01 = gatePass02Seal01;
	}
	public String getGatePass02Seal02() {
		return gatePass02Seal02;
	}
	public void setGatePass02Seal02(String gatePass02Seal02) {
		this.gatePass02Seal02 = gatePass02Seal02;
	}
	public boolean isForceSealUpdate() {
		return forceSealUpdate;
	}
	public void setForceSealUpdate(boolean forceSealUpdate) {
		this.forceSealUpdate = forceSealUpdate;
	}
	
}
