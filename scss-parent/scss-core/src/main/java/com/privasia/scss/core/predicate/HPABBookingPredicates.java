/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.QHPABBooking;
import com.privasia.scss.core.model.QHPABBooking;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;



/**
 * @author Janaka
 *
 */
@Component
public final class HPABBookingPredicates {


  private HPABBookingPredicates() {}


  public static Predicate byBookingStatus(String status) {

    if (StringUtils.isEmpty(status)) {
      return QHPABBooking.hPABBooking.isNull();
    } else {
      return QHPABBooking.hPABBooking.status.eq(HpatReferStatus.fromCode(status));
    }
  }

  public static Predicate byCardNo(String cardNo) {
    if (StringUtils.isEmpty(cardNo)) {
      return QHPABBooking.hPABBooking.isNull();
    } else {
      return QHPABBooking.hPABBooking.cardNo.eq(cardNo);
    }
  }

  public static Predicate byAppointmentEndDate(LocalDateTime date) {
    LocalDateTime endDate = date.plusHours(24);
    return QHPABBooking.hPABBooking.appointmentEndDate.loe(endDate);
  }

  public static Predicate byBookingDetailTypes(List<BookingType> bookingTypes) {
    return QHPABBooking.hPABBooking.hpatBookingDetails.any().bookingType.in(bookingTypes);
  }

  /**
   * order specifire for ORDER BY bookHpat.APPT_DATE_START ASC
   * 
   * @return
   */
  public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
    return QHPABBooking.hPABBooking.appointmentStartDate.asc();
  }

}
