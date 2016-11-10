/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.InOutType;

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
