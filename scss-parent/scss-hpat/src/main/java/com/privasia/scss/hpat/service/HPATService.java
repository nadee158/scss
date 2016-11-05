/**
 * 
 */
package com.privasia.scss.hpat.service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.privasia.scss.hpat.dto.HpatDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hpatService")
public class HPATService {

  @Autowired
  private HPATBookingRepository hpatBookingRepository;

  @Autowired
  private CardRepository cardRepository;



  // overloaded method of above to match the following method's requirements
  /*
   * String sql =
   * "SELECT bookHpat.BOOKING_ID, bookHpat.APPT_DATE_START, bookHpat.BUFFER, bookHpat.CRD_SCARDNO, bookHpat.CREATION_DATE \n"
   * // +
   * " , bookHpat.DRIVER_IC_PP, bookHpat.PM_NO, bookHpat.STATUS_CODE, bookHpat.TRAILER_NO , bookHpat.TRAILER_TYPE, bookHpat.HAULIER_CODE \n"
   * // +
   * " , bookHpat.PM_HEAD_WEIGHT, bookHpat.AXLE_WEIGHT, bookHpat.TRAILER_PLATE , bookHpat.APPT_DATE_END, bookHpat.AXLE_VERIFIED, bookHpat.PM_VERIFIED \n"
   * // +
   * " , det.MGW, det.BOOKING_TYPE, det.CONTAINER_LENGTH, det.CONTAINER_NUMBER, det.CONTAINER_TYPE, det.IMP_GATEPASS_NUMBER, det.SOLAS_INSTRUCTION \n"
   * // +
   * " , det.EXP_SEAL_NO1, det.EXP_SEAL_NO2, det.ODD_LOCATION, det.ODD_PICK_DROP, det.SHIPPER_VGM, det.SOLAS_DET_ID, det.CONTAINER_ISO, det.EXP_BOOKING_NO \n"
   * // + " , det.FA_LEDGER_CODE, det.SOLAS_REF_NO, card.CRD_CARDID_SEQ \n" // +
   * " FROM ETP_BOOKING_HPAT bookHpat \n" // +
   * " INNER JOIN ETP_BOOKING_HPAT_DETAIL det on det.BOOKING_ID = bookHpat.BOOKING_ID \n" // +
   * " INNER JOIN SCSS_CARD card ON bookHpat.CRD_SCARDNO = card.CRD_SCARDNO \n" // +
   * " WHERE bookHpat.BOOKING_ID = ? " + " AND bookHpat.STATUS_CODE = 'ACTV' "
   * "ORDER BY bookHpat.APPT_DATE_START ASC";;
   */
  private List<HpatDto> findEtpHpat4ImpAndExp(long cardId, LocalDateTime date, List<BookingType> bookingTypes) {
    List<HpatDto> dtoList = new ArrayList<HpatDto>();
    Optional<Card> card = cardRepository.findOne(cardId);
    Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
    System.out.println("ACTV : " + HpatReferStatus.ACTIVE.getValue());
    Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
    Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
    Predicate condition = null;
    if (!(bookingTypes == null || bookingTypes.isEmpty())) {
      Predicate byBookingTypes = HPATBookingPredicates.byBookingDetailTypes(bookingTypes);
      condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
    } else {
      condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate);
    }
    // NEWLY ADDED LINE FOR : - "ORDER BY bookHpat.APPT_DATE_START ASC"; add predicate
    OrderSpecifier<LocalDateTime> sortSpec = HPATBookingPredicates.orderByAppointmentStartDateAsc();

    Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition, sortSpec);

    bookingList.forEach((HPATBooking b) -> {
      dtoList.add(new HpatDto(b));
    });
    return dtoList;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> findHpats(Long cardId, LocalDateTime systemDateTime, List<String> bookingTypes,
      String impExpScreen, String menuId) throws ResultsNotFoundException {

    // String impExpScreen = (String) request.getParameter("impExp");
    // String menuId = (String) request.getParameter("menuId");

    List<HpatDto> hpats = null;
    /**
     * Eterminal Flag
     */
    /* TODO:Janaka Sir */
    // String etpFlag = gateInDao.getWDCGlobalSeeting("ETP_HPAT");

    List<BookingType> convertedBookingTypes = null;
    if (!(bookingTypes == null || bookingTypes.isEmpty())) {
      List<BookingType> temp = new ArrayList<BookingType>();
      bookingTypes.forEach(bookingType -> {
        temp.add(BookingType.fromValue(bookingType));
      });
      convertedBookingTypes = temp;
    }

    hpats = findEtpHpat4ImpAndExp(cardId, systemDateTime, convertedBookingTypes);
    if (!(hpats == null || hpats.isEmpty())) {
      // add controller's code here
      // move in to service

      for (HpatDto hpat : hpats) {
        if (StringUtils.isBlank(hpat.getApptStart()))
          continue;
        if (systemDateTime.isAfter(hpat.getApptEndDate()))
          hpat.setStatus(HpatDto.LATE);
        if (systemDateTime.isAfter(hpat.getApptStartDate()) && systemDateTime.isBefore(hpat.getApptEndDate())) {
          hpat.setStatus(HpatDto.ACTIVE);
          hpat.setOnTimeFlag("Y");
        } else if (systemDateTime.isBefore(hpat.getApptEndDate())) {
          hpat.setStatus(HpatDto.EARLY);
          hpat.setOnTimeFlag("N");
        }

        // Imp Exp Screen Value
        hpat.setImpExpScreen(impExpScreen);
        if (StringUtils.isNotBlank(menuId)) {
          hpat.setMenuId(menuId);
        }
      }



      Comparator<HpatDto> byApptStartDate = (o1, o2) -> o1.getApptStartDate().compareTo(o2.getApptStartDate());
      List<HpatDto> sortedHpats = new ArrayList<HpatDto>();
      hpats.stream().sorted(byApptStartDate).forEach(e -> sortedHpats.add(e));

      return sortedHpats;

    } else {
      // need to discuss with etp team to manage web services between etp and scss
      throw new ResultsNotFoundException("No Hpat Records were found!");
    }
  }



}
