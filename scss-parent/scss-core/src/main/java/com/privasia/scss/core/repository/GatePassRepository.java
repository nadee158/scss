package com.privasia.scss.core.repository;



import java.util.Optional;

import com.privasia.scss.core.model.GatePass;

public interface GatePassRepository extends BaseRepository<GatePass, Long> {

  Optional<GatePass> findByGatePassNo(long gatePassNumber);

  Optional<GatePass> findByGatePassNoAndCompany_companyID(long gatePassNo1, long companyID);

}
