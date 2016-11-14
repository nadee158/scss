/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.GateInOutStatus;

/**
 * @author Janaka
 *
 */
public class GateInOutStatusEnumUserType extends GeneralEnumMapUserType<GateInOutStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public GateInOutStatusEnumUserType() {
        super(GateInOutStatus.class);
    }

}
