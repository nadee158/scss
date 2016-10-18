package com.privasia.scss.core.util.constant;

public enum ImpExpFlagStatus {
	
	IMPORT("I"), EXPORT("E"), IMPORT_EXPORT("IE");
	
	private final String impExpFlagStatus;

    private ImpExpFlagStatus(String impExpFlagStatus) {
        this.impExpFlagStatus = impExpFlagStatus;
    }

	/**
	 * @return the impExpFlagStatus
	 */
	public String getImpExpFlagStatus() {
		return impExpFlagStatus;
	}

	

	
}
