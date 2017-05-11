package com.privasia.scss.gatein.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.annotation.DontValidateSeal;
import com.privasia.scss.common.annotation.ISaDG;
import com.privasia.scss.common.dto.BaseCommonGateInOutDTO;
import com.privasia.scss.common.dto.CommonGateInOutDTO;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.ReferRejectDTO;
import com.privasia.scss.common.dto.ReferRejectDetailDTO;
import com.privasia.scss.common.enums.ContainerFullEmptyType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.enums.ReferStatus;
import com.privasia.scss.common.enums.ShipStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.Client;
import com.privasia.scss.core.model.Exports;
import com.privasia.scss.core.model.ExportsQ;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.model.ReferRejectDetail;
import com.privasia.scss.core.model.ShipCode;
import com.privasia.scss.core.model.ShipSCN;
import com.privasia.scss.core.model.SystemUser;
import com.privasia.scss.core.predicate.ExportsPredicates;
import com.privasia.scss.core.repository.DamageCodeRepository;
import com.privasia.scss.core.repository.DgDetailRepository;
import com.privasia.scss.core.repository.ExportsQRepository;
import com.privasia.scss.core.repository.ExportsRepository;
import com.privasia.scss.core.repository.HPABBookingRepository;
import com.privasia.scss.core.repository.ReferRejectDetailRepository;
import com.privasia.scss.core.repository.ReferRejectRepository;
import com.privasia.scss.core.repository.ShipCodeRepository;
import com.privasia.scss.core.repository.ShipSCNRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.gatein.exports.business.service.DGContainerService;
import com.privasia.scss.gatein.exports.business.service.DamageCodeService;
import com.privasia.scss.gatein.exports.business.service.EarlyEntryService;
import com.privasia.scss.gatein.exports.business.service.EmptyContainerService;
import com.privasia.scss.gatein.exports.business.service.SSRService;
import com.privasia.scss.gatein.exports.business.service.SealValidationService;
import com.privasia.scss.gatein.exports.business.service.SolasService;
import com.privasia.scss.gatein.exports.business.service.VesselOmitService;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

@Service("exportGateInService")
public class ExportGateInService {

	private static final Log log = LogFactory.getLog(ExportGateInService.class);

	//@Value("${refer.update.refer.detail.url}")
	//private String updateReferDetailURL;// updatereferdetail

	private ModelMapper modelMapper;

	private ShipCodeRepository shipCodeRepository;

	private ShipSCNRepository shipSCNRepository;

	private ExportsRepository exportsRepository;

	private ExportsQRepository exportsQRepository;

	private HPABBookingRepository hpabBookingRepository;

	private DamageCodeRepository damageCodeRepository;

	private WDCGlobalSettingRepository globalSettingRepository;

	private LPKEDIService lpkediService;

	private DamageCodeService damageCodeService;

	private SealValidationService sealValidationService;

	private SolasService solasService;

	private EarlyEntryService earlyEntryService;

	private SSRService ssrService;

	private VesselOmitService vesselOmitService;

	private EmptyContainerService emptyContainerService;
	
	private DGContainerService dgContainerService;
	
	private ReferRejectDetailRepository referRejectDetailRepository;

	private ReferRejectRepository referRejectRepository;
	

	@Autowired
	public void setSealValidationService(SealValidationService sealValidationService) {
		this.sealValidationService = sealValidationService;
	}

	@Autowired
	public void setDamageCodeService(DamageCodeService damageCodeService) {
		this.damageCodeService = damageCodeService;
	}

	@Autowired
	public void setLpkediService(LPKEDIService lpkediService) {
		this.lpkediService = lpkediService;
	}

	@Autowired
	public void setGlobalSettingRepository(WDCGlobalSettingRepository globalSettingRepository) {
		this.globalSettingRepository = globalSettingRepository;
	}

	@Autowired
	public void setDamageCodeRepository(DamageCodeRepository damageCodeRepository) {
		this.damageCodeRepository = damageCodeRepository;
	}

	@Autowired
	public void setHpabBookingRepository(HPABBookingRepository hpabBookingRepository) {
		this.hpabBookingRepository = hpabBookingRepository;
	}

	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Autowired
	public void setShipCodeRepository(ShipCodeRepository shipCodeRepository) {
		this.shipCodeRepository = shipCodeRepository;
	}

