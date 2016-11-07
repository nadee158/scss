/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.ReferStatus;

/**
 * @author Janaka
 *
 */
public class ReferStatusEnumUserType extends GeneralEnumMapUserType<ReferStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public ReferStatusEnumUserType() {
        super(ReferStatus.class);
    }

}
