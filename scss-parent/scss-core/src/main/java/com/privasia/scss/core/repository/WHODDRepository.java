package com.privasia.scss.core.repository;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.WHODD;

public interface WHODDRepository extends BaseRepository<WHODD, Long> {

  public Long countByCard_CardIDAndContainer01_OddStatus(long cardId, TransactionStatus transactionStatus);

}
