/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum SolasWeightType implements Enumable {
	
	FUEL("FUEL"), TYRE("TYRE"), VARIANCE("VARIANCE"), PM("PM"), TRAILER("TRAILER"), MGW("MGW");
	
	private final String solasWeightType;

    private SolasWeightType(String solasWeightType) {
        this.solasWeightType = solasWeightType;
    }

	/**
	 * @return the solasWeightType
	 */
	public String getValue() {
		return solasWeightType;
	}

	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
