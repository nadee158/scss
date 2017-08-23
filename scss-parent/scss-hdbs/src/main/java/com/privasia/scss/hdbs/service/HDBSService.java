/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.HDBSBkgDetailGridDTO;
import com.privasia.scss.common.dto.HDBSBkgGridDTO;
import com.privasia.scss.common.dto.HDBSBookingResponse;
import com.privasia.scss.common.dto.ODDContainerDetailsDTO;
import com.privasia.scss.common.dto.ODDLocationDTO;
import com.privasia.scss.common.dto.WHoddDTO;
import com.privasia.scss.common.enums.ContainerSize;
import com.privasia.scss.common.enums.HDBSBookingType;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.enums.ImpExpFlagStatus;
import com.privasia.scss.common.enums.RecordStatus;
import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.predicate.HDBSBookingDetailsPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.ODDLocationRepository;
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

	private CardRepository cardRepository;

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private ModelMapper modelMapper;

	private ODDLocationRepository oddLocationRepository;

	@Autowired
	public void setHdbsBookingDetailRepository(HDBSBookingDetailRepository hdbsBookingDetailRepository) {
		this.hdbsBookingDetailRepository = hdbsBookingDetailRepository;
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

	@Autowired
	public void setOddLocationRepository(ODDLocationRepository oddLocationRepository) {
		this.oddLocationRepository = oddLocationRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList) {

		Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);

		return bkgDetails.collect(Collectors.toList());
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public boolean validateSelectedHDBSBookingDetails(HDBSBkgGridDTO bkgDetailGridDTO) {

		List<String> bkgDetailIDList = new ArrayList<>();

		if (bkgDetailGridDTO == null || bkgDetailGridDTO.getHdbsBkgDetailGridDTOList().isEmpty()) {
			throw new BusinessException("Booking selection is Empty !");
		}

		bkgDetailGridDTO.getHdbsBkgDetailGridDTOList().forEach(gridDTO -> {
			bkgDetailIDList.add(gridDTO.getHdbsBKGDetailID());
		});

		List<HDBSBkgDetail> bkgDetails = findHDBSBookingDetailByIDList(bkgDetailIDList);

		if (!(bkgDetails == null || bkgDetails.isEmpty())) {

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

			return true;

		} else {
			throw new ResultsNotFoundException("Provided Bookings Incorrect. Results Not found !");
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public HDBSBookingResponse findHDBSBookingDetailByCard(Long cardID) {

		Optional<Card> optionalCard = cardRepository.findOne(cardID);

		optionalCard.orElseThrow(() -> new BusinessException("Scan Card was not found " + cardID));

		LocalDateTime systemDateTime = LocalDateTime.now();

		HDBSBookingResponse hdbsBookingResponse = new HDBSBookingResponse();

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

		LocalDateTime dateFrom = systemDateTime.plusHours(hdbsStart);// hpatStart
		LocalDateTime dateTo = systemDateTime.plusHours(hdbsEnd);// hpatEnd

		List<HDBSStatus> hdbsStatusList = new ArrayList<>();
		hdbsStatusList.add(HDBSStatus.NEW);
		hdbsStatusList.add(HDBSStatus.ACCEPTED);
		hdbsStatusList.add(HDBSStatus.REJECTED);
		hdbsStatusList.add(HDBSStatus.CANCELLED);

		Optional<List<HDBSBkgDetailGridDTO>> optHDBSBookingList = createPredicatesAndFindHDBS(optionalCard.get(),
				dateFrom, dateTo, hdbsStatusList);

		if (optHDBSBookingList.isPresent() && !optHDBSBookingList.get().isEmpty()) {

			HDBSBkgGridDTO gridDTo = new HDBSBkgGridDTO();

			// set the value in the hdbsbooking dto
			gridDTo.setArrivalTime(systemDateTime);
			gridDTo.setSmartCardNo(String.valueOf(optionalCard.get().getCardNo()));
			// need to do
			gridDTo.setShowManual(optionalHdbsManual.orElse("N"));

			gridDTo.setHdbsBkgDetailGridDTOList(optHDBSBookingList.get());

			LocalDateTime acceptDateFrom = systemDateTime.plusMinutes(hdbsAcceptStart);
			LocalDateTime acceptDateTo = systemDateTime.plusMinutes(hdbsAcceptEnd);

			setAcceptBookingStatus(gridDTo, acceptDateFrom, acceptDateTo);

			Collections.sort(gridDTo.getHdbsBkgDetailGridDTOList(),
					(d1, d2) -> d1.getApptDateTimeFrom().compareTo(d2.getApptDateTimeFrom()));

			hdbsBookingResponse.setAvaliable(true);
			hdbsBookingResponse.setHdbsGrid(gridDTo);
		}

		return hdbsBookingResponse;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public HDBSBkgGridDTO createPredicatesAndFindHDBS(Card card) {

		LocalDateTime dateFrom = LocalDateTime.now().minus(1, ChronoUnit.HOURS);
		LocalDateTime dateTo = LocalDateTime.now().plus(2, ChronoUnit.HOURS);

		HDBSBkgGridDTO gridDTo = new HDBSBkgGridDTO();

		List<HDBSBkgDetailGridDTO> hdbsBookingList = new ArrayList<HDBSBkgDetailGridDTO>();

		List<HDBSStatus> hdbsStatusList = new ArrayList<>();
		hdbsStatusList.add(HDBSStatus.ACCEPTED);

		Optional<List<HDBSBkgDetailGridDTO>> hdbsBookingListOpt = createPredicatesAndFindHDBS(card, dateFrom, dateTo,
				hdbsStatusList);

		if (hdbsBookingListOpt.isPresent()) {
			hdbsBookingList = hdbsBookingListOpt.get();
		} else {
			hdbsBookingList = null;
		}

		gridDTo.setHdbsBkgDetailGridDTOList(hdbsBookingList);
		return gridDTo;

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public Optional<List<HDBSBkgDetailGridDTO>> createPredicatesAndFindHDBS(Card card, LocalDateTime dateFrom,
			LocalDateTime dateTo, List<HDBSStatus> statusList) {

		final Optional<List<HDBSBkgDetailGridDTO>> hdbsBookingList = Optional.of(new ArrayList<HDBSBkgDetailGridDTO>());

		Predicate byCardNo = HDBSBookingDetailsPredicates.byCardNo(String.valueOf(card.getCardNo()));
		Predicate byAPPTDateFrom = HDBSBookingDetailsPredicates.byApptDateTimeFrom(dateFrom);
		Predicate byAPPTDateTo = HDBSBookingDetailsPredicates.byApptDateTimeToActual(dateTo);
		Predicate byDrayageBooking = HDBSBookingDetailsPredicates.byDrayageBooking(0);
		Predicate byHDBSStatus = HDBSBookingDetailsPredicates.byHDBSStatusTypes(statusList);
		Predicate byNullableSCSS = HDBSBookingDetailsPredicates.byNullableSCSSStatusCode();

		Predicate condition = ExpressionUtils.allOf(byCardNo, byAPPTDateFrom, byAPPTDateTo, byDrayageBooking,
				byHDBSStatus, byNullableSCSS);

		OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingDetailsPredicates
				.orderByAppointmentStartDateAsc();

		Iterable<HDBSBkgDetail> bookingList = hdbsBookingDetailRepository.findAll(condition, orderByAPPStartDate);

		Iterator<HDBSBkgDetail> bookingIterator = bookingList.iterator();

		Set<HDBSBkgDetailGridDTO> gridSet = new HashSet<HDBSBkgDetailGridDTO>();

		if (bookingIterator.hasNext()) {
			bookingList.forEach(detail -> {
				HDBSBkgDetailGridDTO hdbs = constructDetailGridDetailDTO(detail);
				setDuration(hdbs);
				gridSet.add(hdbs);

			});

			hdbsBookingList.get().addAll(gridSet);
		}
		return hdbsBookingList;

	}

	private HDBSBkgDetailGridDTO constructDetailGridDetailDTO(HDBSBkgDetail detail) {
		HDBSBkgDetailGridDTO detailGridDTO = modelMapper.map(detail, HDBSBkgDetailGridDTO.class);
		detailGridDTO.setContainerSize(detail.getContainerSize().getValue());
		return detailGridDTO;
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
	public GateInResponse populateGateInODD(GateInRequest gateInRequest) {

		GateInResponse gateInResponse = new GateInResponse();
		gateInResponse.setGateINDateTime(gateInRequest.getGateInDateTime());

		if (gateInRequest.getBkgDetailIDList().isPresent() && !gateInRequest.getBkgDetailIDList().get().isEmpty()) {

			List<String> bkgDetailIDList = gateInRequest.getBkgDetailIDList().get();

			Optional<Card> optionalCard = cardRepository.findOne(gateInRequest.getCardID());

			optionalCard.orElseThrow(() -> new BusinessException("Scan Card was not found " + optionalCard.get()));

			Predicate byCardNo = HDBSBookingDetailsPredicates.byCardNo(String.valueOf(optionalCard.get().getCardNo()));
			Predicate byDrayageBooking = HDBSBookingDetailsPredicates.byDrayageBooking(0);
			Predicate byNullableSCSS = HDBSBookingDetailsPredicates.byNullableSCSSStatusCode();
			Predicate byBookingIDList = HDBSBookingDetailsPredicates.byHDBSBookingDetailByIDList(bkgDetailIDList);

			Predicate condition = ExpressionUtils.allOf(byCardNo, byDrayageBooking, byNullableSCSS, byBookingIDList);

			OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingDetailsPredicates
					.orderByAppointmentStartDateAsc();

			Iterable<HDBSBkgDetail> bookingList = hdbsBookingDetailRepository.findAll(condition, orderByAPPStartDate);

			Iterator<HDBSBkgDetail> bookingIterator = bookingList.iterator();

			List<WHoddDTO> oddInfoList = new ArrayList<WHoddDTO>();

			if (bookingIterator.hasNext()) {

				List<HDBSBkgDetail> convertedBookings = new ArrayList<>();
				bookingIterator.forEachRemaining(convertedBookings::add);
				
				
				List<HDBSBkgDetail> pickupList = convertedBookings.stream().filter(bkgDetail -> StringUtils.equalsIgnoreCase(bkgDetail.getHdbsBkgType().getValue(),
							HDBSBookingType.PICKUP.getValue())).collect(Collectors.toList());
				
				List<HDBSBkgDetail> dropList = convertedBookings.stream().filter(bkgDetail -> StringUtils.equalsIgnoreCase(bkgDetail.getHdbsBkgType().getValue(),
						HDBSBookingType.DROP.getValue())).collect(Collectors.toList());

				
				if(! (pickupList == null || pickupList.isEmpty())){
					WHoddDTO importODDDTO = new WHoddDTO();
					if (gateInRequest.isOddReject()) {
						importODDDTO.setGateInStatus(TransactionStatus.REJECT.getValue());
					}
					pickupList.forEach(bkgDetail -> {
							setODDContainerInfo(importODDDTO, bkgDetail, ImpExpFlagStatus.IMPORT);

					});
					
					oddInfoList.add(importODDDTO);
					gateInResponse.setImpExpFlagStatus(ImpExpFlagStatus.IMPORT.getValue());
					
				}
				
				if(! (dropList == null || dropList.isEmpty())){
					WHoddDTO exportODDDTO = new WHoddDTO();
					if (gateInRequest.isOddReject()) {
						exportODDDTO.setGateInStatus(TransactionStatus.REJECT.getValue());
					}
					dropList.forEach(bkgDetail -> {
							setODDContainerInfo(exportODDDTO, bkgDetail, ImpExpFlagStatus.EXPORT);

					});
					
					oddInfoList.add(exportODDDTO);
					gateInResponse.setImpExpFlagStatus(ImpExpFlagStatus.EXPORT.getValue());
					
				}
				
				if(!pickupList.isEmpty() && !dropList.isEmpty()){
					gateInResponse.setImpExpFlagStatus(ImpExpFlagStatus.IMPORT_EXPORT.getValue());
				}
				
				gateInResponse.setTruckPlateNo(oddInfoList.get(0).getPmPlateNo());
				gateInResponse.setTruckHeadNo(oddInfoList.get(0).getPmHeadNo());
				gateInResponse.setWhoddContainers(oddInfoList);

			} else {
				throw new ResultsNotFoundException("Provided Bookings Incorrect. Results Not found !");
			}

		}

		return gateInResponse;

	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public WHoddDTO setODDContainerInfo(WHoddDTO oddDTO, HDBSBkgDetail bkgDetails, ImpExpFlagStatus impExpFlag) {

		ODDContainerDetailsDTO containerDetailsDTO = new ODDContainerDetailsDTO();
		ODDLocationDTO oddLocationDTO = new ODDLocationDTO();
		containerDetailsDTO.setLocation(oddLocationDTO);
		containerDetailsDTO.setContainerNo(bkgDetails.getContainerNo());
		containerDetailsDTO.getLocation().setOddCode(bkgDetails.gethDBSBkgMaster().getDepotCode());
		containerDetailsDTO.setHdbsBkgDetailNoId(bkgDetails.getHdbsBKGDetailID());
		containerDetailsDTO.setHdbsStatus(bkgDetails.getStatusCode().getValue());
		containerDetailsDTO.setContainerSize(
				bkgDetails.getContainerSize() == null ? "" : bkgDetails.getContainerSize().getValue());

		if (StringUtils.isNotEmpty(bkgDetails.gethDBSBkgMaster().getDepotCode())) {
			Optional<ODDLocation> optLocation = oddLocationRepository
					.findByOddCodeAndStatusCode(bkgDetails.gethDBSBkgMaster().getDepotCode(), RecordStatus.ACTIVE);

			ODDLocation oddLocation = optLocation.orElseThrow(() -> new BusinessException(
					"Depot Code " + bkgDetails.gethDBSBkgMaster().getDepotCode() + " Not found in SCSS ODD Location"));

			ODDLocationDTO location = modelMapper.map(oddLocation, ODDLocationDTO.class);
			containerDetailsDTO.setLocation(location);

		}

		if (LocalDateTime.now().isAfter(bkgDetails.getApptDateTimeToActual())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.LATE);
		} else if (LocalDateTime.now().isAfter(bkgDetails.getApptDateTimeFrom())
				&& LocalDateTime.now().isBefore(bkgDetails.getApptDateTimeToActual())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.ACTIVE);
		} else if (LocalDateTime.now().isBefore(bkgDetails.getApptDateTimeFrom())) {
			containerDetailsDTO.setHdbsArrivalStatus(ApplicationConstants.EARLY);
		}
		
		oddDTO.setPmHeadNo(bkgDetails.gethDBSBkgMaster().getPmHeadNo());
		oddDTO.setPmPlateNo(bkgDetails.gethDBSBkgMaster().getPlateNo());
		
		if (oddDTO.getContainer01() == null) {
			oddDTO.setContainer01(containerDetailsDTO);
			oddDTO.setImpExpFlag(impExpFlag.getValue());
		} else {
			oddDTO.setContainer02(containerDetailsDTO);
		}

		return oddDTO;

	}

}
