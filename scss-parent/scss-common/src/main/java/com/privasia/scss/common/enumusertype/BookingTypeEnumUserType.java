/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.BookingType;

/**
 * @author Janaka
 *
 */
public class BookingTypeEnumUserType extends GeneralEnumMapUserType<BookingType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public BookingTypeEnumUserType() {
        super(BookingType.class);
    }

}
