package com.privasia.scss.gateout.service;

import java.sql.PreparedStatement;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.ETPSolasLog;
import com.privasia.scss.etpws.dto.SolasETPDTO;

@Service("etpSolasLogService")
public class ETPSolasLogService {

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveETPSolasLog(List<SolasETPDTO> solasETPDTOs) {
    if (solasETPDTOs == null || solasETPDTOs.isEmpty()) {
      throw new BusinessException("No data is available to save!");
    }
    final ETPSolasLog etpSolasLog = new ETPSolasLog();
    solasETPDTOs.forEach(etpDTO -> {
      constructETPSolasLog(etpDTO, etpSolasLog);
    });


  }



  public int updateETPSolasLog(SolasDTO solasDTO) {

    Connection conn = null;
    PreparedStatement stmt = null;
    PreparedStatement stmtLog = null;
    int rs = 0;

    try {

      String updateLogDetail = "UPDATE SCSS_ETP_SOLAS_LOG_DETAIL SET RESPONSE_CODE = ?, RESPONSE_MESSAGE = ?, "
          + " REQUEST_SEND_TIME = ?, RES_RECEIVED_TIME = ?, CAL_VARIANCE = ?, WITHIN_TOLERANCE = ?,  "
          + " MGW = ?, SHIPPER_VGM = ?, TERMINAL_VGM = ?, SOLAS_DETAIL = ? " + " WHERE EXP_SEQ = ?";

      String updateLog = "UPDATE SCSS_ETP_SOLAS_LOG log, SCSS_ETP_SOLAS_LOG_DETAIL del  "
          + "SET log.WEIGHING_METHOD = ?, log.WEIGHING_STATION = ?, "
          + " log.CERTIFICATE_NO = ?, log.CER_FILE_DATA = ?, log.WITHNESS_NAME = ?, log.WITHNESS_NRIC = ?,  "
          + " log.TIME_GATE_IN_OK = ?  " + " WHERE del.ETP_SOLAS_LOG = log.ETP_SOLAS_SEQ?" + " AND del.EXP_SEQ = ?";

      conn = SCSSDatabase.getInstance().getConnection();
      conn.setAutoCommit(false);
      stmt = conn.prepareStatement(updateLogDetail);
      stmtLog = conn.prepareStatement(updateLog);
      if (StringUtils.isNotBlank(solasDTO.getExportSEQ01())) {
        stmt.setString(1, solasDTO.getEtpC1ResponseCode());
        stmt.setString(2, solasDTO.getEtpC1ResponseMessage());
        stmt.setTimestamp(3, convertStringToTimestamp(solasDTO.getRequestSendTimeC1()));
        stmt.setTimestamp(4, convertStringToTimestamp(solasDTO.getResponseReceivedTimeC1()));
        stmt.setDouble(5,
            solasDTO.getCalculatedVarianceC1() == null ? 0 : solasDTO.getCalculatedVarianceC1().doubleValue());
        stmt.setString(6, solasDTO.isC1WithInTolerance() == true ? "T" : "F");
        stmt.setInt(7, solasDTO.getMgwC1());
        stmt.setInt(8, solasDTO.getShipperVGMC1());
        stmt.setInt(9, solasDTO.getTerminalVGMC1());
        stmt.setString(10, solasDTO.getSolasDetailC1());
        stmt.setLong(11, Long.parseLong(solasDTO.getExportSEQ01()));
        rs = stmt.executeUpdate();
        conn.commit();

        stmtLog.setInt(1, solasDTO.getWeighingMethod());
        stmtLog.setString(2, solasDTO.getWeighStation());
        stmtLog.setString(3, solasDTO.getCertificateNo());
        stmtLog.setString(4, solasDTO.getCertificate() == null ? "T" : "F");
        stmtLog.setString(5, solasDTO.getIssueBy());
        stmtLog.setString(6, solasDTO.getIssuerNRIC());
        stmtLog.setTimestamp(7, convertStringToTimestamp(solasDTO.getGateInOK()));
        stmtLog.setLong(8, Long.parseLong(solasDTO.getExportSEQ01()));
        rs = stmt.executeUpdate();
        conn.commit();

        System.out.println("updated solas logs : " + solasDTO.getExportSEQ01());
      }


      if (StringUtils.isNotBlank(solasDTO.getExportSEQ02())) {
        stmt.setString(1, solasDTO.getEtpC2ResponseCode());
        stmt.setString(2, solasDTO.getEtpC2ResponseMessage());
        stmt.setTimestamp(3, convertStringToTimestamp(solasDTO.getRequestSendTimeC2()));
        stmt.setTimestamp(4, convertStringToTimestamp(solasDTO.getResponseReceivedTimeC2()));
        stmt.setDouble(5,
            solasDTO.getCalculatedVarianceC2() == null ? 0 : solasDTO.getCalculatedVarianceC2().doubleValue());
        stmt.setString(6, solasDTO.isC2WithInTolerance() == true ? "T" : "F");
        stmt.setInt(7, solasDTO.getMgwC2());
        stmt.setInt(8, solasDTO.getShipperVGMC2());
        stmt.setInt(9, solasDTO.getTerminalVGMC2());
        stmt.setString(10, solasDTO.getSolasDetailC2());
        stmt.setLong(11, Long.parseLong(solasDTO.getExportSEQ02()));
        rs = stmt.executeUpdate();
        conn.commit();

        System.out.println("updated solas logs : " + solasDTO.getExportSEQ01());
      }


    } catch (Exception e) {
      rs = 0;
      e.printStackTrace();
      gateLogger.error("Exceptions happen!", e);
    } finally {
      try {
        if (stmt != null) {
          stmt.close();
        }

        if (conn != null) {
          conn.close();
        }
      } catch (Exception e) {
        gateLogger.error("Exceptions happen!", e);
      }
    }
    return rs;

  }



}
