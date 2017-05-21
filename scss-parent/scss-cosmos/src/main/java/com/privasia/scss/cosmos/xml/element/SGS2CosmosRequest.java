package com.privasia.scss.cosmos.xml.element;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.privasia.scss.cosmos.dto.request.CosmosGateInWriteRequest;
import com.privasia.scss.cosmos.dto.request.CosmosGateOutWriteRequest;

@XmlRootElement(name = "SGS2Cosmos")
public class SGS2CosmosRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int Index;
	private CSMCTL CSMCTL;
	private GINTRCINF GINTRCINF;
	private GOTTRCINF GOTTRCINF;
	private CosmosGateInWriteRequest cosmosGateInWriteRequest;
	private CosmosGateOutWriteRequest cosmosGateOutWriteRequest;
	
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

	public GOTTRCINF getGOTTRCINF() {
		return GOTTRCINF;
	}
	
	@XmlElement(name = "Message")
	public void setGOTTRCINF(GOTTRCINF gOTTRCINF) {
		GOTTRCINF = gOTTRCINF;
	}

	public CosmosGateInWriteRequest getCosmosGateInWriteRequest() {
		return cosmosGateInWriteRequest;
	}

	public void setCosmosGateInWriteRequest(CosmosGateInWriteRequest cosmosGateInWriteRequest) {
		this.cosmosGateInWriteRequest = cosmosGateInWriteRequest;
	}

	public CosmosGateOutWriteRequest getCosmosGateOutWriteRequest() {
		return cosmosGateOutWriteRequest;
	}

	public void setCosmosGateOutWriteRequest(CosmosGateOutWriteRequest cosmosGateOutWriteRequest) {
		this.cosmosGateOutWriteRequest = cosmosGateOutWriteRequest;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
