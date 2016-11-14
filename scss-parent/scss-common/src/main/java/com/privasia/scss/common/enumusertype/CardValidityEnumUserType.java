/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardValidityType;

/**
 * @author Janaka
 *
 */
public class CardValidityEnumUserType extends GeneralEnumMapUserType<CardValidityType> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
     * Constructor.
     *
     */
	public CardValidityEnumUserType() {
		super(CardValidityType.class);
	}

}
