/**
 * 
 */
package com.privasia.scss.hpat.service;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingDetailPredicates;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;

/**
 * @author Janaka
 *
 */
@Service("hpatService")
public class HPATService {
	
	@Autowired
	private HPATBookingRepository hpatBookingRepository;
	
	@Autowired
	private CardRepository cardRepository;
	
	 @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
	 public void findEtpHpat4ImpAndExp(Long cardID) {
		 
		 Optional<Card>  card = cardRepository.findOne(cardID);
		 Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
		 Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getHpatStatus());
		 Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(LocalDateTime.now());
		 Predicate byBookingTypes = HPATBookingDetailPredicates.byBookingTypes(Arrays.asList(BookingType.EXPORT, BookingType.IMPORT, BookingType.IMPORT_ITT));
		 Predicate condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
		 Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition);
		 
		 bookingList.forEach((HPATBooking b) -> System.out.print(b.getBookingID()));

	 }
	
	
	
}
