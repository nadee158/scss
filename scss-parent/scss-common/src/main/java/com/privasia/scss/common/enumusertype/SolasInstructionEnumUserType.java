/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.SolasInstructionType;

/**
 * @author Janaka
 *
 */
public class SolasInstructionEnumUserType extends GeneralEnumMapUserType<SolasInstructionType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public SolasInstructionEnumUserType() {
        super(SolasInstructionType.class);
    }

}
