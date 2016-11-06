/**
 * 
 */
package com.privasia.scss.core.security.jwt.verifier;

/**
 * @author Janaka
 *
 */
public interface TokenVerifier {
	public boolean verify(String jti);
}
