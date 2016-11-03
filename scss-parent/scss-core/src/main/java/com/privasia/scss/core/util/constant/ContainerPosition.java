/**
 * 
 */
package com.privasia.scss.core.util.constant;


/**
 * @author Janaka
 *
 */
public enum ContainerPosition implements Enumable {
	
	FRONT("F"), AFTER("A"), MIDDLE("M");
	
	private final String position;

    private ContainerPosition(String position) {
        this.position = position;
    }

	/**
	 * @return the position
	 */
	public String getValue() {
		return position;
	}
	
	public Enum<?> getEnumFromValue(String value) {
	      return EnumableHelper.getEnumFromValue(this, value, null);
	}

}
