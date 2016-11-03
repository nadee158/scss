/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum CardStatus implements Enumable {
	
	ACTIVE("A"), BLACKLIST("B"), SUSPENDED("S"), TERMINATED("T"), EXPIRED("E"), NOT_ISSUED("N"), 
	PENDING("D"), CREATED("C"), UPDATED("U");
	
	
	private final String cardStatus;

    private CardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }

	/**
	 * @return the cardStatus
	 */
	public String getValue() {
		return cardStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
    

}
