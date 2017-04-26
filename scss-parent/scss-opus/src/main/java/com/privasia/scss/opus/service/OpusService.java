package com.privasia.scss.opus.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privasia.scss.common.business.ExternalContainerInformationService;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

@Service("opusService")
public class OpusService implements ExternalContainerInformationService {

  private static final Log log = LogFactory.getLog(OpusService.class);

  private OpusGateInWriteService opusGateInWriteService;

  private OpusDTOConstructService opusDTOConstructService;

  private Gson gson;

  @Autowired
  public void setOpusGateInWriteService(OpusGateInWriteService opusGateInWriteService) {
    this.opusGateInWriteService = opusGateInWriteService;
  }

  @Autowired
  public void setOpusDTOConstructService(OpusDTOConstructService opusDTOConstructService) {
    this.opusDTOConstructService = opusDTOConstructService;
  }

  @Autowired
  public void setGson(Gson gson) {
    this.gson = gson;
  }

  @Override
  public GateInReponse sendGateinRequest(GateInWriteRequest gateInWriteRequest) {
    OpusGateInWriteRequest opusGateInWriteRequest =
        opusGateInWriteService.constructOpusGateInWriteRequest(gateInWriteRequest);
    System.out.println("opusGateInWriteRequest " + gson.toJson(opusGateInWriteRequest));

    OpusRequestResponseDTO opusRequestResponseDTO =
        new OpusRequestResponseDTO(opusGateInWriteRequest, gson, gateInWriteRequest.getCardId());
    System.out.println("saveGateInInfo :: opusRequestResponseDTO " + opusRequestResponseDTO);

    OpusGateInWriteResponse opusGateInWriteResponse =
        opusGateInWriteService.getGateInWriteResponse(opusGateInWriteRequest, opusRequestResponseDTO);

    System.out.println("opusGateInWriteResponse " + gson.toJson(opusGateInWriteResponse));
    String errorMessage = opusDTOConstructService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
    if (StringUtils.isNotEmpty(errorMessage)) {
      // save it to the db - TO BE IMPLEMENTED
      // throw new business exception with constructed message - there is
      // an error
      throw new BusinessException(errorMessage);
    }

    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setImportContainers(gateInWriteRequest.getImportContainers());
    gateInReponse.setExportContainers(gateInWriteRequest.getExportContainers());
    gateInReponse = opusGateInWriteService.constructGateInReponse(opusGateInWriteResponse, gateInReponse);
    return gateInReponse;
  }

}
