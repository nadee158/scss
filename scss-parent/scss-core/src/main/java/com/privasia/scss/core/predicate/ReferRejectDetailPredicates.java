/**
 * 
 */
package com.privasia.scss.core.predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.core.model.QReferRejectDetail;
import com.querydsl.core.types.Predicate;

/**
 * @author nadeeshani
 *
 */
@Component
public final class ReferRejectDetailPredicates {

  private ReferRejectDetailPredicates() {}


  public static Predicate byReferRejectReferRejectID(Long referRejectID) {
    if (referRejectID == null) {
      return QReferRejectDetail.referRejectDetail.referReject.isNull();
    } else {
      return QReferRejectDetail.referRejectDetail.referReject.referRejectID.eq(referRejectID);
    }
  }


  public static Predicate byContainerNo(String containerNo) {
    if (StringUtils.isEmpty(containerNo)) {
      return QReferRejectDetail.referRejectDetail.containerNo.isNull();
    } else {
      return QReferRejectDetail.referRejectDetail.containerNo.eq(containerNo);
    }
  }


  public static Predicate byReferRejectStatusCode(HpabReferStatus status) {
    if (status == null) {
      return QReferRejectDetail.referRejectDetail.referReject.statusCode.isNull();
    } else {
      return QReferRejectDetail.referRejectDetail.referReject.statusCode.eq(status);
    }
  }

}
