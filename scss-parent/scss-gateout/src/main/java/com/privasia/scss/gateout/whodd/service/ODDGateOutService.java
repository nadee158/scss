package com.privasia.scss.gateout.whodd.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

import com.privasia.scss.common.dto.BoothTransactionInfo;
import com.privasia.scss.common.dto.ContainerValidationInfo;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.dto.GateOutReponse;
import com.privasia.scss.common.dto.GateOutRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ODDContainerDetailsDTO;
import com.privasia.scss.common.dto.ODDLocationDTO;
import com.privasia.scss.common.dto.WHoddDTO;
import com.privasia.scss.common.enums.ClientType;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;
import com.privasia.scss.common.enums.TOSServiceType;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.interfaces.ContainerExternalDataService;
import com.privasia.scss.common.interfaces.TOSService;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.ODDContainerDetails;
import com.privasia.scss.core.model.ODDExportReason;
import com.privasia.scss.core.model.ODDImportReason;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.model.WHODD;
import com.privasia.scss.core.predicate.ODDPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.ODDExportReasonRepository;
import com.privasia.scss.core.repository.ODDImportReasonRepository;
import com.privasia.scss.core.repository.ODDLocationRepository;
import com.privasia.scss.core.repository.ODDRepository;
import com.privasia.scss.core.repository.SystemUserRepository;
import com.privasia.scss.core.service.CommonCardService;
import com.privasia.scss.gateout.dto.FileDTO;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("oddGateOutService")
public class ODDGateOutService {
	
	private static final Log log = LogFactory.getLog(ODDGateOutService.class);
	
	@Value("${async.wait.time}")
	private long asyncWaitTime;
	
	private ODDRepository oddRepository;

	private ODDLocationRepository oddLocationRepository;

	private ModelMapper modelMapper;

	private HDBSBookingDetailRepository hdbsBookingDetailRepository;

	private ClientRepository clientRepository;

	private CardRepository cardRepository;

	private SystemUserRepository systemUserRepository;

	private ODDImportReasonRepository oddImportReasonRepository;

	private ODDExportReasonRepository oddExportReasonRepository;

	private ContainerExternalDataService containerExternalDataService;

	private CommonCardService commonCardService;

