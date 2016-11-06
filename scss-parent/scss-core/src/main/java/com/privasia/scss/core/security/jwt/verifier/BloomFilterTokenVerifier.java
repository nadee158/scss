/**
 * 
 */
package com.privasia.scss.core.security.jwt.verifier;

import org.springframework.stereotype.Component;

/**
 * @author Janaka
 *
 */
@Component
public class BloomFilterTokenVerifier implements TokenVerifier {

	@Override
	public boolean verify(String jti) {
		return true;
	}

}
