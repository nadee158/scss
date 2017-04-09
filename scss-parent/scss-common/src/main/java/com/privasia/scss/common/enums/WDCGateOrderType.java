/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum WDCGateOrderType implements Enumable {

  IMPORT("I"), IMPORT_CHARGABLE("J"), IMPORT_NON_CHARGABLE("K");

  private final String gateOrderType;

  private WDCGateOrderType(String gateOrderType) {
    this.gateOrderType = gateOrderType;
  }

  /**
   * @return the gateOrderType
   */
  public String getValue() {
    return gateOrderType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, WDCGateOrderType> LOOKUP = new HashMap<String, WDCGateOrderType>();

  static {
    for (WDCGateOrderType gateOrderType : EnumSet.allOf(WDCGateOrderType.class)) {
      LOOKUP.put(gateOrderType.getValue(), gateOrderType);
    }
  }

  public static WDCGateOrderType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
