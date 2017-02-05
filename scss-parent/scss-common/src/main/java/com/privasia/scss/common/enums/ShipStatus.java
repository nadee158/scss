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
public enum ShipStatus implements Enumable {

  // only A is available in DB - assumed it stands for Active
  ACTIVE("A");

  private final String status;

  private ShipStatus(String comType) {
    this.status = comType;
  }

  /**
   * @return the status
   */
  public String getValue() {
    return status;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ShipStatus> LOOKUP = new HashMap<String, ShipStatus>();

  private static final Map<String, ShipStatus> LOOKUP_BY_NAME = new HashMap<String, ShipStatus>();

  static {
    for (ShipStatus shipStatus : EnumSet.allOf(ShipStatus.class)) {
      LOOKUP.put(shipStatus.getValue(), shipStatus);
      LOOKUP_BY_NAME.put(shipStatus.name(), shipStatus);
    }
  }

  public static ShipStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

  public static ShipStatus fromName(String name) {
    return LOOKUP_BY_NAME.get(name);
  }

}
