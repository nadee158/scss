/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
// import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.HDBSBkgDetailGridDTO;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.HDBSBkgMaster;
import com.privasia.scss.core.predicate.HDBSBookingMasterPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.HDBSBookingMasterRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hdbsService")
public class HDBSService {

  @Autowired
  private HDBSBookingDetailRepository hdbsBookingDetailRepository;

  @Autowired
  private HDBSBookingMasterRepository hdbsBookingMasterRepository;

  @Autowired
  private CardRepository cardRepository;

  @Autowired
  private WDCGlobalSettingRepository wdcGlobalSettingRepository;

  @Autowired
  private ModelMapper modelMapper;

  public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList) {

    Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);

    return bkgDetails.collect(Collectors.toList());
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public HDBSBkgGridDTO findHDBSBookingDetailByCard(Long cardID) {

    Optional<Card> optionalCard = cardRepository.findOne(cardID);

    optionalCard.orElseThrow(() -> new BusinessException("Scan Card was not found " + cardID));

    HDBSBkgGridDTO gridDTo = new HDBSBkgGridDTO();

    LocalDateTime systemDateTime = LocalDateTime.now();
    // set the value in the hdbsbooking dto
    gridDTo.setArrivalTime(systemDateTime);

    Optional<Integer> optionalHdbsStart =
        wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_START_HOUR);
    int hdbsStart = optionalHdbsStart.orElse(ApplicationConstants.DEFAULT_HDBS_START_HOUR_VALUE);

    Optional<Integer> optionalHdbsEnd =
        wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_END_HOUR);
    int hdbsEnd = optionalHdbsEnd.orElse(ApplicationConstants.DEFAULT_HDBS_END_HOUR_VALUE);

    Optional<Integer> optionalHdbsAcceptedStart =
        wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_START);
    int hdbsAcceptStart = optionalHdbsAcceptedStart.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_START_VALUE);

    Optional<Integer> optionalHdbsAcceptedEnd =
        wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_END);
    int hdbsAcceptEnd = optionalHdbsAcceptedEnd.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_END_VALUE);

    Optional<String> optionalHdbsManual =
        wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode(ApplicationConstants.HDBS_MANUAL);

    // need to do
    gridDTo.setShowManual(optionalHdbsManual.orElse(StringUtils.EMPTY));


    LocalDateTime dateFrom = systemDateTime.plusHours(hdbsStart);// hpatStart
    LocalDateTime dateTo = systemDateTime.plusHours(hdbsEnd);// hpatEnd


    List<HDBSStatus> hdbsStatusList = new ArrayList<>();
    hdbsStatusList.add(HDBSStatus.NEW);
    hdbsStatusList.add(HDBSStatus.ACCEPTED);
    hdbsStatusList.add(HDBSStatus.REJECTED);
    hdbsStatusList.add(HDBSStatus.CANCELLED);

    gridDTo = createPredicatesAndFindHDBS(optionalCard.get(), dateFrom, dateTo, hdbsStatusList, gridDTo);


    LocalDateTime acceptDateFrom = systemDateTime.plusMinutes(hdbsAcceptStart);
    LocalDateTime acceptDateTo = systemDateTime.plusMinutes(hdbsAcceptEnd);


    setAcceptBookingDurationAndStatus(gridDTo, acceptDateFrom, acceptDateTo);;

    Collections.sort(gridDTo.getHdbsBkgDetailGridDTOList(), HDBSBkgDetailGridDTO.ApptDateTimeFromComparator);

    return gridDTo;
  }


  public HDBSBkgGridDTO createPredicatesAndFindHDBS(Card card, LocalDateTime dateFrom, LocalDateTime dateTo,
      List<HDBSStatus> statusList, HDBSBkgGridDTO gridDTo) {

    List<HDBSBkgDetailGridDTO> hdbsBookingList = new ArrayList<HDBSBkgDetailGridDTO>();

    Predicate byCardNo = HDBSBookingMasterPredicates.byCardNo(String.valueOf(card.getCardNo()));
    Predicate byAPPTDateFrom = HDBSBookingMasterPredicates.byApptDateTimeFrom(dateFrom);
    Predicate byAPPTDateTo = HDBSBookingMasterPredicates.byApptDateTimeToActual(dateTo);
    Predicate byDrayageBooking = HDBSBookingMasterPredicates.byDrayageBooking(0);
    Predicate byHDBSStatus = HDBSBookingMasterPredicates.byHDBSStatusTypes(statusList);
    Predicate byNullableSCSS = HDBSBookingMasterPredicates.byNullableSCSSStatusCode();

    Predicate condition =
        ExpressionUtils.allOf(byCardNo, byAPPTDateFrom, byAPPTDateTo, byDrayageBooking, byHDBSStatus, byNullableSCSS);

    OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingMasterPredicates.orderByAppointmentStartDateAsc();

    Iterable<HDBSBkgMaster> bookingList = hdbsBookingMasterRepository.findAll(condition, orderByAPPStartDate);

    bookingList.forEach((hdbsbkgMaster) -> {
      if (!(hdbsbkgMaster.getHdbsBookingDetails() == null || hdbsbkgMaster.getHdbsBookingDetails().isEmpty())) {
        hdbsbkgMaster.getHdbsBookingDetails().forEach(detail -> {
          hdbsBookingList.add(constructDetailGridDetailDTO(detail));
        });
      }
    });

    gridDTo.setHdbsBkgDetailGridDTOList(hdbsBookingList);
    return gridDTo;

  }


  private HDBSBkgDetailGridDTO constructDetailGridDetailDTO(HDBSBkgDetail detail) {
    return modelMapper.map(detail, HDBSBkgDetailGridDTO.class);
  }



  private void setAcceptBookingDurationAndStatus(HDBSBkgGridDTO gridDTo, LocalDateTime acceptDateFrom,
      LocalDateTime dateTo) {

    gridDTo.getHdbsBkgDetailGridDTOList().stream().filter(hdbs -> (hdbs.getApptDateTimeFrom() != null))
        .forEach(hdbs -> {
          if (StringUtils.equals(HDBSStatus.ACCEPTED.getValue(), hdbs.getStatusCode().getValue())) {

            if (hdbs.getApptDateTimeFrom().isAfter(acceptDateFrom) && hdbs.getApptDateTimeToActual().isBefore(dateTo)) {
              hdbs.setAcceptBooking(true);
            }

            setDuration(hdbs, acceptDateFrom, dateTo);

          }
        });
  }

  private void setDuration(HDBSBkgDetailGridDTO hdbs, LocalDateTime acceptDateFrom, LocalDateTime dateTo) {
    LocalDateTime systemDateTime = LocalDateTime.now();

    String durration = StringUtils.EMPTY;
    String status = StringUtils.EMPTY;
    String onTimeFlag = StringUtils.EMPTY;

    List<ChronoUnit> units = new ArrayList<>();
    units.add(ChronoUnit.HOURS);
    units.add(ChronoUnit.MINUTES);

    if (systemDateTime.isAfter(hdbs.getApptDateTimeToActual())) {

      durration =
          CommonUtil.getFormattedDiffrenceBetweenDays(hdbs.getApptDateTimeToActual(), systemDateTime, units, false);
      status = ApplicationConstants.LATE;
      onTimeFlag = "N";

    } else if (systemDateTime.isBefore(hdbs.getApptDateTimeFrom())) {

      durration = CommonUtil.getFormattedDiffrenceBetweenDays(systemDateTime, hdbs.getApptDateTimeFrom(), units, false);
      status = ApplicationConstants.EARLY;
      onTimeFlag = "N";

    } else if (systemDateTime.isAfter(hdbs.getApptDateTimeFrom())
        && systemDateTime.isBefore(hdbs.getApptDateTimeToActual())) {
      status = ApplicationConstants.ON_TIME;
      onTimeFlag = "Y";
    }

    hdbs.setDurration(durration);
    hdbs.setStatus(status);
    hdbs.setOnTimeFlag(onTimeFlag);

  }

}
