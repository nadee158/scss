/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.HDBSBookingType;

/**
 * @author Janaka
 *
 */
public class HDBSBookingEnumUserType extends GeneralEnumMapUserType<HDBSBookingType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public HDBSBookingEnumUserType() {
        super(HDBSBookingType.class);
    }

}
