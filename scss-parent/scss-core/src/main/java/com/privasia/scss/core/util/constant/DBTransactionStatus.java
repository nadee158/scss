package com.privasia.scss.core.util.constant;

public enum DBTransactionStatus {

  LOCK("L"), RESET("R"), ACTIVE("A"), COMPLETE("C");

  private final String transactionStatus;

  private DBTransactionStatus(String transactionStatus) {
    this.transactionStatus = transactionStatus;
  }

  /**
   * @return the transactionStatus
   */
  public String getDBTransactionStatus() {
    return transactionStatus;
  }


}
