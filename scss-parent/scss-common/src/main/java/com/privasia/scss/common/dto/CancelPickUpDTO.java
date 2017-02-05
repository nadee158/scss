/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class CancelPickUpDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long gatePass01;
	private String remarks01;
	private Long gatePass02;
	private String remarks02;
	
	public Long getGatePass01() {
		return gatePass01;
	}
	public void setGatePass01(Long gatePass01) {
		this.gatePass01 = gatePass01;
	}
	public String getRemarks01() {
		return remarks01;
	}
	public void setRemarks01(String remarks01) {
		this.remarks01 = remarks01;
	}
	public Long getGatePass02() {
		return gatePass02;
	}
	public void setGatePass02(Long gatePass02) {
		this.gatePass02 = gatePass02;
	}
	public String getRemarks02() {
		return remarks02;
	}
	public void setRemarks02(String remarks02) {
		this.remarks02 = remarks02;
	}
	

}
