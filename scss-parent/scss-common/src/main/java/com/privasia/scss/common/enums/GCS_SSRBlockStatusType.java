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
public enum GCS_SSRBlockStatusType implements Enumable {

  BLK("BLK"), RLS("RLS");

  private final String gcsBlockStatus;

  private GCS_SSRBlockStatusType(String gcsBlockStatus) {
    this.gcsBlockStatus = gcsBlockStatus;
  }

  /**
   * @return the gcsBlockStatus
   */
  public String getValue() {
    return gcsBlockStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, GCS_SSRBlockStatusType> LOOKUP = new HashMap<String, GCS_SSRBlockStatusType>();

  static {
    for (GCS_SSRBlockStatusType gcsBlockStatusType : EnumSet.allOf(GCS_SSRBlockStatusType.class)) {
      LOOKUP.put(gcsBlockStatusType.getValue(), gcsBlockStatusType);
    }
  }

  public static GCS_SSRBlockStatusType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
