/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.UserType;

/**
 * @author Janaka
 *
 */
public class UserTypeEnumUserType extends GeneralEnumMapUserType<UserType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public UserTypeEnumUserType() {
        super(UserType.class);
    }

}
