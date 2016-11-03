/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum CompanyType implements Enumable {
	
	HAULAGE("H"), PORT_USER("P"), TRANSPORTER("T"), GOVERNMENT_AGENCY("A"), FOREIGN_WORKER("N"), FORWARDING("F"), GENERAL("G"), WESTPORT_STAFF("W");
	
	
	private final String comType;

    private CompanyType(String comType) {
        this.comType = comType;
    }

    /**
	 * @return the comType
	 */
	public String getValue() {
		return comType;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}
	

	

}
