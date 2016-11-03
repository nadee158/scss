/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum Gender implements Enumable {
	
	MALE("M"), FEMALE("F"), OTHER("O");
	
	
	private final String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

	/**
	 * @return the male
	 */
	public String getValue() {
		return gender;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
  
}
