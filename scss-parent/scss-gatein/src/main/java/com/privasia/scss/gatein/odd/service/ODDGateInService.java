package com.privasia.scss.gatein.odd.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutMessage;
import com.privasia.scss.common.enums.GateInOutStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.enums.SCSSHDBSStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.security.model.UserContext;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.HDBSBkgDetail;
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
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("oddGateInService")
public class ODDGateInService {

	private ModelMapper modelMapper;

	private ODDRepository oddRepository;

	private HDBSBookingDetailRepository hdbsBookingDetailRepository;

	private ClientRepository clientRepository;

	private CardRepository cardRepository;

	private SystemUserRepository systemUserRepository;

	private ODDImportReasonRepository oddImportReasonRepository;

	private ODDExportReasonRepository oddExportReasonRepository;

	private ODDLocationRepository oddLocationRepository;

	@Autowired
	public void setOddImportReasonRepository(ODDImportReasonRepository oddImportReasonRepository) {
		this.oddImportReasonRepository = oddImportReasonRepository;
	}

	@Autowired
	public void setOddExportReasonRepository(ODDExportReasonRepository oddExportReasonRepository) {
		this.oddExportReasonRepository = oddExportReasonRepository;
	}

	@Autowired
	public void setOddLocationRepository(ODDLocationRepository oddLocationRepository) {
		this.oddLocationRepository = oddLocationRepository;
	}

	@Autowired
	public void setSystemUserRepository(SystemUserRepository systemUserRepository) {
		this.systemUserRepository = systemUserRepository;
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
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
		this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
	}

