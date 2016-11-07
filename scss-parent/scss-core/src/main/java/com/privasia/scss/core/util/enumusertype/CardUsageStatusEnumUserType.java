/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CardUsageStatus;

/**
 * @author Janaka
 *
 */
public class CardUsageStatusEnumUserType extends GeneralEnumMapUserType<CardUsageStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CardUsageStatusEnumUserType() {
        super(CardUsageStatus.class);
    }

}
