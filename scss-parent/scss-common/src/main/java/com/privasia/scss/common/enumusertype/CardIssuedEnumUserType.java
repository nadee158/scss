/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardIssuedStatus;

/**
 * @author Janaka
 *
 */
public class CardIssuedEnumUserType extends GeneralEnumMapUserType<CardIssuedStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CardIssuedEnumUserType() {
        super(CardIssuedStatus.class);
    }

}
