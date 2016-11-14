/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum SolasInstructionType implements Enumable {
	
	VGM_INSTRUCTION_SHIPPER("SHIPPER VGM"), VGM_INSTRUCTION_TERMINAL("TERMINAL VGM"), VGM_INSTRUCTION_NO_SOLAS("NO_SOLAS");
	
	private final String solasInstructionType;

    private SolasInstructionType(String solasInstructionType) {
        this.solasInstructionType = solasInstructionType;
    }

	/**
	 * @return the solasInstructionType
	 */
	public String getValue() {
		return solasInstructionType;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}


}
