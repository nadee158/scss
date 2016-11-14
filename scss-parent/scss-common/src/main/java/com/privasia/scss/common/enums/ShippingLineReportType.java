/**
 * 
 */
package com.privasia.scss.common.enums;


/**
 * @author Janaka
 *
 */
public enum ShippingLineReportType implements Enumable {
	
	CONTAINER_SIZE_DISCREPANCY("CSD"),  DSO_SEAL_DIFFERENT("DSEAL"), SEAL_LINE("SEAL"), WEIGHT("WGH"), WRONG_DOOR("WRD");
	
	private final String shipMailTypeCode;

    private ShippingLineReportType(String shipMailTypeCode) {
        this.shipMailTypeCode = shipMailTypeCode;
    }

	/**
	 * @return the shipMailTypeCode
	 */
	public String getValue() {
		return shipMailTypeCode;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
}
