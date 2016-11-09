package com.privasia.scss.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.config.JwtSettings;
import com.privasia.scss.core.exception.InvalidJwtTokenException;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.reponse.SignOutResponse;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.request.SignOutRequest;
import com.privasia.scss.core.security.jwt.verifier.TokenVerifier;
import com.privasia.scss.core.security.model.token.RawAccessJwtToken;
import com.privasia.scss.core.security.model.token.RefreshToken;
import com.privasia.scss.core.security.util.AuditContext;
import com.privasia.scss.core.security.util.SecurityContext;

@Service("securityService")
public class SecurityService {

	@Autowired
	private LoginRepository loginRepository;

	@Autowired
	private JwtSettings jwtSettings;

	@Autowired
	private TokenVerifier tokenVerifier;
	
	@Autowired
	private CachedTokenValidatorService cachedTokenValidatorService;

	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Login> getByUsername(String username) throws UsernameNotFoundException {
		return this.loginRepository.findByUserNameContainingIgnoreCase(username);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Integer> getUserAccessFunctions(String username) {
		return this.loginRepository.accessUserFunction(username);
	}

	public SignOutResponse signOut(SecurityContext securityContext, AuditContext auditContext, SignOutRequest request) {

		String token = securityContext.getToken();
		RawAccessJwtToken rawToken = new RawAccessJwtToken(token);
		RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
				.orElseThrow(() -> new InvalidJwtTokenException());

		String jti = refreshToken.getJti();
		if (!tokenVerifier.verify(jti)) {
			throw new InvalidJwtTokenException();
		}

		String subject = refreshToken.getSubject();
		Login loguser = getByUsername(subject)
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

		cachedTokenValidatorService.deleteTokenDetailsFromCache(token, refreshToken.getToken(), loguser.getUserName());

		return new SignOutResponse();
	}

	

}
