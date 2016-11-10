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
public enum CompanyStatus implements Enumable {

	ACTIVE("A"), TERMINATED("T"), SUSPENDED("S"), CREATED("C"), UPDATED("U");

	private final String comStatus;

	private CompanyStatus(String comStatus) {
		this.comStatus = comStatus;
	}

	/**
	 * @return the comStatus
	 */
	public String getValue() {
		return comStatus;
	}

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, CompanyStatus> LOOKUP = new HashMap<String, CompanyStatus>();

	private static final Map<String, CompanyStatus> LOOKUP_BY_NAME = new HashMap<String, CompanyStatus>();

	static {
		for (CompanyStatus companyStatus : EnumSet.allOf(CompanyStatus.class)) {
			LOOKUP.put(companyStatus.getValue(), companyStatus);
			LOOKUP_BY_NAME.put(companyStatus.name(), companyStatus);
		}
	}

	public static CompanyStatus fromValue(String value) {
		return LOOKUP.get(value);
	}

	public static CompanyStatus fromName(String name) {
		return LOOKUP_BY_NAME.get(name);
	}

}
