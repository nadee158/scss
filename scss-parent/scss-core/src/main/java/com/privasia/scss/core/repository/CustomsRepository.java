/**
 * 
 */
package com.privasia.scss.core.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.Customs;

/**
 * @author Janaka
 *
 */
public interface CustomsRepository extends BaseRepository<Customs, Long> {

  @Query(name="Customs.deleteByClientID")
  public void deleteByClientID(@Param("clientID") long clientID);
}
