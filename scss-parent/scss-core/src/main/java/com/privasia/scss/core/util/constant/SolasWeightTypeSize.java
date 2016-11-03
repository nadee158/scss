/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum SolasWeightTypeSize implements Enumable {
	
	SIZE_0(0), SIZE_20(20), SIZE_40(40), SIZE_45(45);
	
	private final int solasWeightTypeSize;

    private SolasWeightTypeSize(int solasWeightTypeSize) {
        this.solasWeightTypeSize = solasWeightTypeSize;
    }

	/**
	 * @return the solasWeightTypeSize
	 */
	public String getValue() {
		return String.valueOf(solasWeightTypeSize);
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

	
	

}
