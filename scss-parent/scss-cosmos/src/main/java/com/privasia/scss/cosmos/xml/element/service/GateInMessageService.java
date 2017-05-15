package com.privasia.scss.cosmos.xml.element.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.cosmos.dto.common.CosmosCommonValuesDTO;
import com.privasia.scss.cosmos.xml.element.GateInMessage;

@Service("gateInMessageService")
public class GateInMessageService {

  private CSMCTLService csmctlService;

  private GINTRCINFService gintrcinfService;

  @Autowired
  public void setCsmctlService(CSMCTLService csmctlService) {
    this.csmctlService = csmctlService;
  }

  @Autowired
  public void setGintrcinfService(GINTRCINFService gintrcinfService) {
    this.gintrcinfService = gintrcinfService;
  }

  public GateInMessage constructGateInMessage(CosmosCommonValuesDTO commonValuesDTO, int index) {
    GateInMessage gateInMessage = new GateInMessage();
    gateInMessage.setCSMCTL(csmctlService.constructCSMCTL(commonValuesDTO));
    gateInMessage.setGINTRCINF(gintrcinfService.constructGINTRCINF(commonValuesDTO));
    gateInMessage.setIndex(index);
    return gateInMessage;
  }

}
