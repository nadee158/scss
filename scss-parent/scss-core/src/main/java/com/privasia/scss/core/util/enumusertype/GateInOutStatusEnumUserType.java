/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.GateInOutStatus;

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
