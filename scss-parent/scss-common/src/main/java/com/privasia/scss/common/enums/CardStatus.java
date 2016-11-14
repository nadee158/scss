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
public enum CardStatus implements Enumable {

	ACTIVE("A"), BLACKLIST("B"), SUSPENDED("S"), TERMINATED("T"), EXPIRED("E"), NOT_ISSUED("N"), PENDING("D"), CREATED(
			"C"), UPDATED("U");

	private final String cardStatus;

	private CardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	/**
	 * @return the cardStatus
	 */
	public String getValue() {
		return cardStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CardStatus> LOOKUP = new HashMap<String, CardStatus>();

	private static final Map<String, CardStatus> LOOKUP_BY_NAME = new HashMap<String, CardStatus>();

	static {
		for (CardStatus cardStatus : EnumSet.allOf(CardStatus.class)) {
			LOOKUP.put(cardStatus.getValue(), cardStatus);
			LOOKUP_BY_NAME.put(cardStatus.name(), cardStatus);
		}
	}

	public static CardStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

	public static CardStatus fromName(String name) {
		return LOOKUP_BY_NAME.get(name);
	}

}
