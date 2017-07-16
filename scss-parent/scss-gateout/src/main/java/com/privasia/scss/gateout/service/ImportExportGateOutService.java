package com.privasia.scss.gateout.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.common.interfaces.OpusCosmosBusinessService;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.gateout.imports.business.service.PortService;
import com.privasia.scss.gateout.imports.exports.business.service.ImportExportCommonGateOutBusinessService;
import com.privasia.scss.gateout.whodd.service.ODDGateOutService;

@Service("importExportGateOutService")
public class ImportExportGateOutService {

  private static final Log log = LogFactory.getLog(ImportExportGateOutService.class);

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  @Value("${service.implementor}")
  private String implementor;

  private ImportGateOutService importGateOutService;

  private ExportGateOutService exportGateOutService;

  private ODDGateOutService oddGateOutService;

  private CommonCardService commonCardService;

  private ClientRepository clientRepository;

  private CardRepository cardRepository;

  private SystemUserRepository systemUserRepository;

  private ImportExportCommonGateOutBusinessService importExportCommonGateOutBusinessService;

  private PortService portService;

  private ContainerExternalDataService containerExternalDataService;

  @Autowired
  public void setContainerExternalDataService(ContainerExternalDataService containerExternalDataService) {
    this.containerExternalDataService = containerExternalDataService;
  }

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setImportGateOutService(ImportGateOutService importGateOutService) {
    this.importGateOutService = importGateOutService;
  }

  @Autowired
  public void setExportGateOutService(ExportGateOutService exportGateOutService) {
    this.exportGateOutService = exportGateOutService;
  }

