package com.privasia.scss.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.core.reponse.ChangePasswordReponse;
import com.privasia.scss.core.reponse.ResetPasswordReponse;
import com.privasia.scss.core.reponse.SignOutResponse;
import com.privasia.scss.core.request.ChangePasswordRequest;
import com.privasia.scss.core.request.ResetPasswordRequest;
import com.privasia.scss.core.request.SignOutRequest;
import com.privasia.scss.core.security.util.AuditContext;
import com.privasia.scss.core.security.util.SecurityContext;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.core.service.SecurityService;

@RestController
@RequestMapping(value = "/api/auth")
public class SecurityServiceController {
	
	@Autowired
	private SecurityService service;

	@RequestMapping(value = "signout", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public SignOutResponse signOut(@RequestBody SignOutRequest request) {
		AuditContext auditContext = SecurityHelper.getAuditContext();
		SecurityContext securityContext = SecurityHelper.getSecurityContext();
		return service.signOut(securityContext, auditContext, request);
	}

	@RequestMapping(value = "resetpassword", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ResetPasswordReponse resetPassword(@RequestBody ResetPasswordRequest request) {
		AuditContext auditContext = SecurityHelper.getAuditContext();
		SecurityContext securityContext = SecurityHelper.getSecurityContext();
		return this.service.resetPassword(securityContext, auditContext, request);
	}

	@RequestMapping(value = "changepassword", method = RequestMethod.POST, produces = {
			MediaType.APPLICATION_JSON_UTF8_VALUE }, consumes = { MediaType.APPLICATION_JSON_UTF8_VALUE })
	public ChangePasswordReponse changePassword(@RequestBody ChangePasswordRequest request) {
		AuditContext auditContext = SecurityHelper.getAuditContext();
		SecurityContext securityContext = SecurityHelper.getSecurityContext();
		return this.service.changePassword(securityContext, auditContext, request);
	}

}
