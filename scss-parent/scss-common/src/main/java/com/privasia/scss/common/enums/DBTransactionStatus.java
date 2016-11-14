package com.privasia.scss.common.enums;


public enum DBTransactionStatus implements Enumable {

  LOCK("L"), RESET("R"), ACTIVE("A"), COMPLETE("C");

  private final String transactionStatus;

  private DBTransactionStatus(String transactionStatus) {
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
