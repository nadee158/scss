package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CancelPickUpDTO;
import com.privasia.scss.common.dto.ConfirmedKioskDTO;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.GatePassValidateDTO;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.UpdateSealDTO;
import com.privasia.scss.common.dto.WDCGateOrderDTO;
import com.privasia.scss.common.dto.WDCGatePassDTO;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
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

  public List<ImportContainer> populateGateOut(GateOutRequest gateOutRequest) {

    List<Long> gatePassNumberList = new ArrayList<Long>();
    if (gateOutRequest.getGatePass1() != null && gateOutRequest.getGatePass1() != 0) {
      gatePassNumberList.add(gateOutRequest.getGatePass1());
    }
    if (gateOutRequest.getGatePass2() != null && gateOutRequest.getGatePass2() != 0) {
      gatePassNumberList.add(gateOutRequest.getGatePass2());
    }
    /*
     * List<ImportContainer> importContainers = fetchContainerInfo(gatePassNumberList);
     * 
     * importContainers.forEach(importContainer -> { GatePassValidateDTO gatePassValidateDTO =
     * validateGatePass(gateOutRequest.getCardID(), gateOutRequest.getGatePass1(),
     * gateOutRequest.isCheckPreArrival(), gateOutRequest.getHpabSeqId(),
     * gateOutRequest.getTruckHeadNo()); if (gatePassValidateDTO.isValidGatePass()) { if
     * (importContainer.getGatePassNo() == gateOutRequest.getGatePass1()) {
     * gateOutRequest.setImpContainer1(importContainer.getContainer().getContainerNumber()); } else
     * { gateOutRequest.setImpContainer2(importContainer.getContainer().getContainerNumber()); } }
     * else { throw new ResultsNotFoundException( gatePassValidateDTO.getGatePassErrorMessage() +
     * gateOutRequest.getGatePass1()); } });
     */

    Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getLaneId());
    Client client =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getLaneId()));
    gateOutRequest.setLaneNo(client.getLaneNo());

    return null;

  }



  public GatePassValidateDTO validateGatePass(Long cardIdSeq, Long gatePassNo, boolean checkPreArrival,
      String hpatSeqId, String truckHeadNo) {

    return new GatePassValidateDTO();
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ImportContainer getGCSDeclarationInfo(ImportContainer importContainer, long gatePassNumber) {

    Optional<WDCGatePass> optionalWdcGatePass = wdcGatePassRepository.findByGatePassNO(gatePassNumber);

    WDCGatePass wdcGatePass = optionalWdcGatePass.orElseThrow(() -> new ResultsNotFoundException(
        "GCS Declaration could be found for the given Gate Pass Numbers! " + gatePassNumber));

    log.info("getGateOrder" + wdcGatePass.getGateOrder());
    log.info("getLineCode" + wdcGatePass.getGateOrder().getLineCode());
    log.info("GcsDelcarerNo" + wdcGatePass.getGcsDelcarerNo());

    WDCGatePassDTO wdcGatePassDTO = modelMapper.map(wdcGatePass, WDCGatePassDTO.class);
    WDCGateOrderDTO gateOrderDTO = modelMapper.map(wdcGatePass.getGateOrder(), WDCGateOrderDTO.class);
    wdcGatePassDTO.setGateOrderDTO(gateOrderDTO);
    wdcGatePassDTO.setPortSecurity(wdcGatePass.getDateTimeADD());
    wdcGatePassDTO.setGatePassIssued(wdcGatePass.getDateTimeADD());
    importContainer.setGcsDeclarationInfo(wdcGatePassDTO);
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

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void updateGateOutImport(List<ImportContainer> importContainers) {

    long clientID = importContainers.get(0).getBaseCommonGateInOutAttribute().getGateOutClient().getClientID();
    Optional<Client> clientOpt = clientRepository.findOne(clientID);
    Client gateOutClient =
        clientOpt.orElseThrow(() -> new ResultsNotFoundException("Invalid GateOutClient ID ! " + clientID));
    SystemUser gateOutClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));

    LocalDateTime timeGateOutOk = LocalDateTime.now();

    importContainers.forEach(importContainer -> {
      Optional<GatePass> gatePassOpt = gatePassRepository.findOne(importContainer.getGatePassID());
      GatePass gatePass = gatePassOpt
          .orElseThrow(() -> new ResultsNotFoundException("Invalid Gate pass  !" + importContainer.getGatePassID()));
      gatePass.getBaseCommonGateInOutAttribute().setGateOutClerk(gateOutClerk);
      gatePass.getBaseCommonGateInOutAttribute()
          .setEirStatus(TransactionStatus.valueOf(importContainer.getBaseCommonGateInOutAttribute().getEirStatus()));
      gatePass.getBaseCommonGateInOutAttribute()
          .setTimeGateOut(importContainer.getBaseCommonGateInOutAttribute().getTimeGateIn());
      gatePass.getBaseCommonGateInOutAttribute().setTimeGateOutOk(timeGateOutOk);

      gatePassRepository.save(gatePass);
    });
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


  public static void main(String args[]) {
    ImportGateOutService importGateOutService = new ImportGateOutService();
    List<Long> gatePassNumberList = new ArrayList<>();
    gatePassNumberList.add(20000070011l);
    gatePassNumberList.add(20000140011l);
    // importGateOutService.fetchContainerInfo(gatePassNumberList);
  }

  public List<ImportContainer> saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {
    throw new BusinessException("METHOD NOT IMPLEMENTED YET!");
  }

}
