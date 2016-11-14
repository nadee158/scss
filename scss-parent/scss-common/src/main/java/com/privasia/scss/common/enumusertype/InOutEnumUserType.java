/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.InOutType;

/**
 * @author Janaka
 *
 */
public class InOutEnumUserType extends GeneralEnumMapUserType<InOutType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public InOutEnumUserType() {
        super(InOutType.class);
    }

}
