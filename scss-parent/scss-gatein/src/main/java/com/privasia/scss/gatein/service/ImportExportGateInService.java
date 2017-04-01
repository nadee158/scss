package com.privasia.scss.gatein.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.service.OpusGateInReadService;
import com.privasia.scss.opus.service.OpusGateInWriteService;
import com.privasia.scss.opus.service.OpusService;

@Service("importExportGateInService")
public class ImportExportGateInService {

  private static final Log log = LogFactory.getLog(ImportExportGateInService.class);

  private ImportGateInService importGateInService;

  private ExportGateInService exportGateInService;

  private OpusGateInReadService opusGateInReadService;

  private OpusGateInWriteService opusGateInWriteService;

  private ClientRepository clientRepository;

  private CardRepository cardRepository;

  private OpusService opusService;

  @Autowired
  public void setOpusService(OpusService opusService) {
    this.opusService = opusService;
  }

  @Autowired
  public void setOpusGateInReadService(OpusGateInReadService opusGateInReadService) {
    this.opusGateInReadService = opusGateInReadService;
  }

  @Autowired
  public void setOpusGateInWriteService(OpusGateInWriteService opusGateInWriteService) {
    this.opusGateInWriteService = opusGateInWriteService;
  }

  @Autowired
  public void setImportGateInService(ImportGateInService importGateInService) {
    this.importGateInService = importGateInService;
  }

  @Autowired
  public void setExportGateInService(ExportGateInService exportGateInService) {
    this.exportGateInService = exportGateInService;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateIn(GateInRequest gateInRequest) {

    Optional<Card> cardOpt = cardRepository.findOne(gateInRequest.getCardID());
    Card card =
        cardOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid Card ID ! " + gateInRequest.getCardID()));
    gateInRequest.setHaulageCode(card.getCompany().getCompanyCode());

    Optional<Client> clientOpt = clientRepository.findOne(gateInRequest.getLaneId());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateInRequest.getLaneId()));
    gateInRequest.setLaneNo(client.getLaneNo());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    System.out.println("userContext.getUsername() " + userContext.getUsername());
    gateInRequest.setUserName(userContext.getUsername());

    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setCheckPreArrival(gateInRequest.isCheckPreArrival());

    if ((gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() > 0)
        || (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() > 0)) {
      List<Long> gatePassList = Arrays.asList(gateInRequest.getGatePass1(), gateInRequest.getGatePass2());
      List<ImportContainer> importContainerList = importGateInService.fetchContainerInfo(gatePassList);
      gateInReponse.setImportContainers(importContainerList);
    }

    // call opus -
    OpusGateInReadRequest gateInReadRequest = opusGateInReadService.constructOpenGateInRequest(gateInRequest);
    OpusGateInReadResponse gateInReadResponse = opusGateInReadService.getGateInReadResponse(gateInReadRequest);

    // check the errorlist of reponse
    String errorMessage = opusService.hasErrorMessage(gateInReadResponse.getErrorList());
    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
    if (StringUtils.isNotEmpty(errorMessage)) {
      // save it to the db - TO BE IMPLEMENTED
      // throw new business exception with constructed message - there is
      // an error
      throw new BusinessException(errorMessage);
    }

    // double check with the documentation
    gateInReponse = opusGateInReadService.constructGateInReponse(gateInReadResponse, gateInReponse);

    if (!(StringUtils.isEmpty(gateInRequest.getExpContainer1())
        || StringUtils.isEmpty(gateInRequest.getExpContainer2()))) {
      gateInReponse = exportGateInService.populateGateInExports(gateInReponse);
    }

    if ((gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() > 0)
        || (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() > 0)) {
      importGateInService.populateGateInImport(gateInReponse);
    }

    return gateInReponse;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateInReponse saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    List<ImportContainer> importContainers = null;
    List<ExportContainer> exportContainers = null;
    Gson gson = new Gson();
    OpusGateInWriteRequest opusGateInWriteRequest =
        opusGateInWriteService.constructOpusGateInWriteRequest(gateInWriteRequest);
    System.out.println("opusGateInWriteRequest " + gson.toJson(opusGateInWriteRequest));

    OpusGateInWriteResponse opusGateInWriteResponse =
        opusGateInWriteService.getGateInWriteResponse(opusGateInWriteRequest);
    System.out.println("opusGateInWriteResponse " + gson.toJson(opusGateInWriteResponse));
    String errorMessage = opusService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
    if (StringUtils.isNotEmpty(errorMessage)) {
      // save it to the db - TO BE IMPLEMENTED
      // throw new business exception with constructed message - there is
      // an error
      throw new BusinessException(errorMessage);
    }


    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    gateInWriteRequest.setUserName(userContext.getUsername());

    if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
      exportContainers = exportGateInService.saveGateInInfo(gateInWriteRequest);
    }

    if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
      importContainers = importGateInService.saveGateInInfo(gateInWriteRequest);
    }

    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setImportContainers(importContainers);
    gateInReponse.setExportContainers(exportContainers);
    gateInReponse = opusGateInWriteService.constructGateInReponse(opusGateInWriteResponse, gateInReponse);

    return gateInReponse;
  }

}
