package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.WDCGatePass;

public interface WDCGatePassRepository extends BaseRepository<WDCGatePass, Long> {
	
  public Optional<WDCGatePass> findByGatePassNo(long gatePassNumber);
  
  @Query(value="WDCGatePass.findByGatePassNOIn")
  public Optional<List<WDCGatePass>> findByGatePassNOIn(@Param("gatePassNoList") List<Long> gatePassNoList);

}
