/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
// import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.HDBSBkgDetailGridDTO;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.common.dto.ODDContainerDetailsDTO;
import com.privasia.scss.common.dto.WHODDDTO;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.HDBSBookingType;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.HDBSBkgMaster;
import com.privasia.scss.core.predicate.HDBSBookingMasterPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.HDBSBookingMasterRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hdbsService")
public class HDBSService {

	private HDBSBookingDetailRepository hdbsBookingDetailRepository;

	private HDBSBookingMasterRepository hdbsBookingMasterRepository;

	private CardRepository cardRepository;

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private ModelMapper modelMapper;
	
	@Autowired
	public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
		this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
	}
	
	@Autowired
	public void setHdbsBookingMasterRepository(HDBSBookingMasterRepository hdbsBookingMasterRepository) {
		this.hdbsBookingMasterRepository = hdbsBookingMasterRepository;
	}
	
	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}
	
	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}
	
	@Autowired
	public void setModelMapper(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList) {

		Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);

		return bkgDetails.collect(Collectors.toList());
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public String validateSelectedHDBSBookingDetails(HDBSBkgGridDTO bkgDetailGridDTO) {

		List<String> bkgDetailIDList = new ArrayList<>();

		if (bkgDetailGridDTO == null || bkgDetailGridDTO.getHdbsBkgDetailGridDTOList().isEmpty()) {
			return "Booking selection is Empty";
		}

		bkgDetailGridDTO.getHdbsBkgDetailGridDTOList().forEach(gridDTO -> {
			bkgDetailIDList.add(gridDTO.getHdbsBKGDetailID());
		});

		List<HDBSBkgDetail> bkgDetails = findHDBSBookingDetailByIDList(bkgDetailIDList);

		Long import40Count = bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.PICKUP)
					&& bkgDetail.getContainerSize().equals(ContainerSize.SIZE_40);
		}).count();

		if (import40Count > 1)
			throw new BusinessException("Two Import 40ft Length Container");

		Long import20Count = bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.PICKUP)
					&& bkgDetail.getContainerSize().equals(ContainerSize.SIZE_20);
		}).count();

		if (import20Count > 2)
			throw new BusinessException("More than two 20ft Length Container");

		if (import40Count == 1 && import20Count == 1)
			throw new BusinessException("Invalid transaction with two container sizes exceeding 40ft");

		Long export40Count = bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.DROP)
					&& bkgDetail.getContainerSize().equals(ContainerSize.SIZE_40);
		}).count();

		if (export40Count > 1)
			throw new BusinessException("Two Export 40ft Length Container");

		Long export20Count = bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.PICKUP)
					&& bkgDetail.getContainerSize().equals(ContainerSize.SIZE_20);
		}).count();

		if (export20Count > 2)
			throw new BusinessException("More than two Export 20ft Length Container");

		if (export40Count == 1 && export20Count == 1)
			throw new BusinessException("Invalid transaction with two container sizes exceeding 40ft");

		return "validation success";

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public HDBSBkgGridDTO findHDBSBookingDetailByCard(Long cardID) {

		Optional<Card> optionalCard = cardRepository.findOne(cardID);

		optionalCard.orElseThrow(() -> new BusinessException("Scan Card was not found " + cardID));

		HDBSBkgGridDTO gridDTo = new HDBSBkgGridDTO();

		LocalDateTime systemDateTime = LocalDateTime.now();
		// set the value in the hdbsbooking dto
		gridDTo.setArrivalTime(systemDateTime);
		gridDTo.setSmartCardNo(String.valueOf(optionalCard.get().getCardNo()));

		Optional<Integer> optionalHdbsStart = wdcGlobalSettingRepository
				.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_START_HOUR);
		int hdbsStart = optionalHdbsStart.orElse(ApplicationConstants.DEFAULT_HDBS_START_HOUR_VALUE);

		Optional<Integer> optionalHdbsEnd = wdcGlobalSettingRepository
				.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_END_HOUR);
		int hdbsEnd = optionalHdbsEnd.orElse(ApplicationConstants.DEFAULT_HDBS_END_HOUR_VALUE);

		Optional<Integer> optionalHdbsAcceptedStart = wdcGlobalSettingRepository
				.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_START);
		int hdbsAcceptStart = optionalHdbsAcceptedStart.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_START_VALUE);

		Optional<Integer> optionalHdbsAcceptedEnd = wdcGlobalSettingRepository
				.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_END);
		int hdbsAcceptEnd = optionalHdbsAcceptedEnd.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_END_VALUE);

		Optional<String> optionalHdbsManual = wdcGlobalSettingRepository
				.fetchGlobalStringByGlobalCode(ApplicationConstants.HDBS_MANUAL);

		// need to do
		gridDTo.setShowManual(optionalHdbsManual.orElse("N"));

		LocalDateTime dateFrom = systemDateTime.plusHours(hdbsStart);// hpatStart
		LocalDateTime dateTo = systemDateTime.plusHours(hdbsEnd);// hpatEnd

		List<HDBSStatus> hdbsStatusList = new ArrayList<>();
		hdbsStatusList.add(HDBSStatus.NEW);
		hdbsStatusList.add(HDBSStatus.ACCEPTED);
		hdbsStatusList.add(HDBSStatus.REJECTED);
		hdbsStatusList.add(HDBSStatus.CANCELLED);

		gridDTo = createPredicatesAndFindHDBS(optionalCard.get(), dateFrom, dateTo, hdbsStatusList, gridDTo);

		LocalDateTime acceptDateFrom = systemDateTime.plusMinutes(hdbsAcceptStart);
		LocalDateTime acceptDateTo = systemDateTime.plusMinutes(hdbsAcceptEnd);

		setAcceptBookingStatus(gridDTo, acceptDateFrom, acceptDateTo);

		Collections.sort(gridDTo.getHdbsBkgDetailGridDTOList(),
				(d1, d2) -> d1.getApptDateTimeFrom().compareTo(d2.getApptDateTimeFrom()));

		return gridDTo;
	}

	public HDBSBkgGridDTO createPredicatesAndFindHDBS(long cardNo) {

		LocalDateTime dateFrom = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
		LocalDateTime dateTo = LocalDateTime.now().plus(2, ChronoUnit.HOURS);

		HDBSBkgGridDTO gridDTo = new HDBSBkgGridDTO();

		List<HDBSBkgDetailGridDTO> hdbsBookingList = new ArrayList<HDBSBkgDetailGridDTO>();

		List<HDBSStatus> hdbsStatusList = new ArrayList<>();
		hdbsStatusList.add(HDBSStatus.ACCEPTED);

		Predicate byCardNo = HDBSBookingMasterPredicates.byCardNo(String.valueOf(cardNo));
		Predicate byAPPTDateFrom = HDBSBookingMasterPredicates.byApptDateTimeFromBetween(dateFrom, dateTo);
		Predicate byDrayageBooking = HDBSBookingMasterPredicates.byDrayageBooking(0);
		Predicate byHDBSStatus = HDBSBookingMasterPredicates.byHDBSStatusTypes(hdbsStatusList);
		Predicate byNullableSCSS = HDBSBookingMasterPredicates.byNullableSCSSStatusCode();

		Predicate condition = ExpressionUtils.allOf(byCardNo, byAPPTDateFrom, byDrayageBooking, byHDBSStatus,
				byNullableSCSS);

		OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingMasterPredicates
				.orderByAppointmentStartDateAsc();

		Iterable<HDBSBkgMaster> bookingList = hdbsBookingMasterRepository.findAll(condition, orderByAPPStartDate);

		bookingList.forEach((hdbsbkgMaster) -> {
			if (!(hdbsbkgMaster.getHdbsBookingDetails() == null || hdbsbkgMaster.getHdbsBookingDetails().isEmpty())) {
				hdbsbkgMaster.getHdbsBookingDetails().forEach(detail -> {
					HDBSBkgDetailGridDTO hdbs = constructDetailGridDetailDTO(detail);
					setDuration(hdbs);
					hdbsBookingList.add(hdbs);
				});
			}
		});

		gridDTo.setHdbsBkgDetailGridDTOList(hdbsBookingList);
		return gridDTo;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public HDBSBkgGridDTO createPredicatesAndFindHDBS(Card card, LocalDateTime dateFrom, LocalDateTime dateTo,
			List<HDBSStatus> statusList, HDBSBkgGridDTO gridDTo) {

		List<HDBSBkgDetailGridDTO> hdbsBookingList = new ArrayList<HDBSBkgDetailGridDTO>();

		Predicate byCardNo = HDBSBookingMasterPredicates.byCardNo(String.valueOf(card.getCardNo()));
		Predicate byAPPTDateFrom = HDBSBookingMasterPredicates.byApptDateTimeFrom(dateFrom);
		Predicate byAPPTDateTo = HDBSBookingMasterPredicates.byApptDateTimeToActual(dateTo);
		Predicate byDrayageBooking = HDBSBookingMasterPredicates.byDrayageBooking(0);
		Predicate byHDBSStatus = HDBSBookingMasterPredicates.byHDBSStatusTypes(statusList);
		Predicate byNullableSCSS = HDBSBookingMasterPredicates.byNullableSCSSStatusCode();

		Predicate condition = ExpressionUtils.allOf(byCardNo, byAPPTDateFrom, byAPPTDateTo, byDrayageBooking,
				byHDBSStatus, byNullableSCSS);

		OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingMasterPredicates
				.orderByAppointmentStartDateAsc();

		Iterable<HDBSBkgMaster> bookingList = hdbsBookingMasterRepository.findAll(condition, orderByAPPStartDate);

		bookingList.forEach((hdbsbkgMaster) -> {
			hdbsbkgMaster.getHdbsBookingDetails().forEach(detail -> {
				HDBSBkgDetailGridDTO hdbs = constructDetailGridDetailDTO(detail);
				setDuration(hdbs);
				hdbsBookingList.add(hdbs);
			});

		});

		gridDTo.setHdbsBkgDetailGridDTOList(hdbsBookingList);
		return gridDTo;

	}

	private HDBSBkgDetailGridDTO constructDetailGridDetailDTO(HDBSBkgDetail detail) {
		return modelMapper.map(detail, HDBSBkgDetailGridDTO.class);
	}

	private void setAcceptBookingStatus(HDBSBkgGridDTO gridDTo, LocalDateTime acceptDateFrom, LocalDateTime dateTo) {

		gridDTo.getHdbsBkgDetailGridDTOList().stream().filter(hdbs -> (hdbs.getApptDateTimeFrom() != null))
				.forEach(hdbs -> {
					if (StringUtils.equals(HDBSStatus.ACCEPTED.getValue(), hdbs.getStatusCode().getValue())) {

						if (hdbs.getApptDateTimeFrom().isAfter(acceptDateFrom)
								&& hdbs.getApptDateTimeToActual().isBefore(dateTo)) {
							hdbs.setAcceptBooking(true);
						}
					}
				});
	}

	private void setDuration(HDBSBkgDetailGridDTO hdbs) {
		LocalDateTime systemDateTime = LocalDateTime.now();

		String durration = StringUtils.EMPTY;
		String status = StringUtils.EMPTY;
		String onTimeFlag = StringUtils.EMPTY;

		List<ChronoUnit> units = new ArrayList<>();
		units.add(ChronoUnit.HOURS);
		units.add(ChronoUnit.MINUTES);

		if (systemDateTime.isAfter(hdbs.getApptDateTimeToActual())) {

			durration = DateUtil.getFormattedDiffrenceBetweenDays(hdbs.getApptDateTimeToActual(), systemDateTime, units,
					false);
			status = ApplicationConstants.LATE;
			onTimeFlag = "N";

		} else if (systemDateTime.isBefore(hdbs.getApptDateTimeFrom())) {

			durration = DateUtil.getFormattedDiffrenceBetweenDays(systemDateTime, hdbs.getApptDateTimeFrom(), units,
					false);
			status = ApplicationConstants.EARLY;
			onTimeFlag = "N";

		} else if (systemDateTime.isAfter(hdbs.getApptDateTimeFrom())
				&& systemDateTime.isBefore(hdbs.getApptDateTimeToActual())) {
			status = ApplicationConstants.ON_TIME;
			onTimeFlag = "Y";
		}

		hdbs.setDurration(durration);
		hdbs.setStatus(status);
		hdbs.setOnTimeFlag(onTimeFlag);

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<WHODDDTO> populateODDInfo(String timeGateIn, List<String> bkgDetailIDList) {

		List<HDBSBkgDetail> bkgDetails = findHDBSBookingDetailByIDList(bkgDetailIDList);
		List<WHODDDTO> oddInfoList = new ArrayList<WHODDDTO>();

		WHODDDTO importODDDTO = new WHODDDTO();
		WHODDDTO exportODDDTO = new WHODDDTO();

		bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.PICKUP);
		}).forEach(bkgDetail -> {
			importODDDTO.setTimeGateIn(DateUtil.getParsedDateTime(timeGateIn));
			setODDContainerInfo(importODDDTO, bkgDetail, ImpExpFlagStatus.IMPORT);
			oddInfoList.add(importODDDTO);

		});

		bkgDetails.stream().filter(bkgDetail -> {
			return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.DROP);
		}).forEach(bkgDetail -> {
			exportODDDTO.setTimeGateIn(DateUtil.getParsedDateTime(timeGateIn));
			setODDContainerInfo(exportODDDTO, bkgDetail, ImpExpFlagStatus.EXPORT);
			oddInfoList.add(exportODDDTO);
		});

		return oddInfoList;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public WHODDDTO setODDContainerInfo(WHODDDTO oddDTO, HDBSBkgDetail bkgDetails, ImpExpFlagStatus impExpFlag) {

		ODDContainerDetailsDTO containerDetailsDTO = new ODDContainerDetailsDTO();
		containerDetailsDTO.setContainerNo(bkgDetails.getContainerNo());
		containerDetailsDTO.setLocation(bkgDetails.gethDBSBkgMaster().getDepotCode());
		containerDetailsDTO.setHdbsBkgDetailNoId(bkgDetails.getHdbsBKGDetailID());
		containerDetailsDTO.setHdbsStatus(bkgDetails.getStatusCode().getValue());
		containerDetailsDTO.setContainerSize(
				bkgDetails.getContainerSize() == null ? "" : bkgDetails.getContainerSize().getValue());

		if (oddDTO.getTimeGateIn().isAfter(bkgDetails.getApptDateTimeToActual())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.LATE);
		} else if (oddDTO.getTimeGateIn().isAfter(bkgDetails.getApptDateTimeFrom())
				&& oddDTO.getTimeGateIn().isBefore(bkgDetails.getApptDateTimeToActual())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.ACTIVE);
		} else if (oddDTO.getTimeGateIn().isBefore(bkgDetails.getApptDateTimeFrom())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.EARLY);
		}

		if (oddDTO.getContainer01() == null) {
			oddDTO.setPmHeadNo(bkgDetails.gethDBSBkgMaster().getPmHeadNo());
			oddDTO.setPmPlateNo(bkgDetails.gethDBSBkgMaster().getPlateNo());
			oddDTO.setImpExpFlag(impExpFlag.getValue());
			oddDTO.setContainer01(containerDetailsDTO);
		} else {
			// oddDTO.setContainer02(containerDetailsDTO);
		}

		return oddDTO;

	}
	

}
