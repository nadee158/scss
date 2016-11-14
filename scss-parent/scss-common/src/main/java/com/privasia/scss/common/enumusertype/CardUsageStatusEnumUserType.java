/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardUsageStatus;

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
