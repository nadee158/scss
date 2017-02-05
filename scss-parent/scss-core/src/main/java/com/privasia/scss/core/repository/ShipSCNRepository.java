package com.privasia.scss.core.repository;

import java.util.Optional;

import com.privasia.scss.core.model.ShipSCN;

public interface ShipSCNRepository extends BaseRepository<ShipSCN, Long> {

  Optional<ShipSCN> findByContainerNo(String containerNumber);

}
