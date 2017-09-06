package com.privasia.scss.refer.service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.soap.Detail;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CardDTO;
import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ReferRejectDTO;
import com.privasia.scss.common.dto.ReferRejectDetailDTO;
import com.privasia.scss.common.dto.ReferRejectListDTO;
import com.privasia.scss.common.dto.ReferRejectReasonDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Company;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.PrintReject;
import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ReferRejectReason;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.model.SupervisorReferRejectReason;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.HPABBookingRepository;
import com.privasia.scss.core.repository.PrintRejectRepository;
import com.privasia.scss.core.repository.ReferReasonRepository;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.security.util.SecurityHelper;

/**
 * @author nadee158
 *
 */
@Service("referRejectService")
public class ReferRejectService {

  private ReferRejectDetailRepository referRejectDetailRepository;

  private ReferRejectRepository referRejectRepository;

  private ReferReasonRepository referReasonRepository;

  private CardRepository cardRepository;

  private SystemUserRepository systemUserRepository;

  private ClientRepository clientRepository;

  private PrintRejectRepository printRejectRepository;

  private ModelMapper modelMapper;

  private HPABBookingRepository hpabBookingRepository;

  @Autowired
  public void setReferRejectDetailRepository(ReferRejectDetailRepository referRejectDetailRepository) {
    this.referRejectDetailRepository = referRejectDetailRepository;
  }

  @Autowired
  public void setReferRejectRepository(ReferRejectRepository referRejectRepository) {
    this.referRejectRepository = referRejectRepository;
  }

  @Autowired
  public void setReferReasonRepository(ReferReasonRepository referReasonRepository) {
    this.referReasonRepository = referReasonRepository;
  }

  @Autowired
  public void setCardRepository(CardRepository cardRepository) {
    this.cardRepository = cardRepository;
  }

