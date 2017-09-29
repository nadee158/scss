/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.Optional;

import org.springframework.data.querydsl.QueryDslPredicateExecutor;

import com.privasia.scss.core.model.LaneOpen;

/**
 * @author Janaka
 *
 */
public interface LaneOpenRepository extends BaseRepository<LaneOpen, Long>, QueryDslPredicateExecutor<LaneOpen> {

  Optional<LaneOpen> findByLaneID_ClientID(Long laneID);


}
