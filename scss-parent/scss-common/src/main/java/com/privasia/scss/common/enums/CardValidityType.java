/**
 * 
 */
package com.privasia.scss.common.enums;


/**
 * @author Janaka
 *
 */
public enum CardValidityType implements Enumable {
	
	USAHAWAN("U"), PERMANENT("P"), CONTRACTOR("C");
	
	
	private final String cardValidityType;

    private CardValidityType(String cardValidityType) {
        this.cardValidityType = cardValidityType;
    }

    
	/**
	 * @return the cardValidityType
	 */
	public String getValue() {
		return cardValidityType;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
