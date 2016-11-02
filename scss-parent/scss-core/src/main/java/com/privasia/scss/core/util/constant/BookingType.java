/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum BookingType {
	
	ER("ER"), EP("EP"), EXPORT("E"), IMPORT("I"), IMPORT_ITT("ITT");
	
	private final String bookingType;

    private BookingType(String bookingType) {
        this.bookingType = bookingType;
    }

	/**
	 * @return the bookingType
	 */
	public String getBookingType() {
		return bookingType;
	}

}
