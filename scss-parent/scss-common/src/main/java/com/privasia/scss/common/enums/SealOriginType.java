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
public enum SealOriginType implements Enumable {

  L("L"), S("S");

  private final String sealOriginType;

  private SealOriginType(String sealOriginType) {
    this.sealOriginType = sealOriginType;
  }

  /**
   * @return the sealOriginType
   */

  public String getValue() {
    return sealOriginType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, SealOriginType> LOOKUP = new HashMap<String, SealOriginType>();

  static {
    for (SealOriginType sealOriginType : EnumSet.allOf(SealOriginType.class)) {
      LOOKUP.put(sealOriginType.getValue(), sealOriginType);
    }
  }

  public static SealOriginType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
