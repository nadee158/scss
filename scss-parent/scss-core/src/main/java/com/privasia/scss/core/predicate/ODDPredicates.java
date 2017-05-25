/**
 * 
 */
package com.privasia.scss.core.predicate;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.QWHODD;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Component
public final class ODDPredicates {

	private ODDPredicates() {
	}

	public static Predicate byPMPlateNo(String plateNo) {

		if (StringUtils.isEmpty(plateNo)) {
			return QWHODD.wHODD.pmPlateNo.isNull();
		} else {
			return QWHODD.wHODD.pmPlateNo.eq(StringUtils.upperCase(plateNo));
		}
	}

	public static Predicate byPMHeadNo(String headNo) {

		if (StringUtils.isEmpty(headNo)) {
			return QWHODD.wHODD.pmHeadNo.isNull();
		} else {
			return QWHODD.wHODD.pmHeadNo.eq(StringUtils.upperCase(headNo));
		}
	}

	public static Predicate byTransactionType(ImpExpFlagStatus impExp) {

		if (impExp == null) {
			return QWHODD.wHODD.impExpFlag.isNull();
		} else {
			return QWHODD.wHODD.impExpFlag.eq(impExp);
		}
	}

	public static Predicate byContainer01Status(TransactionStatus oddStatus) {

		if (oddStatus == null) {
			return QWHODD.wHODD.container01.oddStatus.isNull();
		} else {
			return QWHODD.wHODD.container01.oddStatus.eq(oddStatus);
		}
	}

	public static Predicate byContainer02Status(TransactionStatus oddStatus) {

		if (oddStatus == null) {
			return QWHODD.wHODD.container02.oddStatus.isNull();
		} else {
			return QWHODD.wHODD.container02.oddStatus.eq(oddStatus);
		}
	}

	public static Predicate byODDSeq(Long oddSeq) {

		if (oddSeq == null) {
			return QWHODD.wHODD.oddIdSeq.isNull();
		} else {
			return QWHODD.wHODD.oddIdSeq.eq(oddSeq);
		}
	}

	public static Predicate byGateInStatus(TransactionStatus gateInStatus) {
		if (gateInStatus == null) {
			return QWHODD.wHODD.gateInStatus.isNull();
		} else {
			return QWHODD.wHODD.gateInStatus.eq(gateInStatus);
		}
	}

	public static Predicate byWHoddIDList(List<Long> oddIDList) {
		if (oddIDList == null || oddIDList.isEmpty()) {
			return QWHODD.wHODD.oddIdSeq.isNull();
		} else {
			return QWHODD.wHODD.oddIdSeq.in(oddIDList);
		}
	}

}
