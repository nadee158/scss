/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.SCSSHDBSStatus;

/**
 * @author Janaka
 *
 */
public class SCSSHDBSStatusEnumUserType extends GeneralEnumMapUserType<SCSSHDBSStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public SCSSHDBSStatusEnumUserType() {
        super(SCSSHDBSStatus.class);
    }

}
