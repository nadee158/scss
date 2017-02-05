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
public enum CustomLaneStatus implements Enumable {

	PENDING("P"), CHECK("C");

	private final String laneStatus;

	private CustomLaneStatus(String laneStatus) {
		this.laneStatus = laneStatus;
	}

	/**
	 * @return the laneStatus
	 */
	public String getValue() {
		return laneStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CustomLaneStatus> LOOKUP = new HashMap<String, CustomLaneStatus>();

	static {
		for (CustomLaneStatus laneStatus : EnumSet.allOf(CustomLaneStatus.class)) {
			LOOKUP.put(laneStatus.getValue(), laneStatus);
		}
	}

	public static CustomLaneStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
