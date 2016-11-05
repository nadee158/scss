/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.core.model.QHPATBooking;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;



/**
 * @author Janaka
 *
 */
@Component
public final class HPATBookingPredicates {


  private HPATBookingPredicates() {}


  public static Predicate byBookingStatus(String status) {

    if (StringUtils.isEmpty(status)) {
      return QHPATBooking.hPATBooking.isNull();
    } else {
      return QHPATBooking.hPATBooking.status.eq(HpatReferStatus.fromCode(status));
    }
  }

  public static Predicate byCardNo(String cardNo) {
    if (StringUtils.isEmpty(cardNo)) {
      return QHPATBooking.hPATBooking.isNull();
    } else {
      return QHPATBooking.hPATBooking.cardNo.eq(cardNo);
    }
  }

  public static Predicate byAppointmentEndDate(LocalDateTime date) {
    LocalDateTime endDate = date.plusHours(24);
    return QHPATBooking.hPATBooking.appointmentEndDate.loe(endDate);
  }

  public static Predicate byBookingDetailTypes(List<BookingType> bookingTypes) {
    return QHPATBooking.hPATBooking.hpatBookingDetails.any().bookingType.in(bookingTypes);
  }

  /**
   * order specifire for ORDER BY bookHpat.APPT_DATE_START ASC
   * 
   * @return
   */
  public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
    return QHPATBooking.hPATBooking.appointmentStartDate.asc();
  }

}
