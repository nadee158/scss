package com.privasia.scss.gatein.service;

import java.math.BigDecimal;
import java.math.MathContext;
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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.context.Context;

import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.dto.NotificationDTO;
import com.privasia.scss.common.enums.EmailTemplate;
import com.privasia.scss.common.enums.KioskHLTCheckStatus;
import com.privasia.scss.common.enums.ShippingLineReportType;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.common.util.ApplicationConstants;
import com.privasia.scss.common.util.DateUtil;
import com.privasia.scss.core.model.KioskHLTCheck;
import com.privasia.scss.core.model.ShipEmail;
import com.privasia.scss.core.repository.KioskHLTCheckRepository;
import com.privasia.scss.core.repository.ShipEmailRepository;

@Service("notificationService")
public class NotificationService {


  @Value("${kioskhealthcheckmails.pagesize}")
  private int pageSize;

  @Value("${mail.to}")
  private String recieverEmail;

  private KioskHLTCheckRepository kioskHLTCheckRepository;

  private EmailService emailService;

  private ShipEmailRepository shipEmailRepository;


  @Autowired
  public void setShipEmailRepository(ShipEmailRepository shipEmailRepository) {
    this.shipEmailRepository = shipEmailRepository;
  }

  @Autowired
  public void setEmailService(EmailService emailService) {
    this.emailService = emailService;
  }

