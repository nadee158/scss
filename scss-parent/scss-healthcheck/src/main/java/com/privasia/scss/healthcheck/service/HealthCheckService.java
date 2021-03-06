package com.privasia.scss.healthcheck.service;

import java.util.List;
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

import com.privasia.scss.common.dto.HealthCheckInfoDTO;
import com.privasia.scss.common.exception.BusinessException;
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


  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public List<HealthCheckInfoDTO> getHealthCheckInfo(int size) {

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

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = false)
  public Long saveKioskHealthCheckInfo(HealthCheckInfoDTO healthCheckInfo) {

    log.info("******* Request for Save Kiosk Health Check Info ******* ");
    log.info("Request  Health Check Info : " + healthCheckInfo);
    
    KioskHLTCheck kioskHLTCheck = convertDTOToDomain(healthCheckInfo);
    
    kioskHLTCheck.setNotificationStatus(false);
    
    if(healthCheckInfo.getBoothClientID()!= null){
    	kioskHLTCheck.setBooth(clientRepository.findOne(healthCheckInfo.getBoothClientID()).orElseGet(null));
    }
    
    if(healthCheckInfo.getKioskClientID()!= null){
    	kioskHLTCheck.setKiosk(clientRepository.findOne(healthCheckInfo.getKioskClientID()).orElseGet(null));
    }
    
    kioskHLTCheck = kioskHLTCheckRepository.save(kioskHLTCheck);
    
    if(kioskHLTCheck.getHealthCheckSeq() == null)
    	throw new BusinessException("Error saving Kiosk Health Check info ! ");
    return kioskHLTCheck.getHealthCheckSeq();

  }


  private HealthCheckInfoDTO convertDomainToDto(KioskHLTCheck healthCheckInfo) {
    HealthCheckInfoDTO healthCheckInfoDTO = modelMapper.map(healthCheckInfo, HealthCheckInfoDTO.class);
    return healthCheckInfoDTO;
  }

  private KioskHLTCheck convertDTOToDomain(HealthCheckInfoDTO healthCheckInfoDTO) {
	  KioskHLTCheck healthCheckInfo = modelMapper.map(healthCheckInfoDTO, KioskHLTCheck.class);
	  return healthCheckInfo;

  }
  
  
}
