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
public enum HpabReferStatus implements Enumable {

  ACTIVE("ACTV"), CANCEL("CAN"), COMPLETE("COMP"), EXPIRED("EXPIRED");


  private final String hpatStatus;

  private HpabReferStatus(String hpatStatus) {
    this.hpatStatus = hpatStatus;
  }

  /**
   * @return the hpatStatus
   */
  public String getValue() {
    return hpatStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }


  public static HpabReferStatus fromCode(String hpatStatus) {
    return LOOKUP.get(hpatStatus);
  }


  private static final Map<String, HpabReferStatus> LOOKUP = new HashMap<String, HpabReferStatus>();

  static {
    for (HpabReferStatus hpabReferStatus : EnumSet.allOf(HpabReferStatus.class)) {
      LOOKUP.put(hpabReferStatus.getValue(), hpabReferStatus);
    }
  }



}
