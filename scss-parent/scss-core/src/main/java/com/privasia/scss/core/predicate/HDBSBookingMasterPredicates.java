/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.core.model.QHDBSBkgMaster;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class HDBSBookingMasterPredicates {

	private HDBSBookingMasterPredicates() {
	}

	public static Predicate byCardNo(String cardNo) {
		if (StringUtils.isEmpty(cardNo)) {
			return QHDBSBkgMaster.hDBSBkgMaster.cardNo.isNull();
		} else {
			return QHDBSBkgMaster.hDBSBkgMaster.cardNo.eq(cardNo);
		}
	}

	public static Predicate byNullableSCSSStatusCode() {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().scssStatusCode.isNull();

	}

	public static Predicate byApptDateTimeToActual(LocalDateTime apptDateTimeToActual) {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().apptDateTimeToActual.lt(apptDateTimeToActual);
	}

	public static Predicate byApptDateTimeFrom(LocalDateTime apptDateTimeFrom) {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().apptDateTimeFrom.goe(apptDateTimeFrom);
	}

	public static Predicate byApptDateTimeFromBetween(LocalDateTime timeFrom, LocalDateTime timeTo) {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().apptDateTimeFrom.between(timeFrom, timeTo);
	}

	public static Predicate byHDBSStatusTypes(List<HDBSStatus> statusCode) {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().statusCode.in(statusCode);
	}

	public static Predicate byDrayageBooking(Integer drayageBooking) {
		return QHDBSBkgMaster.hDBSBkgMaster.drayageBooking.eq(drayageBooking);
	}

	public static Predicate byHDBSBookingDetailByIDList(List<String> hdbsDetailIDList) {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().hdbsBKGDetailID.in(hdbsDetailIDList);
	}

	/**
	 * order specifire for ORDER BY hdbsBookingDetails.APPT_DATETIME_TO_ACTUAL
	 * ASC
	 * 
	 * @return
	 */
	public static OrderSpecifier<LocalDateTime> orderByAppointmentStartDateAsc() {
		return QHDBSBkgMaster.hDBSBkgMaster.hdbsBookingDetails.any().apptDateTimeToActual.asc();
	}

}
