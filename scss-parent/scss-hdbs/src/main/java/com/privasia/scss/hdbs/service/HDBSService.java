/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.HDBSBookingDTO;
import com.privasia.scss.common.enums.HDBSStatus;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.exception.BusinessException;
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
	
	@Autowired
	private HDBSBookingDetailRepository hdbsBookingDetailRepository;
	
	@Autowired
	private HDBSBookingMasterRepository hdbsBookingMasterRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private WDCGlobalSettingRepository wdcGlobalSettingRepository;
	
	public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList){
		
		Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);
		
		return bkgDetails.collect(Collectors.toList());
	}
	
	@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	public List<HDBSBookingDTO> findHDBSBookingDetailByCard(Long cardID){
		
		
		Optional<Card> optionalCard = cardRepository.findOne(cardID);
		
		optionalCard.orElseThrow(()-> new BusinessException("Scan Card was not found "+ cardID));
		
		LocalDateTime systemDateTime = LocalDateTime.now();
		//set the value in the hdbsbooking dto
	    //f.setArrivalTime(systemDateTime);
		
		Optional<Integer> optionalHdbsStart = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_START_HOUR);
		int hdbsStart = optionalHdbsStart.orElse(ApplicationConstants.DEFAULT_HDBS_START_HOUR_VALUE);
		
		Optional<Integer> optionalHdbsEnd = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_END_HOUR);
		int hdbsEnd = optionalHdbsEnd.orElse(ApplicationConstants.DEFAULT_HDBS_END_HOUR_VALUE);
		
		Optional<Integer> optionalHdbsAcceptedStart = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_START);
		int hdbsAcceptStart = optionalHdbsAcceptedStart.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_START_VALUE);
		
		Optional<Integer> optionalHdbsAcceptedEnd = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_END);
		int hdbsAcceptEnd = optionalHdbsAcceptedEnd.orElse(ApplicationConstants.DEFAULT_HDBS_ACCEPTED_END_VALUE);
		
		Optional<String> optionalHdbsManual = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode(ApplicationConstants.HDBS_MANUAL);
		//need to do
		//f.setShowManual(optionalHdbsManual.orElse(StringUtils.EMPTY));
		
		
		LocalDateTime dateFrom = systemDateTime.plusHours(hdbsStart);//hpatStart
		LocalDateTime dateTo = systemDateTime.plusHours(hdbsEnd);//hpatEnd
		
		
		List<HDBSStatus> hdbsStatusList = new ArrayList<>();
		hdbsStatusList.add(HDBSStatus.NEW);
		hdbsStatusList.add(HDBSStatus.ACCEPTED);
		hdbsStatusList.add(HDBSStatus.REJECTED);
		hdbsStatusList.add(HDBSStatus.CANCELLED);
		
		List<HDBSBookingDTO> hdbsInfoList =  createPredicatesAndFindHDBS(optionalCard.get(), dateFrom, dateTo, hdbsStatusList);
		
		
		LocalDateTime acceptDateFrom =  systemDateTime.plusMinutes(hdbsAcceptStart);
		LocalDateTime acceptDateTo = systemDateTime.plusMinutes(hdbsAcceptEnd);
		
		hdbsInfoList.stream().filter(hdbs -> StringUtils.isNotBlank(hdbs.getApptStart()))
								.forEach(hdbs -> {
									if("ACCEPTED".equalsIgnoreCase(hdbs.getHdbsStatus())){
										
									}
								});
		
		//return bkgDetails.collect(Collectors.toList());
		
		return null;
	}
	
	
	public List<HDBSBookingDTO> createPredicatesAndFindHDBS(Card card, LocalDateTime dateFrom, LocalDateTime dateTo, List<HDBSStatus> statusList) {
	    
		List<HDBSBookingDTO> hdbsBookingList = new ArrayList<HDBSBookingDTO>();
	    
	    Predicate byCardNo = HDBSBookingMasterPredicates.byCardNo(String.valueOf(card.getCardNo()));
	    Predicate byAPPTDateFrom = HDBSBookingMasterPredicates.byApptDateTimeFrom(dateFrom);
	    Predicate byAPPTDateTo = HDBSBookingMasterPredicates.byApptDateTimeToActual(dateTo);
	    Predicate byDrayageBooking = HDBSBookingMasterPredicates.byDrayageBooking(0);
	    Predicate byHDBSStatus = HDBSBookingMasterPredicates.byHDBSStatusTypes(statusList);
	    Predicate byNullableSCSS = HDBSBookingMasterPredicates.byNullableSCSSStatusCode();
	   
	    Predicate condition = ExpressionUtils.allOf(byCardNo, byAPPTDateFrom, byAPPTDateTo, byDrayageBooking, byHDBSStatus, byNullableSCSS);
	    
	    OrderSpecifier<LocalDateTime>  orderByAPPStartDate = HDBSBookingMasterPredicates.orderByAppointmentStartDateAsc();
	    
	    Iterable<HDBSBkgMaster> bookingList = hdbsBookingMasterRepository.findAll(condition, orderByAPPStartDate);
	    
	    bookingList.forEach((HDBSBkgMaster) -> {
	    	hdbsBookingList.add(new HDBSBookingDTO());
	    });
	    
	    return hdbsBookingList;

	  }
	
	private void setAcceptBooking(HDBSBookingDTO hdbsBookingDTO, LocalDateTime acceptDateFrom, LocalDateTime dateTo){
		
		if(StringUtils.equals(HDBSStatus.ACCEPTED.getValue(), hdbsBookingDTO.getHdbsStatus())){
			
			/*if (hdbsBookingDTO.getApptStartDateFormat().after(acceptDateFrom) && 
					hdbsBookingForm.getApptStartDateFormat().before(acceptHdbsBookingDateTo)) {
					hdbsBookingForm.setIsAcceptBooking(true);
			}*/
			
		}
	}

}
