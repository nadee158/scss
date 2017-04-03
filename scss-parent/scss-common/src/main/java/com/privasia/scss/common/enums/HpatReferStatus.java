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
public enum HpatReferStatus implements Enumable {

  ACTIVE("ACTV"), CANCEL("CAN"), COMPLETE("COMP"), EXPIRED("EXPIRED");


  private final String hpatStatus;

  private HpatReferStatus(String hpatStatus) {
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


  public static HpatReferStatus fromCode(String hpatStatus) {
    return LOOKUP.get(hpatStatus);
  }


  private static final Map<String, HpatReferStatus> LOOKUP = new HashMap<String, HpatReferStatus>();

  static {
    for (HpatReferStatus hpatReferStatus : EnumSet.allOf(HpatReferStatus.class)) {
      LOOKUP.put(hpatReferStatus.getValue(), hpatReferStatus);
    }
  }



}
