/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.QExports;
import com.querydsl.core.types.OrderSpecifier;
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

  public static Predicate timeGateInBetween(LocalDateTime startDate, LocalDateTime endDate) {
    if (startDate == null && endDate == null) {
      return QExports.exports.baseCommonGateInOutAttribute.timeGateIn.isNull();
    } else if (startDate == null) {
      return QExports.exports.baseCommonGateInOutAttribute.timeGateIn.loe(endDate);
    } else if (endDate == null) {
      return QExports.exports.baseCommonGateInOutAttribute.timeGateIn.goe(startDate);
    }
    return QExports.exports.baseCommonGateInOutAttribute.timeGateIn.between(startDate, endDate);
  }

  public static Predicate bySolasInstructionType(SolasInstructionType vgmInstructionTerminal) {
    if (vgmInstructionTerminal == null) {
      return QExports.exports.solas.solasInstruction.isNull();
    } else {
      return QExports.exports.solas.solasInstruction.eq(vgmInstructionTerminal);
    }
  }

  public static Predicate byWithinTolerance(boolean withinTolerance) {
    return QExports.exports.withinTolerance.eq(withinTolerance);
  }

  public static Predicate byHpabBooking(HPABBooking hpabBooking) {
    if (hpabBooking == null) {
      return QExports.exports.baseCommonGateInOutAttribute.hpabBooking.isNull();
    } else {
      return QExports.exports.baseCommonGateInOutAttribute.hpabBooking.bookingID.eq(hpabBooking.getBookingID());
    }
  }

  public static Predicate byContainerFullOrEmpty(ContainerFullEmptyType containerFullEmptyType) {
    if (containerFullEmptyType == null) {
      return QExports.exports.container.containerFullOrEmpty.isNull();
    } else {
      return QExports.exports.container.containerFullOrEmpty.eq(containerFullEmptyType);
    }
  }

  public static OrderSpecifier<Long> orderByPrintEirAsc() {
    return QExports.exports.printEir.printEIRID.asc();
  }

}
