/**
 * 
 */
package com.privasia.scss.security.exceptions;

import org.springframework.security.authentication.AuthenticationServiceException;

/**
 * @author Janaka
 *
 */
public class AuthMethodNotSupportedException extends AuthenticationServiceException {
	
	private static final long serialVersionUID = 3705043083010304496L;
	
	public AuthMethodNotSupportedException(String msg) {
		super(msg);
	}

	public AuthMethodNotSupportedException(String msg, Throwable ex){
		super(msg, ex);
	}

}
