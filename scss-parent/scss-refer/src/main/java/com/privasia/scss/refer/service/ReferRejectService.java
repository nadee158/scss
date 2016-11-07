package com.privasia.scss.refer.service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.PrintReject;
import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ReferRejectReason;
import com.privasia.scss.core.model.SmartCardUser;
import com.privasia.scss.core.model.SupervisorReferRejectReason;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.CompanyRepository;
import com.privasia.scss.core.repository.PrintRejectRepository;
import com.privasia.scss.core.repository.ReferReasonRepository;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.SupervisorReferRejectReasonRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.util.constant.ContainerPosition;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.privasia.scss.core.util.constant.ReferStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;
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
  private CardUsageRepository cardUsageRepository;

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private SupervisorReferRejectReasonRepository supervisorReferRejectReasonRepository;

  @Autowired
  private PrintRejectRepository printRejectRepository;

  @Transactional()
  public List<ReferRejectListDto> getReferRejectList(String statusCode, int page, int pageSize) {
    PageRequest pageRequest = new PageRequest(page, pageSize);

    List<ReferRejectDetail> list = referRejectDetailRepository
        .findByReferReject_StatusCodeOrderByReferReject_ReferDateTimeDesc(statusCode, pageRequest);
    if (!(list == null || list.isEmpty())) {
      List<ReferRejectListDto> dtoList = new ArrayList<ReferRejectListDto>();
      list.forEach(referRejectDetail -> {
        dtoList.add(new ReferRejectListDto(referRejectDetail));
      });
      return dtoList;
    } else {
      throw new ResultsNotFoundException("No refer rejects were found!");
    }
  }

  @Transactional()
  public ReferRejectListDto getReferRejectByReferId(long referId) {
    ReferRejectDetail referRejectDetail = referRejectDetailRepository.findByReferReject_ReferRejectID(referId);
    if (!(referRejectDetail == null)) {
      return new ReferRejectListDto(referRejectDetail);
    } else {
      throw new ResultsNotFoundException("Refer reject was not found!");
    }
  }

  @Transactional()
  public Long saveReferReject(ReferRejectObjetDto referRejectObjetDto) {
    ReferReject referReject = null;
    if (referRejectObjetDto.getReferId().isPresent()) {
      referReject = referRejectRepository.findOne(referRejectObjetDto.getReferId().orElse(null)).orElse(null);
    }
    // bind details via beanutils
    referReject = convertToReferRejectDomain(referRejectObjetDto, referReject);
    ReferReject persisted = referRejectRepository.save(referReject);
    if (!(persisted == null || persisted.getReferRejectID() <= 0)) {
      return persisted.getReferRejectID();
    }
    return 0l;
  }

  public ReferReject convertToReferRejectDomain(ReferRejectObjetDto referRejectObjetDto, ReferReject referReject) {
    // automated conversion
    referReject = referRejectObjetDto.convertToReferRejectDomain(referReject);
    // manual conversion

    BaseCommonGateInOutAttribute baseCommonGateInOut = referReject.getBaseCommonGateInOut();

    Optional<Card> card = cardRepository.findOne(referRejectObjetDto.getCard());
    // automated conversion
    baseCommonGateInOut = referRejectObjetDto.convertToBaseCommonGateInOutAttribute(baseCommonGateInOut);

    // manual conversion
    baseCommonGateInOut.setEirStatus(TransactionStatus.fromCode(referRejectObjetDto.getTransactionStatus()));
    baseCommonGateInOut.setGateInClerk(systemUserRepository.findOne(referRejectObjetDto.getGateInClerk()).orElse(null));
    baseCommonGateInOut.setGateInClient(clientRepository.findOne(referRejectObjetDto.getGateInClient()).orElse(null));

    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    referReject.setStatusCode(HpatReferStatus.valueOf(referRejectObjetDto.getStatusCode()));

    referReject.setCardUsage(cardUsageRepository.findOne(referRejectObjetDto.getCardUsageID()).orElse(null));
    referReject.setCompany(companyRepository.findOne(referRejectObjetDto.getCompanyID()).orElse(null));
    referReject.setCard(card.orElse(null));
    ReferRejectDetailObjetDto referRejectDetailObjetDto = referRejectObjetDto.getReferRejectDetail();

    if (!(referRejectDetailObjetDto == null)) {
      ReferRejectDetail referRejectDetail = null;
      if (referRejectDetailObjetDto.getReferRejectDetailID().isPresent()) {
        referRejectDetail =
            referRejectDetailRepository.findOne(referRejectDetailObjetDto.getReferRejectDetailID().orElse(null));
      }
      referRejectDetail = convertToReferRejectDetailDomain(referRejectDetailObjetDto, referRejectDetail);
      if (referReject.getReferRejectDetails() == null) {
        referReject.setReferRejectDetails(new HashSet<ReferRejectDetail>());
      }
      referRejectDetail.setAddBy(referReject.getAddBy());
      referRejectDetail.setDateTimeAdd(referReject.getDateTimeAdd());
      referRejectDetail.setDateTimeUpdate(referReject.getDateTimeUpdate());
      referRejectDetail.setUpdateBy(referReject.getUpdateBy());
      referRejectDetail.setReferReject(referReject);
      referReject.getReferRejectDetails().add(referRejectDetail);
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

  private void constructReferRejectReasonList(List<Long> referReasonIds, ReferRejectDetail referRejectDetail) {
    if (!(referReasonIds == null || referReasonIds.isEmpty())) {
      if (referRejectDetail.getReferRejectReason() == null) {
        referRejectDetail.setReferRejectReason(new HashSet<ReferRejectReason>());
      }
      referReasonIds.forEach(referReasonId -> {
        ReferRejectReason reason = new ReferRejectReason();
        reason.setAddBy(referRejectDetail.getAddBy());
        reason.setDateTimeAdd(referRejectDetail.getDateTimeAdd());
        reason.setDateTimeUpdate(referRejectDetail.getDateTimeUpdate());
        reason.setUpdateBy(referRejectDetail.getUpdateBy());
        ReferReason referReason = referReasonRepository.findOne(referReasonId).orElse(null);
        reason.setReferReason(referReason);
        reason.setReferRejectDetail(referRejectDetail);
        referRejectDetail.getReferRejectReason().add(reason);
      });
    }

  }

  @Transactional
  public String updateReferReject(ReferRejectUpdateObjetDto dto) {
    String status = "ERROR";
    ReferReject persisted = null;
    List<SupervisorReferRejectReason> supervisorReferRejectReasonList = new ArrayList<SupervisorReferRejectReason>();
    if (!(dto == null || dto.getDetailUpdateObjetDtos() == null || dto.getDetailUpdateObjetDtos().isEmpty())) {
      SystemUser systemUser = systemUserRepository.findOne(dto.getUserId()).orElse(null);
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
                        reason.setAddBy(dto.getUserId());
                        reason.setDateTimeAdd(ZonedDateTime.now());
                        reason.setDateTimeUpdate(ZonedDateTime.now());
                        reason.setReferReason(referReason);
                        reason.setReferRejectDetail(referRejectDetail);
                        reason.setUpdateBy(dto.getUserId());
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

  public String updateLineCodeAndGateInDateForReferRejectDetail(ReferRejectDetailUpdateObjetDto dto) {
    String status = "ERROR";
    if (!(dto == null)) {
      ReferRejectDetail referRejectDetail = referRejectDetailRepository
          .findByReferReject_ReferRejectIDAndContainerNo(dto.getReferRejectID(), dto.getContainerNo());
      if (!(referRejectDetail == null)) {
        referRejectDetail.setLineCode(StringUtils.upperCase(dto.getLineCode()));
        referRejectDetail.setGateInTime(dto.getGateInTime());
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
    //@formatter:off
//    String sqlReferUpdate = "UPDATE SCSS_REFER_REJECT_DET SET"
//        + " LINE_CODE = " + SQL.format(f.getLineC1())
//        + " , GATE_IN_DATETIME = " + SQL.TO_DATETIME(timeGateIn)
//        + " WHERE REFER_ID = " + SQL.format(f.getReferId())
//        + " AND CONT_NO = " + SQL.format(f.getContainerNoC1());
    return status;
  }

 
  public String savePrintReject(long referId, String ipAddress, long systemUserId) {
    String status = "ERROR";
    ReferReject referReject=referRejectRepository.findOne(referId).orElse(null);
    if (!(referReject == null)) {
      if(!(referReject.getReferRejectDetails()==null || referReject.getReferRejectDetails().isEmpty())){
        SystemUser systemUser = systemUserRepository.findOne(systemUserId).orElse(null);
        if (!(systemUser == null)) {
          PrintReject printReject=new PrintReject();
          printReject.setAddBy(systemUserId);
          printReject.setClientIP(ipAddress);
          printReject.setDateTimeAdd(ZonedDateTime.now());
          printReject.setDateTimeUpdate(ZonedDateTime.now());
          if(!(referReject.getBaseCommonGateInOut()==null || referReject.getCard()==null)){
            SmartCardUser cardUser=referReject.getCard().getSmartCardUser();
            if(!(cardUser==null)){
              printReject.setDriverIC(cardUser.getPassportNo());
              printReject.setDriverName(cardUser.getCommonContactAttribute().getPersonName());
            }
          }
          
          printReject.setPmHeadNo(referReject.getBaseCommonGateInOut().getPmHeadNo());
          printReject.setReferReject(referReject);
          printReject.setStaffName(systemUser.getCommonContactAttribute().getPersonName());
          printReject.setStaffNumber(systemUser.getStaffNumber());
          printReject.setStatus(status);
          printReject.setUpdateBy(systemUserId);
          
          int i=0;
          for (ReferRejectDetail referRejectDetail : referReject.getReferRejectDetails()) {
            if(i==0){
              //container 1
              printReject.setContainer01(referRejectDetail.getContainerNo());
              printReject.setContainer01Remarks(referRejectDetail.getSupervisorRemarks());
              printReject=setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason(),i);
            }else{
              //container 2
              printReject.setContainer02(referRejectDetail.getContainerNo());
              printReject.setContainer02Remarks(referRejectDetail.getSupervisorRemarks());
              printReject=setRejectReasonsForPrentReject(printReject, referRejectDetail.getReferRejectReason(),i);
            }
            i++;
          }
          
          PrintReject saved=printRejectRepository.save(printReject);
          if(!(saved==null || saved.getPrientRejectID()==0)){
            status="SUCCESS";
          }
            
          }
          
        }else{
          throw new ResultsNotFoundException("System User detail was not found!");
        }
      }else{
        throw new ResultsNotFoundException("Refer reject detail was not found!");
      }
      return status;
  }
  
  private PrintReject setRejectReasonsForPrentReject(PrintReject printReject, Set<ReferRejectReason> referRejectReasons,
      int i) {
    
    String reason1C1 = "";
    String reason2C1 ="";
    String reason3C1 = "";
    
    if(!(referRejectReasons==null || referRejectReasons.isEmpty())){
      int count=0;
      for (ReferRejectReason referRejectReason : referRejectReasons) {
        if(!(referRejectReason.getReferReason()==null)){
          String reason=referRejectReason.getReferReason().getReasonDescription();
          if (count==1) {
            reason1C1 = "1) " + reason;
        } else if (count == 2){
            reason2C1 = "2) " + reason;
        } else {
            if (StringUtils.isBlank(reason3C1)){
                reason3C1 =  count + ")" + reason;
            } else {
                reason3C1 = reason3C1 + "," + count + ")" + reason;
            }
        }
        }
        
        count++;
      }
      if(i==0){
        printReject.setRejectContainer01Due01(reason1C1);
        printReject.setRejectContainer01Due02(reason2C1);
        printReject.setRejectContainer01Due03(reason3C1);
      }else{
        printReject.setRejectContainer02Due01(reason1C1);
        printReject.setRejectContainer02Due02(reason2C1);
        printReject.setRejectContainer02Due03(reason3C1);
      }
    }
    return printReject;
  } 
 



}
