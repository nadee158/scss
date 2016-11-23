/**
 * 
 */
package com.privasia.scss.hdbs.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.core.exception.BusinessException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HDBSBkgDetail;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HDBSBookingDetailRepository;
import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

/**
 * @author Janaka
 *
 */
@Service("hdbsService")
public class HDBSService {
	
	@Autowired
	private HDBSBookingDetailRepository hdbsBookingDetailRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	@Autowired
	private WDCGlobalSettingRepository wdcGlobalSettingRepository;
	
	public List<HDBSBkgDetail> findHDBSBookingDetailByIDList(List<String> bkgDetailIDList){
		
		Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);
		
		return bkgDetails.collect(Collectors.toList());
	}
	
	
	public List<HDBSBkgDetail> findHDBSBookingDetailByCard(Long cardID){
		
		
		Optional<Card> optionalCard = cardRepository.findOne(cardID);
		
		optionalCard.orElseThrow(()-> new BusinessException("Scan Card was not found "+ cardID));
		
		LocalDateTime systemDateTime = LocalDateTime.now();
		//set the value in the hdbsbooking dto
	    //f.setArrivalTime(systemDateTime);
		
		Optional<Integer> optionalHdbsStart = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_START_HOUR);
		int hdbsStart = optionalHdbsStart.orElse(-6);
		
		Optional<Integer> optionalHdbsEnd = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_END_HOUR);
		int hdbsEnd = optionalHdbsEnd.orElse(6);
		
		Optional<Integer> optionalHdbsAcceptedStart = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_START);
		int hdbsAcceptStart = optionalHdbsAcceptedStart.orElse(-6);
		
		Optional<Integer> optionalHdbsAcceptedEnd = wdcGlobalSettingRepository.fetchGlobalItemsByGlobalCode(ApplicationConstants.HDBS_ACCEPTED_END);
		int hdbsAcceptEnd = optionalHdbsAcceptedEnd.orElse(6);
		
		Optional<String> optionalHdbsManual = wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode(ApplicationConstants.HDBS_MANUAL);
		//need to do
		//f.setShowManual(optionalHdbsManual.orElse(StringUtils.EMPTY));
		
		
		LocalDateTime dateFrom = systemDateTime.plusHours(hdbsStart);//hpatStart
		LocalDateTime dateTo = systemDateTime.plusHours(hdbsEnd);//hpatEnd
		
		
		
		
		//Stream<HDBSBkgDetail> bkgDetails = hdbsBookingDetailRepository.findByHdbsBKGDetailIDIn(bkgDetailIDList);
		
		//return bkgDetails.collect(Collectors.toList());
		
		return null;
	}

}
