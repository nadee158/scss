/**
 * 
 */
package com.privasia.scss.gateout.aspect;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

import com.privasia.scss.common.annotation.DSOSealNotification;
import com.privasia.scss.common.dto.GateOutWriteRequest;
import com.privasia.scss.common.dto.ImportContainer;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.core.service.NotificationService;

/**
 * @author Janaka
 *
 */
@Aspect
public class GateOutEmailNotificationAspect {

  private static final Log log = LogFactory.getLog(GateOutEmailNotificationAspect.class);

  private NotificationService notificationService;



  @Autowired
  public void setNotificationService(NotificationService notificationService) {
    this.notificationService = notificationService;
  }


  @AfterReturning(pointcut = "@annotation(dsoSealNotification)")
  public void sendDsoSealDifferentEmail(JoinPoint joinPoint, DSOSealNotification dsoSealNotification) {
    log.info("*****************   sendDsoSealDifferentEmail called *************************");
    if (joinPoint.getArgs()[0] instanceof GateOutWriteRequest) {
      GateOutWriteRequest gateOutWriteRequest = (GateOutWriteRequest) joinPoint.getArgs()[0];

//@formatter:off
      List<ImportContainer> changeSealContainers = gateOutWriteRequest.getImportContainers().stream()
          .filter(imports -> 
                    imports.getSealAttribute() != null
                    && (
                        StringUtils.isNotBlank(imports.getCosmosSeal01Number())
                        && (!(
                             StringUtils.equals(imports.getSealAttribute().getSeal01Number(), imports.getCosmosSeal01Number())
                             || StringUtils.equals(imports.getSealAttribute().getSeal02Number(), imports.getCosmosSeal01Number())
                            ))
                       )
                    ||
                      (
                        StringUtils.isNotBlank(imports.getCosmosSeal02Number())
                          && (!(
                              StringUtils.equals(imports.getSealAttribute().getSeal01Number(), imports.getCosmosSeal02Number())
                               || StringUtils.equals(imports.getSealAttribute().getSeal02Number(), imports.getCosmosSeal02Number())
                             )) 
                       )
                  )
          .collect(Collectors.toList());
//@formatter:on

      if (!(changeSealContainers == null || changeSealContainers.isEmpty())) {
        notificationService.sendDsoSealDifferentEmail(changeSealContainers);
      }

      return;
    } else {
      throw new BusinessException("Invalid agruments for send Dso Seal Different Email Email");
    }

  }



}


