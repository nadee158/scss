package com.privasia.scss.refer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
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

import com.privasia.scss.common.enums.ContainerPosition;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.util.CommonUtil;
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
import com.privasia.scss.refer.dto.ReferRejectDTO;
import com.privasia.scss.refer.dto.ReferRejectDetailObjetDto;
import com.privasia.scss.refer.dto.ReferRejectDetailUpdateObjetDto;
import com.privasia.scss.refer.dto.ReferRejectListDto;
import com.privasia.scss.refer.dto.ReferRejectObjetDto;
import com.privasia.scss.refer.dto.ReferRejectUpdateObjetDto;

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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ModelMap getReferRejectList(ModelMap map, int page, int pageSize) {

    Pageable pageRequest = new PageRequest(page, pageSize, Sort.Direction.DESC, "referDateTime");
    Page<ReferReject> referRejectPages = referRejectRepository.findByStatusCode(HpatReferStatus.ACTIVE, pageRequest);

    long totalcount = referRejectPages.getTotalElements();

    if (referRejectPages != null && !referRejectPages.getContent().isEmpty()) {
      List<ReferRejectListDto> dtoList = new ArrayList<ReferRejectListDto>();
      referRejectPages.getContent().forEach(referReject -> {
        dtoList.add(new ReferRejectListDto(referReject));
      });
      map.put("totalcount", totalcount);
      map.put("referList", dtoList);
      return map;
    } else {
      throw new ResultsNotFoundException("No refer rejects were found!");
    }
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public ReferRejectDTO getReferRejectByReferId(long referId) {
    Optional<ReferReject> optionalReferReject = referRejectRepository.findOne(referId);
    ReferReject referReject = optionalReferReject
        .orElseThrow(() -> new ResultsNotFoundException("Refer reject was not found for ID : " + referId));
    return new ReferRejectDTO(referReject);
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Long saveReferReject(ReferRejectObjetDto referRejectObjetDto) {
    
    if (referRejectObjetDto != null) {
    SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElseThrow(
    					() -> new AuthenticationServiceException("Log in User Not Found : " + SecurityHelper.getCurrentUserId()));
    
    // bind details via beanutils
    ReferReject referReject = null;
    referReject = convertToReferRejectDomain(referRejectObjetDto, referReject, systemUser);
    
    ReferReject persisted = referRejectRepository.save(referReject);
    if (persisted != null) {
    	return persisted.getReferRejectID();
    }
    return 0L;
    
    } else {
    	throw new BusinessException("No Data given to be updated!");
    }
    
   
  }

  public ReferReject convertToReferRejectDomain(ReferRejectObjetDto referRejectObjetDto, ReferReject referReject, SystemUser logInUser) {
    
	if(referRejectObjetDto.getExpWeightBridge() == 0)
		throw new BusinessException("Incorrect Weight Bridge !");
	// automated conversion
    referReject = referRejectObjetDto.convertToReferRejectDomain(referReject);

    // manual conversion
    BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();

    Optional<Card> optionalCard = cardRepository.findOne(referRejectObjetDto.getCard());
    // automated conversion
    baseCommonGateInOut = referRejectObjetDto.convertToBaseCommonGateInOutAttribute(baseCommonGateInOut);

    // manual conversion
    // set EIR STATUS to R
    baseCommonGateInOut.setEirStatus(TransactionStatus.REJECT);

    // fetch from clientIP
    Optional<Client> optionalClient = clientRepository.findByWebIPAddress(referRejectObjetDto.getGateInClientIP());
    baseCommonGateInOut.setGateInClient(optionalClient.orElseThrow(
        () -> new BusinessException("Gate In Client not found " + referRejectObjetDto.getGateInClientIP())));
    baseCommonGateInOut.setGateInClerk(logInUser);

    // throw due to card not found
    baseCommonGateInOut.setCard(optionalCard
        .orElseThrow(() -> new BusinessException("Scan Card was not found " + referRejectObjetDto.getCard())));

    // gate in time should capture from client
    // need to clarify from feroz this. here we need to save this time
    if (StringUtils.isEmpty(referRejectObjetDto.getTimeGateIn()))
      throw new BusinessException("Gate In Time Required !");
    baseCommonGateInOut.setTimeGateIn(CommonUtil.getParsedDate(referRejectObjetDto.getTimeGateIn()));

    baseCommonGateInOut.setTimeGateInOk(CommonUtil.getParsedDate(referRejectObjetDto.getTimeGateInOk()));

    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    // Need to set to ACTV
    referReject.setStatusCode(HpatReferStatus.ACTIVE);


    // from card take the company
    referReject.setCompany(optionalCard.get().getCompany());

    ReferRejectDetailObjetDto referRejectDetailObjetDto = referRejectObjetDto.getReferRejectDetail();

    if (referRejectDetailObjetDto != null) {
      
      ReferRejectDetail referRejectDetail = null;
     
      referRejectDetail = convertToReferRejectDetailDomain(referRejectDetailObjetDto, referRejectDetail);
      if (referReject.getReferRejectDetails() == null) {
        referReject.setReferRejectDetails(new HashSet<ReferRejectDetail>());
      }
      referRejectDetail.setReferBy(logInUser);
      referRejectDetail.setReferReject(referReject);
      referReject.getReferRejectDetails().add(referRejectDetail);
    
    }else{
    	throw new BusinessException("No Refer Reject Data given to be updated!");
    }
    

    return referReject;
  }

  public ReferRejectDetail convertToReferRejectDetailDomain(ReferRejectDetailObjetDto referRejectDetailObjetDto,
      ReferRejectDetail referRejectDetail) {
    // automated conversion
    referRejectDetail = referRejectDetailObjetDto.convertToReferRejectDetailDomain(referRejectDetail);

    // manual conversion
    referRejectDetail.setPosition(ContainerPosition.fromValue(referRejectDetailObjetDto.getPosition()));
    referRejectDetail.setStatus(ReferStatus.fromValue(referRejectDetailObjetDto.getStatus()));

    // ReferRejectReason objects
    constructReferRejectReasonList(referRejectDetailObjetDto.getReferReasonIds(), referRejectDetail);

    return referRejectDetail;
  }

  public void constructReferRejectReasonList(List<Long> referReasonIds, ReferRejectDetail referRejectDetail) {
    if (referReasonIds != null &&  !referReasonIds.isEmpty()) {
      if (referRejectDetail.getReferRejectReason() == null) {
        referRejectDetail.setReferRejectReason(new HashSet<ReferRejectReason>());
      }
      referReasonIds.forEach(referReasonId -> {
        ReferRejectReason reason = new ReferRejectReason();
        ReferReason referReason = referReasonRepository.findOne(referReasonId).orElseThrow(() -> new BusinessException("Provided Refer Reson Not Found " + referReasonId));
        reason.setReferReason(referReason);
        reason.setReferRejectDetail(referRejectDetail);
        referRejectDetail.getReferRejectReason().add(reason);
      });
    }

  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public String updateReferReject(ReferRejectUpdateObjetDto dto) {
    String status = "ERROR";
    ReferReject persisted = null;
    List<SupervisorReferRejectReason> supervisorReferRejectReasonList = new ArrayList<SupervisorReferRejectReason>();
    if (!(dto == null || dto.getDetailUpdateObjetDtos() == null || dto.getDetailUpdateObjetDtos().isEmpty())) {
      SystemUser systemUser = systemUserRepository.findOne(SecurityHelper.getCurrentUserId()).orElse(null);
      if (!(systemUser == null)) {
        ReferReject referReject = referRejectRepository.findOne(dto.getReferRejectID()).orElse(null);
        if (!(referReject == null)) {
          if (!(referReject.getReferRejectDetails() == null || referReject.getReferRejectDetails().isEmpty())) {
            referReject.getReferRejectDetails().forEach(referRejectDetail -> {
              dto.getDetailUpdateObjetDtos().forEach(detailUpdateDto -> {
                if (referRejectDetail.getReferRejectDetailID() == detailUpdateDto.getReferRejectDetailID()) {
                  // update detail
                  referRejectDetail.setStatus(ReferStatus.fromValue(detailUpdateDto.getStatus()));
                  referRejectDetail.setSupervisorRemarks(StringUtils.upperCase(detailUpdateDto.getSupervisorRemarks()));
                  referRejectDetail.setRejectBy(systemUser);
                  referRejectDetail.setContainerNo(StringUtils.upperCase(detailUpdateDto.getContainerNo()));

                  if (!(detailUpdateDto.getReferReasonIdList() == null
                      || detailUpdateDto.getReferReasonIdList().isEmpty())) {
                    detailUpdateDto.getReferReasonIdList().forEach(referReasonId -> {
                      ReferReason referReason = referReasonRepository.findOne(referReasonId).orElse(null);
                      if (!(referReason == null)) {
                        SupervisorReferRejectReason reason = new SupervisorReferRejectReason();
                        reason.setReferReason(referReason);
                        reason.setReferRejectDetail(referRejectDetail);
                        supervisorReferRejectReasonList.add(reason);
                      } else {
                        throw new ResultsNotFoundException("Refer reason was not found!");
                      }

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
          } else {
            throw new ResultsNotFoundException("Refer reject detail was not found!");
          }
        } else {
          throw new ResultsNotFoundException("Refer reject detail was not found!");
        }
      } else {
        throw new ResultsNotFoundException("System User was not found!");
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

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public String updateLineCodeAndGateInDateForReferRejectDetail(ReferRejectDetailUpdateObjetDto dto) {
    String status = "ERROR";
    if (!(dto == null)) {
      ReferRejectDetail referRejectDetail = referRejectDetailRepository
          .findByReferReject_ReferRejectIDAndContainerNo(dto.getReferRejectID(), dto.getContainerNo());
      if (!(referRejectDetail == null)) {
        referRejectDetail.setLineCode(StringUtils.upperCase(dto.getLineCode()));
        referRejectDetail.setGateInTime(CommonUtil.getParsedDate(dto.getGateInTime()));
        ReferRejectDetail persisted = referRejectDetailRepository.save(referRejectDetail);
        if (!(persisted == null || persisted.getReferRejectDetailID() <= 0)) {
          if (persisted.getReferReject().getReferRejectID() == dto.getReferRejectID()) {
            status = "SUCCESS";
          }
        }
      } else {
        throw new ResultsNotFoundException("Refer reject detail was not found!");
      }
    }
    return status;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
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
