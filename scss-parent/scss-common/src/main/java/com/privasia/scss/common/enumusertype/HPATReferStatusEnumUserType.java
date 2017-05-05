/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.HpabReferStatus;

/**
 * @author Janaka
 *
 */
public class HPATReferStatusEnumUserType extends GeneralEnumMapUserType<HpabReferStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public HPATReferStatusEnumUserType() {
        super(HpabReferStatus.class);
    }

}
