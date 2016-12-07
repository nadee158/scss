package com.privasia.scss.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.privasia.scss.common.dto.NotificationDTO;
import com.privasia.scss.common.enums.KioskHLTCheckStatus;
import com.privasia.scss.common.util.NotificationSentStatus;
import com.privasia.scss.core.model.KioskHLTCheck;
import com.privasia.scss.core.repository.KioskHLTCheckRepository;
import com.privasia.scss.core.util.service.MailUtil;

@Service("notificationService")
public class NotificationService {

  @Autowired
  private KioskHLTCheckRepository kioskHLTCheckRepository;

  @Value("${kioskhealthcheckmails.pagesize}")
  private int pageSize;

  @Autowired
  private MailUtil mailUtil;

  public void sendKioskHealthCheckMails() {
    Boolean notificationStatus = false;

    long totalPendingNotificationCount =
        kioskHLTCheckRepository.getCountHealthCheckInfoForNofitication(notificationStatus);
    System.out.println("totalPendingNotificationCount :" + totalPendingNotificationCount);
    if (totalPendingNotificationCount > 0) {
      long fetchedCount = 0;
      while (fetchedCount <= totalPendingNotificationCount) {
        System.out.println("fetchedCount BEFORE :" + fetchedCount);
        fetchedCount = fetchedCount + pageSize;
        System.out.println("fetchedCount AFTER :" + fetchedCount);
        Map<String, List<NotificationDTO>> notifications = getNotifications(notificationStatus);
        if (!(notifications == null || notifications.isEmpty())) {
          NotificationSentStatus status = mailUtil.sendEmail(notifications);
          System.out.println("NotificationSentStatus :" + status);
        }
      }


    }

  }

  public Map<String, List<NotificationDTO>> getNotifications(Boolean notificationStatus) {

    Pageable pageRequest = new PageRequest(0, pageSize);

    List<KioskHLTCheck> healthCheckInfoList =
        kioskHLTCheckRepository.findByNotificationStatus(notificationStatus, pageRequest);

    if (!(healthCheckInfoList == null || healthCheckInfoList.isEmpty())) {
      Map<String, List<NotificationDTO>> results = new HashMap<String, List<NotificationDTO>>();

      Stream<KioskHLTCheck> parentStream = healthCheckInfoList.stream();

      Map<Long, List<KioskHLTCheck>> map = parentStream.filter(ch -> ch.getKiosk() != null)
          .collect(Collectors.groupingBy((k -> k.getKiosk().getClientID())));

      if (!(map == null || map.isEmpty())) {

        map.entrySet().forEach(mapItem -> {

          if (!(mapItem.getValue() == null || mapItem.getValue().isEmpty())) {

            List<NotificationDTO> list = new ArrayList<NotificationDTO>();

            String kioskId = Long.toString(mapItem.getKey());

            // PrinterStatus stream
            NotificationDTO printerStatusNotification = createNotificationForPrinterStatus(kioskId, mapItem.getValue());
            if (!(printerStatusNotification == null)) {
              list.add(printerStatusNotification);
            }

            // PaperStatus stream
            NotificationDTO paperStatusNotification = createNotificationForPaperStatus(kioskId, mapItem.getValue());
            if (!(paperStatusNotification == null)) {
              list.add(paperStatusNotification);
            }

            // CardReaderStatus stream
            NotificationDTO cardReaderStatusNotification =
                createNotificationForCardReaderStatus(kioskId, mapItem.getValue());
            if (!(cardReaderStatusNotification == null)) {
              list.add(cardReaderStatusNotification);
            }

            // PcStatus stream
            NotificationDTO pcStatusNotification = createNotificationForPcStatus(kioskId, mapItem.getValue());
            if (!(pcStatusNotification == null)) {
              list.add(pcStatusNotification);
            }

            // IntercomStatus stream
            NotificationDTO intercomStatusNotification =
                createNotificationForIntercomStatus(kioskId, mapItem.getValue());
            if (!(intercomStatusNotification == null)) {
              list.add(intercomStatusNotification);
            }

            // CameraStatus stream
            NotificationDTO cameraStatusNotification = createNotificationForCameraStatus(kioskId, mapItem.getValue());
            if (!(cameraStatusNotification == null)) {
              list.add(cameraStatusNotification);
            }

            if (!(list.isEmpty())) {
              results.put(kioskId, list);
            }
          }

        });
      }
      return results;
    }
    return null;
  }

