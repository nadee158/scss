/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.QHPABBookingDetail;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class HPABBookingDetailPredicates {

	private HPABBookingDetailPredicates() {
	}

	public static Predicate byBookingTypes(List<BookingType> bookingTypes) {
		return QHPABBookingDetail.hPABBookingDetail.bookingType.in(bookingTypes);

	}

	public static Predicate byCardNo(String cardNo) {
		if (StringUtils.isEmpty(cardNo)) {
			return QHPABBookingDetail.hPABBookingDetail.hpabBooking.cardNo.isNull();
		} else {
			return QHPABBookingDetail.hPABBookingDetail.hpabBooking.cardNo.eq(cardNo);
		}
	}

	public static Predicate byBookingStatus(String status) {

		if (StringUtils.isEmpty(status)) {
			return QHPABBookingDetail.hPABBookingDetail.hpabBooking.status.isNull();
		} else {
			return QHPABBookingDetail.hPABBookingDetail.hpabBooking.status.eq(HpabReferStatus.fromCode(status));
		}
	}

	public static Predicate byAppointmentEndDate(LocalDateTime date) {
		LocalDateTime endDate = date.plusHours(24);
		return QHPABBookingDetail.hPABBookingDetail.hpabBooking.appointmentEndDate.loe(endDate);
	}

	/**
	 * order specifire for ORDER BY bookHpat.APPT_DATE_START ASC
	 * 
	 * @return
	 */
	public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
		return QHPABBookingDetail.hPABBookingDetail.hpabBooking.appointmentStartDate.asc();
	}

}
