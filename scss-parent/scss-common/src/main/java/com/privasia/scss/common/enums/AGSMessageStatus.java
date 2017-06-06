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
public enum AGSMessageStatus implements Enumable {

	SEND("SEND"), RECEIVE("RECEIVE");

	private final String messageStatus;

	private AGSMessageStatus(String messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
	 * @return the messageStatus
	 */
	public String getValue() {
		return messageStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	public static AGSMessageStatus fromCode(String messageStatus) {
		return LOOKUP.get(messageStatus);
	}

	private static final Map<String, AGSMessageStatus> LOOKUP = new HashMap<String, AGSMessageStatus>();

	static {
		for (AGSMessageStatus messageStatus : EnumSet.allOf(AGSMessageStatus.class)) {
			LOOKUP.put(messageStatus.getValue(), messageStatus);
		}
	}

}
