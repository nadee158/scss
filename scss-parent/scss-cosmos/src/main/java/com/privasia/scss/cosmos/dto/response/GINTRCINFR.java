package com.privasia.scss.cosmos.dto.response;

import javax.xml.bind.annotation.XmlElement;

public class GINTRCINFR {

	String BZKNSC;

	public String getBZKNSC() {
		return BZKNSC;
	}

	@XmlElement
	public void setBZKNSC(String bZKNSC) {
		BZKNSC = bZKNSC;
	}

	@Override
	public String toString() {
		return "GINTRCINFR [BZKNSC=" + BZKNSC + "]";
	}
	
	

}
