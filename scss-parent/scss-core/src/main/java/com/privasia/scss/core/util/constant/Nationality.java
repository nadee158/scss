/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum Nationality {
	
	MALAYSIAN("MY"), INDIAN("IN");
	
	
	private final String nationality;

    private Nationality(String nationality) {
        this.nationality = nationality;
    }

	/**
	 * @return the nationality
	 */
	public String getNationality() {
		return nationality;
	}

	
	

}
