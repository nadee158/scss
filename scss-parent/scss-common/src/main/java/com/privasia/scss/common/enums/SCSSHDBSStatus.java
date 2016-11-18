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
public enum SCSSHDBSStatus implements Enumable {

	IN_PROGRESS("I"), ACCEPTED("A"), EXPIRE("EXP"), REJECTED("R");

	private final String scssHDBSStatus;

	private SCSSHDBSStatus(String scssHDBSStatus) {
		this.scssHDBSStatus = scssHDBSStatus;
	}

	/**
	 * @return the scssHDBSStatus
	 */
	public String getValue() {
		return scssHDBSStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, SCSSHDBSStatus> LOOKUP = new HashMap<String, SCSSHDBSStatus>();

	static {
		for (SCSSHDBSStatus scssHDBSStatus : EnumSet.allOf(SCSSHDBSStatus.class)) {
			LOOKUP.put(scssHDBSStatus.getValue(), scssHDBSStatus);
		}
	}

	public static SCSSHDBSStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
