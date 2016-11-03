/**
 * 
 */
package com.privasia.scss.core.util.constant;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum KioskLockStatus implements Enumable {

  ACTIVE("A"), LOCK("L"), COMPLETE("C");

  private final String kioskLockStatus;

  private KioskLockStatus(String kioskLockStatus) {
    this.kioskLockStatus = kioskLockStatus;
  }

  /**
   * @return the kioskLockStatus
   */
  public String getValue() {
    return kioskLockStatus;
  }
  
  public Enum<?> getEnumFromValue(String value) {
      return EnumableHelper.getEnumFromValue(this, value, null);
  }

  public static KioskLockStatus fromCode(String kioskLockStatus) {
    return LOOKUP.get(kioskLockStatus);
  }


  private static final Map<String, KioskLockStatus> LOOKUP = new HashMap<String, KioskLockStatus>();

  static {
    for (KioskLockStatus kioskLockStatus : EnumSet.allOf(KioskLockStatus.class)) {
      LOOKUP.put(kioskLockStatus.getValue(), kioskLockStatus);
    }
  }



}
