package com.privasia.scss.gatein.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.opus.dto.OpusGateInReadRequest;
import com.privasia.scss.opus.dto.OpusGateInReadResponse;
import com.privasia.scss.opus.dto.OpusGateInWriteRequest;
import com.privasia.scss.opus.dto.OpusGateInWriteResponse;
import com.privasia.scss.opus.service.OpusGateInReadService;
import com.privasia.scss.opus.service.OpusGateInWriteService;
import com.privasia.scss.opus.service.OpusService;

@Service("importExportGateInService")
public class ImportExportGateInService {

  private ImportGateInService importGateInService;

  private ExportGateInService exportGateInService;

  private OpusGateInReadService opusGateInReadService;

  private OpusGateInWriteService opusGateInWriteService;

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


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public GateInReponse populateGateIn(GateInRequest gateInRequest) {

    List<ImportContainer> importContainers = null;
    List<ExportContainer> exportContainers = null;

    if (gateInRequest.getGatePass1() > 0 || gateInRequest.getGatePass2() > 0) {
      importContainers = importGateInService.populateGateIn(gateInRequest);
    }

    if (!(StringUtils.isEmpty(gateInRequest.getExpContainer1())
        || StringUtils.isEmpty(gateInRequest.getExpContainer2()))) {
      exportContainers = exportGateInService.populateGateIn(gateInRequest);
    }

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    gateInRequest.setUserName((String) authentication.getPrincipal());

    // call opus -
    OpusGateInReadRequest gateInReadRequest = opusGateInReadService.constructOpenGateInRequest(gateInRequest);
    OpusGateInReadResponse gateInReadResponse = opusGateInReadService.getGateInReadResponse(gateInReadRequest);
    GateInReponse gateInReponse = new GateInReponse();
    gateInReponse.setImportContainers(importContainers);
    gateInReponse.setExportContainers(exportContainers);
    gateInReponse = opusGateInReadService.constructGateInReponse(gateInReadResponse, gateInReponse);

    return gateInReponse;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public GateInReponse saveGateInInfo(GateInWriteRequest gateInWriteRequest) {
    List<ImportContainer> importContainers = null;
    List<ExportContainer> exportContainers = null;

    // call opus -
    OpusGateInWriteRequest opusGateInWriteRequest =
        opusGateInWriteService.constructOpusGateInWriteRequest(gateInWriteRequest);
    OpusGateInWriteResponse opusGateInWriteResponse =
        opusGateInWriteService.getGateInWriteResponse(opusGateInWriteRequest);

    String errorMessage = OpusService.hasErrorMessage(opusGateInWriteResponse.getErrorList());
    if (StringUtils.isNotEmpty(errorMessage)) {
      // throw new business exception with constructed message - there is an error
      throw new BusinessException(errorMessage);
    }

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
