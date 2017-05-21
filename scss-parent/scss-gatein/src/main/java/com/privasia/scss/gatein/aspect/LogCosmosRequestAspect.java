/**
 * 
 */
package com.privasia.scss.gatein.aspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.LogCosmosRequest;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class LogCosmosRequestAspect {

  private static final Log log = LogFactory.getLog(LogCosmosRequestAspect.class);


  @Async
  @AfterReturning(pointcut = "@annotation(logCosmosRequest)")
  public void logCosmosRequest(JoinPoint joinPoint, LogCosmosRequest logCosmosRequest) {

    log.info("*****************   logCosmosRequest called *************************");


  }



}


