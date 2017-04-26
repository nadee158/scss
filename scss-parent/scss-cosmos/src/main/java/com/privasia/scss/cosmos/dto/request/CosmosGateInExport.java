package com.privasia.scss.cosmos.dto.request;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class CosmosGateInExport {

	CSMCTL CSMCTL;
	GINCNTDRP GINCNTDRP;
	int Index;

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	@XmlElement
	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	public GINCNTDRP getGINCNTDRP() {
		return GINCNTDRP;
	}

	@XmlElement
	public void setGINCNTDRP(GINCNTDRP gINCNTDRP) {
		GINCNTDRP = gINCNTDRP;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

}
