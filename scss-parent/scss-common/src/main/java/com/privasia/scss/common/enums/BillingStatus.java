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
public enum BillingStatus implements Enumable {

  ACTIVE("ACTV"), CANCEL("CAN"), COMPLETE("COMP"), EXPIRED("EXPIRED");


  private final String billingStatus;

  private BillingStatus(String billingStatus) {
    this.billingStatus = billingStatus;
  }

  /**
   * @return the billingStatus
   */
  public String getValue() {
    return billingStatus;
  }

  public Enum<?> getEnumFromValue(String value) {
    return EnumableHelper.getEnumFromValue(this, value, null);
  }


  public static BillingStatus fromCode(String billingStatus) {
    return LOOKUP.get(billingStatus);
  }


  private static final Map<String, BillingStatus> LOOKUP = new HashMap<String, BillingStatus>();

  static {
    for (BillingStatus billingStatus : EnumSet.allOf(BillingStatus.class)) {
      LOOKUP.put(billingStatus.getValue(), billingStatus);
    }
  }



}
