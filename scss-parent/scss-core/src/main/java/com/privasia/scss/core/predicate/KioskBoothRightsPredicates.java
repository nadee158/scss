/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.privasia.scss.common.enums.KioskLockStatus;
import com.privasia.scss.core.model.QClient;
import com.privasia.scss.core.model.QKioskBoothRights;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
public final class KioskBoothRightsPredicates {

	private KioskBoothRightsPredicates() {
	}

	public static Predicate KioskBoothStatus(String Status) {
		if (StringUtils.isEmpty(Status)) {
			return QKioskBoothRights.kioskBoothRights.isNull();
		} else {
			return QKioskBoothRights.kioskBoothRights.kioskLockStatus.eq(KioskLockStatus.fromCode(Status));
		}
	}

	public static Predicate KioskBoothStatusIN(List<KioskLockStatus> statusList) {
		if (statusList == null || statusList.isEmpty()) {
			return QKioskBoothRights.kioskBoothRights.isNull();
		} else {
			return QKioskBoothRights.kioskBoothRights.kioskLockStatus.in(statusList);
		}
	}

	public static Predicate KioskBoothStatusNull() {
		return QKioskBoothRights.kioskBoothRights.kioskLockStatus.isNull();
	}

	public static Predicate KioskBoothInfoByKioskID(Long kioskID) {
		if (kioskID == null) {
			return QKioskBoothRights.kioskBoothRights.isNull();
		} else {
			return QKioskBoothRights.kioskBoothRights.kioskBoothRightsID.kiosk.clientID.eq(kioskID);
		}

	}

	public static Predicate KioskBoothInfoByBoothID(Long boothID) {
		if (boothID == null) {
			return QKioskBoothRights.kioskBoothRights.isNull();
		} else {
			QClient client = QClient.client;
			client.clientID.eq(boothID);
			return QKioskBoothRights.kioskBoothRights.kioskBoothRightsID.booth.clientID.eq(boothID);
		}
	}

	public static Predicate KioskBoothInfoByCardNumber(Integer cardNumber) {
		if (cardNumber == null) {
			return QKioskBoothRights.kioskBoothRights.isNull();
		} else {
			return QKioskBoothRights.kioskBoothRights.cardNumber.eq(cardNumber);
		}
	}

}
