/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardUsageStatus {
	
	STATUS_COMPLETE("C"), STATUS_LOCK("L"), STATUS_RESET("R");
	
	private final String cardUsageStatus;

    private CardUsageStatus(String cardUsageStatus) {
        this.cardUsageStatus = cardUsageStatus;
    }

	/**
	 * @return the cardUsageStatus
	 */
	public String getCardUsageStatus() {
		return cardUsageStatus;
	}

	

}
