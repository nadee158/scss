/**
 * 
 */
package com.privasia.scss.core.predicate;

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
	
	
	private KioskBoothRightsPredicates(){}
	
	
	public static Predicate KioskBoothStatus(String Status) {
        if (StringUtils.isEmpty(Status)) {
            return QKioskBoothRights.kioskBoothRights.isNull();
        }
        else {
            return QKioskBoothRights.kioskBoothRights.kioskLockStatus.eq(KioskLockStatus.valueOf(Status));
        }
	
	}
	
	public static Predicate KioskBoothInfoByKioskID(String kioskID) {
        if (StringUtils.isEmpty(kioskID)) {
            return QKioskBoothRights.kioskBoothRights.isNull();
        }
        else {
        	QClient client = QClient.client;
        	client.clientID.eq(Long.valueOf(kioskID));
            return QKioskBoothRights.kioskBoothRights.kioskBoothRightsID.kioskID.eq(client);
        }
	
	}
	
}
