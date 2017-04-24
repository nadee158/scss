package com.privasia.scss.cosmos.dto.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CosmosGateOutImport {

	CSMCTL CSMCTL;
	GOTTRCINF GOTTRCINF;
	GOTCNTINF GOTCNTINF;
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

	public GOTCNTINF getGOTCNTINF() {
		return GOTCNTINF;
	}

	@XmlElement
	public void setGOTCNTINF(GOTCNTINF gOTCNTINF) {
		GOTCNTINF = gOTCNTINF;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

}
