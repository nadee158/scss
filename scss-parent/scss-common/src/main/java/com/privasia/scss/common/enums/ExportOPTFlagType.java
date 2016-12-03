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
public enum ExportOPTFlagType implements Enumable {

  OPTFLAG_NORMAL("N"), OPTFLAG_MANUAL("M");

  private final String optFlagType;

  private ExportOPTFlagType(String optFlagType) {
    this.optFlagType = optFlagType;
  }

  /**
   * @return the optFlagType
   */
  public String getValue() {
    return optFlagType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ExportOPTFlagType> LOOKUP = new HashMap<String, ExportOPTFlagType>();

  static {
    for (ExportOPTFlagType optFlagType : EnumSet.allOf(ExportOPTFlagType.class)) {
      LOOKUP.put(optFlagType.getValue(), optFlagType);
    }
  }

  public static ExportOPTFlagType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
