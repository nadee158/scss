package com.privasia.scss.gatein.express.service;


import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateTicketInfoDTO;
import com.privasia.scss.common.enums.Nationality;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.gatein.imports.business.service.GatePassValidationService;
import com.privasia.scss.gatein.service.ImportExportGateInService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressGatePassService")
public class ExpressGatePassService {

  private Logger log = Logger.getLogger(this.getClass());

  private GatePassValidationService gatePassValidationService;

  private ImportExportGateInService importExportGateInService;

  private SystemUserRepository systemUserRepository;

  @Autowired
  public void setGatePassValidationService(GatePassValidationService gatePassValidationService) {
    this.gatePassValidationService = gatePassValidationService;
  }

  @Autowired
  public void setImportExportGateInService(ImportExportGateInService importExportGateInService) {
    this.importExportGateInService = importExportGateInService;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
  public GateTicketInfoDTO getGateTicketInfo(GateInRequest gateInRequest) {
    log.warn("getGateTicketInfo starting: gateInRequest:-  " + gateInRequest);
   
    if (gateInRequest.getGatePass1() == null && gateInRequest.getGatePass2() == null) 
    	throw new BusinessException("Gate Pass Information are not available!");
    
    //do the populate
    GateInResponse gateInResponse =  importExportGateInService.populateGateIn(gateInRequest);
    
    
    GateInWriteRequest gateInWriteRequest = new GateInWriteRequest();
    gateInWriteRequest.setImpExpFlag("I");
    gateInWriteRequest.setCardID(gateInRequest.getCardID());
    gateInWriteRequest.setGateInClient(gateInRequest.getClientID());
    gateInWriteRequest.setGateInStatus("A");
    gateInWriteRequest.setLaneNo(gateInResponse.getLaneNo());
    gateInWriteRequest.setTruckPlateNo(gateInResponse.getTruckPlateNo());
    gateInWriteRequest.setTruckHeadNo(gateInResponse.getTruckHeadNo());
    gateInWriteRequest.setHpatBookingId(gateInRequest.getHpabSeqId());
    gateInWriteRequest.setImportContainers(gateInResponse.getImportContainers());
    gateInWriteRequest.setGateInDateTime(gateInRequest.getGateInDateTime());
    gateInWriteRequest.setTrailerNo("AXBN8198");
    
    // saving gate in info
    log.warn("saving gate in info starting: gateInWriteRequest:-  " + gateInWriteRequest);
    gateInResponse = importExportGateInService.saveGateInInfo(gateInWriteRequest);
    log.warn("saving gate in info completed: gateInResponse :- " + gateInResponse);

    GateTicketInfoDTO gateTicketInfoDTO = printGatePassInfo(gateInWriteRequest, gateInResponse);
    log.warn("getGateTicketInfo ending: gateTicketInfoDTO:-  " + gateTicketInfoDTO);
    return gateTicketInfoDTO;
  }

  public GateTicketInfoDTO printGatePassInfo(GateInWriteRequest gateInWriteRequest, GateInResponse gateInResponse) {
    GateTicketInfoDTO gateTicketInfoDTO = new GateTicketInfoDTO();

    gateTicketInfoDTO.setStatus("OK");

    gateTicketInfoDTO.setCallCardNo(gateInResponse.getCallCardNo());

    gateTicketInfoDTO.setGateInLaneNo(gateInResponse.getLaneNo());

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    UserContext userContext = (UserContext) authentication.getPrincipal();
    long systemUserId = userContext.getUserID();

    Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(systemUserId);

    SystemUser systemUser = systemUserOpt.orElseThrow(() -> new BusinessException("System user could not be found!"));

    if (systemUser.getNationality() == null) {
      throw new BusinessException("System user's nationality could not be found!");
    }

    if (systemUser.getNationality().equals(Nationality.MALAYSIAN)) {
      if (StringUtils.isNotBlank(systemUser.getCommonContactAttribute().getNewNRICNO())) {
        gateTicketInfoDTO.setIcOrPassport(systemUser.getCommonContactAttribute().getNewNRICNO());
      } else {
        gateTicketInfoDTO.setIcOrPassport(systemUser.getCommonContactAttribute().getOldNRICNO());
      }
    } else {
      gateTicketInfoDTO.setIcOrPassport(systemUser.getPassportNo());
    }


    gateTicketInfoDTO.setImportOrExport("IMP");

    gateTicketInfoDTO.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
    gateTicketInfoDTO.setGateInDateTime(gateInResponse.getGateINDateTime());

    if (!(gateInResponse.getImportContainers() == null || gateInResponse.getImportContainers().isEmpty())) {
      gateInResponse.getImportContainers().forEach(importContainer -> {
        if (gateTicketInfoDTO.getContainer1() == null) {
          gateTicketInfoDTO.setContainer1(importContainer);
        } else {
          gateTicketInfoDTO.setContainer2(importContainer);
        }
      });
    }

    return gateTicketInfoDTO;
  }



}
