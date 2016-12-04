/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Janaka
 *
 */
public enum KioskHLTCheckStatus implements Enumable {

  OK("OK"), CARD_READER_DOWN("Card Reader Down"), PC_DOWN("PC Down"), INTERCOM_DOWN("Intercom Down"), PRINTER_DOWN(
      "Printer Down"), CAMERA_DOWN("Camera Down");

  private final String kioskHLTStatus;

  private KioskHLTCheckStatus(String kioskHLTStatus) {
    this.kioskHLTStatus = kioskHLTStatus;
  }

  /**
   * @return the kioskHLTStatus
   */
  @JsonValue
  public String getValue() {
    return kioskHLTStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, KioskHLTCheckStatus> LOOKUP = new HashMap<String, KioskHLTCheckStatus>();

  static {
    for (KioskHLTCheckStatus kioskHLTStatus : EnumSet.allOf(KioskHLTCheckStatus.class)) {
      LOOKUP.put(kioskHLTStatus.getValue(), kioskHLTStatus);
    }
  }

  public static KioskHLTCheckStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

}
