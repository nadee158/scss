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
public enum ContainerSize implements Enumable {

  SIZE_0("0"), SIZE_20("20"), SIZE_40("40"), SIZE_45("45");

  private final String containerSize;

  private ContainerSize(String containerSize) {
    this.containerSize = containerSize;
  }

  /**
   * @return the containerSize
   */
  public String getValue() {
    return containerSize;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ContainerSize> LOOKUP = new HashMap<String, ContainerSize>();

  static {
    for (ContainerSize containerSize : EnumSet.allOf(ContainerSize.class)) {
      LOOKUP.put(containerSize.getValue(), containerSize);
    }
  }

  public static ContainerSize fromValue(String value) {
    return LOOKUP.get(value);
  }

}
