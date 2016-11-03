/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum Nationality implements Enumable {
	
	MALAYSIAN("MY"), INDIAN("IN");
	
	
	private final String nationality;

    private Nationality(String nationality) {
        this.nationality = nationality;
    }

	/**
	 * @return the nationality
	 */
	public String getValue() {
		return nationality;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
	
}
