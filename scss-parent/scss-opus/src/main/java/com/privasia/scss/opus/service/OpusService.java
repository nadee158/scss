package com.privasia.scss.opus.service;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusGateOutReadRequest;
import com.privasia.scss.opus.dto.OpusGateOutReadResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;

@Service("opusService")
public class OpusService implements OpusCosmosBusinessService {

  private static final Log log = LogFactory.getLog(OpusService.class);

  private OpusGateInWriteService opusGateInWriteService;

  private OpusDTOConstructService opusDTOConstructService;

  private OpusGateOutReadService opusGateOutReadService;

  private Gson gson;

  @Autowired
  public void setOpusGateOutReadService(OpusGateOutReadService opusGateOutReadService) {
    this.opusGateOutReadService = opusGateOutReadService;
  }

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
  public GateInReponse sendGateInRequest(GateInWriteRequest gateInWriteRequest) {
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

  @Override
  public GateOutReponse sendGateOutReadRequest(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {
    OpusGateOutReadRequest gateOutReadRequest = opusGateOutReadService.constructOpenGateOutRequest(gateOutRequest);

    OpusRequestResponseDTO opusRequestResponseDTO =
        new OpusRequestResponseDTO(gateOutReadRequest, gson, gateOutRequest.getCardID());
    System.out.println("populateGateOut :: opusRequestResponseDTO " + opusRequestResponseDTO);

    OpusGateOutReadResponse gateOutReadResponse =
        opusGateOutReadService.getGateOutReadResponse(gateOutReadRequest, opusRequestResponseDTO);
    // check the errorlist of reponse
    String errorMessage = opusDTOConstructService.hasErrorMessage(gateOutReadResponse.getErrorList());
    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
    if (StringUtils.isNotEmpty(errorMessage)) {
      // save it to the db - TO BE IMPLEMENTED
      // throw new business exception with constructed message - there
      // is
      // an error
      System.err.println(errorMessage);
      throw new BusinessException(errorMessage);
    }
    return opusGateOutReadService.constructGateOutReponse(gateOutReadResponse, gateOutReponse);
  }

}
