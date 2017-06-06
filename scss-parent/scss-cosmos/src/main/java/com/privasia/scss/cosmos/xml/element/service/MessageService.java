/**
 * 
 */
package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.Message;

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
	
	
	public Message constructGateInRootMessage(CosmosCommonValuesDTO commonValuesDTO) {
		Message message = new Message();
		message.setIndex(1);
		message.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
		message.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));
		message.setGOTTRCINF(null);
		return message;
	}

}
