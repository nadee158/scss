package com.privasia.scss.cosmos.dto.expresponse;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class Message {

	CSMCTL CSMCTL;
	XMLERRINF XMLERRINF;
	int Index;

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	@XmlElement
	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	public XMLERRINF getXMLERRINF() {
		return XMLERRINF;
	}

	@XmlElement
	public void setXMLERRINF(XMLERRINF xMLERRINF) {
		XMLERRINF = xMLERRINF;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

}
