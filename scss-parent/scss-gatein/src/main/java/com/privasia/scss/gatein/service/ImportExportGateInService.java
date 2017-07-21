package com.privasia.scss.gatein.service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.TOSServiceType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.common.interfaces.TOSService;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.HPABBookingRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.security.util.SecurityHelper;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.gatein.exports.business.service.SolasService;
import com.privasia.scss.hpat.service.HPABService;

@Service("importExportGateInService")
public class ImportExportGateInService {

	@Value("${async.wait.time}")
	private long asyncWaitTime;

	/*
	 * @Value("${service.implementor}") private String implementor;
	 */

	private static final Log log = LogFactory.getLog(ImportExportGateInService.class);

	private ImportGateInService importGateInService;

	private ExportGateInService exportGateInService;

	private ClientRepository clientRepository;

	private CardRepository cardRepository;

	private HPABService hpabService;

	private CommonCardService commonCardService;

	private SystemUserRepository systemUserRepository;

	private GateInReferService gateInReferService;

	private ContainerExternalDataService containerExternalDataService;

	private SolasService solasService;

	private HPABBookingRepository hpabBookingRepository;

	@Autowired
	public void setExternalContainerInformationService(ContainerExternalDataService containerExternalDataService) {
		this.containerExternalDataService = containerExternalDataService;
	}

	@Autowired
	public void setGateInReferService(GateInReferService gateInReferService) {
		this.gateInReferService = gateInReferService;
	}

	@Autowired
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Autowired
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	@Autowired
	public void setHpabService(HPABService hpabService) {
		this.hpabService = hpabService;
	}

	@Autowired
	public void setImportGateInService(ImportGateInService importGateInService) {
		this.importGateInService = importGateInService;
	}

	@Autowired
	public void setExportGateInService(ExportGateInService exportGateInService) {
		this.exportGateInService = exportGateInService;
	}

