package com.privasia.scss.cosmos.dto.response;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

import com.privasia.scss.cosmos.dto.expresponse.XMLERRINF;

public class Message {

	CSMCTL CSMCTL;
	XMLERRINF XMLERRINF;
	GINTRCINFR GINTRCINFR;
	GINCNTDRPR GINCNTDRPR;
	GINCNTPUPR GINCNTPUPR;
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

	public GINTRCINFR getGINTRCINFR() {
		return GINTRCINFR;
	}

	@XmlElement
	public void setGINTRCINFR(GINTRCINFR gINTRCINFR) {
		GINTRCINFR = gINTRCINFR;
	}

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

	public GINCNTDRPR getGINCNTDRPR() {
		return GINCNTDRPR;
	}

	@XmlElement
	public void setGINCNTDRPR(GINCNTDRPR gINCNTDRPR) {
		GINCNTDRPR = gINCNTDRPR;
	}

	public GINCNTPUPR getGINCNTPUPR() {
		return GINCNTPUPR;
	}

	@XmlElement
	public void setGINCNTPUPR(GINCNTPUPR gINCNTPUPR) {
		GINCNTPUPR = gINCNTPUPR;
	}

	@Override
	public String toString() {
		return "Message [CSMCTL=" + CSMCTL + ", XMLERRINF=" + XMLERRINF + ", GINTRCINFR=" + GINTRCINFR + ", GINCNTDRPR="
				+ GINCNTDRPR + ", GINCNTPUPR=" + GINCNTPUPR + ", Index=" + Index + "]";
	}
	
	

}
