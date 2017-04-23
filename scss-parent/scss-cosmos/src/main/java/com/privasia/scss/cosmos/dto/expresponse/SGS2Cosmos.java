package com.privasia.scss.cosmos.dto.expresponse;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "SGS2Cosmos")
public class SGS2Cosmos {

	Message message;

	public Message getMessage() {
		return message;
	}

	@XmlElement(name = "Message")
	public void setMessage(Message message) {
		this.message = message;
	}

}
