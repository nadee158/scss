/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.HDBSArrivalStatus;

/**
 * @author Janaka
 *
 */
public class HDBSArrivalStatusEnumUserType extends GeneralEnumMapUserType<HDBSArrivalStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public HDBSArrivalStatusEnumUserType() {
        super(HDBSArrivalStatus.class);
    }

}
