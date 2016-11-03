/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardUsageStatus implements Enumable {
	
	STATUS_COMPLETE("C"), STATUS_LOCK("L"), STATUS_RESET("R");
	
	private final String cardUsageStatus;

    private CardUsageStatus(String cardUsageStatus) {
        this.cardUsageStatus = cardUsageStatus;
    }

	/**
	 * @return the cardUsageStatus
	 */
	public String getValue() {
		return cardUsageStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
