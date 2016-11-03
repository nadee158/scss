/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum GateInOutStatus implements Enumable {
	
	IN("IN"), OUT("OUT");
	
	private final String gateInOutStatus;

    private GateInOutStatus(String gateInOutStatus) {
        this.gateInOutStatus = gateInOutStatus;
    }

	/**
	 * @return the gateInOutStatus
	 */
	public String getValue() {
		return gateInOutStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
