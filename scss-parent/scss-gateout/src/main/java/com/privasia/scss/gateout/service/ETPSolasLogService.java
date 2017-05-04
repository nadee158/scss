package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.ETPSolasLog;
import com.privasia.scss.core.model.ETPSolasLogDetail;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.repository.ETPSolasLogRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.etpws.dto.SolasETPDTO;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;

@Service("etpSolasLogService")
public class ETPSolasLogService {

  private ExportsRepository exportsRepository;

  private ETPSolasLogRepository etpSolasLogRepository;

  @Autowired
  public void setEtpSolasLogRepository(ETPSolasLogRepository etpSolasLogRepository) {
    this.etpSolasLogRepository = etpSolasLogRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  private DateTimeFormatter dateTimeFormatter =
      DateTimeFormatter.ofPattern(ETPWebserviceClient.WEB_SERVICE_DATE_PATTERN);

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveETPSolasLog(List<SolasETPDTO> solasETPDTOs) {
    if (solasETPDTOs == null || solasETPDTOs.isEmpty()) {
      throw new BusinessException("No data is available to save!");
    }
    final ETPSolasLog etpSolasLog = new ETPSolasLog();
    solasETPDTOs.forEach(etpDTO -> {

      constructETPSolasLog(etpDTO, etpSolasLog);
    });
    etpSolasLogRepository.save(etpSolasLog);
  }



  public void constructETPSolasLog(SolasETPDTO etpDTO, ETPSolasLog etpSolasLog) {
    if (etpSolasLog.getEtpSolasLogDetails() == null) {
      etpSolasLog.setEtpSolasLogDetails(new HashSet<ETPSolasLogDetail>());
      etpSolasLog.setCertificateNumber(etpDTO.getCertificateNo());
      byte[] pdfBytes = etpDTO.getCertificate();
      if (!((pdfBytes == null || pdfBytes.length <= 0))) {
        etpSolasLog.setFiledata(true);
      }
      etpSolasLog.setTimeGateInOk(etpDTO.getGateInOK());
      etpSolasLog.setWeighingMethod(etpDTO.getWeighingMethod());
      etpSolasLog.setWeighingStation(etpDTO.getWeighStation());
      etpSolasLog.setWithnessName(etpDTO.getIssueBy());
      etpSolasLog.setWithnessNRIC(etpDTO.getIssuerNRIC());
      etpSolasLog.getEtpSolasLogDetails().add(constructEtpSolasLogDetails(etpDTO, etpSolasLog));
    } else {
      etpSolasLog.getEtpSolasLogDetails().add(constructEtpSolasLogDetails(etpDTO, etpSolasLog));
    }
  }



  public ETPSolasLogDetail constructEtpSolasLogDetails(SolasETPDTO etpDTO, ETPSolasLog etpSolasLog) {
    ETPSolasLogDetail etpSolasLogDetail = new ETPSolasLogDetail();
    etpSolasLogDetail.setCalculatedVariance(etpDTO.getCalculatedVariance());
    etpSolasLogDetail.setEtpSolasLog(etpSolasLog);
    Exports export = exportsRepository.findOne(Long.parseLong(etpDTO.getExportSEQ())).orElse(null);
    etpSolasLogDetail.setExport(export);
    etpSolasLogDetail.setGrossWeight(etpDTO.getGrossWeight());
    etpSolasLogDetail.setMgw(etpDTO.getMgw());
    
    if(StringUtils.isNotEmpty(etpDTO.getRequestSendTime())){
    	etpSolasLogDetail.setRequestSendTime(LocalDateTime.parse(etpDTO.getRequestSendTime(), dateTimeFormatter));
    }
    
    etpSolasLogDetail.setResponseCode(etpDTO.getEtpResponseCode());
    etpSolasLogDetail.setResponseMessage(etpDTO.getEtpResponseMessage());
    etpSolasLogDetail.setResponseReceivedTime(etpDTO.getResponseReceivedTime());
    etpSolasLogDetail.setShipperVGM(etpDTO.getShipperVGM());
    etpSolasLogDetail.setSolasDetail(etpDTO.getSolasDetail());
    etpSolasLogDetail.setTerminalVGM(etpDTO.getTerminalVGM());
    etpSolasLogDetail.setWithInTolerance(etpDTO.isWithInTolerance());
    etpSolasLogDetail.getRequestSendTime();
    return etpSolasLogDetail;
  }

}
