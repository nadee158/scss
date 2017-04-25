package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.privasia.scss.common.annotation.OpenGate;
import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.CommonSolasDTO;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.security.model.UserContext;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.hpat.service.HPABService;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.dto.OpusRequestResponseDTO;
import com.privasia.scss.opus.service.OpusGateInReadService;
import com.privasia.scss.opus.service.OpusGateInWriteService;
import com.privasia.scss.opus.service.OpusService;

@Service("importExportGateInService")
public class ImportExportGateInService {

  @Value("${async.wait.time}")
  private long asyncWaitTime;

  private static final Log log = LogFactory.getLog(ImportExportGateInService.class);

  private ImportGateInService importGateInService;

  private ExportGateInService exportGateInService;

  private OpusGateInReadService opusGateInReadService;

  private OpusGateInWriteService opusGateInWriteService;

  private ClientRepository clientRepository;

  private CardRepository cardRepository;

  private OpusService opusService;

  private HPABService hpabService;

  private CommonCardService commonCardService;

  private Gson gson;

  private SystemUserRepository systemUserRepository;

  private ReferRejectRepository referRejectRepository;

  private ModelMapper modelMapper;


  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setReferRejectRepository(ReferRejectRepository referRejectRepository) {
    this.referRejectRepository = referRejectRepository;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setCommonCardService(CommonCardService commonCardService) {
    this.commonCardService = commonCardService;
  }

  @Autowired
  public void setHpabService(HPABService hpabService) {
    this.hpabService = hpabService;
  }

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

  @Autowired
  public void setGson(Gson gson) {
    this.gson = gson;
  }


	

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateIn(GateInRequest gateInRequest) {

    Optional<Card> cardOpt = cardRepository.findOne(gateInRequest.getCardID());
    Card card =
        cardOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid Card ID ! " + gateInRequest.getCardID()));

    gateInRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

    Optional<Client> clientOpt = clientRepository.findOne(gateInRequest.getClientID());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid client ID ! " + gateInRequest.getClientID()));
    System.out.println("client.getLaneNo() " + client.getLaneNo());
    if (StringUtils.isEmpty(client.getLaneNo()))
      throw new BusinessException("Lane no does not setup for client " + client.getClientID());
    gateInRequest.setLaneNo(client.getLaneNo());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    System.out.println("userContext.getUsername() " + userContext.getUsername());
    gateInRequest.setUserName(userContext.getUsername());
    
    /* if the refer id avaliable then fetch here. then pass export container list */

    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setCheckPreArrival(gateInRequest.isCheckPreArrival());

    if ((gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() > 0)
        || (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() > 0)) {
      // List<Long> gatePassList =
      // Arrays.asList(gateInRequest.getGatePass1(),
      // gateInRequest.getGatePass2());
      List<ImportContainer> importContainerList = importGateInService.populateGateInImport(gateInRequest);
      // GatePassValidationService call validate - within another method
      importGateInService.validateImport(gateInRequest, importContainerList);
      gateInReponse.setImportContainers(importContainerList);
    }

    // call opus -
    OpusGateInReadRequest gateInReadRequest = opusGateInReadService.constructOpenGateInRequest(gateInRequest);

    OpusRequestResponseDTO opusRequestResponseDTO =
        new OpusRequestResponseDTO(gateInReadRequest, gson, gateInRequest.getCardID());
    System.out.println("populateGateIn :: opusRequestResponseDTO " + opusRequestResponseDTO);

    OpusGateInReadResponse gateInReadResponse =
        opusGateInReadService.getGateInReadResponse(gateInReadRequest, opusRequestResponseDTO);

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

    if (gateInRequest.getExpWeightBridge() <= 0) {
      throw new BusinessException("Invalid Exp Weight Bridge" + gateInRequest.getExpWeightBridge());
    }

    gateInReponse.setExpWeightBridge(gateInRequest.getExpWeightBridge());

    if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())) {
      gateInReponse = exportGateInService.validateGateInExports(gateInReponse, gateInRequest.getGateInDateTime());
    }

