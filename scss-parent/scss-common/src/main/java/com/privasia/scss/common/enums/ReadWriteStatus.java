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
public enum ReadWriteStatus implements Enumable {

  READ("R"), WRITE("W");

  private final String readWriteStatus;

  private ReadWriteStatus(String readWriteStatus) {
    this.readWriteStatus = readWriteStatus;
  }

  /**
   * @return the readWriteStatus
   */
  public String getValue() {
    return readWriteStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, ReadWriteStatus> LOOKUP = new HashMap<String, ReadWriteStatus>();

  static {
    for (ReadWriteStatus readWriteStatus : EnumSet.allOf(ReadWriteStatus.class)) {
      LOOKUP.put(readWriteStatus.getValue(), readWriteStatus);
    }
  }

  public static ReadWriteStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

}
