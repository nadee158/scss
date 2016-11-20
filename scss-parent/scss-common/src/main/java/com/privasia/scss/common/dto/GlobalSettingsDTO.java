package com.privasia.scss.common.dto;

import java.io.Serializable;

public class GlobalSettingsDTO implements Serializable {
	private static final long serialVersionUID = -9082356106505693557L;

	public static final String SMTP_SUBJECT = "SMTP_SUBJ";
	public static final String SMTP_HOST = "SMTP_HOST";
	public static final String SMTP_FROM = "SMTP_FROM";
	public static final String SMTP_TO = "SMTP_TO";
	public static final String SMTP_CC = "SMTP_CC";
	public static final String SMTP_BCC = "SMTP_BCC";

	public static final String GLOBAL_CODE = "globalCode";
	public static final String GLOBAL_NAME = "globalName";
	public static final String GLOBAL_ITEMS = "globalItems";
	public static final String GLOBAL_AMOUNT = "globalAmount";
	public static final String GLOBAL_STRING = "globalString";
	public static final String PARAM_VALUE1 = "paramValue1";
	
	public static final String HDBS_START_HOUR = "HDBS_START";
	public static final String HDBS_END_HOUR = "HDBS_END";
	
	public static final String HDBS_ACCEPTED_START = "HDBS_ACC_S";
	public static final String HDBS_ACCEPTED_END = "HDBS_ACC_E";
	public static final String HDBS_MANUAL = "HDBS_MANU";
	
	public static final String REPORT_MAX_DATE = "REPT_MAX";
	public static final String REPORT_TRANSCATION_MAX_DATE = "R_TRAN_MAX";

	private String globalCode;
	private String globalName;
	private Long globalItems;
	private Double globalAmount;
	private String globalString;
	private String paramValue1;

	public GlobalSettingsDTO() {
	}

	public GlobalSettingsDTO(String globalCode) {
		this.globalCode = globalCode;
	}

	public GlobalSettingsDTO(boolean defaultValue) {
		if (defaultValue) {
		}
	}

	public String getGlobalCode() {
		return globalCode;
	}

	public String getGlobalName() {
		return globalName;
	}

	public Long getGlobalItems() {
		return globalItems;
	}

	public Double getGlobalAmount() {
		return globalAmount;
	}

	public String getGlobalString() {
		return globalString;
	}

	public void setGlobalCode(String globalCode) {
		this.globalCode = globalCode;
	}

	public void setGlobalName(String globalName) {
		this.globalName = globalName;
	}

	public void setGlobalItems(Long globalItems) {
		this.globalItems = globalItems;
	}

	public void setGlobalAmount(Double globalAmount) {
		this.globalAmount = globalAmount;
	}

	public void setGlobalString(String globalString) {
		this.globalString = globalString;
	}

	public String getParamValue1() {
		return paramValue1;
	}

	public void setParamValue1(String paramValue1) {
		this.paramValue1 = paramValue1;
	}

}
