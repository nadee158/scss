/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.GatePassStatus;

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
