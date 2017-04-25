package com.privasia.scss.cosmos.dto.request;

import javax.xml.bind.annotation.XmlElement;

public class GOTCNTINF {

	String UNITSE;

	String SO01SE;
	String ST01SE;
	String SN01SE;

	String SO02SE;
	String ST02SE;
	String SN02SE;

	public String getUNITSE() {
		return UNITSE;
	}

	@XmlElement
	public void setUNITSE(String uNITSE) {
		UNITSE = uNITSE;
	}

	public String getSO01SE() {
		return SO01SE;
	}

	@XmlElement
	public void setSO01SE(String sO01SE) {
		SO01SE = sO01SE;
	}

	public String getST01SE() {
		return ST01SE;
	}

	@XmlElement
	public void setST01SE(String sT01SE) {
		ST01SE = sT01SE;
	}

	public String getSN01SE() {
		return SN01SE;
	}

	@XmlElement
	public void setSN01SE(String sN01SE) {
		SN01SE = sN01SE;
	}

	public String getSO02SE() {
		return SO02SE;
	}

	@XmlElement
	public void setSO02SE(String sO02SE) {
		SO02SE = sO02SE;
	}

	public String getST02SE() {
		return ST02SE;
	}

	@XmlElement
	public void setST02SE(String sT02SE) {
		ST02SE = sT02SE;
	}

	public String getSN02SE() {
		return SN02SE;
	}

	@XmlElement
	public void setSN02SE(String sN02SE) {
		SN02SE = sN02SE;
	}

}
