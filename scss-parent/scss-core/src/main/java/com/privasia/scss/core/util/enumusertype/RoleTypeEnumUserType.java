/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.RoleType;

/**
 * @author Janaka
 *
 */
public class RoleTypeEnumUserType extends GeneralEnumMapUserType<RoleType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public RoleTypeEnumUserType() {
        super(RoleType.class);
    }

}
