/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.GatePassStatus;

/**
 * @author Janaka
 *
 */
public class GatePassStatusEnumUserType extends GeneralEnumMapUserType<GatePassStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public GatePassStatusEnumUserType() {
        super(GatePassStatus.class);
    }

}
