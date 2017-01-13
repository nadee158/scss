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
public enum LaneOpenFlag implements Enumable {

  READY("R"), OPENED("O");

  private final String laneOpenFlag;

  private LaneOpenFlag(String laneOpenFlag) {
    this.laneOpenFlag = laneOpenFlag;
  }

  /**
   * @return the laneOpenFlag
   */
  public String getValue() {
    return laneOpenFlag;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, LaneOpenFlag> LOOKUP = new HashMap<String, LaneOpenFlag>();

  static {
    for (LaneOpenFlag laneOpenFlag : EnumSet.allOf(LaneOpenFlag.class)) {
      LOOKUP.put(laneOpenFlag.getValue(), laneOpenFlag);
    }
  }

  public static LaneOpenFlag fromValue(String value) {
    return LOOKUP.get(value);
  }

}
