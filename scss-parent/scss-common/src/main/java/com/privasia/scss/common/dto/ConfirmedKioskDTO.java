/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class ConfirmedKioskDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long gatePass01;
	private boolean status01;
	private Long gatePass02;
	private boolean status02;
	private Long kioskID;
	
	
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
	public Long getKioskID() {
		return kioskID;
	}
	public void setKioskID(Long kioskID) {
		this.kioskID = kioskID;
	}
	public boolean isStatus01() {
		return status01;
	}
	public void setStatus01(boolean status01) {
		this.status01 = status01;
	}
	public boolean isStatus02() {
		return status02;
	}
	public void setStatus02(boolean status02) {
		this.status02 = status02;
	}
	
	

}
