/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CardValidityType {
	
	USAHAWAN("U"), PERMANENT("P"), CONTRACTOR("C");
	
	
	private final String cardValidityType;

    private CardValidityType(String cardValidityType) {
        this.cardValidityType = cardValidityType;
    }

    
	/**
	 * @return the cardValidityType
	 */
	public String getCardValidityType() {
		return cardValidityType;
	}

	
	

}
