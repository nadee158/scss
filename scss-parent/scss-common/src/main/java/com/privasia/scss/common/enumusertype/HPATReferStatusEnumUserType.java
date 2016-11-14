/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.HpatReferStatus;

/**
 * @author Janaka
 *
 */
public class HPATReferStatusEnumUserType extends GeneralEnumMapUserType<HpatReferStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public HPATReferStatusEnumUserType() {
        super(HpatReferStatus.class);
    }

}
