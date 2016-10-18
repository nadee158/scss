/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CompanyAccountType {
	
	LEDGER("LEDGER"), CREDIT("CREDIT"), CASH("CASH"), CHEQUE("CHEQUE");
	
	
	private final String accType;

    private CompanyAccountType(String accType) {
        this.accType = accType;
    }

	/**
	 * @return the accType
	 */
	public String getAccType() {
		return accType;
	}

}
