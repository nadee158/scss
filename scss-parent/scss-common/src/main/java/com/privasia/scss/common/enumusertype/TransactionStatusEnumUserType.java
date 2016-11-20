/**
 * 
 */
package com.privasia.scss.common.enumusertype;

import java.io.Serializable;

import com.privasia.scss.common.enums.TransactionStatus;

/**
 * @author Janaka
 *
 */
public class TransactionStatusEnumUserType extends GeneralEnumMapUserType<TransactionStatus> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructor.
     *
     */
	public TransactionStatusEnumUserType() {
        super(TransactionStatus.class);
    }

}
