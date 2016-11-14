/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CompanyAccountType;

/**
 * @author Janaka
 *
 */
public class CompanyAccountTypeEnumUserType extends GeneralEnumMapUserType<CompanyAccountType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CompanyAccountTypeEnumUserType() {
        super(CompanyAccountType.class);
    }

}
