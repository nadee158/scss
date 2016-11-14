package com.privasia.scss.core.util.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

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

  private static final Map<String, ImpExpFlagStatus> LOOKUP = new HashMap<String, ImpExpFlagStatus>();

  static {
    for (ImpExpFlagStatus impExpFlagStatus : EnumSet.allOf(ImpExpFlagStatus.class)) {
      LOOKUP.put(impExpFlagStatus.getValue(), impExpFlagStatus);
    }
  }

  public static ImpExpFlagStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

}
