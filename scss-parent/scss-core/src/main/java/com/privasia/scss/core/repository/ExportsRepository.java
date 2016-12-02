package com.privasia.scss.core.repository;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.Exports;

public interface ExportsRepository extends BaseRepository<Exports, Long> {

  public Long countByCommonGateInOut_Card_CardIDAndCommonGateInOut_EirStatus(long cardId,
      TransactionStatus transactionStatus);

}
