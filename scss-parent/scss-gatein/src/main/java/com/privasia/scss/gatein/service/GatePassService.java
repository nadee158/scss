package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ISOInfo;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.SealInfo;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.CompanyType;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.GatePassStatus;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.CardUsage;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CommonContainerAttribute;
import com.privasia.scss.core.model.CommonGateInOutAttribute;
import com.privasia.scss.core.model.CommonSealAttribute;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.HPABBookingDetail;
import com.privasia.scss.core.model.ISOCode;
import com.privasia.scss.core.model.PrintEir;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WDCGlobalSetting;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.HPATBookingDetailRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.ISOCodeRepository;
import com.privasia.scss.core.repository.PrintEirRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.etpws.service.EdoExpiryForLineResponseType;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

@Service("gatePassService")
public class GatePassService {

  private static final Log log = LogFactory.getLog(GatePassService.class);

  @Autowired
  private GatePassRepository gatePassRepository;

  @Autowired
  private WDCGatePassRepository wdcGatePassRepository;

  @Autowired
  private WDCGlobalSettingRepository wdcGlobalSettingRepository;

  @Autowired
  private HPATBookingDetailRepository hpatBookingDetailRepository;

  private CardRepository cardRepository;

  @Autowired
  private ETPWebserviceClient etpWebserviceClient;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ISOCodeRepository isoCodeRepository;

  @Autowired
  private SystemUserRepository systemUserRepository;

  @Autowired
  private PrintEirRepository printEirRepository;

  @Autowired
  private CardUsageRepository cardUsageRepository;

  @Autowired
  private HPATBookingRepository hpatBookingRepository;

