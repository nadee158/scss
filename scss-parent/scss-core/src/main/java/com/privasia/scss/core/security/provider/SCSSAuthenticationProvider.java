/**
 * 
 */
package com.privasia.scss.core.security.provider;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.service.SecurityService;

/**
 * @author Janaka
 *
 */
@Component
public class SCSSAuthenticationProvider implements AuthenticationProvider {

	private final SecurityService securityService;
	private final PasswordEncoder encoder;

	@Autowired
	public SCSSAuthenticationProvider(final SecurityService securityService, final PasswordEncoder encoder) {
		this.securityService = securityService;
		this.encoder = encoder;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Assert.notNull(authentication, "No authentication data provided");

		String username = (String) authentication.getPrincipal();
		String password = (String) authentication.getCredentials();

		Login loguser = securityService.getByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

		List<Long> functionList = loguser.getRole().getRoleRights().stream()
				.map(roleRights -> roleRights.getRoleRightsID().getFunction().getFunctionID())
				.collect(Collectors.toList());

		if (!encoder.matches(password, loguser.getPassword())) {
			throw new BadCredentialsException("Authentication Failed. Username or Password not valid.");
		}

		if (loguser.getRole() == null)
			throw new InsufficientAuthenticationException("User has no roles assigned");

		UserContext userContext = UserContext.create(loguser.getSystemUser().getSystemUserID(), loguser.getUserName(),
				AuthorityUtils.createAuthorityList(loguser.getRole().getRoleName()), functionList,
				loguser.getSystemUser().getCommonContactAttribute().getPersonName(),
				loguser.getSystemUser().getStaffNumber());

		return new UsernamePasswordAuthenticationToken(userContext, null, userContext.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}

}
