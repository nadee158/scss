package com.privasia.scss.core.util.constant;

public enum TransactionStatus {
	
	INPROGRESS("I"), APPROVED("A"), REJECT("R");
	
	private final String transactionStatus;

    private TransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

	/**
	 * @return the transactionStatus
	 */
	public String getTransactionStatus() {
		return transactionStatus;
	}

	
}
