package com.privasia.scss.core.aspects;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

	private static final Log log = LogFactory.getLog(LoggingAspect.class);
	
	@Before(value="@annotation(com.privasia.scss.common.annotation.LoggingInfor)", argNames="joinPoint")
	public void beforeMethodeExecute(JoinPoint joinPoint) {
		log.debug("Methode Signature : " + joinPoint.getSignature().getName());
		int argCount = 0;
		if(joinPoint.getArgs() != null){
			for (Object object : joinPoint.getArgs()) {
				log.debug("arg "+argCount+" : "+object.toString());
				argCount++;
			}
		}
	}
	
	@AfterReturning(pointcut = "@annotation(com.privasia.scss.common.annotation.LoggingInfor)", returning="returnValue", argNames="jointPoint, returnValue")
	public void  afterMehodeExecute(JoinPoint joinPoint, Object returnValue){
		log.debug("Methode Signature : " + joinPoint.getSignature().getName());
		if(returnValue != null){
			log.debug("Return Value :"+returnValue.toString());
		}
	}
	
}
