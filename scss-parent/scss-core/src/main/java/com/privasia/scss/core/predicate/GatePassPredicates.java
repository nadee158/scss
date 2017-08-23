/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.QGatePass;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class GatePassPredicates {

	private GatePassPredicates() {
	}

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

	public static Predicate byContainerFullOrEmpty(ContainerFullEmptyType containerFullEmptyType) {
		if (containerFullEmptyType == null) {
			return QGatePass.gatePass.container.containerFullOrEmpty.isNull();
		} else {
			return QGatePass.gatePass.container.containerFullOrEmpty.eq(containerFullEmptyType);
		}
	}

	public static Predicate byGatePassIDList(List<Long> gatePassIDList) {
		if (gatePassIDList == null || gatePassIDList.isEmpty()) {
			return QGatePass.gatePass.gatePassID.isNull();
		} else {
			return QGatePass.gatePass.gatePassID.in(gatePassIDList);
		}
	}

	public static Predicate byEirStatus(TransactionStatus eirStatus) {
		if (eirStatus == null) {
			return QGatePass.gatePass.baseCommonGateInOutAttribute.eirStatus.isNull();
		} else {
			return QGatePass.gatePass.baseCommonGateInOutAttribute.eirStatus.eq(eirStatus);
		}
	}

	public static Predicate byCancelPickup(Boolean cancelPickup) {
		if (cancelPickup == null) {
			return QGatePass.gatePass.commonGateInOut.kioskCancelPickUp.isNull();
		} else {
			return QGatePass.gatePass.commonGateInOut.kioskCancelPickUp.eq(cancelPickup);
		}
	}
	
	public static Predicate byGateOutClient(Long gateoutClientID) {
		if (gateoutClientID == null || gateoutClientID == 0) {
			return QGatePass.gatePass.baseCommonGateInOutAttribute.gateOutClient.isNull();
		} else {
			return QGatePass.gatePass.baseCommonGateInOutAttribute.gateOutClient.clientID.eq(gateoutClientID);
		}
	}

}
