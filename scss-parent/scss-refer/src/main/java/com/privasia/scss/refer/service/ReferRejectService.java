package com.privasia.scss.refer.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.BaseCommonGateInOutAttribute;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.ReferReason;
import com.privasia.scss.core.model.ReferReject;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ReferRejectReason;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.CardUsageRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.CompanyRepository;
import com.privasia.scss.core.repository.ReferReasonRepository;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.util.constant.ContainerPosition;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.privasia.scss.core.util.constant.ReferStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;
import com.privasia.scss.refer.dto.ReferRejectDetailObjetDto;
import com.privasia.scss.refer.dto.ReferRejectListDto;
import com.privasia.scss.refer.dto.ReferRejectObjetDto;

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
  public String saveReferReject(ReferRejectObjetDto referRejectObjetDto) {
    String status = "ERROR";
    ReferReject referReject = null;
    if (referRejectObjetDto.getReferId().isPresent()) {
      referReject = referRejectRepository.findOne(referRejectObjetDto.getReferId().orElse(null)).orElse(null);
    }
    // bind details via beanutils
    referReject = convertToReferRejectDomain(referRejectObjetDto, referReject);
    ReferReject persisted = referRejectRepository.save(referReject);
    if (!(persisted == null || persisted.getReferRejectID() <= 0)) {
      status = "SUCCESS";
    }
    return status;
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
    baseCommonGateInOut.setCard(card.orElse(null));
    baseCommonGateInOut.setEirStatus(TransactionStatus.fromCode(referRejectObjetDto.getTransactionStatus()));
    baseCommonGateInOut.setGateInClerk(systemUserRepository.findOne(referRejectObjetDto.getGateInClerk()).orElse(null));
    baseCommonGateInOut.setGateInClient(clientRepository.findOne(referRejectObjetDto.getGateInClient()).orElse(null));

    referReject.setBaseCommonGateInOut(baseCommonGateInOut);

    referReject.setStatusCode(HpatReferStatus.valueOf(referRejectObjetDto.getStatusCode()));

    referReject.setCardUsage(cardUsageRepository.findOne(referRejectObjetDto.getCardUsageID()).orElse(null));
    referReject.setCompany(companyRepository.findOne(referRejectObjetDto.getCompanyID()).orElse(null));

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


}
