/**
 * 
 */
package com.privasia.scss.core.repository;


import java.util.List;
import java.util.Optional;

import com.privasia.scss.core.model.Customs;

/**
 * @author Janaka
 *
 */
public interface CustomsRepository extends BaseRepository<Customs, Long> {

  Optional<List<Customs>> deleteByCsmGateOutClient_ClientID(long clientID);
}
