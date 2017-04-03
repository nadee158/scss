/**
 * 
 */
package com.privasia.scss.common.dto;

import java.io.Serializable;

import com.privasia.scss.common.enums.TransactionType;

/**
 * @author Janaka
 *
 */
public class InProgressTrxDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TransactionType trxType;
	private boolean inProgress;
	
	public TransactionType getTrxType() {
		return trxType;
	}
	public void setTrxType(TransactionType trxType) {
		this.trxType = trxType;
	}
	public boolean isInProgress() {
		return inProgress;
	}
	public void setInProgress(boolean inProgress) {
		this.inProgress = inProgress;
	}
	
	
	
}
