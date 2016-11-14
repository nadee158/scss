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
public enum ReferStatus implements Enumable {

  REJECT("R"), NONE("");

  private final String referStatus;

  private ReferStatus(String referStatus) {
    this.referStatus = referStatus;
  }

  /**
   * @return the referStatus
   */
  public String getValue() {
    return referStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ReferStatus> LOOKUP = new HashMap<String, ReferStatus>();

  static {
    for (ReferStatus referStatus : EnumSet.allOf(ReferStatus.class)) {
      LOOKUP.put(referStatus.getValue(), referStatus);
    }
  }

  public static ReferStatus fromValue(String value) {
    return LOOKUP.get(value);
  }


}
