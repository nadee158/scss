/**
 * 
 */
package com.privasia.scss.core.security.provider;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import com.privasia.scss.core.config.JwtSettings;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.security.model.token.JwtAuthenticationToken;
import com.privasia.scss.core.security.model.token.RawAccessJwtToken;

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
	public JwtAuthenticationProvider(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		RawAccessJwtToken rawAccessToken = (RawAccessJwtToken) authentication.getCredentials();

		Jws<Claims> jwsClaims = rawAccessToken.parseClaims(jwtSettings.getTokenSigningKey());
		String subject = jwsClaims.getBody().getSubject();
		List<String> roles = jwsClaims.getBody().get("roles", List.class);
		List<GrantedAuthority> authorities = roles.stream().map(authority -> new SimpleGrantedAuthority(authority))
				.collect(Collectors.toList());
		
		Set<Long> functions = jwsClaims.getBody().get("functions", Set.class);
		
		UserContext context = UserContext.create(subject, authorities, functions);

		return new JwtAuthenticationToken(context, context.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
