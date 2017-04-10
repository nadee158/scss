package com.privasia.scss.hpat.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.hpat.dto.HpatDetailsResponseType;

@Service("etpService")
public class EtpService {

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public HpatDetailsResponseType[] getHpatDetails(String smartCardNo, String icNo, String compCode,
      LocalDateTime driverArrivalDate) throws Exception {

    return null;
  }

}
