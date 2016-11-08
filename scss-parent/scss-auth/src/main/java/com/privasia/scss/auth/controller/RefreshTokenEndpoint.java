/**
 * 
 */
package com.privasia.scss.auth.controller;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
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
@RequestMapping(value = "/api/auth")
public class RefreshTokenEndpoint {

  @Autowired
  private JwtTokenFactory tokenFactory;
  @Autowired
  private JwtSettings jwtSettings;
  @Autowired
  private SecurityService securityService;
  @Autowired
  private TokenVerifier tokenVerifier;
  @Autowired
  @Qualifier("jwtHeaderTokenExtractor")
  private TokenExtractor tokenExtractor;

  @Autowired
  @Qualifier("customRedisTemplate")
  private RedisTemplate<String, String> customRedisTemplate;

  @RequestMapping(value = "token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
      consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})

  public @ResponseBody JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response)
      throws IOException, ServletException {
    String tokenPayload = tokenExtractor.extract(request.getHeader(WebSecurityConfig.JWT_TOKEN_HEADER_PARAM));

    RawAccessJwtToken rawToken = new RawAccessJwtToken(tokenPayload);
    RefreshToken refreshToken = RefreshToken.create(rawToken, jwtSettings.getTokenSigningKey())
        .orElseThrow(() -> new InvalidJwtTokenException());

    String jti = refreshToken.getJti();
    if (!tokenVerifier.verify(jti)) {
      throw new InvalidJwtTokenException();
    }

    String subject = refreshToken.getSubject();
    Login loguser = securityService.getByUsername(subject)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + subject));

    if (loguser.getRole() == null)
      throw new InsufficientAuthenticationException("User has no roles assigned");

    UserContext userContext =
        UserContext.create(loguser.getUserName(), AuthorityUtils.createAuthorityList(loguser.getRole().getRoleName()));

    JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);

    return accessToken;
  }

  public void addTokenDetailsToCache(String token, String refreshToken, UserContext userContext) {
    try {
      String key = userContext.getUsername();
      customRedisTemplate.opsForHash().put(key, "id", UUID.randomUUID().toString());
      customRedisTemplate.opsForHash().put(key, "username", userContext.getUsername());
      customRedisTemplate.opsForHash().put(key, "authorities", userContext.getAuthorities());
      customRedisTemplate.opsForHash().put(key, "token", token);
      customRedisTemplate.opsForHash().put(key, "refreshToken", refreshToken);

      ListOperations<String, String> listOps = customRedisTemplate.opsForList();
      listOps.leftPush("userLoginList", key);

      customRedisTemplate.opsForSet().add("tokens", token);

      String refreshTokenKey = refreshToken;
      customRedisTemplate.opsForHash().put(refreshTokenKey, "token", token);
      customRedisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateTokenDetailsOfCache(String oldToken, String newtoken, String refreshToken,
      UserContext userContext) {
    String key = userContext.getUsername();

    customRedisTemplate.opsForHash().delete(key, "token");

    String existingRecord = (String) customRedisTemplate.opsForHash().get(key, "id");

    if (!(existingRecord == null)) {
      customRedisTemplate.opsForHash().put(key, "token", newtoken);
    }

    String refreshTokenKey = refreshToken;

    // delete old token
    customRedisTemplate.opsForSet().remove("tokens", oldToken);
    customRedisTemplate.opsForHash().delete(refreshTokenKey, "tokens");

    // add new token
    customRedisTemplate.opsForSet().add("tokens", newtoken);


    customRedisTemplate.opsForHash().put(refreshTokenKey, "token", newtoken);
    customRedisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

  }

}
