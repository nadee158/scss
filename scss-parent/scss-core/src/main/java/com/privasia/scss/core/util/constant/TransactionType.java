package com.privasia.scss.core.util.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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

  public static TransactionType fromCode(String transactionType) {
    return LOOKUP.get(transactionType);
  }


  private static final Map<String, TransactionType> LOOKUP = new HashMap<String, TransactionType>();

  static {
    for (TransactionType transactionType : EnumSet.allOf(TransactionType.class)) {
      LOOKUP.put(transactionType.getTransactionType(), transactionType);
    }
  }



}
