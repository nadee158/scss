package com.privasia.scss.core.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.NotificationDTO;
import com.privasia.scss.core.model.KioskHLTCheck;
import com.privasia.scss.core.repository.KioskHLTCheckRepository;

@Service("notificationService")
public class NotificationService {

  @Autowired
  private KioskHLTCheckRepository kioskHLTCheckRepository;

  @Value("${kioskhealthcheckmails.pagesize}")
  private int pageSize;

  public void sendKioskHealthCheckMails() {
    Boolean notificationStatus = false;

    long totalPendingNotificationCount =
        kioskHLTCheckRepository.countHealthCheckInfoForNofitication(notificationStatus);

    if (totalPendingNotificationCount > 0) {
      long fetchedCount = 0;
      while (fetchedCount <= totalPendingNotificationCount) {

      }
      Pageable pageRequest = new PageRequest(0, 15);

    }

  }

  public Map<String, List<NotificationDTO>> getNotifications(Boolean notificationStatus, Pageable pageRequest) {


    Page<KioskHLTCheck> healthCheckInfoList =
        kioskHLTCheckRepository.getKioskHealthCheckInfoForNofitication(notificationStatus, pageRequest);



    List<NotificationDTO> notificationList = null;
    NotificationDTO notificationDTO = null;
    Map<String, List<NotificationDTO>> results = new HashMap<String, List<NotificationDTO>>();

    /*
     * if (healthCheckInfoList.size() > 0) { for (HealthCheckInfo healthCheckInfo :
     * healthCheckInfoList) { if (StringUtils.isNotEmpty(healthCheckInfo.getKioskID())) {
     * 
     * if (results.containsKey(healthCheckInfo.getKioskID())) { notificationList =
     * results.get(healthCheckInfo.getKioskID()); } else { notificationList = new
     * ArrayList<NotificationDTO>(); }
     * 
     * if (StringUtils.isNotEmpty(healthCheckInfo.getPrinterStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getPrinterStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("PRINTER STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getPrinterStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); }
     * 
     * } if (StringUtils.isNotEmpty(healthCheckInfo.getPaperStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getPaperStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("PAPER STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getPaperStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); } } if
     * (StringUtils.isNotEmpty(healthCheckInfo.getCardReaderStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getCardReaderStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("CARD READER STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getCardReaderStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); } } if
     * (StringUtils.isNotEmpty(healthCheckInfo.getPcStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getPcStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("PC STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getPcStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); } } if
     * (StringUtils.isNotEmpty(healthCheckInfo.getIntercomStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getIntercomStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("INTERCOM STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getIntercomStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); } } if
     * (StringUtils.isNotEmpty(healthCheckInfo.getCameraStatus()) &&
     * (!StringUtils.equals(healthCheckInfo.getCameraStatus(), "OK"))) { notificationDTO = new
     * NotificationDTO(); notificationDTO.setId(healthCheckInfo.getKioskID());
     * notificationDTO.setRoot("CAMERA STATUS");
     * notificationDTO.setDescription(healthCheckInfo.getCameraStatus().toUpperCase()); if
     * (!notificationList.contains(notificationDTO)) { notificationList.add(notificationDTO); } }
     * 
     * if (notificationList.size() > 0) { results.put(healthCheckInfo.getKioskID(),
     * notificationList); }
     * 
     * } }
     * 
     * } else { results = null; }
     */


    return results;

  }

}
