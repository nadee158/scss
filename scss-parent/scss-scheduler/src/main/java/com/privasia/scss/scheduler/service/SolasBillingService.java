/**
 * 
 */
package com.privasia.scss.scheduler.service;

import java.time.LocalDateTime;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.enums.BillingStatus;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.SolasInstructionType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.CommonSolasAttribute;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WDCTermWeighingDetail;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.scheduler.util.AppLogger;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("solasBillingService")
public class SolasBillingService {
	
	private final static AppLogger logger = AppLogger.getInstance();

	private ExportsRepository exportsRepository;

	private WDCBillingService wdcBillingService;

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setWdcBillingService(WDCBillingService wdcBillingService) {
		this.wdcBillingService = wdcBillingService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void fetchSolasBilling(LocalDateTime yesterday, LocalDateTime today) {
		Predicate timeGateInBetween = ExportsPredicates.timeGateInBetween(yesterday, today);
		Predicate byTransactionStatus = ExportsPredicates.byTransactionStatus(TransactionStatus.APPROVED);
		Predicate bySolasInstructionTerminal = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_TERMINAL);
		Predicate bySolasInstructionShipper = ExportsPredicates
				.bySolasInstructionType(SolasInstructionType.VGM_INSTRUCTION_SHIPPER);
		Predicate byWithinTolerance = ExportsPredicates.byWithinTolerance(false);
		Predicate byHpabBooking = ExportsPredicates.byHpabNotNull();
		Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
		OrderSpecifier<Long> orderByPrintEirAsc = ExportsPredicates.orderByPrintEirAsc();

		Predicate condition = ExpressionUtils.allOf(timeGateInBetween, byTransactionStatus,
				ExpressionUtils.or(bySolasInstructionTerminal, ExpressionUtils.and(bySolasInstructionShipper, byWithinTolerance)),
				byHpabBooking, byContainerFullOrEmpty);

		Iterable<Exports> exportsList = exportsRepository.findAll(condition, orderByPrintEirAsc);

		if (exportsList != null) {

			Iterator<Exports> expIterator = exportsList.iterator();
			logger.info("Results to execute " + expIterator.hasNext());
			while (expIterator.hasNext()) {
				Exports export = expIterator.next();
				logger.info("ExportID  : " + export.getExportID());
				populateTermWeighingDetail(export);

			}

		}
	}

	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void populateTermWeighingDetail(Exports export) {

		WDCTermWeighingDetail weighingDetail = new WDCTermWeighingDetail();
		weighingDetail.setBookingNo(export.getBookingNo());

		if (export.getBaseCommonGateInOutAttribute() != null) {
			weighingDetail.setTimeGateIn(export.getBaseCommonGateInOutAttribute().getTimeGateIn());
			weighingDetail.setHpabBooking(export.getBaseCommonGateInOutAttribute().getHpabBooking());

			if (export.getBaseCommonGateInOutAttribute().getGateInClerk() != null) {

				SystemUser systemUser = export.getBaseCommonGateInOutAttribute().getGateInClerk();
				weighingDetail.setAddBy(systemUser.getSystemUserID());

			}

			if (export.getBaseCommonGateInOutAttribute().getGateInClient() != null) {
				weighingDetail.setLocation(export.getBaseCommonGateInOutAttribute().getGateInClient().getUnitNo());
			}
		}

		if (export.getContainer() != null) {
			weighingDetail.setContainerNumber(export.getContainer().getContainerNumber());
			weighingDetail.setContainerISOCode(export.getContainer().getContainerISOCode());
		}

		weighingDetail.setStatus(BillingStatus.ACTIVE);
		weighingDetail.setInvoiceStatus(BillingStatus.ACTIVE);
		weighingDetail.setHpabBookingDate(null);

		if (export.getSolas() != null) {
			CommonSolasAttribute solas = export.getSolas();
			weighingDetail.setShipperVGM(solas.getShipperVGM());
			weighingDetail.setFaLedgerCode(solas.getFaLedgerCode());
			weighingDetail.setSolasRefNumber(solas.getSolasRefNumber());
			weighingDetail
					.setTerminalVGM(export.getExpNetWeight() == null ? null : String.valueOf(export.getExpNetWeight()));
			weighingDetail.setCalculatedVariance(export.getCalculatedVariance());
			weighingDetail.setSolasCertNo(export.getSolasCertNo());

		}

		weighingDetail.setUserRemarks(null);
		weighingDetail.setBackToback(export.getBackToback());
		weighingDetail.setShippingLine(export.getShippingLine());
		weighingDetail.setContainerPosition(export.getContainerPosition());
		weighingDetail.setVesselSCN(export.getVesselSCN());
		weighingDetail.setPrintEir(export.getPrintEir());
		wdcBillingService.saveBillingInfo(weighingDetail);

	}

}
