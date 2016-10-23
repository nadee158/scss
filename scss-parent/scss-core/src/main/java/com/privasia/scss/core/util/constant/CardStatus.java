/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardStatus {
	
	ACTIVE("A"), BLACKLIST("B"), SUSPENDED("S"), TERMINATED("T"), EXPIRED("E"), NOT_ISSUED("N"), 
	PENDING("D"), CREATED("C"), UPDATED("U");
	
	
	private final String cardStatus;

    private CardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

	/**
	 * @return the cardStatus
	 */
	public String getCardStatus() {
		return cardStatus;
	}
    

}
