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
public enum ContainerPosition implements Enumable {

  FRONT("F"), AFTER("A"), MIDDLE("M");

  private final String position;

  private ContainerPosition(String position) {
    this.position = position;
  }

  /**
   * @return the position
   */
  public String getValue() {
    return position;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ContainerPosition> LOOKUP = new HashMap<String, ContainerPosition>();

  static {
    for (ContainerPosition containerPosition : EnumSet.allOf(ContainerPosition.class)) {
      LOOKUP.put(containerPosition.getValue(), containerPosition);
    }
  }

  public static ContainerPosition fromValue(String value) {
    return LOOKUP.get(value);
  }

}
