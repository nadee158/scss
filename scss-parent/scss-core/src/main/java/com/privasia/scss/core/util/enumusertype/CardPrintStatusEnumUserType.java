/**
 * 
 */
package com.privasia.scss.core.util.enumusertype;

import java.io.Serializable;

import com.privasia.scss.core.util.constant.CardPrintStatus;

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
