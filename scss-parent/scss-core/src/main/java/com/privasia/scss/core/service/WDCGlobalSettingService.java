package com.privasia.scss.core.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

@Service("wdcGlobalSettingService")
public class WDCGlobalSettingService {

  @Autowired
  private WDCGlobalSettingRepository wdcGlobalSettingRepository;

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public String getWDCGlobalSetting(String globalCode) {
    return wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode(globalCode).orElse(null);
  }


}
