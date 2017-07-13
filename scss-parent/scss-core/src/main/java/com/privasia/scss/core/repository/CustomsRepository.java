/**
 * 
 */
package com.privasia.scss.core.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.model.Customs;

/**
 * @author Janaka
 *
 */
public interface CustomsRepository extends BaseRepository<Customs, Long> {

  @Modifying
  @Query(name = "Customs.deleteByClientID")
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public void deleteByClientID(@Param("clientID") long clientID);

  @Query(name = "Customs.checkCustomStatus", nativeQuery = true)
  public String checkCustomStatus(@Param("clientID") long clientID);
}
