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
public enum BookingType implements Enumable {

  ER("ER"), EP("EP"), EXPORT("E"), IMPORT("I"), IMPORT_ITT("ITT");

  private final String bookingType;

  private BookingType(String bookingType) {
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

  private static final Map<String, BookingType> LOOKUP = new HashMap<String, BookingType>();

  static {
    for (BookingType bookingType : EnumSet.allOf(BookingType.class)) {
      LOOKUP.put(bookingType.getValue(), bookingType);
    }
  }

  public static BookingType fromValue(String value) {
    return LOOKUP.get(value);
  }

}
