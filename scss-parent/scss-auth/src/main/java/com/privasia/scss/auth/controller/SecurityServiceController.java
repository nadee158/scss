package com.privasia.scss.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
@RequestMapping(value = "**/secure")
public class SecurityServiceController {

	@Autowired
	private SecurityService service;

	@Autowired
	@Qualifier("jwtHeaderTokenExtractor")
	private TokenExtractor tokenExtractor;

	@RequestMapping(value = "/logout", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResponseEntity<SignOutResponse> signOut() {
		AuditContext auditContext = SecurityHelper.getAuditContext();
		SecurityContext securityContext = SecurityHelper.getSecurityContext(tokenExtractor);
		SignOutResponse signOutResponse = service.signOut(securityContext, auditContext);
		return new ResponseEntity<SignOutResponse>(signOutResponse, HttpStatus.OK);
	}

}
