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
public enum TOSServiceType implements Enumable {

  OPUS("O"), COSMOS("C");

  private final String tosServiceType;

  private TOSServiceType(String tosServiceType) {
    this.tosServiceType = tosServiceType;
  }

  /**
   * @return the tosServiceType
   */
  public String getValue() {
    return tosServiceType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, TOSServiceType> LOOKUP = new HashMap<String, TOSServiceType>();

  static {
    for (TOSServiceType tosServiceType : EnumSet.allOf(TOSServiceType.class)) {
      LOOKUP.put(tosServiceType.getValue(), tosServiceType);
    }
  }

  public static TOSServiceType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
