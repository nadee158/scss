/**
 * 
 */
package com.privasia.scss.core.security.model.token;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.BadCredentialsException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * @author Janaka
 *
 */
public class RefreshToken implements JwtToken {
	
	private Jws<Claims> claims;

    private RefreshToken(Jws<Claims> claims) {
        this.claims = claims;
    }

    /**
     * Creates and validates Refresh token 
     * 
     * @param token
     * @param signingKey
     * 
     * @throws BadCredentialsException
     * @throws JwtExpiredTokenException
     * 
     * @return
     */
    public static Optional<RefreshToken> create(RawAccessJwtToken token, String signingKey) {
        
    	Jws<Claims> claims = token.parseClaims(signingKey);

        @SuppressWarnings("unchecked")
		List<String> roles = claims.getBody().get("roles", List.class);
        
        if (roles == null || roles.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new RefreshToken(claims));
    }

    @Override
    public String getToken() {
        return null;
    }

    public Jws<Claims> getClaims() {
        return claims;
    }
    
    public String getJti() {
        return claims.getBody().getId();
    }
    
    public String getSubject() {
        return claims.getBody().getSubject();
    }

}
