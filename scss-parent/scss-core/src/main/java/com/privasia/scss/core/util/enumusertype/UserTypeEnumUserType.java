/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.UserType;

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
