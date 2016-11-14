/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum CompanyAccountType implements Enumable {
	
	LEDGER("LEDGER"), CREDIT("CREDIT"), CASH("CASH"), CHEQUE("CHEQUE");
	
	
	private final String accType;

    private CompanyAccountType(String accType) {
        this.accType = accType;
    }

	/**
	 * @return the accType
	 */
	public String getValue() {
		return accType;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
