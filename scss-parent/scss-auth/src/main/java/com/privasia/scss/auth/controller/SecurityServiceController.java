package com.privasia.scss.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.core.reponse.SignOutResponse;
import com.privasia.scss.core.security.jwt.extractor.TokenExtractor;
import com.privasia.scss.core.security.util.AuditContext;
import com.privasia.scss.core.security.util.SecurityContext;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.core.service.SecurityService;

@RestController
@RequestMapping(value = "/logout")
public class SecurityServiceController {

	@Autowired
	private SecurityService service;

	@Autowired
	@Qualifier("jwtHeaderTokenExtractor")
	private TokenExtractor tokenExtractor;

	@RequestMapping(method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public SignOutResponse signOut() {
		AuditContext auditContext = SecurityHelper.getAuditContext();
		SecurityContext securityContext = SecurityHelper.getSecurityContext(tokenExtractor);
		return service.signOut(securityContext, auditContext);
	}

}
