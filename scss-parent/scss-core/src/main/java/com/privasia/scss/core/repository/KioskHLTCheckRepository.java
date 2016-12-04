package com.privasia.scss.core.repository;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.privasia.scss.core.model.KioskHLTCheck;

public interface KioskHLTCheckRepository extends BaseRepository<KioskHLTCheck, Long> {

  @Query(name = "KioskHLTCheck.KioskHealthCheckInfo")
  public Page<KioskHLTCheck> kioskHealthCheckInfo(Pageable pageRequest);

  // @Query(name = "KioskHLTCheck.KioskHealthCheckInfoForNofitication")
  public List<KioskHLTCheck> findByNotificationStatus(Boolean notificationStatus, Pageable pageRequest);

  @Query(name = "KioskHLTCheck.CountHealthCheckInfoForNofitication")
  public Long getCountHealthCheckInfoForNofitication(Boolean notificationStatus);



}
