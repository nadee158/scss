package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class GINCNTPUPR implements Serializable {

	private static final long serialVersionUID = 1L;
	private String PSIDSE;
	private String PKIDSE;
	private String UNITSE;

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
	
	@XmlElement
	public String getUNITSE() {
		return UNITSE;
	}

	public void setUNITSE(String uNITSE) {
		UNITSE = uNITSE;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
