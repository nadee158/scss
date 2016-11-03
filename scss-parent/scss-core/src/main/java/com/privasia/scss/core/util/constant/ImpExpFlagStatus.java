package com.privasia.scss.core.util.constant;

public enum ImpExpFlagStatus implements Enumable {
	
	IMPORT("I"), EXPORT("E"), IMPORT_EXPORT("IE");
	
	private final String impExpFlagStatus;

    private ImpExpFlagStatus(String impExpFlagStatus) {
        this.impExpFlagStatus = impExpFlagStatus;
    }

	/**
	 * @return the impExpFlagStatus
	 */
	public String getValue() {
		return impExpFlagStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
