package com.privasia.scss.cosmos.service;

import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;

@Service("cosmosService")
public class CosmosService implements OpusCosmosBusinessService {

  @Override
  public GateInReponse sendGateInRequest(GateInWriteRequest gateInWriteRequest) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
    // TODO Auto-generated method stub
    // gateOutReponse-transaction type - switch
    if (!(gateOutReponse.getImportContainers() == null || gateOutReponse.getImportContainers().isEmpty())) {

    }
    return gateOutReponse;
  }

}
