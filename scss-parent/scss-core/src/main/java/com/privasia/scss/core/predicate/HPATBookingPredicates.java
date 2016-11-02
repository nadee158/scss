/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.core.model.QHPATBooking;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.querydsl.core.types.Predicate;



/**
 * @author Janaka
 *
 */
@Component
public final class HPATBookingPredicates {
	
	
	private HPATBookingPredicates(){}
	
	
	public static Predicate byBookingStatus(String Status) {
        if (StringUtils.isEmpty(Status)) {
            return QHPATBooking.hPATBooking.isNull();
        }else {
            return  QHPATBooking.hPATBooking.status.eq(HpatReferStatus.valueOf(Status));
        }
	}
	
	public static Predicate byCardNo(String cardNo) {
        if (StringUtils.isEmpty(cardNo)) {
            return QHPATBooking.hPATBooking.isNull();
        }else {
            return QHPATBooking.hPATBooking.cardNo.eq(cardNo);
        }
	}
	
	public static Predicate byAppointmentEndDate(LocalDateTime date) {
		LocalDateTime endDate = date.plusHours(24);
        return QHPATBooking.hPATBooking.appointmentEndDate.loe(endDate);
	}
	
}
