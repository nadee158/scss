/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum SolasInstructionType {
	
	VGM_INSTRUCTION_SHIPPER("SHIPPER VGM"), VGM_INSTRUCTION_TERMINAL("TERMINAL VGM"), VGM_INSTRUCTION_NO_SOLAS("NO_SOLAS");
	
	private final String solasInstructionType;

    private SolasInstructionType(String solasInstructionType) {
        this.solasInstructionType = solasInstructionType;
    }

	/**
	 * @return the solasInstructionType
	 */
	public String getSolasInstructionType() {
		return solasInstructionType;
	}


}
