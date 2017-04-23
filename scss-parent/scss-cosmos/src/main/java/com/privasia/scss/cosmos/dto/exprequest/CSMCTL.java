package com.privasia.scss.cosmos.dto.exprequest;

import com.sun.xml.internal.txw2.annotation.XmlElement;

public class CSMCTL {

	String RQST;
	String ACTN;
	String RTNC;
	String ERRI;
	String RQDS;
	String RTNM;
	String USID;
	String RQUI;
	String TRMC;

	public String getRQST() {
		return RQST;
	}

	@XmlElement
	public void setRQST(String rQST) {
		RQST = rQST;
	}

	public String getACTN() {
		return ACTN;
	}

	@XmlElement
	public void setACTN(String aCTN) {
		ACTN = aCTN;
	}

	public String getRTNC() {
		return RTNC;
	}

	@XmlElement
	public void setRTNC(String rTNC) {
		RTNC = rTNC;
	}

	public String getRQDS() {
		return RQDS;
	}

	@XmlElement
	public void setRQDS(String rQDS) {
		RQDS = rQDS;
	}

	public String getRTNM() {
		return RTNM;
	}

	@XmlElement
	public void setRTNM(String rTNM) {
		RTNM = rTNM;
	}

	public String getUSID() {
		return USID;
	}

	@XmlElement
	public void setUSID(String uSID) {
		USID = uSID;
	}

	public String getRQUI() {
		return RQUI;
	}

	@XmlElement
	public void setRQUI(String rQUI) {
		RQUI = rQUI;
	}

	public String getTRMC() {
		return TRMC;
	}

	@XmlElement
	public void setTRMC(String tRMC) {
		TRMC = tRMC;
	}

	public String getERRI() {
		return ERRI;
	}

	@XmlElement
	public void setERRI(String eRRI) {
		ERRI = eRRI;
	}
	
	

}
