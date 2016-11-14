/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardStatus;

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
