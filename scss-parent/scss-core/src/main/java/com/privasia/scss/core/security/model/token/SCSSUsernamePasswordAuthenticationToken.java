/**
 * 
 */
package com.privasia.scss.core.security.model.token;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

/**
 * @author Janaka
 *
 */
public class SCSSUsernamePasswordAuthenticationToken extends UsernamePasswordAuthenticationToken {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String clientIP;

	public SCSSUsernamePasswordAuthenticationToken(Object principal, Object credentials, String clientIP) {
		super(principal, credentials);
		this.clientIP = clientIP;
	}

	public String getClientIP() {
		return clientIP;
	}

	

}
