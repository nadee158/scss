package com.privasia.scss.core.util.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionStatus implements Enumable {

  NEW("N"), INPROGRESS("I"), APPROVED("A"), REJECT("R");

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

  public static TransactionStatus fromCode(String transactionStatus) {
    return LOOKUP.get(transactionStatus);
  }


  private static final Map<String, TransactionStatus> LOOKUP = new HashMap<String, TransactionStatus>();

  static {
    for (TransactionStatus transactionStatus : EnumSet.allOf(TransactionStatus.class)) {
      LOOKUP.put(transactionStatus.getValue(), transactionStatus);
    }
  }


}
