/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum UserType implements Enumable {
	
	INTERNAL("I"), EXTERNAL("E");
	
	
	private final String userType;

    private UserType(String userType) {
        this.userType = userType;
    }

	/**
	 * @return the userType
	 */
	public String getValue() {
		return userType;
	}
	
	public Enum<?> getEnumFromValue(String value) {
        return EnumableHelper.getEnumFromValue(this, value, null);
    }

	
	

}
