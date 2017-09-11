package com.privasia.scss.gateout.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.SolasApplicable;
import com.privasia.scss.common.dto.BoothTransactionInfo;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.InProgressTrxDTO;
import com.privasia.scss.common.dto.WHoddDTO;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.enums.TransactionType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.common.interfaces.TOSService;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.gateout.imports.exports.business.service.ImportExportCommonGateOutBusinessService;
import com.privasia.scss.gateout.whodd.service.ODDGateOutService;

@Service("importExportGateOutService")
public class ImportExportGateOutService {

	private static final Log log = LogFactory.getLog(ImportExportGateOutService.class);

	@Value("${async.wait.time}")
	private long asyncWaitTime;

	private ImportGateOutService importGateOutService;

	private ExportGateOutService exportGateOutService;

	private ODDGateOutService oddGateOutService;

	private CommonCardService commonCardService;

	private ClientRepository clientRepository;

	private CardRepository cardRepository;

	private SystemUserRepository systemUserRepository;

	private ImportExportCommonGateOutBusinessService importExportCommonGateOutBusinessService;

	private ContainerExternalDataService containerExternalDataService;

	private ModelMapper modelMapper;

	@Autowired
	public void setContainerExternalDataService(ContainerExternalDataService containerExternalDataService) {
		this.containerExternalDataService = containerExternalDataService;
	}

	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Autowired
	public void setImportGateOutService(ImportGateOutService importGateOutService) {
		this.importGateOutService = importGateOutService;
	}

	@Autowired
	public void setExportGateOutService(ExportGateOutService exportGateOutService) {
		this.exportGateOutService = exportGateOutService;
	}

	@Autowired
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Autowired
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Autowired
	public void setImportExportCommonGateOutBusinessService(
			ImportExportCommonGateOutBusinessService importExportCommonGateOutBusinessService) {
		this.importExportCommonGateOutBusinessService = importExportCommonGateOutBusinessService;
	}

	@Autowired
	public void setOddGateOutService(ODDGateOutService oddGateOutService) {
		this.oddGateOutService = oddGateOutService;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOut(GateOutRequest gateOutRequest) {

		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setGateOUTDateTime(gateOutRequest.getGateOUTDateTime());

		Optional<Card> cardOpt = cardRepository.findOne(gateOutRequest.getCardID());
		Card card = cardOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutRequest.getCardID()));
		gateOutRequest.setComID(card.getCompany().getCompanyID());

		gateOutRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

		Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getClientID());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getClientID()));
		if (StringUtils.isEmpty(client.getUnitNo()))
			throw new BusinessException("Unit no does not setup for client " + client.getClientID());
		gateOutRequest.setLaneNo(client.getUnitNo());

		InProgressTrxDTO trxDTO = commonCardService.isTrxInProgress(gateOutRequest.getCardID());

		if (trxDTO.isInProgress()) {

			List<ExportContainer> exportContainers = null;

			List<ImportContainer> importContainers = null;

			switch (trxDTO.getTrxType()) {
			case EXPORT:
				exportContainers = exportGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
				break;
			case IMPORT:
				importContainers = importGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
				break;
			case IMPORT_EXPORT:
				exportContainers = exportGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
				importContainers = importGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
				break;
			case ODD:
				gateOutReponse = oddGateOutService.populateGateOut(gateOutRequest, gateOutReponse);
				return gateOutReponse;
			default:
				break;
			}

			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			UserContext userContext = (UserContext) authentication.getPrincipal();
			gateOutRequest.setUserName(userContext.getUsername());

			gateOutReponse.setImportContainers(importContainers);
			gateOutReponse.setExportContainers(exportContainers);
			gateOutReponse.setTransactionType(trxDTO.getTrxType().name());

			// call opus or cosmos only for approved gate in state
			if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(), gateOutReponse.getGateInStatus())) {
				TOSService businessService = containerExternalDataService
						.getImplementationService(gateOutReponse.getTosIndicator());
				gateOutReponse = businessService.sendGateOutReadRequest(gateOutRequest, gateOutReponse);
			}

			return gateOutReponse;
		} else {
			throw new BusinessException(
					"No Valid Gate in Transaction Found for the Scan Card : " + gateOutRequest.getCardID());
		}

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateOutReponse saveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {

		Optional<Card> cardOpt = cardRepository.findOne(gateOutWriteRequest.getCardID());
		Card card = cardOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutWriteRequest.getCardID()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		log.info("userContext.getUsername() " + userContext.getUsername());
		gateOutWriteRequest.setUserName(userContext.getUsername());
		Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(userContext.getUserID());
		SystemUser user = systemUserOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Login User ! " + userContext.getUsername()));

		Optional<Client> clientOpt = clientRepository.findOne(gateOutWriteRequest.getGateOutClient());
		Client client = clientOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutWriteRequest.getGateOutClient()));
		if (StringUtils.isEmpty(client.getUnitNo()))
			throw new BusinessException("Unit no does not setup for client " + client.getClientID());
		gateOutWriteRequest.setLaneNo(client.getUnitNo());
		gateOutWriteRequest.setCosmosPort(client.getCosmosPortNo());

		Optional<Client> boothOpt = clientRepository.findOne(gateOutWriteRequest.getGateOutBooth());
		Client booth = boothOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Booth ID ! " + gateOutWriteRequest.getGateOutBooth()));

		gateOutWriteRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

		List<ImportContainer> importContainers = null;
		List<ExportContainer> exportContainers = null;

		if (StringUtils.isEmpty(gateOutWriteRequest.getImpExpFlag()))
			throw new BusinessException("Invalid GateOutWriteRequest Empty ImpExpFlag");
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateOutWriteRequest.getImpExpFlag());

		importExportCommonGateOutBusinessService.isValidGateOutLane(client, gateOutWriteRequest);
		importExportCommonGateOutBusinessService.checkContainerTobeReleasedByPort(client, gateOutWriteRequest);

		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setGateOUTDateTime(gateOutWriteRequest.getGateOUTDateTime());
		gateOutReponse.setImportContainers(importContainers);
		gateOutReponse.setExportContainers(exportContainers);

		if (StringUtils.isEmpty(gateOutWriteRequest.getGateInStatus()))
			throw new BusinessException(
					"Gate In status Required for the transaction : " + gateOutWriteRequest.getGateInStatus());

		if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(),
				gateOutWriteRequest.getGateInStatus())) {
			TOSService businessService = containerExternalDataService
					.getImplementationService(gateOutWriteRequest.getTosIndicator());
			gateOutReponse = businessService.sendGateOutWriteRequest(gateOutWriteRequest, gateOutReponse);
		}

		/*
		 * Future<Boolean> impSave = null; Future<Boolean> expSave = null;
		 */

		switch (impExpFlag) {
		case IMPORT:
			importGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
			// expSave = new AsyncResult<Boolean>(true);
			break;
		case EXPORT:
			// impSave = new AsyncResult<Boolean>(true);
			exportGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
			break;
		case IMPORT_EXPORT:
			importGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
			exportGateOutService.saveGateOutInfo(gateOutWriteRequest, client, user, booth);
			break;
		default:
			break;
		}

		GateOutMessage gateOutMessage = new GateOutMessage();
		gateOutMessage.setCode(GateOutMessage.OK);
		gateOutMessage.setDescription("Saved Successfully!");

		gateOutReponse.setMessage(gateOutMessage);
		return gateOutReponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateTransaction(BoothTransactionInfo boothTransactionInfo) {

		GateOutReponse gateOutReponse = new GateOutReponse();

		if (boothTransactionInfo.getGateoutClientID() == null || boothTransactionInfo.getGateoutClientID() == 0)
			throw new BusinessException("Please provide the gate out lane ID ");

		if (StringUtils.isEmpty(boothTransactionInfo.getTransactionType()))
			throw new BusinessException("Please provide the transaction type ");

		TransactionType trxType = TransactionType.fromCode(boothTransactionInfo.getTransactionType());
		List<Exports> exportsList = null;
		List<GatePass> gatePassList = null;
		List<WHODD> whODDList = null;
		switch (trxType) {
		case EXPORT:
			exportsList = exportGateOutService.populateSaveTransaction(boothTransactionInfo);
			populateGateOutResponseByExports(exportsList, gateOutReponse);
			break;
		case IMPORT:
			gatePassList = importGateOutService.populateSaveTransaction(boothTransactionInfo);
			populateGateOutResponseByImport(gatePassList, gateOutReponse);
			break;
		case IMPORT_EXPORT:
			exportsList = exportGateOutService.populateSaveTransaction(boothTransactionInfo);
			populateGateOutResponseByExports(exportsList, gateOutReponse);
			gatePassList = importGateOutService.populateSaveTransaction(boothTransactionInfo);
			populateGateOutResponseByImport(gatePassList, gateOutReponse);
			break;
		case ODD:
			whODDList = oddGateOutService.populateSaveTransaction(boothTransactionInfo);
			populateGateOutResponseByWHODD(whODDList, gateOutReponse);
			break;
		default:
			throw new BusinessException("Not a Valid Transaction type : " + boothTransactionInfo.getTransactionType());

		}

		return gateOutReponse;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOutResponseByExports(List<Exports> exportsList, GateOutReponse gateOutReponse) {

		Exports exportContainer = exportsList.get(0);
		gateOutReponse.setGateInDateTime(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn());
		gateOutReponse.setGateOUTDateTime(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateOut());
		gateOutReponse.setTruckHeadNo(exportContainer.getBaseCommonGateInOutAttribute().getPmHeadNo());
		gateOutReponse.setGateInStatus(exportContainer.getCommonGateInOut().getGateInStatus().getValue());
		gateOutReponse.setGateInLaneNo(exportContainer.getBaseCommonGateInOutAttribute().getGateInClient().getLaneNo());
		gateOutReponse.setTruckPlateNo(exportContainer.getBaseCommonGateInOutAttribute().getPmPlateNo());
		gateOutReponse.setClerkName(exportContainer.getBaseCommonGateInOutAttribute().getGateInClerk()
				.getCommonContactAttribute().getPersonName());

		ArrayList<ExportContainer> exportContainers = new ArrayList<>();

		exportsList.forEach(exports -> {
			exportContainers.add(modelMapper.map(exports, ExportContainer.class));

		});

		gateOutReponse.setExportContainers(exportContainers);

		return gateOutReponse;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOutResponseByImport(List<GatePass> gatePassList, GateOutReponse gateOutReponse) {

		GatePass importContainer = gatePassList.get(0);
		gateOutReponse.setGateInDateTime(importContainer.getBaseCommonGateInOutAttribute().getTimeGateIn());
		gateOutReponse.setGateOUTDateTime(importContainer.getBaseCommonGateInOutAttribute().getTimeGateOut());
		gateOutReponse.setTruckHeadNo(importContainer.getBaseCommonGateInOutAttribute().getPmHeadNo());
		gateOutReponse.setGateInStatus(importContainer.getCommonGateInOut().getGateInStatus().getValue());
		gateOutReponse.setGateInLaneNo(importContainer.getBaseCommonGateInOutAttribute().getGateInClient().getLaneNo());
		gateOutReponse.setTruckPlateNo(importContainer.getBaseCommonGateInOutAttribute().getPmPlateNo());
		gateOutReponse.setClerkName(importContainer.getBaseCommonGateInOutAttribute().getGateInClerk()
				.getCommonContactAttribute().getPersonName());

		ArrayList<ImportContainer> importContainers = new ArrayList<>();

		gatePassList.forEach(gatePass -> {
			importContainers.add(modelMapper.map(importContainers, ImportContainer.class));

		});

		gateOutReponse.setImportContainers(importContainers);

		return gateOutReponse;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOutResponseByWHODD(List<WHODD> oddList, GateOutReponse gateOutReponse) {

		WHODD whContainer = oddList.get(0);
		gateOutReponse.setGateInDateTime(whContainer.getTimeGateIn());
		gateOutReponse.setGateOUTDateTime(whContainer.getTimeGateOut());
		gateOutReponse.setTruckHeadNo(whContainer.getPmHeadNo());
		gateOutReponse.setGateInStatus(whContainer.getGateInStatus().getValue());
		gateOutReponse.setGateInLaneNo(whContainer.getGateInClient().getLaneNo());
		gateOutReponse.setTruckPlateNo(whContainer.getPmPlateNo());
		gateOutReponse.setClerkName(whContainer.getGateInClerk().getCommonContactAttribute().getPersonName());

		ArrayList<WHoddDTO> whODDContainers = new ArrayList<>();

		oddList.forEach(whODD -> {
			whODDContainers.add(modelMapper.map(whODD, WHoddDTO.class));

		});

		gateOutReponse.setWhODDContainers(whODDContainers);

		return gateOutReponse;

	}

	@SolasApplicable
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateOutReponse testSaveGateOutInfo(GateOutWriteRequest gateOutWriteRequest) {

		exportGateOutService.testSolas(gateOutWriteRequest);

		GateOutReponse gateOutReponse = new GateOutReponse();
		GateOutMessage gateOutMessage = new GateOutMessage();
		gateOutMessage.setCode(GateOutMessage.OK);
		gateOutMessage.setDescription("Saved Successfully!");

		gateOutReponse.setMessage(gateOutMessage);
		return gateOutReponse;

	}

}
