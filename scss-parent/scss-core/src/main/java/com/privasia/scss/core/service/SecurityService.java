package com.privasia.scss.core.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.reponse.ChangePasswordReponse;
import com.privasia.scss.core.reponse.ResetPasswordReponse;
import com.privasia.scss.core.reponse.SignOutResponse;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.request.ChangePasswordRequest;
import com.privasia.scss.core.request.ResetPasswordRequest;
import com.privasia.scss.core.request.SignOutRequest;
import com.privasia.scss.core.security.util.AuditContext;
import com.privasia.scss.core.security.util.SecurityContext;

@Service("securityService")
public class SecurityService {

	@Autowired
	private LoginRepository loginRepository;
	
	// inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<Login> getByUsername(String username) throws UsernameNotFoundException {
		return this.loginRepository.findByUserNameContainingIgnoreCase(username);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<Integer> getUserAccessFunctions(String username) {
		return this.loginRepository.accessUserFunction(username);
	}

	public SignOutResponse signOut(SecurityContext securityContext, AuditContext auditContext, SignOutRequest request) {
		deleteTokenDetailsFromCache(securityContext.getToken());
		return new SignOutResponse();
	}
	
//	@CacheEvict(cacheNames = "tokens", key = "#token")
	public void deleteTokenDetailsFromCache(String token) {
		template.delete(token);
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ResetPasswordReponse resetPassword(SecurityContext securityContext, AuditContext auditContext,
			ResetPasswordRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public ChangePasswordReponse changePassword(SecurityContext securityContext, AuditContext auditContext,
			ChangePasswordRequest request) {
		// TODO Auto-generated method stub
		return null;
	}

}
