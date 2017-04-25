/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.Optional;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.HPABBooking;

/**
 * @author Janaka
 *
 */
public interface HPABBookingRepository
    extends BaseRepository<HPABBooking, String>, QueryDslPredicateExecutor<HPABBooking> {

  Optional<HPABBooking> findByBookingIDAndStatus(String bookingID, HpatReferStatus active);


}
