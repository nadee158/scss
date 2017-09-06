/**
 * 
 */
package com.privasia.scss.hpat.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInRequest;
import com.privasia.scss.common.dto.GateInResponse;
import com.privasia.scss.common.dto.HpatDto;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.TransactionDTO;
import com.privasia.scss.common.enums.BookingType;
import com.privasia.scss.common.enums.HpabReferStatus;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPABBooking;
import com.privasia.scss.core.predicate.HPABBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPABBookingRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;
import com.privasia.scss.etpws.service.client.ETPWebserviceClient;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hpabService")
public class HPABService {

	private HPABBookingRepository hpabBookingRepository; 

	private CardRepository cardRepository;

	private ETPWebserviceClient etpWebserviceClient;

	private WDCGlobalSettingRepository wdcGlobalSettingRepository;
	

	@Autowired
	public void setHpabBookingRepository(HPABBookingRepository hpabBookingRepository) {
		this.hpabBookingRepository = hpabBookingRepository;
	}
	
	@Autowired
	public void setCardRepository(CardRepository cardRepository) {
		this.cardRepository = cardRepository;
	}

	@Autowired
	public void setEtpWebserviceClient(@Qualifier("etpWebserviceClient") ETPWebserviceClient etpWebserviceClient) {
		this.etpWebserviceClient = etpWebserviceClient;
	}

