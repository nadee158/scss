/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum HpatStatus {
	
	ACTIVE("ACTV"), CANCEL("CAN"), COMPLETE("COMP");
	
	
	private final String hpatStatus;

    private HpatStatus(String hpatStatus) {
        this.hpatStatus = hpatStatus;
    }

	/**
	 * @return the hpatStatus
	 */
	public String getHpatStatus() {
		return hpatStatus;
	}

	

}
