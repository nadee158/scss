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
public enum HDBSBookingType implements Enumable {

	PICKUP("PICKUP"), DROP("DROP");

	private final String hdbsBookingType;

	private HDBSBookingType(String hdbsBookingType) {
		this.hdbsBookingType = hdbsBookingType;
	}

	/**
	 * @return the hdbsBookingType
	 */
	public String getValue() {
		return hdbsBookingType;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, HDBSBookingType> LOOKUP = new HashMap<String, HDBSBookingType>();

	static {
		for (HDBSBookingType hdbsBookingType : EnumSet.allOf(HDBSBookingType.class)) {
			LOOKUP.put(hdbsBookingType.getValue(), hdbsBookingType);
		}
	}

	public static HDBSBookingType fromValue(String value) {
		return LOOKUP.get(value);
	}

}
