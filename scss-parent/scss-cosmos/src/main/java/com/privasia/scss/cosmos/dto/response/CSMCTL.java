package com.privasia.scss.cosmos.dto.response;

import javax.xml.bind.annotation.XmlElement;

public class CSMCTL {

	String ERRI;

	public String getERRI() {
		return ERRI;
	}

	@XmlElement
	public void setERRI(String eRRI) {
		ERRI = eRRI;
	}

	@Override
	public String toString() {
		return "CSMCTL [ERRI=" + ERRI + "]";
	}
	
	


}
