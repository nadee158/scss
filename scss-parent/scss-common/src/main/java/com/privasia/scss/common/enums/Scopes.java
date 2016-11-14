/**
 * 
 */
package com.privasia.scss.common.enums;

/**
 * @author Janaka
 *
 */
public enum Scopes {
	
	REFRESH_TOKEN;
    
    public String authority() {
        return "ROLE_" + this.name();
    }

}
