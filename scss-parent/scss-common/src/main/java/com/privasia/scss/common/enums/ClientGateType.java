/**
 * 
 */
package com.privasia.scss.common.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Janaka
 *
 */
public enum ClientGateType implements Enumable {

	GCS("GATE", "Main gate"), PKFZ("PKFZ", "Pkfz gate"), LPK("ITD", "mini booth");

	private final String clientGateType;
	private final String clientGateDescription;

	private ClientGateType(String clientGateType, String clientGateDescription) {
		this.clientGateType = clientGateType;
		this.clientGateDescription = clientGateDescription;
	}

	/**
	 * @return the clientGateType
	 */
	public String getValue() {
		return clientGateType;
	}
	
	public String getClientGateDescription() {
        return this.clientGateDescription;
    }

	public Enum<?> getEnumFromValue(String value) {
		return EnumableHelper.getEnumFromValue(this, value, null);
	}

	private static final Map<String, ClientGateType> LOOKUP = new HashMap<String, ClientGateType>();

	static {
		for (ClientGateType clientGateType : EnumSet.allOf(ClientGateType.class)) {
			LOOKUP.put(clientGateType.getValue(), clientGateType);
		}
	}

	public static ClientGateType fromValue(String value) {
		return LOOKUP.get(value);
	}

}
