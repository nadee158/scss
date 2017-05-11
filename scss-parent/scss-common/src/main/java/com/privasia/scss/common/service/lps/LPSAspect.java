/**
 * 
 */
package com.privasia.scss.common.service.lps;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.privasia.scss.common.annotation.OpenGate;
import com.privasia.scss.common.dto.GateInWriteRequest;
import com.privasia.scss.common.dto.GateOutWriteRequest;

@Aspect
@Component
public class LPSAspect {
	
	private static final Log log = LogFactory.getLog(LPSAspect.class);
	
	
	@AfterReturning("@annotation(openGate)")
	@Async
	public void openGate(JoinPoint joinPoint, OpenGate openGate){
		
		Long clientID = null;
		
		if(joinPoint.getArgs()[0] instanceof GateInWriteRequest){
			GateInWriteRequest gateInWriteRequest = (GateInWriteRequest) joinPoint.getArgs()[0];
			clientID = gateInWriteRequest.getGateInClient();
		}else if(joinPoint.getArgs()[0] instanceof GateOutWriteRequest){
			GateOutWriteRequest gateOutWriteRequest = (GateOutWriteRequest) joinPoint.getArgs()[0];
			clientID = gateOutWriteRequest.getGateOutClient();
		}
		
		log.info("*****************   openGate called *************************");
		log.info("hijacked : " + joinPoint.getSignature().getName());
		log.info("Method returned value is : " + joinPoint.getArgs()[0]);
		log.info("******");
		
	}
	
}
