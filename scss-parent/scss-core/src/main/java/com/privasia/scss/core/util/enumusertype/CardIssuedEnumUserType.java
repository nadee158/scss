/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CardIssuedStatus;

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
