/**
 * 
 */
package com.privasia.scss.security.model;

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