  @Autowired
  public void setCommonCardService(CommonCardService commonCardService) {
    this.commonCardService = commonCardService;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setImportExportCommonGateOutBusinessService(
      ImportExportCommonGateOutBusinessService importExportCommonGateOutBusinessService) {
    this.importExportCommonGateOutBusinessService = importExportCommonGateOutBusinessService;
  }

  @Autowired
  public void setPortService(PortService portService) {
    this.portService = portService;
  }

  @Autowired
  public void setOddGateOutService(ODDGateOutService oddGateOutService) {
    this.oddGateOutService = oddGateOutService;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateOutReponse populateGateOut(GateOutRequest gateOutRequest) {

    GateOutReponse gateOutReponse = new GateOutReponse();
    gateOutReponse.setGateOUTDateTime(gateOutRequest.getGateOUTDateTime());

    Optional<Card> cardOpt = cardRepository.findOne(gateOutRequest.getCardID());
    Card card =
        cardOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutRequest.getCardID()));
    gateOutRequest.setComID(card.getCompany().getCompanyID());

    gateOutRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

    Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getClientID());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getClientID()));
    if (StringUtils.isEmpty(client.getUnitNo()))
      throw new BusinessException("Unit no does not setup for client " + client.getClientID());
    gateOutRequest.setLaneNo(client.getUnitNo());

    InProgressTrxDTO trxDTO = commonCardService.isTrxInProgress(gateOutRequest.getCardID());

    if (trxDTO.isInProgress()) {

      List<ExportContainer> exportContainers = null;

      List<ImportContainer> importContainers = null;

      switch (trxDTO.getTrxType()) {
        case EXPORT:
          exportContainers = exportGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
          break;
        case IMPORT:
          importContainers = importGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
          break;
        case IMPORT_EXPORT:
          exportContainers = exportGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
          importContainers = importGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
          break;
        case ODD:
          gateOutReponse = oddGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
          return gateOutReponse;
        default:
          break;
      }

      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
      UserContext userContext = (UserContext) authentication.getPrincipal();
      gateOutRequest.setUserName(userContext.getUsername());

      gateOutReponse.setImportContainers(importContainers);
      gateOutReponse.setExportContainers(exportContainers);
      gateOutReponse.setTransactionType(trxDTO.getTrxType().name());

      // call opus or cosmos only for approved gate in state
      if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(), gateOutReponse.getGateInStatus())) {
        OpusCosmosBusinessService businessService = containerExternalDataService.getImplementationService(implementor);
        gateOutReponse = businessService.sendGateOutReadRequest(gateOutRequest, gateOutReponse);
      }

      return gateOutReponse;
    } else {
      throw new BusinessException(
          "No Valid Gate in Transaction Found for the Scan Card : " + gateOutRequest.getCardID());
    }

  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateOutReponse saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {

    Optional<Card> cardOpt = cardRepository.findOne(gateOutWriteRequest.getCardID());
    Card card = cardOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutWriteRequest.getCardID()));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    log.info("userContext.getUsername() " + userContext.getUsername());
    gateOutWriteRequest.setUserName(userContext.getUsername());
    Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(userContext.getUserID());
    SystemUser user = systemUserOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Login User ! " + userContext.getUsername()));

    Optional<Client> clientOpt = clientRepository.findOne(gateOutWriteRequest.getGateOutClient());
    Client client = clientOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutWriteRequest.getGateOutClient()));
    if (StringUtils.isEmpty(client.getUnitNo()))
      throw new BusinessException("Unit no does not setup for client " + client.getClientID());
    gateOutWriteRequest.setLaneNo(client.getUnitNo());
    gateOutWriteRequest.setCosmosPort(client.getCosmosPortNo());

    Optional<Client> boothOpt = clientRepository.findOne(gateOutWriteRequest.getGateOutBooth());
    Client booth = boothOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Booth ID ! " + gateOutWriteRequest.getGateOutBooth()));

    gateOutWriteRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

    List<ImportContainer> importContainers = null;
    List<ExportContainer> exportContainers = null;

    if (StringUtils.isEmpty(gateOutWriteRequest.getImpExpFlag()))
      throw new BusinessException("Invalid GateOutWriteRequest Empty ImpExpFlag");
    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());

    importExportCommonGateOutBusinessService.isValidGateOutLane(client, gateOutWriteRequest);

    GateOutReponse gateOutReponse = new GateOutReponse();
    gateOutReponse.setGateOUTDateTime(gateOutWriteRequest.getGateOUTDateTime());
    gateOutReponse.setImportContainers(importContainers);
    gateOutReponse.setExportContainers(exportContainers);

    if (StringUtils.isEmpty(gateOutWriteRequest.getGateInStatus()))
      throw new BusinessException(
          "Gate In status Required for the transaction : " + gateOutWriteRequest.getGateInStatus());

    if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(), gateOutWriteRequest.getGateInStatus())) {
      OpusCosmosBusinessService businessService = containerExternalDataService.getImplementationService(implementor);
      gateOutReponse = businessService.sendGateOutWriteRequest(gateOutWriteRequest, gateOutReponse);
    }

    /*
     * Future<Boolean> impSave = null; Future<Boolean> expSave = null;
     */

    switch (impExpFlag) {
      case IMPORT:
        portService.checkContainerTobeReleasedByPort(client, gateOutWriteRequest.getImportContainers());
        importGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
        // expSave = new AsyncResult<Boolean>(true);
        break;
      case EXPORT:
        // impSave = new AsyncResult<Boolean>(true);
        exportGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
        break;
      case IMPORT_EXPORT:
        portService.checkContainerTobeReleasedByPort(client, gateOutWriteRequest.getImportContainers());
        importGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
        exportGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
        break;
      default:
        break;
    }

    GateOutMessage gateOutMessage = new GateOutMessage();
    gateOutMessage.setCode(GateOutMessage.OK);
    gateOutMessage.setDescription("Saved Successfully!");

    /*
     * while (true) { if (impSave.isDone() && expSave.isDone()) {
     * 
     * gateOutMessage.setCode(GateOutMessage.OK); gateOutMessage.setDescription(
     * "Saved Successfully!");
     * 
     * System.out.println("WHILE LOOP BROKEN!!!!. "); break; } System.out.println(
     * "Continue doing something else. ");
     * 
     * try { Thread.sleep(asyncWaitTime); } catch (InterruptedException e) {
     * log.error(e.getMessage()); System.out.println( "WHILE LOOP BROKEN ON THREAD EXCEPTION!!!!. "
     * ); break; } }
     */
    gateOutReponse.setMessage(gateOutMessage);
    return gateOutReponse;
  }


  @SolasApplicable
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateOutReponse testSaveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {

    exportGateOutService.testSolas(gateOutWriteRequest);

    GateOutReponse gateOutReponse = new GateOutReponse();
    GateOutMessage gateOutMessage = new GateOutMessage();
    gateOutMessage.setCode(GateOutMessage.OK);
    gateOutMessage.setDescription("Saved Successfully!");


    gateOutReponse.setMessage(gateOutMessage);
    return gateOutReponse;

  }

}