	@Autowired
	public void setShipSCNRepository(ShipSCNRepository shipSCNRepository) {
		this.shipSCNRepository = shipSCNRepository;
	}

	@Autowired
	public void setExportsRepository(ExportsRepository exportsRepository) {
		this.exportsRepository = exportsRepository;
	}

	@Autowired
	public void setExportsQRepository(ExportsQRepository exportsQRepository) {
		this.exportsQRepository = exportsQRepository;
	}

	@Autowired
	public void setSolasService(SolasService solasService) {
		this.solasService = solasService;
	}

	@Autowired
	public void setEarlyEntryService(EarlyEntryService earlyEntryService) {
		this.earlyEntryService = earlyEntryService;
	}

	@Autowired
	public void setSsrService(SSRService ssrService) {
		this.ssrService = ssrService;
	}

	@Autowired
	public void setVesselOmitService(VesselOmitService vesselOmitService) {
		this.vesselOmitService = vesselOmitService;
	}

	@Autowired
	public void setEmptyContainerService(EmptyContainerService emptyContainerService) {
		this.emptyContainerService = emptyContainerService;
	}

	@Autowired
	public void setReferRejectDetailRepository(ReferRejectDetailRepository referRejectDetailRepository) {
		this.referRejectDetailRepository = referRejectDetailRepository;
	}
	
	@Autowired
	public void setReferRejectRepository(ReferRejectRepository referRejectRepository) {
		this.referRejectRepository = referRejectRepository;
	}
	
	@Autowired
	public void setDgContainerService(DGContainerService dgContainerService) {
		this.dgContainerService = dgContainerService;
	}
	

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInReponse validateExportsGateInRead(GateInReponse gateInReponse, LocalDateTime timegateIn) {

		Optional<String> globalSetting = globalSettingRepository.fetchGlobalStringByGlobalCode("LPK_EDI");

		gateInReponse.getExportContainers().forEach(container -> {
			container.setExpWeightBridge(gateInReponse.getExpWeightBridge());
			setStoragePeriod(container);
			setSCN(container);
			dgContainerService.checkDg(container);
			findLpkEdiMsg(container, globalSetting);
			vesselOmitService.isValidVesselOmit(container);
			earlyEntryService.isContainerHasAOpening(container);
			ssrService.checkExportSSR(timegateIn, container);
			dgContainerService.validateDGContainer(container);
			dgContainerService.userAccessToByPassDG(container);
			
			if(container.getContainer().getContainerFullOrEmpty() == ContainerFullEmptyType.EMPTY.getValue()){
				emptyContainerService.setEmptyContainerWeight(container, container.getCosmosISOCode());
			}
			

		});
		
		emptyContainerService.calculateWeightBridgeForEmpty(gateInReponse.getExportContainers(), gateInReponse.getExpWeightBridge());
		
		boolean fullExist = gateInReponse.getExportContainers().stream()
				.filter(expCon -> (StringUtils.equalsIgnoreCase(ContainerFullEmptyType.FULL.getValue(),
						expCon.getContainer().getContainerFullOrEmpty())))
				.findAny().isPresent();
		if (fullExist)
			solasService.calculateTerminalVGM(gateInReponse.getExportContainers());
		
		
		return gateInReponse;

	}

