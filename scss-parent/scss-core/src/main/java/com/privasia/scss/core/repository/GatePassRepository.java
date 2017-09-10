package com.privasia.scss.core.repository;



import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.GatePass;

public interface GatePassRepository extends BaseRepository<GatePass, Long>, QueryDslPredicateExecutor<GatePass> {

  public Optional<GatePass> findByGatePassNo(long gatePassNumber);

  public Optional<GatePass> findByGatePassNoAndCompany_companyID(long gatePassNo1, long companyID);

  @Query(name = "GatePass.countByCardIdAndEirStatus", nativeQuery = true)
  public Long countRecordsByCardIdAndEirStatus(@Param("cardID") long cardId, @Param("eirStatus") String eirStatus);

  public Optional<List<GatePass>> findByGatePassNoIn(List<Long> gatePassNumberList);

  @Query(name = "GatePass.findContainerNoByGatePassNo")
  public String findContainerNoByGatePassNo(@Param("gatePassNo") long gatePassNo);

  @Query(name = "GatePass.fetchInProgressTransaction")
  public Optional<List<GatePass>> fetchInProgressTransaction(@Param("cardID") long cardId, @Param("comID") long comId,
      @Param("eirStatus") TransactionStatus eirStatus);
  
  @Query(name = "Eir.getNextPrintEIRNo",nativeQuery = true)
  public Long getNextPrintEIRNo();
}
