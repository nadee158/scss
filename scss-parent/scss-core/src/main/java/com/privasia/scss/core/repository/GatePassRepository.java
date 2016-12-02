package com.privasia.scss.core.repository;



import java.util.Optional;

import com.privasia.scss.common.enums.TransactionStatus;
import com.privasia.scss.core.model.GatePass;

public interface GatePassRepository extends BaseRepository<GatePass, Long> {

  public Optional<GatePass> findByGatePassNo(long gatePassNumber);

  public Optional<GatePass> findByGatePassNoAndCompany_companyID(long gatePassNo1, long companyID);

  public Long countByCommonGateInOut_Card_CardIDAndCommonGateInOut_EirStatus(long cardId,
      TransactionStatus transactionStatus);

}
