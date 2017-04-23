package com.privasia.scss.cosmos.dto.expresponse;

import javax.xml.bind.annotation.XmlElement;

public class CSMCTL {

	String RTNC;
	String ERRI;
	String ERRQ;
	String MSGF;
	String RQLD;
	String RQDS;
	String RSDS;

	public String getRTNC() {
		return RTNC;
	}

	@XmlElement
	public void setRTNC(String rTNC) {
		RTNC = rTNC;
	}

	public String getERRI() {
		return ERRI;
	}

	@XmlElement
	public void setERRI(String eRRI) {
		ERRI = eRRI;
	}

	public String getERRQ() {
		return ERRQ;
	}

	@XmlElement
	public void setERRQ(String eRRQ) {
		ERRQ = eRRQ;
	}

	public String getMSGF() {
		return MSGF;
	}

	@XmlElement
	public void setMSGF(String mSGF) {
		MSGF = mSGF;
	}

	public String getRQLD() {
		return RQLD;
	}

	@XmlElement
	public void setRQLD(String rQLD) {
		RQLD = rQLD;
	}

	public String getRQDS() {
		return RQDS;
	}

	@XmlElement
	public void setRQDS(String rQDS) {
		RQDS = rQDS;
	}

	public String getRSDS() {
		return RSDS;
	}

	@XmlElement
	public void setRSDS(String rSDS) {
		RSDS = rSDS;
	}

}
