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
public enum SolasWeightType implements Enumable {

  FUEL("FUEL"), TYRE("TYRE"), VARIANCE("VARIANCE"), PM("PM"), TRAILER("TRAILER"), MGW("MGW");

  private final String solasWeightType;

  private SolasWeightType(String solasWeightType) {
    this.solasWeightType = solasWeightType;
  }

  /**
   * @return the solasWeightType
   */
  public String getValue() {
    return solasWeightType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, SolasWeightType> LOOKUP = new HashMap<String, SolasWeightType>();

  static {
    for (SolasWeightType solasWeightType : EnumSet.allOf(SolasWeightType.class)) {
      LOOKUP.put(solasWeightType.getValue(), solasWeightType);
    }
  }

  public static SolasWeightType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
