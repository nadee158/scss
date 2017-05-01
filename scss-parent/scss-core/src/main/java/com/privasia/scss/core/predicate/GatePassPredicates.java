/**
 * 
 */
package com.privasia.scss.core.predicate;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.QGatePass;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class GatePassPredicates {

  private GatePassPredicates() {}

  public static Predicate byPMPlateNo(String plateNo) {
    if (StringUtils.isEmpty(plateNo)) {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.pmPlateNo.isNull();
    } else {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.pmPlateNo.eq(StringUtils.upperCase(plateNo));
    }
  }

  public static Predicate byPMHeadNo(String headNo) {
    if (StringUtils.isEmpty(headNo)) {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.pmHeadNo.isNull();
    } else {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.pmHeadNo.eq(StringUtils.upperCase(headNo));
    }
  }

  public static Predicate byTransactionStatus(TransactionStatus eirStatus) {
    if (eirStatus == null) {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.eirStatus.isNull();
    } else {
      return QGatePass.gatePass.baseCommonGateInOutAttribute.eirStatus.eq(eirStatus);
    }
  }



}
