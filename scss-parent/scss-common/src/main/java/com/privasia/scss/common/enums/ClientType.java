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
public enum ClientType implements Enumable {

  GATE_IN("GATE IN"), CCC("CCC"), GREEN_GATE("GREEN GATE"), OTHERS("OTHERS"), SPV("SPV"), SECOND_GBOOTH(
      "2GBOOTH"), GATE_OUT("GATE OUT"), SECOND_GKIOSK("2GKIOSK");

  private final String clientType;

  private ClientType(String clientType) {
    this.clientType = clientType;
  }

  /**
   * @return the clientType
   */
  public String getValue() {
    return clientType;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }


  private static final Map<String, ClientType> LOOKUP = new HashMap<String, ClientType>();

  private static final Map<String, ClientType> LOOKUP_BY_NAME = new HashMap<String, ClientType>();

  static {
    for (ClientType clientType : EnumSet.allOf(ClientType.class)) {
      LOOKUP.put(clientType.getValue(), clientType);
      LOOKUP_BY_NAME.put(clientType.name(), clientType);
    }
  }

  public static ClientType fromValue(String value) {
    return LOOKUP.get(value);
  }

  public static ClientType fromName(String name) {
    return LOOKUP_BY_NAME.get(name);
  }



}
