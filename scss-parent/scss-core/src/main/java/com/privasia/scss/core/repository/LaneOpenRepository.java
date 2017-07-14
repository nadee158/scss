/**
 * 
 */
package com.privasia.scss.core.repository;

import java.util.Optional;

import com.privasia.scss.core.model.LaneOpen;

/**
 * @author Janaka
 *
 */
public interface LaneOpenRepository extends BaseRepository<LaneOpen, Long> {

  Optional<LaneOpen> findByLaneID_ClientID(Long laneID);


}
