/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CardValidityType;

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
