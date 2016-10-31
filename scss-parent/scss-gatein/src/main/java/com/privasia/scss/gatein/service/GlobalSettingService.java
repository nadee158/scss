package com.privasia.scss.gatein.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.privasia.scss.core.repository.GlobalSettingRepository;

@Service("globalSettingService")
@Transactional
public class GlobalSettingService {

  @Autowired
  private GlobalSettingRepository globalSettingRepository;

  public String getWDCGlobalSeeting(String globalCode) {
    return null;
  }

}
