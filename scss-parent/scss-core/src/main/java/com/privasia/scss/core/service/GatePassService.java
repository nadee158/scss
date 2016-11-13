package com.privasia.scss.core.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.GatePassErrMsg;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.CommonContainerAttribute;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WDCGlobalSetting;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.core.util.constant.GatePassStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;



@Service("gatePassService")
public class GatePassService {

  private static final Log log = LogFactory.getLog(GatePassService.class);

  @Autowired
  private GatePassRepository gatePassRepository;

  @Autowired
  private WDCGatePassRepository wdcGatePassRepository;

  @Autowired
  private WDCGlobalSettingRepository wdcGlobalSettingRepository;

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
      if (!(gatePass.getCommonGateInOut() == null || gatePass.getCommonGateInOut().getEirStatus() == null)) {
        if (gatePass.getCommonGateInOut().getEirStatus().equals(eirStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_IS_USED, new Object[] {gatePassNo}));
        }
      }
      log.debug("---END check gatepass no approved, EIRStatus = A? ----" + gatePassNo + ":" + truckHeadNo);

      // check gatepass is in progress (in using), EIRStatus = I?
      log.debug("--START check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo + ":" + truckHeadNo);
      eirStatus = TransactionStatus.INPROGRESS;
      if (!(gatePass.getCommonGateInOut() == null || gatePass.getCommonGateInOut().getEirStatus() == null)) {
        if (gatePass.getCommonGateInOut().getEirStatus().equals(eirStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_IN_PROGRESS, new Object[] {gatePassNo}));
        }
      }
      log.debug("--END check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo + ":" + truckHeadNo);


      // check gatepass is cancelled?
      log.debug("------START check gatepass is cancelled ------" + gatePassNo + ":" + truckHeadNo);
      gatePassStatus = GatePassStatus.CANCEL;
      if (!(gatePass.getGatePassStatus() == null)) {
        if (gatePass.getGatePassStatus().equals(gatePassStatus)) {
          throw new BusinessException(
              CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_CANCEL, new Object[] {gatePassNo}));
        }
        log.error("-----END check gatepass is cancelled -----" + gatePassNo + ":" + truckHeadNo);

        // check gatepass is valid , EIRStatus = N?
        log.error("------START check gatepass is valid , EIRStatus = N? ----" + gatePassNo + ":" + truckHeadNo);
        eirStatus = TransactionStatus.NEW;
        gatePassStatus = GatePassStatus.ACTIVE;
        if (!(gatePass.getCommonGateInOut() == null || gatePass.getCommonGateInOut().getEirStatus() == null
            || gatePass.getGatePassStatus() == null)) {
          if (gatePass.getCommonGateInOut().getEirStatus().equals(eirStatus)
              && gatePass.getGatePassStatus().equals(gatePassStatus)) {
            throw new BusinessException(
                CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_INVALID, new Object[] {gatePassNo}));
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
                CommonUtil.formatMessageCode(GatePassErrMsg.DATE_GATEPASS_EXPIRY, new Object[] {gatePassNo}));
          }
          log.error("------END check Gate Pass Expiry Date By YPN? ---- gatePassNo: " + gatePassNo + " :validateDate: "
              + validateDate + " :today: " + today);
        }
        log.error("------ENDING CHECKING SCSS GATEPASS is valid------" + gatePassNo + ":" + truckHeadNo);


        log.error("------STARTING CHECKING WDC GATEPASS is valid------" + gatePassNo + ":" + truckHeadNo);
        Optional<WDCGatePass> wdcGatePassOpt = wdcGatePassRepository.findByGatePassNO(gatePassNo);

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
                  CommonUtil.formatMessageCode(GatePassErrMsg.DATE_GATEPASS_EXPIRY, new Object[] {gatePassNo}));
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
                      throw new BusinessException(CommonUtil.formatMessageCode(GatePassErrMsg.DATE_GATEPASS_EDO_EXPIRY,
                          new Object[] {gatePassNo}));
                    }
                  } else {
                    String lineCode = wdcGatePass.getGateOrder().getLineCode();
                    if (StringUtils.isNotBlank(lineCode)) {
                      // EtpPlusWebService etpPlusWebService = EtpPlusWebService.getInstance();
                      // boolean isEdoLineEnabled = etpPlusWebService.getEdoExpiryForLine(lineCode);
                      // if (isEdoLineEnabled) {
                      // if (edoExpiryDto.getEdoExpiryDate() == null) {
                      // return GatePassErrMsg.EDO_EXPIRY_DATE_NULL;
                      // }
                      // }
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
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_COMPANY_NOT_MATCH, new Object[] {gatePassNo}));
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
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_OGA_INTERNAL_BLOCK, new Object[] {gatePassNo}));
    } else if (isOgaBlock && !isInternalBlock) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_OGA_BLOCK, new Object[] {gatePassNo}));
    } else if (!isOgaBlock && isInternalBlock) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_INTERNAL_BLOCK, new Object[] {gatePassNo}));
    }

    /**
     * End OGA And Internal Block
     */
    log.error("-------------END OGA And Internal Block -----------" + gatePassNo + ":" + containerNo);

    return checkPreArrival(containerNo, cardIdSeq, "MANUALLY", "");

  }

  private boolean checkPreArrival(String containerNo, String cardIdSeq, String hpatSeqId, String truckHeadNo)
      throws Exception {
    // GateInDAOImpl gateInDao = GateInDAOImpl.getInstance();
    //
    // if (StringUtils.isNotBlank(hpatSeqId)) {
    // throw new BusinessException(CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_OK, new
    // Object[] {gatePassNo}));
    // } else {
    // long start = System.currentTimeMillis();
    // log.error("Check Gate Pass pre-Arrival Booking.." + containerNo + ":" + truckHeadNo);
    //
    // boolean isHpatExist = this.checkHpatBookingExist(containerNo, cardIdSeq, truckHeadNo);
    //
    // // Call Web Service
    // if (!isHpatExist) {
    // ScssCardDto scssCardDto = gateInDao.getSCardInfo(cardIdSeq);
    // if (scssCardDto != null) {
    // String icNo = "";
    //
    // if (StringUtils.isNotBlank(scssCardDto.getNewICNo())) {
    // icNo = scssCardDto.getNewICNo();
    // } else if (StringUtils.isNotBlank(scssCardDto.getOldICNo())) {
    // icNo = scssCardDto.getOldICNo();
    // } else if (StringUtils.isNotBlank(scssCardDto.getPassportNo())) {
    // icNo = scssCardDto.getPassportNo();
    // }
    // String etpFlag = gateInDao.getWDCGlobalSeeting("ETP_HPAT");
    // if ("Y".equalsIgnoreCase(etpFlag)) {
    // EtpPlusWebService etpWebService = EtpPlusWebService.getInstance();
    // etpWebService.getHpatDetails(scssCardDto.getCrdScardNo(), icNo, scssCardDto.getCompCode(),
    // new Date());
    // isHpatExist = this.checkHpatBookingExist(containerNo, cardIdSeq, truckHeadNo);
    // } else {
    // isHpatExist = true;
    // }
    // }
    // }
    //
    // long end = System.currentTimeMillis();
    // log.error("End Check Gate Pass pre-Arrival Booking time taken : " + containerNo + ":" +
    // truckHeadNo
    // + (end - start) / 1000.0 + "sec");
    //
    // if (isHpatExist) {
    // return GatePassErrMsg.GATE_PASS_OK;
    // } else {
    // return check_Laden(containerNo);
    // }
    // }
    return false;
  }


  public boolean checkOGABlock(String containerNo) throws Exception {
    // AS400Database db = null;

    boolean isOGABlock = false;


    // sql = "SELECT cnid80"
    // + ", rlgb80"
    // + ", bldt80"
    // + ", bltd80"
    // + ", dgfs80"
    // + " FROM PCTCSE.ctcubl"
    // + " WHERE cnid80=" + SQL.format(containerNo)
    // + " AND cufs80='RGS'"
    // + " AND rldt80=0"
    // + " AND rltd80=0"
    // + " AND trmk80='WPT1'";


    // if (rs.next()) {
    // //return GatePassErrMsg.GATE_PASS_OGA_BLOCK;
    // isOGABlock = true;
    // }

    return isOGABlock;

  }

  public boolean checkInternalBlock(String containerNo) throws Exception {
    // AS400Database db = null;
    boolean isInternalBlock = false;

    // sql = "SELECT intp30"
    // + " FROM PCTCSE.ctinst7"
    // + " WHERE cnid30=" + SQL.format(containerNo)
    // + " AND (infs30='RGS'"
    // + " OR infs30='ACT')"
    // + " AND intp30='BLK'"
    // + " ORDER BY orid30 DESC";
    //
    // if (rs.next()) {
    // //return GatePassErrMsg.GATE_PASS_INTERNAL_BLOCK;
    // isInternalBlock = true;
    // }
    return isInternalBlock;
  }



  // if (ret == GatePassErrMsg.GATE_PASS_IS_USED) {
  // returnmsg += MessageCode.format("ERR_MSG_060", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (ret == GatePassErrMsg.GATE_PASS_IN_PROGRESS) {
  // returnmsg += MessageCode.format("ERR_MSG_061", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (ret == GatePassErrMsg.GATE_PASS_CANCEL) {
  // returnmsg += MessageCode.format("ERR_MSG_062", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (ret == GatePassErrMsg.GATE_PASS_INVALID) {
  // returnmsg += MessageCode.format("ERR_MSG_011", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (!isMCByPass && ret == GatePassErrMsg.GATE_PASS_COMPANY_NOT_MATCH) {
  // returnmsg += MessageCode.format("ERR_MSG_012", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (!isMCByPass && ret == GatePassErrMsg.GATE_PASS_NO_PREARRIVAL) {
  // returnmsg += gateInDto.getGatePassNo2() + "No pre-arrival booking available";
  // } else if (ret == GatePassErrMsg.GATE_PASS_OGA_INTERNAL_BLOCK) {
  // f.setInternalBlock(true);
  // f.setOGABlock(true);
  // returnmsg += MessageCode.format("ERR_MSG_074", new Object[] { gateInDto.getContainerNoC2() });
  // } else if (ret == GatePassErrMsg.GATE_PASS_INTERNAL_BLOCK) {
  // f.setInternalBlock(true);
  // returnmsg += MessageCode.format("ERR_MSG_072", new Object[] { gateInDto.getContainerNoC2() });
  // } else if (ret == GatePassErrMsg.GATE_PASS_OGA_BLOCK) {
  // f.setOGABlock(true);
  // returnmsg += MessageCode.format("ERR_MSG_073", new Object[] { gateInDto.getContainerNoC2() });
  // } else if (ret == GatePassErrMsg.DATE_GATEPASS_EXPIRY) {
  // returnmsg += MessageCode.format("ERR_MSG_080", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (ret == GatePassErrMsg.DATE_GATEPASS_EDO_EXPIRY) {
  // returnmsg += MessageCode.format("ERR_MSG_088", new Object[] { gateInDto.getGatePassNo2() });
  // } else if (ret == GatePassErrMsg.EDO_EXPIRY_DATE_NULL) {
  // returnmsg += MessageCode.format("ERR_MSG_090", new Object[] { gateInDto.getGatePassNo2() });
  // }



}
