/**
 * 
 */
package com.privasia.scss.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.privasia.scss.core.security.model.token.JwtToken;

/**
 * @author Janaka
 *
 */

@Configuration
@ConfigurationProperties(prefix = "demo.security.jwt")
public class JwtSettings {

  /**
   * {@link JwtToken} will expire after this time.
   */
  @Value("#{new Integer('${scss.token.tokenExpirationTime}')}")
  private Integer tokenExpirationTime;


  /**
   * Token issuer.
   */
  @Value("${scss.token.tokenIssuer}")
  private String tokenIssuer;

  /**
   * Key is used to sign {@link JwtToken}.
   */
  @Value("${scss.token.tokenSigningKey}")
  private String tokenSigningKey;

  /**
   * {@link JwtToken} can be refreshed during this timeframe.
   */
  @Value("#{new Integer('${scss.token.refreshTokenExpTime}')}")
  private Integer refreshTokenExpTime;


  public Integer getRefreshTokenExpTime() {
    return refreshTokenExpTime;
  }

  public void setRefreshTokenExpTime(Integer refreshTokenExpTime) {
    this.refreshTokenExpTime = refreshTokenExpTime;
  }

  public Integer getTokenExpirationTime() {
    return tokenExpirationTime;
  }

  public void setTokenExpirationTime(Integer tokenExpirationTime) {
    this.tokenExpirationTime = tokenExpirationTime;
  }

  public String getTokenIssuer() {
    return tokenIssuer;
  }

  public void setTokenIssuer(String tokenIssuer) {
    this.tokenIssuer = tokenIssuer;
  }

  public String getTokenSigningKey() {
    return tokenSigningKey;
  }

  public void setTokenSigningKey(String tokenSigningKey) {
    this.tokenSigningKey = tokenSigningKey;
  }



}
