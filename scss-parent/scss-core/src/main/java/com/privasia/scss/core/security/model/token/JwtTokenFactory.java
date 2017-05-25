/**
 * 
 */
package com.privasia.scss.core.security.model.token;

import java.util.stream.Collectors;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.config.JwtSettings;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * @author Janaka
 *
 */
@Component
public class JwtTokenFactory {
	
	private final JwtSettings settings;
	
	@Autowired
    public JwtTokenFactory(JwtSettings settings) {
        this.settings = settings;
    }
	
	/**
     * Factory method for issuing new JWT Tokens.
     * 
     * @param username
     * @param roles
     * @return
     */
    public AccessJwtToken createAccessJwtToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) 
            throw new IllegalArgumentException("Cannot create JWT Token without username");

        if (userContext.getAuthorities() == null || userContext.getAuthorities().isEmpty()) 
            throw new IllegalArgumentException("User doesn't have any privileges");
        
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("roles", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("functions", userContext.getFunctions());
        claims.put("staffName", userContext.getStaffName());
        claims.put("staffNumber", userContext.getStaffNumber());
        claims.put("userID", userContext.getUserID());

        DateTime currentTime = new DateTime();

        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getTokenExpirationTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

    public JwtToken createRefreshToken(UserContext userContext) {
        if (StringUtils.isBlank(userContext.getUsername())) {
            throw new IllegalArgumentException("Cannot create JWT Token without username");
        }
        
        Claims claims = Jwts.claims().setSubject(userContext.getUsername());
        claims.put("roles", userContext.getAuthorities().stream().map(s -> s.toString()).collect(Collectors.toList()));
        claims.put("functions", userContext.getFunctions());
        claims.put("staffName", userContext.getStaffName());
        claims.put("staffNumber", userContext.getStaffNumber());
        claims.put("userID", userContext.getUserID());
        
        DateTime currentTime = new DateTime();
        
        String token = Jwts.builder()
          .setClaims(claims)
          .setIssuer(settings.getTokenIssuer())
          .setId(UUID.randomUUID().toString())
          .setIssuedAt(currentTime.toDate())
          .setExpiration(currentTime.plusMinutes(settings.getRefreshTokenExpTime()).toDate())
          .signWith(SignatureAlgorithm.HS512, settings.getTokenSigningKey())
        .compact();

        return new AccessJwtToken(token, claims);
    }

}
