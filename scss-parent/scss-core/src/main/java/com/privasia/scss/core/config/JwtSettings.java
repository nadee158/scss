/**
 * 
 */
package com.privasia.scss.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

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
    private Integer tokenExpirationTime = new Integer(15);

    /**
     * Token issuer.
     */
    private String tokenIssuer = new String("http://scss.com.my");
    
    /**
     * Key is used to sign {@link JwtToken}.
     */
    private String tokenSigningKey = new String("xm8EV6Hy5RMFK4EEACIDAwQus");
    
    /**
     * {@link JwtToken} can be refreshed during this timeframe.
     */
    private Integer refreshTokenExpTime = new Integer(60);
    
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
