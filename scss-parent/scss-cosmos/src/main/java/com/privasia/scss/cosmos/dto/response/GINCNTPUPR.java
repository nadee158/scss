package com.privasia.scss.cosmos.dto.response;

import javax.xml.bind.annotation.XmlElement;

public class GINCNTPUPR {

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
		return "GINCNTPUPR [PSIDSE=" + PSIDSE + ", PKIDSE=" + PKIDSE + "]";
	}
	
	

}