	@Autowired
	public void setClientRepository(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Autowired
	public void setSolasService(SolasService solasService) {
		this.solasService = solasService;
	}

	@Autowired
	public void setHpabBookingRepository(HPABBookingRepository hpabBookingRepository) {
		this.hpabBookingRepository = hpabBookingRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInResponse populateGateIn(GateInRequest gateInRequest) {

		Optional<Card> cardOpt = cardRepository.findOne(gateInRequest.getCardID());
		Card card = cardOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Card ID ! " + gateInRequest.getCardID()));

		gateInRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

		Optional<Client> clientOpt = clientRepository.findOne(gateInRequest.getClientID());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid client ID ! " + gateInRequest.getClientID()));
		if (StringUtils.isEmpty(client.getUnitNo()))
			throw new BusinessException("Unit no does not setup for client " + client.getClientID());
		gateInRequest.setLaneNo(client.getUnitNo());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		gateInRequest.setUserName(userContext.getUsername());

		GateInResponse gateInResponse = new GateInResponse();

		/*
		 * if the refer id avaliable then fetch here. then pass export container
		 * list
		 */
		// refere reject details
		if (gateInRequest.getReferID().isPresent()) {
			gateInResponse = gateInReferService.fetchReferDataForExport(gateInRequest.getReferID().get());
		}

		gateInResponse.setCheckPreArrival(gateInRequest.isCheckPreArrival());
		gateInResponse.setGateINDateTime(gateInRequest.getGateInDateTime());
		gateInResponse.setExpWeightBridge(gateInRequest.getExpWeightBridge());

		if ((gateInRequest.getGatePass1() != null && gateInRequest.getGatePass1() > 0)
				|| (gateInRequest.getGatePass2() != null && gateInRequest.getGatePass2() > 0)) {

			List<ImportContainer> importContainerList = importGateInService.populateGateInImport(gateInRequest);
			// GatePassValidationService call validate - within another method
			importGateInService.validateImport(gateInRequest, importContainerList);
			gateInResponse.setImportContainers(importContainerList);
		}

		/*TOSService businessService = containerExternalDataService.getImplementationService(implementor);
		businessService.sendGateInReadRequest(gateInRequest, gateInResponse);*/
		
		fetchTOSServiceDataForGateInRead(gateInRequest, gateInResponse);

		// assign details from hpab booking
		if ((StringUtils.isNotEmpty(gateInRequest.getHpabSeqId())) && (!(gateInRequest.getReferID().isPresent()))) {
			gateInResponse = hpabService.populateHpabForImpExp(gateInResponse, gateInRequest.getHpabSeqId());

			if (gateInResponse.getExportContainers() != null) {
				boolean fullExist = gateInResponse.getExportContainers().stream()
						.filter(expCon -> (StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
								expCon.getContainer().getContainerFullOrEmpty())))
						.findAny().isPresent();
				if (fullExist)
					solasService.calculateTerminalVGM(gateInResponse.getExportContainers(), false);
			}

		}

		if (!(gateInResponse.getExportContainers() == null || gateInResponse.getExportContainers().isEmpty())) {
			// set iso info if cosmos
			gateInResponse = exportGateInService.validateExportsGateInRead(gateInResponse,
					gateInRequest.getGateInDateTime());
		}
		gateInResponse.setLaneNo(client.getUnitNo());
		gateInResponse.setUserId(userContext.getStaffName());
		return gateInResponse;
	}

	public void fetchTOSServiceDataForGateInRead(GateInRequest gateInRequest, GateInResponse gateInResponse) {
		// assign details from hpab booking
		if (gateInRequest.getReferID().isPresent()) {

			TOSService businessService = containerExternalDataService
					.getImplementationService(gateInResponse.getTosIndicator());
			Future<GateInResponse> response = businessService.sendGateInReadRequest(gateInRequest, gateInResponse);

			while (true) {

				System.out.println("inside the loop getTOSServiceDataAtGateInRead");
				log.debug("inside the loop getTOSServiceDataAtGateInRead");

				if (response.isDone()) {

					try {
						response.get().setTosIndicator(gateInResponse.getTosIndicator());
						break;
					} catch (InterruptedException | ExecutionException | CancellationException e) {
						System.err.println(
								"Error Occured while retrieve data data from " + gateInResponse.getTosIndicator());
						System.err.println(e.getMessage());
						log.debug("Error Occured while retrieve data data from " + gateInResponse.getTosIndicator());
						log.debug(e.getMessage());
					}
				}

				try {
					System.out.println("waiting getTOSServiceDataAtGateInRead " + asyncWaitTime);
					log.debug("waiting getTOSServiceDataAtGateInRead " + asyncWaitTime);
					Thread.sleep(10);
				} catch (InterruptedException e) {
					log.error(e.getMessage());
					break;
				}

			}

		} else {
			// no refer id present - access simultaniously
			gateInResponse = accessAllTOStoFetchGateInReadRequest(gateInRequest, gateInResponse);

		}

	}

	public GateInResponse accessAllTOStoFetchGateInReadRequest(GateInRequest gateInRequest,
			GateInResponse gateInResponse) {
		long start = System.currentTimeMillis();

		TOSService cosmosBusinessService = containerExternalDataService
				.getImplementationService(TOSServiceType.COSMOS.getValue());
		TOSService opusBusinessService = containerExternalDataService
				.getImplementationService(TOSServiceType.OPUS.getValue());

		Future<GateInResponse> cosmosResponse = null;
		Future<GateInResponse> opusResponse = null;

		cosmosResponse = cosmosBusinessService.sendGateInReadRequest(gateInRequest, gateInResponse);

		opusResponse = opusBusinessService.sendGateInReadRequest(gateInRequest, gateInResponse);

		while (true) {
			System.out.println("inside the loop sendAllTOSServiceGateInReadRequest");
			log.debug("inside the loop sendAllTOSServiceGateInReadRequest");

			if (cosmosResponse.isDone() && opusResponse.isDone()) {

				System.out.println("now done sendAllTOSServiceGateInReadRequest");
				System.err.println("gateInResponse.getTosIndicator() before: " + gateInResponse.getTosIndicator());
				log.debug("now done sendAllTOSServiceGateInReadRequest");
				log.debug("gateInResponse.getTosIndicator() before: " + gateInResponse.getTosIndicator());
				try {
					gateInResponse = cosmosResponse.get();
					gateInResponse.setTosIndicator(TOSServiceType.COSMOS.getValue());
					System.err.println("COSMOS SUCCESS: ");
					log.debug("COSMOS SUCCESS: ");
				} catch (InterruptedException | ExecutionException | CancellationException e) {
					System.err.println("Error Occured while retrieve data data from cosmos");
					System.err.println(e.getMessage());
					log.debug("Error Occured while retrieve data data from cosmos");
					log.debug(e.getMessage());
				}

				try {
					gateInResponse = opusResponse.get();
					gateInResponse.setTosIndicator(TOSServiceType.OPUS.getValue());
					System.err.println("OPUS SUCCESS: ");
					log.debug("OPUS SUCCESS: ");
				} catch (InterruptedException | ExecutionException | CancellationException e) {
					System.err.println("Error Occured while retrieve data data from opus");
					System.err.println(e.getMessage());
					log.debug("Error Occured while retrieve data data from opus");
					log.debug(e.getMessage());
				}
				System.err.println("gateInResponse.getTosIndicator() after: " + gateInResponse.getTosIndicator());
				log.debug("gateInResponse.getTosIndicator() after: " + gateInResponse.getTosIndicator());

				if (StringUtils.isEmpty(gateInResponse.getTosIndicator())) {
					throw new BusinessException("Data not found in opus or cosmos!");
				}

				break;
			} else {
				System.out.println("not done yet sendAllTOSServiceGateInReadRequest");
				log.debug("not done yet sendAllTOSServiceGateInReadRequest");
			}

			try {
				System.out.println("waiting sendAllTOSServiceGateInReadRequest " + asyncWaitTime);
				log.debug("waiting sendAllTOSServiceGateInReadRequest " + asyncWaitTime);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				break;
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("time : " + (end - start) / 1000.0 + "sec");

		return gateInResponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateInResponse saveGateInInfo(GateInWriteRequest gateInWriteRequest) {

		if (StringUtils.isEmpty(gateInWriteRequest.getTosIndicator()))
			throw new BusinessException("Tos Indicator Not provided From Client Application");

		Card card = cardRepository.findOne(gateInWriteRequest.getCardID())
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Card : " + gateInWriteRequest.getCardID()));

		gateInWriteRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		gateInWriteRequest.setUserName(userContext.getUsername());

		Client gateInClient = clientRepository.findOne(gateInWriteRequest.getGateInClient()).orElseThrow(
				() -> new ResultsNotFoundException("Invalid Client Id : " + gateInWriteRequest.getGateInClient()));

		if (StringUtils.isEmpty(gateInClient.getUnitNo()))
			throw new BusinessException("Unit no does not setup for client " + gateInClient.getClientID());
		gateInWriteRequest.setLaneNo(gateInClient.getUnitNo());
		gateInWriteRequest.setCosmosPort(gateInClient.getCosmosPortNo());

		SystemUser gateInClerk = systemUserRepository.findOne(SecurityHelper.getCurrentUserId())
				.orElseThrow(() -> new AuthenticationServiceException(
						"Log in User Not Found : " + SecurityHelper.getCurrentUserId()));

		HPABBooking hpabBooking = null;

		if (StringUtils.isNotEmpty(gateInWriteRequest.getHpatBookingId())) {
			hpabBooking = hpabBookingRepository.findOne(gateInWriteRequest.getHpatBookingId())
					.orElseThrow(() -> new ResultsNotFoundException(
							"No HPAB Booking found ! : " + gateInWriteRequest.getHpatBookingId()));
		}

		/*
		 * Future<Boolean> impSave = null; Future<Boolean> expSave = null;
		 */

		if (StringUtils.isEmpty(gateInWriteRequest.getImpExpFlag()))
			throw new BusinessException("Invalid GateOutWriteRequest Empty ImpExpFlag");
		ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(gateInWriteRequest.getImpExpFlag());

		GateInResponse gateInResponse = null;

		System.out.println("gateInWriteRequest.getTosIndicator() " + gateInWriteRequest.getTosIndicator());

		if (StringUtils.isEmpty(gateInWriteRequest.getTosIndicator()))
			throw new BusinessException("Tos Indicator Not provided From Client Application");

		TOSService businessService = containerExternalDataService
				.getImplementationService(gateInWriteRequest.getTosIndicator());

		switch (impExpFlag) {
		case IMPORT:
			if (StringUtils.equalsIgnoreCase(gateInWriteRequest.getGateInStatus(),
					TransactionStatus.APPROVED.getValue())) {
				gateInResponse = businessService.sendGateInWriteRequest(gateInWriteRequest);
				gateInWriteRequest.setImportContainers(gateInResponse.getImportContainers());
			} else {
				gateInResponse = new GateInResponse();
				gateInResponse.setImportContainers(gateInWriteRequest.getImportContainers());
			}

			importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card, hpabBooking);
			// expSave = new AsyncResult<Boolean>(true);
			break;
		case EXPORT:
			// impSave = new AsyncResult<Boolean>(true);
			exportGateInService.validateExportsGateInWrite(gateInWriteRequest);
			if (StringUtils.equalsIgnoreCase(gateInWriteRequest.getGateInStatus(),
					TransactionStatus.APPROVED.getValue())) {
				gateInResponse = businessService.sendGateInWriteRequest(gateInWriteRequest);
				gateInWriteRequest.setExportContainers(gateInResponse.getExportContainers());
			} else {
				gateInResponse = new GateInResponse();
				gateInResponse.setExportContainers(gateInWriteRequest.getExportContainers());
			}
			exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card, hpabBooking);
			break;
		case IMPORT_EXPORT:
			exportGateInService.validateExportsGateInWrite(gateInWriteRequest);

			if (StringUtils.equalsIgnoreCase(gateInWriteRequest.getGateInStatus(),
					TransactionStatus.APPROVED.getValue())) {
				gateInResponse = businessService.sendGateInWriteRequest(gateInWriteRequest);
				gateInWriteRequest.setImportContainers(gateInResponse.getImportContainers());
				gateInWriteRequest.setExportContainers(gateInResponse.getExportContainers());
			} else {
				gateInResponse = new GateInResponse();
				gateInResponse.setImportContainers(gateInWriteRequest.getImportContainers());
				gateInResponse.setExportContainers(gateInWriteRequest.getExportContainers());
			}
			importGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card, hpabBooking);
			exportGateInService.saveGateInInfo(gateInWriteRequest, gateInClient, gateInClerk, card, hpabBooking);
			break;
		default:
			// impSave = new AsyncResult<Boolean>(true);
			// expSave = new AsyncResult<Boolean>(true);
			break;
		}

		if (StringUtils.isNotEmpty(gateInWriteRequest.getHpatBookingId())) {
			hpabService.updateHPABAfterGateIn(gateInWriteRequest.getHpatBookingId());
		}

		GateOutMessage gateOutMessage = new GateOutMessage();
		gateOutMessage.setCode(GateOutMessage.OK);
		gateOutMessage.setDescription("Saved Successfully!");

		/*
		 * while (true) { if (impSave.isDone() && expSave.isDone()) {
		 * 
		 * gateOutMessage.setCode(GateOutMessage.OK);
		 * gateOutMessage.setDescription( "Saved Successfully!");
		 * 
		 * System.out.println("WHILE LOOP BROKEN!!!!. "); break; }
		 * System.out.println( "Continue doing something else. ");
		 * 
		 * try { Thread.sleep(asyncWaitTime); } catch (InterruptedException e) {
		 * log.error(e.getMessage()); System.out.println(
		 * "WHILE LOOP BROKEN ON THREAD EXCEPTION!!!!. " ); break; } }
		 */
		gateInResponse.setGateINDateTime(gateInWriteRequest.getGateInDateTime());
		gateInResponse.setMessage(gateOutMessage);
		gateInResponse.setUserId(userContext.getStaffName());
		return gateInResponse;
	}

}
