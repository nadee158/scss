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
public enum KioskHLTCheckStatus implements Enumable {

  OK("OK"), CARD_READER_DOWN("Card Reader Down"), PC_DOWN("PC Down"), INTERCOM_DOWN("Intercom Down"), PRINTER_DOWN(
      "Printer Down"), CAMERA_DOWN("Camera Down");

  private final String bookingType;

  private KioskHLTCheckStatus(String bookingType) {
    this.bookingType = bookingType;
  }

  /**
   * @return the bookingType
   */
  public String getValue() {
    return bookingType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }

  private static final Map<String, KioskHLTCheckStatus> LOOKUP = new HashMap<String, KioskHLTCheckStatus>();

  static {
    for (KioskHLTCheckStatus bookingType : EnumSet.allOf(KioskHLTCheckStatus.class)) {
      LOOKUP.put(bookingType.getValue(), bookingType);
    }
  }

  public static KioskHLTCheckStatus fromValue(String value) {
    return LOOKUP.get(value);
  }

}
