/**
 * 
 */
package com.privasia.scss.hdbs.query.predicate;



/**
 * @author Janaka
 *
 */
public final class HDBSBookingDetailPredicates {
	
	
	private HDBSBookingDetailPredicates() {}


	  /*public static Predicate byBookingStatus(String status) {

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
	  /*public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
	    return QHPATBooking.hPATBooking.appointmentStartDate.asc();
	  }*/

}
