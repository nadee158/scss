/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.Optional;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.common.enums.HpatReferStatus;
import com.privasia.scss.core.model.HPATBooking;

/**
 * @author Janaka
 *
 */
public interface HPATBookingRepository
    extends BaseRepository<HPATBooking, String>, QueryDslPredicateExecutor<HPATBooking> {

  Optional<HPATBooking> findByBookingIDAndStatus(String bookingID, HpatReferStatus active);


}
