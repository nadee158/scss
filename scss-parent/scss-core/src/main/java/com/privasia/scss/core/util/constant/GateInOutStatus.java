/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum GateInOutStatus {
	
	IN("IN"), OUT("OUT");
	
	private final String gateInOutStatus;

    private GateInOutStatus(String gateInOutStatus) {
        this.gateInOutStatus = gateInOutStatus;
    }

	/**
	 * @return the gateInOutStatus
	 */
	public String getGateInOutStatus() {
		return gateInOutStatus;
	}

}
