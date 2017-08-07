package com.privasia.scss.gateout.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.CustomsDTO;
import com.privasia.scss.common.dto.CustomsExportInfo;
import com.privasia.scss.common.dto.CustomsGatePassInfo;
import com.privasia.scss.common.dto.CustomsODDInfo;
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
import com.privasia.scss.core.model.ODDContainerDetails;
import com.privasia.scss.core.model.WDCGatePass;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.predicate.GatePassPredicates;
import com.privasia.scss.core.predicate.ODDPredicates;
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
			
			if ((customsDTO.getImportContainer01Info() == null && customsDTO.getImportContainer01Info().isPresent() == false) &&
					(customsDTO.getImportContainer02Info() == null && customsDTO.getImportContainer02Info().isPresent() == false))
				throw new BusinessException("Import Information not provided");

			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.IMPORT);
			updateCustomsImport(custom, customsDTO);
			return "success";
		case EXPORT:

			if ((customsDTO.getExportContainer01Info() == null && customsDTO.getExportContainer01Info().isPresent() == false) &&
					(customsDTO.getExportContainer02Info() == null && customsDTO.getExportContainer02Info().isPresent() == false))
				throw new BusinessException("Export Information not provided");
			
			deleteCustomsByGateOutClientId(customsDTO.getGateOutClientId());
			custom = constructCustoms(customsDTO, TransactionType.EXPORT);
			updateCustomsExport(custom, customsDTO);
			return "success";
		case IMPORT_EXPORT:
			
			if ((customsDTO.getImportContainer01Info() == null && customsDTO.getImportContainer01Info().isPresent() == false) &&
					(customsDTO.getImportContainer02Info() == null && customsDTO.getImportContainer02Info().isPresent() == false))
				throw new BusinessException("Import Information not provided");

			if ((customsDTO.getExportContainer01Info() == null && customsDTO.getExportContainer01Info().isPresent() == false) &&
					(customsDTO.getExportContainer02Info() == null && customsDTO.getExportContainer02Info().isPresent() == false))
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
		
		System.out.println("CustomsODDInfo : "+customsODDInfo.toString());

		Predicate byGateInStatus = ODDPredicates.byGateInStatus(TransactionStatus.APPROVED);

		Predicate byWHoddIDList = ODDPredicates.byODDSeq(customsODDInfo.getOddIdSeq());

		Predicate condition = ExpressionUtils.allOf(byGateInStatus, byWHoddIDList);
		Iterable<WHODD> whODDList = oddRepository.findAll(condition);
		if ((whODDList != null && whODDList.iterator().hasNext())) {
			System.out.println("whODDList.iterator().hasNext() : "+whODDList.iterator().hasNext());
			whODDList.forEach(whODD -> {
				customs.setWhODD(whODD);
				if (customs.getContainer01() == null) {
					
					System.out.println("customsODDInfo.getFullOREmptyCon01().get() : "+customsODDInfo.getFullOrEmptyCon01());
					
					customs.setContainer01(constructCustomContainer(customsODDInfo.getFullOrEmptyCon01(),
							whODD.getContainer01(), whODD.getImpExpFlag(), customsODDInfo.getTrxStatusCon01()));
				} else {
					
					System.out.println("else customsODDInfo.getFullOREmptyCon01().get() : "+customsODDInfo.getFullOrEmptyCon01());
					System.out.println("else customsODDInfo.getFullOREmptyCon01().get() : "+customsODDInfo.getFullOrEmptyCon02());
					
					customs.setContainer02(constructCustomContainer(customsODDInfo.getFullOrEmptyCon02(),
							whODD.getContainer02(), whODD.getImpExpFlag(), customsODDInfo.getTrxStatusCon02()));
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
			});
		} else {
			throw new BusinessException("Save customs failed. Given data not valid ");
		}

		return null;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public String updateCustomsImport(Customs customs, CustomsDTO customsDTO) {
		Predicate byContainerFullOrEmpty = GatePassPredicates.byContainerFullOrEmpty(ContainerFullEmptyType.FULL);
		Predicate byCancelPickup = GatePassPredicates.byCancelPickup(false);
		
		List<Long> gatePassIDList = new ArrayList<>();
		Map<Long,CustomsGatePassInfo> importCustoms = new HashMap<Long,CustomsGatePassInfo>();  
		
		if(customsDTO.getImportContainer01Info() != null && customsDTO.getImportContainer01Info().isPresent()){
			gatePassIDList.add(customsDTO.getImportContainer01Info().get().getGatePassIDSeq());
			importCustoms.put(customsDTO.getImportContainer01Info().get().getGatePassIDSeq(), customsDTO.getImportContainer01Info().get());
		}
		
		if(customsDTO.getImportContainer02Info() != null && customsDTO.getImportContainer02Info().isPresent()){
			gatePassIDList.add(customsDTO.getImportContainer02Info().get().getGatePassIDSeq());
			importCustoms.put(customsDTO.getImportContainer02Info().get().getGatePassIDSeq(), customsDTO.getImportContainer02Info().get());
		}
		
		Predicate byGatePassIDList = GatePassPredicates.byGatePassIDList(gatePassIDList);

		Predicate condition = ExpressionUtils.allOf(byContainerFullOrEmpty, byCancelPickup, byGatePassIDList);
		Iterable<GatePass> gatePassList = gatePassRepository.findAll(condition);
		if ((gatePassList != null && gatePassList.iterator().hasNext())) {
			gatePassList.forEach(gatePass -> {
				CustomsGatePassInfo containerInfo = importCustoms.get(gatePass.getGatePassID());
				if (customs.getContainer01() == null) {
					customs.setContainer01(constructCustomContainer(containerInfo.getFullOrEmpty(), gatePass,
							containerInfo.getTrxStatus()));
				} else {
					customs.setContainer02(constructCustomContainer(containerInfo.getFullOrEmpty(), gatePass,
							containerInfo.getTrxStatus()));
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
		Predicate byGateInStatus = ExportsPredicates.byEirStatus(TransactionStatus.REJECT);
		
		List<Long> exportIDList = new ArrayList<>();
		Map<Long, CustomsExportInfo> exportCustoms = new HashMap<Long,CustomsExportInfo>();  
		
		if(customsDTO.getExportContainer01Info() != null && customsDTO.getExportContainer01Info().isPresent()){
			exportIDList.add(customsDTO.getExportContainer01Info().get().getExportIDSeq());
			exportCustoms.put(customsDTO.getExportContainer01Info().get().getExportIDSeq(), customsDTO.getExportContainer01Info().get());
		}
		
		if(customsDTO.getExportContainer02Info() != null && customsDTO.getExportContainer02Info().isPresent()){
			exportIDList.add(customsDTO.getExportContainer02Info().get().getExportIDSeq());
			exportCustoms.put(customsDTO.getExportContainer02Info().get().getExportIDSeq(), customsDTO.getExportContainer02Info().get());
		}
		
		Predicate byExportsIDList = ExportsPredicates.byExportsIDList(exportIDList);

		Predicate condition = ExpressionUtils.allOf(byContainerFullOrEmpty, byGateInStatus, byExportsIDList);
		Iterable<Exports> exportsList = exportsRepository.findAll(condition);
		if ((exportsList != null && exportsList.iterator().hasNext())) {
			
			exportsList.forEach(exports -> {
				CustomsExportInfo containerInfo = exportCustoms.get(exports.getExportID());
				if (customs.getContainer01() == null) {
					customs.setContainer01(constructCustomContainer(containerInfo.getFullOrEmpty(), exports,
							containerInfo.getTrxStatus()));
				} else {
					customs.setContainer02(constructCustomContainer(containerInfo.getFullOrEmpty(), exports,
							containerInfo.getTrxStatus()));
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

	private CustomContainer constructCustomContainer(String fullOrEmpty, Exports exports, String transactionStatus) {
		CustomContainer customContainer = new CustomContainer();
		customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(fullOrEmpty));
		customContainer.setContainerNumber(exports.getContainer().getContainerNumber());
		customContainer.setCustomEirStatus(TransactionStatus.fromCode(transactionStatus));
		customContainer.setCustomRejection(exports.getCommonGateInOut().getRejectReason());
		customContainer.setExport(exports);

		return customContainer;
	}

	private CustomContainer constructCustomContainer(String fullOrEmpty, GatePass gatePass, String transactionStatus) {
		CustomContainer customContainer = new CustomContainer();
		customContainer.setContainerNumber(gatePass.getContainer().getContainerNumber());
		customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(fullOrEmpty));
		customContainer.setGatePass(gatePass);
		customContainer.setCustomRejection(gatePass.getCommonGateInOut().getRejectReason());
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

	private CustomContainer constructCustomContainer(String fullOrEmpty, ODDContainerDetails oddContainerDetails,
			ImpExpFlagStatus impExpFlag, String transactionStatus) {

		if (oddContainerDetails != null
				&& (StringUtils.equalsIgnoreCase(fullOrEmpty, ContainerFullEmptyType.FULL.getValue()))) {

			if ((StringUtils.equalsIgnoreCase(impExpFlag.getValue(), ImpExpFlagStatus.EXPORT.getValue()))
					&& (StringUtils.equalsIgnoreCase(transactionStatus, TransactionStatus.APPROVED.getValue()))) {
				return null;
			}
			CustomContainer customContainer = new CustomContainer();
			customContainer.setContainerFullOrEmpty(ContainerFullEmptyType.fromValue(fullOrEmpty));
			customContainer.setContainerNumber(oddContainerDetails.getContainerNo());
			customContainer.setCustomEirStatus(TransactionStatus.fromCode(transactionStatus));
			customContainer.setOddLocation(oddContainerDetails.getLocation().getOddCode());
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
		String status = customsRepository.checkCustomStatus(clientID);
		if (StringUtils.isEmpty(status)) {
			status = LPS.RES_WAIT;
		}
		System.out.println("******* Response for Check Custom Status ******* " + status);
		return status;
	}
}
