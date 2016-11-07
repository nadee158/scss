package com.privasia.scss.scancard.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Janaka
 *
 */
public class CardValidationDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3307259032020005634L;
	Long cardId;
	boolean isValid = false;
	String validationMessage = StringUtils.EMPTY;
	String cardStatus = StringUtils.EMPTY;

	public CardValidationDto(Long cardId, boolean isValid, String validationMessage, String cardStatus) {
		super();
		this.cardId = cardId;
		this.isValid = isValid;
		this.validationMessage = validationMessage;
		this.cardStatus = cardStatus;
	}

	public String getValidationMessage() {
		return validationMessage;
	}

	public void setValidationMessage(String validationMessage) {
		this.validationMessage = validationMessage;
	}

	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public Long getCardId() {
		return cardId;
	}

	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}

	

}
