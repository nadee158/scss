/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum HDBSStatus implements Enumable {

	CANCELLED("CANCELLED"), ACCEPTED("ACCEPTED"), DELETE("DELETE"), REJECTED("REJECTED"), NEW("NEW"), APPROVED(
			"APPROVED"), COMPLETED("COMPLETED");

	private final String hdbsStatus;

	private HDBSStatus(String hdbsStatus) {
		this.hdbsStatus = hdbsStatus;
	}

	/**
	 * @return the hdbsStatus
	 */
	public String getValue() {
		return hdbsStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, HDBSStatus> LOOKUP = new HashMap<String, HDBSStatus>();

	static {
		for (HDBSStatus hdbsStatus : EnumSet.allOf(HDBSStatus.class)) {
			LOOKUP.put(hdbsStatus.getValue(), hdbsStatus);
		}
	}

	public static HDBSStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
