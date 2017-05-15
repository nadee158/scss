package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.GateOutMessage;

@Service("gateOutMessageService")
public class GateOutMessageService {

  private CSMCTLService csmctlService;

  private GOTTRCINFService gottrcinfService;

  @Autowired
  public void setCsmctlService(CSMCTLService csmctlService) {
    this.csmctlService = csmctlService;
  }

  @Autowired
  public void setGottrcinfService(GOTTRCINFService gottrcinfService) {
    this.gottrcinfService = gottrcinfService;
  }

  public GateOutMessage constructGateOutMessage(CosmosCommonValuesDTO commonValuesDTO, int index) {
    GateOutMessage gateOutMessage = new GateOutMessage();
    gateOutMessage.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
    gateOutMessage.setGOTTRCINF(gottrcinfService.constructGOTTRCINF(commonValuesDTO));
    gateOutMessage.setIndex(index);
    return gateOutMessage;
  }



}
