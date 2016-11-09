/**
 * 
 */
package com.privasia.scss.auth.controller;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

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
@RequestMapping(value = "/refreshtoken")
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
  private RedisTemplate<String, String> redisTemplate;

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
    
    Set<Long> functionList = 
    		loguser.getRole().getRoleRights().stream().map(roleRights -> roleRights.getRoleRightsID()
    					.getFunction().getFunctionID()).collect(Collectors.toSet());

    if (loguser.getRole() == null)
      throw new InsufficientAuthenticationException("User has no roles assigned");

    UserContext userContext =
        UserContext.create(loguser.getUserName(), 
        		AuthorityUtils.createAuthorityList(loguser.getRole().getRoleName()), functionList);

    JwtToken accessToken = tokenFactory.createAccessJwtToken(userContext);

    return accessToken;
  }

  public void addTokenDetailsToCache(String token, String refreshToken, UserContext userContext) {
    try {
      String key = userContext.getUsername();
      redisTemplate.opsForHash().put(key, "id", UUID.randomUUID().toString());
      redisTemplate.opsForHash().put(key, "username", userContext.getUsername());
      redisTemplate.opsForHash().put(key, "authorities", userContext.getAuthorities());
      redisTemplate.opsForHash().put(key, "token", token);
      redisTemplate.opsForHash().put(key, "refreshToken", refreshToken);

      ListOperations<String, String> listOps = redisTemplate.opsForList();
      listOps.leftPush("userLoginList", key);

      redisTemplate.opsForSet().add("tokens", token);

      String refreshTokenKey = refreshToken;
      redisTemplate.opsForHash().put(refreshTokenKey, "token", token);
      redisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void updateTokenDetailsOfCache(String oldToken, String newtoken, String refreshToken,
      UserContext userContext) {
    String key = userContext.getUsername();

    redisTemplate.opsForHash().delete(key, "token");

    String existingRecord = (String) redisTemplate.opsForHash().get(key, "id");

    if (!(existingRecord == null)) {
    	redisTemplate.opsForHash().put(key, "token", newtoken);
    }

    String refreshTokenKey = refreshToken;

    // delete old token
    redisTemplate.opsForSet().remove("tokens", oldToken);
    redisTemplate.opsForHash().delete(refreshTokenKey, "tokens");

    // add new token
    redisTemplate.opsForSet().add("tokens", newtoken);


    redisTemplate.opsForHash().put(refreshTokenKey, "token", newtoken);
    redisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

  }

}
