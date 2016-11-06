/**
 * 
 */
package com.privasia.scss.core.security.controller;

import java.io.IOException;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.privasia.scss.core.config.JwtSettings;
import com.privasia.scss.core.config.WebSecurityConfig;
import com.privasia.scss.core.exception.InvalidJwtTokenException;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.security.jwt.extractor.TokenExtractor;
import com.privasia.scss.core.security.jwt.verifier.TokenVerifier;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.security.model.token.JwtToken;
import com.privasia.scss.core.security.model.token.JwtTokenFactory;
import com.privasia.scss.core.security.model.token.RawAccessJwtToken;
import com.privasia.scss.core.security.model.token.RefreshToken;
import com.privasia.scss.core.service.SecurityService;

/**
 * @author Janaka
 *
 */
@RestController
public class RefreshTokenEndpoint {
	
	@Autowired 
	private JwtTokenFactory tokenFactory;
    @Autowired 
    private JwtSettings jwtSettings;
    @Autowired 
    private SecurityService securityService;
    @Autowired 
    private TokenVerifier tokenVerifier;
    @Autowired @Qualifier("jwtHeaderTokenExtractor") 
    private TokenExtractor tokenExtractor;
    
    @RequestMapping(value="/api/auth/token", method=RequestMethod.GET, produces={ MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));
        
        RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
        RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey()).orElseThrow(() -> new InvalidJwtTokenException());

        String jti = refreshToken.getJti();
        if (!tokenVerifier.verify(jti)) {
            throw new InvalidJwtTokenException();
        }

        String subject = refreshToken.getSubject();
        Login loguser = securityService.getByUsername(subject).orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

        if (loguser.getRole() == null) throw new InsufficientAuthenticationException("User has no roles assigned");
        
        UserContext userContext = UserContext.create(loguser.getUserName(), AuthorityUtils.createAuthorityList(loguser.getRole().getRoleName()));

        return tokenFactory.createAccessJwtToken(userContext);
    }

}
