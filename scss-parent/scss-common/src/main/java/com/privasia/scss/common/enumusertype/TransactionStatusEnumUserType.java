/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.TransactionType;

/**
 * @author Janaka
 *
 */
public class TransactionStatusEnumUserType extends GeneralEnumMapUserType<TransactionType> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public TransactionStatusEnumUserType() {
        super(TransactionType.class);
    }

}
