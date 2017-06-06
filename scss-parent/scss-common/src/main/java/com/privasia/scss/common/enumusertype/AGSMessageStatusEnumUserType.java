/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.AGSMessageStatus;

/**
 * @author Janaka
 *
 */
public class AGSMessageStatusEnumUserType extends GeneralEnumMapUserType<AGSMessageStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor.
     *
     */
	public AGSMessageStatusEnumUserType() {
		super(AGSMessageStatus.class);
	}

}
