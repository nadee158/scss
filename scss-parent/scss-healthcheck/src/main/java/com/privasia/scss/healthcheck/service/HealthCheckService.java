package com.privasia.scss.healthcheck.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.HealthCheckInfoDTO2;
import com.privasia.scss.core.model.KioskHLTCheck;
import com.privasia.scss.core.repository.ClientRepository;
import com.privasia.scss.core.repository.KioskHLTCheckRepository;

@Service("healthCheckService")
@Transactional
public class HealthCheckService {

  private static final Log log = LogFactory.getLog(HealthCheckService.class);

  @Autowired
  private KioskHLTCheckRepository kioskHLTCheckRepository;

  @Autowired
  private ClientRepository clientRepository;

  @Autowired
  private ModelMapper modelMapper;


  @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
  public List<HealthCheckInfoDTO2> getHealthCheckInfo(int size) {

    log.info("******* Request for Get Health Check Info ******* ");

    Pageable pageRequest = new PageRequest(0, size, Sort.Direction.ASC, "dateTimeAdd");

    Page<KioskHLTCheck> kioskHealthCheckPages = kioskHLTCheckRepository.kioskHealthCheckInfo(pageRequest);

    List<KioskHLTCheck> kioskHealthCheckInfoList = kioskHealthCheckPages.getContent();
    log.info("******* Response for Get Kiosk Health Check Info******* ");

    if (kioskHealthCheckInfoList != null && !kioskHealthCheckInfoList.isEmpty()) {
      log.info("Response  HealthCheckInfo Size : " + kioskHealthCheckInfoList.size());

      return kioskHealthCheckInfoList.stream().map(kioskHLTCheck -> convertDomainToDto(kioskHLTCheck))
          .collect(Collectors.toList());

    }
    return null;
  }

  @Transactional(propagation = Propagation.REQUIRED, readOnly = false)
  public Map<String, String> saveKioskHealthCheckInfo(HealthCheckInfoDTO2 healthCheckInfo) {
    Map<String, String> resultMap = new HashMap<String, String>();

    log.info("******* Request for Save Kiosk Health Check Info ******* ");
    log.info("Request  Health Check Info : " + healthCheckInfo);


    KioskHLTCheck kioskHLTCheck = convertDTOToDomain(healthCheckInfo);

    System.out.println("getLaneNumber : " + kioskHLTCheck.getLaneNumber());
    System.out.println("getCardReaderStatus : " + kioskHLTCheck.getCardReaderStatus());
    System.out.println("getKiosk().getClientID() : " + kioskHLTCheck.getKiosk().getClientID());
    System.out.println("getBooth().getClientID() : " + kioskHLTCheck.getBooth().getClientID());
    System.out.println("getCameraStatus : " + kioskHLTCheck.getCameraStatus());
    System.out.println("getPaperStatus : " + kioskHLTCheck.getPaperStatus());

    /*
     * KioskHLTCheck kioskHLTCheck = new KioskHLTCheck(); if
     * (StringUtils.isNotEmpty(healthCheckInfo.getKioskID())) { long kioskId =
     * Long.parseLong(healthCheckInfo.getKioskID()); Optional<Client> client =
     * clientRepository.findOne(kioskId); if (client.isPresent()) {
     * kioskHLTCheck.setKiosk(client.get()); } }
     * 
     * if (StringUtils.isNotEmpty(healthCheckInfo.getBoothID())) { long boothId =
     * Long.parseLong(healthCheckInfo.getBoothID()); Optional<Client> client =
     * clientRepository.findOne(boothId); if (client.isPresent()) {
     * kioskHLTCheck.setBooth(client.get()); } }
     * 
     * KioskHLTCheck persisted = kioskHLTCheckRepository.save(kioskHLTCheck);
     * 
     * if (persisted.getHealthCheckSeq() > 0) { resultMap.put(ApplicationConstants.MESSAGE_CODE,
     * ApplicationConstants.MESSAGE_OK); resultMap.put(ApplicationConstants.MESSAGE_DESCRIPTION,
     * CommonUtil.formatMessageCode("SCSS_KIOSK_HEALTH_CHECK_CODE01", null)); } else {
     * resultMap.put(ApplicationConstants.MESSAGE_CODE, ApplicationConstants.MESSAGE_NOK);
     * resultMap.put(ApplicationConstants.MESSAGE_DESCRIPTION,
     * CommonUtil.formatMessageCode("SCSS_KIOSK_HEALTH_CHECK_ERR_CODE02", null)); }
     * 
     * log.info("******* Response for Save Kiosk Health Check Info******* "); log.info(
     * "Response  resultMap Info : " + resultMap);
     */

    return resultMap;

  }


  private HealthCheckInfoDTO2 convertDomainToDto(KioskHLTCheck healthCheckInfo) {
    HealthCheckInfoDTO2 healthCheckInfoDTO2 = modelMapper.map(healthCheckInfo, HealthCheckInfoDTO2.class);
    return healthCheckInfoDTO2;
  }

  private KioskHLTCheck convertDTOToDomain(HealthCheckInfoDTO2 healthCheckInfoDTO2) {

	  KioskHLTCheck healthCheckInfo = modelMapper.map(healthCheckInfoDTO2, KioskHLTCheck.class);
	  
	  return healthCheckInfo;

  }
  
  
}
