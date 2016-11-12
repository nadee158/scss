package com.privasia.scss.core.repository;

import org.jadira.usertype.spi.repository.BaseRepository;

import com.privasia.scss.core.model.GatePass;
import com.privasia.scss.core.util.constant.GatePassStatus;
import com.privasia.scss.core.util.constant.TransactionStatus;

public interface GatePassRepository extends BaseRepository<GatePass, Long> {

  long countByCommonGateInOut_EirStatusAndGatePassNo(TransactionStatus status, long gatePassNumber);

  long countByGatePassStatusAndGatePassNo(GatePassStatus gatePassStatus, long gatePassNumber);

  long countByCommonGateInOut_EirStatusAndGatePassStatusAndGatePassNo(TransactionStatus transactionStatus,
      GatePassStatus gatePassStatus, long gatePassNumber);

}
