package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.Login;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.LoginRepository;
import com.privasia.scss.core.repository.PrintEirRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;

@Service("importGateInService")
public class ImportGateInService {

  private static final Log log = LogFactory.getLog(ImportGateInService.class);

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

  private LoginRepository loginRepository;

  private CardRepository cardRepository;

  private ClientRepository clientRepository;

  private PrintEirRepository printEirRepository;

  private CardUsageRepository cardUsageRepository;

  private HPATBookingRepository hpatBookingRepository;

  private DamageCodeRepository damageCodeRepository;

  private WDCGatePassRepository wdcGatePassRepository;

  @Autowired
  public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
    this.damageCodeRepository = damageCodeRepository;
  }

  @Autowired
  public void setLoginRepository(LoginRepository loginRepository) {
    this.loginRepository = loginRepository;
  }

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setPrintEirRepository(PrintEirRepository printEirRepository) {
    this.printEirRepository = printEirRepository;
  }

  @Autowired
  public void setCardUsageRepository(CardUsageRepository cardUsageRepository) {
    this.cardUsageRepository = cardUsageRepository;
  }

  @Autowired
  public void setHPATBookingRepository(HPATBookingRepository hpatBookingRepository) {
    this.hpatBookingRepository = hpatBookingRepository;
  }

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
    this.wdcGatePassRepository = wdcGatePassRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInRequest findContainerNoByGatePass(GateInRequest gateInRequest) {

    if (gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() != 0) {
      String impContainer01 = gatePassRepository.findContainerNoByGatePassNo(gateInRequest.getGatePass1());
      gateInRequest.setImpContainer1(impContainer01);
    }
    if (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() != 0) {
      String impContainer02 = gatePassRepository.findContainerNoByGatePassNo(gateInRequest.getGatePass2());
      gateInRequest.setImpContainer2(impContainer02);
    }
    return gateInRequest;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateInImport(GateInReponse gateInReponse) {

    gateInReponse.getImportContainers().forEach(importContainer -> {
      GatePassValidateDTO gatePassValidateDTO =
          validateGatePass(importContainer.getBaseCommonGateInOutAttribute().getCard(), importContainer.getGatePassNo(),
              gateInReponse.isCheckPreArrival(), importContainer.getBaseCommonGateInOutAttribute().getHpatBooking(),
              gateInReponse.getTruckHeadNo());
      if (!gatePassValidateDTO.isValidGatePass()) {
        throw new ResultsNotFoundException(
            gatePassValidateDTO.getGatePassErrorMessage() + importContainer.getGatePassNo());
      }
    });

    return gateInReponse;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
      String hpatSeqId, String truckHeadNo) {

    boolean result = true;

    TransactionStatus eirStatus = null;
    GatePassStatus gatePassStatus = null;
    LocalDateTime today = LocalDateTime.now();

    log.error("------Gate Pass Validate Inputs ------" + " gatePassNo :" + gatePassNo + " truckHeadNo :" + truckHeadNo
        + " cardIdSeq :" + cardIdSeq + " checkPreArrival : " + checkPreArrival + " hpatSeqId :" + hpatSeqId);
    Optional<GatePass> scssGatePassOpt = gatePassRepository.findByGatePassNo(gatePassNo);

    GatePass gatePass =
        scssGatePassOpt.orElseThrow(() -> new BusinessException("Invalid Gate Pass. Not Found !" + gatePassNo));

    if (!(gatePass.getCommonGateInOut() == null || gatePass.getBaseCommonGateInOutAttribute().getEirStatus() == null)) {

      // check gatepass is approved(used), EIRStatus = A?
      log.debug("-----START check gatepass is approved(used), EIRStatus = A? ----" + gatePassNo);
      eirStatus = TransactionStatus.APPROVED;
      if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)) {
        /*
         * throw new BusinessException( CommonUtil.formatMessageCode(ApplicationConstants.
         * GATE_PASS_IS_USED, new Object[] {gatePassNo}));
         */
        throw new BusinessException("Gate Pass No " + gatePassNo + " is already been used");

      }

      log.debug("---END check gatepass gatepass is approved(used), EIRStatus = A? ----" + gatePassNo);

      // check gatepass is in progress (in using), EIRStatus = I?
      log.debug("--START check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo);
      eirStatus = TransactionStatus.INPROGRESS;
      if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)) {
        /*
         * throw new BusinessException( CommonUtil.formatMessageCode(ApplicationConstants.
         * GATE_PASS_IN_PROGRESS, new Object[] {gatePassNo}));
         */
        throw new BusinessException("Gate Pass No " + gatePassNo + " in progress");
      }
      log.debug("--END check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo);

      if (gatePass.getGatePassStatus() != null) {
        // check gatepass is cancelled?
        log.debug("------START check gatepass is cancelled ------" + gatePassNo);
        gatePassStatus = GatePassStatus.CANCEL;
        if (gatePass.getGatePassStatus().equals(gatePassStatus)) {
          /*
           * throw new BusinessException( CommonUtil.formatMessageCode(ApplicationConstants.
           * GATE_PASS_CANCEL, new Object[] {gatePassNo}));
           */
          throw new BusinessException("Gate Pass No " + gatePassNo + " is already been cancelled");
        }
        log.error("-----END check gatepass is cancelled -----" + gatePassNo);

        // check gatepass is valid , EIRStatus = N?
        log.error("------START check gatepass is valid , EIRStatus = N? ----" + gatePassNo);
        eirStatus = TransactionStatus.NEW;
        gatePassStatus = GatePassStatus.ACTIVE;
        if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)
            && gatePass.getGatePassStatus().equals(gatePassStatus)) {
          /*
           * throw new
           * BusinessException(CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_INVALID,
           * new Object[] { gatePassNo }));
           */
          throw new BusinessException("Invalid Gate Pass No" + gatePassNo);
        }
        log.error("------END check gatepass is valid , EIRStatus = N? ------" + gatePassNo);

      }

      /**
       * Gate Pass Expiry Date By YPN
       */
      LocalDateTime validateDate = gatePass.getGatePassValidDate();
      log.error("------START check Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo + " :validateDate: "
          + validateDate + " :today: " + today);
      if (validateDate == null || today.isAfter(validateDate)) {
        throw new BusinessException("Gate Pass No " + gatePassNo + " is already Expiry");
        /*
         * throw new BusinessException(
         * CommonUtil.formatMessageCode(ApplicationConstants.DATE_GATEPASS_EXPIRY, new Object[]
         * {gatePassNo}));
         */

      }
      log.error("------ENDING CHECKING Gate Pass Expiry Date By YPN ------" + gatePassNo);


      log.error("------STARTING CHECKING WDC GATEPASS is valid------" + gatePassNo);
      Optional<WDCGatePass> wdcGatePassOpt = wdcGatePassRepository.findByGatePassNO(gatePassNo);

      WDCGatePass wdcGatePass =
          wdcGatePassOpt.orElseThrow(() -> new BusinessException("Invalid Gate Pass In WDC. Not Found !" + gatePassNo));

      /**
       * Gate Pass Expiry Date
       */
      log.error("-------------START WDC Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

      LocalDateTime wdcValidateDate = wdcGatePass.getGatePassValidDate();

      log.error("------START check WDC Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo
          + " :wdcValidateDate: " + wdcValidateDate + " :today: " + today);

      if (wdcValidateDate == null || today.isAfter(wdcValidateDate)) {

        log.error("------END check WDC Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo
            + " :wdcValidateDate: " + wdcValidateDate + " :today: " + today);
        throw new BusinessException("Gate Pass No " + gatePassNo + " is already Expiry");
        /*
         * throw new BusinessException(
         * CommonUtil.formatMessageCode(ApplicationConstants.DATE_GATEPASS_EXPIRY, new Object[]
         * {gatePassNo}));
         */


      }
      log.error("-------------END Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);


    } else {
      log.debug("--Invalid Gate Pass record --" + gatePassNo);
      throw new BusinessException("Invalid Gate Pass record " + gatePassNo);
    }



    return new GatePassValidateDTO();
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ImportContainer> fetchContainerInfo(List<Long> gatePassNumberList) {

    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatePassNumberList);

    List<GatePass> gatePassList = optionalGatePassList.orElseThrow(
        () -> new ResultsNotFoundException("No Import Containers could be found for the given Gate Pass Numbers!"));
    List<ImportContainer> importContainers = new ArrayList<ImportContainer>();
    gatePassList.forEach(item -> {
      ImportContainer importContainer = new ImportContainer();
      modelMapper.map(item, importContainer);

      // log.info("item " + item);
      // log.info("importContainer " + importContainer);
      log.info("getGateInOut " + importContainer.getGateInOut());
      log.info("getShippingLine " + importContainer.getShippingLine());
      log.info("getContainer().getContainerNumber() " + importContainer.getContainer().getContainerNumber());
      log.info("getBaseCommonGateInOutAttribute().getPmHeadNo() "
          + importContainer.getBaseCommonGateInOutAttribute().getPmHeadNo());
      log.info("getBaseCommonGateInOutAttribute().getPmPlateNo()"
          + importContainer.getBaseCommonGateInOutAttribute().getPmPlateNo());

      importContainers.add(importContainer);
    });
    return importContainers;

  }

  public List<ImportContainer> saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    // construct a new export entity for each exportcontainer and save
    if (!(gateInWriteRequest.getImportContainers() == null || gateInWriteRequest.getImportContainers().isEmpty())) {
      gateInWriteRequest.getImportContainers().forEach(importContainer -> {
        if (importContainer.getBaseCommonGateInOutAttribute() == null) {
          importContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
        }
        System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());
        importContainer.getBaseCommonGateInOutAttribute()
            .setTimeGateIn(CommonUtil.getParsedDate(gateInWriteRequest.getGateInDateTime()));

        System.out.println("importContainer " + importContainer);

        if (!(importContainer.getGatePassNo() == null)) {

          GatePass gatePass = gatePassRepository.findByGatePassNo(importContainer.getGatePassNo()).orElse(null);
          if (!(gatePass == null)) {

            Card card = null;
            HPABBooking hpatBooking = null;
            if (!(importContainer.getBaseCommonGateInOutAttribute() == null)) {
              if (StringUtils.isNotEmpty(importContainer.getBaseCommonGateInOutAttribute().getHpatBooking())) {
                hpatBooking = hpatBookingRepository
                    .findOne(importContainer.getBaseCommonGateInOutAttribute().getHpatBooking()).orElse(null);
              }
              if (!(importContainer.getBaseCommonGateInOutAttribute().getCard() == null)) {
                card = cardRepository.findOne(importContainer.getBaseCommonGateInOutAttribute().getCard()).orElse(null);
              }
            }

            SystemUser gateInClerk = null;
            System.out.println("gateInWriteRequest.getUserName() " + gateInWriteRequest.getUserName());
            Login login = loginRepository.findByUserName(gateInWriteRequest.getUserName()).orElse(null);
            if (!(login == null)) {
              gateInClerk = login.getSystemUser();
            }
            System.out.println("gateInClerk " + gateInClerk);

            Client gateInClient = null;
            if (!(importContainer.getBaseCommonGateInOutAttribute() == null
                || importContainer.getBaseCommonGateInOutAttribute().getGateInClient() == null)) {
              gateInClient = clientRepository
                  .findOne(importContainer.getBaseCommonGateInOutAttribute().getGateInClient().getClientID())
                  .orElse(null);
            }

            PrintEir printEir = null;
            if (!(importContainer.getPrintEir() == null || importContainer.getPrintEir().getPrintEIRID() == null)) {
              printEir = printEirRepository.findOne(importContainer.getPrintEir().getPrintEIRID()).orElse(null);
            }

            CardUsage cardUsage = null;
            if (!(importContainer.getCardUsage() == null || importContainer.getCardUsage().getCardUsageID() == null)) {
              cardUsage = cardUsageRepository.findOne(importContainer.getCardUsage().getCardUsageID()).orElse(null);
            }

            modelMapper.map(importContainer, gatePass);
            gatePass.prepareForInsertFromOpus(card, gateInClerk, gateInClient, printEir, cardUsage, hpatBooking);
            System.out.println("gatePass after initializeing " + gatePass);
            gatePass = gatePassRepository.save(gatePass);
            System.out.println("gatePass after modal map from importContainer " + gatePass);
          } else {
            throw new ResultsNotFoundException("Gatepass could not be found with the given gate pass number!");
          }

        }

      });
      return gateInWriteRequest.getImportContainers();
    }
    return null;
  }

}
