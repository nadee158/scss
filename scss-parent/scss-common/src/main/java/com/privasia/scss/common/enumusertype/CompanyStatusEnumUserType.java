/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CompanyStatus;

/**
 * @author Janaka
 *
 */
public class CompanyStatusEnumUserType extends GeneralEnumMapUserType<CompanyStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CompanyStatusEnumUserType() {
        super(CompanyStatus.class);
    }

}
