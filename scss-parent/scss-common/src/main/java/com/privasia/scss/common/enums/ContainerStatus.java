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
public enum ContainerStatus implements Enumable {

	EXECUTE("EXE"), NON_EXECUTE("NEXE");

	private final String containerStatus;

	private ContainerStatus(String containerStatus) {
		this.containerStatus = containerStatus;
	}

	/**
	 * @return the containerStatus
	 */
	public String getValue() {
		return containerStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, ContainerStatus> LOOKUP = new HashMap<String, ContainerStatus>();

	static {
		for (ContainerStatus containerStatus : EnumSet.allOf(ContainerStatus.class)) {
			LOOKUP.put(containerStatus.getValue(), containerStatus);
		}
	}

	public static ContainerStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
