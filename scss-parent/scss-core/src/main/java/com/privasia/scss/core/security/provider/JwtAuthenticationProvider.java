/**
 * 
 */
package com.privasia.scss.core.security.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.privasia.scss.core.config.JwtSettings;
import com.privasia.scss.core.exception.JwtExpiredTokenException;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.security.model.token.JwtAuthenticationToken;
import com.privasia.scss.core.security.model.token.RawAccessJwtToken;
import com.privasia.scss.core.service.CachedTokenValidatorService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;

/**
 * @author Janaka
 *
 */
@Component
@SuppressWarnings("unchecked")
public class JwtAuthenticationProvider implements AuthenticationProvider {

  private final JwtSettings jwtSettings;

  @Autowired
  private CachedTokenValidatorService cachedTokenValidatorService;

  @Autowired
  public JwtAuthenticationProvider(JwtSettings jwtSettings) {
    this.jwtSettings = jwtSettings;
  }

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

    Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());

    // is a valid key - check in cache now

    boolean isActiveToken = cachedTokenValidatorService.checkIfValidToken(rawAccessToken.getToken());
    System.out.println("isActiveToken :" + isActiveToken);
    if (!(isActiveToken)) {
      throw new JwtExpiredTokenException("JWT Token not available in cache!");
    }

    String subject = jwsClaims.getBody().getSubject();
    List<String> roles = jwsClaims.getBody().get("roles", List.class);
    List<GrantedAuthority> authorities =
        roles.stream().map(authority -> new SimpleGrantedAuthority(authority)).collect(Collectors.toList());

    List<Long> functions = jwsClaims.getBody().get("functions", List.class);
    
    String staffName = jwsClaims.getBody().get("staffName", String.class);
    
    String staffNumber = jwsClaims.getBody().get("staffNumber", String.class);
    
    Long userID = jwsClaims.getBody().get("userID", Long.class);
    

    UserContext context = UserContext.create(userID, subject, authorities, functions, staffName, staffNumber);

    return new JwtAuthenticationToken(context, context.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
