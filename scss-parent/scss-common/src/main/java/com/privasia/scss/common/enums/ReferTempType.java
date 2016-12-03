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
public enum ReferTempType implements Enumable {

  C("C"), F("F");

  private final String referTempType;

  private ReferTempType(String referTempType) {
    this.referTempType = referTempType;
  }

  /**
   * @return the referTempType
   */
  public String getValue() {
    return referTempType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ReferTempType> LOOKUP = new HashMap<String, ReferTempType>();

  static {
    for (ReferTempType referTempType : EnumSet.allOf(ReferTempType.class)) {
      LOOKUP.put(referTempType.getValue(), referTempType);
    }
  }

  public static ReferTempType fromValue(String value) {
    return LOOKUP.get(value);
  }


}
