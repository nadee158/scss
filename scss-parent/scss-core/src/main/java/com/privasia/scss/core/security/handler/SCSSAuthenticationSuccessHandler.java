/**
 * 
 */
package com.privasia.scss.core.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.security.model.token.JwtToken;
import com.privasia.scss.core.security.model.token.JwtTokenFactory;

/**
 * @author Janaka
 *
 */
@Component
public class SCSSAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

  private final ObjectMapper mapper;
  private final JwtTokenFactory tokenFactory;

  @Autowired
  private RedisTemplate<String, String> redisTemplate;


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

    addTokenDetailsToCache(accessToken.getToken(), refreshToken.getToken(), userContext);

    response.setStatus(HttpStatus.OK.value());
    response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
    mapper.writeValue(response.getWriter(), tokenMap);

    clearAuthenticationAttributes(request);
  }


  public void addTokenDetailsToCache(String token, String refreshToken, UserContext userContext) {
    try {
      String key = userContext.getUsername();
      redisTemplate.opsForHash().put(key, "id", UUID.randomUUID().toString());
      redisTemplate.opsForHash().put(key, "username", userContext.getUsername());
      redisTemplate.opsForHash().put(key, "authorities", userContext.getAuthorities().toString());
      redisTemplate.opsForHash().put(key, "token", token);
      redisTemplate.opsForHash().put(key, "refreshToken", refreshToken);

      ListOperations<String, String> listOps = redisTemplate.opsForList();
      listOps.leftPush("userLoginList", key);

      redisTemplate.opsForSet().add("tokens", token);

      String refreshTokenKey = refreshToken;
      redisTemplate.opsForHash().put(refreshTokenKey, "token", UUID.randomUUID().toString());

      redisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

    } catch (Exception e) {
      e.printStackTrace();
    }
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
