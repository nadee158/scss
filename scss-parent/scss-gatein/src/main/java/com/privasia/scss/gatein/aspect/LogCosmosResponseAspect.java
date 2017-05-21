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

import com.privasia.scss.common.annotation.LogCosmosResponse;

/**
 * @author Janaka
 *
 */
@Aspect
@Component
public class LogCosmosResponseAspect {

  private static final Log log = LogFactory.getLog(LogCosmosResponseAspect.class);


  @Async
  @AfterReturning(pointcut = "@annotation(logCosmosResponse)")
  public void logCosmosResponse(JoinPoint joinPoint, LogCosmosResponse logCosmosResponse) {

    log.info("*****************   logCosmosResponse called *************************");


  }



}