	@Autowired
	public void setOddRepository(ODDRepository oddRepository) {
		this.oddRepository = oddRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public GateInResponse saveODDGateInInFo(GateInWriteRequest gateInWriteRequest) {

		if (gateInWriteRequest.getWhoddContainers() == null || gateInWriteRequest.getWhoddContainers().isEmpty())
			throw new BusinessException("Invalid GateInWriteRequest to save ODD ! ");

		Optional<Card> cardOpt = cardRepository.findOne(gateInWriteRequest.getCardID());
		Card card = cardOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid Scan Card ID ! " + gateInWriteRequest.getCardID()));

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		UserContext userContext = (UserContext) authentication.getPrincipal();

		Optional<SystemUser> systemUserOpt = systemUserRepository.findOne(userContext.getUserID());
		SystemUser gateInClerk = systemUserOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid Login User ! " + userContext.getUsername()));

		Optional<Client> clientOpt = clientRepository.findOne(gateInWriteRequest.getGateInClient());
		Client gateInClient = clientOpt.orElseThrow(
				() -> new ResultsNotFoundException("Invalid lane ID ! " + gateInWriteRequest.getGateInClient()));
		
		gateInWriteRequest.getWhoddContainers().forEach(whODDdto -> {

			if (StringUtils.isEmpty(whODDdto.getImpExpFlag()))
				throw new BusinessException("Transaction not specified Import or Export ");
			ImpExpFlagStatus impExpFlag = ImpExpFlagStatus.fromValue(whODDdto.getImpExpFlag());

			if (StringUtils.equalsIgnoreCase(impExpFlag.getValue(), ImpExpFlagStatus.IMPORT_EXPORT.getValue()))
				throw new BusinessException("Invalid Transaction type for ODD " + impExpFlag.name());

			if (gateInWriteRequest.isOddReject()) {
				
				if (StringUtils.isEmpty(whODDdto.getContainer01().getRemarks())) 
					throw new BusinessException(
							"Rejection Remarks is need for container  : " + whODDdto.getContainer01().getContainerNo());
				if (whODDdto.getContainer01().getRejectionReasonID() == null)
					throw new BusinessException(
							"Rejection Reason is need for container  : " + whODDdto.getContainer01().getContainerNo());
				whODDdto.setGateInStatus(TransactionStatus.REJECT.getValue());
				

				if (whODDdto.getContainer02() != null) {
					if (StringUtils.isEmpty(whODDdto.getContainer02().getRemarks()))
						throw new BusinessException("Rejection Remarks is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());
					if (whODDdto.getContainer02().getRejectionReasonID() == null)
						throw new BusinessException("Rejection Reason is need for container  : "
								+ whODDdto.getContainer02().getContainerNo());
				}

				switch (impExpFlag) {
				case IMPORT:
					Optional<ODDImportReason> optODDImportReason = oddImportReasonRepository.findOne(whODDdto.getContainer01().getRejectionReasonID());
					if(optODDImportReason.isPresent()){
						whODDdto.getContainer01().setRejectionReason(optODDImportReason.get().getImportReason());
					}else{
						throw new BusinessException("Rejection Reason cannot be found for container  : "
								+ whODDdto.getContainer01().getContainerNo());
					}
					if (whODDdto.getContainer02() != null) {
						optODDImportReason = oddImportReasonRepository.findOne(whODDdto.getContainer02().getRejectionReasonID());
						if(optODDImportReason.isPresent()){
							whODDdto.getContainer02().setRejectionReason(optODDImportReason.get().getImportReason());
						}else{
							throw new BusinessException("Rejection Reason cannot be found for container  : "
									+ whODDdto.getContainer02().getContainerNo());
						}
					}
					break;

				case EXPORT:
					
					Optional<ODDExportReason> optODDExportReason = oddExportReasonRepository.findOne(whODDdto.getContainer01().getRejectionReasonID());
					if(optODDExportReason.isPresent()){
						whODDdto.getContainer01().setRejectionReason(optODDExportReason.get().getExportReason());
					}else{
						throw new BusinessException("Rejection Reason cannot be found for container  : "
								+ whODDdto.getContainer01().getContainerNo());
					}
					if (whODDdto.getContainer02() != null) {
						optODDExportReason = oddExportReasonRepository.findOne(whODDdto.getContainer02().getRejectionReasonID());
						if(optODDExportReason.isPresent()){
							whODDdto.getContainer02().setRejectionReason(optODDExportReason.get().getExportReason());
						}else{
							throw new BusinessException("Rejection Reason cannot be found for container  : "
									+ whODDdto.getContainer02().getContainerNo());
						}
					}

					break;
				default:
					break;

				}

			} else {
				whODDdto.setGateInStatus(TransactionStatus.APPROVED.getValue());
			}

			whODDdto.setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
			whODDdto.setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
			whODDdto.setTimeGateIn(gateInWriteRequest.getGateInDateTime());
			whODDdto.setTimeGateInOk(LocalDateTime.now());
			whODDdto.setInOutFlag(GateInOutStatus.IN.getValue());

			WHODD whODD = modelMapper.map(whODDdto, WHODD.class);
			whODD.setCard(card);
			whODD.setGateInClerk(gateInClerk);
			whODD.setGateInClient(gateInClient);
			
			// before save check the pm plate in used
			if (!isPlateNoHeadNoUsed(whODD)) {

				if (whODDdto.getContainer01() != null) {
					
					whODDdto.getContainer01().setOddStatus(TransactionStatus.INPROGRESS.getValue());
					Optional<ODDLocation> optLocation = oddLocationRepository
							.findByOddCodeAndStatusCode(whODDdto.getContainer01().getLocation().getOddCode(), RecordStatus.ACTIVE);
					
					ODDLocation location = optLocation.orElseThrow(() -> new ResultsNotFoundException(
							"Invalid location provided for container " + whODDdto.getContainer01().getContainerNo() + " / "
									+ whODDdto.getContainer01().getLocation().getOddCode()));
					
					whODD.getContainer01().setLocation(location);
					
					whODD.getContainer01().setOddStatus(TransactionStatus.INPROGRESS);
					if (whODDdto.getContainer01().getHdbsBkgDetailNoId() != null) {
						Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
								.findOne(whODDdto.getContainer01().getHdbsBkgDetailNoId());
						HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
								.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
										+ whODDdto.getContainer01().getHdbsBkgDetailNoId()));
						whODD.getContainer01().setHdbsBkgDetailNo(hdbsBookingDetail);
						whODD.getContainer01().setHdbsStatus(hdbsBookingDetail.getStatusCode());
						hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.IN_PROGRESS);
						hdbsBookingDetail.setOddTimeGateInOk(whODD.getTimeGateInOk());
						hdbsBookingDetail.setWhodd(whODD);
					}
				}
				if (whODDdto.getContainer02() != null) {
					
					whODDdto.getContainer02().setOddStatus(TransactionStatus.INPROGRESS.getValue());
					Optional<ODDLocation> optLocation = oddLocationRepository
							.findByOddCodeAndStatusCode(whODDdto.getContainer02().getLocation().getOddCode(), RecordStatus.ACTIVE);
					
					ODDLocation location = optLocation.orElseThrow(() -> new ResultsNotFoundException(
							"Invalid location provided for container " + whODDdto.getContainer02().getContainerNo() + " / "
									+ whODDdto.getContainer02().getLocation().getOddCode()));
					
					whODD.getContainer02().setLocation(location);
					
					whODD.getContainer02().setOddStatus(TransactionStatus.INPROGRESS);
					if (whODDdto.getContainer02().getHdbsBkgDetailNoId() != null) {
	
						Optional<HDBSBkgDetail> optHDBSBookingDetail = hdbsBookingDetailRepository
								.findOne(whODDdto.getContainer02().getHdbsBkgDetailNoId());
						HDBSBkgDetail hdbsBookingDetail = optHDBSBookingDetail
								.orElseThrow(() -> new ResultsNotFoundException("Invalid HDBS Booking Detail ID :"
										+ whODDdto.getContainer02().getHdbsBkgDetailNoId()));
	
						whODD.getContainer02().setHdbsBkgDetailNo(hdbsBookingDetail);
						whODD.getContainer02().setHdbsStatus(hdbsBookingDetail.getStatusCode());
						hdbsBookingDetail.setScssStatusCode(SCSSHDBSStatus.IN_PROGRESS);
						hdbsBookingDetail.setOddTimeGateInOk(whODD.getTimeGateInOk());
						hdbsBookingDetail.setWhodd(whODD);
					}
				}
				
				oddRepository.save(whODD);
				whODDdto.setOddIdSeq(whODD.getOddIdSeq());
			}

		});
		GateInResponse gateInResponse = new GateInResponse();
		GateOutMessage gateOutMessage = new GateOutMessage();
	    gateOutMessage.setCode(GateOutMessage.OK);
	    gateOutMessage.setDescription("Saved Successfully!");
	    
