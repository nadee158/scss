/**
 * 
 */
package com.privasia.scss.core.util.constant;

/**
 * @author Janaka
 *
 */
public enum ContainerPosition {
	
	FRONT("F"), AFTER("A"), MIDDLE("M");
	
	private final String position;

    private ContainerPosition(String position) {
        this.position = position;
    }

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

}
