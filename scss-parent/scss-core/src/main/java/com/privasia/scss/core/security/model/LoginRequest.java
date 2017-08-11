/**
 * 
 */
package com.privasia.scss.core.security.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author Janaka
 *
 */
public class LoginRequest {
	
	private String username;
    private String password;
    private String clientIP;

    @JsonCreator
    public LoginRequest(@JsonProperty("username") String username, @JsonProperty("password") String password,
    		@JsonProperty("clientIP") String clientIP) {
        this.username = username;
        this.password = password;
        this.clientIP = clientIP;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

	public String getClientIP() {
		return clientIP;
	}
    
    

}
