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
public enum SolasWeightTypeSize implements Enumable {

  SIZE_0(0), SIZE_20(20), SIZE_40(40), SIZE_45(45);

  private final int solasWeightTypeSize;

  private SolasWeightTypeSize(int solasWeightTypeSize) {
    this.solasWeightTypeSize = solasWeightTypeSize;
  }

  /**
   * @return the solasWeightTypeSize
   */
  public String getValue() {
    return String.valueOf(solasWeightTypeSize);
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<Integer, SolasWeightTypeSize> LOOKUP = new HashMap<Integer, SolasWeightTypeSize>();

  static {
    for (SolasWeightTypeSize solasWeightTypeSize : EnumSet.allOf(SolasWeightTypeSize.class)) {
      LOOKUP.put(Integer.parseInt(solasWeightTypeSize.getValue()), solasWeightTypeSize);
    }
  }

  public static SolasWeightTypeSize fromValue(int value) {
    return LOOKUP.get(value);
  }



}
