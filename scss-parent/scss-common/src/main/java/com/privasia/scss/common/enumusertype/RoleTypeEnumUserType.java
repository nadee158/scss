/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.RoleType;

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
