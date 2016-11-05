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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.exception.ResultsNotFoundException;
import com.privasia.scss.core.model.Card;
import com.privasia.scss.core.model.HPATBooking;
import com.privasia.scss.core.predicate.HPATBookingPredicates;
import com.privasia.scss.core.repository.CardRepository;
import com.privasia.scss.core.repository.HPATBookingRepository;
import com.privasia.scss.core.util.constant.BookingType;
import com.privasia.scss.core.util.constant.HpatReferStatus;
import com.privasia.scss.hpat.dto.HpatDto;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
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
  public List<HpatDto> getEtpHpat4ImpAndExp(long cardId, LocalDateTime date, List<BookingType> bookingTypes) {
    List<HpatDto> dtoList = new ArrayList<HpatDto>();
    Optional<Card> card = cardRepository.findOne(cardId);
    Predicate byCardNo = HPATBookingPredicates.byCardNo(String.valueOf(card.get().getCardNo()));
    Predicate byBookingStatus = HPATBookingPredicates.byBookingStatus(HpatReferStatus.ACTIVE.getValue());
    Predicate byAppointmentEndDate = HPATBookingPredicates.byAppointmentEndDate(date);
    Predicate condition = null;
    if (!(bookingTypes == null || bookingTypes.isEmpty())) {
      Predicate byBookingTypes = HPATBookingPredicates.byBookingDetailTypes(bookingTypes);
      condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate, byBookingTypes);
    } else {
      condition = ExpressionUtils.allOf(byCardNo, byBookingStatus, byAppointmentEndDate);
    }
    OrderSpecifier<LocalDateTime> sortSpec = HPATBookingPredicates.orderByAppointmentStartDateAsc();

    Iterable<HPATBooking> bookingList = hpatBookingRepository.findAll(condition, sortSpec);

    bookingList.forEach((HPATBooking b) -> {
      dtoList.add(new HpatDto(b));
    });
    return dtoList;
  }


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<HpatDto> findEtpHpat4ImpAndExp(Long cardId, LocalDateTime systemDateTime, List<String> bookingTypes) throws ResultsNotFoundException {
	  
    List<HpatDto> hpats = null;
    
    List<BookingType> convertedBookingTypes = new ArrayList<>();
    
    bookingTypes.forEach(bookingType -> {
    	  convertedBookingTypes.add(BookingType.fromValue(bookingType));
    });

    hpats = getEtpHpat4ImpAndExp(cardId, systemDateTime, convertedBookingTypes);
    if (!(hpats == null || hpats.isEmpty())) {
     
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
      // need to discuss with etp team to manage web services between etp and scss
      throw new ResultsNotFoundException("No Hpat Records were found!");
    }
  }



}
