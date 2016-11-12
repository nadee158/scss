package com.privasia.scss.core.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.common.util.GatePassErrMsg;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.util.constant.GatePassStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;



@Service("gatePassService")
public class GatePassService {

  private static final Log log = LogFactory.getLog(GatePassService.class);

  private static final String[] COLUMN_EIR_STATUS = {"gtp_eirstatus", "gtp_gatepassno"};
  private static final String[] COLUMN_GP_STATUS = {"gtp_gatepassstatus", "gtp_gatepassno"};
  private static final String[] COLUMN_EIR_GP_STATUS = {"gtp_eirstatus", "gtp_gatepassstatus", "gtp_gatepassno"};
  private static final String TABLE_GATE_PASS = "scss_gate_pass";


  public static final String GP_STATUS_ACTIVE = "A";
  public static final String GP_STATUS_CANCELLED = "C";

  public static final String IMP_FLAG = "I";
  public static final String IMPEXP_FLAG = "B";

  public static final String GATEIN_MC_FLAG = "1";
  public static final String GATEIN_NORMAL_FLAG = "0";

  @Autowired
  private GatePassRepository gatePassRepository;

  public int validateInfo_N(String cardIdSeq, String gatePassNo, String check, String hpatSeqId, String truckHeadNo,
      String companyId) throws Exception {
    int result = 0;

    long recordCount = 0;
    TransactionStatus eirStatus = null;
    GatePassStatus gatePassStatus = null;

    long gatePassNumber = Long.parseLong(gatePassNo);

    // check gatepass no approved, EIRStatus = A?
    log.debug("-----START check gatepass no approved, EIRStatus = A? ----" + gatePassNo + ":" + truckHeadNo);
    // data = new String[] {EIR_STATUS_APPROVED, gatePassNo};
    // {"gtp_eirstatus", "gtp_gatepassno"}
    // "scss_gate_pass"
    eirStatus = TransactionStatus.APPROVED;
    recordCount = gatePassRepository.countByCommonGateInOut_EirStatusAndGatePassNo(eirStatus, gatePassNumber);
    if (recordCount <= 0) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_IS_USED, new Object[] {gatePassNo}));
    }
    log.debug("---END check gatepass no approved, EIRStatus = A? ----" + gatePassNo + ":" + truckHeadNo);


    // check gatepass is in progress (in using), EIRStatus = I?
    log.debug("--START check gatepass is in progress (in using), EIRStatus = I? --" + gatePassNo + ":" + truckHeadNo);
    // data = new String[] {GatePass.EIR_STATUS_INPROGRESS, gatePassNo};
    // {"gtp_eirstatus", "gtp_gatepassno"};
    eirStatus = TransactionStatus.INPROGRESS;
    recordCount = gatePassRepository.countByCommonGateInOut_EirStatusAndGatePassNo(eirStatus, gatePassNumber);
    if (recordCount <= 0) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_IN_PROGRESS, new Object[] {gatePassNo}));
    }
    log.debug("---END check gatepass is in progress (in using), EIRStatus = I? ----" + gatePassNo + ":" + truckHeadNo);


    log.debug("------START check gatepass is cancelled ------" + gatePassNo + ":" + truckHeadNo);
    // check gatepass is cancelled?
    // data = new String[] {GatePass.GP_STATUS_CANCELLED, gatePassNo};
    // {"gtp_gatepassstatus", "gtp_gatepassno"};
    gatePassStatus = GatePassStatus.CANCEL;
    recordCount = gatePassRepository.countByGatePassStatusAndGatePassNo(gatePassStatus, gatePassNumber);
    if (recordCount <= 0) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_CANCEL, new Object[] {gatePassNo}));
    }
    log.error("-----END check gatepass is cancelled -----" + gatePassNo + ":" + truckHeadNo);


    log.error("------START check gatepass is valid , EIRStatus = N? ----" + gatePassNo + ":" + truckHeadNo);
    // check gatepass is valid , EIRStatus = N?
    // data = new String[] {GatePass.EIR_STATUS_NEW, GatePass.GP_STATUS_ACTIVE, gatePassNo};
    // {"gtp_eirstatus", "gtp_gatepassstatus", "gtp_gatepassno"};
    eirStatus = TransactionStatus.NEW;
    gatePassStatus = GatePassStatus.ACTIVE;
    recordCount = gatePassRepository.countByCommonGateInOut_EirStatusAndGatePassStatusAndGatePassNo(eirStatus,
        gatePassStatus, gatePassNumber);
    if (recordCount <= 0) {
      throw new BusinessException(
          CommonUtil.formatMessageCode(GatePassErrMsg.GATE_PASS_INVALID, new Object[] {gatePassNo}));
    }

    log.error("------END check gatepass is valid , EIRStatus = N? ------" + gatePassNo + ":" + truckHeadNo);


    /**
     * Gate Pass Expiry Date
     */
    log.error("-------------START Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);
    // String sql = "SELECT DATE_GATEPASS_VALID FROM WDC_GATE_PASS " + " WHERE GATE_PASS_NO ="
    // + SQL.format(gatePassNo) + " AND DATE_GATEPASS_VALID IS NOT NULL ";
    // if (CheckDB.isGatePassValid(gatePassNo)) {
    // return GatePassErrMsg.DATE_GATEPASS_EXPIRY;
    // }
    log.error("-------------END Gate Pass Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);

    /**
     * Gate Pass Expiry Date By YPN
     */
    // String sql = "SELECT DATE_GATEPASS_VALID FROM SCSS_GATE_PASS " + " WHERE GTP_GATEPASSNO ="
    // + SQL.format(gatePassNo) + " AND DATE_GATEPASS_VALID IS NOT NULL ";
    // GatePass gatePass=findByGatePassNoAnd
    // if (CheckDB.isGatePassValidByYPN(gatePassNo)) {
    // return GatePassErrMsg.DATE_GATEPASS_EXPIRY;
    // }

    return result;
  }


//@formatter:off    
//  if (ret == GatePassErrMsg.GATE_PASS_IS_USED) {
//    returnmsg += MessageCode.format("ERR_MSG_060", new Object[] { gateInDto.getGatePassNo2() });
//} else if (ret == GatePassErrMsg.GATE_PASS_IN_PROGRESS) {
//    returnmsg += MessageCode.format("ERR_MSG_061", new Object[] { gateInDto.getGatePassNo2() });
//} else if (ret == GatePassErrMsg.GATE_PASS_CANCEL) {
//    returnmsg += MessageCode.format("ERR_MSG_062", new Object[] { gateInDto.getGatePassNo2() });
//} else if (ret == GatePassErrMsg.GATE_PASS_INVALID) {
//    returnmsg += MessageCode.format("ERR_MSG_011", new Object[] { gateInDto.getGatePassNo2() });
//} else if (!isMCByPass && ret == GatePassErrMsg.GATE_PASS_COMPANY_NOT_MATCH) {
//    returnmsg += MessageCode.format("ERR_MSG_012", new Object[] { gateInDto.getGatePassNo2() });
//} else if (!isMCByPass && ret == GatePassErrMsg.GATE_PASS_NO_PREARRIVAL) {
//    returnmsg += gateInDto.getGatePassNo2() + "No pre-arrival booking available";
//} else if (ret == GatePassErrMsg.GATE_PASS_OGA_INTERNAL_BLOCK) {
//    f.setInternalBlock(true);
//    f.setOGABlock(true);
//    returnmsg += MessageCode.format("ERR_MSG_074", new Object[] { gateInDto.getContainerNoC2() });
//} else if (ret == GatePassErrMsg.GATE_PASS_INTERNAL_BLOCK) {
//    f.setInternalBlock(true);
//    returnmsg += MessageCode.format("ERR_MSG_072", new Object[] { gateInDto.getContainerNoC2() });
//} else if (ret == GatePassErrMsg.GATE_PASS_OGA_BLOCK) {
//    f.setOGABlock(true);
//    returnmsg += MessageCode.format("ERR_MSG_073", new Object[] { gateInDto.getContainerNoC2() });
//} else if (ret == GatePassErrMsg.DATE_GATEPASS_EXPIRY) {
//    returnmsg += MessageCode.format("ERR_MSG_080", new Object[] { gateInDto.getGatePassNo2() });
//} else if (ret == GatePassErrMsg.DATE_GATEPASS_EDO_EXPIRY) {
//    returnmsg += MessageCode.format("ERR_MSG_088", new Object[] { gateInDto.getGatePassNo2() });
//} else if (ret == GatePassErrMsg.EDO_EXPIRY_DATE_NULL) {
//    returnmsg += MessageCode.format("ERR_MSG_090", new Object[] { gateInDto.getGatePassNo2() });
//}



//  public int validateInfo_N(String cardIdSeq, String gatePassNo, String check, String hpatSeqId, String truckHeadNo) throws Exception {
//    String[] data;
//    Connection conn = null;
//
//    try {
//      conn = SCSSDatabase.getInstance().getConnection();
//      log.error("-------------START check gatepass no approved, EIRStatus = A? -----------" + gatePassNo + ":" + truckHeadNo);


  

 


//
//      
//      /**
//       * Edo Expiry Date //edo logs
//       */
//      GateInDAOImpl gateInDao = GateInDAOImpl.getInstance();
//      String etpEdoExpiryDateFlag = gateInDao.getWDCGlobalSeeting("EDO_EXP");
//      if ("Y".equalsIgnoreCase(etpEdoExpiryDateFlag)){
//          log.error("-------------START Edo Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);
//          EdoExpiryDto edoExpiryDto = CheckDB.getEdoExipryDate(gatePassNo);
//          log.error("-------------END Edo Expiry Date -----------" + gatePassNo + ":" + truckHeadNo);
//          if (edoExpiryDto != null) { 
//             if ("I".equalsIgnoreCase(edoExpiryDto.getGateOrderType()) || "J".equalsIgnoreCase(edoExpiryDto.getGateOrderType()) ||
//                     "K".equalsIgnoreCase(edoExpiryDto.getGateOrderType())) {
//                 if (edoExpiryDto.getEdoExpiryDate() != null){
//                     Date today = new Date();
//                     if (today.after(edoExpiryDto.getEdoExpiryDate())) {
//                         return GatePassErrMsg.DATE_GATEPASS_EDO_EXPIRY;
//                     } 
//                 } else {
//                     if (StringUtils.isNotBlank(edoExpiryDto.getLineCode())) {
//                     EtpPlusWebService etpPlusWebService = EtpPlusWebService.getInstance();
//                     boolean isEdoLineEnabled = etpPlusWebService.getEdoExpiryForLine(edoExpiryDto.getLineCode());
//                        if (isEdoLineEnabled){
//                            if (edoExpiryDto.getEdoExpiryDate() == null){
//                                return GatePassErrMsg.EDO_EXPIRY_DATE_NULL;
//                            }
//                        }
//                     }
//                 }
//             }
//          }
//      }
//      
//      /**
//       * Checking the Haulage Information
//       */     
//      return check_MatchCompany_PreArrival_New(conn, cardIdSeq, gatePassNo, check, hpatSeqId, truckHeadNo);
//      
//    } catch (Exception e) {
//      throw e;
//    } finally {
//      if (conn != null) {
//        conn.close();
//      }
//    }
//  }

  
//  public static boolean isRecordExist(String[] columns, String table, String[] data) {
//    boolean ret = false;
//
//    String sql = "";
//
//    for (int i = 0; i < columns.length; i++) {
//        sql = sql + columns[i] + " = " + SQL.format(data[i]);
//        if (i != columns.length - 1) {
//            sql = sql + " AND ";
//        }
//    }
//
//    sql = "SELECT COUNT(1) FROM "
//          + table
//          + " WHERE "
//          + sql;
//    Connection conn = null;
//    Statement stmt = null;
//    ResultSet rs = null;
}
