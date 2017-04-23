package com.privasia.scss.refer.service;

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

import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ReferRejectDTO;
import com.privasia.scss.common.dto.ReferRejectDetailDTO;
import com.privasia.scss.common.dto.ReferRejectListDTO;
import com.privasia.scss.common.dto.ReferRejectReasonDTO;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
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

  @Autowired
  private ReferRejectDetailRepository referRejectDetailRepository;

  @Autowired
  private ReferRejectRepository referRejectRepository;

  @Autowired
  private ReferReasonRepository referReasonRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private SystemUserRepository systemUserRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private SupervisorReferRejectReasonRepository supervisorReferRejectReasonRepository;

  @Autowired
  private PrintRejectRepository printRejectRepository;

  @Autowired
  private ModelMapper modelMapper;

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
  public Long saveReferReject(GateInWriteRequest gateInWriteRequest) {

    ReferRejectDTO referRejectDTO = gateInWriteRequest.getReferRejectDTO()
        .orElseThrow(() -> new BusinessException("No refer reject data is available to save"));

    if ((referRejectDTO.getReferRejectDetails() == null || referRejectDTO.getReferRejectDetails().isEmpty()))
      throw new BusinessException("No Refer Reject detail Data given to be updated!");

    ReferReject referReject = new ReferReject();

    // bind details via modal map
    modelMapper.map(referRejectDTO, referReject);

    // bind details manually from dby
    referReject = manualUpdateToReferReject(referRejectDTO, referReject);

    ReferReject persisted = referRejectRepository.save(referReject);

    if (persisted != null && persisted.getReferRejectID() > 0) {
      return persisted.getReferRejectID();
    }

    throw new BusinessException("Could not save the refer reject details! Internal server error!");

  }

  public ReferReject manualUpdateToReferReject(ReferRejectDTO referRejectDTO, ReferReject referReject) {
    // manual conversion
    if (referRejectDTO.getExpWeightBridge() == 0)
      throw new BusinessException("Incorrect Weight Bridge !");

    // manual conversion
    BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();
    if (baseCommonGateInOut == null) {
      baseCommonGateInOut = new BaseCommonGateInOutAttribute();
      baseCommonGateInOut.setTimeGateIn(referRejectDTO.getBaseCommonGateInOut().getTimeGateIn());
      baseCommonGateInOut.setTimeGateInOk(referRejectDTO.getBaseCommonGateInOut().getTimeGateInOk());
    }

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    baseCommonGateInOut.setGateInClerk(systemUser);

    Card card = cardRepository.findOne(referRejectDTO.getBaseCommonGateInOut().getCard())
        .orElseThrow(() -> new AuthenticationServiceException(
            "Scan Card was Not Found : " + referRejectDTO.getBaseCommonGateInOut().getCard()));
    baseCommonGateInOut.setCard(card);

    // set EIR STATUS to R
    baseCommonGateInOut.setEirStatus(TransactionStatus.REJECT);

    // fetch from clientID
    Client client = clientRepository.findOne(referRejectDTO.getBaseCommonGateInOut().getGateInClient().getClientID())
        .orElseThrow(() -> new BusinessException(
            "Gate In Client not found " + referRejectDTO.getBaseCommonGateInOut().getGateInClient().getClientID()));

    baseCommonGateInOut.setGateInClient(client);


    // gate in time should capture from client
    // need to clarify from feroz this. here we need to save this time
    if (referRejectDTO.getBaseCommonGateInOut().getTimeGateIn() == null)
      throw new BusinessException("Gate In Time Required !");


    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    // Need to set to ACTV
    referReject.setStatusCode(HpatReferStatus.ACTIVE);

    // from card take the company
    referReject.setCompany(card.getCompany());

    final ReferReject referRejectF = referReject;

    referReject.getReferRejectDetails().forEach(referRejectDetail -> {
      referRejectDTO.getReferRejectDetails().forEach(referRejectDetailDTO -> {
        if (StringUtils.equals(referRejectDetail.getContainerNo(), referRejectDetailDTO.getContainerNo())) {
          manualUpdateToRejectDetail(referRejectDetailDTO, referRejectDetail);
          referRejectDetail.setReferBy(systemUser);
          referRejectDetail.setReferReject(referRejectF);
        }
      });
    });
    return referRejectF;
  }

  public ReferRejectDetail manualUpdateToRejectDetail(ReferRejectDetailDTO referRejectDetailDTO,
      ReferRejectDetail referRejectDetail) {

    // ReferRejectReason objects
    constructReferRejectReasonList(referRejectDetailDTO.getReferRejectReasons(), referRejectDetail);

    return referRejectDetail;
  }

  public void constructReferRejectReasonList(Set<ReferRejectReasonDTO> rejectReasonDTOs,
      ReferRejectDetail referRejectDetail) {
    if (rejectReasonDTOs != null && !rejectReasonDTOs.isEmpty()) {
      if (referRejectDetail.getReferRejectReason() == null) {
        referRejectDetail.setReferRejectReason(new HashSet<ReferRejectReason>());
      }
      rejectReasonDTOs.forEach(referReasonDTO -> {
        ReferRejectReason reason = new ReferRejectReason();
        ReferReason referReason = referReasonRepository.findOne(referReasonDTO.getReferReasonID()).orElseThrow(
            () -> new BusinessException("Provided Refer Reson Not Found " + referReasonDTO.getReferReasonID()));
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
    List<SupervisorReferRejectReason> supervisorReferRejectReasonList = new ArrayList<SupervisorReferRejectReason>();
    if ((dto == null || dto.getReferRejectDetails() == null || dto.getReferRejectDetails().isEmpty()))
      throw new BusinessException("No data is available to update!");

    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
        () -> new ResultsNotFoundException("System User was not found! " + SecurityHelper.getCurrentUserId()));

    ReferReject referReject = referRejectRepository.findOne(dto.getReferRejectID()).orElseThrow(
        () -> new ResultsNotFoundException("Refer reject detail was not found! " + dto.getReferRejectID()));

    if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty()))
      throw new ResultsNotFoundException("Refer reject detail was not found!");

    referReject.getReferRejectDetails().forEach(referRejectDetail -> {
      dto.getReferRejectDetails().forEach(referRejectDetailDTO -> {
        if (referRejectDetail.getReferRejectDetailID() == referRejectDetailDTO.getReferRejectDetailID()) {
          // update detail
          referRejectDetail.setStatus(ReferStatus.fromValue(referRejectDetailDTO.getStatus()));
          referRejectDetail.setSupervisorRemarks(StringUtils.upperCase(referRejectDetailDTO.getSupervisorRemarks()));
          referRejectDetail.setRejectBy(systemUser);
          referRejectDetail.setContainerNo(StringUtils.upperCase(referRejectDetailDTO.getContainerNo()));

          if (!(referRejectDetailDTO.getReferRejectReasons() == null
              || referRejectDetailDTO.getReferRejectReasons().isEmpty())) {
            referRejectDetailDTO.getReferRejectReasons().forEach(referReasonDTO -> {
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
    referRejectDetail.setGateInTime(DateUtil.getParsedDateTime(dto.getGateInTime()));
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
      if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty())) {
        Long systemUserId = SecurityHelper.getCurrentUserId();
        if (!(systemUserId == null || systemUserId == 0)) {
          PrintReject printReject = new PrintReject();
          printReject.setClientIP(ipAddress);
          if (referReject.getBaseCommonGateInOut().getCard() != null) {
            SmartCardUser cardUser = referReject.getBaseCommonGateInOut().getCard().getSmartCardUser();
            if (cardUser != null) {
              printReject.setDriverIC(cardUser.getPassportNo());
              printReject.setDriverName(cardUser.getCommonContactAttribute().getPersonName());
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
