package com.privasia.scss.cosmos.dto.response;

import javax.xml.bind.annotation.XmlElement;

public class GINCNTDRPR {

	String PSIDSE;
	String PKIDSE;

	public String getPSIDSE() {
		return PSIDSE;
	}

	@XmlElement
	public void setPSIDSE(String pSIDSE) {
		PSIDSE = pSIDSE;
	}

	public String getPKIDSE() {
		return PKIDSE;
	}

	@XmlElement
	public void setPKIDSE(String pKIDSE) {
		PKIDSE = pKIDSE;
	}

	@Override
	public String toString() {
		return "GINCNTDRPR [PSIDSE=" + PSIDSE + ", PKIDSE=" + PKIDSE + "]";
	}
	
	
	

}
