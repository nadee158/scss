/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardIsusedStatus {
	
	YES("Y"), NO("N");
	
	
	private final String cardIsused;

    private CardIsusedStatus(String cardIsused) {
        this.cardIsused = cardIsused;
    }

	/**
	 * @return the cardIsused
	 */
	public String getCardIsused() {
		return cardIsused;
	}

	

}
