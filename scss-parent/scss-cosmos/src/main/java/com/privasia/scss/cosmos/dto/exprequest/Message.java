package com.privasia.scss.cosmos.dto.exprequest;

import javax.xml.bind.annotation.XmlAttribute;

import com.sun.xml.internal.txw2.annotation.XmlElement;

public class Message {

	CSMCTL CSMCTL;
	GOTTRCINF GOTTRCINF;
	int Index;

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	@XmlElement
	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	public GOTTRCINF getGOTTRCINF() {
		return GOTTRCINF;
	}

	@XmlElement
	public void setGOTTRCINF(GOTTRCINF gOTTRCINF) {
		GOTTRCINF = gOTTRCINF;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}
	
	

}
