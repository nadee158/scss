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
public enum SealType implements Enumable {

  SL("SL");

  private final String sealType;

  private SealType(String sealType) {
    this.sealType = sealType;
  }

  /**
   * @return the sealType
   */
  public String getValue() {
    return sealType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, SealType> LOOKUP = new HashMap<String, SealType>();

  static {
    for (SealType sealType : EnumSet.allOf(SealType.class)) {
      LOOKUP.put(sealType.getValue(), sealType);
    }
  }

  public static SealType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
