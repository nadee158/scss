package com.privasia.scss.cosmos.dto.common;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class CosmosCommonValuesDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String msgUniqueId;
	private String date;
	private String time;
	private String laneNo;
	private String truckNo;
	private String compCode;
	private String loginUser;

	public String getMsgUniqueId() {
		return msgUniqueId;
	}

	public void setMsgUniqueId(String msgUniqueId) {
		this.msgUniqueId = msgUniqueId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getLaneNo() {
		return laneNo;
	}

	public void setLaneNo(String laneNo) {
		this.laneNo = laneNo;
	}

	public String getTruckNo() {
		return truckNo;
	}

	public void setTruckNo(String truckNo) {
		this.truckNo = truckNo;
	}

	public String getCompCode() {
		return compCode;
	}

	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(String loginUser) {
		this.loginUser = loginUser;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
