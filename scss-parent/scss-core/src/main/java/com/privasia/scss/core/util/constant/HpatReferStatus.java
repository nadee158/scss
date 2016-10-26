/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum HpatReferStatus {
	
	ACTIVE("ACTV"), CANCEL("CAN"), COMPLETE("COMP");
	
	
	private final String hpatStatus;

    private HpatReferStatus(String hpatStatus) {
        this.hpatStatus = hpatStatus;
    }

	/**
	 * @return the hpatStatus
	 */
	public String getHpatStatus() {
		return hpatStatus;
	}

	

}
