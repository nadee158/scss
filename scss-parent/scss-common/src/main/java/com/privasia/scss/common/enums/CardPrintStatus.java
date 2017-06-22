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
public enum CardPrintStatus implements Enumable {

	PRINTED("P");

	private final String cardPrintStatus;

	private CardPrintStatus(String cardPrintStatus) {
		this.cardPrintStatus = cardPrintStatus;
	}

	/**
	 * @return the cardPrintStatus
	 */
	public String getValue() {
		return cardPrintStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CardPrintStatus> LOOKUP = new HashMap<String, CardPrintStatus>();

	static {
		for (CardPrintStatus cardPrintStatus : EnumSet.allOf(CardPrintStatus.class)) {
			LOOKUP.put(cardPrintStatus.getValue(), cardPrintStatus);
		}
	}

	public static CardPrintStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

}
