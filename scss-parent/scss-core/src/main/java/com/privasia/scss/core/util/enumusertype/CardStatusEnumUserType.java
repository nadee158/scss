/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CardStatus;

/**
 * @author Janaka
 *
 */
public class CardStatusEnumUserType extends GeneralEnumMapUserType<CardStatus> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor.
     *
     */
	public CardStatusEnumUserType() {
        super(CardStatus.class);
    }

}