  private NotificationDTO createNotificationForCameraStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();

    Optional<KioskHLTCheck> kioskHLTCheck = filteringStream.filter(kCheck -> {
      return kCheck.getCameraStatus() != null && !(kCheck.getCameraStatus().equals(KioskHLTCheckStatus.OK));
    }).findFirst();

    if (kioskHLTCheck.isPresent()) {
      return new NotificationDTO(kioskId, "CAMERA STATUS", kioskHLTCheck.get().getCameraStatus().getValue());
    }
    return null;
  }

  private NotificationDTO createNotificationForIntercomStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();

    Optional<KioskHLTCheck> kioskHLTCheck = filteringStream.filter(kCheck -> {
      return kCheck.getIntercomStatus() != null && !(kCheck.getIntercomStatus().equals(KioskHLTCheckStatus.OK));
    }).findFirst();

    if (kioskHLTCheck.isPresent()) {
      return new NotificationDTO(kioskId, "INTERCOM STATUS", kioskHLTCheck.get().getIntercomStatus().getValue());
    }
    return null;
  }

  private NotificationDTO createNotificationForPcStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();

    Optional<KioskHLTCheck> kioskHLTCheck = filteringStream.filter(kCheck -> {
      return kCheck.getPcStatus() != null && !(kCheck.getPcStatus().equals(KioskHLTCheckStatus.OK));
    }).findFirst();

    if (kioskHLTCheck.isPresent()) {
      return new NotificationDTO(kioskId, "PC STATUS", kioskHLTCheck.get().getPcStatus().getValue());
    }
    return null;
  }

  private NotificationDTO createNotificationForCardReaderStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();

    Optional<KioskHLTCheck> kioskHLTCheck = filteringStream.filter(kCheck -> {
      return kCheck.getCardReaderStatus() != null && !(kCheck.getCardReaderStatus().equals(KioskHLTCheckStatus.OK));
    }).findFirst();

    if (kioskHLTCheck.isPresent()) {
      return new NotificationDTO(kioskId, "CARD READER STATUS", kioskHLTCheck.get().getCardReaderStatus().getValue());
    }
    return null;
  }

  public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
    final Set<Object> seen = new HashSet<>();
    return t -> seen.add(keyExtractor.apply(t));
  }

  private NotificationDTO createNotificationForPaperStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();

    Predicate<KioskHLTCheck> nonNullPredicate = t -> StringUtils.isNotEmpty(t.getPaperStatus())
        && !(StringUtils.equals(t.getPaperStatus(), KioskHLTCheckStatus.OK.getValue()));

    Predicate<KioskHLTCheck> fullPredicate = nonNullPredicate.and(distinctByKey(p -> p.getPaperStatus()));

    List<KioskHLTCheck> filteredList = filteringStream.filter(fullPredicate).collect(Collectors.toList());

    if (!(filteredList == null || filteredList.isEmpty())) {
      StringBuilder desc = new StringBuilder("");
      filteredList.forEach(item -> {
        desc.append(StringUtils.upperCase(item.getPaperStatus()));
        desc.append(" / ");
      });
      String description = StringUtils.trim(desc.toString());
      if (description.endsWith("/")) {
        description = description.substring(0, (description.length() - 1));
      }
      return new NotificationDTO(kioskId, "PAPER STATUS", description);
    }

    return null;
  }

  private NotificationDTO createNotificationForPrinterStatus(String kioskId, List<KioskHLTCheck> kioskList) {
    Stream<KioskHLTCheck> filteringStream = kioskList.stream();
    Optional<KioskHLTCheck> kioskHLTCheck = filteringStream.filter(kCheck -> {
      return kCheck.getPrinterStatus() != null && !(kCheck.getPrinterStatus().equals(KioskHLTCheckStatus.OK));
    }).findFirst();

    if (kioskHLTCheck.isPresent()) {
      return new NotificationDTO(kioskId, "PRINTER STATUS", kioskHLTCheck.get().getPrinterStatus().getValue());
    }
    return null;
  }

}