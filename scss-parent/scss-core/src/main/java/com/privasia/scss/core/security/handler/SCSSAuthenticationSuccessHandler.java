/**
 * 
 */
package com.privasia.scss.core.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.security.model.token.JwtToken;
import com.privasia.scss.core.security.model.token.JwtTokenFactory;
import com.privasia.scss.core.service.CachedTokenValidatorService;

/**
 * @author Janaka
 *
 */
@Component
public class SCSSAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final ObjectMapper mapper;
  private final JwtTokenFactory tokenFactory;

  @Autowired
  private CachedTokenValidatorService cachedTokenValidatorService;


  @Autowired
  public SCSSAuthenticationSuccessHandler(final ObjectMapper mapper, final JwtTokenFactory tokenFactory) {
    this.mapper = mapper;
    this.tokenFactory = tokenFactory;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException, ServletException {
    UserContext userContext = (UserContext) authentication.getPrincipal();

    JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);
    JwtToken refreshToken = tokenFactory.createRefreshToken(userContext);

    Map<String, String> tokenMap = new HashMap<String, String>();
    tokenMap.put("token", accessToken.getToken());
    tokenMap.put("refreshToken", refreshToken.getToken());

    cachedTokenValidatorService.addTokenDetailsToCache(accessToken.getToken(), refreshToken.getToken(), userContext);

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mapper.writeValue(response.getWriter(), tokenMap);

    SecurityContextHolder.getContext().setAuthentication(authentication);

    clearAuthenticationAttributes(request);
  }



  /**
   * Removes temporary authentication-related data which may have been stored in the session during
   * the authentication process..
   * 
   */
  protected final void clearAuthenticationAttributes(HttpServletRequest request) {
    HttpSession session = request.getSession(false);

    if (session == null) {
      return;
    }

    session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
  }

}
