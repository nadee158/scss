package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.common.dto.BoothTransactionInfo;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.gateout.dto.FileDTO;
import com.privasia.scss.hpat.service.HPABService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("exportGateOutService")
public class ExportGateOutService {

	private static final Log log = LogFactory.getLog(ExportGateOutService.class);

	private ModelMapper modelMapper;

	private ShipCodeRepository shipCodeRepository;

	private ExportsRepository exportsRepository;

	private HPABService hpabService;

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
		this.shipCodeRepository = shipCodeRepository;
	}

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setHpabService(HPABService hpabService) {
		this.hpabService = hpabService;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<ExportContainer> populateGateOut(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {

		Optional<List<Exports>> optExpList = exportsRepository.fetchInProgressTransaction(gateOutRequest.getCardID(),
				TransactionStatus.INPROGRESS);
		List<Exports> inprogressExpList = optExpList.orElseThrow(() -> new BusinessException(
				"No InProgress Export Transaction for the scan card ! " + gateOutRequest.getCardID()));

		List<ExportContainer> exportContainerList = new ArrayList<ExportContainer>();

		inprogressExpList.forEach(export -> {
			ExportContainer exportContainer = new ExportContainer();
			modelMapper.map(export, exportContainer);

			if (export.getBaseCommonGateInOutAttribute() != null) {

				if (!(export.getBaseCommonGateInOutAttribute().getTimeGateIn() == null)) {
					LocalDateTime timeGateIn = export.getBaseCommonGateInOutAttribute().getTimeGateIn();
					gateOutReponse.setGateInDateTime(timeGateIn);
				}
				if (export.getBaseCommonGateInOutAttribute().getGateInClerk() != null) {
					gateOutReponse.setClerkName(export.getBaseCommonGateInOutAttribute().getGateInClerk()
							.getCommonContactAttribute().getPersonName());
				}
				if (export.getBaseCommonGateInOutAttribute().getGateInClient() != null) {
					gateOutReponse
							.setGateInLaneNo(export.getBaseCommonGateInOutAttribute().getGateInClient().getLaneNo());
				}
			}
			// adding log info
			exportContainerList.add(exportContainer);
			if (StringUtils.isEmpty(gateOutRequest.getExpContainer1())) {
				gateOutRequest.setExpContainer1(export.getContainer().getContainerNumber());
			} else {
				gateOutRequest.setExpContainer2(export.getContainer().getContainerNumber());
			}
			gateOutRequest.setTruckHeadNo(export.getBaseCommonGateInOutAttribute().getPmHeadNo());
			gateOutReponse.setGateInStatus(export.getCommonGateInOut().getGateInStatus().getValue());
		});

		return exportContainerList;

	}

	public List<ShipCode> checkContainer(List<ExportContainer> exportContainers) {
		if (!(exportContainers == null || exportContainers.isEmpty())) {
			List<String> shippingCodes = exportContainers.stream().map(ExportContainer::getShipCode)
					.collect(Collectors.toList());

			Optional<List<ShipCode>> list = shipCodeRepository.findByShipStatusAndShippingCodeIn(ShipStatus.ACTIVE,
					shippingCodes);
			if (list.isPresent()) {
				List<ShipCode> codes = list.orElse(null);
				for (ShipCode shipCode : codes) {
					for (ExportContainer item : exportContainers) {
						if (StringUtils.equals(shipCode.getShippingCode(), item.getShipCode())) {
							item.setStoragePeriod(shipCode.getStoragePeriod());
						}
					}
				}

				return codes;
			}
		}
		return null;
	}

	@SolasApplicable
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest, Client gateOutClient, SystemUser gateOutClerk,
			Client booth) {

		if (gateOutWriteRequest.getExportContainers() == null || gateOutWriteRequest.getExportContainers().isEmpty())
			throw new BusinessException("Invalid Request to Update Export !");

		List<ExportContainer> exportContainers = gateOutWriteRequest.getExportContainers();

		exportContainers.forEach(exportContainer -> {
			Optional<Exports> optExport = exportsRepository.findOne(exportContainer.getExportID());
			Exports exports = optExport.orElseThrow(() -> new BusinessException(
					"Invalid Exports Information to Update ! " + exportContainer.getExportID()));

			if (StringUtils.isEmpty(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus()))
				throw new BusinessException("Invalid EIR Status for Exports : " + exportContainer.getExportID());

			exports.getBaseCommonGateInOutAttribute().setEirStatus(
					TransactionStatus.fromCode(exportContainer.getBaseCommonGateInOutAttribute().getEirStatus()));
			exports.getBaseCommonGateInOutAttribute().setTimeGateOut(gateOutWriteRequest.getGateOUTDateTime());
			exports.getBaseCommonGateInOutAttribute().setTimeGateOutOk(LocalDateTime.now());
			exports.getBaseCommonGateInOutAttribute().setGateOutBoothClerk(gateOutClerk);
			exports.getBaseCommonGateInOutAttribute().setGateOutBoothNo(String.valueOf(booth.getClientID()));
			exports.getBaseCommonGateInOutAttribute().setGateOutClerk(gateOutClerk);
			exports.getBaseCommonGateInOutAttribute().setGateOutClient(gateOutClient);
			exports.getCommonGateInOut().setRejectReason(exportContainer.getGateOutRemarks());

			exports = exportsRepository.save(exports);
			if (StringUtils.equalsIgnoreCase(exports.getBaseCommonGateInOutAttribute().getEirStatus().getValue(),
					TransactionStatus.REJECT.getValue())) {
				if (exports.getBaseCommonGateInOutAttribute().getHpabBooking() != null && StringUtils
						.isNotEmpty(exports.getBaseCommonGateInOutAttribute().getHpabBooking().getBookingID())) {
					hpabService.updateHPABAfterGateOut(
							exports.getBaseCommonGateInOutAttribute().getHpabBooking().getBookingID());
				}
			}

			// No need to update to Export_Q as the data is already deleted from
			// Export_Q by wdc scheduler and inserted into Export_Q2
			/*
			 * if(StringUtils.equalsIgnoreCase(exports.getCommonGateInOut().
			 * getGateInStatus().getValue(),
			 * TransactionStatus.APPROVED.getValue())){ Optional<ExportsQ>
			 * exportQOpt = exportsQRepository.findOne(exports.getExportID());
			 * ExportsQ exportq = exportQOpt.orElseThrow(() -> new
			 * ResultsNotFoundException(
			 * "Not valid Gate In ExportQ Process found ! " +
			 * exports.getExportID()));
			 * 
			 * modelMapper.map(exports, exportq);
			 * exportsQRepository.save(exportq); }
			 */

		});

		log.info("saveGateOutInfo completed ***************************");
		// return new AsyncResult<Boolean>(true);
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void testSolas(GateOutWriteRequest gateOutWriteRequest) {

		if (gateOutWriteRequest.getExportContainers() == null || gateOutWriteRequest.getExportContainers().isEmpty())
			throw new BusinessException("Invalid Request to Update Export !");

		List<ExportContainer> exportContainers = gateOutWriteRequest.getExportContainers();

		exportContainers.forEach(exportContainer -> {

			log.info("exportContainer ********************** " + exportContainer.getExportID());
			Optional<Exports> optExport = exportsRepository.findOne(exportContainer.getExportID());
			Exports exports = optExport.orElseThrow(() -> new BusinessException(
					"Invalid Exports Information to Update ! " + exportContainer.getExportID()));

			exports.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.APPROVED);
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			exportsRepository.save(exports);

		});
		// return new AsyncResult<Boolean>(true);

		log.info("testSolas method end first ********************** ");
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void updateExportReference(FileDTO fileDTO) {

		Optional<List<Exports>> exportsOptList = exportsRepository.findByExportIDIn(
				Arrays.asList(fileDTO.getExportNoSeq1().orElse(null), fileDTO.getExportNoSeq2().orElse(null)));
		if (exportsOptList.isPresent() && !exportsOptList.get().isEmpty()) {
			exportsOptList.get().forEach(exports -> {
				assignUpdatedValuesExports(exports, fileDTO);
				log.info("before save to exports ********************** " + fileDTO.getFileName().get());
				System.out.println("before save to exports ********************** " + fileDTO.getFileName().get());
				exportsRepository.save(exports);
			});
		} else {
			throw new BusinessException("Invalid Exports ID to update file reference : "
					+ fileDTO.getExportNoSeq1().orElse(null) + " / " + fileDTO.getExportNoSeq2().orElse(null));
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<Exports> populateSaveTransaction(BoothTransactionInfo boothTransactionInfo) {

		ArrayList<Long> exportIDList = new ArrayList<>();

		if (!(boothTransactionInfo.getExportID01() == null || boothTransactionInfo.getExportID01() == 0)) {
			exportIDList.add(boothTransactionInfo.getExportID01());
		}

		if (!(boothTransactionInfo.getExportID02() == null || boothTransactionInfo.getExportID02() == 0)) {
			exportIDList.add(boothTransactionInfo.getExportID02());
		}

		Predicate idListPredicate = ExportsPredicates.byExportsIDList(exportIDList);

		Predicate gateoutClientPredicate = ExportsPredicates.byGateOutClient(boothTransactionInfo.getGateoutClientID());

		Predicate condition = ExpressionUtils.allOf(idListPredicate, gateoutClientPredicate);

		Iterable<Exports> bookingList = exportsRepository.findAll(condition);

		Iterator<Exports> exports = bookingList.iterator();

		ArrayList<Exports> exportContainers = new ArrayList<>();

		if (exports != null && exports.hasNext()) {

			while (exports.hasNext()) {
				exportContainers.add(exports.next());
			}

		} else {
			throw new BusinessException("Provided Exports information not valid ");
		}

		return exportContainers;

	}

	private Exports assignUpdatedValuesExports(Exports exports, FileDTO fileDTO) {
		switch (fileDTO.getCollectionType()) {
		case PDF_FILE_COLLECTION:
			exports.getCommonGateInOut().setTrxSlipNo(fileDTO.getFileName().get());
			break;
		case ZIP_FILE_COLLECTION:
			exports.getCommonGateInOut().setZipFileNo(fileDTO.getFileName().get());
			break;
		case SOLAS_CERTIFICATE_COLLECTION:
			exports.setSolasCertNo(fileDTO.getFileName().get());
			break;
		default:
			break;
		}
		return exports;
	}

}
