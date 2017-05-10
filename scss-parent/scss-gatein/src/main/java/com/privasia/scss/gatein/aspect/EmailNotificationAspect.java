/**
 * 
 */
package com.privasia.scss.gatein.aspect;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.annotation.ISaDG;
import com.privasia.scss.common.dto.ExportContainer;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.core.service.NotificationService;

/**
 * @author Janaka
 *
 */
@Aspect
public class EmailNotificationAspect {

  private static final Log log = LogFactory.getLog(EmailNotificationAspect.class);

  private NotificationService notificationService;



  @Autowired
  public void setNotificationService(NotificationService notificationService) {
    this.notificationService = notificationService;
  }


  @AfterReturning(pointcut = "@annotation(weightNotification)")
  public void sendWeightEmail(JoinPoint joinPoint, ISaDG isaDG) {
    log.info("*****************   sendWeightEmail called *************************");
    if (joinPoint.getArgs()[0] instanceof GateInWriteRequest) {
      GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];

      List<ExportContainer> fullContainers = gateInWriteRequest.getExportContainers().stream()
          .filter(exports -> exports.getContainer() != null
              && StringUtils.isNotEmpty(exports.getContainer().getContainerFullOrEmpty())
              && StringUtils.equals(exports.getContainer().getContainerFullOrEmpty(), "F"))
          .collect(Collectors.toList());

      if (!(fullContainers == null || fullContainers.isEmpty())) {
        notificationService.sendWeightEmail(fullContainers);
      }
      return;
    } else {
      throw new BusinessException("Invalid agruments for send Weight Email");
    }

  }


  @AfterReturning(pointcut = "@annotation(wrongDoorNotification)")
  public void sendWrongDoorEmail(JoinPoint joinPoint, ISaDG isaDG) {
    log.info("*****************   sendWrongDoorEmail called *************************");
    if (joinPoint.getArgs()[0] instanceof GateInWriteRequest) {
      GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];

      List<ExportContainer> wrongDoorContainers = gateInWriteRequest.getExportContainers().stream()
          .filter(exports -> exports.getContainer() != null
              && StringUtils.isNotEmpty(exports.getContainer().getContainerFullOrEmpty())
              && StringUtils.equals(exports.getContainer().getContainerFullOrEmpty(), "F")
              && exports.getWrongDoor() == true)
          .collect(Collectors.toList());

      if (!(wrongDoorContainers == null || wrongDoorContainers.isEmpty())) {
        notificationService.sendWrongDoorEmail(wrongDoorContainers);
      }
      return;
    } else {
      throw new BusinessException("Invalid agruments for send send Wrong Door Email");
    }

  }

  @AfterReturning(pointcut = "@annotation(sealLineNotification)")
  public void sendNonStandardSealLineCodeEmail(JoinPoint joinPoint, ISaDG isaDG) {
    log.info("*****************   sendNonsStandardSealLineCodeEmail called *************************");
    if (joinPoint.getArgs()[0] instanceof GateInWriteRequest) {
      GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];

      List<ExportContainer> dontValidateSealContainers = gateInWriteRequest.getExportContainers().stream()
          .filter(exports -> exports.getContainer() != null
              && StringUtils.isNotEmpty(exports.getContainer().getContainerFullOrEmpty())
              && StringUtils.equals(exports.getContainer().getContainerFullOrEmpty(), "F")
              && exports.getDontValidateSeal() == true)
          .collect(Collectors.toList());

      if (!(dontValidateSealContainers == null || dontValidateSealContainers.isEmpty())) {
        notificationService.sendNonStandardSealLineCodeEmail(dontValidateSealContainers);
      }
      return;
    } else {
      throw new BusinessException("Invalid agruments for send send Wrong Door Email");
    }

  }



}

