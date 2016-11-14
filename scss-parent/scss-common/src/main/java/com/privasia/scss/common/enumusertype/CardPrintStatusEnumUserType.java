/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.CardPrintStatus;

/**
 * @author Janaka
 *
 */
public class CardPrintStatusEnumUserType extends GeneralEnumMapUserType<CardPrintStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public CardPrintStatusEnumUserType() {
		super(CardPrintStatus.class);
	}

}
