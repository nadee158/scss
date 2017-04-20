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
public enum SSRBlockType implements Enumable {

  BLK("BLK"), RLS("RLS");

  private final String ssrBlockType;

  private SSRBlockType(String ssrBlockType) {
    this.ssrBlockType = ssrBlockType;
  }

  /**
   * @return the ssrBlockType
   */
  public String getValue() {
    return ssrBlockType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, SSRBlockType> LOOKUP = new HashMap<String, SSRBlockType>();

  static {
    for (SSRBlockType ssrBlockType : EnumSet.allOf(SSRBlockType.class)) {
      LOOKUP.put(ssrBlockType.getValue(), ssrBlockType);
    }
  }

  public static SSRBlockType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
