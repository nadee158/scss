/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum GatePassStatus implements Enumable {
	
	ACTIVE("A"), CANCEL("C");
	
	private final String gatePassStatus;

    private GatePassStatus(String gatePassStatus) {
        this.gatePassStatus = gatePassStatus;
    }

	/**
	 * @return the gatePassStatus
	 */
	public String getValue() {
		return gatePassStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
