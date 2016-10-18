/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum SolasWeightTypeSize {
	
	SIZE_0(0), SIZE_20(20), SIZE_40(40), SIZE_45(45);
	
	private final int solasWeightTypeSize;

    private SolasWeightTypeSize(int solasWeightTypeSize) {
        this.solasWeightTypeSize = solasWeightTypeSize;
    }

	/**
	 * @return the solasWeightTypeSize
	 */
	public int getSolasWeightTypeSize() {
		return solasWeightTypeSize;
	}

	
	

}
