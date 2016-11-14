/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CompanyType;

/**
 * @author Janaka
 *
 */
public class CompanyTypeEnumUserType extends GeneralEnumMapUserType<CompanyType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor.
     *
     */
	public CompanyTypeEnumUserType() {
		super(CompanyType.class);
	}

}
