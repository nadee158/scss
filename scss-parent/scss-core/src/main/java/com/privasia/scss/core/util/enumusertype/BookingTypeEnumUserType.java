/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.BookingType;

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
