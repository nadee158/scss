/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CompanyType;

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
