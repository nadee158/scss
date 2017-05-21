/**
 * 
 */
package com.privasia.scss.core.predicate;

import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.QReferReject;
import com.querydsl.core.types.Predicate;

/**
 * @author nadeeshani
 *
 */
@Component
public final class ReferRejectPredicates {

  private ReferRejectPredicates() {}


  public static Predicate byReferRejectID(Long referRejectID) {
    if (referRejectID == null) {
      return QReferReject.referReject.referRejectID.isNull();
    } else {
      return QReferReject.referReject.referRejectID.eq(referRejectID);
    }
  }



  public static Predicate byStatusCode(HpabReferStatus status) {
    if (status == null) {
      return QReferReject.referReject.statusCode.isNull();
    } else {
      return QReferReject.referReject.statusCode.eq(status);
    }
  }

}
