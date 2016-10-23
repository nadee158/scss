/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardPrintStatus {
	
	PRINTED("P");
	
	private final String cardPrintStatus;

    private CardPrintStatus(String cardPrintStatus) {
        this.cardPrintStatus = cardPrintStatus;
    }

	/**
	 * @return the cardPrintStatus
	 */
	public String getCardPrintStatus() {
		return cardPrintStatus;
	}

}