	@Autowired
	public void setContainerExternalDataService(ContainerExternalDataService containerExternalDataService) {
		this.containerExternalDataService = containerExternalDataService;
	}

	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}

	@Autowired
	public void setOddLocationRepository(ODDLocationRepository oddLocationRepository) {
		this.oddLocationRepository = oddLocationRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
		this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
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
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
	}

	@Autowired
	public void setOddImportReasonRepository(ODDImportReasonRepository oddImportReasonRepository) {
		this.oddImportReasonRepository = oddImportReasonRepository;
	}

	@Autowired
	public void setOddExportReasonRepository(ODDExportReasonRepository oddExportReasonRepository) {
		this.oddExportReasonRepository = oddExportReasonRepository;
	}

	@Autowired
	public void setCommonCardService(CommonCardService commonCardService) {
		this.commonCardService = commonCardService;
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true, value = "as400TransactionManager")
	public ContainerValidationInfo validateODDContainers(GateOutRequest gateOutRequest) {

		Optional<Card> cardOpt = cardRepository.findOne(gateOutRequest.getCardID()); 
		Card card = cardOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateOutRequest.getCardID()));
		gateOutRequest.setComID(card.getCompany().getCompanyID());

		gateOutRequest.setHaulageCode(commonCardService.getHaulierCodeByScanCard(card));

		Optional<Client> clientOpt = clientRepository.findOne(gateOutRequest.getClientID());
		Client client = clientOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutRequest.getClientID()));
		if (StringUtils.isEmpty(client.getLaneNo()))
			throw new BusinessException("Lane no does not setup for client " + client.getClientID());
		gateOutRequest.setLaneNo(client.getLaneNo());

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		gateOutRequest.setUserName(userContext.getUsername());

		ContainerValidationInfo validateInfo = accessAllTOStoODDContainerValidation(gateOutRequest);

		return validateInfo;
	}
	
	public ContainerValidationInfo accessAllTOStoODDContainerValidation(GateOutRequest gateOutRequest) {
		long start = System.currentTimeMillis();

		TOSService cosmosBusinessService = containerExternalDataService.getImplementationService(TOSServiceType.COSMOS.getValue());
		TOSService opusBusinessService = containerExternalDataService.getImplementationService(TOSServiceType.OPUS.getValue());

		Future<ContainerValidationInfo> cosmosResponse = null;
		Future<ContainerValidationInfo> opusResponse = null;
		boolean opus = false;
		boolean cosmos = false;

		cosmosResponse = cosmosBusinessService.sendODDContainerValidationRequest(gateOutRequest);

		opusResponse = opusBusinessService.sendODDContainerValidationRequest(gateOutRequest);
		
		ContainerValidationInfo validateInfo = null;

		while (true) {
			log.debug("inside the loop sendAllTOSServiceGateInReadRequest");

			if (cosmosResponse.isDone() && opusResponse.isDone()) {

				log.debug("now done sendAllTOSServiceGateInReadRequest");
	
				try {
					validateInfo = cosmosResponse.get();
					
					if(validateInfo.isContainerNo1Status() || validateInfo.isContainerNo2Status()){
						cosmos = true;
					}
					
					log.info("COSMOS SUCCESS: ");
				} catch (InterruptedException | ExecutionException | CancellationException e) {
					log.debug("Error Occured while retrieve data data from cosmos");
					log.debug(e.getMessage());
				}

				try {
					validateInfo = opusResponse.get();
					opus = true;
					log.info("OPUS SUCCESS: ");
				} catch (InterruptedException | ExecutionException | CancellationException e) {
					log.debug("Error Occured while retrieve data data from opus");
					log.debug(e.getMessage());
				}
				
				
				
				if(opus && cosmos){
					throw new BusinessException("Data found in both TOS services. opus and cosmos !");
				}else if(opus){
					validateInfo.setTosIndicator(TOSServiceType.OPUS.getValue());
				}else if(cosmos){
					validateInfo.setTosIndicator(TOSServiceType.COSMOS.getValue());
				}else {
					log.info("ODD container not found in TOS services : "+gateOutRequest.getExpContainer1()+" / : "+gateOutRequest.getExpContainer2()+ 
							gateOutRequest.getImpContainer1()+" / : "+gateOutRequest.getImpContainer2());
					throw new BusinessException("Data not found in opus or cosmos!");
				}
			

				break;
			} else {
				
				log.debug("not done yet accessAllTOStoODDContainerValidation");
			}

			try {
				
				log.debug("waiting accessAllTOStoODDContainerValidation " + asyncWaitTime);
				Thread.sleep(10);
			} catch (InterruptedException e) {
				log.error(e.getMessage());
				break;
			}
		}
		long end = System.currentTimeMillis();
		log.info("time : " + (end - start) / 1000.0 + "sec");
		return validateInfo;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateOutReponse populateGateOut(GateOutRequest gateOutRequest, GateOutReponse gateOutReponse) {

		Optional<List<WHODD>> optionalODDList = oddRepository.findByCardIDAndEirStatus(gateOutRequest.getCardID(),
				TransactionStatus.INPROGRESS);

		if (optionalODDList.isPresent()) {
			List<WHoddDTO> whODDContainers = new ArrayList<WHoddDTO>();
			List<WHODD> transactionList = optionalODDList.get();

			transactionList.forEach(whODD -> {
				WHoddDTO oddDTO = modelMapper.map(whODD, WHoddDTO.class);
				if (oddDTO.getContainer01() != null) {
					Optional<ODDLocation> optionalLocation = oddLocationRepository
							.findOne(oddDTO.getContainer01().getLocation().getOddCode());
					if (optionalLocation.isPresent()) {
						ODDLocationDTO locationDTO = modelMapper.map(optionalLocation.get(), ODDLocationDTO.class);
						oddDTO.getContainer01().setLocation(locationDTO);
					}

				}

				if (oddDTO.getContainer02() != null) {
					Optional<ODDLocation> optionalLocation = oddLocationRepository
							.findOne(oddDTO.getContainer02().getLocation().getOddCode());
					if (optionalLocation.isPresent()) {
						ODDLocationDTO locationDTO = modelMapper.map(optionalLocation.get(), ODDLocationDTO.class);
						oddDTO.getContainer02().setLocation(locationDTO);
					}
				}
				whODDContainers.add(oddDTO);
			});

			Optional<WHODD> optElement = optionalODDList.get().stream().findAny();
			gateOutReponse.setWhODDContainers(whODDContainers);
			gateOutReponse.setGateOUTDateTime(gateOutRequest.getGateOUTDateTime());
			if (optElement.isPresent()) {
				gateOutReponse.setGateInDateTime(optElement.get().getTimeGateIn());
				gateOutReponse.setTruckPlateNo(optElement.get().getPmPlateNo());
				gateOutReponse.setTruckHeadNo(optElement.get().getPmHeadNo());
			}

			boolean imports = optionalODDList.get().stream().filter(whODD -> StringUtils
					.equalsIgnoreCase(whODD.getImpExpFlag().getValue(), ImpExpFlagStatus.IMPORT.getValue())).findAny()
					.isPresent();

			boolean exports = optionalODDList.get().stream().filter(whODD -> StringUtils
					.equalsIgnoreCase(whODD.getImpExpFlag().getValue(), ImpExpFlagStatus.EXPORT.getValue())).findAny()
					.isPresent();

			if (imports && exports) {
				gateOutReponse.setTransactionType(ImpExpFlagStatus.IMPORT_EXPORT.getValue());
			} else if (imports) {
				gateOutReponse.setTransactionType(ImpExpFlagStatus.IMPORT.getValue());
			} else if (exports) {
				gateOutReponse.setTransactionType(ImpExpFlagStatus.EXPORT.getValue());
			}

		}

		return gateOutReponse;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateOutReponse saveODDGateOutInFo(GateOutWriteRequest gateOutWriteRequest) {

		if (gateOutWriteRequest.getWhoddContainers() == null || gateOutWriteRequest.getWhoddContainers().isEmpty())
			throw new BusinessException("Invalid GateOutWriteRequest to save ODD ! ");

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();
		Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(userContext.getUserID());
		SystemUser gateOutClerk = systemUserOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Login User ! " + userContext.getUsername()));

		Optional<Client> clientOpt = clientRepository.findOne(gateOutWriteRequest.getGateOutClient());
		Client gateOutClient = clientOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid lane ID ! " + gateOutWriteRequest.getGateOutClient()));

		GateOutReponse gateOutReponse = new GateOutReponse();
		gateOutReponse.setGateOUTDateTime(gateOutWriteRequest.getGateOUTDateTime());
		gateOutReponse.setWhODDContainers(gateOutWriteRequest.getWhoddContainers());

		gateOutReponse.getWhODDContainers().forEach(whODDdto -> {

			Optional<WHODD> optODD = oddRepository.findOne(whODDdto.getOddIdSeq());

			WHODD whODD = optODD.orElseThrow(
					() -> new ResultsNotFoundException("ODD Transaction Not Found : " + whODDdto.getOddIdSeq()));

			// check for eirStatus
			if (whODD.getContainer01().getOddStatus().getValue() != TransactionStatus.INPROGRESS.getValue())
				throw new BusinessException(
						"Not a valid Gate In ODD Transaction to update : " + whODDdto.getOddIdSeq());

			if (whODD.getContainer02() != null
					&& whODD.getContainer02().getOddStatus().getValue() != TransactionStatus.INPROGRESS.getValue())
				throw new BusinessException(
						"Not a valid Gate In ODD Transaction to update : " + whODDdto.getOddIdSeq());

			if (StringUtils.isNotEmpty(gateOutWriteRequest.getReviseHeadNo())) {
				whODD.setReviseHeadNo(gateOutWriteRequest.getReviseHeadNo());
				whODD.setReviseHeadNoRemarks(gateOutWriteRequest.getReviseHeadNoRemarks());
				whODD.setOldHeadNo(whODD.getPmHeadNo());
				whODD.setPmHeadNo(gateOutWriteRequest.getReviseHeadNo());
			}

			if (StringUtils.equalsIgnoreCase(ImpExpFlagStatus.IMPORT.getValue(), whODDdto.getImpExpFlag())) {
				whODD.getContainer01().setContainerNo(whODDdto.getContainer01().getContainerNo());
				// check lane

				if (StringUtils.equalsIgnoreCase(whODDdto.getContainer01().getOddStatus(),
						TransactionStatus.APPROVED.getValue())) {
					if (gateOutClient.getType() != null) {
						if (StringUtils.isNotBlank(gateOutClient.getType().getValue())) {
							if (ClientType.GATE_IN.getValue()
									.equalsIgnoreCase(StringUtils.trim(gateOutClient.getType().getValue()))) {
								throw new BusinessException("Gate out transaction not allowed for gate in lane "
										+ gateOutClient.getDescription()
										+ ". Please rescan the card at gate out lane and try again.");
							}
						}
					}

				}
			}

			if (StringUtils.equalsIgnoreCase(TransactionStatus.APPROVED.getValue(),
					whODD.getGateInStatus().getValue())) {

				if (StringUtils.equalsIgnoreCase(whODDdto.getContainer01().getOddStatus(),
						TransactionStatus.REJECT.getValue())) {

					if (StringUtils.isEmpty(whODDdto.getContainer01().getRemarks()))
						throw new BusinessException("Rejection Remarks is need for container  : "
								+ whODDdto.getContainer01().getContainerNo());
					if (whODDdto.getContainer01().getRejectionReasonID() == null)
						throw new BusinessException("Rejection Reason is need for container  : "
								+ whODDdto.getContainer01().getContainerNo());
					setRejectReason(whODD.getImpExpFlag(), whODD.getContainer01(), whODDdto.getContainer01());
				}
			}

			whODD.getContainer01()
					.setFullOrEmpty(ContainerFullEmptyType.fromValue(whODDdto.getContainer01().getFullOrEmpty()));
			whODD.getContainer01().setOddStatus(TransactionStatus.fromCode(whODDdto.getContainer01().getOddStatus()));

			if (whODD.getContainer01().getHdbsBkgDetailNo().isPresent()) {

				Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
						.findOne(whODD.getContainer01().getHdbsBkgDetailNo().get().getHdbsBKGDetailID());
				HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
						.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
								+ whODD.getContainer01().getHdbsBkgDetailNo().get().getHdbsBKGDetailID()));

				hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.fromValue(whODDdto.getContainer01().getOddStatus()));
				hdbsBookingDetail.setOddTimeGateOutOk(whODD.getTimeGateOutOk());
				hdbsBookingDetail.setContainerNo(whODD.getContainer01().getContainerNo());
			}

			if (whODDdto.getContainer02() != null) {

				if (StringUtils.equalsIgnoreCase(ImpExpFlagStatus.IMPORT.getValue(), whODDdto.getImpExpFlag())) {
					whODD.getContainer02().setContainerNo(whODDdto.getContainer02().getContainerNo());

					if (StringUtils.equalsIgnoreCase(whODDdto.getContainer02().getOddStatus(),
							TransactionStatus.APPROVED.getValue())) {
						if (gateOutClient.getType() != null) {
							if (StringUtils.isNotBlank(gateOutClient.getType().getValue())) {
								if (ClientType.GATE_IN.getValue()
										.equalsIgnoreCase(StringUtils.trim(gateOutClient.getType().getValue()))) {
									throw new BusinessException("Gate out transaction not allowed for gate in lane "
											+ gateOutClient.getDescription()
											+ ". Please rescan the card at gate out lane and try again.");
								}
							}
						}

					}
				}
				whODD.getContainer02()
						.setFullOrEmpty(ContainerFullEmptyType.fromValue(whODDdto.getContainer02().getFullOrEmpty()));

				whODD.getContainer02()
						.setOddStatus(TransactionStatus.fromCode(whODDdto.getContainer02().getOddStatus()));

				if (StringUtils.equalsIgnoreCase(whODDdto.getContainer02().getOddStatus(),
						TransactionStatus.REJECT.getValue())) {

					if (StringUtils.isEmpty(whODDdto.getContainer02().getRemarks()))
						throw new BusinessException("Rejection Remarks is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());
					if (whODDdto.getContainer02().getRejectionReasonID() == null)
						throw new BusinessException("Rejection Reason is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());
					setRejectReason(whODD.getImpExpFlag(), whODD.getContainer02(), whODDdto.getContainer02());

				}

				if (whODD.getContainer02().getHdbsBkgDetailNo().isPresent()) {

					Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
							.findOne(whODD.getContainer02().getHdbsBkgDetailNo().get().getHdbsBKGDetailID());
					HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
							.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
									+ whODD.getContainer02().getHdbsBkgDetailNo().get().getHdbsBKGDetailID()));

					hdbsBookingDetail
							.setScssStatusCode(SCSSHDBSStatus.fromValue(whODDdto.getContainer02().getOddStatus()));
					hdbsBookingDetail.setOddTimeGateOutOk(whODD.getTimeGateOutOk());
					hdbsBookingDetail.setContainerNo(whODD.getContainer02().getContainerNo());
				}
			}

			whODD.setInOutFlag(GateInOutStatus.OUT);
			whODD.setTimeGateOut(gateOutWriteRequest.getGateOUTDateTime());
			whODD.setTimeGateOutOk(LocalDateTime.now());
			whODD.setGateOutClerk(gateOutClerk);
			whODD.setGateOutClient(gateOutClient);
			whODD.setTosServiceType(TOSServiceType.fromValue(gateOutWriteRequest.getTosIndicator()));

			if (!(gateOutWriteRequest.getGateOutBooth() == null && gateOutWriteRequest.getGateOutBooth() == 0)) {
				Client gateOutBooth = clientRepository.findOne(gateOutWriteRequest.getGateOutBooth())
						.orElseThrow(() -> new ResultsNotFoundException(
								"Invalid Booth ID ! " + gateOutWriteRequest.getGateOutBooth()));
				whODD.setGateOutBoothNo(String.valueOf(gateOutBooth.getClientID()));
				whODD.setGateOutBoothClerk(gateOutClerk);
				whODD.setTimeGateOutBooth(whODD.getTimeGateOut());
			}

			oddRepository.save(whODD);

		});

		GateOutMessage gateOutMessage = new GateOutMessage();
		gateOutMessage.setCode(GateOutMessage.OK);
		gateOutMessage.setDescription("Saved Successfully!");

		gateOutReponse.setMessage(gateOutMessage);
		return gateOutReponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void updateODDReference(FileDTO fileDTO) {

		Optional<List<WHODD>> oddOptList = oddRepository
				.findByOddIdSeqIn(Arrays.asList(fileDTO.getOddSeq1().orElse(0l), fileDTO.getOddSeq2().orElse(0l)));

		if (oddOptList.isPresent() && !oddOptList.get().isEmpty()) {
			oddOptList.get().forEach(whODD -> {
				assignUpdatedValuedWHODDobj(whODD, fileDTO);
				oddRepository.save(whODD);
			});
		} else {
			throw new BusinessException("Invalid WhODD ID to update file reference : "
					+ fileDTO.getOddSeq1().orElse(null) + " / " + fileDTO.getOddSeq2().orElse(null));
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void setRejectReason(ImpExpFlagStatus impExpFlag, ODDContainerDetails container,
			ODDContainerDetailsDTO containerDTO) {

		if (impExpFlag == null)
			throw new BusinessException("Transaction not specified Import or Export ");

		switch (impExpFlag) {
		case IMPORT:
			Optional<ODDImportReason> optODDImportReason = oddImportReasonRepository
					.findOne(containerDTO.getRejectionReasonID());
			if (optODDImportReason.isPresent()) {
				container.setRejectionReason(optODDImportReason.get().getImportReason());
				container.setRemarks(containerDTO.getRemarks());
			} else {
				throw new BusinessException(
						"Rejection Reason cannot be found for container  : " + containerDTO.getContainerNo());
			}

			break;

		case EXPORT:

			Optional<ODDExportReason> optODDExportReason = oddExportReasonRepository
					.findOne(containerDTO.getRejectionReasonID());
			if (optODDExportReason.isPresent()) {
				container.setRejectionReason(optODDExportReason.get().getExportReason());
				container.setRemarks(containerDTO.getRemarks());
			} else {
				throw new BusinessException(
						"Rejection Reason cannot be found for container  : " + containerDTO.getContainerNo());
			}

			break;
		default:
			break;

		}
	}
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<WHODD> populateSaveTransaction(BoothTransactionInfo boothTransactionInfo) {

		ArrayList<Long> whODDIDList = new ArrayList<>();

		if (!(boothTransactionInfo.getOddIdSeq01() == null || boothTransactionInfo.getOddIdSeq01() == 0)) {
			whODDIDList.add(boothTransactionInfo.getOddIdSeq01());
		}

		if (!(boothTransactionInfo.getOddIdSeq02() == null || boothTransactionInfo.getOddIdSeq02() == 0)) {
			whODDIDList.add(boothTransactionInfo.getOddIdSeq02());
		}

		Predicate idListPredicate = ODDPredicates.byWHoddIDList(whODDIDList);

		Predicate gateoutClientPredicate = ODDPredicates.byGateOutClient(boothTransactionInfo.getGateoutClientID());

		Predicate condition = ExpressionUtils.allOf(idListPredicate, gateoutClientPredicate);

		Iterable<WHODD> bookingList = oddRepository.findAll(condition);

		Iterator<WHODD> oddItr = bookingList.iterator();
	
		ArrayList<WHODD> whoddContainers = new ArrayList<>();

		if (oddItr != null && oddItr.hasNext()) {
			while (oddItr.hasNext()) {
				whoddContainers.add(oddItr.next());
			}

		} else {
			throw new BusinessException("Provided WHODD information not valid ");
		}

		return whoddContainers;

	}


	private WHODD assignUpdatedValuedWHODDobj(WHODD whoDDobj, FileDTO fileDTO) {
		switch (fileDTO.getCollectionType()) {
		case PDF_FILE_COLLECTION:
			whoDDobj.setTrxSlipNo(fileDTO.getFileName().get());
			break;
		case ZIP_FILE_COLLECTION:
			whoDDobj.setZipFileNo(fileDTO.getFileName().get());
			break;
		default:
			break;
		}
		return whoDDobj;
	}

}
