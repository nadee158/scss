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
public enum ODDGroup implements Enumable {

	WAREHOUSE("WAREHOUSE"), ODD("ODD"), TRACK("TRACK"), COMMON("COMMON");

	private final String oddGroup;

	private ODDGroup(String oddGroup) {
		this.oddGroup = oddGroup;
	}

	/**
	 * @return the oddGroup
	 */
	public String getValue() {
		return oddGroup;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, ODDGroup> LOOKUP = new HashMap<String, ODDGroup>();

	static {
		for (ODDGroup oddGroup : EnumSet.allOf(ODDGroup.class)) {
			LOOKUP.put(oddGroup.getValue(), oddGroup);
		}
	}

	public static ODDGroup fromValue(String value) {
		return LOOKUP.get(value);
	}

}
