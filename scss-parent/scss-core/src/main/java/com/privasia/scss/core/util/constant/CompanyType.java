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
public enum CompanyType implements Enumable {

	HAULAGE("H"), PORT_USER("P"), TRANSPORTER("T"), GOVERNMENT_AGENCY("A"), FOREIGN_WORKER("N"), FORWARDING(
			"F"), GENERAL("G"), WESTPORT_STAFF("W");

	private final String comType;

	private CompanyType(String comType) {
		this.comType = comType;
	}

	/**
	 * @return the comType
	 */
	public String getValue() {
		return comType;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CompanyType> LOOKUP = new HashMap<String, CompanyType>();

	private static final Map<String, CompanyType> LOOKUP_BY_NAME = new HashMap<String, CompanyType>();

	static {
		for (CompanyType companyType : EnumSet.allOf(CompanyType.class)) {
			LOOKUP.put(companyType.getValue(), companyType);
			LOOKUP_BY_NAME.put(companyType.name(), companyType);
		}
	}

	public static CompanyType fromValue(String value) {
		return LOOKUP.get(value);
	}

	public static CompanyType fromName(String name) {
		return LOOKUP_BY_NAME.get(name);
	}

}
