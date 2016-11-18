/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.HDBSStatus;

/**
 * @author Janaka
 *
 */
public class HDBSStatusEnumUserType extends GeneralEnumMapUserType<HDBSStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public HDBSStatusEnumUserType() {
        super(HDBSStatus.class);
    }

}
