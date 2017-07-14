/**
 * 
 */
package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.RequestMessage;

/**
 * @author Janaka
 *
 */
@Service("messageService")
public class MessageService {
	
	private CSMCTLService csmctlService;

	private GINTRCINFService gintrcinfService;

	private GOTTRCINFService gottrcinfService;
	
	@Autowired
	public void setCsmctlService(CSMCTLService csmctlService) {
		this.csmctlService = csmctlService;
	}

	@Autowired
	public void setGintrcinfService(GINTRCINFService gintrcinfService) {
		this.gintrcinfService = gintrcinfService;
	}

	@Autowired
	public void setGottrcinfService(GOTTRCINFService gottrcinfService) {
		this.gottrcinfService = gottrcinfService;
	}
	
	
	public RequestMessage constructGateInRootMessage(CosmosCommonValuesDTO commonValuesDTO) {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setIndex(1);
		requestMessage.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
		requestMessage.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));
		requestMessage.setGOTTRCINF(null);
		return requestMessage;
	}
	public RequestMessage constructGateOutRootMessage(CosmosCommonValuesDTO commonValuesDTO) {
		RequestMessage requestMessage = new RequestMessage();
		requestMessage.setIndex(1);
		requestMessage.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
		requestMessage.setGOTTRCINF(gottrcinfService.constructGOTTRCINF(commonValuesDTO));
		requestMessage.setGINTRCINF(null);
		return requestMessage;
	}
}
