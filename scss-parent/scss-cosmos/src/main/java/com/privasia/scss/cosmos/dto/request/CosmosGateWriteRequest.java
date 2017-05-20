package com.privasia.scss.cosmos.dto.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.xml.element.CSMCTL;

public class CosmosGateWriteRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int Index;
	private CSMCTL CSMCTL;
	
	
	public int getIndex() {
		return Index;
	}

	@XmlElement(name = "Message")
	public void setIndex(int index) {
		Index = index;
	}

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	@XmlElement(name = "Message")
	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
