package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.gateout.dto.FileDTO;

@Service("exportGateOutService")
public class ExportGateOutService {

  private ModelMapper modelMapper;

  private ShipCodeRepository shipCodeRepository;

  private ExportsRepository exportsRepository;

  private ExportsQRepository exportsQRepository;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
    this.shipCodeRepository = shipCodeRepository;
  }

  @Autowired
  public void setExportsRepository(ExportsRepository exportsRepository) {
    this.exportsRepository = exportsRepository;
  }

  @Autowired
  public void setExportsQRepository(ExportsQRepository exportsQRepository) {
    this.exportsQRepository = exportsQRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ExportContainer> populateGateOut(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {

    Optional<List<Exports>> optExpList =
        exportsRepository.fetchInProgressTransaction(gateOutRequest.getCardID(), TransactionStatus.INPROGRESS);
    List<Exports> inprogressExpList = optExpList.orElseThrow(() -> new BusinessException(
        "No InProgress Export Transaction for the scan card ! " + gateOutRequest.getCardID()));

    List<ExportContainer> exportContainerList = new ArrayList<ExportContainer>();

    inprogressExpList.forEach(export -> {
      ExportContainer exportContainer = new ExportContainer();
      modelMapper.map(export, exportContainer);

      if (export.getBaseCommonGateInOutAttribute() != null) {

        if (!(export.getBaseCommonGateInOutAttribute().getTimeGateIn() == null)) {
          LocalDateTime timeGateIn = export.getBaseCommonGateInOutAttribute().getTimeGateIn();
          gateOutReponse.setGateInDateTime(timeGateIn);
        }

      }
      // adding log info
      exportContainerList.add(exportContainer);
      if (StringUtils.isEmpty(gateOutRequest.getExpContainer1())) {
        gateOutRequest.setExpContainer1(export.getContainer().getContainerNumber());
      } else {
        gateOutRequest.setExpContainer2(export.getContainer().getContainerNumber());
      }
      gateOutRequest.setTruckHeadNo(export.getBaseCommonGateInOutAttribute().getPmHeadNo());
    });

    return exportContainerList;

  }

  public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
    if (!(exportContainers == null || exportContainers.isEmpty())) {
      List<String> shippingCodes =
          exportContainers.stream().map(ExportContainer::getShipCode).collect(Collectors.toList());

      Optional<List<ShipCode>> list =
          shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE, shippingCodes);
      if (list.isPresent()) {
        List<ShipCode> codes = list.orElse(null);
        for (ShipCode shipCode : codes) {
          for (ExportContainer item : exportContainers) {
            if (StringUtils.equals(shipCode.getShippingCode(), item.getShipCode())) { 
              item.setStoragePeriod(shipCode.getStoragePeriod());
            }
          }
        }

        return codes;
      }
    }
    return null;
  }

  // @Async
  @SolasApplicable
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest, Client gateOutClient, SystemUser gateOutClerk,
      Client booth) {

    if (gateOutWriteRequest.getExportContainers() == null || gateOutWriteRequest.getExportContainers().isEmpty())
      throw new BusinessException("Invalid Request to Update Export !");

    List<ExportContainer> exportContainers = gateOutWriteRequest.getExportContainers();
    
    exportContainers.forEach(exportContainer -> {
      Optional<Exports> optExport = exportsRepository.findOne(exportContainer.getExportID());
      Exports exports = optExport.orElseThrow(
          () -> new BusinessException("Invalid Exports Information to Update ! " + exportContainer.getExportID()));

      if (StringUtils.isEmpty(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus()))
        throw new BusinessException("Invalid EIR Status for Exports : " + exportContainer.getExportID());

      exports.getBaseCommonGateInOutAttribute()
          .setEirStatus(TransactionStatus.fromCode(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus()));
      exports.getBaseCommonGateInOutAttribute().setTimeGateOut(gateOutWriteRequest.getGateOUTDateTime());
      exports.getBaseCommonGateInOutAttribute().setTimeGateOutOk(LocalDateTime.now());
      exports.getBaseCommonGateInOutAttribute().setGateOutBoothClerk(gateOutClerk);
      exports.getBaseCommonGateInOutAttribute().setGateOutBoothNo(String.valueOf(booth.getClientID()));
      exports.getBaseCommonGateInOutAttribute().setGateOutClerk(gateOutClerk);
      exports.getBaseCommonGateInOutAttribute().setGateOutClient(gateOutClient);
      exports.getCommonGateInOut().setRejectReason(exportContainer.getGateOutRemarks());

      exportsRepository.save(exports);

      Optional<ExportsQ> exportQOpt = exportsQRepository.findOne(exports.getExportID());
      ExportsQ exportq = exportQOpt.orElseThrow(
          () -> new ResultsNotFoundException("Not valid Gate In ExportQ Process found ! " + exports.getExportID()));

      modelMapper.map(exports, exportq);
      exportsQRepository.save(exportq);
      
    });
    // return new AsyncResult<Boolean>(true);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void updateExportReference(FileDTO fileDTO) {

    Optional<List<Exports>> exportsOptList = exportsRepository.findByExportIDIn(
        Arrays.asList(fileDTO.getExportNoSeq1().orElse(null), fileDTO.getExportNoSeq2().orElse(null)));
    if (exportsOptList.isPresent() && !exportsOptList.get().isEmpty()) {
      exportsOptList.get().forEach(exports -> {
        assignUpdatedValuesExports(exports, fileDTO);
        exportsRepository.save(exports);
      });
    } else {
      throw new BusinessException("Invalid Exports ID to update file reference : "
          + fileDTO.getExportNoSeq1().orElse(null) + " / " + fileDTO.getExportNoSeq2().orElse(null));
    }
  }

  private Exports assignUpdatedValuesExports(Exports exports, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        exports.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName().get());
        break;
      case ZIP_FILE_COLLECTION:
        exports.getCommonGateInOut().setZipFileNo(fileDTO.getFileName().get());
        break;
      case SOLAS_CERTIFICATE_COLLECTION:
        exports.setSolasCertNo(fileDTO.getFileName().get());
        break;
      default:
        break;
    }
    return exports;
  }


}
