/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum UserStatus {
	
	TERMINATED("T"), ACTIVE("A"), SUSPENDED("S");
	
	
	private final String userStatus;

    private UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}

	
	

}
