package com.privasia.scss.core.repository;



import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.core.model.GatePass;

public interface GatePassRepository extends BaseRepository<GatePass, Long> {

  public Optional<GatePass> findByGatePassNo(long gatePassNumber);

  public Optional<GatePass> findByGatePassNoAndCompany_companyID(long gatePassNo1, long companyID);

  @Query(name = "GatePass.countByCardIdAndEirStatus", nativeQuery = true)
  public Long countRecordsByCardIdAndEirStatus(@Param("cardID") long cardId, @Param("eirStatus") String eirStatus);

 
}
