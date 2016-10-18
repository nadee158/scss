/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum ShippingLineReportType {
	
	CONTAINER_SIZE_DISCREPANCY("CSD"),  DSO_SEAL_DIFFERENT("DSEAL"), SEAL_LINE("SEAL"), WEIGHT("WGH"), WRONG_DOOR("WRD");
	
	private final String shipMailTypeCode;

    private ShippingLineReportType(String shipMailTypeCode) {
        this.shipMailTypeCode = shipMailTypeCode;
    }

	/**
	 * @return the shipMailTypeCode
	 */
	public String getShipMailTypeCode() {
		return shipMailTypeCode;
	}
}
