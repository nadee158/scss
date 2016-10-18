/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum CompanyStatus {
	
	ACTIVE("A"), TERMINATED("T"), SUSPENDED("S"), CREATED("C"), UPDATED("U");
	
	
	private final String comStatus;

    private CompanyStatus(String comStatus) {
        this.comStatus = comStatus;
    }
    

	/**
	 * @return the comStatus
	 */
	public String getComStatus() {
		return comStatus;
	}

	

}
