/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum GatePassStatus {
	
	ACTIVE("A"), CANCEL("C");
	
	private final String gatePassStatus;

    private GatePassStatus(String gatePassStatus) {
        this.gatePassStatus = gatePassStatus;
    }

	/**
	 * @return the gatePassStatus
	 */
	public String getGatePassStatus() {
		return gatePassStatus;
	}

	

}
