/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum SolasWeightType {
	
	FUEL("FUEL"), TYRE("TYRE"), VARIANCE("VARIANCE"), PM("PM"), TRAILER("TRAILER"), MGW("MGW");
	
	private final String solasWeightType;

    private SolasWeightType(String solasWeightType) {
        this.solasWeightType = solasWeightType;
    }

	/**
	 * @return the solasWeightType
	 */
	public String getSolasWeightType() {
		return solasWeightType;
	}

	

}
