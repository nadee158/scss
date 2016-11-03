/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum CardPrintStatus implements Enumable {
	
	PRINTED("P");
	
	private final String cardPrintStatus;

    private CardPrintStatus(String cardPrintStatus) {
        this.cardPrintStatus = cardPrintStatus;
    }

	/**
	 * @return the cardPrintStatus
	 */
	public String getValue() {
		return cardPrintStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
