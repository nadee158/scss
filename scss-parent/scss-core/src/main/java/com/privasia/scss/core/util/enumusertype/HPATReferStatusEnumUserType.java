/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.HpatReferStatus;

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
