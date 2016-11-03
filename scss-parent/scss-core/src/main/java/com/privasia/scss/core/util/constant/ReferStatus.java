/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum ReferStatus implements Enumable {
	
	REJECT("R");
	
	private final String referStatus;

    private ReferStatus(String referStatus) {
        this.referStatus = referStatus;
    }

	/**
	 * @return the referStatus
	 */
	public String getValue() {
		return referStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

	
}
