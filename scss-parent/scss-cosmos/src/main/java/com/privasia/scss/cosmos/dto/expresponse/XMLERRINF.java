package com.privasia.scss.cosmos.dto.expresponse;

import javax.xml.bind.annotation.XmlElement;

public class XMLERRINF {

	String TAGCSG;

	public String getTAGCSG() {
		return TAGCSG;
	}

	@XmlElement
	public void setTAGCSG(String tAGCSG) {
		TAGCSG = tAGCSG;
	}

	@Override
	public String toString() {
		return "XMLERRINF [TAGCSG=" + TAGCSG + "]";
	}
	
	
	
}
