package com.privasia.scss.gatein.express.service;


import java.time.LocalDateTime;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateTicketInfoDTO;
import com.privasia.scss.common.dto.ODDContainerDetailsDTO;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.Nationality;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.repository.WHODDRepository;
import com.privasia.scss.gatein.odd.service.ODDGateInService;

/**
 * @author Nadeeshani Senevirathna
 *
 */
@Service("expressODDService")
public class ExpressODDService {

  private Logger log = Logger.getLogger(this.getClass());

  private ODDGateInService oddGateInService;

  private SystemUserRepository systemUserRepository;

  private WHODDRepository whoddRepository;

  private ModelMapper modelMapper;

  @Autowired
  public void setOddGateInService(ODDGateInService oddGateInService) {
    this.oddGateInService = oddGateInService;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setWhoddRepository(WHODDRepository whoddRepository) {
    this.whoddRepository = whoddRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false, value = "transactionManager")
  public GateTicketInfoDTO getGateTicketInfoODD(GateInWriteRequest gateInWriteRequest) {
    log.warn("getGateTicketInfo starting: gateInWriteRequest:-  " + gateInWriteRequest);

    if (gateInWriteRequest.getWhoddContainers() == null || gateInWriteRequest.getWhoddContainers().isEmpty()) {
      throw new BusinessException("ODD Containers are not available!");
    }

    // saving ODD info
    log.warn("saving gate in info starting: gateInWriteRequest:-  " + gateInWriteRequest);
    GateInResponse gateInResponse = oddGateInService.saveODDGateInInFo(gateInWriteRequest);
    log.warn("saving gate in info completed: gateInResponse :- " + gateInResponse);

    GateTicketInfoDTO gateTicketInfoDTO = printGatePassInfoODD(gateInWriteRequest, gateInResponse);
    log.warn("getGateTicketInfo ending: gateTicketInfoDTO:-  " + gateTicketInfoDTO); 
    return gateTicketInfoDTO;
  }

  public GateTicketInfoDTO printGatePassInfoODD(GateInWriteRequest gateInWriteRequest, GateInResponse gateInResponse) {
    GateTicketInfoDTO gateTicketInfoDTO = new GateTicketInfoDTO();

    Optional<WHODD> impWHODDOpt = whoddRepository.findWHODDInfo(gateInWriteRequest.getCardID(), ImpExpFlagStatus.IMPORT,
        TransactionStatus.INPROGRESS);

    Optional<WHODD> expWHODDOpt = whoddRepository.findWHODDInfo(gateInWriteRequest.getCardID(), ImpExpFlagStatus.EXPORT,
        TransactionStatus.INPROGRESS);

    gateTicketInfoDTO.setGateInLaneNo(gateInResponse.getLaneNo());

    gateTicketInfoDTO.setStatus("OK");

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

    gateTicketInfoDTO.setImportOrExport("ODD");

    gateTicketInfoDTO.setTruckHeadNo(gateInWriteRequest.getTruckHeadNo());
    gateTicketInfoDTO.setGateInDateTime(LocalDateTime.now());



    long impODDIdSeq = 0;
    long expODDIdSeq = 0;

    if (impWHODDOpt.isPresent()) {
      impODDIdSeq = impWHODDOpt.get().getOddIdSeq();

      if (!(impWHODDOpt.get().getContainer01() == null)) {
        ODDContainerDetailsDTO dto = new ODDContainerDetailsDTO();
        modelMapper.map(impWHODDOpt.get().getContainer01(), dto);
        gateTicketInfoDTO.setContainerPickup1(dto);
      }

      if (!(impWHODDOpt.get().getContainer02() == null)) {
        ODDContainerDetailsDTO dto = new ODDContainerDetailsDTO();
        modelMapper.map(impWHODDOpt.get().getContainer02(), dto);
        gateTicketInfoDTO.setContainerPickup2(dto);
      }

    }


    if (expWHODDOpt.isPresent()) {
      expODDIdSeq = expWHODDOpt.get().getOddIdSeq();

      if (!(expWHODDOpt.get().getContainer01() == null)) {
        ODDContainerDetailsDTO dto = new ODDContainerDetailsDTO();
        modelMapper.map(expWHODDOpt.get().getContainer01(), dto);
        gateTicketInfoDTO.setContainerDrop1(dto);
      }

      if (!(expWHODDOpt.get().getContainer02() == null)) {
        ODDContainerDetailsDTO dto = new ODDContainerDetailsDTO();
        modelMapper.map(expWHODDOpt.get().getContainer02(), dto);
        gateTicketInfoDTO.setContainerDrop2(dto);
      }
    }

    gateTicketInfoDTO.setCallCardNo("I" + impODDIdSeq + ",E" + expODDIdSeq);

    return gateTicketInfoDTO;
  }



}
