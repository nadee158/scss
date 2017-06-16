package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;



public class SGS2CosmosRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private RequestMessage requestMessage;
	
	public RequestMessage getMessage() {
		return requestMessage;
	}
	
	@XmlElement(name = "Message")
	public void setMessage(RequestMessage requestMessage) {
		this.requestMessage = requestMessage;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
