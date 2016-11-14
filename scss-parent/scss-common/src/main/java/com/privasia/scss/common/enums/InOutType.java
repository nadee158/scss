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
public enum InOutType implements Enumable {

  IN("ER"), OUT("EP");

  private final String inOutType;

  private InOutType(String inOutType) {
    this.inOutType = inOutType;
  }

  /**
   * @return the inOutType
   */
  public String getValue() {
    return inOutType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, InOutType> LOOKUP = new HashMap<String, InOutType>();

  static {
    for (InOutType inOutType : EnumSet.allOf(InOutType.class)) {
      LOOKUP.put(inOutType.getValue(), inOutType);
    }
  }

  public static InOutType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
