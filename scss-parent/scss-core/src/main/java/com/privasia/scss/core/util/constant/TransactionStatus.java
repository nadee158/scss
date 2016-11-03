package com.privasia.scss.core.util.constant;


public enum TransactionStatus implements Enumable {
	
	INPROGRESS("I"), APPROVED("A"), REJECT("R");
	
	private final String transactionStatus;

    private TransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

	/**
	 * @return the transactionStatus
	 */
	public String getValue() {
		return transactionStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

	
}
