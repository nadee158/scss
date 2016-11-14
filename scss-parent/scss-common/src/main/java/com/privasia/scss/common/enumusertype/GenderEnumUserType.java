/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.Gender;

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
