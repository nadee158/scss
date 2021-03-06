/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.UserStatus;

/**
 * @author Janaka
 *
 */
public class UserStatusEnumUserType extends GeneralEnumMapUserType<UserStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public UserStatusEnumUserType() {
        super(UserStatus.class);
    }

}
