package com.privasia.scss.common.dto;

import java.util.List;

public class ScssClientDto {
	public static final String GATE ="GATE";
	public static final String PKFZ ="PKFZ";
	public static final String ITD ="ITD";
	
	private String cliType;
	private String cliDescription;
	private List<String> gateType;

	public ScssClientDto() {
	}

	public String getCliType() {
		return cliType;
	}

	public void setCliType(String cliType) {
		this.cliType = cliType;
	}

	public String getCliDescription() {
		return cliDescription;
	}

	public void setCliDescription(String cliDescription) {
		this.cliDescription = cliDescription;
	}

	public List<String> getGateType() {
		return gateType;
	}

	public void setGateType(List<String> gateType) {
		this.gateType = gateType;
	}	
}
