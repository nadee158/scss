package com.privasia.scss.cosmos.dto.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CosmosGateInImport {

	CSMCTL CSMCTL;
	GOTTRCINF GOTTRCINF;
	GINCNTPUP GINCNTPUP;
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

	public GINCNTPUP getGINCNTPUP() {
		return GINCNTPUP;
	}

	@XmlElement
	public void setGINCNTPUP(GINCNTPUP gINCNTPUP) {
		GINCNTPUP = gINCNTPUP;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

}
