/**
 * 
 */
package com.privasia.scss.core.util.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;


/**
 * @author Janaka
 *
 */
public enum Nationality implements Enumable {

	MALAYSIAN("MY"), INDIAN("IN");

	private final String nationality;

	private Nationality(String nationality) {
		this.nationality = nationality;
	}

	/**
	 * @return the nationality
	 */
	public String getValue() {
		return nationality;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, Nationality> LOOKUP = new HashMap<String, Nationality>();
	
	private static final Map<String, Nationality> LOOKUP_BY_NAME = new HashMap<String, Nationality>();

	static {
		for (Nationality nationality : EnumSet.allOf(Nationality.class)) {
			LOOKUP.put(nationality.getValue(), nationality);
			LOOKUP_BY_NAME.put(nationality.name(), nationality);
		}
	}

	public static Nationality fromValue(String value) {
		return LOOKUP.get(value);
	}
	
	public static Nationality fromName(String name) {
		return LOOKUP_BY_NAME.get(name);
	}

}
