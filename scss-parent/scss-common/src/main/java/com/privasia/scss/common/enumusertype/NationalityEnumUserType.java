/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.Nationality;

/**
 * @author Janaka
 *
 */
public class NationalityEnumUserType extends GeneralEnumMapUserType<Nationality> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public NationalityEnumUserType() {
        super(Nationality.class);
    }

}
