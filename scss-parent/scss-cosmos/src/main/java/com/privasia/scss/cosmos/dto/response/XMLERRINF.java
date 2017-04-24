package com.privasia.scss.cosmos.dto.response;

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
	
}
