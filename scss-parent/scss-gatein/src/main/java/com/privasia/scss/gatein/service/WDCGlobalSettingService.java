package com.privasia.scss.gatein.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.repository.GlobalSettingRepository;

@Service("wdcGlobalSettingService")
@Transactional
public class WDCGlobalSettingService {

  @Autowired
  private GlobalSettingRepository globalSettingRepository;

  public String getWDCGlobalSeeting(String globalCode) {
    return null;
  }

}
