/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

/**
 * @author Janaka
 *
 */
public class GatePassValidateDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private boolean validGatePass = true;
	
	private String gatePassErrorMessage = "Invalid gate pass ! ";

	public boolean isValidGatePass() {
		return validGatePass;
	}

	public void setValidGatePass(boolean validGatePass) {
		this.validGatePass = validGatePass;
	}

	public String getGatePassErrorMessage() {
		return gatePassErrorMessage;
	}

	public void setGatePassErrorMessage(String gatePassErrorMessage) {
		this.gatePassErrorMessage = gatePassErrorMessage;
	}
	
	
	

}
