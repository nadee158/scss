package com.privasia.scss.gatein.odd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.predicate.ODDPredicates;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("gateInODDService")
public class GateInODDService {

  private ModelMapper modelMapper;

  private ODDRepository oddRepository;

  private HDBSBookingDetailRepository hdbsBookingDetailRepository;

  @Autowired
  public void setModelMapper(ModelMapper modelMapper) {
    this.modelMapper = modelMapper;
  }

  @Autowired
  public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
    this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
  }

  @Autowired
  public void setOddRepository(ODDRepository oddRepository) {
    this.oddRepository = oddRepository;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public List<WHODD> saveODDGateInInFo(GateInWriteRequest gateInWriteRequest, Card card, Client gateInClient,
      SystemUser gateInClerk, ImpExpFlagStatus impExpFlag) {

    if (gateInWriteRequest.getWhoddContainers() == null || gateInWriteRequest.getWhoddContainers().isEmpty())
      throw new BusinessException("Invalid GateInWriteRequest to save ODD ! ");
    List<WHODD> oddSavedList = new ArrayList<WHODD>();
    gateInWriteRequest.getWhoddContainers().forEach(whODDdto -> {

      if (gateInWriteRequest.isOddReject()) {
        whODDdto.setGateInStatus(TransactionStatus.REJECT.getValue());

        if (StringUtils.isEmpty(whODDdto.getContainer01().getRemarks()))
          throw new BusinessException(
              "Rejection Remarks is need for container  : " + whODDdto.getContainer01().getContainerNo());
        if (StringUtils.isEmpty(whODDdto.getContainer01().getRejectionReason()))
          throw new BusinessException(
              "Rejection Reason is need for container  : " + whODDdto.getContainer01().getContainerNo());

        if (whODDdto.getContainer02().isPresent()) {
          if (StringUtils.isEmpty(whODDdto.getContainer02().get().getRemarks()))
            throw new BusinessException(
                "Rejection Remarks is need for container  : " + whODDdto.getContainer02().get().getContainerNo());
          if (StringUtils.isEmpty(whODDdto.getContainer02().get().getRejectionReason()))
            throw new BusinessException(
                "Rejection Reason is need for container  : " + whODDdto.getContainer02().get().getContainerNo());
        }

      } else {
        whODDdto.setGateInStatus(TransactionStatus.APPROVED.getValue());
      }

      whODDdto.setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
      whODDdto.setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
      whODDdto.setTimeGateIn(gateInWriteRequest.getGateInDateTime());
      whODDdto.setTimeGateInOk(LocalDateTime.now());
      whODDdto.setInOutFlag(GateInOutStatus.IN.getValue());

      WHODD whODD = modelMapper.map(whODDdto, WHODD.class);

      if (whODDdto.getContainer01() != null) {
        whODD.getContainer01().setOddStatus(TransactionStatus.INPROGRESS);
        if (whODDdto.getContainer01().getHdbsBkgDetailNoId() != null) {
          Optional<HDBSBkgDetail> optHDBSBookingDetail =
              hdbsBookingDetailRepository.findOne(whODDdto.getContainer01().getHdbsBkgDetailNoId());
          HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail.orElseThrow(() -> new ResultsNotFoundException(
              "Invalid HDBS Booking Detail ID :" + whODDdto.getContainer01().getHdbsBkgDetailNoId()));
          whODD.getContainer01().setHdbsBkgDetailNo(hdbsBookingDetail);
          whODD.getContainer01().setHdbsStatus(hdbsBookingDetail.getStatusCode());
          hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.IN_PROGRESS);
          hdbsBookingDetail.setOddTimeGateInOk(whODD.getTimeGateInOk());
          hdbsBookingDetail.setOddIdSeq(whODD);
        }
      }
      if (whODDdto.getContainer02().isPresent()) {
        whODD.getContainer02().setOddStatus(TransactionStatus.INPROGRESS);
        if (whODDdto.getContainer02().get().getHdbsBkgDetailNoId() != null) {

          Optional<HDBSBkgDetail> optHDBSBookingDetail =
              hdbsBookingDetailRepository.findOne(whODDdto.getContainer02().get().getHdbsBkgDetailNoId());
          HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail.orElseThrow(() -> new ResultsNotFoundException(
              "Invalid HDBS Booking Detail ID :" + whODDdto.getContainer02().get().getHdbsBkgDetailNoId()));

          whODD.getContainer02().setHdbsBkgDetailNo(hdbsBookingDetail);
          whODD.getContainer02().setHdbsStatus(hdbsBookingDetail.getStatusCode());
          hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.IN_PROGRESS);
          hdbsBookingDetail.setOddTimeGateInOk(whODD.getTimeGateInOk());
          hdbsBookingDetail.setOddIdSeq(whODD);
        }
      }

      whODD.setCard(card);
      whODD.setGateInClerk(gateInClerk);
      whODD.setGateInClient(gateInClient);

      // before save check the pm plate in used
      if (!isPlateNoHeadNoUsed(whODD)) {
        oddRepository.save(whODD);
        oddSavedList.add(whODD);
      }

    });

    return oddSavedList;
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public boolean isPlateNoHeadNoUsed(WHODD whodd) {

    Predicate byHeadNo = ODDPredicates.byPMHeadNo(whodd.getPmHeadNo());
    Predicate byPlateNo = ODDPredicates.byPMPlateNo(whodd.getPmPlateNo());
    Predicate bycon01ODDStatus = ODDPredicates.byContainer01Status(whodd.getContainer01().getOddStatus());
    Predicate bycon02ODDStatus = ODDPredicates.byContainer02Status(whodd.getContainer02().getOddStatus());
    Predicate byTransactionType = ODDPredicates.byTransactionType(whodd.getImpExpFlag());

    Predicate condition = ExpressionUtils
        .allOf(ExpressionUtils.or(ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), bycon01ODDStatus),
            ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), bycon02ODDStatus)), byTransactionType);

    Iterable<WHODD> oddList = oddRepository.findAll(condition);

    if (oddList == null || Stream.of(oddList).count() == 0)
      return false;

    if (oddList.iterator().hasNext()) {
      WHODD dbODD = oddList.iterator().next();
      if (StringUtils.equalsIgnoreCase(dbODD.getPmHeadNo(), whodd.getPmHeadNo())
          && StringUtils.equalsIgnoreCase(dbODD.getPmPlateNo(), whodd.getPmPlateNo())) {
        throw new BusinessException(
            "PM Head No " + dbODD.getPmHeadNo() + "and PM plate No " + dbODD.getPmPlateNo() + " already in use.");
      } else if (StringUtils.equalsIgnoreCase(dbODD.getPmHeadNo(), whodd.getPmHeadNo())) {
        throw new BusinessException("PM Head No " + dbODD.getPmHeadNo() + " already in use");
      } else if (StringUtils.equalsIgnoreCase(dbODD.getPmPlateNo(), whodd.getPmPlateNo())) {
        throw new BusinessException("PM plate No " + dbODD.getPmPlateNo() + " already in use");
      }
    }

    return false;

  }

}
