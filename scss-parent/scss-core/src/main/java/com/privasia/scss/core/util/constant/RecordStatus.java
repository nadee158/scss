/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum RecordStatus {
	
	ACTIVE("A"), INACTIVE("I");
	
	
	private final String recordStatus;

    private RecordStatus(String recordStatus) {
        this.recordStatus = recordStatus;
    }

	/**
	 * @return the recordStatus
	 */
	public String getRecordStatus() {
		return recordStatus;
	}

	
    

}