	@Autowired
	public void setWdcGlobalSettingRepository(WDCGlobalSettingRepository wdcGlobalSettingRepository) {
		this.wdcGlobalSettingRepository = wdcGlobalSettingRepository;
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public List<HpatDto> createPredicatesAndFindHpab4ImpAndExp(Long cardId, LocalDateTime date,
			List<BookingType> bookingTypes, boolean importOnly) {
		List<HpatDto> dtoList = new ArrayList<HpatDto>();
		Optional<Card> card = cardRepository.findOne(cardId); // need to handle
																// optional
		if (card.isPresent()) {
			Predicate byCardNo = HPABBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
			Predicate byBookingStatus = HPABBookingPredicates.byBookingStatus(HpabReferStatus.ACTIVE.getValue()); 
			Predicate byAppointmentEndDate = HPABBookingPredicates.byAppointmentEndDate(date);
			Predicate byBookingTypes = HPABBookingPredicates.byBookingDetailTypes(bookingTypes);
			Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate,
					byBookingTypes);
			OrderSpecifier<LocalDateTime> sortSpec = HPABBookingPredicates.orderByAppointmentStartDateAsc();

			Iterable<HPABBooking> bookingList = hpabBookingRepository.findAll(condition, sortSpec);

			bookingList.forEach((HPABBooking b) -> {
				HpatDto dto = b.constructHpatDto(importOnly);
				if(dto!=null){
					dtoList.add(b.constructHpatDto(importOnly));
				}
				
			});
			return dtoList;
		} else {
			throw new ResultsNotFoundException("No HPAB Bookings Founds !");
		}

	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public List<HpatDto> findEtpHpab4ImpAndExp(Long cardId, LocalDateTime systemDateTime, List<String> bookingTypes, boolean importOnly)
			throws ResultsNotFoundException {

		List<HpatDto> hpats = null;

		List<BookingType> convertedBookingTypes = new ArrayList<>();
		bookingTypes.forEach(bookingType -> {
			BookingType bk = BookingType.fromValue(bookingType);
			if (bk != null) {
				convertedBookingTypes.add(bk);
			} else {
				throw new BusinessException("Invalid Booking Type!");
			}
		});
		hpats = createPredicatesAndFindHpab4ImpAndExp(cardId, systemDateTime, convertedBookingTypes, importOnly);
		if (hpats != null && !hpats.isEmpty()) {

			for (HpatDto hpat : hpats) {
				
				if (StringUtils.isBlank(hpat.getApptStart()))
					continue;
				if (systemDateTime.isAfter(hpat.getApptEndDate()))
					hpat.setStatus(HpatDto.LATE);
				if (systemDateTime.isAfter(hpat.getApptStartDate()) && systemDateTime.isBefore(hpat.getApptEndDate())) {
					hpat.setStatus(HpatDto.ACTIVE);
					hpat.setOnTimeFlag("Y");
				} else if (systemDateTime.isBefore(hpat.getApptEndDate())) {
					hpat.setStatus(HpatDto.EARLY);
					hpat.setOnTimeFlag("N");
				}

			}
			Comparator<HpatDto> byApptStartDate = (o1, o2) -> o1.getApptStartDate().compareTo(o2.getApptStartDate());
			List<HpatDto> sortedHpats = new ArrayList<HpatDto>();
			hpats.stream().sorted(byApptStartDate).forEach(e -> sortedHpats.add(e));

			return sortedHpats;

		} else {
			// need to discuss with etp team to manage web services between etp
			// and scss
			throw new ResultsNotFoundException("No HPAT Bookings were found!");
		}
	}

	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public TransactionDTO getEtpHpab4ImpAndExp(String bookingID) {

		Optional<HPABBooking> hpatBooking = hpabBookingRepository.findByBookingIDAndStatus(bookingID,
				HpabReferStatus.ACTIVE);

		if (hpatBooking.isPresent()) {

			HPABBooking booking = hpatBooking.get();

			TransactionDTO transactionDTO = booking.constructTransactionDTO();

			if (!(booking.getHpabBookingDetails() == null || booking.getHpabBookingDetails().isEmpty())) {

				booking.getHpabBookingDetails().forEach(bookingDetail -> {

					BookingType bookingType = bookingDetail.getBookingType();
					bookingDetail.getImpGatePassNumber();
					switch (bookingType) {
					case IMPORT:

						if (transactionDTO.getImportContainer01() == null) {
							if (!(bookingDetail == null)) {
								transactionDTO.setImportContainer01(bookingDetail.constructImportContainer(null));
							}
						} else {
							if (!(bookingDetail == null)) {
								transactionDTO.setImportContainer02(bookingDetail.constructImportContainer(null));
							}
						}

						break;
					case EXPORT:

						if (transactionDTO.getExportContainer01() == null) {
							if (!(bookingDetail == null)) {
								transactionDTO.setExportContainer01(bookingDetail.constructExportContainer(null));
							}
						} else {
							transactionDTO.setExportContainer02(bookingDetail.constructExportContainer(null));
						}

						break;
					case IMPORT_ITT:

						if (transactionDTO.getImportContainer01() == null) {
							if (!(bookingDetail == null)) {
								transactionDTO.setImportContainer01(bookingDetail.constructImportContainer(null));
							}
						} else {
							if (!(bookingDetail == null)) {
								transactionDTO.setImportContainer02(bookingDetail.constructImportContainer(null));
							}
						}

						break;
					case EMPTY_PICKUP:

						break;
					case EMPTY_RETURN:

						break;

					default:
						break;
					}

				});
			}

			return transactionDTO;

		} else {
			// need to discuss with etp team to manage web services between etp
			// and scss
			throw new ResultsNotFoundException("No HPAT Booking was Found !");
		}
	}

	// rename method to populateHpabForImpExp
	// return GateOutReponse
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
	public GateInResponse populateHpabForImpExp(GateInResponse gateInResponse, GateInRequest gateInRequest) {

		Optional<HPABBooking> hpatBookingOpt = hpabBookingRepository.findByBookingIDAndStatus(gateInRequest.getHpabSeqId(),
				HpabReferStatus.ACTIVE);

		HPABBooking booking = hpatBookingOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid HPAB Bookibg ID ! " + gateInRequest.getHpabSeqId()));

		List<ImportContainer> updatedImportContainers = new ArrayList<ImportContainer>();
		List<ExportContainer> updatedExportContainers = new ArrayList<ExportContainer>();

		if (!(booking.getHpabBookingDetails() == null || booking.getHpabBookingDetails().isEmpty())) {
			
			gateInResponse.setTrailerPlate(booking.getTrailerNo());
			if(StringUtils.isNotEmpty(booking.getAxleWeight())){
				gateInResponse.setTrailerWeight(Integer.parseInt(booking.getAxleWeight()));
			}
			
			if(StringUtils.isNotEmpty(booking.getPmWeight())){
				gateInResponse.setTruckWeight(Integer.parseInt(booking.getPmWeight()));
			}
			gateInResponse.setTruckHeadNo(booking.getPmNumber());
			gateInResponse.setTruckPlateNo(booking.getTrailerPlate());
			
			gateInResponse.setPmVerified(booking.getPmVerified());
			gateInResponse.setAxleVerified(booking.getAxleVerified());
			gateInResponse.setHpabBookingId(booking.getBookingID());
			
			// construct DTO from domain
			booking.getHpabBookingDetails().forEach(bookingDetail -> {

				BookingType bookingType = bookingDetail.getBookingType();
				switch (bookingType) {
				case IMPORT:
				case IMPORT_ITT:
					/// create a import conatiner
					// fetch details from hpat
					// add to a list
					ImportContainer importContainer = null;
					if (!(gateInResponse.getImportContainers() == null
							|| gateInResponse.getImportContainers().isEmpty())) {
						importContainer = gateInResponse.getImportContainers().stream()
								.filter(e -> (e.getContainer() != null)
										&& (StringUtils.equals(e.getContainer().getContainerNumber(),
												bookingDetail.getContainerNumber())))
								.findFirst().get();
					}
					updatedImportContainers.add(bookingDetail.constructImportContainer(importContainer));
					break;
				case EXPORT:
					/// create a export conatiner
					// fetch details from hpat
					// add to a list

					ExportContainer exportContainer = null;
					if (!(gateInResponse.getExportContainers() == null
							|| gateInResponse.getExportContainers().isEmpty())) {
						exportContainer = gateInResponse.getExportContainers().stream()
								.filter(e -> (e.getContainer() != null)
										&& (StringUtils.equals(e.getContainer().getContainerNumber(),
												bookingDetail.getContainerNumber())))
								.findFirst().get();
					}

					ExportContainer exportContainerCons = bookingDetail.constructExportContainer(exportContainer);
					updatedExportContainers.add(exportContainerCons);
					break;
				case EMPTY_PICKUP:

					break;
				case EMPTY_RETURN:

					break;

				default:
					break;
				}

			});
		}

		if (!(updatedImportContainers == null || updatedImportContainers.isEmpty())) {
			gateInResponse.setImportContainers(updatedImportContainers);
		}

		if (!(updatedExportContainers == null || updatedExportContainers.isEmpty())) {
			gateInResponse.setExportContainers(updatedExportContainers);
		}

		return gateInResponse;

	}

	
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
	public void updateHPABAfterGateIn(String hpabID) {

		Optional<HPABBooking> hpabBookingOpt = hpabBookingRepository.findByBookingIDAndStatus(hpabID, 
				HpabReferStatus.ACTIVE);

		HPABBooking booking = hpabBookingOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid HPAB Bookibg ID Provided ! " + hpabID));

		booking.setStatus(HpabReferStatus.COMPLETE);

		hpabBookingRepository.save(booking);
	}
	
	
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = true)
	public void updateETPAfterGateIn(String hpabID) {

		Optional<String> optGlobalString = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode("ETP_HPAT"); 

		if (optGlobalString.isPresent()) {

			if (StringUtils.isNotBlank(optGlobalString.get())
					&& StringUtils.equalsIgnoreCase("Y", optGlobalString.get())) {
				etpWebserviceClient.updateHpabStatus(hpabID);
			}
		}
	}
	
	
	@Async
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
	public void updateHPABAfterGateOut(String hpabID) {

		Optional<HPABBooking> hpabBookingOpt = hpabBookingRepository.findByBookingIDAndStatus(hpabID, 
				HpabReferStatus.COMPLETE);

		HPABBooking booking = hpabBookingOpt
				.orElseThrow(() -> new ResultsNotFoundException("Invalid HPAB Bookibg ID Provided ! " + hpabID));

		booking.setStatus(HpabReferStatus.REJECT);

		hpabBookingRepository.save(booking);

		Optional<String> optGlobalString = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode("ETP_HPAT"); 

		if (optGlobalString.isPresent()) {

			if (StringUtils.isNotBlank(optGlobalString.get())
					&& StringUtils.equalsIgnoreCase("Y", optGlobalString.get())) {
				etpWebserviceClient.updateHpabRejectStatus(hpabID);
			}
		}

//		return true;

	}
	
}
