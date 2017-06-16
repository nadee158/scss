package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class RequestMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	private int Index;
	private CSMCTL CSMCTL;
	private GINTRCINF GINTRCINF;
	private GOTTRCINF GOTTRCINF;

	public int getIndex() {
		return Index;
	}

	@XmlAttribute
	public void setIndex(int index) {
		Index = index;
	}

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	public GINTRCINF getGINTRCINF() {
		return GINTRCINF;
	}

	public void setGINTRCINF(GINTRCINF gINTRCINF) {
		GINTRCINF = gINTRCINF;
	}

	public GOTTRCINF getGOTTRCINF() {
		return GOTTRCINF;
	}

	public void setGOTTRCINF(GOTTRCINF gOTTRCINF) {
		GOTTRCINF = gOTTRCINF;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
