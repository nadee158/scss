/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.BillingStatus;

/**
 * @author Janaka
 *
 */
public class BillingStatusEnumUserType extends GeneralEnumMapUserType<BillingStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor.
     *
     */
	public BillingStatusEnumUserType() {
		super(BillingStatus.class);
	}

}
