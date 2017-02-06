/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.ShippingLineReportType;

/**
 * @author Janaka
 *
 */
public class ShippingLineReportEnumUserType extends GeneralEnumMapUserType<ShippingLineReportType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public ShippingLineReportEnumUserType() {
        super(ShippingLineReportType.class);
    }

}
