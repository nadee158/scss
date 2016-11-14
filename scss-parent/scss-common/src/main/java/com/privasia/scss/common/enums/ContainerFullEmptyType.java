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
public enum ContainerFullEmptyType implements Enumable {

  EMPTY("E"), FULL("F");

  private final String fullEmptyType;

  private ContainerFullEmptyType(String fullEmptyType) {
    this.fullEmptyType = fullEmptyType;
  }

  /**
   * @return the fullEmptyType
   */
  public String getValue() {
    return fullEmptyType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ContainerFullEmptyType> LOOKUP = new HashMap<String, ContainerFullEmptyType>();

  static {
    for (ContainerFullEmptyType fullEmptyType : EnumSet.allOf(ContainerFullEmptyType.class)) {
      LOOKUP.put(fullEmptyType.getValue(), fullEmptyType);
    }
  }

  public static ContainerFullEmptyType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
