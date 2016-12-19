package com.privasia.scss.common.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

public class GateInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private boolean allowGateIn = true;

	private String message = StringUtils.EMPTY;
	
	private Long cardID = null;

	public GateInfo() {
		super();

	}

	public boolean isAllowGateIn() {
		return allowGateIn;
	}

	public void setAllowGateIn(boolean allowGateIn) {
		this.allowGateIn = allowGateIn;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCardID() {
		return cardID;
	}

	public void setCardID(Long cardID) {
		this.cardID = cardID;
	}
	
}
