package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CustomsDTO;
import com.privasia.scss.common.dto.CustomsExportInfo;
import com.privasia.scss.common.dto.CustomsGatePassInfo;
import com.privasia.scss.common.dto.CustomsODDInfo;
import com.privasia.scss.common.dto.ODDContainerDetailsDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.LPS;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.CustomContainer;
import com.privasia.scss.core.model.Customs;
import com.privasia.scss.core.model.CustomsReport;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.predicate.GatePassPredicates;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.CustomsReportRepository;
import com.privasia.scss.core.repository.CustomsRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.GatePassRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.core.repository.WDCGatePassRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("customsService")
public class CustomsService {
	
	private static final Log log = LogFactory.getLog(CustomsService.class);

	private ExportsRepository exportsRepository;

	private GatePassRepository gatePassRepository;

	private ODDRepository oddRepository;

	private CustomsReportRepository customsReportRepository;

	private CustomsRepository customsRepository;

	private ClientRepository clientRepository;

	private WDCGatePassRepository wdcGatePassRepository;

	private ModelMapper modelMapper;

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Autowired
	public void setCustomsReportRepository(CustomsReportRepository customsReportRepository) {
		this.customsReportRepository = customsReportRepository;
	}

	@Autowired
	public void setCustomsRepository(CustomsRepository customsRepository) {
		this.customsRepository = customsRepository;
	}

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setGatePassRepository(GatePassRepository gatePassRepository) {
		this.gatePassRepository = gatePassRepository;
	}

	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}

	@Autowired
	public void setWdcGatePassRepository(WDCGatePassRepository wdcGatePassRepository) {
		this.wdcGatePassRepository = wdcGatePassRepository;
	}

	public String updateCustoms(CustomsDTO customsDTO) {

		if (StringUtils.isEmpty(customsDTO.getTransactionType()))
			throw new BusinessException("Transaction Type need to provided");

		TransactionType trxType = TransactionType.fromCode(customsDTO.getTransactionType());
		Customs custom = null;

		switch (trxType) {
		case IMPORT:

			if ((customsDTO.getImportContainer01Info() == null
					&& customsDTO.getImportContainer01Info().isPresent() == false)
					&& (customsDTO.getImportContainer02Info() == null
							&& customsDTO.getImportContainer02Info().isPresent() == false))
				throw new BusinessException("Import Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.IMPORT);
			updateCustomsImport(custom, customsDTO);
			return "success";
		case EXPORT:

			if ((customsDTO.getExportContainer01Info() == null
					&& customsDTO.getExportContainer01Info().isPresent() == false)
					&& (customsDTO.getExportContainer02Info() == null
							&& customsDTO.getExportContainer02Info().isPresent() == false))
				throw new BusinessException("Export Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.EXPORT);
			updateCustomsExport(custom, customsDTO);
			return "success";
		case IMPORT_EXPORT:

			if ((customsDTO.getImportContainer01Info() == null
					&& customsDTO.getImportContainer01Info().isPresent() == false)
					&& (customsDTO.getImportContainer02Info() == null
							&& customsDTO.getImportContainer02Info().isPresent() == false))
				throw new BusinessException("Import Information not provided");

			if ((customsDTO.getExportContainer01Info() == null
					&& customsDTO.getExportContainer01Info().isPresent() == false)
					&& (customsDTO.getExportContainer02Info() == null
							&& customsDTO.getExportContainer02Info().isPresent() == false))
				throw new BusinessException("Export Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.IMPORT);
			updateCustomsImport(custom, customsDTO);
			custom = constructCustoms(customsDTO, TransactionType.EXPORT);
			updateCustomsExport(custom, customsDTO);
			return "success";
		case ODD_IMPORT:

			if (customsDTO.getImportODDInfo() == null && customsDTO.getImportODDInfo().isPresent() == false)
				throw new BusinessException("ODD Import Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.IMPORT);
			updateCustomsODD(custom, customsDTO.getImportODDInfo().get());
			return "success";

		case ODD_EXPORT:

			if (customsDTO.getExportODDInfo() == null && customsDTO.getExportODDInfo().isPresent() == false)
				throw new BusinessException("ODD Export Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.EXPORT);
			updateCustomsODD(custom, customsDTO.getExportODDInfo().get());
			return "success";

		case ODD_IMPORT_EXPORT:

			if (customsDTO.getImportODDInfo() == null && customsDTO.getImportODDInfo().isPresent() == false)
				throw new BusinessException("ODD Import Information not provided");

			if (customsDTO.getExportODDInfo() == null && customsDTO.getExportODDInfo().isPresent() == false)
				throw new BusinessException("ODD Export Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.EXPORT);
			updateCustomsODD(custom, customsDTO.getExportODDInfo().get());
			custom = constructCustoms(customsDTO, TransactionType.IMPORT);
			updateCustomsODD(custom, customsDTO.getImportODDInfo().get());

			return "success";

		default:
			throw new BusinessException("No transaction type to update customs !");
		}

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public String updateCustomsODD(Customs customs, CustomsODDInfo customsODDInfo) {

		System.out.println("CustomsODDInfo : " + customsODDInfo.toString());

		Optional<WHODD> optionalODDContainer = oddRepository.findOne(customsODDInfo.getOddIdSeq());

		WHODD whODD = optionalODDContainer.orElseThrow(() -> new ResultsNotFoundException(
				"" + "Save customs failed. ODD Information not found ! " + customsODDInfo.getOddIdSeq()));

		customs.setWhODD(whODD);
		if (customsODDInfo.getContainer01() != null) {

			customs.setContainer01(constructCustomContainer(customsODDInfo.getContainer01(), whODD.getImpExpFlag(),
					whODD.getContainer01().getLocation().getOddCode()));
		} 
		
		if (whODD.getContainer02() != null) {

			customs.setContainer02(constructCustomContainer(customsODDInfo.getContainer02(), whODD.getImpExpFlag(),
					whODD.getContainer02().getLocation().getOddCode()));
		}

		customsRepository.save(customs);
		if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
			CustomsReport customsReport = new CustomsReport();
			modelMapper.map(customs, customsReport);
			customsReport.setCustomsReportID(customs.getCustomsID());
			customsReportRepository.save(customsReport);
		} else {
			throw new BusinessException("Save customs failed ! ");
		}

		return "success";
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public String updateCustomsImport(Customs customs, CustomsDTO customsDTO) {
		Predicate byContainerFullOrEmpty = GatePassPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);

		List<Long> gatePassIDList = new ArrayList<>();
		Map<Long, CustomsGatePassInfo> importCustoms = new HashMap<Long, CustomsGatePassInfo>();

		if (customsDTO.getImportContainer01Info() != null && customsDTO.getImportContainer01Info().isPresent()) {

			CustomsGatePassInfo containerInfo = customsDTO.getImportContainer01Info().get();

			if (containerInfo.getTrxStatus() == null
					|| StringUtils.equalsIgnoreCase(TransactionStatus.REJECT.getValue(), containerInfo.getTrxStatus()))
				throw new BusinessException(
						"Invalid Transaction status for Import transaction : " + containerInfo.getGatePassIDSeq());

			gatePassIDList.add(customsDTO.getImportContainer01Info().get().getGatePassIDSeq());
			importCustoms.put(customsDTO.getImportContainer01Info().get().getGatePassIDSeq(),
					customsDTO.getImportContainer01Info().get());
		}

		if (customsDTO.getImportContainer02Info() != null && customsDTO.getImportContainer02Info().isPresent()) {

			CustomsGatePassInfo containerInfo = customsDTO.getImportContainer02Info().get();

			if (containerInfo.getTrxStatus() == null
					|| StringUtils.equalsIgnoreCase(TransactionStatus.REJECT.getValue(), containerInfo.getTrxStatus()))
				throw new BusinessException(
						"Invalid Transaction status for Import transaction : " + containerInfo.getGatePassIDSeq());

			gatePassIDList.add(customsDTO.getImportContainer02Info().get().getGatePassIDSeq());
			importCustoms.put(customsDTO.getImportContainer02Info().get().getGatePassIDSeq(),
					customsDTO.getImportContainer02Info().get());
		}

		Predicate byGatePassIDList = GatePassPredicates.byGatePassIDList(gatePassIDList);

		Predicate condition = ExpressionUtils.allOf(byContainerFullOrEmpty, byGatePassIDList);
		Iterable<GatePass> gatePassList = gatePassRepository.findAll(condition);
		if ((gatePassList != null && gatePassList.iterator().hasNext())) {
			gatePassList.forEach(gatePass -> {
				CustomsGatePassInfo containerInfo = importCustoms.get(gatePass.getGatePassID());
				if (customs.getContainer01() == null) {
					customs.setContainer01(constructCustomContainer(containerInfo.getFullOrEmpty(), gatePass,
							containerInfo.getTrxStatus(), containerInfo.getRejectRemarks()));
				} else {
					customs.setContainer02(constructCustomContainer(containerInfo.getFullOrEmpty(), gatePass,
							containerInfo.getTrxStatus(), containerInfo.getRejectRemarks()));
				}
			});

			customsRepository.save(customs);
			if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
				CustomsReport customsReport = new CustomsReport();
				modelMapper.map(customs, customsReport);
				customsReport.setCustomsReportID(customs.getCustomsID());
				customsReportRepository.save(customsReport);
			} else {
				throw new BusinessException("Save customs failed ! ");
			}

		} else {
			throw new BusinessException("Save customs failed. Given Import data not valid ");
		}
		return "success";
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public String updateCustomsExport(Customs customs, CustomsDTO customsDTO) {

		Predicate byContainerFullOrEmpty = ExportsPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);

		List<Long> exportIDList = new ArrayList<>();
		Map<Long, CustomsExportInfo> exportCustoms = new HashMap<Long, CustomsExportInfo>();

		if (customsDTO.getExportContainer01Info() != null && customsDTO.getExportContainer01Info().isPresent()) {

			CustomsExportInfo containerInfo = customsDTO.getExportContainer01Info().get();

			if (containerInfo.getTrxStatus() == null || StringUtils
					.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(), containerInfo.getTrxStatus()))
				throw new BusinessException(
						"Invalid Transaction status for export transaction : " + containerInfo.getExportIDSeq());

			exportIDList.add(customsDTO.getExportContainer01Info().get().getExportIDSeq());
			exportCustoms.put(customsDTO.getExportContainer01Info().get().getExportIDSeq(),
					customsDTO.getExportContainer01Info().get());
		}

		if (customsDTO.getExportContainer02Info() != null && customsDTO.getExportContainer02Info().isPresent()) {

			CustomsExportInfo containerInfo = customsDTO.getExportContainer02Info().get();

			if (containerInfo.getTrxStatus() == null || StringUtils
					.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(), containerInfo.getTrxStatus()))
				throw new BusinessException(
						"Invalid Transaction status for export transaction : " + containerInfo.getExportIDSeq());

			exportIDList.add(customsDTO.getExportContainer02Info().get().getExportIDSeq());
			exportCustoms.put(customsDTO.getExportContainer02Info().get().getExportIDSeq(),
					customsDTO.getExportContainer02Info().get());
		}

		Predicate byExportsIDList = ExportsPredicates.byExportsIDList(exportIDList);

		Predicate condition = ExpressionUtils.allOf(byContainerFullOrEmpty, byExportsIDList);
		Iterable<Exports> exportsList = exportsRepository.findAll(condition);
		if ((exportsList != null && exportsList.iterator().hasNext())) {

			exportsList.forEach(exports -> {
				CustomsExportInfo containerInfo = exportCustoms.get(exports.getExportID());
				if (customs.getContainer01() == null) {
					customs.setContainer01(constructCustomContainer(containerInfo.getFullOrEmpty(), exports,
							containerInfo.getTrxStatus(), containerInfo.getRejectRemarks()));
				} else {
					customs.setContainer02(constructCustomContainer(containerInfo.getFullOrEmpty(), exports,
							containerInfo.getTrxStatus(), containerInfo.getRejectRemarks()));
				}
			});
			customsRepository.save(customs);
			if (!(customs.getCustomsID() == null || customs.getCustomsID() == 0)) {
				CustomsReport customsReport = new CustomsReport();
				modelMapper.map(customs, customsReport);
				customsReport.setCustomsReportID(customs.getCustomsID());
				customsReportRepository.save(customsReport);
			} else {
				throw new BusinessException("Save customs failed ! ");
			}
		} else {
			throw new BusinessException("Save customs failed. Given Export data not valid ");
		}
		return "success";
	}

	private CustomContainer constructCustomContainer(String fullOrEmpty, Exports exports, String transactionStatus,
			String rejectRemarks) {
		CustomContainer customContainer = new CustomContainer();
		customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(fullOrEmpty));
		customContainer.setContainerNumber(exports.getContainer().getContainerNumber());
		customContainer.setCustomEirStatus(TransactionStatus.fromCode(transactionStatus));
		customContainer.setCustomRejection(rejectRemarks);
		customContainer.setExport(exports);

		return customContainer;
	}

	private CustomContainer constructCustomContainer(String fullOrEmpty, GatePass gatePass, String transactionStatus,
			String rejectRemarks) {
		CustomContainer customContainer = new CustomContainer();
		customContainer.setContainerNumber(gatePass.getContainer().getContainerNumber());
		customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(fullOrEmpty));
		customContainer.setGatePass(gatePass);
		customContainer.setCustomRejection(rejectRemarks);
		customContainer.setCustomEirStatus(TransactionStatus.fromCode(transactionStatus));

		Optional<WDCGatePass> optionalWdcGatePass = wdcGatePassRepository.findByGatePassNO(gatePass.getGatePassNo());

		WDCGatePass wdcGatePass = optionalWdcGatePass.orElseThrow(() -> new ResultsNotFoundException(
				"GCS Declaration could be found for the given Gate Pass Numbers! " + gatePass.getGatePassNo()));

		customContainer.setGcsDelcarerNo(wdcGatePass.getGcsDelcarerNo());
		customContainer.setCusGCSReleaseDate(wdcGatePass.getCusGCSReleaseDate());
		customContainer.setPortPoliceDate(wdcGatePass.getDateTimeADD());
		customContainer.setGatePassIssuedDate(wdcGatePass.getDateTimeADD());

		return customContainer;
	}

	private CustomContainer constructCustomContainer(ODDContainerDetailsDTO oddContainerDetails, ImpExpFlagStatus impExpFlag, String location) {

		if (oddContainerDetails != null
				&& (StringUtils.equalsIgnoreCase(oddContainerDetails.getFullOrEmpty(), ContainerFullEmptyType.FULL.getValue()))) {

			if ((StringUtils.equalsIgnoreCase(impExpFlag.getValue(), ImpExpFlagStatus.EXPORT.getValue()))
					&& (StringUtils.equalsIgnoreCase(oddContainerDetails.getOddStatus(), TransactionStatus.APPROVED.getValue()))) {
				return null;
			}
			CustomContainer customContainer = new CustomContainer();
			customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(oddContainerDetails.getFullOrEmpty()));
			customContainer.setContainerNumber(oddContainerDetails.getContainerNo());
			customContainer.setCustomEirStatus(TransactionStatus.fromCode(oddContainerDetails.getOddStatus()));
			customContainer.setOddLocation(location);
			customContainer.setCustomRejection(oddContainerDetails.getRejectionReason());
			return customContainer;
		}
		return null;
	}

	public Customs constructCustoms(CustomsDTO customsDTO, TransactionType transactionType) {
		Customs customs = new Customs();
		customs.setCsmFlag(transactionType);
		Optional<Client> clientOpt = clientRepository.findOne(customsDTO.getGateOutClientId());
		Client client = clientOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid lane ID ! " + customsDTO.getGateOutClientId()));
		customs.setCsmGateOutClient(client);
		customs.setTimeGateOut(LocalDateTime.now());
		return customs;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void deleteCustomsByGateOutClientId(Long gateOutClientId) {
		if (gateOutClientId == null || gateOutClientId <= 0) {
			throw new BusinessException("Gate out client id is not available!");
		}
		customsRepository.deleteByClientID(gateOutClientId);
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String checkCustomStatus(Long clientID) {
		
		log.info("checkCustomStatus clientID "+clientID);
		
		String status = customsRepository.checkCustomStatus(clientID);
		log.info("checkCustomStatus status "+ status);
		if (StringUtils.isEmpty(status)) {
			status = LPS.RES_WAIT;
		}
		log.info("******* Response for Check Custom Status ******* " + status);
		return status;
	}
}
