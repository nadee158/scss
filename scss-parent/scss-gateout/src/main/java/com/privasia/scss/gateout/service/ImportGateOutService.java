package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CancelPickUpDTO;
import com.privasia.scss.common.dto.CommonSealDTO;
import com.privasia.scss.common.dto.ConfirmedKioskDTO;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.UpdateSealDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CommonSealAttribute;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.cosmos.repository.CosmosImportRepository;
import com.privasia.scss.gateout.dto.FileDTO;

@Service("importGateOutService")
public class ImportGateOutService {

  private static final Log log = LogFactory.getLog(ImportGateOutService.class);

  private GatePassRepository gatePassRepository;

  private ModelMapper modelMapper;

  private ClientRepository clientRepository;

  private WDCGatePassRepository wdcGatePassRepository;

  private SystemUserRepository systemUserRepository;

  private CosmosImportRepository cosmosImportRepository;

  @Autowired
  public void setGatePassRepository(GatePassRepository gatePassRepository) {
    this.gatePassRepository = gatePassRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
    this.wdcGatePassRepository = wdcGatePassRepository;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setCosmosImportRepository(CosmosImportRepository cosmosImportRepository) {
    this.cosmosImportRepository = cosmosImportRepository;
  }



  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<ImportContainer> populateGateOut(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {

    Optional<List<GatePass>> optGatePassList = gatePassRepository.fetchInProgressTransaction(gateOutRequest.getCardID(),
        gateOutRequest.getComID(), TransactionStatus.INPROGRESS);
    List<GatePass> inprogressGatePassList = optGatePassList.orElseThrow(() -> new BusinessException(
        "No InProgress Import Transaction for the scan card ! " + gateOutRequest.getCardID()));
    List<ImportContainer> importContainerList = new ArrayList<ImportContainer>();
    inprogressGatePassList.forEach(gatePass -> {
      ImportContainer importContainer = new ImportContainer();
      modelMapper.map(gatePass, importContainer);

      if (gatePass.getBaseCommonGateInOutAttribute() != null) {
       
        if (!(gatePass.getBaseCommonGateInOutAttribute().getTimeGateIn() == null)) {
          LocalDateTime timeGateIn = gatePass.getBaseCommonGateInOutAttribute().getTimeGateIn();
          DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:mm");
          gateOutReponse.setGateInDateTime(timeGateIn.format(formatter));
        }
      }
      // adding log info
      if (gatePass.getContainerLength() != null) {
        importContainer.setContainerLength(Integer.parseInt(gatePass.getContainerLength().getValue()));
      }


      importContainer = getGCSDeclarationInfo(importContainer);
      importContainerList.add(importContainer);
      System.out.println("################# importContainer ######### " + importContainer);
      if (StringUtils.isEmpty(gateOutRequest.getImpContainer1())) {
        gateOutRequest.setImpContainer1(gatePass.getContainer().getContainerNumber());
      } else {
        gateOutRequest.setImpContainer2(gatePass.getContainer().getContainerNumber());
      }
      gateOutRequest.setTruckHeadNo(gatePass.getBaseCommonGateInOutAttribute().getPmHeadNo());
    });

    return importContainerList;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ImportContainer getGCSDeclarationInfo(ImportContainer importContainer) {

    Optional<WDCGatePass> optionalWdcGatePass = wdcGatePassRepository.findByGatePassNO(importContainer.getGatePassNo());

    WDCGatePass wdcGatePass = optionalWdcGatePass.orElseThrow(() -> new ResultsNotFoundException(
        "GCS Declaration could be found for the given Gate Pass Numbers! " + importContainer.getGatePassNo()));

    log.info("getGateOrder" + wdcGatePass.getGateOrder());
    log.info("getLineCode" + wdcGatePass.getGateOrder().getLineCode());
    log.info("GcsDelcarerNo" + wdcGatePass.getGcsDelcarerNo());

    importContainer.setVesselScn(wdcGatePass.getGateOrder().getVesselSCN());
    importContainer.setVesselName(wdcGatePass.getGateOrder().getVesselName());
    importContainer.setGcsDelcarerNo(wdcGatePass.getGcsDelcarerNo());
    importContainer.setContainerLength(wdcGatePass.getContainerLength().intValue());
    importContainer.setPkfzBlock(wdcGatePass.getPkfzBlock());
    importContainer.setLpkBlock(wdcGatePass.getLpkBlock());
    importContainer.setCusGCSReleaseDate(wdcGatePass.getCusGCSReleaseDate());
    importContainer.setPortSecurity(wdcGatePass.getDateTimeADD());
    importContainer.setGatePassIssued(wdcGatePass.getDateTimeADD());
    return importContainer;

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String cancelPickUp(CancelPickUpDTO cancelPickUpDTO) {

    List<Long> gatepassNumList = Arrays.asList(cancelPickUpDTO.getGatePass01(), cancelPickUpDTO.getGatePass02());
    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);

    List<GatePass> gatePassList =
        optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException("Invalid Gate pass numbers ! "
            + String.join(",", gatepassNumList.stream().map(Object::toString).collect(Collectors.toList()))));

    gatePassList.forEach(gatePass -> {
      gatePass.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.REJECT);
      if (gatePass.getGatePassNo() == cancelPickUpDTO.getGatePass01()) {
        gatePass.getCommonGateInOut().setRejectReason(cancelPickUpDTO.getRemarks01());
      } else {
        gatePass.getCommonGateInOut().setRejectReason(cancelPickUpDTO.getRemarks02());
      }
      gatePass.getCommonGateInOut().setKioskCancelPickUp(true);
      gatePassRepository.save(gatePass);
    });

    return "success";

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateSeal(UpdateSealDTO updateSealDTO) {

    List<Long> gatepassNumList = Arrays.asList(updateSealDTO.getGatePass01(), updateSealDTO.getGatePass02());
    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);

    List<GatePass> gatePassList =
        optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException("Invalid Gate pass numbers ! "
            + String.join(",", gatepassNumList.stream().map(Object::toString).collect(Collectors.toList()))));

    gatePassList.forEach(gatePass -> {
      if (gatePass.getSealAttribute() == null) {
        gatePass.setSealAttribute(new CommonSealAttribute());
      }
      if (gatePass.getGatePassNo() == updateSealDTO.getGatePass01()) {
        gatePass.getSealAttribute().setSeal01Number(updateSealDTO.getGatePass01Seal01().trim());
        gatePass.getSealAttribute().setSeal02Number(updateSealDTO.getGatePass01Seal02().trim());
      } else {
        gatePass.getSealAttribute().setSeal01Number(updateSealDTO.getGatePass02Seal01().trim());
        gatePass.getSealAttribute().setSeal02Number(updateSealDTO.getGatePass02Seal02().trim());
      }
      gatePass.setForcedSeal(updateSealDTO.isForceSealUpdate());
      gatePassRepository.save(gatePass);
    });

    return "success";

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String confirmedKioskTransaction(ConfirmedKioskDTO confirmedKioskDTO) {

    List<Long> gatepassNumList = Arrays.asList(confirmedKioskDTO.getGatePass01(), confirmedKioskDTO.getGatePass02());
    Optional<List<GatePass>> optionalGatePassList = gatePassRepository.findByGatePassNoIn(gatepassNumList);

    List<GatePass> gatePassList =
        optionalGatePassList.orElseThrow(() -> new ResultsNotFoundException("Invalid Gate pass numbers ! "
            + String.join(",", gatepassNumList.stream().map(Object::toString).collect(Collectors.toList()))));

    Optional<Client> clientOpt = clientRepository.findOne(confirmedKioskDTO.getKioskID());
    Client client = clientOpt
        .orElseThrow(() -> new ResultsNotFoundException("Invalid Kiosk ID ! " + confirmedKioskDTO.getKioskID()));
    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));

