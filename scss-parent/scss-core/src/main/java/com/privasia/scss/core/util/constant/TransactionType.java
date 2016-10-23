package com.privasia.scss.core.util.constant;

public enum TransactionType {
	
	IMPORT("I"), EXPORT("E"), IMPORT_EXPORT("IE"), ODD_EXPORT("OE"), ODD_IMPORT("OI"), ODD_IMPORT_EXPORT("OIE");
	
	private final String transactionType;

    private TransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

	/**
	 * @return the transactionType
	 */
	public String getTransactionType() {
		return transactionType;
	}

	
	
}
