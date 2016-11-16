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
public enum VesselStatus implements Enumable {

	ACTIVE("ACT"), RGS("RGS"), EXECUTE("EXE"), CANCEL("CAN");

	private final String vesselStatus;

	private VesselStatus(String vesselStatus) {
		this.vesselStatus = vesselStatus;
	}

	/**
	 * @return the vesselStatus
	 */
	public String getValue() {
		return vesselStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, VesselStatus> LOOKUP = new HashMap<String, VesselStatus>();

	static {
		for (VesselStatus vesselStatus : EnumSet.allOf(VesselStatus.class)) {
			LOOKUP.put(vesselStatus.getValue(), vesselStatus);
		}
	}

	public static VesselStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
