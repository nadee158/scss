package com.privasia.scss.healthcheck.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.privasia.scss.common.dto.HealthCheckInfoDTO;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.CommonUtil;
import com.privasia.scss.core.model.KioskHLTCheck;
import com.privasia.scss.core.repository.KioskHLTCheckRepository;

@Service("healthCheckService")
@Transactional
public class HealthCheckService {

  private static final Log log = LogFactory.getLog(HealthCheckService.class);

  private KioskHLTCheckRepository kioskHLTCheckRepository;

  @Transactional(readOnly = true)
  public List<HealthCheckInfoDTO> getHealthCheckInfo(int pageNo, int size) {
    if (size <= 0) {
      size = 14;
    }
    log.info("******* Request for Get Health Check Info ******* ");
    Pageable page = new PageRequest(pageNo, size);
    List<KioskHLTCheck> kioskHealthCheckInfoList = kioskHLTCheckRepository.listLimitedEntities(page);
    log.info("******* Response for Get Kiosk Health Check Info******* ");
    if (!(kioskHealthCheckInfoList == null || kioskHealthCheckInfoList.isEmpty())) {
      log.info("Response  HealthCheckInfo Size : " + kioskHealthCheckInfoList.size());
      List<HealthCheckInfoDTO> dtoList = new ArrayList<HealthCheckInfoDTO>();
      kioskHealthCheckInfoList.forEach(info -> {
        dtoList.add(info.constructDto());
      });
      return dtoList;
    }
    return null;
  }

  @Transactional(readOnly = false)
  public Map<String, String> saveKioskHealthCheckInfo(HealthCheckInfoDTO healthCheckInfo) {
    Map<String, String> resultMap = new HashMap<String, String>();

    log.info("******* Request for Save Kiosk Health Check Info ******* ");
    log.info("Request  Health Check Info : " + healthCheckInfo);

    KioskHLTCheck kioskHLTCheck = new KioskHLTCheck(healthCheckInfo);

    KioskHLTCheck persisted = kioskHLTCheckRepository.save(kioskHLTCheck);

    if (persisted.getHealthCheckSeq() > 0) {
      resultMap.put(ApplicationConstants.MESSAGE_CODE, ApplicationConstants.MESSAGE_OK);
      resultMap.put(ApplicationConstants.MESSAGE_DESCRIPTION,
          CommonUtil.formatMessageCode("SCSS_KIOSK_HEALTH_CHECK_CODE01", null));
    } else {
      resultMap.put(ApplicationConstants.MESSAGE_CODE, ApplicationConstants.MESSAGE_NOK);
      resultMap.put(ApplicationConstants.MESSAGE_DESCRIPTION,
          CommonUtil.formatMessageCode("SCSS_KIOSK_HEALTH_CHECK_ERR_CODE02", null));
    }

    log.info("******* Response for Save Kiosk Health Check Info******* ");
    log.info("Response  resultMap Info : " + resultMap);

    return resultMap;

  }



}
