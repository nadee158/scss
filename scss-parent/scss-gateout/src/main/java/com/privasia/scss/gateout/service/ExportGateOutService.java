package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.gateout.dto.FileDTO;

@Service("exportGateOutService")
public class ExportGateOutService {

  private ModelMapper modelMapper;

  private ClientRepository clientRepository;

  private ShipCodeRepository shipCodeRepository;

  private ShipSCNRepository shipSCNRepository;

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
  public void setShipSCNRepository(ShipSCNRepository shipSCNRepository) {
    this.shipSCNRepository = shipSCNRepository;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
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
        // exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
        /*
         * if (export.getBaseCommonGateInOutAttribute().getCard() != null) {
         * System.out.println("################## exports : "+export.toString());
         * exportContainer.getBaseCommonGateInOutAttribute()
         * .getCard().setCardID(export.getBaseCommonGateInOutAttribute().getCard().getCardID()); }
         */
        /*
         * if (export.getBaseCommonGateInOutAttribute().getEirStatus() != null) {
         * exportContainer.getBaseCommonGateInOutAttribute()
         * .setEirStatus(export.getBaseCommonGateInOutAttribute().getEirStatus().getValue()); }
         */

        // changed gateout cleark to gateout clierk
        /*
         * if (export.getBaseCommonGateInOutAttribute().getGateInClerk() != null) { SystemUserDTO
         * gateInClerk = new SystemUserDTO();
         * modelMapper.map(export.getBaseCommonGateInOutAttribute().getGateInClerk(), gateInClerk);
         * if
         * (!(export.getBaseCommonGateInOutAttribute().getGateInClerk().getCommonContactAttribute()
         * == null)) { gateOutReponse.setClerkName(
         * export.getBaseCommonGateInOutAttribute().getGateInClerk().getCommonContactAttribute().
         * getPersonName()); }
         * exportContainer.getBaseCommonGateInOutAttribute().setGateInClerk(gateInClerk); }
         */

        if (!(export.getBaseCommonGateInOutAttribute().getTimeGateIn() == null)) {
          LocalDateTime timeGateIn = export.getBaseCommonGateInOutAttribute().getTimeGateIn();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
          gateOutReponse.setGateInDateTime(timeGateIn.format(formatter));
        }

        // changed gateout client to gatein client
        /*
         * if (export.getBaseCommonGateInOutAttribute().getGateInClient() != null) { ClientDTO
         * gateInClient = new ClientDTO();
         * modelMapper.map(export.getBaseCommonGateInOutAttribute().getGateInClient(),
         * gateInClient);
         * gateOutReponse.setGateInLaneNo(export.getBaseCommonGateInOutAttribute().getGateInClient()
         * .getLaneNo());
         * exportContainer.getBaseCommonGateInOutAttribute().setGateInClient(gateInClient); }
         * 
         * if (export.getBaseCommonGateInOutAttribute().getHpabBooking()!=null) {
         * exportContainer.getBaseCommonGateInOutAttribute()
         * .setHpabBooking(export.getBaseCommonGateInOutAttribute().getHpabBooking().getBookingID())
         * ; }
         * 
         * exportContainer.getBaseCommonGateInOutAttribute()
         * .setPmHeadNo(export.getBaseCommonGateInOutAttribute().getPmHeadNo());
         * exportContainer.getBaseCommonGateInOutAttribute()
         * .setPmPlateNo(export.getBaseCommonGateInOutAttribute().getPmHeadNo());
         */

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

  public void updateExportReference(FileDTO fileDTO) {

    Optional<List<Exports>> exportsOptList = exportsRepository
        .findByExportIDIn(Arrays.asList(fileDTO.getExportNoSeq1().orElse(0l), fileDTO.getExportNoSeq2().orElse(0l)));

    if (!(exportsOptList.orElse(null) == null || exportsOptList.get().isEmpty())) {
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
