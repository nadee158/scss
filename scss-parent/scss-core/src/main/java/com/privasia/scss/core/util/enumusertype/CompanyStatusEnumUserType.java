/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CompanyStatus;

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
