package com.privasia.scss.core.dto;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



public class GateOutMessage implements Serializable {
	
	private static final long serialVersionUID = 1713804952766849123L;

	public static final String OK = "OK";
	public static final String NOK = "NOK";

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getCustomStatus() {
		return customStatus;
	}

	public void setCustomStatus(String customStatus) {
		this.customStatus = customStatus;
	}

	public String getCustomStatusDescription() {
		return customStatusDescription;
	}

	public void setCustomStatusDescription(String customStatusDescription) {
		this.customStatusDescription = customStatusDescription;
	}

	private String code; // OK, NOK
	private String description;
	private String customStatus;
	private String customStatusDescription;
	
	public String toString(){
	    return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
