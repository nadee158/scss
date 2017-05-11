package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum LpkClassType implements Enumable {

	LPK_CLASS1("1"), LPK_CLASS2("2"), LPK_CLASS3("3"), LPK_CLASS4("4");

	private final String lpkClass;

	private LpkClassType(String lpkClass) {
		this.lpkClass = lpkClass;
	}

	/**
	 * @return the lpkClass
	 */
	public String getValue() {
		return lpkClass;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, LpkClassType> LOOKUP = new HashMap<String, LpkClassType>();

	static {
		for (LpkClassType classType : EnumSet.allOf(LpkClassType.class)) {
			LOOKUP.put(classType.getValue(), classType);
		}
	}

	public static LpkClassType fromValue(String value) {
		return LOOKUP.get(value);
	}
}
