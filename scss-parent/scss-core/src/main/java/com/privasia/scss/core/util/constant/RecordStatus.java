/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum RecordStatus implements Enumable {
	
	ACTIVE("A"), INACTIVE("I");
	
	
	private final String recordStatus;

    private RecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

	/**
	 * @return the recordStatus
	 */
	public String getValue() {
		return recordStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
	
    

}
