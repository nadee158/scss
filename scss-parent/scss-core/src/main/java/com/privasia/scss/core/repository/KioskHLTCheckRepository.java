package com.privasia.scss.core.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.core.model.KioskHLTCheck;

public interface KioskHLTCheckRepository extends BaseRepository<KioskHLTCheck, Long> {

  @Query(name = "KioskHLTCheck.KioskHealthCheckInfo")
  public Page<KioskHLTCheck> listLimitedEntities(Pageable pageRequest);

}
