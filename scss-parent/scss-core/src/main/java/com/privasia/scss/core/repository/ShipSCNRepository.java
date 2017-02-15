package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.core.model.ShipSCN;

public interface ShipSCNRepository extends BaseRepository<ShipSCN, Long> {

  Optional<ShipSCN> findByContainerNo(String containerNumber);
  
  @Query(name = "ShipSCN.fetchContainerSCN")
  Optional<List<ShipSCN>> fetchContainerSCN(String scn01, String container01, String scn02, String container02);

}
