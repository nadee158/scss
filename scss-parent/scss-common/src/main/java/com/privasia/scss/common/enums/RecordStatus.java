/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

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
    @JsonValue
	public String getValue() {
		return recordStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
	
	
	public static RecordStatus fromCode(String recordStatus) {
	    return LOOKUP.get(recordStatus);
	}


	private static final Map<String, RecordStatus> LOOKUP = new HashMap<String, RecordStatus>();

	static {
	   for (RecordStatus recordStatus : EnumSet.allOf(RecordStatus.class)) {
	     LOOKUP.put(recordStatus.getValue(), recordStatus);
	   }
	}
    

}
