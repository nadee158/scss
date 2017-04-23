package com.privasia.scss.core.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Exports;

public interface ExportsRepository extends BaseRepository<Exports, Long> {

  @Query(name = "Exports.countByCardIdAndEirStatus", nativeQuery = true)
  public Long countRecordsByCardIdAndEirStatus(@Param("cardID") long cardId, @Param("eirStatus") String eirStatus);
  
  @Query(name = "Exports.fetchInProgressTransaction")
  public Optional<List<Exports>> fetchInProgressTransaction(@Param("cardID") long cardId, @Param("eirStatus") TransactionStatus eirStatus);
  
  public Optional<List<Exports>> findByExportIDIn(List<Long> expIDList);

}
