package com.privasia.scss.common.business;

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;

public interface ExternalContainerInformationService {

  public GateInReponse sendGateinRequest(GateInWriteRequest gateInWriteRequest);

}
