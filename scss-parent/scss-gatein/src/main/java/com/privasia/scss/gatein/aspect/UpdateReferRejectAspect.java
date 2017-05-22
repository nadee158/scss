/**
 * 
 */
package com.privasia.scss.gatein.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.UpdateReferReject;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.exception.BusinessException;
import com.privasia.scss.gatein.service.ExportGateInService;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class UpdateReferRejectAspect {

  private static final Log log = LogFactory.getLog(UpdateReferRejectAspect.class);

  private ExportGateInService exportGateInService;

  @Autowired
  public void setExportGateInService(ExportGateInService exportGateInService) {
    this.exportGateInService = exportGateInService;
  }


  @Async
  @AfterReturning(pointcut = "@annotation(updateReferReject)")
  public void updateReferReject(JoinPoint joinPoint, UpdateReferReject updateReferReject) {

    log.info("*****************   updateReferReject called *************************");

    if (joinPoint.getArgs()[0] instanceof GateInWriteRequest) {
      GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];

      if (!(gateInWriteRequest.getExportContainers() == null || gateInWriteRequest.getExportContainers().isEmpty())) {
        // referee reject service update
        if (gateInWriteRequest.getReferRejectDTO().isPresent()
            && gateInWriteRequest.getReferRejectDTO().get().getReferRejectID() != null) {
          exportGateInService.updateReferReject(gateInWriteRequest, gateInWriteRequest.getExportContainers());
        }

        return;
      }

      return;
    } else {
      throw new BusinessException("Invalid agruments for update Refer Reject Details");
    }

  }



}


