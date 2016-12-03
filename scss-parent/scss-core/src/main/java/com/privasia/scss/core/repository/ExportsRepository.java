package com.privasia.scss.core.repository;

import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Exports;

public interface ExportsRepository extends BaseRepository<Exports, Long> {

  @Query(name = "Exports.countRecordsByCardIdAndEirStatus", nativeQuery = true)
  public Long countRecordsByCardIdAndEirStatus(long cardId, TransactionStatus eirStatus);

}
