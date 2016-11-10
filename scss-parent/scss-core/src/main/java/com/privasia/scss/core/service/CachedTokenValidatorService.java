package com.privasia.scss.core.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.security.model.UserContext;

@Service("cachedTokenValidatorService")
public class CachedTokenValidatorService {

  @Autowired
  private RedisTemplate<String, String> redisTemplate;

  public boolean checkIfValidToken(String token) {
    return redisTemplate.opsForSet().isMember("tokens", token);
  }

  public void addTokenDetailsToCache(String token, String refreshToken, UserContext userContext) {
    System.out.println("ADDING TOKENS TO REDIS :" + " :token: " + token + " :refreshToken: " + refreshToken
        + " :userName: " + userContext.getUsername());
    try {
      String key = userContext.getUsername();
      redisTemplate.opsForHash().put(key, "id", UUID.randomUUID().toString());
      redisTemplate.opsForHash().put(key, "username", userContext.getUsername());
      // redisTemplate.opsForHash().put(key, "authorities",
      // userContext.getAuthorities().toString());
      redisTemplate.opsForHash().put(key, "token", token);
      redisTemplate.opsForHash().put(key, "refreshToken", refreshToken);

      ListOperations<String, String> listOps = redisTemplate.opsForList();
      listOps.leftPush("userLoginList", key);

      redisTemplate.opsForSet().add("tokens", token);

      // String refreshTokenKey = refreshToken;
      // redisTemplate.opsForHash().put(refreshTokenKey, "token", UUID.randomUUID().toString());

      // redisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }


  public void updateTokenDetailsOfCache(String oldToken, String newtoken, String refreshToken,
      UserContext userContext) {
    try {
      System.out.println("UPDATING TOKENS TO REDIS :" + " :refreshToken: " + refreshToken + " :oldToken: " + oldToken
          + " :newtoken: " + newtoken + " :userName: " + userContext.getUsername());

      String key = userContext.getUsername();

      redisTemplate.opsForHash().delete(key, "token");

      String existingRecord = (String) redisTemplate.opsForHash().get(key, "id");

      if (existingRecord != null) {
        redisTemplate.opsForHash().put(key, "token", newtoken);
      }

      // String refreshTokenKey = refreshToken;

      // delete old token
      redisTemplate.opsForSet().remove("tokens", oldToken);
      // redisTemplate.opsForHash().delete(refreshTokenKey, "tokens");

      // add new token
      redisTemplate.opsForSet().add("tokens", newtoken);

      // redisTemplate.opsForHash().put(refreshTokenKey, "token", newtoken);
      // redisTemplate.opsForSet().add("refreshTokens", refreshTokenKey);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

  public void deleteTokenDetailsFromCache(String token, String refreshToken, String userName) {
    try {
      System.out.println(
          "DELETING TOKENS :" + " :token: " + token + " :refreshToken: " + refreshToken + " :userName: " + userName);
      String key = userName;
      redisTemplate.opsForHash().delete(key, "id");
      redisTemplate.opsForHash().delete(key, "username");
      // redisTemplate.opsForHash().delete(key, "authorities");
      redisTemplate.opsForHash().delete(key, "token");
      redisTemplate.opsForHash().delete(key, "password");
      redisTemplate.opsForHash().delete(key, "refreshToken");

      ListOperations<String, String> listOps = redisTemplate.opsForList();
      listOps.remove("userLoginList", 0, key);

      redisTemplate.opsForSet().remove("tokens", token);

      // String refreshTokenKey = refreshToken;
      // redisTemplate.opsForHash().delete(refreshTokenKey, "token");
      // redisTemplate.opsForSet().remove("refreshTokens", refreshTokenKey);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


}
