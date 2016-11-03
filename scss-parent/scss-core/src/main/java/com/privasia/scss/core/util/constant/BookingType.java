/**
 * 
 */
package com.privasia.scss.core.util.constant;


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

}
