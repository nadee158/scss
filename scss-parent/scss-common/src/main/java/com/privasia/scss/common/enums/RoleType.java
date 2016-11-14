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
public enum RoleType implements Enumable {

  ROLE_TYPE_INTERNAL("I"), ROLE_TYPE_EXTERNAL("E");

  private final String roleType;

  private RoleType(String roleType) {
    this.roleType = roleType;
  }

  /**
   * @return the roleType
   */
  public String getValue() {
    return roleType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, RoleType> LOOKUP = new HashMap<String, RoleType>();

  static {
    for (RoleType roleType : EnumSet.allOf(RoleType.class)) {
      LOOKUP.put(roleType.getValue(), roleType);
    }
  }

  public static RoleType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
