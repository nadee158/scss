/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum ReferStatus {
	
	REJECT("R");
	
	private final String referStatus;

    private ReferStatus(String referStatus) {
        this.referStatus = referStatus;
    }

	/**
	 * @return the referStatus
	 */
	public String getReferStatus() {
		return referStatus;
	}

	
}
