package com.privasia.scss.core.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.repository.WDCGlobalSettingRepository;

@Service("wdcGlobalSettingService")
@Transactional
public class WDCGlobalSettingService {

  @Autowired
  private WDCGlobalSettingRepository wdcGlobalSettingRepository;

  public String getWDCGlobalSetting(String globalCode) {
    return wdcGlobalSettingRepository.fetchGlobalStringByGlobalCode(globalCode).orElse(null);
  }


}
