package com.privasia.scss.refer.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
import com.privasia.scss.common.dto.ClientDTO;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ReferRejectDTO;
import com.privasia.scss.common.dto.ReferRejectDetailDTO;
import com.privasia.scss.common.dto.ReferRejectListDTO;
import com.privasia.scss.common.dto.ReferRejectReasonDTO;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
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
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.repository.PrintRejectRepository;
import com.privasia.scss.core.repository.ReferReasonRepository;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.SupervisorReferRejectReasonRepository;
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

  private SupervisorReferRejectReasonRepository supervisorReferRejectReasonRepository;

  private PrintRejectRepository printRejectRepository;

  private ModelMapper modelMapper;

  private HPATBookingRepository hpatBookingRepository;

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
  public void setSupervisorReferRejectReasonRepository(
      SupervisorReferRejectReasonRepository supervisorReferRejectReasonRepository) {
    this.supervisorReferRejectReasonRepository = supervisorReferRejectReasonRepository;
  }

  @Autowired
  public void setPrintRejectRepository(PrintRejectRepository printRejectRepository) {
    this.printRejectRepository = printRejectRepository;
  }

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  public void setHpatBookingRepository(HPATBookingRepository hpatBookingRepository) {
    this.hpatBookingRepository = hpatBookingRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ModelMap getReferRejectList(ModelMap map, int page, int pageSize) {

    Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, "referDateTime");
    Page<ReferReject> referRejectPages = referRejectRepository.findByStatusCode(HpatReferStatus.ACTIVE, pageRequest);

    long totalcount = referRejectPages.getTotalElements();

    if (referRejectPages != null && !referRejectPages.getContent().isEmpty()) {
      List<ReferRejectListDTO> dtoList = new ArrayList<ReferRejectListDTO>();
      referRejectPages.getContent().forEach(referReject -> {
        dtoList.add(referReject.constructReferRejectListDTO());
      });
      map.put("totalcount", totalcount);
      map.put("referList", dtoList);
      return map;
    } else {
      throw new ResultsNotFoundException("No refer rejects were found!");
    }
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ReferRejectDTO getReferRejectByReferId(long referId) {
    ReferReject referReject = referRejectRepository.findOne(referId)
        .orElseThrow(() -> new ResultsNotFoundException("Refer reject was not found for ID : " + referId));
    ReferRejectDTO referRejectDTO = new ReferRejectDTO();
    modelMapper.map(referReject, referRejectDTO);
    return referRejectDTO;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public ReferReject saveReferReject(GateInWriteRequest gateInWriteRequest) {

    ReferRejectDTO referRejectDTO = gateInWriteRequest.getReferRejectDTO()
        .orElseThrow(() -> new BusinessException("No refer reject data is available to save"));

    if ((!referRejectDTO.getReferRejectDetails().isPresent() || referRejectDTO.getReferRejectDetails().get().isEmpty()))
      throw new BusinessException("No Refer Reject detail Data given to be updated!");

    if (gateInWriteRequest.getGateInDateTime() == null)
      throw new BusinessException("Gate In Time Required !");

    // manual conversion
    if (gateInWriteRequest.getWeightBridge() <= 0)
      throw new BusinessException("Incorrect Weight Bridge !");

    // update refer reject dto object from gateInWriteRequest
    if (!referRejectDTO.getBaseCommonGateInOut().isPresent()) {
      referRejectDTO.setBaseCommonGateInOut(new BaseCommonGateInOutDTO());
    }

    BaseCommonGateInOutDTO baseCommonGateInOut = referRejectDTO.getBaseCommonGateInOut().get();
    baseCommonGateInOut.setCard(gateInWriteRequest.getCardId());
    baseCommonGateInOut.setTimeGateInOk(LocalDateTime.now());
    ClientDTO gateInClient = new ClientDTO();
    gateInClient.setClientID(gateInWriteRequest.getGateInClient());
    baseCommonGateInOut.setGateInClient(gateInClient);
    baseCommonGateInOut.setTimeGateIn(gateInWriteRequest.getGateInDateTime());
    baseCommonGateInOut.setEirStatus(TransactionStatus.REJECT.getValue());
    baseCommonGateInOut.setHpatBooking(gateInWriteRequest.getHpatBookingId());
    baseCommonGateInOut.setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
    baseCommonGateInOut.setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
    baseCommonGateInOut.setTimeGateInOk(LocalDateTime.now());
    referRejectDTO.setTrailerWeight(gateInWriteRequest.getTrailerWeight());
    referRejectDTO.setPmWeight(gateInWriteRequest.getTruckWeight());
    referRejectDTO.setTrailerPlateNo(gateInWriteRequest.getTrailerNo());
    referRejectDTO.setExpWeightBridge(gateInWriteRequest.getWeightBridge());


    Set<ReferRejectDetailDTO> referRejectDetailsDTOs = referRejectDTO.getReferRejectDetails().get();
    System.out.println("referRejectDetailsDTOs " + referRejectDetailsDTOs);

    // bind details via modal map
    ReferReject referReject = modelMapper.map(referRejectDTO, ReferReject.class);

    Set<ReferRejectDetail> referRejectDetails = referReject.getReferRejectDetails().get();
    System.out.println("referRejectDetails " + referRejectDetails);


    // Set<ReferRejectDetail> referRejectDetails = new HashSet<ReferRejectDetail>();
    // referRejectDetailsDTOs.forEach(referRejectDetailDTO -> {
    // referRejectDetails.add(modelMapper.map(referRejectDetailDTO, ReferRejectDetail.class));
    // });
    // referReject.setReferRejectDetails(referRejectDetails);

    boolean doubleBoooking = referReject.getReferRejectDetails().get().size() == 2 ? true : false;

    HPABBooking hpabBooking = null;
    if (baseCommonGateInOut.getHpatBooking().isPresent()) {
      hpabBooking = hpatBookingRepository.findOne(baseCommonGateInOut.getHpatBooking().get())
          .orElseThrow(() -> new ResultsNotFoundException(
              "No HPAB Booking found ! : " + baseCommonGateInOut.getHpatBooking().get()));
    }

    // bind details manually from dby
    referReject = updateReferRejectReference(referRejectDTO, referReject, hpabBooking);

    final ReferReject referRejectF = referReject;

    referReject.getReferRejectDetails().get().forEach(referRejectDetail -> {

      ReferRejectDetailDTO referRejectDetailDTO = null;
      referRejectDetailDTO = referRejectDTO.getReferRejectDetails().get().stream()
          .filter(e -> (e.getContainerNo() != null)
              && (StringUtils.equalsIgnoreCase(e.getContainerNo(), referRejectDetail.getContainerNo())))
          .findFirst().get();
      referRejectDetail.setDoubleBooking(doubleBoooking);
      updateRejectDetailReference(referRejectDetailDTO, referRejectDetail, referRejectF,
          referRejectF.getBaseCommonGateInOut().get().getGateInClerk());

    });

    return referRejectRepository.save(referRejectF);

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public ReferReject updateReferRejectReference(ReferRejectDTO referRejectDTO, ReferReject referReject,
      HPABBooking hpabBooking) {

    // manual conversion
    BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut().get();
    BaseCommonGateInOutDTO baseCommonGateInOutDTO = referRejectDTO.getBaseCommonGateInOut().get();

    if (baseCommonGateInOut == null) {
      baseCommonGateInOut = new BaseCommonGateInOutAttribute();
      /*
       * baseCommonGateInOut.setTimeGateIn(referRejectDTO.
       * getBaseCommonGateInOut().getTimeGateIn());
       * baseCommonGateInOut.setTimeGateInOk(referRejectDTO.
       * getBaseCommonGateInOut().getTimeGateInOk());
       */ // already added ne
    }

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    baseCommonGateInOut.setGateInClerk(systemUser);

    Card card =
        cardRepository.findOne(baseCommonGateInOutDTO.getCard()).orElseThrow(() -> new AuthenticationServiceException(
            "Scan Card was Not Found : " + referRejectDTO.getBaseCommonGateInOut().get().getCard()));
    baseCommonGateInOut.setCard(card);

    // set EIR STATUS to R
    // baseCommonGateInOut.setEirStatus(TransactionStatus.REJECT);

    // fetch from clientID
    Client client = clientRepository.findOne(baseCommonGateInOutDTO.getGateInClient().getClientID())
        .orElseThrow(() -> new ResultsNotFoundException(
            "Gate In Client not found " + baseCommonGateInOutDTO.getGateInClient().getClientID()));

    baseCommonGateInOut.setGateInClient(client);

    baseCommonGateInOut.setHpatBooking(hpabBooking);

    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    // Need to set to ACTV
    referReject.setStatusCode(HpatReferStatus.ACTIVE);

    // from card take the company
    referReject.setCompany(card.getCompany());

    return referReject;
  }

  public ReferRejectDetail updateRejectDetailReference(ReferRejectDetailDTO referRejectDetailDTO,
      ReferRejectDetail referRejectDetail, ReferReject referReject, SystemUser systemUser) {

    // ReferRejectReason objects
    constructReferRejectReason(referRejectDetailDTO.getReferRejectReasons().get(), referRejectDetail);
    referRejectDetail.setReferBy(systemUser);
    referRejectDetail.setReferReject(referReject);

    return referRejectDetail;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void constructReferRejectReason(Set<ReferRejectReasonDTO> rejectReasonDTOs,
      ReferRejectDetail referRejectDetail) {
    if (!(rejectReasonDTOs == null || rejectReasonDTOs.isEmpty())) {
      if (!referRejectDetail.getReferRejectReason().isPresent()) {
        referRejectDetail.setReferRejectReason(new HashSet<ReferRejectReason>());
      }
      rejectReasonDTOs.forEach(referReasonDTO -> {
        ReferRejectReason reason = new ReferRejectReason();
        ReferReason referReason = referReasonRepository.findOne(referReasonDTO.getReferReasonID()).orElseThrow(
            () -> new ResultsNotFoundException("Provided Refer Reson Not Found " + referReasonDTO.getReferReasonID()));
        reason.setReferReason(referReason);
        reason.setReferRejectDetail(referRejectDetail);
        referRejectDetail.getReferRejectReason().get().add(reason);
      });
    }

  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateReferReject(ReferRejectDTO dto) {
    String status = "ERROR";
    ReferReject persisted = null;
    List<SupervisorReferRejectReason> supervisorReferRejectReasonList = new ArrayList<SupervisorReferRejectReason>();
    if ((dto == null || !dto.getReferRejectDetails().isPresent() || dto.getReferRejectDetails().get().isEmpty()))
      throw new BusinessException("No data is available to update!");

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new ResultsNotFoundException("System User was not found! " + SecurityHelper.getCurrentUserId()));

    ReferReject referReject = referRejectRepository.findOne(dto.getReferRejectID()).orElseThrow(
        () -> new ResultsNotFoundException("Refer reject detail was not found! " + dto.getReferRejectID()));

    if (!(referReject.getReferRejectDetails().isPresent() || referReject.getReferRejectDetails().get().isEmpty()))
      throw new ResultsNotFoundException("Refer reject detail was not found!");

    referReject.getReferRejectDetails().get().forEach(referRejectDetail -> {
      dto.getReferRejectDetails().get().forEach(referRejectDetailDTO -> {
        if (referRejectDetail.getReferRejectDetailID() == referRejectDetailDTO.getReferRejectDetailID()) {
          // update detail
          referRejectDetail.setStatus(ReferStatus.fromValue(referRejectDetailDTO.getStatus()));
          referRejectDetail.setSupervisorRemarks(StringUtils.upperCase(referRejectDetailDTO.getSupervisorRemarks()));
          referRejectDetail.setRejectBy(systemUser);
          referRejectDetail.setContainerNo(StringUtils.upperCase(referRejectDetailDTO.getContainerNo()));

          if (!(referRejectDetailDTO.getReferRejectReasons().isPresent()
              || referRejectDetailDTO.getReferRejectReasons().get().isEmpty())) {
            referRejectDetailDTO.getReferRejectReasons().get().forEach(referReasonDTO -> {
              ReferReason referReason = referReasonRepository.findOne(referReasonDTO.getReferReasonID())
                  .orElseThrow(() -> new ResultsNotFoundException(
                      "Refer reason was not found! " + referReasonDTO.getReferReasonID()));
              SupervisorReferRejectReason reason = new SupervisorReferRejectReason();
              reason.setReferReason(referReason);
              reason.setReferRejectDetail(referRejectDetail);
              supervisorReferRejectReasonList.add(reason);
            });
          }
        }
      });
    });

    // ReferReject -> update as completed
    referReject.setStatusCode(HpatReferStatus.fromCode(dto.getStatusCode()));

    persisted = referRejectRepository.save(referReject);
    if (!(persisted == null || persisted.getReferRejectID() <= 0)) {
      if (persisted.getReferRejectID() == dto.getReferRejectID()) {
        status = "SUCCESS";
      }
    }

    // String sqlIns = "INSERT INTO SCSS_REJECT_SUP_REASON (" //
    if (StringUtils.equals(status, "SUCCESS")) {
      if (!(supervisorReferRejectReasonList == null || supervisorReferRejectReasonList.isEmpty())) {
        for (SupervisorReferRejectReason reason : supervisorReferRejectReasonList) {
          supervisorReferRejectReasonRepository.save(reason);
        }
      }
    }

    return status;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String updateLineCodeAndGateInDateForReferRejectDetail(ReferRejectDetailDTO dto) {
    String status = "ERROR";
    if (dto == null)
      throw new BusinessException("Refer reject details to update is not available!");

    ReferRejectDetail referRejectDetail = referRejectDetailRepository
        .findByReferReject_ReferRejectIDAndContainerNo(dto.getReferRejectID(), dto.getContainerNo())
        .orElseThrow(() -> new ResultsNotFoundException("Refer reject detail was not found!"));

    referRejectDetail.setLineCode(StringUtils.upperCase(dto.getLineCode()));
    referRejectDetail.setGateInTime(dto.getGateInTime());
    ReferRejectDetail persisted = referRejectDetailRepository.save(referRejectDetail);
    if (!(persisted == null || persisted.getReferRejectDetailID() <= 0)) {
      if (persisted.getReferReject().getReferRejectID() == dto.getReferRejectID()) {
        status = "SUCCESS";
      }
    }

    return status;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public String savePrintReject(long referId, String ipAddress) {
    System.out.println("referId :" + referId);
    String status = "ERROR";
    ReferReject referReject = referRejectRepository.findOne(referId).orElse(null);
    if (!(referReject == null)) {
      if (!(referReject.getReferRejectDetails().isPresent() || referReject.getReferRejectDetails().get().isEmpty())) {
        Long systemUserId = SecurityHelper.getCurrentUserId();
        if (!(systemUserId == null || systemUserId == 0)) {
          PrintReject printReject = new PrintReject();
          printReject.setClientIP(ipAddress);
          if (referReject.getBaseCommonGateInOut().get().getCard() != null) {
            SmartCardUser cardUser = referReject.getBaseCommonGateInOut().get().getCard().getSmartCardUser();
            if (cardUser != null) {
              printReject.setDriverIC(cardUser.getPassportNo());
              printReject.setDriverName(cardUser.getPersonName());
            }
          }

          printReject.setPmHeadNo(referReject.getBaseCommonGateInOut().get().getPmHeadNo());
          printReject.setReferReject(referReject);
          printReject.setStaffName(SecurityHelper.getStaffName());
          printReject.setStaffNumber(SecurityHelper.getStaffNumber());
          printReject.setStatus(status);

          int i = 0;
          for (ReferRejectDetail referRejectDetail : referReject.getReferRejectDetails().get()) {
            if (i == 0) {
              // container 1
              printReject.setContainer01(referRejectDetail.getContainerNo());
              printReject.setContainer01Remarks(referRejectDetail.getSupervisorRemarks());
              printReject =
                  setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason().get(), i);
            } else {
              // container 2
              printReject.setContainer02(referRejectDetail.getContainerNo());
              printReject.setContainer02Remarks(referRejectDetail.getSupervisorRemarks());
              printReject =
                  setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason().get(), i);
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
