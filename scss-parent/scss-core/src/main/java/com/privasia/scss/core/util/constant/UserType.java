/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum UserType {
	
	INTERNAL("I"), EXTERNAL("E");
	
	
	private final String userType;

    private UserType(String userType) {
        this.userType = userType;
    }

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	
	

}
