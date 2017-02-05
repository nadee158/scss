/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CustomLaneStatus;

/**
 * @author Janaka
 *
 */
public class CustomLaneStatusEnumUserType extends GeneralEnumMapUserType<CustomLaneStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CustomLaneStatusEnumUserType() {
        super(CustomLaneStatus.class);
    }

}
