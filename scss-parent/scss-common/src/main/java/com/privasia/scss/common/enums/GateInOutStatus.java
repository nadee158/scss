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
public enum GateInOutStatus implements Enumable {

  IN("IN"), OUT("OUT");

  private final String gateInOutStatus;

  private GateInOutStatus(String gateInOutStatus) {
    this.gateInOutStatus = gateInOutStatus;
  }

  /**
   * @return the gateInOutStatus
   */
  public String getValue() {
    return gateInOutStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, GateInOutStatus> LOOKUP = new HashMap<String, GateInOutStatus>();

  static {
    for (GateInOutStatus gateInOutStatus : EnumSet.allOf(GateInOutStatus.class)) {
      LOOKUP.put(gateInOutStatus.getValue(), gateInOutStatus);
    }
  }

  public static GateInOutStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

}
