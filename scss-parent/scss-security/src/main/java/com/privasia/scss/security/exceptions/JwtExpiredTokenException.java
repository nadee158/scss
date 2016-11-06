/**
 * 
 */
package com.privasia.scss.security.exceptions;

import org.springframework.security.core.AuthenticationException;

import com.privasia.scss.security.model.token.JwtToken;

/**
 * @author Janaka
 *
 */
public class JwtExpiredTokenException extends AuthenticationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3327219411942460252L;

	private JwtToken token;

	public JwtExpiredTokenException(String msg) {
		super(msg);
	}

	public JwtExpiredTokenException(JwtToken token, String msg, Throwable t) {
		super(msg, t);
		this.token = token;
	}
	
	public String token(){
		return this.token.getToken();
	}

}
