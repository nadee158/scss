package com.privasia.scss.common.security.model;

/**
 * @author Janaka
 *
 */

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.security.core.GrantedAuthority;

public class UserContext {

	private final Long userID;
	private final String username;
	private final String staffName;
	private final String staffNumber;
	private final String clientIP;
	private final List<GrantedAuthority> authorities;
	private final List<Long> functions;

	private UserContext(Long userID, String username, List<GrantedAuthority> authorities, List<Long> functions,
			String staffName, String staffNumber, String clientIP) {
		this.userID = userID;
		this.username = username;
		this.authorities = authorities;
		this.functions = functions;
		this.staffName = staffName;
		this.staffNumber = staffNumber;
		this.clientIP = clientIP;
	}

	public static UserContext create(Long userID, String username, List<GrantedAuthority> authorities,
			List<Long> functions, String staffName, String staffNumber, String clientIP) {
		if (StringUtils.isBlank(username))
			throw new IllegalArgumentException("Username is blank: " + username);
		return new UserContext(userID, username, authorities, functions, staffName, staffNumber, clientIP);
	}

	public String getUsername() {
		return username;
	}

	public List<GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public List<Long> getFunctions() {
		return functions;
	}

	public String getStaffName() {
		return staffName;
	}

	public String getStaffNumber() {
		return staffNumber;
	}

	public Long getUserID() {
		return userID;
	}

	public String getClientIP() {
		return clientIP;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
