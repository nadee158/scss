package com.privasia.scss.core.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.config.JwtSettings;
import com.privasia.scss.core.exception.InvalidJwtTokenException;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.reponse.SignOutResponse;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.security.jwt.verifier.TokenVerifier;
import com.privasia.scss.core.security.model.token.RawAccessJwtToken;
import com.privasia.scss.core.security.model.token.RefreshToken;
import com.privasia.scss.core.security.util.SecurityContext;

@Service("securityService")
public class SecurityService {

	private LoginRepository loginRepository;

	private JwtSettings jwtSettings;

	private TokenVerifier tokenVerifier;

	private CachedTokenValidatorService cachedTokenValidatorService;

	@Autowired
	public void setLoginRepository(LoginRepository loginRepository) {
		this.loginRepository = loginRepository;
	}

	@Autowired
	public void setJwtSettings(JwtSettings jwtSettings) {
		this.jwtSettings = jwtSettings;
	}

	@Autowired
	public void setTokenVerifier(TokenVerifier tokenVerifier) {
		this.tokenVerifier = tokenVerifier;
	}

	@Autowired
	public void setCachedTokenValidatorService(CachedTokenValidatorService cachedTokenValidatorService) {
		this.cachedTokenValidatorService = cachedTokenValidatorService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Login> getByUsername(String username) throws UsernameNotFoundException {
		return this.loginRepository.findByUserName(StringUtils.upperCase(username));
	}

	public SignOutResponse signOut(SecurityContext securityContext) {

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

		SignOutResponse signOutResponse = new SignOutResponse();
		signOutResponse.setMessage("SUCCESS");
		signOutResponse.setStatusCode(200);
		return signOutResponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Long> getRoleAndClientRights(long roleID, String clientIP) {

		Optional<List<BigDecimal>> optFunctionList = loginRepository.fetchRoleAndClientRights(roleID, clientIP);

		List<Long> finctionList = optFunctionList.get().stream().map(BigDecimal::longValue)
				.collect(Collectors.toList());

		return finctionList;
	}

}
