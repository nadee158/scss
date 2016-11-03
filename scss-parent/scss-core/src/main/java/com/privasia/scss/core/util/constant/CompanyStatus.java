/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum CompanyStatus implements Enumable {
	
	ACTIVE("A"), TERMINATED("T"), SUSPENDED("S"), CREATED("C"), UPDATED("U");
	
	
	private final String comStatus;

    private CompanyStatus(String comStatus) {
        this.comStatus = comStatus;
    }
    

	/**
	 * @return the comStatus
	 */
	public String getValue() {
		return comStatus;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
	

}