  @Autowired
  public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
    this.systemUserRepository = systemUserRepository;
  }

  @Autowired
  public void setClientRepository(ClientRepository clientRepository) {
    this.clientRepository = clientRepository;
  }

  @Autowired
  public void setPrintRejectRepository(PrintRejectRepository printRejectRepository) {
    this.printRejectRepository = printRejectRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setHpabBookingRepository(HPABBookingRepository hpabBookingRepository) {
    this.hpabBookingRepository = hpabBookingRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ModelMap getReferRejectList(ModelMap map, int page, int pageSize) {

    Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, "referDateTime");
    Optional<Page<ReferReject>> optReferRejectPages =
        referRejectRepository.findByStatusCode(HpabReferStatus.ACTIVE, pageRequest);

    if (optReferRejectPages.isPresent()) {
      long totalcount = optReferRejectPages.get().getContent().stream().count();
      List<ReferRejectListDTO> dtoList = optReferRejectPages.get().getContent().stream()
          .map(referReject -> constructReferRejectListDTO(referReject)).collect(Collectors.toList());

      map.put("totalcount", totalcount);
      map.put("referList", dtoList);

      return map;
    } else {
      throw new ResultsNotFoundException("No Active refer rejects were found!");
    }
  }

  private ReferRejectListDTO constructReferRejectListDTO(ReferReject referReject) {
	
	  
    ReferRejectListDTO listDTO = new ReferRejectListDTO();
    listDTO.setReferId(referReject.getReferRejectID());
    
    listDTO.setExpWeightBridge(referReject.getExpWeightBridge());
    
    if (referReject.getBaseCommonGateInOut() != null) {

      BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();
      listDTO.setPmHeadNo(baseCommonGateInOut.getPmHeadNo());
      
  	if(!(referReject.getBaseCommonGateInOut().getHpabBooking() == null)){
  		
  		HPABBooking booking  = referReject.getBaseCommonGateInOut().getHpabBooking();
  		
  		System.out.println("HPAB ID *************************** "+booking.getBookingID());
  		System.out.println("DETAIL SIZE *************************** "+booking.getHpabBookingDetails().size());
  		
  		booking.getHpabBookingDetails().forEach(detail-> {
  			
  			if(StringUtils.isNotEmpty(detail.getImpGatePassNumber())){
  				if(listDTO.getGatePass01() == null){
  	  	        	listDTO.setGatePass01(Long.parseLong(detail.getImpGatePassNumber()));
  	  	        }else{
  	  	        	listDTO.setGatePass02(Long.parseLong(detail.getImpGatePassNumber()));
  	  	        }
  			}
  		});
  	}
  	
     
      Client client = baseCommonGateInOut.getGateInClient();
      if (client != null) {
        listDTO.setBoothNo(client.getUnitNo());
      }

      Card card = baseCommonGateInOut.getCard();
      
      if (card != null) {
    	listDTO.setCardID(card.getCardID());
        Company company = card.getCompany();
        if (company != null) {
          listDTO.setHaulierCompany(company.getCompanyName());
        }

        SmartCardUser smartCardUser = card.getSmartCardUser();
        if (card != null) {
          listDTO.setDriverName(smartCardUser.getPersonName());
        }
      }
    }

    if (referReject.getReferDateTime() != null) {
      listDTO.setReferDateTime(referReject.getReferDateTime());
    }

    referReject.getReferRejectDetails().forEach(referRejectDetail -> {
    	
      if (StringUtils.isBlank(listDTO.getContNo01())) {
        listDTO.setContNo01(referRejectDetail.getContainerNo());
        
      } else {
        listDTO.setContNo02(referRejectDetail.getContainerNo());
        
      }

      if (referRejectDetail.getDoubleBooking() != null) {
        listDTO.setDoubleBooking(referRejectDetail.getDoubleBooking());
      }


    });
    return listDTO;
  }


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ReferRejectDTO getReferRejectByReferId(long referId) {
    ReferReject referReject = referRejectRepository.findOne(referId)
        .orElseThrow(() -> new ResultsNotFoundException("Refer reject was not found for ID : " + referId));

    ReferRejectDTO referRejectDTO = new ReferRejectDTO();
    modelMapper.map(referReject, referRejectDTO);

    Set<ReferRejectDetailDTO> referRejectDetailDTOList = new HashSet<ReferRejectDetailDTO>();
    referReject.getReferRejectDetails().stream().forEach(referRejectDetail -> {
      ReferRejectDetailDTO detailDTO = modelMapper.map(referRejectDetail, ReferRejectDetailDTO.class);

      Set<ReferRejectReasonDTO> referRejectReasonDTOList = referRejectDetail.getReferRejectReason().stream()
          .map(referRejectReason -> modelMapper.map(referRejectReason, ReferRejectReasonDTO.class))
          .collect(Collectors.toSet());

      detailDTO.setReferRejectReasons(referRejectReasonDTOList);
      referRejectDetailDTOList.add(detailDTO);
    });

    referRejectDTO.setReferRejectDetails(referRejectDetailDTOList);

    return referRejectDTO;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public ReferReject saveReferReject(GateInWriteRequest gateInWriteRequest) { 

    ReferRejectDTO referRejectDTO = gateInWriteRequest.getReferRejectDTO()
        .orElseThrow(() -> new BusinessException("No refer reject data is available to save"));

    if ((referRejectDTO.getReferRejectDetails() == null || referRejectDTO.getReferRejectDetails().isEmpty()))
      throw new BusinessException("No Refer Reject detail Data given to be updated!");

    if (gateInWriteRequest.getGateInDateTime() == null)
      throw new BusinessException("Gate In Time Required !");

    // manual conversion
    if (gateInWriteRequest.getWeightBridge() <= 0)
    	gateInWriteRequest.setWeightBridge(0); //pkfz....
      //throw new BusinessException("Incorrect Weight Bridge !");

    // update refer reject dto object from gateInWriteRequest
    if (referRejectDTO.getBaseCommonGateInOut() == null) {
      referRejectDTO.setBaseCommonGateInOut(new BaseCommonGateInOutDTO());
    }

    BaseCommonGateInOutDTO baseCommonGateInOut = referRejectDTO.getBaseCommonGateInOut();
    // baseCommonGateInOut.setCard(gateInWriteRequest.getCardId());
    baseCommonGateInOut.setTimeGateInOk(LocalDateTime.now());
    ClientDTO gateInClient = new ClientDTO();
    gateInClient.setClientID(gateInWriteRequest.getGateInClient());
    baseCommonGateInOut.setGateInClient(gateInClient);
    CardDTO cardDTO = new CardDTO();
    cardDTO.setCardID(gateInWriteRequest.getCardID());
    baseCommonGateInOut.setCard(cardDTO);
    baseCommonGateInOut.setTimeGateIn(gateInWriteRequest.getGateInDateTime());
    baseCommonGateInOut.setEirStatus(TransactionStatus.REJECT.getValue());
    baseCommonGateInOut.setHpabBooking(gateInWriteRequest.getHpatBookingId());
    baseCommonGateInOut.setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
    baseCommonGateInOut.setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
    
    referRejectDTO.setTrailerWeight(gateInWriteRequest.getTrailerWeight());
    referRejectDTO.setPmWeight(gateInWriteRequest.getTruckWeight());
    referRejectDTO.setTrailerPlateNo(gateInWriteRequest.getTrailerNo());
    referRejectDTO.setExpWeightBridge(gateInWriteRequest.getWeightBridge());

    Set<ReferRejectDetailDTO> referRejectDetailsDTOs = referRejectDTO.getReferRejectDetails();

    // bind details via modal map
    ReferReject referReject = modelMapper.map(referRejectDTO, ReferReject.class);

    referReject.setStatusCode(HpabReferStatus.ACTIVE);
    referReject.setReferDateTime(gateInWriteRequest.getGateInDateTime());


    Set<ReferRejectDetail> referRejectDetails = referRejectDetailsDTOs.stream()
        .map(referRejectDetailDTO -> modelMapper.map(referRejectDetailDTO, ReferRejectDetail.class))
        .collect(Collectors.toSet());

    referReject.setReferRejectDetails(referRejectDetails);

    HPABBooking hpabBooking = null;
    if (StringUtils.isNotEmpty(baseCommonGateInOut.getHpabBooking())) {
      hpabBooking = hpabBookingRepository.findOne(baseCommonGateInOut.getHpabBooking()).orElseThrow(
          () -> new ResultsNotFoundException("No HPAB Booking found ! : " + baseCommonGateInOut.getHpabBooking()));
    }

    // bind details manually from dby
    referReject = updateReferRejectReference(referRejectDTO, referReject, hpabBooking);

    final ReferReject referRejectF = referReject;

    referRejectDTO.getReferRejectDetails().stream().forEach(detailDTO -> {

      ReferRejectDetail referRejectDetail = null;
      referRejectDetail = referRejectF.getReferRejectDetails().stream()
          .filter(e -> (StringUtils.isNotEmpty(e.getContainerNo()))
              && (StringUtils.equalsIgnoreCase(e.getContainerNo(), detailDTO.getContainerNo())))
          .findFirst().orElse(referRejectDetail);
      referRejectDetail.setDoubleBooking(null);
      referRejectDetail.setGateInTime(gateInWriteRequest.getGateInDateTime());
      updateRejectDetailReference(detailDTO, referRejectDetail, referRejectF,
          referRejectF.getBaseCommonGateInOut().getGateInClerk());

    });

    return referRejectRepository.save(referRejectF);

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ReferReject updateReferRejectReference(ReferRejectDTO referRejectDTO, ReferReject referReject,
      HPABBooking hpabBooking) {

    // manual conversion
    BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();
    BaseCommonGateInOutDTO baseCommonGateInOutDTO = referRejectDTO.getBaseCommonGateInOut();

    if (baseCommonGateInOut == null) {
      baseCommonGateInOut = new BaseCommonGateInOutAttribute();
    }

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    baseCommonGateInOut.setGateInClerk(systemUser);

    Card card = cardRepository.findOne(baseCommonGateInOutDTO.getCard().getCardID())
        .orElseThrow(() -> new AuthenticationServiceException(
            "Scan Card was Not Found : " + referRejectDTO.getBaseCommonGateInOut().getCard()));
    baseCommonGateInOut.setCard(card);

    // fetch from clientID
    Client client = clientRepository.findOne(baseCommonGateInOutDTO.getGateInClient().getClientID())
        .orElseThrow(() -> new ResultsNotFoundException(
            "Gate In Client not found " + baseCommonGateInOutDTO.getGateInClient().getClientID()));

    baseCommonGateInOut.setGateInClient(client);

    baseCommonGateInOut.setHpabBooking(hpabBooking);

    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    // Need to set to ACTV
    referReject.setStatusCode(HpabReferStatus.ACTIVE);

    // from card take the company
    referReject.setCompany(card.getCompany());

    return referReject;
  }

  public ReferRejectDetail updateRejectDetailReference(ReferRejectDetailDTO referRejectDetailDTO,
      ReferRejectDetail referRejectDetail, ReferReject referReject, SystemUser systemUser) {

    // ReferRejectReason objects
    constructReferRejectReason(referRejectDetailDTO.getReferRejectReasons(), referRejectDetail);
    referRejectDetail.setReferBy(systemUser);
    referRejectDetail.setReferReject(referReject);

    return referRejectDetail;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void constructReferRejectReason(Set<ReferRejectReasonDTO> rejectReasonDTOs,
      ReferRejectDetail referRejectDetail) {
    if (!(rejectReasonDTOs == null || rejectReasonDTOs.isEmpty())) {
      if (referRejectDetail.getReferRejectReason() == null) {
        referRejectDetail.setReferRejectReason(new HashSet<ReferRejectReason>());
      }
      rejectReasonDTOs.forEach(referReasonDTO -> {
        ReferRejectReason reason = new ReferRejectReason();
        ReferReason referReason = referReasonRepository.findOne(referReasonDTO.getReferReason().getReferReasonID())
            .orElseThrow(() -> new ResultsNotFoundException(
                "Provided Refer Reson Not Found " + referReasonDTO.getReferReason().getReferReasonID()));
        reason.setReferReason(referReason);
        reason.setReferRejectDetail(referRejectDetail);
        referRejectDetail.getReferRejectReason().add(reason);
      });
    }

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateReferReject(ReferRejectDTO dto) {
    String status = "ERROR";
    ReferReject persisted = null;

    if ((dto == null || dto.getReferRejectDetails() == null || dto.getReferRejectDetails().isEmpty()))
      throw new BusinessException("No Refer data is available to update!");

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new ResultsNotFoundException("System User was not found! " + SecurityHelper.getCurrentUserId()));

    ReferReject referReject = referRejectRepository.findOne(dto.getReferRejectID()).orElseThrow(
        () -> new ResultsNotFoundException("Refer reject detail was not found! " + dto.getReferRejectID()));

    if ((referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty()))
      throw new ResultsNotFoundException("Refer reject detail was not found!");

    referReject.getReferRejectDetails().forEach(referRejectDetail -> {
      dto.getReferRejectDetails().forEach(referRejectDetailDTO -> {
        if (referRejectDetail.getReferRejectDetailID() == referRejectDetailDTO.getReferRejectDetailID()) {
          Set<SupervisorReferRejectReason> supervisorReferRejectReasons = new HashSet<SupervisorReferRejectReason>();
          // update detail
          referRejectDetail.setStatus(ReferStatus.fromValue(referRejectDetailDTO.getStatus()));
          referRejectDetail.setSupervisorRemarks(StringUtils.upperCase(referRejectDetailDTO.getSupervisorRemarks()));
          referRejectDetail.setRejectBy(systemUser);
          referRejectDetail.setContainerNo(StringUtils.upperCase(referRejectDetailDTO.getContainerNo()));

          if (!(referRejectDetailDTO.getReferRejectReasons() == null
              || referRejectDetailDTO.getReferRejectReasons().isEmpty())) {
            referRejectDetailDTO.getReferRejectReasons().forEach(referReasonDTO -> {
              ReferReason referReason =
                  referReasonRepository.findOne(referReasonDTO.getReferReason().getReferReasonID())
                      .orElseThrow(() -> new ResultsNotFoundException(
                          "Refer reason was not found! " + referReasonDTO.getReferReason().getReferReasonID()));
              SupervisorReferRejectReason supervisorreason = new SupervisorReferRejectReason();
              supervisorreason.setReferReason(referReason);
              supervisorreason.setReferRejectDetail(referRejectDetail);
              // supervisorReferRejectReasonList.add(reason);
            });
          }
          referRejectDetail.setSupervisorReferRejectReason(supervisorReferRejectReasons);
        }
      });

    });

    // ReferReject -> update as completed
    referReject.setStatusCode(HpabReferStatus.fromCode(dto.getStatusCode()));

    persisted = referRejectRepository.save(referReject);
    if (!(persisted == null || persisted.getReferRejectID() <= 0)) {
      if (persisted.getReferRejectID() == dto.getReferRejectID()) {
        status = "SUCCESS";
      }
    }

    // String sqlIns = "INSERT INTO SCSS_REJECT_SUP_REASON (" //
    /*
     * if (StringUtils.equals(status, "SUCCESS")) { if (!(supervisorReferRejectReasonList == null ||
     * supervisorReferRejectReasonList.isEmpty())) { for (SupervisorReferRejectReason reason :
     * supervisorReferRejectReasonList) { supervisorReferRejectReasonRepository.save(reason); } } }
     */

    return status;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateLineCodeAndGateInDateForReferRejectDetail(ReferRejectDetailDTO dto) {
   
    if (dto == null)
      throw new BusinessException("Refer reject details to update is not available!");

    ReferRejectDetail referRejectDetail = referRejectDetailRepository
        .findByReferReject_ReferRejectIDAndContainerNoAndReferReject_StatusCode(dto.getReferReject().getReferRejectID(),
            dto.getContainerNo(), HpabReferStatus.ACTIVE)
        .orElseThrow(() -> new ResultsNotFoundException("Refer reject detail was not found!"));

    referRejectDetail.setLineCode(StringUtils.upperCase(dto.getLineCode()));
    referRejectDetail.setGateInTime(dto.getGateInTime());
    referRejectDetail.setStatus(ReferStatus.REJECT_EXE);
    referRejectDetail.getReferReject().setStatusCode(HpabReferStatus.COMPLETE);
    referRejectRepository.save(referRejectDetail.getReferReject());
   
    return "SUCCESS";
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String savePrintReject(long referId, String ipAddress) {
    String status = "ERROR";
    ReferReject referReject = referRejectRepository.findOne(referId).orElse(null);
    if (!(referReject == null)) {
      if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty())) {
        Long systemUserId = SecurityHelper.getCurrentUserId();
        if (!(systemUserId == null || systemUserId == 0)) {
          PrintReject printReject = new PrintReject();
          printReject.setClientIP(ipAddress);
          if (referReject.getBaseCommonGateInOut().getCard() != null) {
            SmartCardUser cardUser = referReject.getBaseCommonGateInOut().getCard().getSmartCardUser();
            if (cardUser != null) {
              printReject.setDriverIC(cardUser.getPassportNo());
              printReject.setDriverName(cardUser.getPersonName());
            }
          }

          printReject.setPmHeadNo(referReject.getBaseCommonGateInOut().getPmHeadNo());
          printReject.setReferReject(referReject);
          printReject.setStaffName(SecurityHelper.getStaffName());
          printReject.setStaffNumber(SecurityHelper.getStaffNumber());
          printReject.setStatus(status);

          int i = 0;
          for (ReferRejectDetail referRejectDetail : referReject.getReferRejectDetails()) {
            if (i == 0) {
              // container 1
              printReject.setContainer01(referRejectDetail.getContainerNo());
              printReject.setContainer01Remarks(referRejectDetail.getSupervisorRemarks());
              printReject = setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason(), i);
            } else {
              // container 2
              printReject.setContainer02(referRejectDetail.getContainerNo());
              printReject.setContainer02Remarks(referRejectDetail.getSupervisorRemarks());
              printReject = setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason(), i);
            }
            i++;
          }

          PrintReject saved = printRejectRepository.save(printReject);
          if (!(saved == null || saved.getPrientRejectID() == 0)) {
            status = "SUCCESS";
          }

        }

      } else {
        throw new ResultsNotFoundException("System User detail was not found!");
      }
    } else {
      throw new ResultsNotFoundException("Refer reject detail was not found!");
    }
    return status;
  }

  private PrintReject setRejectReasonsForPrentReject(PrintReject printReject, Set<ReferRejectReason> referRejectReasons,
      int i) {

    String reason1C1 = "";
    String reason2C1 = "";
    String reason3C1 = "";

    if (!(referRejectReasons == null || referRejectReasons.isEmpty())) {
      int count = 0;
      for (ReferRejectReason referRejectReason : referRejectReasons) {
        if (!(referRejectReason.getReferReason() == null)) {
          String reason = referRejectReason.getReferReason().getReasonDescription();
          if (count == 1) {
            reason1C1 = "1) " + reason;
          } else if (count == 2) {
            reason2C1 = "2) " + reason;
          } else {
            if (StringUtils.isBlank(reason3C1)) {
              reason3C1 = count + ")" + reason;
            } else {
              reason3C1 = reason3C1 + "," + count + ")" + reason;
            }
          }
        }

        count++;
      }
      if (i == 0) {
        printReject.setRejectContainer01Due01(reason1C1);
        printReject.setRejectContainer01Due02(reason2C1);
        printReject.setRejectContainer01Due03(reason3C1);
      } else {
        printReject.setRejectContainer02Due01(reason1C1);
        printReject.setRejectContainer02Due02(reason2C1);
        printReject.setRejectContainer02Due03(reason3C1);
      }
    }
    return printReject;
  }

}
