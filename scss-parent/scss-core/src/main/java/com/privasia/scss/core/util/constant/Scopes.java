/**
 * 
 */
package com.privasia.scss.core.util.constant;

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
