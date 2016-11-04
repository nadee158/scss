package com.privasia.scss.hpat.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.privasia.scss.hpat.dto.HpatDetailsResponseType;

@Service("etpService")
public class EtpService {

  public HpatDetailsResponseType[] getHpatDetails(String smartCardNo, String icNo, String compCode,
      LocalDateTime driverArrivalDate) throws Exception {

    return null;
  }

}
