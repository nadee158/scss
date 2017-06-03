/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.GateInReponse;
import com.privasia.scss.common.dto.GateInRequest;
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
import com.privasia.scss.core.model.HDBSBkgMaster;
import com.privasia.scss.core.model.ODDLocation;
import com.privasia.scss.core.predicate.HDBSBookingMasterPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.HDBSBookingMasterRepository;
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

	private HDBSBookingMasterRepository hdbsBookingMasterRepository;

	private CardRepository cardRepository;

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;

	private ModelMapper modelMapper;

	private ODDLocationRepository oddLocationRepository;

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
	public Optional<List<HDBSBkgDetailGridDTO>> createPredicatesAndFindHDBS(Card card, LocalDateTime dateFrom,
			LocalDateTime dateTo, List<HDBSStatus> statusList) {

		final Optional<List<HDBSBkgDetailGridDTO>> hdbsBookingList = Optional.of(new ArrayList<HDBSBkgDetailGridDTO>());

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

		Iterator<HDBSBkgMaster> bookingIterator = bookingList.iterator();

		if (bookingIterator.hasNext()) {
			bookingList.forEach((hdbsbkgMaster) -> {
				hdbsbkgMaster.getHdbsBookingDetails().forEach(detail -> {
					HDBSBkgDetailGridDTO hdbs = constructDetailGridDetailDTO(detail);
					setDuration(hdbs);
					hdbsBookingList.get().add(hdbs);
				});

			});
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
	public GateInReponse populateGateInODD(GateInRequest gateInRequest) {

		GateInReponse gateInReponse = new GateInReponse();
		gateInReponse.setGateINDateTime(gateInRequest.getGateInDateTime());

		if (gateInRequest.getBkgDetailIDList().isPresent() && !gateInRequest.getBkgDetailIDList().get().isEmpty()) {

			List<String> bkgDetailIDList = gateInRequest.getBkgDetailIDList().get();

			Optional<Card> optionalCard = cardRepository.findOne(gateInRequest.getCardID());

			optionalCard.orElseThrow(() -> new BusinessException("Scan Card was not found " + optionalCard.get()));

			Predicate byCardNo = HDBSBookingMasterPredicates.byCardNo(String.valueOf(optionalCard.get().getCardNo()));
			Predicate byDrayageBooking = HDBSBookingMasterPredicates.byDrayageBooking(0);
			Predicate byNullableSCSS = HDBSBookingMasterPredicates.byNullableSCSSStatusCode();
			Predicate byBookingIDList = HDBSBookingMasterPredicates.byHDBSBookingDetailByIDList(bkgDetailIDList);

			Predicate condition = ExpressionUtils.allOf(byCardNo, byDrayageBooking, byNullableSCSS, byBookingIDList);

			OrderSpecifier<LocalDateTime> orderByAPPStartDate = HDBSBookingMasterPredicates
					.orderByAppointmentStartDateAsc();

			Iterable<HDBSBkgMaster> bookingList = hdbsBookingMasterRepository.findAll(condition, orderByAPPStartDate);

			Iterator<HDBSBkgMaster> bookingIterator = bookingList.iterator();

			List<WHoddDTO> oddInfoList = new ArrayList<WHoddDTO>();

			if (bookingIterator.hasNext()) {
				while (bookingIterator.hasNext()) {

					HDBSBkgMaster bkgMaster = bookingIterator.next();

					boolean pickup = bkgMaster.getHdbsBookingDetails().stream().filter(bkgDetail -> StringUtils
							.equalsIgnoreCase(bkgDetail.getHdbsBkgType().getValue(), HDBSBookingType.PICKUP.getValue()))
							.findAny().isPresent();

					if (pickup) {
						WHoddDTO importODDDTO = new WHoddDTO();
						if (gateInRequest.isOddReject()) {
							importODDDTO.setGateInStatus(TransactionStatus.REJECT.getValue());
						}

						bkgMaster.getHdbsBookingDetails().stream().filter(bkgDetail -> {
							return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.PICKUP);
						}).forEach(bkgDetail -> {
							setODDContainerInfo(importODDDTO, bkgDetail, ImpExpFlagStatus.IMPORT);
							oddInfoList.add(importODDDTO);

						});

						gateInReponse.setImpExpFlagStatus(ImpExpFlagStatus.IMPORT.getValue());
					}

					boolean drop = bkgMaster.getHdbsBookingDetails().stream().filter(bkgDetail -> StringUtils
							.equalsIgnoreCase(bkgDetail.getHdbsBkgType().getValue(), HDBSBookingType.DROP.getValue()))
							.findAny().isPresent();

					if (drop) {
						WHoddDTO exportODDDTO = new WHoddDTO();
						if (gateInRequest.isOddReject()) {
							exportODDDTO.setGateInStatus(TransactionStatus.REJECT.getValue());
						}
						bkgMaster.getHdbsBookingDetails().stream().filter(bkgDetail -> {
							return bkgDetail.getHdbsBkgType().equals(HDBSBookingType.DROP);
						}).forEach(bkgDetail -> {
							setODDContainerInfo(exportODDDTO, bkgDetail, ImpExpFlagStatus.EXPORT);
							oddInfoList.add(exportODDDTO);
						});

						gateInReponse.setImpExpFlagStatus(ImpExpFlagStatus.EXPORT.getValue());
					}

					gateInReponse.setTruckPlateNo(bkgMaster.getPlateNo());
					gateInReponse.setTruckHeadNo(bkgMaster.getPmHeadNo());
					gateInReponse.setWhoddContainers(oddInfoList);

					if (drop && pickup) {
						gateInReponse.setImpExpFlagStatus(ImpExpFlagStatus.IMPORT_EXPORT.getValue());
					}
				}
			} else {
				throw new ResultsNotFoundException("Provided Bookings Incorrect. Results Not found !");
			}

		}

		return gateInReponse;

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

		if (oddDTO.getContainer01() == null) {
			oddDTO.setContainer01(containerDetailsDTO);
			oddDTO.setImpExpFlag(impExpFlag.getValue());
		} else {
			oddDTO.setContainer02(containerDetailsDTO);
		}

		return oddDTO;

	}

}