	private void findLpkEdiMsg(ExportContainer container, Optional<String> globalSettingOpt) {
		if (StringUtils.equalsIgnoreCase(container.getImdg(), "Y")) {
			globalSettingOpt.orElseThrow(() -> new ResultsNotFoundException("Global Setting not available ! LPK_EDI"));
			lpkediService.findLPKEDITDigiMessage(container);
		}
	}

	

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void setStoragePeriod(ExportContainer exportContainer) {
		if (exportContainer != null) {
			String shipCodeStr = exportContainer.getShippingLine();
			Optional<ShipCode> optionalShipCode = shipCodeRepository.findByShipStatusAndShippingCode(ShipStatus.ACTIVE,
					shipCodeStr);
			if (optionalShipCode.isPresent()) {
				ShipCode shipCode = optionalShipCode.get();
				exportContainer.setStoragePeriod(shipCode.getStoragePeriod());
			}

		}

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void setSCN(ExportContainer exportContainer) {

		String exportContainerNumber = null;
		String vesselSCN = null; // vessel scn and ship scn are same

		if (!(exportContainer == null)) {

			exportContainerNumber = exportContainer.getContainer().getContainerNumber();
			vesselSCN = exportContainer.getVesselSCN();
			System.out.println("scn " + vesselSCN);

			Optional<ShipSCN> optionalshipSCN = shipSCNRepository.fetchContainerSCN(vesselSCN, exportContainerNumber);

			if (optionalshipSCN.isPresent()) {
				ShipSCN shipSCN = optionalshipSCN.get();
				exportContainer.setShipSCNID(Optional.of(shipSCN.getShipSCNID()));
				exportContainer.setBypassEEntry(shipSCN.getScnByPass());
				exportContainer.setRegisteredInEarlyEntry(true);
			} else {
				exportContainer.setRegisteredInEarlyEntry(false);
			}

		}

	}

	// @Async
	@ISaDG @DontValidateSeal
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void saveGateInInfo(GateInWriteRequest gateInWriteRequest, Client gateInClient, SystemUser gateInClerk,
			Card card) {
		// construct a new export entity for each exportcontainer and save 

		System.out.println("gateInWriteRequest.getExportContainers() " + gateInWriteRequest.getExportContainers());

		if (gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())
			throw new BusinessException("Invalid GateInWriteRequest to save Exports ! ");
		System.out.println(
				"gateInWriteRequest.getExportContainers().size() " + gateInWriteRequest.getExportContainers().size());

		HPABBooking hpabBooking = null;
		Optional<ExportContainer> exportContainerFirstOpt = gateInWriteRequest.getExportContainers().stream()
				.findFirst();
		if (exportContainerFirstOpt.isPresent()) {
			ExportContainer exportContainerFirst = exportContainerFirstOpt.get();
			if (!(exportContainerFirst.getBaseCommonGateInOutAttribute() == null)) {
				if (StringUtils.isNotEmpty(exportContainerFirst.getBaseCommonGateInOutAttribute().getHpabBooking())) {
					hpabBooking = hpabBookingRepository
							.findOne(exportContainerFirst.getBaseCommonGateInOutAttribute().getHpabBooking())
							.orElseThrow(() -> new ResultsNotFoundException("No HPAB Booking found ! : "
									+ exportContainerFirst.getBaseCommonGateInOutAttribute().getHpabBooking()));
				}
			}
		}
		final HPABBooking hpabBookingFinal = hpabBooking;

		boolean backToback = gateInWriteRequest.getExportContainers().size() == 2 ? true : false;

		gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
			if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
				exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
			}
			System.out.println("gateInWriteRequest.getGateInDateTime() " + gateInWriteRequest.getGateInDateTime());

			// assign values from header level to container level
			exportContainer.getBaseCommonGateInOutAttribute().setEirStatus(TransactionStatus.INPROGRESS.getValue());
			exportContainer.getBaseCommonGateInOutAttribute().setHpabBooking(gateInWriteRequest.getHpatBookingId());
			exportContainer.getBaseCommonGateInOutAttribute().setPmHeadNo(gateInWriteRequest.getTruckHeadNo());
			exportContainer.getBaseCommonGateInOutAttribute().setPmPlateNo(gateInWriteRequest.getTruckPlateNo());
			exportContainer.getBaseCommonGateInOutAttribute().setTimeGateIn(gateInWriteRequest.getGateInDateTime());
			exportContainer.getBaseCommonGateInOutAttribute().setTimeGateInOk(LocalDateTime.now());
			exportContainer.setFuelWeight(gateInWriteRequest.getFuelWeight());
			exportContainer.setTireWeight(gateInWriteRequest.getTireWeight());
			exportContainer.setVariance(gateInWriteRequest.getVariance());
			exportContainer.setBackToback(backToback);

			exportContainer.setTrailerPlateNo(gateInWriteRequest.getTrailerNo());
			exportContainer.setTrailerWeight(Integer.toString(gateInWriteRequest.getTrailerWeight()));
			exportContainer.setPmWeight(Integer.toString(gateInWriteRequest.getTruckWeight()));

			if (exportContainer.getCommonGateInOut() == null) {
				exportContainer.setCommonGateInOut(new CommonGateInOutDTO());
			}
			exportContainer.getCommonGateInOut().setGateInStatus(gateInWriteRequest.getGateInStatus());

			Exports exports = new Exports();
			modelMapper.map(exportContainer, exports);

			ShipSCN scn = null;
			if (!(exportContainer.getVesselSCN() == null)) { // scn = VesselSCN

				scn = shipSCNRepository.fetchContainerSCN(exportContainer.getVesselSCN(),
						exportContainer.getContainer().getContainerNumber()).orElse(null);
			}
			exports.prepareForInsertFromOpus(gateInClerk, card, gateInClient, scn, hpabBookingFinal,
					damageCodeRepository);
			exports = exportsRepository.save(exports);
			log.info("########## Save Exports ###############");
			ExportsQ exportsQ = new ExportsQ();
			modelMapper.map(exports, exportsQ);
			exportsQ = exportsQRepository.save(exportsQ);
			System.out.println("exportsQ.getExportID() after save " + exportsQ.getExportID());
			exportContainer.setExportID(exports.getExportID());
			// referee reject service update
			if (gateInWriteRequest.getReferRejectDTO().isPresent() && gateInWriteRequest.getReferRejectDTO().get().getReferRejectID()!=null) {
				updateReferReject(gateInWriteRequest, exportContainer);
			}

		});

		// return new AsyncResult<Boolean>(true);
	}
	
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateReferReject(GateInWriteRequest gateInWriteRequest, ExportContainer exportContainer) {
		
		ReferRejectDetailDTO detailDTO = constructReferRejectDetailDTO(gateInWriteRequest, exportContainer);

		/*RestTemplate restTemplate = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<ReferRejectDetailDTO> request = new HttpEntity<ReferRejectDetailDTO>(detailDTO, headers);

		log.info("OpusGateInReadRequest : -" + (new Gson()).toJson(detailDTO));

		ResponseEntity<ApiResponseObject> response = restTemplate.postForEntity(updateReferDetailURL, request,
				ApiResponseObject.class);

		log.info("RESPONSE FROM REFER REJECT: " + response.toString());*/
		
	    if (detailDTO == null)
	      throw new BusinessException("Refer reject details to update is not available!");

	    ReferRejectDetail referRejectDetail = referRejectDetailRepository
	        .findByReferReject_ReferRejectIDAndContainerNoAndReferReject_StatusCode(detailDTO.getReferReject().getReferRejectID(),
	        		detailDTO.getContainerNo(), HpabReferStatus.ACTIVE)
	        .orElseThrow(() -> new ResultsNotFoundException("Refer reject detail was not found!"));

	    referRejectDetail.setLineCode(StringUtils.upperCase(detailDTO.getLineCode()));
	    referRejectDetail.setGateInTime(detailDTO.getGateInTime());
	    referRejectDetail.setStatus(ReferStatus.REJECT_EXE);
	    referRejectDetail.getReferReject().setStatusCode(HpabReferStatus.COMPLETE);
	    referRejectRepository.save(referRejectDetail.getReferReject());
	   
	}

	private ReferRejectDetailDTO constructReferRejectDetailDTO(GateInWriteRequest gateInWriteRequest,
			ExportContainer exportContainer) {
		ReferRejectDetailDTO referRejectDetailDTO = new ReferRejectDetailDTO();
		referRejectDetailDTO.setContainerNo(exportContainer.getContainer().getContainerNumber());
		referRejectDetailDTO.setLineCode(exportContainer.getShippingLine());
		referRejectDetailDTO.setGateInTime(gateInWriteRequest.getGateInDateTime());
		referRejectDetailDTO.setReferReject(new ReferRejectDTO());
		referRejectDetailDTO.getReferReject()
				.setReferRejectID(gateInWriteRequest.getReferRejectDTO().get().getReferRejectID());

		return referRejectDetailDTO;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public void validateExportsGateInWrite(GateInWriteRequest gateInWriteRequest) {
		if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
			gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
				// 1. DAMAGE CONTAINER CHECK - 1313 (DamageCodeService )
				damageCodeService.checkDuplicateDameCodeExistence(exportContainer);
				// 2. Shipping Line Seal Validation -1578
				// (SealValidationServicve)
				sealValidationService.validateSeal(exportContainer);
				dgContainerService.validateUserByPassDG(exportContainer);
				
				if(exportContainer.getContainer().getContainerFullOrEmpty() == ContainerFullEmptyType.EMPTY.getValue()){
					emptyContainerService.setEmptyContainerWeight(exportContainer, exportContainer.getContainer().getContainerISOCode());
				}

			});
			
			emptyContainerService.calculateWeightBridgeForEmpty(gateInWriteRequest.getExportContainers(), gateInWriteRequest.getWeightBridge());
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean isPlateNoHeadNoUsed(GateInWriteRequest gateInWriteRequest) {

		Predicate byHeadNo = ExportsPredicates.byPMHeadNo(gateInWriteRequest.getTruckHeadNo());
		Predicate byPlateNo = ExportsPredicates.byPMPlateNo(gateInWriteRequest.getTruckPlateNo());
		Predicate byTransactionStatus = ExportsPredicates.byTransactionStatus(TransactionStatus.INPROGRESS);

		Predicate condition = ExpressionUtils
				.allOf(ExpressionUtils.and(ExpressionUtils.or(byHeadNo, byPlateNo), byTransactionStatus));

		Iterable<Exports> exportsList = exportsRepository.findAll(condition);

		if (exportsList == null || Stream.of(exportsList).count() == 0)
			return false;

		if (exportsList.iterator().hasNext()) {
			Exports dbExports = exportsList.iterator().next();
			if (StringUtils.equalsIgnoreCase(dbExports.getBaseCommonGateInOutAttribute().getPmHeadNo(),
					gateInWriteRequest.getTruckHeadNo())
					&& StringUtils.equalsIgnoreCase(dbExports.getBaseCommonGateInOutAttribute().getPmPlateNo(),
							gateInWriteRequest.getTruckPlateNo())) {
				throw new BusinessException(
						"PM Head No " + dbExports.getBaseCommonGateInOutAttribute().getPmHeadNo() + "and PM plate No "
								+ dbExports.getBaseCommonGateInOutAttribute().getPmPlateNo() + " already in use.");
			} else if (StringUtils.equalsIgnoreCase(dbExports.getBaseCommonGateInOutAttribute().getPmHeadNo(),
					gateInWriteRequest.getTruckHeadNo())) {
				throw new BusinessException(
						"PM Head No " + dbExports.getBaseCommonGateInOutAttribute().getPmHeadNo() + " already in use");
			} else if (StringUtils.equalsIgnoreCase(dbExports.getBaseCommonGateInOutAttribute().getPmPlateNo(),
					gateInWriteRequest.getTruckPlateNo())) {
				throw new BusinessException("PM plate No " + dbExports.getBaseCommonGateInOutAttribute().getPmPlateNo()
						+ " already in use");
			}
		}

		return false;

	}
	
	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void testDGValidationLog(GateInWriteRequest gateInWriteRequest) {
		// construct a new export entity for each exportcontainer and save 

		System.out.println("gateInWriteRequest.getExportContainers() " + gateInWriteRequest.getExportContainers());


		gateInWriteRequest.getExportContainers().forEach(exportContainer -> {
			if (exportContainer.getBaseCommonGateInOutAttribute() == null) {
				exportContainer.setBaseCommonGateInOutAttribute(new BaseCommonGateInOutDTO());
			}
			exportContainer.getBaseCommonGateInOutAttribute().setTimeGateIn(gateInWriteRequest.getGateInDateTime());
			exportContainer.setImdg("2.2");
			exportContainer.setExportID(6292684l);
			exportContainer.getContainer().setContainerNumber("SGPU1180138");
			exportContainer.setLpkClass("2");
			exportContainer.setLpkApproval("test approval");
			exportContainer.setDgBypassRemark("test by pass");
			
		});

	}
	

}
