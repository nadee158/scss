/**
 * 
 */
package com.privasia.scss.core.predicate;


import java.util.List;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.core.model.QHPABBookingDetail;
import com.querydsl.core.types.Predicate;



/**
 * @author Janaka
 *
 */
@Component
public final class HPABBookingDetailPredicates {


  private HPABBookingDetailPredicates() {}


  public static Predicate byBookingTypes(List<BookingType> bookingTypes) {
    return QHPABBookingDetail.hPABBookingDetail.bookingType.in(bookingTypes);

  }


}
