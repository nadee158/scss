package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;

@XmlRootElement(name = "SGS2Cosmos")
public class SGS2CosmosRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int Index;
	private CSMCTL CSMCTL;
	private GINTRCINF GINTRCINF;
	private CosmosGateInWriteRequest cosmosGateInWriteRequest;
	
	public int getIndex() {
		return Index;
	}

	@XmlElement(name = "Message")
	public void setIndex(int index) {
		Index = index;
	}

	public CSMCTL getCSMCTL() {
		return CSMCTL;
	}

	@XmlElement(name = "Message")
	public void setCSMCTL(CSMCTL cSMCTL) {
		CSMCTL = cSMCTL;
	}

	public GINTRCINF getGINTRCINF() {
		return GINTRCINF;
	}

	@XmlElement(name = "Message")
	public void setGINTRCINF(GINTRCINF gINTRCINF) {
		GINTRCINF = gINTRCINF;
	}

	public CosmosGateInWriteRequest getCosmosGateInWriteRequest() {
		return cosmosGateInWriteRequest;
	}

	public void setCosmosGateInWriteRequest(CosmosGateInWriteRequest cosmosGateInWriteRequest) {
		this.cosmosGateInWriteRequest = cosmosGateInWriteRequest;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
