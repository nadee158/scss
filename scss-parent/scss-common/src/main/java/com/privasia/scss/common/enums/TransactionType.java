package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TransactionType implements Enumable {

  IMPORT("I"), EXPORT("E"), IMPORT_EXPORT("IE"), ODD_EXPORT("OE"), ODD_IMPORT("OI"), ODD_IMPORT_EXPORT("OIE"), ODD("ODD");

  private final String transactionType;

  private TransactionType(String transactionType) {
    this.transactionType = transactionType;
  }

  /**
   * @return the transactionType
   */
  public String getValue() {
    return transactionType;
  }
  
  public Enum<?> getEnumFromValue(String value) {
      return EnumableHelper.getEnumFromValue(this, value, null);
  }

  public static TransactionType fromCode(String transactionType) {
    return LOOKUP.get(transactionType);
  }


  private static final Map<String, TransactionType> LOOKUP = new HashMap<String, TransactionType>();

  static {
    for (TransactionType transactionType : EnumSet.allOf(TransactionType.class)) {
      LOOKUP.put(transactionType.getValue(), transactionType);
    }
  }

  

}
