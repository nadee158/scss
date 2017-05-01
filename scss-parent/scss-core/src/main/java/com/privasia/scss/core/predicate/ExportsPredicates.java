/**
 * 
 */
package com.privasia.scss.core.predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.QExports;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class ExportsPredicates {

  private ExportsPredicates() {}

  public static Predicate byPMPlateNo(String plateNo) {
    if (StringUtils.isEmpty(plateNo)) {
      return QExports.exports.baseCommonGateInOutAttribute.pmPlateNo.isNull();
    } else {
      return QExports.exports.baseCommonGateInOutAttribute.pmPlateNo.eq(StringUtils.upperCase(plateNo));
    }
  }

  public static Predicate byPMHeadNo(String headNo) {
    if (StringUtils.isEmpty(headNo)) {
      return QExports.exports.baseCommonGateInOutAttribute.pmHeadNo.isNull();
    } else {
      return QExports.exports.baseCommonGateInOutAttribute.pmHeadNo.eq(StringUtils.upperCase(headNo));
    }
  }

  public static Predicate byTransactionStatus(TransactionStatus eirStatus) {
    if (eirStatus == null) {
      return QExports.exports.baseCommonGateInOutAttribute.eirStatus.isNull();
    } else {
      return QExports.exports.baseCommonGateInOutAttribute.eirStatus.eq(eirStatus);
    }
  }



}