  private CosmosImportRepository cosmosImportRepository;

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
    this.cosmosImportRepository = cosmosImportRepository;
  }

  public boolean validateGatePass(String cardIdSeq, String gatePassNo, String check, String hpatSeqId,
      String truckHeadNo, long companyId) throws Exception {
    boolean result = true;

    TransactionStatus eirStatus = null;
    GatePassStatus gatePassStatus = null;
    LocalDateTime today = LocalDateTime.now();

    long gatePassNumber = Long.parseLong(gatePassNo);

    log.error("------STARTING CHECKING SCSS GATEPASS is valid------" + gatePassNo + ":" + truckHeadNo);
    Optional<GatePass> scssGatePassOpt = gatePassRepository.findByGatePassNo(gatePassNumber);
    if (scssGatePassOpt.isPresent()) {

      GatePass gatePass = scssGatePassOpt.get();
      // check gatepass no approved, EIRStatus = A?
      log.debug("-----START check gatepass no approved, EIRStatus = A? ----" + gatePassNo + ":" + truckHeadNo);
      eirStatus = TransactionStatus.APPROVED;
      if (!(gatePass.getCommonGateInOut() == null
          || gatePass.getBaseCommonGateInOutAttribute().getEirStatus() == null)) {
        if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_IS_USED, new Object[] {gatePassNo}));
        }
      }
      log.debug("---END check gatepass no approved, EIRStatus = A? ----" + gatePassNo + ":" + truckHeadNo);

      // check gatepass is in progress (in using), EIRStatus = I?
      log.debug("--START check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo + ":" + truckHeadNo);
      eirStatus = TransactionStatus.INPROGRESS;
      if (!(gatePass.getCommonGateInOut() == null
          || gatePass.getBaseCommonGateInOutAttribute().getEirStatus() == null)) {
        if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_IN_PROGRESS, new Object[] {gatePassNo}));
        }
      }
      log.debug("--END check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo + ":" + truckHeadNo);

      // check gatepass is cancelled?
      log.debug("------START check gatepass is cancelled ------" + gatePassNo + ":" + truckHeadNo);
      gatePassStatus = GatePassStatus.CANCEL;
      if (!(gatePass.getGatePassStatus() == null)) {
        if (gatePass.getGatePassStatus().equals(gatePassStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_CANCEL, new Object[] {gatePassNo}));
        }
        log.error("-----END check gatepass is cancelled -----" + gatePassNo + ":" + truckHeadNo);

        // check gatepass is valid , EIRStatus = N?
        log.error("------START check gatepass is valid , EIRStatus = N? ----" + gatePassNo + ":" + truckHeadNo);
        eirStatus = TransactionStatus.NEW;
        gatePassStatus = GatePassStatus.ACTIVE;
        if (!(gatePass.getCommonGateInOut() == null || gatePass.getBaseCommonGateInOutAttribute().getEirStatus() == null
            || gatePass.getGatePassStatus() == null)) {
          if (gatePass.getBaseCommonGateInOutAttribute().getEirStatus().equals(eirStatus)
              && gatePass.getGatePassStatus().equals(gatePassStatus)) {
            throw new BusinessException(
                CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_INVALID, new Object[] {gatePassNo}));
          }
        }
        log.error("------END check gatepass is valid , EIRStatus = N? ------" + gatePassNo + ":" + truckHeadNo);

        /**
         * Gate Pass Expiry Date By YPN
         */
        LocalDateTime validateDate = gatePass.getGatePassValidDate();
        if (!(validateDate == null)) {
          log.error("------START check Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo
              + " :validateDate: " + validateDate + " :today: " + today);
          if (today.isAfter(validateDate)) {
            throw new BusinessException(
                CommonUtil.formatMessageCode(ApplicationConstants.DATE_GATEPASS_EXPIRY, new Object[] {gatePassNo}));
          }
          log.error("------END check Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo + " :validateDate: "
              + validateDate + " :today: " + today);
        }
        log.error("------ENDING CHECKING SCSS GATEPASS is valid------" + gatePassNo + ":" + truckHeadNo);

        log.error("------STARTING CHECKING WDC GATEPASS is valid------" + gatePassNo + ":" + truckHeadNo);
        Optional<WDCGatePass> wdcGatePassOpt = wdcGatePassRepository.findByGatePassNO(Long.parseLong(gatePassNo));

        if (wdcGatePassOpt.isPresent()) {

          WDCGatePass wdcGatePass = wdcGatePassOpt.get();

          /**
           * Gate Pass Expiry Date
           */
          log.error("-------------START Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

          LocalDateTime wdcValidateDate = wdcGatePass.getGatePassValidDate();
          if (!(wdcValidateDate == null)) {
            log.error("------START check WDC Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo
                + " :wdcValidateDate: " + wdcValidateDate + " :today: " + today);
            if (today.isAfter(wdcValidateDate)) {
              throw new BusinessException(
                  CommonUtil.formatMessageCode(ApplicationConstants.DATE_GATEPASS_EXPIRY, new Object[] {gatePassNo}));
            }
            log.error("------END check WDC Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo
                + " :wdcValidateDate: " + wdcValidateDate + " :today: " + today);
          }
          log.error("-------------END Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

          /**
           * Edo Expiry Date //edo logs
           */
          Optional<WDCGlobalSetting> wdcGlobalSettingOpt = wdcGlobalSettingRepository.findOne("EDO_EXP");
          if (wdcGlobalSettingOpt.isPresent()) {
            WDCGlobalSetting wdcGlobalSetting = wdcGlobalSettingOpt.get();
            String etpEdoExpiryDateFlag = wdcGlobalSetting.getGlobalString();

            if ("Y".equalsIgnoreCase(etpEdoExpiryDateFlag)) {

              log.error("-------------START Edo Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

              log.error("-------------END Edo Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

              if (wdcGatePass.getGateOrder() != null) {
                String gateOrderType = wdcGatePass.getGateOrder().getTypeCode();
                if ("I".equalsIgnoreCase(gateOrderType) || "J".equalsIgnoreCase(gateOrderType)
                    || "K".equalsIgnoreCase(gateOrderType)) {
                  LocalDateTime edoExpiryDate = wdcGatePass.getEdoExpiryDate();
                  if (edoExpiryDate != null) {
                    if (today.isAfter(edoExpiryDate)) {
                      throw new BusinessException(CommonUtil
                          .formatMessageCode(ApplicationConstants.DATE_GATEPASS_EDO_EXPIRY, new Object[] {gatePassNo}));
                    }
                  } else {
                    String lineCode = wdcGatePass.getGateOrder().getLineCode();
                    if (StringUtils.isNotBlank(lineCode)) {
                      EdoExpiryForLineResponseType responseType = etpWebserviceClient.getEdoExpiryForLine(lineCode);
                      if (responseType.isEdoExpiryEnabled()) {
                        if (edoExpiryDate == null) {
                          throw new BusinessException(CommonUtil
                              .formatMessageCode(ApplicationConstants.EDO_EXPIRY_DATE_NULL, new Object[] {gatePassNo}));
                        }
                      }
                    }
                  }
                }
              }

              /**
               * Checking the Haulage Information
               */
              return checkMatchCompanyPreArrival(cardIdSeq, gatePassNo, check, hpatSeqId, truckHeadNo, companyId,
                  gatePass);

            }

          } else {
            throw new ResultsNotFoundException("WDCGlobalSetting could not be found!");
          }

        } else {
          throw new ResultsNotFoundException("WDC GatePass could not be found!");
        }

      }

    } else {
      throw new ResultsNotFoundException("GatePass could not be found!");
    }

    // check passed company id with gtp_hcid
    return result;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void checkEdoExpiry(GatePass gatePass, WDCGatePass wdcGatePass) {

    /**
     * Edo Expiry Date //edo logs
     * 
     */
    log.error("-------------START Edo Expiry Date -----------" + gatePass.getGatePassNo());

    Optional<WDCGlobalSetting> wdcGlobalSettingOpt = wdcGlobalSettingRepository.findOne("EDO_EXP");

    WDCGlobalSetting wdcGlobalSetting = wdcGlobalSettingOpt
        .orElseThrow(() -> new ResultsNotFoundException("WDCGlobalSetting could not be Found! " + "EDO_EXP"));

    String etpEdoExpiryDateFlag = wdcGlobalSetting.getGlobalString();

    if (StringUtils.equalsIgnoreCase("Y", etpEdoExpiryDateFlag)) {

      if (wdcGatePass.getGateOrder() != null) {
        String gateOrderType = wdcGatePass.getGateOrder().getTypeCode();
        if ("I".equalsIgnoreCase(gateOrderType) || "J".equalsIgnoreCase(gateOrderType)
            || "K".equalsIgnoreCase(gateOrderType)) {
          LocalDateTime edoExpiryDate = wdcGatePass.getEdoExpiryDate();
          if (edoExpiryDate != null) {
            if (LocalDateTime.now().isAfter(edoExpiryDate)) {
              throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " Demurrage already Expiry");

              /*
               * throw new BusinessException(CommonUtil .formatMessageCode(ApplicationConstants.
               * DATE_GATEPASS_EDO_EXPIRY, new Object[] {gatePass.getGatePassNo()}));
               */
            }
          } else {
            String lineCode = wdcGatePass.getGateOrder().getLineCode();
            if (StringUtils.isNotBlank(lineCode)) {
              EdoExpiryForLineResponseType responseType = etpWebserviceClient.getEdoExpiryForLine(lineCode);
              if (responseType.isEdoExpiryEnabled()) {
                if (edoExpiryDate == null) {
                  throw new BusinessException("Gate Pass No " + gatePass.getGatePassNo() + " Demurrage already Expiry");
                  /*
                   * throw new BusinessException(CommonUtil .formatMessageCode(ApplicationConstants.
                   * EDO_EXPIRY_DATE_NULL, new Object[] {gatePass.getGatePassNo()}));
                   */
                }
              }
            }
          }
        }
      }
    }
    log.error("-------------END Edo Expiry Date -----------" + gatePass.getGatePassNo());

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void matchCompany(GatePass gatePass, Long cardIdSeq) {

    /**
     * Match Company
     * 
     */
    log.error("-------------START Match Company -----------" + gatePass.getGatePassNo());

    Optional<Card> cardOpt = cardRepository.findOne(cardIdSeq);
    Card card = cardOpt
        .orElseThrow(() -> new ResultsNotFoundException("Card could not be Found for given card Id ! " + cardIdSeq));

    if (gatePass.getCompany() != null || card.getCompany() != null) {
      if (gatePass.getCompany().getCompanyID().longValue() != card.getCompany().getCompanyID().longValue()) {
        throw new BusinessException(
            "Company''s Gate Pass No " + gatePass.getGatePassNo() + " and Smart Card No do not match");
      }
      log.error("-------------END Match Company -----------" + gatePass.getGatePassNo());
    } else {
      log.error("-------------END Match Company -----------" + gatePass.getGatePassNo());
      throw new BusinessException("Company could not be Found for given Gate Pass No ! " + gatePass.getGatePassNo());
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean checkOGABlock(String containerNo) {
    return cosmosImportRepository.isOGABlock(containerNo);

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean checkInternalBlock(String containerNo) {
    return cosmosImportRepository.isInternalBlock(containerNo);
  }

  public boolean checkMatchCompanyPreArrival(String cardIdSeq, String gatePassNo, String check, String hpatSeqId,
      String truckHeadNo, long companyId, GatePass gatePass) throws Exception {

    log.error("-------------START check_MatchCompany_PreArrival_New -----------" + gatePassNo + ":" + truckHeadNo);

    Company company = gatePass.getCompany();
    CommonContainerAttribute container = gatePass.getContainer();

    String containerNo = container.getContainerNumber();
    long hcid = company.getCompanyID();

    log.error("-------------END check_MatchCompany_PreArrival_New -----------" + gatePassNo + ":" + truckHeadNo);
    if (!(companyId == hcid)) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_COMPANY_NOT_MATCH, new Object[] {gatePassNo}));
    }

    /**
     * OGA And Internal Block
     */
    log.error("-------------START OGA And Internal Block -----------" + gatePassNo + ":" + containerNo);
    // AS400Database db
    boolean isOgaBlock = checkOGABlock(containerNo);
    // AS400Database db = null;
    boolean isInternalBlock = checkInternalBlock(containerNo);

    if (isOgaBlock && isInternalBlock) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_OGA_INTERNAL_BLOCK, new Object[] {containerNo}));
    } else if (isOgaBlock && !isInternalBlock) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_OGA_BLOCK, new Object[] {containerNo}));
    } else if (!isOgaBlock && isInternalBlock) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_INTERNAL_BLOCK, new Object[] {containerNo}));
    }

    /**
     * End OGA And Internal Block
     */
    log.error("-------------END OGA And Internal Block -----------" + gatePassNo + ":" + containerNo);

    return checkPreArrival(containerNo, cardIdSeq, "MANUALLY", "", gatePassNo);

  }

  private boolean checkPreArrival(String containerNo, String cardIdSeq, String hpatSeqId, String truckHeadNo,
      String gatePassNo) throws Exception {

    if (StringUtils.isNotBlank(hpatSeqId)) {
      return true;
    } else {
      log.error("Check Gate Pass pre-Arrival Booking.." + containerNo + ":" + truckHeadNo);

      Optional<Card> cardOpt = cardRepository.findOne(Long.parseLong(cardIdSeq));
      if (cardOpt.isPresent()) {

        Card card = cardOpt.get();

        SmartCardUser smartCardUser = card.getSmartCardUser();
        Company company = card.getCompany();

        boolean isHpatExist = this.checkHpatBookingExist(containerNo, truckHeadNo, card);

        // Call Web Service
        if (!isHpatExist) {

          if (!(smartCardUser == null || company == null || company.getCompanyType() == null
              || smartCardUser.getCommonContactAttribute() == null)) {
            if (company.getCompanyType().equals(CompanyType.HAULAGE)) {

              String icNo = "";

              if (StringUtils.isNotBlank(smartCardUser.getCommonContactAttribute().getNewNRICNO())) {
                icNo = smartCardUser.getCommonContactAttribute().getNewNRICNO();
              } else if (StringUtils.isNotBlank(smartCardUser.getCommonContactAttribute().getOldNRICNO())) {
                icNo = smartCardUser.getCommonContactAttribute().getOldNRICNO();
              } else if (StringUtils.isNotBlank(smartCardUser.getPassportNo())) {
                icNo = smartCardUser.getPassportNo();
              }

              Optional<WDCGlobalSetting> wdcGlobalSettingOpt = wdcGlobalSettingRepository.findOne("ETP_HPAT");
              if (wdcGlobalSettingOpt.isPresent()) {
                WDCGlobalSetting wdcGlobalSetting = wdcGlobalSettingOpt.get();
                String etpFlag = wdcGlobalSetting.getGlobalString();
                if ("Y".equalsIgnoreCase(etpFlag)) {

                  // web service
                  // getHpatDetails(card.getCardNo(), icNo,
                  // company.getCompanyCode(), new Date());
                  // isHpatExist =
                  // this.checkHpatBookingExist(containerNo,
                  // cardIdSeq, truckHeadNo);
                } else {
                  isHpatExist = true;
                }
              }

              if (isHpatExist) {
                return true;
              } else {
                return checkLaden(containerNo, gatePassNo);
              }
            }
          }
        }
      }
    }
    return false;
  }

  private boolean checkHpatBookingExist(String containerNo, String truckHeadNo, Card card) throws Exception {
    if (!(card == null)) {
      String cardNo = Long.toString((card.getCardNo()));
      containerNo = StringUtils.upperCase(containerNo);
      BookingType type = BookingType.IMPORT;
      HpatReferStatus hpatReferStatus = HpatReferStatus.ACTIVE;

      HPABBookingDetail hpatBookingDetail = null;

      if (StringUtils.isNotBlank(truckHeadNo)) {

        truckHeadNo = StringUtils.upperCase(truckHeadNo);

        hpatBookingDetail = hpatBookingDetailRepository
            .findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNoAndHpatBooking_PmNumber(
                containerNo, type, hpatReferStatus, cardNo, truckHeadNo);
      } else {
        hpatBookingDetail =
            hpatBookingDetailRepository.findByContainerNumberAndBookingTypeAndHpatBooking_StatusAndHpatBooking_CardNo(
                containerNo, type, hpatReferStatus, cardNo);
      }

      if (!(hpatBookingDetail == null)) {
        return true;
      }

    }
    return false;
  }

  public boolean getHpatDetails(String smartCardNo, String icNo, String compCode, Date driverArrivalDate)
      throws Exception {
    // GateInDAOImpl gateInDAOImpl = GateInDAOImpl.getInstance();
    //
    // EtpServicePortTypeProxy ws = new EtpServicePortTypeProxy();
    // ws.setEndpoint(gateInDAOImpl.getEtpWebServiceAddress());
    //
    // HpatDetailsRequestType hpatDetailsRequestType = new
    // HpatDetailsRequestType();
    // hpatDetailsRequestType.setDriverIc(icNo); // Driver IC
    // hpatDetailsRequestType.setHaulageCompanyCode(compCode);
    // hpatDetailsRequestType.setAppointmnetDate(this.convertDateToString(driverArrivalDate));
    // //
    // DD/MM/YYYY
    // // Format
    //
    // try {
    // HpatDetailsResponseType[] response =
    // ws.getHpatDetails(hpatDetailsRequestType);
    //
    // if (response != null && response.length > 0) {
    // for (int i = 0; i < response.length; i++) {
    // HpatDetailsResponseType hpatDetailsResponseType = response[i];
    // /**
    // * Check ETP_BOOKING_HPAT Based On Ref ID
    // */
    // boolean isExist =
    // gateInDAOImpl.isExistEtpBookingHpat(hpatDetailsResponseType.getRefId().trim());
    //
    // if (!isExist) {
    // gateInDAOImpl.doEterminalPlusHpat(hpatDetailsResponseType,
    // smartCardNo, "");
    // }
    // }
    // }
    // } catch (EtpProcessExceptionType etpEx) {
    // String stackTrace = ExceptionUtil.getExceptionStacktrace(etpEx);
    // log.error(stackTrace);
    // } catch (RemoteException ex) {
    // String stackTrace = ExceptionUtil.getExceptionStacktrace(ex);
    // log.error(stackTrace);
    // }

    return true;
  }

  private boolean checkLaden(String containerNo, String gatePassNo) throws Exception {
    // AS400Database db = null;
    // sql = "SELECT hdid03" + ", hddt03" + ", hdtd03" + ", cnbt03" + " FROM
    // PCTCSE.cthndl5" + "
    // WHERE hdtm03='WPT1'"
    // + " AND hdtp03='IN'" + " AND hddt03 >=20060601" + " AND hdtd03 >=0" +
    // " AND hdfs03<>'CAN'" +
    // " AND cnid03="
    // + SQL.format(containerNo) + " ORDER BY hddt03 DESC, hdtd03 DESC";

    // if (rs.next()) {
    // if (rs.getString("cnbt03") != null &&
    // rs.getString("cnbt03").equals("E")) {
    // return true;
    // } else {
    // throw new BusinessException(
    // CommonUtil.formatMessageCode(ApplicationConstants.GATE_PASS_NO_PREARRIVAL,
    // new Object[]
    // {gatePassNo}));
    // }
    // }
    return false;
  }

  @Transactional
  public TransactionDTO selectGatePassInfo(TransactionDTO transactionDTO, long companyID, long clientId)
      throws Exception {

    CommonGateInOutAttribute commonGateInOut = null;
    BaseCommonGateInOutAttribute baseCommonGateInOutAttribute = null;

    log.error("-------START selectGatePassInfo----------" + transactionDTO.getImportContainer01() + "::"
        + transactionDTO.getImportContainer02());

    // try to find by gatePassNo1
    ImportContainer container01 = transactionDTO.getImportContainer01();
    GatePass gpForC1 = getGatePassForImportContainer(container01, companyID);

    if (!(gpForC1 == null)) {
      transactionDTO = assignValuesToImportContainer(transactionDTO, gpForC1, true);
      commonGateInOut = gpForC1.getCommonGateInOut();
      baseCommonGateInOutAttribute = gpForC1.getBaseCommonGateInOutAttribute();
    }

    // try to find by gatePassNo1
    ImportContainer container02 = transactionDTO.getImportContainer01();
    GatePass gpForC2 = getGatePassForImportContainer(container02, companyID);

    if (!(gpForC2 == null)) {
      transactionDTO = assignValuesToImportContainer(transactionDTO, gpForC2, false);
      if (commonGateInOut == null) {
        commonGateInOut = gpForC2.getCommonGateInOut();
      }
    }

    if (!(commonGateInOut == null)) {
      transactionDTO.setPmHeadNo(baseCommonGateInOutAttribute.getPmHeadNo());
      transactionDTO.setPmPlateNo(baseCommonGateInOutAttribute.getPmPlateNo());
      Client gateInClient = baseCommonGateInOutAttribute.getGateInClient();
      if (!(gateInClient == null)) {
        int portNo = gateInClient.getCosmosPortNo();
        transactionDTO.setCosmosPortNo(portNo);
      }
    }

    String unitNo = selectLaneNo(clientId);
    transactionDTO.setUnitNo(unitNo);

    log.error("-------END selectGatePassInfo----------" + transactionDTO.getImportContainer01() + "::"
        + transactionDTO.getImportContainer02());

    return transactionDTO;

  }

  public String selectLaneNo(long clientId) {
    Optional<ClientDTO> client = clientRepository.getClientUnitNoByClientID(clientId);
    if (client.isPresent()) {
      return client.get().getUnitNo();
    }
    return null;
  }

  public TransactionDTO assignValuesToImportContainer(TransactionDTO transactionDTO, GatePass gatePass,
      boolean isContainerNo1) throws Exception {
    ImportContainer container = null;
    if (isContainerNo1) {
      container = transactionDTO.getImportContainer01();
    } else {
      container = transactionDTO.getImportContainer02();
    }
    CommonContainerAttribute containerComm = gatePass.getContainer();
    if (!(containerComm == null)) {
      container.getContainer().setContainerNumber(containerComm.getContainerNumber());
    }
    GateInOutStatus gateInOut = gatePass.getGateInOut();
    if (!(gateInOut == null)) {
      container.setGateInOut(gateInOut.getValue());
    }
    container.setShippingLine(gatePass.getShippingLine());

    transactionDTO = selectGatePassInfoCosmos(transactionDTO, false);

    return transactionDTO;
  }

  public TransactionDTO selectGatePassInfoCosmos(TransactionDTO transactionDTO, boolean isContainerNo1)
      throws Exception {

    log.error("-------START selectGatePassInfo cosmos----------" + transactionDTO + ":" + isContainerNo1);
    ImportContainer container = null;
    if (isContainerNo1) {
      container = transactionDTO.getImportContainer01();
    } else {
      container = transactionDTO.getImportContainer02();
    }

    String containerNo = container.getContainer().getContainerNumber();

    // sql = "SELECT cnid03" + ", hdtp03" + ", cnbt03" + ", lynd05" + ",
    // orgv05" + ", cnis03" + ",
    // orrf05"
    // + ", psex45" + ", hdid10" + " FROM PCTCSE.cthndl5" + ",
    // PCTCSE.ctlthd2" + ", PCTCSE.ctordru"
    // + ", PSPACEE.spcinfi" + " WHERE hdtm03='WPT1'" + " AND cnid03=" +
    // SQL.format(containerNo)
    // //+ ", PSPACEE.spcinfi" + " WHERE hdtm03='WPT1'" + " AND cnid03 LIKE
    // " + "'%" + containerNo +
    // "%'"
    // + " AND hdtp03='OUT'" + " AND lttp10='ORD'" + " AND hdid10 = hdid03"
    // + " AND orid10 = orid05"
    // + " AND lhfs10='RGS'" + " AND (ortp05='FOT'" + " OR ortp05 = 'BKG'" +
    // " OR ortp05 = 'CNA' )"
    // + " AND trmc45='WPT1'"
    // + " AND cnid45=cnid03" + " AND psex45 IS NOT NULL";

    /**
     * Change COSMOS Schema
     */

    // if (isContainerNo1) {
    // f.setAgentCode(TextString.format(rs.getString("orgv05")));
    // }
    //
    // f.setContainerNoC1(TextString.format(rs.getString("cnid03")));
    // f.setInOrOutC1(rs.getString("hdtp03"));
    // f.setLineC1(TextString.format(rs.getString("lynd05")));
    // f.setFullOrEmptyC1(TextString.format(rs.getString("cnbt03")));
    // f.setISOC1(TextString.format(rs.getString("cnis03")));
    // f.setOrderFOTC1(TextString.format(rs.getString("orrf05")));
    // f.setCurPosC1(TextString.format(rs.getString("psex45")));

    // TextString.format(rs.getString("cnis03")
    String isoCode = null;
    ISOInfo isoInfo = selectISOInfo(isoCode);
    container.setIsoInfo(isoInfo);

    // TextString.format(rs.getString("hdid10"))
    String handlingId = null;
    SealInfo sealInfo = selectSealInfo(handlingId);
    CommonSealDTO commonSealDTO = new CommonSealDTO();
    commonSealDTO.setSeal01Number(sealInfo.getSealNo());
    commonSealDTO.setSeal01Origin(sealInfo.getSealOrigin());
    commonSealDTO.setSeal01Type(sealInfo.getSealType());
    container.setSealAttribute(commonSealDTO);

    // if (rs.getRow() == 0) {
    // f.setFOTBKGFlag(false);
    // }

    log.error("-------END selectGatePassInfo cosmos----------" + transactionDTO + ":" + isContainerNo1);
    return transactionDTO;
  }

  public ISOInfo selectISOInfo(String isoCode) throws Exception {
    log.error("Check selectISOInfo:" + isoCode);

    Optional<ISOCode> codeOpt = isoCodeRepository.findByIsoCode(isoCode);
    if (codeOpt.isPresent()) {
      ISOCode code = codeOpt.get();
      if (!(code == null)) {
        return code.constructISOInfo();
      }
    }
    return null;
  }

  public SealInfo selectSealInfo(String handlingId) throws Exception {

    // sql = "SELECT slor2k" + ", sltp2k" + ", seal2k" + " FROM" + "
    // PCTCSE.ctseal1" + " WHERE" + "
    // rfid2k="
    // + SQL.format(handlingId);
    SealInfo sealInfo = new SealInfo();

    // if (rs.next()) {
    // if (isContainerNo1) {
    // f.setSeal1OriginC1(TextString.format(rs.getString("slor2k")));
    // f.setSeal1TypeC1(TextString.format(rs.getString("sltp2k")));
    // f.setSeal1C1(TextString.format(rs.getString("seal2k")));
    // } else {
    // f.setSeal1OriginC2(TextString.format(rs.getString("slor2k")));
    // f.setSeal1TypeC2(TextString.format(rs.getString("sltp2k")));
    // f.setSeal1C2(TextString.format(rs.getString("seal2k")));
    // }
    // }

    return sealInfo;

  }

  public GatePass getGatePassForImportContainer(ImportContainer container, long companyID) {
    if (!(container == null)) {
      // get gate pass 01
      long gatePassNo = container.getGatePassNo();

      // try to find by gatePassNo1
      Optional<GatePass> gatePassOptional =
          gatePassRepository.findByGatePassNoAndCompany_companyID(gatePassNo, companyID);

      return gatePassOptional.orElse(null);

    }
    return null;
  }

  public void updateGatePass(TransactionDTO transactionDTO, LocalDateTime timeGateIn, String clientId, String cardIdSeq,
      String expImpFlag) throws Exception {

    if (!(transactionDTO == null)) {

      ImportContainer container01 = transactionDTO.getImportContainer01();
      ImportContainer container02 = transactionDTO.getImportContainer02();

      if (!(container01 == null || StringUtils.isEmpty(container01.getContainer().getContainerNumber()))) {
        container01.setContainerPosition("M");
        container01.setIsoInfo(selectISOInfo(container02.getContainer().getContainerISOCode()));
        updateGatePassIntoDb(transactionDTO, container02, cardIdSeq, timeGateIn, clientId, expImpFlag);
      }

      if (!(container02 == null || StringUtils.isEmpty(container02.getContainer().getContainerNumber()))) {
        container02.setContainerPosition("F");
        container02.setIsoInfo(selectISOInfo(container02.getContainer().getContainerISOCode()));
        if (container02.getGatePassNo() != null) {
          container02.setContainerPosition("A");
          updateGatePassIntoDb(transactionDTO, container02, cardIdSeq, timeGateIn, clientId, expImpFlag);
        }
      }

    }

  }

  public void updateGatePassIntoDb(TransactionDTO transactionDTO, ImportContainer container, String cardIdSeq,
      LocalDateTime timeGateIn, String clientId, String expImpFlag) {

    if (!(container == null || container.getGatePassNo() == null)) {
      long gatePassNo = container.getGatePassNo();
      Optional<GatePass> gatePassOpt = gatePassRepository.findByGatePassNo(gatePassNo);
      if (gatePassOpt.isPresent()) {
        GatePass gatePass = gatePassOpt.get();
        CommonGateInOutAttribute commonGateInOut = gatePass.getCommonGateInOut();
        BaseCommonGateInOutAttribute baseCommonGateInOutAttribute = gatePass.getBaseCommonGateInOutAttribute();
        if (commonGateInOut == null) {
          commonGateInOut = new CommonGateInOutAttribute();
        }
        commonGateInOut.setEirNumber(container.getCommonGateInOut().getEirNumber());
        baseCommonGateInOutAttribute.setEirStatus(TransactionStatus.INPROGRESS);

        if (StringUtils.isNotEmpty(cardIdSeq)) {
          long cardId = Long.parseLong(cardIdSeq);
          Optional<Card> card = cardRepository.findOne(cardId);
          if (card.isPresent()) {
            baseCommonGateInOutAttribute.setCard(card.get());
          }
        }

        if (!(container.getUserSessionId() == 0)) {
          Optional<SystemUser> gateInClerkOpt = systemUserRepository.findOne(container.getUserSessionId());
          if (gateInClerkOpt.isPresent()) {
            SystemUser gateInClerk = gateInClerkOpt.get();
            baseCommonGateInOutAttribute.setGateInClerk(gateInClerk);
          }
        }

        baseCommonGateInOutAttribute.setTimeGateIn(timeGateIn);
        baseCommonGateInOutAttribute.setTimeGateInOk(LocalDateTime.now());

        if (StringUtils.isNotEmpty(clientId)) {
          Optional<Client> gateInClientOpt = clientRepository.findOne(Long.parseLong(clientId));
          if (gateInClientOpt.isPresent()) {
            Client gateInClient = gateInClientOpt.get();
            baseCommonGateInOutAttribute.setGateInClient(gateInClient);
          }
        }
        commonGateInOut.setImpExpFlag(ImpExpFlagStatus.fromValue(StringUtils.upperCase(expImpFlag)));

        baseCommonGateInOutAttribute.setPmHeadNo(StringUtils.upperCase(transactionDTO.getPmHeadNo()));
        baseCommonGateInOutAttribute.setPmPlateNo(StringUtils.upperCase(transactionDTO.getPmPlateNo()));

        gatePass.setYardPosition(StringUtils.upperCase(container.getYardPosition()));
        gatePass.setBayCode(StringUtils.upperCase(container.getYardBayCode()));
        gatePass
            .setContainerPosition(ContainerPosition.fromValue(StringUtils.upperCase(container.getContainerPosition())));

        CommonSealAttribute sealAttribute = gatePass.getSealAttribute();
        if (sealAttribute == null) {
          sealAttribute = new CommonSealAttribute();
        }

        CommonSealDTO commonSealAttribute = container.getSealAttribute();
        if (!(commonSealAttribute == null)) {
          sealAttribute.setSeal01Number(commonSealAttribute.getSeal01Number());
          sealAttribute.setSeal01Origin(commonSealAttribute.getSeal01Origin());
          sealAttribute.setSeal01Type(commonSealAttribute.getSeal01Type());
          sealAttribute.setSeal02Number(commonSealAttribute.getSeal02Number());
          sealAttribute.setSeal02Origin(commonSealAttribute.getSeal02Origin());
          sealAttribute.setSeal02Type(commonSealAttribute.getSeal02Type());
        }

        gatePass.setSealAttribute(sealAttribute);

        if (container.getBaseCommonGateInOutAttribute().getHpatBooking().isPresent()) {
          Optional<HPABBooking> hpatBooking = hpatBookingRepository
              .findOne(container.getBaseCommonGateInOutAttribute().getHpatBooking().get());
          if (hpatBooking.isPresent()) {
            baseCommonGateInOutAttribute.setHpatBooking(hpatBooking.get());
          }
        }
        commonGateInOut.setRejectReason(StringUtils.upperCase(container.getCommonGateInOut().getRejectReason()));
        commonGateInOut
            .setGateInStatus(TransactionStatus.fromCode(StringUtils.upperCase(container.getAcceptOrReject())));
        gatePass.setCommonGateInOut(commonGateInOut);

        gatePass.setOrderNo(StringUtils.upperCase(container.getOrderFOT()));
        gatePass.setCurrentPosition(StringUtils.upperCase(container.getCurrentPosition()));

        gatePass.setGateInOut(GateInOutStatus.fromValue(container.getGateInOut()));

        CommonContainerAttribute containerCommonAttribute = gatePass.getContainer();
        if (containerCommonAttribute == null) {
          containerCommonAttribute = new CommonContainerAttribute();

        }
        containerCommonAttribute.setContainerFullOrEmpty(
            ContainerFullEmptyType.fromValue(container.getContainer().getContainerFullOrEmpty()));
        container.getContainer()
            .setContainerISOCode(StringUtils.upperCase(container.getContainer().getContainerISOCode()));
        gatePass.setContainer(containerCommonAttribute);

        gatePass.setShippingLine(StringUtils.upperCase(container.getShippingLine()));
        gatePass.setGateInLaneNo(StringUtils.upperCase(container.getGateInLaneNo()));

        gatePass.setCallCard(transactionDTO.getCallCard());
        Optional<PrintEir> printEirOpt = printEirRepository.findOne(container.getPrintEIRNo());
        if (printEirOpt.isPresent()) {
          PrintEir printEir = printEirOpt.get();
          gatePass.setPrintEir(printEir);
        }

        if (StringUtils.isNotEmpty(container.getCugId())) {
          long cardUsageID = Long.parseLong(container.getCugId());
          Optional<CardUsage> cardUsage = cardUsageRepository.findOne(cardUsageID);
          if (cardUsage.isPresent()) {
            gatePass.setCardUsage(cardUsage.get());
          }
        }

        gatePass.setGatePassNo(gatePassNo);

        Optional<ClientDTO> client = clientRepository.getClientUnitNoByClientID(Long.parseLong(clientId));
        if (client.isPresent()) {
          transactionDTO.setGateInNo(client.get().getUnitNo());
        }

      } else {
        throw new ResultsNotFoundException("Gatepass could not be found!");
      }
    } else {
      throw new BusinessException("Gatepass number is null!");
    }
  }

}
