/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum CardIssuedStatus implements Enumable {
	
	YES("Y"), NO("N");
		
	private final String cardIsused;

    private CardIssuedStatus(String cardIsused) {
        this.cardIsused = cardIsused;
    }

	/**
	 * @return the cardIsused
	 */
	public String getValue() {
		return cardIsused;
	}
	
	public Enum<?> getEnumFromValue(String value) {
        return EnumableHelper.getEnumFromValue(this, value, null);
    }
	

}
