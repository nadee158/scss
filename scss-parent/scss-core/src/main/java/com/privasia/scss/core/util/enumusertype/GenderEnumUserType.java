/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.Gender;

/**
 * @author Janaka
 *
 */
public class GenderEnumUserType extends GeneralEnumMapUserType<Gender> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public GenderEnumUserType() {
        super(Gender.class);
    }

}
