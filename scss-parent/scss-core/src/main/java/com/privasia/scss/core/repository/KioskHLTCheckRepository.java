package com.privasia.scss.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.common.dto.HealthCheckInfoDTO;
import com.privasia.scss.core.model.KioskHLTCheck;

public interface KioskHLTCheckRepository extends BaseRepository<KioskHLTCheck, Long> {

  @Query(name = "KioskHLTCheck.KioskHealthCheckInfo", nativeQuery = true)
  public List<HealthCheckInfoDTO> listLimitedEntities(int limit);

}
