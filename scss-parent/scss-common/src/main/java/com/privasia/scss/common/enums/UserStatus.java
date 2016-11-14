/**
 * 
 */
package com.privasia.scss.common.enums;


/**
 * @author Janaka
 *
 */
public enum UserStatus implements Enumable {
	
	TERMINATED("T"), ACTIVE("A"), SUSPENDED("S");
	
	
	private final String userStatus;

    private UserStatus(String userStatus) {
        this.userStatus = userStatus;
    }

	/**
	 * @return the userStatus
	 */
	public String getValue() {
		return userStatus;
	}
	
	
	public Enum<?> getEnumFromValue(String value) {
        return EnumableHelper.getEnumFromValue(this, value, null);
    }
	
	

}
