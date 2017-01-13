/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.LaneOpenFlag;

/**
 * @author Janaka
 *
 */
public class LaneOpenEnumUserType extends GeneralEnumMapUserType<LaneOpenFlag> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public LaneOpenEnumUserType() {
        super(LaneOpenFlag.class);
    }

}
