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
public enum HDBSArrivalStatus implements Enumable {

	LATE("LATE"), EARLY("EARLY"), ACTIVE("ACTIVE");

	private final String hdbsArrivalStatus;

	private HDBSArrivalStatus(String hdbsArrivalStatus) {
		this.hdbsArrivalStatus = hdbsArrivalStatus;
	}

	/**
	 * @return the hdbsArrivalStatus
	 */
	public String getValue() {
		return hdbsArrivalStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, HDBSArrivalStatus> LOOKUP = new HashMap<String, HDBSArrivalStatus>();

	static {
		for (HDBSArrivalStatus hdbsArrivalStatus : EnumSet.allOf(HDBSArrivalStatus.class)) {
			LOOKUP.put(hdbsArrivalStatus.getValue(), hdbsArrivalStatus);
		}
	}

	public static HDBSArrivalStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