	    gateInResponse.setMessage(gateOutMessage);
	    gateInResponse.setGateINDateTime(gateInWriteRequest.getGateInDateTime());
	    gateInResponse.setWhoddContainers(gateInWriteRequest.getWhoddContainers());
		return gateInResponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isPlateNoHeadNoUsed(WHODD whodd) {

		Predicate byHeadNo = ODDPredicates.byPMHeadNo(whodd.getPmHeadNo());
		Predicate byPlateNo = ODDPredicates.byPMPlateNo(whodd.getPmPlateNo());
		Predicate bycon01ODDStatus = ODDPredicates.byContainer01Status(TransactionStatus.INPROGRESS);
		Predicate bycon02ODDStatus = ODDPredicates.byContainer02Status(TransactionStatus.INPROGRESS);
		Predicate byTransactionType = ODDPredicates.byTransactionType(whodd.getImpExpFlag());

		Predicate condition = ExpressionUtils.allOf(
				ExpressionUtils.or(ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), bycon01ODDStatus),
						ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), bycon02ODDStatus)),
				byTransactionType);

		Iterable<WHODD> oddList = oddRepository.findAll(condition);

		if (oddList == null || Stream.of(oddList).count() == 0)
			return false;

		if (oddList.iterator().hasNext()) {
			WHODD dbODD = oddList.iterator().next();
			if (StringUtils.equalsIgnoreCase(dbODD.getPmHeadNo(), whodd.getPmHeadNo())
					&& StringUtils.equalsIgnoreCase(dbODD.getPmPlateNo(), whodd.getPmPlateNo())) {
				throw new BusinessException("PM Head No " + dbODD.getPmHeadNo() + " and PM plate No "
						+ dbODD.getPmPlateNo() + " already in use.");
			} else if (StringUtils.equalsIgnoreCase(dbODD.getPmHeadNo(), whodd.getPmHeadNo())) {
				throw new BusinessException("PM Head No " + dbODD.getPmHeadNo() + " already in use");
			} else if (StringUtils.equalsIgnoreCase(dbODD.getPmPlateNo(), whodd.getPmPlateNo())) {
				throw new BusinessException("PM plate No " + dbODD.getPmPlateNo() + " already in use");
			}
		}

		return false;

	}

}
