/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum ClientType {
	
	GATE_IN("GATE IN"), CCC("CCC"), GREEN_GATE("GREEN GATE"), OTHERS("OTHERS"), SPV("SPV"), SECOND_GBOOTH("2GBOOTH"), 
	GATE_OUT("GATE OUT"), SECOND_GKIOSK("2GKIOSK");
	
	private final String clientType;

    private ClientType(String clientType) {
        this.clientType = clientType;
    }

	/**
	 * @return the clientType
	 */
	public String getClientType() {
		return clientType;
	}

	

}
