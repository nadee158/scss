/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.Nationality;

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
