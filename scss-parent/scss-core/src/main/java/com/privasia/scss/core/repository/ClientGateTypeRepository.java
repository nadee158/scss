package com.privasia.scss.core.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.ClientGateType;

/**
 * @author Janaka
 *
 */

public interface ClientGateTypeRepository extends BaseRepository<ClientGateType, Long> {

  @Query(name = "ClientGateType.findAllGateTypeByClient")
  public Optional<List<String>> findAllGateTypeByClient(@Param("clientID") long clientID);

}