    // assign details from hpab booking
    if (StringUtils.isNotEmpty(gateInRequest.getHpabSeqId())) {
      gateInReponse = hpabService.populateHpabForImpExp(gateInReponse, gateInRequest.getHpabSeqId());
    }

    // refere reject details
    if (gateInRequest.getReferID().isPresent()) {
      Optional<ReferReject> optReferReject = referRejectRepository.findOne(gateInRequest.getReferID().get());
      if (optReferReject.isPresent()) {
        ReferReject referReject = optReferReject.get();
        updateFromReferReject(referReject, gateInReponse);
      }
    }

    return gateInReponse;
  }
  
  // take this to refer service
  private void updateFromReferReject(ReferReject referReject, GateInReponse gateInReponse) { 

    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%% CANT FIND FIELDS IN ExportContainer to set following fields
    // %%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
    // f.setCardIdSeq(rejectFrom.getCrdCardidSeq());
    // f.setExpWeightBridge(rejectFrom.getExpWeightBridge());
    // f.setCugIdSeq(rejectFrom.getCugIdSeq());
    // f.setTimeIn(rejectFrom.getReferDateTime());
    // f.setHpatSeqId(rejectFrom.getHpatSeqId());
    // f.setPmVerified(StringUtils.isBlank(rejectFrom.getPmVerified()) ? "F" :
    // rejectFrom.getPmVerified().toUpperCase());
    // f.setTrailerVerified(StringUtils.isBlank(rejectFrom.getTrailerVerified()) ? "F" :
    // rejectFrom.getTrailerVerified().toUpperCase());

    if (!(gateInReponse.getExportContainers() == null || gateInReponse.getExportContainers().isEmpty())
        || referReject.getReferRejectDetails().isPresent() || referReject.getReferRejectDetails().get().isEmpty()) {
      gateInReponse.getExportContainers().forEach(exportContainer -> {
        referReject.getReferRejectDetails().get().forEach(referRejectDetail -> {
          if (StringUtils.equals(exportContainer.getContainer().getContainerNumber(),
              referRejectDetail.getContainerNo())) {

            if (!(referRejectDetail.getSolas() == null)) {
              if (exportContainer.getSolas() == null) {
                exportContainer.setSolas(new CommonSolasDTO());
              }
              modelMapper.map(referRejectDetail.getSolas(), exportContainer.getSolas());
            }

            if (!(referRejectDetail.getSeal() == null)) {
              if (exportContainer.getSealAttribute() == null) {
                exportContainer.setSealAttribute(new CommonSealDTO());
              }
              modelMapper.map(referRejectDetail.getSeal(), exportContainer.getSealAttribute());
            }

            if (!(referReject.getBaseCommonGateInOut() == null)) {
              if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
                exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
              }
              modelMapper.map(referReject.getBaseCommonGateInOut(), exportContainer.getBaseCommonGateInOutAttribute());
            }
          }
        });
      });

    }

  }
  
  @OpenGate
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateInReponse saveGateInInfo(GateInWriteRequest gateInWriteRequest) {

    if (gateInWriteRequest.getExportContainers() != null && !gateInWriteRequest.getExportContainers().isEmpty()) {
      gateInWriteRequest.getExportContainers().forEach(con -> {
        log.info(
            "########################### EXPORT CONTAINER DETAILS GATE IN #########################################");
        log.info(con.toString());
        log.info(
            "########################### END EXPORT CONTAINER DETAILS GATE IN #########################################");
      });
    }

    if (gateInWriteRequest.getImportContainers() != null && !gateInWriteRequest.getImportContainers().isEmpty()) {
      gateInWriteRequest.getImportContainers().forEach(con -> {
        log.info(
            "########################### IMPORT CONTAINER DETAILS GATE IN #########################################");
        log.info(con.toString());
        log.info(
            "########################### END IMPORT CONTAINER DETAILS GATE IN #########################################");
      });
    }

    Card card = cardRepository.findOne(gateInWriteRequest.getCardId())
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Card : " + gateInWriteRequest.getCardId()));

    gateInWriteRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    gateInWriteRequest.setUserName(userContext.getUsername());

    Client gateInClient = clientRepository.findOne(gateInWriteRequest.getGateInClient())
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Client Id : " + gateInWriteRequest.getGateInClient()));

    if (StringUtils.isEmpty(gateInClient.getLaneNo()))
      throw new BusinessException("Lane no does not setup for client " + gateInClient.getClientID());
    gateInWriteRequest.setLaneNo(gateInClient.getLaneNo());

    SystemUser gateInClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    System.out.println("gateInClerk " + gateInClerk);
    
    OpusGateInWriteRequest opusGateInWriteRequest =
        opusGateInWriteService.constructOpusGateInWriteRequest(gateInWriteRequest);
    System.out.println("opusGateInWriteRequest " + gson.toJson(opusGateInWriteRequest));

    OpusRequestResponseDTO opusRequestResponseDTO =
        new OpusRequestResponseDTO(opusGateInWriteRequest, gson, gateInWriteRequest.getCardId());
    System.out.println("saveGateInInfo :: opusRequestResponseDTO " + opusRequestResponseDTO);

    OpusGateInWriteResponse opusGateInWriteResponse =
        opusGateInWriteService.getGateInWriteResponse(opusGateInWriteRequest, opusRequestResponseDTO);

    System.out.println("opusGateInWriteResponse " + gson.toJson(opusGateInWriteResponse)); 
    String errorMessage = opusService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
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
    gateInWriteRequest.setImportContainers(gateInReponse.getImportContainers());
    gateInWriteRequest.setExportContainers(gateInReponse.getExportContainers());

    /*
     * Future<Boolean> impSave = null; Future<Boolean> expSave = null;
     */

    if (StringUtils.isEmpty(gateInWriteRequest.getImpExpFlag()))
      throw new BusinessException("Invalid GateOutWriteRequest Empty ImpExpFlag");
    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

    switch (impExpFlag) {
      case IMPORT:
        importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        // expSave = new AsyncResult<Boolean>(true);
        break;
      case EXPORT:
        // impSave = new AsyncResult<Boolean>(true);
        exportGateInService.validateExport(gateInWriteRequest.getExportContainers());
        exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        break;
      case IMPORT_EXPORT:
        importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);

        exportGateInService.validateExport(gateInWriteRequest.getExportContainers());
        exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        break;
      default:
        // impSave = new AsyncResult<Boolean>(true);
        // expSave = new AsyncResult<Boolean>(true);
        break;
    }

    if (StringUtils.isNotEmpty(gateInWriteRequest.getHpatBookingId())) {
      hpabService.updateHPABAfterGateIn(gateInWriteRequest.getHpatBookingId());
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
     * log.error(e.getMessage()); System.out.println(
     * "WHILE LOOP BROKEN ON THREAD EXCEPTION!!!!. "); break; } }
     */

    gateInReponse.setMessage(gateOutMessage);

    return gateInReponse;
  }
  
  @OpenGate
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateInReponse saveTestGateInInfo(GateInWriteRequest gateInWriteRequest) {
	  
	 System.out.println("################# REQUEST : ############ "+gateInWriteRequest.toString()); 
    /*Card card = cardRepository.findOne(gateInWriteRequest.getCardId())
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Card : " + gateInWriteRequest.getCardId()));

    gateInWriteRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    gateInWriteRequest.setUserName(userContext.getUsername());

    Client gateInClient = clientRepository.findOne(gateInWriteRequest.getGateInClient())
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Client Id : " + gateInWriteRequest.getGateInClient()));

    if (StringUtils.isEmpty(gateInClient.getLaneNo()))
      throw new BusinessException("Lane no does not setup for client " + gateInClient.getClientID());
    gateInWriteRequest.setLaneNo(gateInClient.getLaneNo());

    SystemUser gateInClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    System.out.println("gateInClerk " + gateInClerk);

    OpusGateInWriteRequest opusGateInWriteRequest =
        opusGateInWriteService.constructOpusGateInWriteRequest(gateInWriteRequest);
    System.out.println("opusGateInWriteRequest " + gson.toJson(opusGateInWriteRequest));

    OpusRequestResponseDTO opusRequestResponseDTO =
        new OpusRequestResponseDTO(opusGateInWriteRequest, gson, gateInWriteRequest.getCardId());
    System.out.println("populateGateIn :: opusRequestResponseDTO " + opusRequestResponseDTO);

    OpusGateInWriteResponse opusGateInWriteResponse =
        opusGateInWriteService.getGateInWriteResponse(opusGateInWriteRequest, opusRequestResponseDTO);

    System.out.println("opusGateInWriteResponse " + gson.toJson(opusGateInWriteResponse));
    String errorMessage = opusService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
    log.error("ERROR MESSAGE FROM OPUS SERVICE: " + errorMessage);
    /*
     * if (StringUtils.isNotEmpty(errorMessage)) { // save it to the db - TO BE IMPLEMENTED // throw
     * new business exception with constructed message - there is // an error throw new
     * BusinessException(errorMessage); }
     */

    GateInReponse gateInReponse = new GateInReponse();
    /*
     * gateInReponse.setImportContainers(gateInWriteRequest. getImportContainers());
     * gateInReponse.setExportContainers(gateInWriteRequest. getExportContainers()); gateInReponse =
     * opusGateInWriteService.constructGateInReponse( opusGateInWriteResponse, gateInReponse);
     * gateInWriteRequest.setImportContainers(gateInReponse. getImportContainers());
     * gateInWriteRequest.setExportContainers(gateInReponse. getExportContainers());
     */

    /*
     * Future<Boolean> impSave = null; Future<Boolean> expSave = null;
     */

    /*if (StringUtils.isEmpty(gateInWriteRequest.getImpExpFlag()))
      throw new BusinessException("Invalid GateOutWriteRequest Empty ImpExpFlag");
    ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

    switch (impExpFlag) {
      case IMPORT:
        importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        // expSave = new AsyncResult<Boolean>(true);
        break;
      case EXPORT:
        // impSave = new AsyncResult<Boolean>(true);
        exportGateInService.validateExport(gateInWriteRequest.getExportContainers());
        exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        break;
      case IMPORT_EXPORT:
        importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);

        exportGateInService.validateExport(gateInWriteRequest.getExportContainers());
        exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card);
        break;
      default:
        // impSave = new AsyncResult<Boolean>(true);
        // expSave = new AsyncResult<Boolean>(true);
        break;
    }

    if (StringUtils.isNotEmpty(gateInWriteRequest.getHpatBookingId())) {
      hpabService.updateHPABAfterGateIn(gateInWriteRequest.getHpatBookingId());
    }
    */
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
     * log.error(e.getMessage()); System.out.println(
     * "WHILE LOOP BROKEN ON THREAD EXCEPTION!!!!. "); break; } }
     */

    gateInReponse.setMessage(gateOutMessage);

    return gateInReponse;
  }
  
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public Long saveODDGateInFo(GateInWriteRequest gateInWriteRequest) {

		if (gateInWriteRequest.getWhoddContainers() == null || (gateInWriteRequest.getWhoddContainers().isEmpty()))
			throw new BusinessException("Invalid GateInWriteRequest to save ODD ! ");

		Optional<Card> cardOpt = cardRepository.findOne(gateInWriteRequest.getCardId());
		Card card = cardOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateInWriteRequest.getCardId()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		log.info("userContext.getUsername() " + userContext.getUsername());

		Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(userContext.getUserID());
		SystemUser user = systemUserOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Login User ! " + userContext.getUsername()));

		Optional<Client> clientOpt = clientRepository.findOne(gateInWriteRequest.getGateInClient());
		Client client = clientOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid lane ID ! " + gateInWriteRequest.getGateInClient()));

		if (StringUtils.isEmpty(gateInWriteRequest.getImpExpFlag()))
			throw new BusinessException("Invalid GateInWriteRequest Empty ImpExpFlag");
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

		switch (impExpFlag) {
		case IMPORT:

			break;
		case EXPORT:

			break;
		case IMPORT_EXPORT:

			break;
		default:
			break;
		}

		
		return null;
	}

 

}
