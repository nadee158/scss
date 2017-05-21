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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.UpdateReferReject;
import com.privasia.scss.common.dto.ExportContainer;
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
  public void isADGContainer(JoinPoint joinPoint, UpdateReferReject updateReferReject) {

    log.info("*****************   isADGContainer called *************************");

    if (joinPoint.getArgs()[0] instanceof GateInWriteRequest) {
      GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];

      List<ExportContainer> containers = gateInWriteRequest.getExportContainers().stream()
          .filter(
              exports -> StringUtils.isNotEmpty(exports.getImdg()) && exports.isDontValidateDg())
          .collect(Collectors.toList());

      if (!(containers == null || containers.isEmpty())) {

        // referee reject service update
        if (gateInWriteRequest.getReferRejectDTO().isPresent()
            && gateInWriteRequest.getReferRejectDTO().get().getReferRejectID() != null) {
          exportGateInService.updateReferReject(gateInWriteRequest, containers);
        }

        return;
      }

      return;
    } else {
      throw new BusinessException("Invalid agruments for update DG Validation Log");
    }

  }



}


