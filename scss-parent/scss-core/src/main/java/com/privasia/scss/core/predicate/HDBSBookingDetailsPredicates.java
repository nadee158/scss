/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.core.model.QHDBSBkgDetail;
import com.privasia.scss.core.model.QHDBSBkgMaster;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class HDBSBookingDetailsPredicates {

	private HDBSBookingDetailsPredicates() {
	}
	
	
	public static Predicate byCardNo(String cardNo) {
		if (StringUtils.isEmpty(cardNo)) {
			return QHDBSBkgDetail.hDBSBkgDetail.hDBSBkgMaster.cardNo.isNull();
		} else {
			return QHDBSBkgDetail.hDBSBkgDetail.hDBSBkgMaster.cardNo.eq(cardNo);
		}
	}
	
	public static Predicate byDrayageBooking(Integer drayageBooking) {
		return QHDBSBkgDetail.hDBSBkgDetail.hDBSBkgMaster.drayageBooking.eq(drayageBooking);
	}
	

	public static Predicate byNullableSCSSStatusCode() {
		return QHDBSBkgDetail.hDBSBkgDetail.scssStatusCode.isNull();

	}

	public static Predicate byApptDateTimeToActual(LocalDateTime apptDateTimeToActual) {
		return QHDBSBkgDetail.hDBSBkgDetail.apptDateTimeToActual.lt(apptDateTimeToActual);
	}

	public static Predicate byApptDateTimeFrom(LocalDateTime apptDateTimeFrom) {
		return QHDBSBkgDetail.hDBSBkgDetail.apptDateTimeFrom.goe(apptDateTimeFrom);
	}

	public static Predicate byApptDateTimeFromBetween(LocalDateTime timeFrom, LocalDateTime timeTo) {
		return QHDBSBkgDetail.hDBSBkgDetail.apptDateTimeFrom.between(timeFrom, timeTo);
	}

	public static Predicate byHDBSStatusTypes(List<HDBSStatus> statusCode) {
		return QHDBSBkgDetail.hDBSBkgDetail.statusCode.in(statusCode);
	}

	public static Predicate byHDBSBookingDetailByIDList(List<String> hdbsDetailIDList) {
		return QHDBSBkgDetail.hDBSBkgDetail.hdbsBKGDetailID.in(hdbsDetailIDList);
	}

	/**
	 * order specifire for ORDER BY hdbsBookingDetails.APPT_DATETIME_TO_ACTUAL
	 * ASC
	 * 
	 * @return
	 */
	public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
		return QHDBSBkgDetail.hDBSBkgDetail.apptDateTimeToActual.asc();
	}

}
