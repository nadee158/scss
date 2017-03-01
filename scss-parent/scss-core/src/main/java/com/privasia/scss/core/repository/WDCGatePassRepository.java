package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import com.privasia.scss.core.model.WDCGatePass;

public interface WDCGatePassRepository extends BaseRepository<WDCGatePass, Long> {

  Optional<WDCGatePass> findByGatePassNO(String gatePassNo);
  
  Optional<List<WDCGatePass>> findByGatePassNOIn(List<Long> gatePassNoList);

}
