/**
 * 
 */
package com.privasia.scss.core.repository;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.core.model.HPATBooking;

/**
 * @author Janaka
 *
 */
public interface HPATBookingRepository extends BaseRepository<HPATBooking, String>, QueryDslPredicateExecutor<HPATBooking> {

  
}
