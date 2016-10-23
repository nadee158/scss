/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum Gender {
	
	MALE("M"), FEMALE("F"), OTHER("O");
	
	
	private final String male;

    private Gender(String male) {
        this.male = male;
    }

	/**
	 * @return the male
	 */
	public String getMale() {
		return male;
	}
    

	

}