    gatePassList.forEach(gatePass -> {
      gatePass.getBaseCommonGateInOutAttribute().setGateOutClerk(systemUser);
      gatePass.getBaseCommonGateInOutAttribute().setGateOutClient(client);
      gatePass.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
      if (gatePass.getGatePassNo() == confirmedKioskDTO.getGatePass01()) {
        gatePass.getCommonGateInOut().setKioskConfirmed(confirmedKioskDTO.isStatus01());
      } else {
        gatePass.getCommonGateInOut().setKioskConfirmed(confirmedKioskDTO.isStatus02());
      }
      gatePassRepository.save(gatePass);
    });

    return "success";

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void ogaInternalBlockCheck(String containerNo) {

    boolean ogaBlock = cosmosImportRepository.isOGABlock(containerNo);
    boolean internalBlock = cosmosImportRepository.isInternalBlock(containerNo);

    if (ogaBlock && internalBlock) {
      throw new BusinessException("Container " + containerNo + " : Internal & OGA Block!");
    } else if (ogaBlock) {
      throw new BusinessException("Container " + containerNo + " : OGA Block!");
    } else if (internalBlock) {
      throw new BusinessException("Container " + containerNo + " : Internal Block!");
    }
  }

  // @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest, Client gateOutClient, SystemUser gateOutClerk,
      Client booth) {
    if (gateOutWriteRequest.getImportContainers() == null || gateOutWriteRequest.getImportContainers().isEmpty())
      throw new BusinessException("Invalid GateOutWriteRequest to save Imports ! ");
    List<ImportContainer> importContainers = gateOutWriteRequest.getImportContainers();
    importContainers.forEach(importContainer -> {
      if (StringUtils.isEmpty(importContainer.getBaseCommonGateInOutAttribute().getEirStatus()))
        throw new BusinessException("Invalid EIR Status for Gate Pass : " + importContainer.getGatePassNo());
      Optional<GatePass> optGatePass = gatePassRepository.findOne(importContainer.getGatePassNo());
      GatePass gatePass = optGatePass.orElseThrow(
          () -> new ResultsNotFoundException("Invalid GatePass to Update ! " + importContainer.getGatePassNo()));

      gatePass.getBaseCommonGateInOutAttribute()
          .setEirStatus(TransactionStatus.fromCode(importContainer.getBaseCommonGateInOutAttribute().getEirStatus()));
      gatePass.getBaseCommonGateInOutAttribute().setTimeGateOut(gateOutWriteRequest.getGateOUTDateTime());
      gatePass.getBaseCommonGateInOutAttribute().setTimeGateOutBooth(gateOutWriteRequest.getGateOUTDateTime());
      gatePass.getBaseCommonGateInOutAttribute().setTimeGateOutOk(LocalDateTime.now());
      gatePass.getBaseCommonGateInOutAttribute().setGateOutBoothClerk(gateOutClerk);
      gatePass.getBaseCommonGateInOutAttribute().setGateOutBoothNo(String.valueOf(booth.getClientID()));
      gatePass.getBaseCommonGateInOutAttribute().setGateOutClerk(gateOutClerk);
      gatePass.getBaseCommonGateInOutAttribute().setGateOutClient(gateOutClient);
      gatePass.getCommonGateInOut().setRejectReason(importContainer.getCommonGateInOut().getRejectReason());
      gatePass.setGateOutRemarks(importContainer.getGateOutRemarks());
      gatePass.setGateOutLaneNo(gateOutClient.getLaneNo());

      if (gatePass.getContainer().getContainerFullOrEmpty().getValue().equals(ContainerFullEmptyType.FULL.getValue())) {

        CommonSealAttribute sealAttribute = gatePass.getSealAttribute();
        if (sealAttribute == null)
          sealAttribute = new CommonSealAttribute();
        CommonSealDTO sealDTO = importContainer.getSealAttribute();
        if (sealDTO != null) {
          sealAttribute.setSeal01Number(sealDTO.getSeal01Number());
          sealAttribute.setSeal02Number(sealDTO.getSeal02Number());
          sealAttribute.setSeal01Origin(sealDTO.getSeal01Origin());
          sealAttribute.setSeal02Origin(sealDTO.getSeal02Origin());
          sealAttribute.setSeal01Type(sealDTO.getSeal01Type());
          sealAttribute.setSeal02Type(sealDTO.getSeal02Type());
          gatePass.setSealAttribute(sealAttribute);

          gatePass.setCosmosSeal01Number(importContainer.getCosmosSeal01Number());
          gatePass.setCosmosSeal02Number(importContainer.getCosmosSeal01Number());
          gatePass.setCosmosSeal01Origin(importContainer.getCosmosSeal01Origin());
          gatePass.setCosmosSeal02Origin(importContainer.getCosmosSeal02Origin());
          gatePass.setCosmosSeal01Type(importContainer.getCosmosSeal01Type());
          gatePass.setCosmosSeal02Type(importContainer.getCosmosSeal02Type());
          gatePass.setRetrievedCosmos(importContainer.isRetrievedCosmos());
          gatePass.setSealChange(gatePass.checkChangeSeal());
        }
      }

      gatePassRepository.save(gatePass);
    });

    // return new AsyncResult<Boolean>(true);
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void updateGatePassReference(FileDTO fileDTO) {

    Optional<List<GatePass>> gatePassOptList = gatePassRepository
        .findByGatePassNoIn(Arrays.asList(fileDTO.getGatePassNo1().orElse(null), fileDTO.getGatePassNo2().orElse(null)));

    if (gatePassOptList.isPresent() && ! gatePassOptList.get().isEmpty()) {
      gatePassOptList.get().forEach(gatePass -> {
        assignUpdatedValuesGatePass(gatePass, fileDTO);
        gatePassRepository.save(gatePass);
      });
    } else {
      throw new BusinessException("Invalid GatePass ID to update file reference : "
          + fileDTO.getGatePassNo1().orElse(null) + " / " + fileDTO.getGatePassNo2().orElse(null));
    }
  }

  private GatePass assignUpdatedValuesGatePass(GatePass gatePass, FileDTO fileDTO) {
    switch (fileDTO.getCollectionType()) {
      case PDF_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName().get());
        break;
      case ZIP_FILE_COLLECTION:
        gatePass.getCommonGateInOut().setZipFileNo(fileDTO.getFileName().get());
        break;
      default:
        break;
    }
    return gatePass;
  }

}
