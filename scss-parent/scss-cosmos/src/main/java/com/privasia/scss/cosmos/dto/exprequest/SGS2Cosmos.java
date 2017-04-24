package com.privasia.scss.cosmos.dto.exprequest;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="SGS2Cosmos")
public class SGS2Cosmos {

	List<Message> message;

	public List<Message> getMessage() {
		return message;
	}

	@XmlElement(name="Message")
	public void setMessage(List<Message> Message) {
		this.message = Message;
	}

}