  @Autowired
  public void setKioskHLTCheckRepository(KioskHLTCheckRepository kioskHLTCheckRepository) {
    this.kioskHLTCheckRepository = kioskHLTCheckRepository;
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void sendDsoSealDifferentEmail(List<ImportContainer> changeSealContainers) {
    changeSealContainers.forEach(importContainer -> {
      if ((importContainer.getContainer() == null)) {
        throw new BusinessException("Container is null !");
      }
      Optional<ShipEmail> shipEmailOpt = shipEmailRepository
          .findBylineCodeAndTypeCode(importContainer.getShippingLine(), ShippingLineReportType.DSO_SEAL_DIFFERENT);
      if (shipEmailOpt.isPresent()) {
        ShipEmail shipEmail = shipEmailOpt.get();
        String emailContent = null;
        Context context = new Context();
        String subject =
            "DSO SEAL Different - " + StringUtils.trim(importContainer.getContainer().getContainerNumber());
        try {
          context.setVariable(ApplicationConstants.EMAIL_BCC, shipEmail.getEmailBCC());
          context.setVariable(ApplicationConstants.EMAIL_CC, shipEmail.getEmailCC());

          if (!(importContainer.getBaseCommonGateInOutAttribute() == null)) {
            context.setVariable("gateOutDateTime",
                DateUtil.getFormatteDate(importContainer.getBaseCommonGateInOutAttribute().getTimeGateOut()));
          }
          context.setVariable("contNo", importContainer.getContainer().getContainerNumber());
          context.setVariable("isoCode", importContainer.getContainer().getContainerISOCode());
          context.setVariable("fullEmptyFlag", importContainer.getContainer().getContainerFullOrEmpty());
          context.setVariable("lineCode", importContainer.getShippingLine());
          context.setVariable("vesselScn", importContainer.getVesselScn());
          context.setVariable("vesselName", importContainer.getVesselName());
          context.setVariable("dsoSealNo1", importContainer.getCosmosSeal01Number());
          context.setVariable("dsoSealNo2", importContainer.getCosmosSeal02Number());
          if (!(importContainer.getSealAttribute() == null)) {
            context.setVariable("scssSealNo1", importContainer.getSealAttribute().getSeal01Number());
            context.setVariable("scssSealNo2", importContainer.getSealAttribute().getSeal02Number());
          }
          // try and send email
          emailContent = emailService.prepareAndSendEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.DSO_SEAL_TEMPLATE.getValue());
        } catch (Exception e) {
          // if fails, save to db
          emailContent = emailService.prepareAndSaveEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.DSO_SEAL_TEMPLATE.getValue());
        }
        
      }
    });
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void sendContainerSizeDiscrepancyEmail(List<ExportContainer> sizeDiscrepancyContainers) {
    sizeDiscrepancyContainers.forEach(exportContainer -> {
      if ((exportContainer.getContainer() == null)) {
        throw new BusinessException("Container is null !");
      }
      Optional<ShipEmail> shipEmailOpt = shipEmailRepository.findBylineCodeAndTypeCode(
          exportContainer.getShippingLine(), ShippingLineReportType.CONTAINER_SIZE_DISCREPANCY);
      if (shipEmailOpt.isPresent()) {
        ShipEmail shipEmail = shipEmailOpt.get();
        String emailContent = null;
        Context context = new Context();
        String subject =
            "CONTAINER SIZE DISCREPANCY - " + StringUtils.trim(exportContainer.getContainer().getContainerNumber());
       
        try {
          context.setVariable(ApplicationConstants.EMAIL_BCC, shipEmail.getEmailBCC());
          context.setVariable(ApplicationConstants.EMAIL_CC, shipEmail.getEmailCC());

          if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
            context.setVariable("gateInTime",
                DateUtil.getFormatteDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn()));
          }
          context.setVariable("bookingNo", exportContainer.getBookingNo());
          context.setVariable("contNo", exportContainer.getContainer().getContainerNumber());
          context.setVariable("isoCode", exportContainer.getContainer().getContainerISOCode());
          context.setVariable("fullEmptyFlag", exportContainer.getContainer().getContainerFullOrEmpty());
          context.setVariable("lineCode", exportContainer.getShippingLine());

          context.setVariable("haulageCompany", exportContainer.getShipCode());
          context.setVariable("cosmosIsoCode", exportContainer.getCosmosISOCode());
          context.setVariable("hpabIsoCode", exportContainer.getHpabISOCode());
          // try and send email
          emailContent = emailService.prepareAndSendEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.CONTAINER_SIZE_TEMPLATE.getValue());
        } catch (Exception e) {
          // if fails, save to db
          emailContent = emailService.prepareAndSaveEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.CONTAINER_SIZE_TEMPLATE.getValue());
        }
        
      }
    });
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void sendNonStandardSealLineCodeEmail(List<ExportContainer> dontValidateSealContainers) {
    dontValidateSealContainers.forEach(exportContainer -> {
      if ((exportContainer.getContainer() == null)) {
        throw new BusinessException("Container is null !");
      }
      Optional<ShipEmail> shipEmailOpt = shipEmailRepository
          .findBylineCodeAndTypeCode(exportContainer.getShippingLine(), ShippingLineReportType.SEAL_LINE);
      if (shipEmailOpt.isPresent()) {
        ShipEmail shipEmail = shipEmailOpt.get();
        String emailContent = null;
        Context context = new Context();
        String subject = "ADVICE ON NON STANDARD SEAL LINE CODE - "
            + StringUtils.trim(exportContainer.getContainer().getContainerNumber()) + " - "
            + StringUtils.trim(exportContainer.getContainer().getContainerISOCode());
        try {
          context.setVariable(ApplicationConstants.EMAIL_BCC, shipEmail.getEmailBCC());
          context.setVariable(ApplicationConstants.EMAIL_CC, shipEmail.getEmailCC());

          if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
            context.setVariable("gateInTime",
                DateUtil.getFormatteDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn()));
          }
          context.setVariable("sealCode", exportContainer.getSealAttribute().getSeal01Number());
          context.setVariable("bookingNo", exportContainer.getBookingNo());
          context.setVariable("contNo", exportContainer.getContainer().getContainerNumber());
          context.setVariable("isoCode", exportContainer.getContainer().getContainerISOCode());
          context.setVariable("fullEmptyFlag", exportContainer.getContainer().getContainerFullOrEmpty());
          context.setVariable("lineCode", exportContainer.getShippingLine());
          context.setVariable("vesselScn", exportContainer.getVesselSCN());
          context.setVariable("vesselName", exportContainer.getVesselName());
          // try and send email
          emailContent = emailService.prepareAndSendEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.NON_STANDARD_SEAL_LINE_CODE_TEMPLATE.getValue());
        } catch (Exception e) {
          // if fails, save to db
          emailContent = emailService.prepareAndSaveEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.NON_STANDARD_SEAL_LINE_CODE_TEMPLATE.getValue());
        }
        
      }
    });
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void sendWrongDoorEmail(List<ExportContainer> wrongDoorContainers) {
    wrongDoorContainers.forEach(exportContainer -> {
      if ((exportContainer.getContainer() == null)) {
        throw new BusinessException("Container is null !");
      }
      Optional<ShipEmail> shipEmailOpt = shipEmailRepository
          .findBylineCodeAndTypeCode(exportContainer.getShippingLine(), ShippingLineReportType.WRONG_DOOR);
      if (shipEmailOpt.isPresent()) {
        ShipEmail shipEmail = shipEmailOpt.get();
        String emailContent = null;
        Context context = new Context();

        String subject = "WRONG DOOR - " + StringUtils.trim(exportContainer.getContainer().getContainerNumber()) + " - "
            + StringUtils.trim(exportContainer.getContainer().getContainerISOCode());

        try {

          context.setVariable(ApplicationConstants.EMAIL_BCC, shipEmail.getEmailBCC());
          context.setVariable(ApplicationConstants.EMAIL_CC, shipEmail.getEmailCC());

          if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
            context.setVariable("gateInTime",
                DateUtil.getFormatteDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn()));
          }
          context.setVariable("bookingNo", exportContainer.getBookingNo());
          context.setVariable("contNo", exportContainer.getContainer().getContainerNumber());
          context.setVariable("isoCode", exportContainer.getContainer().getContainerISOCode());
          context.setVariable("fullEmptyFlag", exportContainer.getContainer().getContainerFullOrEmpty());
          context.setVariable("lineCode", exportContainer.getShippingLine());
          context.setVariable("vesselScn", exportContainer.getVesselSCN());
          context.setVariable("vesselName", exportContainer.getVesselName());

          // try and send email
          emailContent = emailService.prepareAndSendEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.WRONG_DOOR_TEMPLATE.getValue());

        } catch (Exception e) {
          // if fails, save to db
          emailContent = emailService.prepareAndSaveEmail(shipEmail.getEmailTo(), subject, context,
              EmailTemplate.WRONG_DOOR_TEMPLATE.getValue());
        }
        
      }
    });
  }

  @Async
  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRES_NEW, readOnly = false)
  public void sendWeightEmail(List<ExportContainer> fullContainers) {
    fullContainers.forEach(exportContainer -> {
      if ((exportContainer.getContainer() == null)) {
        throw new BusinessException("Container is null !");
      }
      Optional<ShipEmail> shipEmailOpt = shipEmailRepository
          .findBylineCodeAndTypeCode(exportContainer.getShippingLine(), ShippingLineReportType.WEIGHT);
      if (shipEmailOpt.isPresent()) {
        ShipEmail shipEmail = shipEmailOpt.get();
        double weightDiffPercentage = exportContainer.getWeightDiffPercentage();
        if (weightDiffPercentage < 0) {
          weightDiffPercentage = weightDiffPercentage * -1;
        }
        BigDecimal contWeightDiffPercentage = new BigDecimal(weightDiffPercentage, MathContext.DECIMAL64);

        if (contWeightDiffPercentage.compareTo(shipEmail.getPercentage()) >= 0) {
          String emailContent = null;
          Context context = new Context();

          String subject =
              "WEIGHT DISCREPANCY - " + StringUtils.trim(exportContainer.getContainer().getContainerNumber()) + " - "
                  + StringUtils.trim(exportContainer.getContainer().getContainerISOCode());

          try {

            context.setVariable(ApplicationConstants.EMAIL_BCC, shipEmail.getEmailBCC());
            context.setVariable(ApplicationConstants.EMAIL_CC, shipEmail.getEmailCC());

            if (!(exportContainer.getBaseCommonGateInOutAttribute() == null)) {
              context.setVariable("gateInTime",
                  DateUtil.getFormatteDate(exportContainer.getBaseCommonGateInOutAttribute().getTimeGateIn()));
            }
            context.setVariable("bookingNo", exportContainer.getBookingNo());
            context.setVariable("contNo", exportContainer.getContainer().getContainerNumber());
            context.setVariable("isoCode", exportContainer.getContainer().getContainerISOCode());
            context.setVariable("fullEmptyFlag", exportContainer.getContainer().getContainerFullOrEmpty());
            context.setVariable("lineCode", exportContainer.getShippingLine());
            context.setVariable("netWeight", exportContainer.getExpNetWeight());
            context.setVariable("cosmosWeight", exportContainer.getCosmosNetWeight());
            context.setVariable("weightDifferent", exportContainer.getWeightDifference());
            context.setVariable("signPercentage", weightDiffPercentage);
            context.setVariable("vesselScn", exportContainer.getVesselSCN());
            context.setVariable("vesselName", exportContainer.getVesselName());

            // try and send email
            emailContent = emailService.prepareAndSendEmail(shipEmail.getEmailTo(), subject, context,
                EmailTemplate.WEIGHT_TEMPLATE.getValue());

          } catch (Exception e) {
            // if fails, save to db
            emailContent = emailService.prepareAndSaveEmail(shipEmail.getEmailTo(), subject, context,
                EmailTemplate.WEIGHT_TEMPLATE.getValue());
          }

        }
      }
    });
  }

  @Transactional(value = "transactionManager", propagation = Propagation.REQUIRED, readOnly = true)
  public void sendKioskHealthCheckMails() {
    
    Boolean notificationStatus = false;
    long totalPendingNotificationCount =
        kioskHLTCheckRepository.getCountHealthCheckInfoForNofitication(notificationStatus);
   
    if (totalPendingNotificationCount > 0) {
      long fetchedCount = 0;
      while (fetchedCount <= totalPendingNotificationCount) {
        
        fetchedCount = fetchedCount + pageSize;
        Map<String, List<NotificationDTO>> notifications = getNotifications(notificationStatus);
        if (!(notifications == null || notifications.isEmpty())) {
          Context context = new Context();
          context.setVariable("resultMap", notifications);
          String mailSubject = "Notification From Kiosk Health Check";
          emailService.prepareAndSendEmail(recieverEmail, mailSubject, context,
              EmailTemplate.KIOSK_HEALTH_CHECK_MAIL_TEMPLATE.getValue());
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
